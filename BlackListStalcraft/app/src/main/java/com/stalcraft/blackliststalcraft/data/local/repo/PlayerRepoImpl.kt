package com.stalcraft.blackliststalcraft.data.local.repo

import android.util.Log
import com.stalcraft.blackliststalcraft.core.utils.MyResult
import com.stalcraft.blackliststalcraft.data.local.MainDb
import com.stalcraft.blackliststalcraft.data.remote.FireBaseMain
import com.stalcraft.blackliststalcraft.domain.models.local.entities.BillingEntity
import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.models.local.entities.UserEntity
import com.stalcraft.blackliststalcraft.domain.models.remote.models.PlayerModel
import com.stalcraft.blackliststalcraft.domain.models.remote.models.UserModel
import com.stalcraft.blackliststalcraft.domain.repo.PlayerRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayerRepoImpl @Inject constructor(mainDb: MainDb, private val api: FireBaseMain) :
    PlayerRepo {
    private val playerDao = mainDb.getPlayerDao()
    override suspend fun getPlayerByNickAndStatus(
        nick: String,
        isGoodPerson: Boolean
    ): PlayerEntity? {
        return playerDao.getPlayerByNickAndStatus(nick, isGoodPerson)
    }

    override suspend fun getAllPlayersByStatus(isGoodPerson: Boolean): Flow<List<PlayerEntity>> =
        playerDao.getAllPlayersByStatus(isGoodPerson)

    override suspend fun insertPlayer(player: PlayerEntity) {
        playerDao.insertPlayer(player)
    }

    override suspend fun insertPlayers(players: List<PlayerEntity>) {
        playerDao.insertPlayers(players)
    }

    override suspend fun getUser(): UserModel? {
       return api.getUser()
    }

    override suspend fun insertPlayerRemote(player: PlayerModel){
         api.addPlayerToUser(player)
    }

    override suspend fun updatePlayerRemote(player: PlayerModel) {
        api.updatePlayer(player)
    }

    override suspend fun deletePlayer(player: PlayerEntity) {
        playerDao.deletePlayer(player)
    }

    override suspend fun deletePlayerById(playerId: String) {
        playerDao.deletePlayerById(playerId)
    }

    override suspend fun searchPlayers(query: String): Flow<List<PlayerEntity>> =
        playerDao.searchPlayers(query)


    override suspend fun updateIsGoodPersonAndPercentageAnger(
        playerId: String,
        newIsGoodPerson: Boolean,
        newPercentageAnger: Int
    ) {
        playerDao.updateIsGoodPersonAndPercentageAnger(
            playerId,
            newIsGoodPerson,
            newPercentageAnger
        )
    }

    override suspend fun updateIsGoodPerson(playerId: String, newIsGoodPerson: Boolean) {
        playerDao.updateIsGoodPerson(playerId, newIsGoodPerson)
    }

    override suspend fun updatePlayer(
        playerId: String,
        newNick: String,
        newReason: String?,
        newIsGoodPerson: Boolean,
        newPercentageAnger: Int
    ) {
        playerDao.updatePlayer(playerId, newNick, newReason, newIsGoodPerson, newPercentageAnger)
    }

    override suspend fun createUser(userEntity: UserEntity) {
       playerDao.insertUser(userEntity)
    }

    override suspend fun createUserRemote(name: String) {
        api.addUser(name)
    }

    override suspend fun getAllPlayersRemote(): List<PlayerModel> {
        return api.getAllPlayers()
    }

    override suspend fun getAllPlayersFromAllUsers(): List<PlayerModel> {
        return api.getAllPlayersFromAllUsers()
    }

    override suspend fun changeGoodPersonPlayer(
        playerId: String,
        isGoodPerson: Boolean,
        percentageAnger: Int
    ) {
        api.changeGoodPersonPlayer(playerId, isGoodPerson, percentageAnger)
    }

    override fun isUserAuthenticated(): Boolean {
        return api.isUserAuthenticated()
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
