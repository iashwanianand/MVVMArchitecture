package com.example.mvvmarchitecture.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmarchitecture.model.Person

@Database(entities = [Person::class], version = 1)
abstract class PersonDatabase : RoomDatabase() {
    abstract val dao : PersonDao

    companion object {

        private val INSTANCE: PersonDatabase? = null

        fun getInstance(context: Context): PersonDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    PersonDatabase::class.java,
                    "person_db"
                ).build()
            }
            return instance
        }
    }
}