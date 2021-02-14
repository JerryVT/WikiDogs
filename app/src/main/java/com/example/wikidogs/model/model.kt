package com.example.wikidogs.model

import com.google.gson.annotations.SerializedName

data class DogBreed(

    @SerializedName("id")      // used to define from which id part of json object, data has to be taken and assigned
    val breedId: String?,           //? is added to make that it can be empty

    @SerializedName("name")  //if id name in json is same as that of variable name here, no need for @serializedname
    val dogBreed: String?,

    @SerializedName("life_span")
    val lifeSpan: String?,             // we are using String since we get data from backend

    @SerializedName("breed_group")
    val breedGroup: String?,

    @SerializedName("bred_for")
    val bredFor: String?,

    @SerializedName("temperament")
    val temperament: String?,

    @SerializedName("url")
    val imageUrl: String?
)