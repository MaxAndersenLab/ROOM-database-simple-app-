package com.example.roomdatabaseappexample

data class ContactState(
    val contacts: List<Contact> = emptyList(),
    val firstName: String = "",
    val lastName: String = "",
    val cellPhoneNumber: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME
)
