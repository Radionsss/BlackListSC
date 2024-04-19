package com.stalcraft.blackliststalcraft.presenter.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SplashViewModel @Inject constructor(private val playerRepo: PlayerRepo) : ViewModel() {
    fun isUserAuthenticated(): Boolean = playerRepo.isUserAuthenticated()
}