package com.stalcraft.blackliststalcraft.data.local.repo

import com.stalcraft.blackliststalcraft.data.local.MainDb
import com.stalcraft.blackliststalcraft.data.local.entities.BillingEntity
import com.stalcraft.blackliststalcraft.data.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayerRepoImpl @Inject constructor(mainDb: MainDb): PlayerRepo {
    private val playerDao = mainDb.getPlayerDao()
    override suspend fun getPlayerByNickAndStatus(nick: String, isGoodPerson: Boolean): PlayerEntity? {
       return playerDao.getPlayerByNickAndStatus(nick, isGoodPerson)
    }

    override suspend fun getAllPlayersByStatus(isGoodPerson: Boolean): Flow<List<PlayerEntity>> = playerDao.getAllPlayersByStatus(isGoodPerson)

    override suspend fun insertPlayer(player: PlayerEntity) {
        playerDao.insertPlayer(player)
    }

    override suspend fun deletePlayer(player: PlayerEntity) {
        playerDao.deletePlayer(player)
    }
    override suspend fun deletePlayerById(playerId: Int) {
        playerDao.deletePlayerById(playerId)
    }

    override suspend fun searchPlayers(query: String): Flow<List<PlayerEntity>> = playerDao.searchPlayers(query)


    override suspend fun updateIsGoodPersonAndPercentageAnger(
        playerId: Int,
        newIsGoodPerson: Boolean,
        newPercentageAnger: Int
    ) {
        playerDao.updateIsGoodPersonAndPercentageAnger(playerId, newIsGoodPerson, newPercentageAnger)
    }

    override suspend fun updateIsGoodPerson(playerId: Int, newIsGoodPerson: Boolean) {
        playerDao.updateIsGoodPerson(playerId,newIsGoodPerson)
    }

    override suspend fun updatePlayer(
        playerId: Int,
        newNick: String,
        newReason: String?,
        newIsGoodPerson: Boolean,
        newPercentageAnger: Int
    ) {
        playerDao.updatePlayer(playerId, newNick, newReason, newIsGoodPerson, newPercentageAnger)
    }
/*
    override suspend fun insertBillingProvider(billingEntity: BillingEntity) {
        playerDao.insertBillingProvider(billingEntity)
    }

  *//*  override suspend fun getProviderBiiling(): BillingEntity {
        return playerDao.getProviderBiiling()
    }
*//*
    override suspend fun deleteProvideBilling(billingEntity: BillingEntity) {
       playerDao.deleteProvideBilling(billingEntity)
    }

    override suspend fun updateProviderBilling(billingEntity: BillingEntity) {
       playerDao.updateProviderBilling(billingEntity)
    }*/

}
