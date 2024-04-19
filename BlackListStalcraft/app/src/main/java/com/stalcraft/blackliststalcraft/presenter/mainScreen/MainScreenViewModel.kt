package com.stalcraft.blackliststalcraft.presenter.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stalcraft.blackliststalcraft.core.utils.MyResult
import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import com.stalcraft.blackliststalcraft.domain.usecase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val playerRepo: PlayerRepo,private val updateUserUseCase: UpdateUserUseCase) : ViewModel() {
    private val _goodsPlayers = MutableStateFlow<List<PlayerEntity>>(emptyList())
    val goodsPlayers: StateFlow<List<PlayerEntity>> = _goodsPlayers
    private val _badPlayers = MutableStateFlow<List<PlayerEntity>>(emptyList())
    val badPlayers: StateFlow<List<PlayerEntity>> = _badPlayers

    private val _userUpdateResult = MutableStateFlow<MyResult<Unit>?>(null)
    val userUpdateResult: StateFlow<MyResult<Unit>?> = _userUpdateResult


    /*  fun getProvider(billing:((BillingEntity)->Unit)){
          viewModelScope.launch {
              billing.invoke(playerRepo.getProviderBiiling())
          }
      }*/

    fun getAllPlayer() {
        viewModelScope.launch {
            playerRepo.getAllPlayersByStatus(true).collect { users ->
                _goodsPlayers.value = users
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

    fun updateIsGoodPersonAndPercentageAnger(
        playerId: String,
        newIsGoodPerson: Boolean,
        newPercentageAnger: Int
    ) {
        viewModelScope.launch {
            updateUserUseCase.changeGoodPersonPlayer(playerId,
                newIsGoodPerson,
                newPercentageAnger)
                .collect { result ->
                    _userUpdateResult.value = result
                }
        }
    }

    fun searchGoodPlayers(query: String) {
        viewModelScope.launch {
            playerRepo.searchPlayers(query).collect { users ->
                _goodsPlayers.value = users
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

    fun deletePlayerById(playerId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playerRepo.deletePlayerById(playerId)
        }
    }
}
