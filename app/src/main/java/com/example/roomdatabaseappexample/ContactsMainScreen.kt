package com.example.roomdatabaseappexample

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ContactsMainScreen(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit
) {
    Scaffold(

        floatingActionButton = {
            Button(
                onClick = {
                    onEvent(ContactEvent.ShowDialog)
                },
                colors = ButtonDefaults.buttonColors(Color.DarkGray)
            ) {
                Text(text = "Add new contact")

            }
        },

        ) { _ ->

        if (state.isAddingContact) {
            AddContactDialogWindow(
                state = state,
                onEvent = onEvent
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = CenterVertically
                ) {
                    SortType.values().forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(ContactEvent.SortContacts(sortType = sortType))
                                },
                            verticalAlignment = CenterVertically
                        ) {
                            RadioButton(
                                selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(ContactEvent.SortContacts(sortType = sortType))
                                },
                            )
                            Text(text = sortType.name)
                        }

                    }

                }
            }
            items(state.contacts) { contact ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(width = 1.dp, color = Color.DarkGray),
                    shape = ShapeDefaults.Small,
                    colors = CardDefaults.cardColors(Color.LightGray)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 6.dp, start = 6.dp),
                                text = "${contact.firstName} ${contact.lastName}",
                                fontSize = 20.sp
                            )
                            Text(
                                modifier = Modifier.padding(6.dp),
                                text = contact.cellPhoneNumber,
                                fontSize = 15.sp
                            )
                        }
                        IconButton(
                            onClick = {
                                onEvent(ContactEvent.DeleteContact(contact))
                            }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete icon"
                            )

                        }
                    }
                }


            }


        }

    }

}