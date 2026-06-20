package com.rohan.chessfetch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohan.chessfetch.data.model.Player
import com.rohan.chessfetch.data.model.PlayerStats
import com.rohan.chessfetch.data.repository.ChessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlayerUiState(
    val isLoading: Boolean = false,
    val player: Player? = null,
    val stats: PlayerStats? = null,
    val error: String? = null
)

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val repository: ChessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    fun loadPlayer(username: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            repository.getPlayer(username).collect { result ->
                result.fold(
                    onSuccess = { player ->
                        _uiState.update { it.copy(player = player) }
                        loadStats(username)
                    },
                    onFailure = { e ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = when {
                                    e.message?.contains("404") == true ->
                                        "Player \"$username\" not found"
                                    e.message?.contains("Unable to resolve host") == true ->
                                        "No internet connection"
                                    else -> "Failed to load player"
                                }
                            )
                        }
                    }
                )
            }
        }
    }

    private fun loadStats(username: String) {
        viewModelScope.launch {
            repository.getPlayerStats(username).collect { result ->
                result.fold(
                    onSuccess = { stats ->
                        _uiState.update { it.copy(isLoading = false, stats = stats) }
                    },
                    onFailure = {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                )
            }
        }
    }

    fun reset() {
        _uiState.value = PlayerUiState()
    }
}
