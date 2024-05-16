package com.stalcraft.blackliststalcraft.domain.usecase

import android.util.Log
import com.stalcraft.blackliststalcraft.core.utils.MyResult
import com.stalcraft.blackliststalcraft.core.utils.toPlayerEntity
import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class LoadUseCase @Inject constructor(private val playerRepo: PlayerRepo) {

    fun getAllPlayersFromAllUsers(): Flow<MyResult<List<PlayerEntity>>> = flow {
        emit(MyResult.Loading)
        try {
            val response = playerRepo.getAllPlayersFromAllUsers().map { it.toPlayerEntity(false) }
            emit(MyResult.Success(data = response))

        } catch (e: TimeoutCancellationException) {
            Log.d("TimeoutError", "Request timed out")
            emit(MyResult.Failure(e))
        } catch (e: Exception) {
            Log.d("rerererere", "$e")
            emit(MyResult.Failure(e))
        }
    }

}