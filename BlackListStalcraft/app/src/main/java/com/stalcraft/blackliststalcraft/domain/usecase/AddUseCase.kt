package com.stalcraft.blackliststalcraft.domain.usecase

import android.util.Log
import com.stalcraft.blackliststalcraft.core.exception.AddPlayerErrorEnum
import com.stalcraft.blackliststalcraft.data.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class AddUseCase  @Inject constructor(private val playerRepo: PlayerRepo) {

    suspend fun insertUserInfo(playerEntity: PlayerEntity, isSuccess: ((AddPlayerErrorEnum) -> Unit)){
        try {
            if(playerEntity.nick.trim().isEmpty()){
                isSuccess.invoke(AddPlayerErrorEnum.NICK_EMPTY)
            }else{
                isSuccess.invoke(AddPlayerErrorEnum.SUCCESS)
                withContext(Dispatchers.IO) {
                    playerRepo.insertPlayer(playerEntity)
                }
            }
        }catch (e: IOException){
            Log.d("dwdw",e.toString())
            isSuccess.invoke(AddPlayerErrorEnum.ERROR)
        }catch (e:NullPointerException){
            Log.d("dwdw",e.toString())
            isSuccess.invoke(AddPlayerErrorEnum.NULL_POINT_ERROR)
        }catch (e:Exception){
            Log.d("dwdw",e.toString())
            isSuccess.invoke(AddPlayerErrorEnum.UNKNOWN_ERROR)
        }
    }
}