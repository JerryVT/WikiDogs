package com.example.wikidogs.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDao {          //interface through which we will access objects from db
                            // it also contian the service to connect to db

    @Insert
    suspend fun insertAll(vararg dogs: DogBreed) : List<Long>
        //varargs - variable arguments means a lot of objects is passwed
        //we will get the primary keys, ie, uuids
        // suspend - need to be done from a separate thread
        // as we cannot run these ooperations in klocal main thread as it can lead to make it wait
        // which leads to app to crash
        // suspend is used to denote that

    @Query("SELECT * FROM dogbreed")
    suspend fun getAllDogs(): List<DogBreed>

    @Query("SELECT * FROM dogbreed WHERE uuid = :dogId")
    suspend fun getDog(dogId: Int): DogBreed

    @Query("DELETE FROM dogbreed")
    suspend fun deleteAllDogs()


}