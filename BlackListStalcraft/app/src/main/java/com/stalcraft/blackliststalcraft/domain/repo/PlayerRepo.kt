package com.stalcraft.blackliststalcraft.domain.repo

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.stalcraft.blackliststalcraft.core.utils.MyResult
import com.stalcraft.blackliststalcraft.domain.models.local.entities.BillingEntity
import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.models.local.entities.UserEntity
import com.stalcraft.blackliststalcraft.domain.models.remote.models.PlayerModel
import com.stalcraft.blackliststalcraft.domain.models.remote.models.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface PlayerRepo {
    suspend fun getPlayerByNickAndStatus(nick: String, isGoodPerson: Boolean): PlayerEntity?
    suspend fun getAllPlayersByStatus(isGoodPerson: Boolean): Flow<List<PlayerEntity>>
    suspend fun insertPlayer(player: PlayerEntity)
    suspend fun insertPlayers(players: List<PlayerEntity>)
    suspend fun getUser(): UserModel?
    suspend fun insertPlayerRemote(player: PlayerModel)
    suspend fun updatePlayerRemote(player: PlayerModel)
    suspend fun deletePlayer(player: PlayerEntity)
    suspend fun deletePlayerById(playerId: String)
    suspend fun searchPlayers(query: String): Flow<List<PlayerEntity>>
    suspend fun updateIsGoodPersonAndPercentageAnger(
        playerId: String,
        newIsGoodPerson: Boolean,
        newPercentageAnger: Int
    )

    suspend fun updateIsGoodPerson(playerId: String, newIsGoodPerson: Boolean)
    suspend fun updatePlayer(
        playerId: String,
        newNick: String,
        newReason: String?,
        newIsGoodPerson: Boolean,
        newPercentageAnger: Int
    )

    suspend fun createUser(userEntity: UserEntity)
    suspend fun createUserRemote(name:String)
    suspend fun getAllPlayersRemote(): List<PlayerModel>
    suspend fun getAllPlayersFromAllUsers(): List<PlayerModel>
    suspend fun changeGoodPersonPlayer(
        playerId: String,
        isGoodPerson: Boolean,
        percentageAnger: Int
    )

    fun isUserAuthenticated(): Boolean

    /*    suspend fun insertBillingProvider(billingEntity: BillingEntity)
       // suspend fun getProviderBiiling(): BillingEntity
        suspend fun deleteProvideBilling(billingEntity: BillingEntity)
        suspend fun updateProviderBilling(billingEntity: BillingEntity)*/
}