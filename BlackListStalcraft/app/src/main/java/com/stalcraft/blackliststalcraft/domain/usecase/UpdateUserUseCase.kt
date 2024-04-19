package com.stalcraft.blackliststalcraft.domain.usecase

import android.util.Log
import com.stalcraft.blackliststalcraft.core.utils.MyResult
import com.stalcraft.blackliststalcraft.core.utils.toPlayerModel
import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(private val playerRepo: PlayerRepo) {

    fun changeGoodPersonPlayer(playerId: String, isGoodPerson: Boolean, percentageAnger: Int): Flow<MyResult<Unit>> = flow {
        emit(MyResult.Loading)

        try {
            val response = playerRepo.changeGoodPersonPlayer(playerId, isGoodPerson, percentageAnger)
            withContext(Dispatchers.IO) {
                playerRepo.updateIsGoodPersonAndPercentageAnger(playerId,isGoodPerson,percentageAnger)
            }
            emit(MyResult.Success(data = response))
        } catch (e: Exception) {
            Log.d("rerererere", "$e")
            emit(MyResult.Failure(e))
        }
    }
    fun updatePlayer(playerEntity: PlayerEntity): Flow<MyResult<Unit>> = flow {
        emit(MyResult.Loading)

        try {
            val response = playerRepo.updatePlayerRemote(playerEntity.toPlayerModel())
            withContext(Dispatchers.IO) {
                playerRepo.updatePlayer(playerEntity.id,playerEntity.nick,playerEntity.reason,playerEntity.isGoodPerson,playerEntity.percentageAnger)
            }
            emit(MyResult.Success(data = response))
        } catch (e: Exception) {
            Log.d("rerererere", "$e")
            emit(MyResult.Failure(e))
        }
    }
}