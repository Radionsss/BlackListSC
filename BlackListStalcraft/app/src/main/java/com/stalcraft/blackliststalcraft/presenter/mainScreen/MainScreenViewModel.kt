package com.stalcraft.blackliststalcraft.presenter.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stalcraft.blackliststalcraft.data.local.entities.BillingEntity
import com.stalcraft.blackliststalcraft.data.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val playerRepo: PlayerRepo) : ViewModel() {
    private val _allPlayers = MutableStateFlow<List<PlayerEntity>>(emptyList())
    val allPlayers: StateFlow<List<PlayerEntity>> = _allPlayers
    private val _badPlayers = MutableStateFlow<List<PlayerEntity>>(emptyList())
    val badPlayers: StateFlow<List<PlayerEntity>> = _badPlayers


    /*  fun getProvider(billing:((BillingEntity)->Unit)){
          viewModelScope.launch {
              billing.invoke(playerRepo.getProviderBiiling())
          }
      }*/

    fun getAllPlayer() {
        viewModelScope.launch {
            playerRepo.getAllPlayersByStatus(true).collect { users ->
                _allPlayers.value = users
            }
        }
    }

    fun getBadAllPlayer() {
        viewModelScope.launch {
            playerRepo.getAllPlayersByStatus(false).collect { users ->
                _badPlayers.value = users
            }
        }
    }

    fun updateIsGoodPerson(playerId: Int, newIsGoodPerson: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepo.updateIsGoodPerson(playerId, newIsGoodPerson)
        }
    }

    fun updateIsGoodPersonAndPercentageAnger(
        playerId: Int,
        newIsGoodPerson: Boolean,
        newPercentageAnger: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepo.updateIsGoodPersonAndPercentageAnger(
                playerId,
                newIsGoodPerson,
                newPercentageAnger
            )
        }
    }

    fun searchGoodPlayers(query: String) {
        viewModelScope.launch {
            playerRepo.searchPlayers(query).collect { users ->
                if (users.isEmpty()) {

                }
                _allPlayers.value = users
            }
        }
    }

    fun searchBadPlayers(query: String) {
        viewModelScope.launch {
            playerRepo.searchPlayers(query).collect { users ->
                _badPlayers.value = users
            }
        }
    }

    fun deletePlayerById(playerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepo.deletePlayerById(playerId)
        }
    }
}
