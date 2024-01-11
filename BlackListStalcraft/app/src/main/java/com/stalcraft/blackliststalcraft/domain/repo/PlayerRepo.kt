package com.stalcraft.blackliststalcraft.domain.repo

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.stalcraft.blackliststalcraft.data.local.entities.BillingEntity
import com.stalcraft.blackliststalcraft.data.local.entities.PlayerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface PlayerRepo {
    suspend fun getPlayerByNickAndStatus(nick: String, isGoodPerson: Boolean): PlayerEntity?
    suspend fun getAllPlayersByStatus(isGoodPerson: Boolean): Flow<List<PlayerEntity>>
    suspend fun insertPlayer(player: PlayerEntity)
    suspend fun deletePlayer(player: PlayerEntity)
    suspend fun deletePlayerById(playerId: Int)
    suspend fun searchPlayers(query: String): Flow<List<PlayerEntity>>
    suspend fun updateIsGoodPersonAndPercentageAnger(playerId: Int, newIsGoodPerson: Boolean, newPercentageAnger: Int)
    suspend fun updateIsGoodPerson(playerId: Int, newIsGoodPerson: Boolean)
    suspend fun updatePlayer(playerId: Int, newNick: String, newReason: String?, newIsGoodPerson: Boolean, newPercentageAnger: Int)

/*    suspend fun insertBillingProvider(billingEntity: BillingEntity)
   // suspend fun getProviderBiiling(): BillingEntity
    suspend fun deleteProvideBilling(billingEntity: BillingEntity)
    suspend fun updateProviderBilling(billingEntity: BillingEntity)*/
}