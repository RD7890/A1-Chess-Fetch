package com.rohan.chessfetch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rohan.chessfetch.data.model.Game
import com.rohan.chessfetch.ui.theme.Background
import com.rohan.chessfetch.ui.theme.BlitzColor
import com.rohan.chessfetch.ui.theme.BorderColor
import com.rohan.chessfetch.ui.theme.BulletColor
import com.rohan.chessfetch.ui.theme.ClassicalColor
import com.rohan.chessfetch.ui.theme.DrawGray
import com.rohan.chessfetch.ui.theme.Gold
import com.rohan.chessfetch.ui.theme.LossRed
import com.rohan.chessfetch.ui.theme.OnSurface
import com.rohan.chessfetch.ui.theme.OnSurfaceLow
import com.rohan.chessfetch.ui.theme.OnSurfaceMedium
import com.rohan.chessfetch.ui.theme.RapidColor
import com.rohan.chessfetch.ui.theme.Surface
import com.rohan.chessfetch.ui.theme.SurfaceContainer
import com.rohan.chessfetch.ui.theme.WinGreen
import com.rohan.chessfetch.util.TimeUtil
import com.rohan.chessfetch.viewmodel.GamesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesScreen(
    username: String,
    onBack: () -> Unit,
    viewModel: GamesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(username) { viewModel.loadGames(username) }

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Recent Games",
                            style = MaterialTheme.typography.titleMedium,
                            color = OnSurface
                        )
                        Text(
                            "@${username.lowercase()}",
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceMedium
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBackIosNew, contentDescription = "Back", tint = OnSurface)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background,
                    scrolledContainerColor = Surface
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Gold,
                        strokeWidth = 2.dp
                    )
                }

                uiState.error != null -> {
                    Text(
                        uiState.error!!,
                        modifier = Modifier.align(Alignment.Center).padding(24.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = LossRed
                    )
                }

                uiState.games.isEmpty() -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Outlined.SportsEsports,
                            contentDescription = null,
                            tint = OnSurfaceLow,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "No games found",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceLow
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(
                            horizontal = 20.dp,
                            vertical = 12.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            Text(
                                "${uiState.games.size} GAMES THIS MONTH",
                                style = MaterialTheme.typography.labelSmall,
                                color = OnSurfaceLow,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        items(uiState.games, key = { it.url }) { game ->
                            GameRow(game = game, viewerUsername = username)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GameRow(game: Game, viewerUsername: String) {
    val isWhite = game.white.username.lowercase() == viewerUsername.lowercase()
    val myPlayer = if (isWhite) game.white else game.black
    val opponent = if (isWhite) game.black else game.white

    val resultColor = when (myPlayer.result.lowercase()) {
        "win"     -> WinGreen
        "resigned", "timeout", "checkmated", "abandoned" -> LossRed
        else      -> DrawGray
    }
    val resultLabel = when (myPlayer.result.lowercase()) {
        "win"     -> "W"
        "resigned", "timeout", "checkmated", "abandoned" -> "L"
        else      -> "D"
    }

    val timeColor = when (game.timeClass.lowercase()) {
        "bullet"    -> BulletColor
        "blitz"     -> BlitzColor
        "rapid"     -> RapidColor
        else        -> ClassicalColor
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceContainer)
            .border(1.dp, BorderColor, RoundedCornerShape(14.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Result indicator
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(resultColor.copy(alpha = 0.12f))
                    .border(1.dp, resultColor.copy(alpha = 0.4f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    resultLabel,
                    style = MaterialTheme.typography.titleMedium,
                    color = resultColor
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        if (isWhite) "\u2659" else "\u265F",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceMedium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "vs ${opponent.username}",
                        style = MaterialTheme.typography.titleSmall,
                        color = OnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PillLabel(
                        text = game.timeClass.replaceFirstChar { it.uppercaseChar() },
                        color = timeColor
                    )
                    if (game.rated) {
                        PillLabel(text = "Rated", color = Gold)
                    }
                    Text(
                        TimeUtil.formatGameDate(game.endTime),
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceLow
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "${myPlayer.rating}",
                    style = MaterialTheme.typography.titleMedium,
                    color = OnSurface
                )
                Text(
                    "vs ${opponent.rating}",
                    style = MaterialTheme.typography.labelSmall,
                    color = OnSurfaceLow
                )
            }
        }
    }
}

@Composable
private fun PillLabel(text: String, color: androidx.compose.ui.graphics.Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelSmall, color = color)
    }
}
