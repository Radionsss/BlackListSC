package com.stalcraft.blackliststalcraft.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.stalcraft.blackliststalcraft.data.local.entities.BillingEntity
import com.stalcraft.blackliststalcraft.data.local.entities.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

 /*   @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBillingProvider(billingEntity: BillingEntity)

 *//*   @Query("SELECT * FROM BillingEntityTable")
    suspend fun getProviderBiiling():BillingEntity
*//*
    @Delete
    suspend fun deleteProvideBilling(billingEntity: BillingEntity)

    @Update
    suspend fun updateProviderBilling(billingEntity: BillingEntity)
*/
    @Query("SELECT * FROM PlayerEntityTable WHERE nick = :nick AND isGoodPerson = :isGoodPerson")
    fun getPlayerByNickAndStatus(nick: String, isGoodPerson: Boolean): PlayerEntity?

    @Query("SELECT * FROM PlayerEntityTable WHERE isGoodPerson = :isGoodPerson")
    fun getAllPlayersByStatus(isGoodPerson: Boolean): Flow<List<PlayerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: PlayerEntity)

    @Query("UPDATE PlayerEntityTable SET isGoodPerson = :newIsGoodPerson WHERE id = :playerId")
    suspend fun updateIsGoodPerson(playerId: Int, newIsGoodPerson: Boolean)

    @Query("UPDATE PlayerEntityTable SET isGoodPerson = :newIsGoodPerson, percentageAnger = :newPercentageAnger WHERE id = :playerId")
    suspend fun updateIsGoodPersonAndPercentageAnger(playerId: Int, newIsGoodPerson: Boolean, newPercentageAnger: Int)

    @Query("SELECT * FROM PlayerEntityTable WHERE nick LIKE :query || '%'")
    fun searchPlayers(query: String): Flow<List<PlayerEntity>>

    @Query("UPDATE PlayerEntityTable SET " +
            "nick = :newNick, " +
            "reason = :newReason, " +
            "isGoodPerson = :newIsGoodPerson, " +
            "percentageAnger = :newPercentageAnger " +
            "WHERE id = :playerId")
    suspend fun updatePlayer(playerId: Int, newNick: String, newReason: String?, newIsGoodPerson: Boolean, newPercentageAnger: Int)

    @Delete
    fun deletePlayer(player: PlayerEntity)

    @Query("DELETE FROM PlayerEntityTable WHERE id = :playerId")
    suspend fun deletePlayerById(playerId: Int)


}