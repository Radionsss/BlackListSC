package com.stalcraft.blackliststalcraft.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stalcraft.blackliststalcraft.data.local.dao.PlayerDao
import com.stalcraft.blackliststalcraft.domain.models.local.entities.PlayerEntity
import com.stalcraft.blackliststalcraft.data.local.typeConverters.DateConverter
import com.stalcraft.blackliststalcraft.domain.models.local.entities.UserEntity


@Database(entities = [PlayerEntity::class,UserEntity::class,], version = 1)
@TypeConverters(DateConverter::class)
abstract class MainDb:RoomDatabase(){
    abstract fun getPlayerDao():PlayerDao
}