package com.stalcraft.blackliststalcraft.domain.usecase

import android.util.Log
import com.stalcraft.blackliststalcraft.core.exception.AddPlayerErrorEnum
import com.stalcraft.blackliststalcraft.core.utils.MyResult
import com.stalcraft.blackliststalcraft.core.utils.toPlayerModel
import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.models.remote.models.PlayerModel
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class AddUseCase @Inject constructor(private val playerRepo: PlayerRepo) {
    fun insertUserInfo(playerEntity: PlayerEntity): Flow<MyResult<Unit>> = flow {
        emit(MyResult.Loading)
        Log.d("rererecsdcscrere", "$playerEntity")

        try {
            val response = playerRepo.insertPlayerRemote(playerEntity.toPlayerModel())
            withContext(Dispatchers.IO) {
                playerRepo.insertPlayer(playerEntity)
            }
            emit(MyResult.Success(data = response))
        } catch (e: Exception) {
            Log.d("rererecsdcscrere", "$e")
            emit(MyResult.Failure(e))
        }
    }
}