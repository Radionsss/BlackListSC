package com.stalcraft.blackliststalcraft.domain.models.remote.models

data class UserModel(
    val id:String="",
    val name:String="",
    val players:List<PlayerModel>?=null,
)
