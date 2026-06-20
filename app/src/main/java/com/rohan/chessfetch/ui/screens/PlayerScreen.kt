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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rohan.chessfetch.data.model.ChessMode
import com.rohan.chessfetch.ui.theme.Background
import com.rohan.chessfetch.ui.theme.BlitzColor
import com.rohan.chessfetch.ui.theme.BorderColor
import com.rohan.chessfetch.ui.theme.BulletColor
import com.rohan.chessfetch.ui.theme.ClassicalColor
import com.rohan.chessfetch.ui.theme.DividerColor
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
import com.rohan.chessfetch.viewmodel.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    username: String,
    onBack: () -> Unit,
    onViewGames: () -> Unit,
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(username) { viewModel.loadPlayer(username) }

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        uiState.player?.username?.lowercase() ?: username.lowercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = OnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
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
                    Column(
                        modifier = Modifier.align(Alignment.Center).padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            uiState.error!!,
                            style = MaterialTheme.typography.bodyMedium,
                            color = LossRed
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = onBack,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderColor)
                        ) {
                            Text("Go Back", color = OnSurfaceMedium)
                        }
                    }
                }

                uiState.player != null -> {
                    val player = uiState.player!!
                    val stats  = uiState.stats

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp)
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))

                        // Header card
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(18.dp))
                                .background(SurfaceContainer)
                                .border(1.dp, BorderColor, RoundedCornerShape(18.dp))
                                .padding(20.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                AsyncImage(
                                    model = player.avatar ?: "https://www.chess.com/bundles/web/images/user-image.007dad08.svg",
                                    contentDescription = "Avatar",
                                    modifier = Modifier
                                        .size(88.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, Gold, CircleShape)
                                        .background(Background)
                                )

                                Spacer(modifier = Modifier.height(14.dp))

                                if (player.title != null) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Gold.copy(alpha = 0.15f))
                                            .border(1.dp, Gold.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                                            .padding(horizontal = 10.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            player.title!!,
                                            style = MaterialTheme.typography.labelMedium,
                                            color = Gold
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                }

                                Text(
                                    player.name ?: player.username,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = OnSurface
                                )

                                Text(
                                    "@${player.username.lowercase()}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnSurfaceMedium
                                )

                                Spacer(modifier = Modifier.height(18.dp))
                                Divider(color = DividerColor)
                                Spacer(modifier = Modifier.height(18.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    StatPill(
                                        icon = Icons.Outlined.Group,
                                        label = "Followers",
                                        value = formatCount(player.followers)
                                    )
                                    StatPill(
                                        icon = Icons.Outlined.DateRange,
                                        label = "Joined",
                                        value = TimeUtil.formatJoinDate(player.joined)
                                    )
                                    StatPill(
                                        icon = Icons.Outlined.History,
                                        label = "Last seen",
                                        value = TimeUtil.formatLastSeen(player.lastOnline)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Ratings
                        if (stats != null) {
                            Text(
                                "RATINGS",
                                style = MaterialTheme.typography.labelSmall,
                                color = OnSurfaceLow
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                RatingCard(
                                    modifier = Modifier.weight(1f),
                                    label = "Bullet",
                                    color = BulletColor,
                                    mode = stats.bullet
                                )
                                RatingCard(
                                    modifier = Modifier.weight(1f),
                                    label = "Blitz",
                                    color = BlitzColor,
                                    mode = stats.blitz
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                RatingCard(
                                    modifier = Modifier.weight(1f),
                                    label = "Rapid",
                                    color = RapidColor,
                                    mode = stats.rapid
                                )
                                RatingCard(
                                    modifier = Modifier.weight(1f),
                                    label = "Daily",
                                    color = ClassicalColor,
                                    mode = stats.daily
                                )
                            }

                            // Tactics
                            if (stats.tactics?.highest != null) {
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    "TACTICS",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OnSurfaceLow
                                )
                                Spacer(modifier = Modifier.height(10.dp))
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
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                Icons.Outlined.EmojiEvents,
                                                contentDescription = null,
                                                tint = Gold,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text("Puzzle Rating", style = MaterialTheme.typography.bodyMedium, color = OnSurfaceMedium)
                                        }
                                        Text(
                                            "${stats.tactics!!.highest!!.rating}",
                                            style = MaterialTheme.typography.titleLarge,
                                            color = Gold
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = onViewGames,
                            modifier = Modifier.fillMaxWidth().height(52.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Gold,
                                contentColor = Color.Black
                            )
                        ) {
                            Icon(Icons.Outlined.QueryStats, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("View Recent Games", style = MaterialTheme.typography.labelLarge.copy(color = Color.Black))
                        }

                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun StatPill(icon: ImageVector, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = Gold, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, style = MaterialTheme.typography.labelLarge, color = OnSurface)
        Text(label, style = MaterialTheme.typography.labelSmall, color = OnSurfaceLow)
    }
}

@Composable
private fun RatingCard(
    modifier: Modifier = Modifier,
    label: String,
    color: Color,
    mode: ChessMode?
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceContainer)
            .border(1.dp, BorderColor, RoundedCornerShape(14.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(label, style = MaterialTheme.typography.labelMedium, color = OnSurfaceMedium)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = if (mode?.last != null) "${mode.last.rating}" else "—",
                style = MaterialTheme.typography.headlineMedium,
                color = if (mode?.last != null) OnSurface else OnSurfaceLow
            )
            if (mode?.best != null) {
                Text(
                    "Best ${mode.best.rating}",
                    style = MaterialTheme.typography.labelSmall,
                    color = OnSurfaceLow
                )
            }
            if (mode?.record != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("${mode.record.win}W", style = MaterialTheme.typography.labelSmall, color = WinGreen)
                    Text("${mode.record.draw}D", style = MaterialTheme.typography.labelSmall, color = OnSurfaceLow)
                    Text("${mode.record.loss}L", style = MaterialTheme.typography.labelSmall, color = LossRed)
                }
            }
        }
    }
}

private fun formatCount(n: Int): String = when {
    n >= 1_000_000 -> "${n / 1_000_000}M"
    n >= 1_000     -> "${n / 1_000}K"
    else           -> "$n"
}
