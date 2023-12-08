package com.example.roomdatabaseappexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabaseappexample.room.ContactDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactViewModel(private val dao: ContactDao) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _contacts = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortType.FIRST_NAME -> dao.getContactsOrderedByFirstName()
                SortType.LAST_NAME -> dao.getContactsOrderedByLastName()
                SortType.CELL_PHONE_NUMBER -> dao.getContactsOrderedByCellPhoneNumber()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    private val _state = MutableStateFlow(ContactState())
    val state = combine(_state, _contacts, _sortType) { state, contacts, sortType ->
        state.copy(
            contacts = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), ContactState())


    fun onEvent(event: ContactEvent) {

        when (event) {

            is ContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    dao.deleteContact(contact = event.contact)
                }
            }

            ContactEvent.HideDialog -> {
                _state.update { it.copy(isAddingContact = false) }
            }

            ContactEvent.SaveContact -> {

                val _firstName = state.value.firstName
                val _lastName = state.value.lastName
                val _cellPhoneNumber = state.value.cellPhoneNumber

                if (_firstName.isBlank() || _lastName.isBlank() || _cellPhoneNumber.isBlank()) {
                    return
                }

                val contact = Contact(
                    firstName = _firstName,
                    lastName = _lastName,
                    cellPhoneNumber = _cellPhoneNumber
                )
                viewModelScope.launch {
                    dao.upsertContact(contact)
                }
                _state.update {
                    it.copy(
                        isAddingContact = false,
                        firstName = "",
                        lastName = "",
                        cellPhoneNumber = ""

                    )
                }

            }

            is ContactEvent.SetFirstName -> {
                _state.update {
                    it.copy(firstName = event.firstName)
                }
            }

            is ContactEvent.SetLastName -> {
                _state.update {
                    it.copy(lastName = event.lastName)
                }
            }

            is ContactEvent.SetCellPhoneNumber -> {
                _state.update {
                    it.copy(cellPhoneNumber = event.cellPhoneNumber)
                }
            }

            ContactEvent.ShowDialog -> {
                _state.update {
                    it.copy(isAddingContact = true)
                }
            }

            is ContactEvent.SortContacts -> {
                _sortType.value = event.sortType
            }

        }
    }


}