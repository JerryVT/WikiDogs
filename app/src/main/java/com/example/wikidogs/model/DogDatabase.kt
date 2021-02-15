package com.example.wikidogs.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(DogBreed::class), version = 1)  //adding entity
abstract class DogDatabase: RoomDatabase() {        //this class extends from RoomDatabse
    //Singleton pattern - so that only one instance will be running
    // helps to prevent from same data at the same by mutiple instances
    // singleton creates only one object and not more than that

    //as this class helps to access db and if multiple threads at background accessing db can cause issues
    // This is achieved by considering the class as singleton
    // consistency is achieved

    abstract fun dogDao() : DogDao      //returns our interface

    companion object {
        @Volatile private var instance: DogDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {   //create onstance
                instance = it       //this instance is passed to the invoker
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                                                //in case when instance is called and db is not created
            context.applicationContext,      //passing application context as this context is volatile
                                            //for ex: when phone is rotated

            DogDatabase::class.java,
            "dogdatabase"
        ).build()

    }
}