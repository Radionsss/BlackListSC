package com.stalcraft.blackliststalcraft.domain.usecase

import android.util.Log
import com.stalcraft.blackliststalcraft.core.utils.MyResult
import com.stalcraft.blackliststalcraft.core.utils.toPlayerEntity
import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

class DeleteUseCase @Inject constructor(private val playerRepo: PlayerRepo) {

    fun deletePlayer(playerId: String): Flow<MyResult<Unit>> = flow {
        emit(MyResult.Loading)
        Log.d("deletePlayer", "deletePlayer: $playerId")
        try {
            val response = playerRepo.deletePlayerRemote(playerId)
            withContext(Dispatchers.IO) {
                playerRepo.deletePlayerById(playerId)
            }
            emit(MyResult.Success(data = response))
        } catch (e: Exception) {
            Log.d("rerererere", "$e")
            emit(MyResult.Failure(e))
        }
    }
}