package com.stalcraft.blackliststalcraft.domain.models.remote.models

data class PlayerModel(
    val id: String="",
    val nick: String="",
    val author: String="",
    val reason: String? = null,
    val date: String="",
    val isGoodPerson: Boolean = true,
    val percentageAnger: Int = 1,
)
