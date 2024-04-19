package com.stalcraft.blackliststalcraft.domain.models.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("BillingEntityTable")
data class BillingEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    val googlwBillingProvider:Boolean,
    val ruBillingProvider:Boolean,
    val phoneNumber:String?=""
)