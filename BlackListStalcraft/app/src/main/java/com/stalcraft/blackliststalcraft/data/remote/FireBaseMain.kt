package com.stalcraft.blackliststalcraft.data.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.stalcraft.blackliststalcraft.domain.models.remote.models.PlayerModel
import com.stalcraft.blackliststalcraft.domain.models.remote.models.UserModel
import kotlinx.coroutines.tasks.await

class FireBaseMain {
    private val auth = FirebaseAuth.getInstance()

    suspend fun addUser(name: String) {
        val currentUser = auth.currentUser
        val dbRef = FirebaseDatabase.getInstance().getReference("users/${currentUser?.uid}")
        val snapshot = dbRef.get().await()

        if (!snapshot.exists()) {
            val userId = dbRef.push().key
            val teacherModelTemp = UserModel(
                id = userId!!,
                name = name,
                players = emptyList()
            )
            dbRef.setValue(teacherModelTemp).await()
        }
    }

    suspend fun getUser(): UserModel? {
        val currentUser = auth.currentUser
        val dbRef = FirebaseDatabase.getInstance().getReference("users/${currentUser?.uid}")
        val snapshot = dbRef.get().await()

        return if (snapshot.exists()) {
            val userMap = snapshot.getValue<HashMap<String, Any>>() // Получаем данные как HashMap
            val id = userMap?.get("id").toString()
            val name = userMap?.get("name").toString()
            val players = userMap?.get("players") as? List<PlayerModel> ?: emptyList() // Преобразуем players в список или пустой список, если он не является списком
            UserModel(id, name, players)
        } else {
            null
        }
    }
    suspend fun updatePlayer(player: PlayerModel) {
        val currentUser = auth.currentUser
        val dbRef = FirebaseDatabase.getInstance().getReference("users/${currentUser?.uid}")
        val playerRef = dbRef.child("players/${player.id}")
        playerRef.setValue(player).await()
    }
    suspend fun getAllPlayersFromAllUsers(): List<PlayerModel> {
         val dbRef = FirebaseDatabase.getInstance().getReference("users")
        val snapshot = dbRef.get().await()

        val allPlayers = mutableListOf<PlayerModel>()

        for (userSnapshot in snapshot.children) {
            val playersRef = userSnapshot.child("players")
            for (playerSnapshot in playersRef.children) {
                val player = playerSnapshot.getValue(PlayerModel::class.java)
                player?.let {
                    allPlayers.add(it)
                }
            }
        }

        return allPlayers.toList()
    }

    suspend fun getAllPlayers(): List<PlayerModel> {

        val currentUser = auth.currentUser
        val dbRef = FirebaseDatabase.getInstance().getReference("users/${currentUser?.uid}")
        val playersRef = dbRef.child("players")
        val snapshot = playersRef.get().await()

        val playersList = mutableListOf<PlayerModel>()
        for (playerSnapshot in snapshot.children) {
            val player = playerSnapshot.getValue(PlayerModel::class.java)
            player?.let {
                playersList.add(it)
            }
        }
        return playersList.toList()
    }

    suspend fun addPlayerToUser(player: PlayerModel) {

        val currentUser = auth.currentUser
        val dbRef = FirebaseDatabase.getInstance().getReference("users/${currentUser?.uid}")
        val newPlayerRef = dbRef.child("players/${player.id}")
        val playerTemp = PlayerModel(
            id = player.id,
            nick = player.nick,
            reason = player.reason,
            author = getUser()!!.name,
            date = player.date,
            isGoodPerson = player.isGoodPerson,
            percentageAnger = player.percentageAnger,
        )
        newPlayerRef.setValue(playerTemp).await()
    }

    suspend fun changeGoodPersonPlayer(
        playerId: String,
        isGoodPerson: Boolean,
        percentageAnger: Int
    ) {
        val currentUser = auth.currentUser
        val dbRef = FirebaseDatabase.getInstance().getReference("users/${currentUser?.uid}")
        val playerRef = dbRef.child("players/$playerId")

        val updates = mapOf(
            "goodPerson" to isGoodPerson,
            "percentageAnger" to percentageAnger
        )
        playerRef.updateChildren(updates).await()
    }

    fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }
}