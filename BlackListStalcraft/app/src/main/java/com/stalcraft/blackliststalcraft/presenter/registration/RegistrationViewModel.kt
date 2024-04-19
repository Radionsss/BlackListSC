package com.stalcraft.blackliststalcraft.presenter.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stalcraft.blackliststalcraft.core.utils.MyResult
import com.stalcraft.blackliststalcraft.domain.models.local.entities.BillingEntity
import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import com.stalcraft.blackliststalcraft.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _loginResult = MutableStateFlow<MyResult<Unit>?>(null)
    val loginResult: StateFlow<MyResult<Unit>?> = _loginResult

    fun createUser(name:String) {
        viewModelScope.launch {
            loginUseCase.loginUser(name)
                .collect { result ->
                    _loginResult.value = result
                }
        }
    }
}
