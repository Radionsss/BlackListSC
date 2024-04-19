package com.stalcraft.blackliststalcraft.core.utils

import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.domain.models.local.entities.UserEntity
import com.stalcraft.blackliststalcraft.domain.models.remote.models.PlayerModel
import com.stalcraft.blackliststalcraft.domain.models.remote.models.UserModel

fun PlayerEntity.toPlayerModel(): PlayerModel {
    return PlayerModel(
        id = this.id,
        nick = this.nick,
        reason = this.reason,
        date = this.date,
        isGoodPerson = this.isGoodPerson,
        percentageAnger = this.percentageAnger,
    )
}
fun PlayerModel.toPlayerEntity(): PlayerEntity {
    return PlayerEntity(
        id = this.id,
        nick = this.nick,
        reason = this.reason,
        date = this.date,
        author = this.author,
        isGoodPerson = this.isGoodPerson,
        percentageAnger = this.percentageAnger,
    )
}
fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.name
    )
}

