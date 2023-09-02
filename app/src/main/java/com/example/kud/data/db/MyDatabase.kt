package com.example.kud.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kud.utils.Converter
import com.example.kud.data.model.User

//@Database(entities = [User::class, CheckOut::class], version = 2, exportSchema = false)
//@TypeConverters(Converter::class)
//abstract class MyDatabase : RoomDatabase() {
//
//    abstract fun myDao(): MyDao
//    abstract fun myCheckOut():CheckOutDao
//
//}