package com.stalcraft.blackliststalcraft.presenter.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stalcraft.blackliststalcraft.core.exception.AddPlayerErrorEnum
import com.stalcraft.blackliststalcraft.core.utils.MyResult
import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import com.stalcraft.blackliststalcraft.domain.usecase.AddUseCase
import com.stalcraft.blackliststalcraft.domain.usecase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlayerViewModel  @Inject constructor(private val addUseCase: AddUseCase,private val updateUserUseCase: UpdateUserUseCase): ViewModel() {
    private val _userResult = MutableStateFlow<MyResult<Unit>?>(null)
    val userResult: StateFlow<MyResult<Unit>?> = _userResult
    fun insertPlayer(playerEntity: PlayerEntity) {
        viewModelScope.launch {
            addUseCase.insertUserInfo(playerEntity)
                .collect { result ->
                    _userResult.value = result
                }
        }
    }
    fun updatePlayer(playerEntity: PlayerEntity) {
        viewModelScope.launch {
            updateUserUseCase.updatePlayer(playerEntity)
                .collect { result ->
                    _userResult.value = result
                }
        }
    }
}