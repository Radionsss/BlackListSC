package com.stalcraft.blackliststalcraft.domain.models.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("UserEntityTable")
@Parcelize
data class UserEntity(
    @PrimaryKey
    val id:String,
    val name:String
): Parcelable
