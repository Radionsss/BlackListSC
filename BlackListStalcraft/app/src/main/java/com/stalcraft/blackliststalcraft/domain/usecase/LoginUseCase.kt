package com.stalcraft.blackliststalcraft.domain.usecase

import android.util.Log
import com.stalcraft.blackliststalcraft.core.utils.MyResult
import com.stalcraft.blackliststalcraft.core.utils.toPlayerEntity
import com.stalcraft.blackliststalcraft.core.utils.toUserEntity
import com.stalcraft.blackliststalcraft.domain.models.local.entities.UserEntity
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val playerRepo: PlayerRepo) {

    suspend fun loginUser(nameUser: String): Flow<MyResult<Unit>> = flow {
        emit(MyResult.Loading)

        try {
            playerRepo.createUserRemote(nameUser)
            val remote = playerRepo.getAllPlayersRemote().map { it.toPlayerEntity() }
            val user = playerRepo.getUser()
            withContext(Dispatchers.IO) {

                playerRepo.createUser(user!!.toUserEntity())
                playerRepo.insertPlayers(remote)
            }
            emit(MyResult.Success(data = Unit))
        } catch (e: Exception) {
            Log.d("rerererere", "${e.message}")
            emit(MyResult.Failure(e))
        }

    }
}