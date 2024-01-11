package com.stalcraft.blackliststalcraft.presenter.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stalcraft.blackliststalcraft.core.exception.AddPlayerErrorEnum
import com.stalcraft.blackliststalcraft.data.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import com.stalcraft.blackliststalcraft.domain.usecase.AddUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlayerViewModel  @Inject constructor(private val addUseCase: AddUseCase,private val playerRepo: PlayerRepo): ViewModel() {
    fun insertPlayer(playerEntity: PlayerEntity, isSuccess: ((AddPlayerErrorEnum) -> Unit)) {
        viewModelScope.launch {
            addUseCase.insertUserInfo(playerEntity, isSuccess)
        }
    }
    fun updatePlayer(playerEntity: PlayerEntity) {
        viewModelScope.launch {
            playerEntity.id?.let { playerRepo.updatePlayer(it,playerEntity.nick,playerEntity.reason,playerEntity.isGoodPerson,playerEntity.percentageAnger) }
        }
    }

}