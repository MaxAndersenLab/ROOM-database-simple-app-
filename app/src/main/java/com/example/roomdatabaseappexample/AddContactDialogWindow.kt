package com.example.roomdatabaseappexample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactDialogWindow(
    modifier: Modifier = Modifier,
    state: ContactState,
    onEvent: (ContactEvent) -> Unit
) {
    AlertDialog(

        modifier = modifier,
        onDismissRequest = { onEvent(ContactEvent.HideDialog) },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = { onEvent(ContactEvent.SaveContact) }) {
                    Text(text = "Save")
                }
            }

        },
        dismissButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = { onEvent(ContactEvent.HideDialog) }) {
                    Text(text = "Cancel")
                }
            }
        },

        title = { Text(text = "Add new contact") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                TextField(
                    value = state.firstName,
                    onValueChange = {
                        onEvent(ContactEvent.SetFirstName(it))
                    },
                    placeholder = {
                        Text(text = "First name")
                    }
                )
                TextField(
                    value = state.lastName,
                    onValueChange = {
                        onEvent(ContactEvent.SetLastName(it))
                    },
                    placeholder = {
                        Text(text = "Last name")
                    }
                )
                TextField(
                    value = state.cellPhoneNumber,
                    onValueChange = {
                        onEvent(ContactEvent.SetCellPhoneNumber(it))
                    },
                    placeholder = {
                        Text(text = "Cellphone number")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
        },

        )
}

