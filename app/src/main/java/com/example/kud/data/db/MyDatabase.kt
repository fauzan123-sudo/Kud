package com.example.kud.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kud.data.model.Converter
import com.example.kud.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class MyDatabase : RoomDatabase() {

    abstract fun myDao(): MyDao

//    companion object {
//        @Volatile
//        private var INSTANCE: MyDatabase? = null
//
//        fun getDatabase(context: Context): MyDatabase {
//            val tempInstance = INSTANCE
//            if (tempInstance != null) {
//                return tempInstance
//            }
//            synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    MyDatabase::class.java,
//                    "my_database"
//                ).fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE = instance
//                return instance
//            }
//        }
//
//    }
}