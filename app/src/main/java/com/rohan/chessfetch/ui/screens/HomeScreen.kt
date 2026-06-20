package com.rohan.chessfetch.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.PersonSearch
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rohan.chessfetch.ui.theme.Background
import com.rohan.chessfetch.ui.theme.BorderColor
import com.rohan.chessfetch.ui.theme.Gold
import com.rohan.chessfetch.ui.theme.OnSurface
import com.rohan.chessfetch.ui.theme.OnSurfaceLow
import com.rohan.chessfetch.ui.theme.OnSurfaceMedium
import com.rohan.chessfetch.ui.theme.SurfaceContainer
import com.rohan.chessfetch.ui.theme.SurfaceContainerHigh

private val suggestedPlayers = listOf(
    "hikaru", "magnuscarlsen", "fabianocaruana",
    "gothamchess", "penguingm", "anishgiri"
)

@Composable
fun HomeScreen(onPlayerSelected: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    fun submit() {
        if (query.isNotBlank()) {
            focusManager.clearFocus()
            onPlayerSelected(query.trim())
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(88.dp))

            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(SurfaceContainer)
                    .border(1.dp, BorderColor, RoundedCornerShape(22.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "\u265A",
                    style = MaterialTheme.typography.displayLarge.copy(
                        color = Gold,
                        fontSize = androidx.compose.ui.unit.TextUnit(
                            36f,
                            androidx.compose.ui.unit.TextUnitType.Sp
                        )
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Chess Fetch",
                style = MaterialTheme.typography.headlineLarge,
                color = OnSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Search any Chess.com player",
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurfaceMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(44.dp))

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Enter username…", color = OnSurfaceLow)
                },
                leadingIcon = {
                    Icon(Icons.Outlined.PersonSearch, contentDescription = null, tint = OnSurfaceMedium)
                },
                trailingIcon = {
                    AnimatedVisibility(visible = query.isNotEmpty(), enter = fadeIn(), exit = fadeOut()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Outlined.Close, contentDescription = "Clear", tint = OnSurfaceMedium)
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { submit() }),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Gold,
                    unfocusedBorderColor = BorderColor,
                    cursorColor = Gold,
                    focusedTextColor = OnSurface,
                    unfocusedTextColor = OnSurface,
                    focusedContainerColor = SurfaceContainer,
                    unfocusedContainerColor = SurfaceContainer
                ),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = OnSurface)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = { submit() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold,
                    contentColor = Color.Black,
                    disabledContainerColor = BorderColor,
                    disabledContentColor = OnSurfaceLow
                ),
                enabled = query.isNotBlank()
            ) {
                Icon(Icons.Outlined.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Search Player", style = MaterialTheme.typography.labelLarge.copy(color = Color.Black))
            }

            Spacer(modifier = Modifier.height(44.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f).height(1.dp).background(BorderColor))
                Text(
                    "  TOP PLAYERS  ",
                    style = MaterialTheme.typography.labelSmall,
                    color = OnSurfaceLow
                )
                Box(modifier = Modifier.weight(1f).height(1.dp).background(BorderColor))
            }

            Spacer(modifier = Modifier.height(16.dp))

            suggestedPlayers.chunked(3).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { handle ->
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onPlayerSelected(handle) },
                            color = SurfaceContainerHigh,
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderColor)
                        ) {
                            Text(
                                text = handle,
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                                style = MaterialTheme.typography.labelMedium,
                                color = OnSurfaceMedium,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
