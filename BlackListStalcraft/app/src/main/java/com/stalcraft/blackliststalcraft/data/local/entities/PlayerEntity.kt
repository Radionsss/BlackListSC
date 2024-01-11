package com.stalcraft.blackliststalcraft.data.local.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("PlayerEntityTable")
@Parcelize
data class PlayerEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val nick:String,
    val reason:String?=null,
    val date:String,
    val isGoodPerson:Boolean=true,
    val percentageAnger:Int=1,
    var isSelected:Boolean=false,
    var isNeedShowCheckBox:Boolean=false,
): Parcelable