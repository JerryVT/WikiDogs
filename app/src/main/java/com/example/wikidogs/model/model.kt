package com.example.wikidogs.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity         //room library considers this class as an entity for a db

data class DogBreed(    //default class name
                        // for data class, we are giving variables as constructors

    @ColumnInfo(name = "breed_id")      //used to name the column in database
    @SerializedName("id")      // used to define from which id part of json object, data has to be taken and assigned
    val breedId: String?,           //? is added to make that it can be empty

    @ColumnInfo(name = "dog_name")
    @SerializedName("name")  //if id name in json is same as that of variable name here, no need for @serializedname
    val dogBreed: String?,

    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    val lifeSpan: String?,             // we are using String since we get data from backend

    @ColumnInfo(name ="breed_group")
    @SerializedName("breed_group")
    val breedGroup: String?,

    @ColumnInfo(name="bred_for")
    @SerializedName("bred_for")
    val bredFor: String?,

    @SerializedName("temperament")
    val temperament: String?,

    @ColumnInfo(name = "dog_url")
    @SerializedName("url")
    val imageUrl: String?
) {         //defining the primary class in body

    @PrimaryKey(autoGenerate = true) //Room library generates primary key here.
                                    // Hence key is generated only at time data needs to be stored
    var uuid: Int = 0

}