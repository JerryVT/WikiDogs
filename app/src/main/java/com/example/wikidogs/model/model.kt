package com.example.wikidogs.model

data class DogBreed(
    val breedId: String?,           //? is added to make that it can be empty
    val dogBreed: String?,
    val lifeSpan: String?,             // we are using String since we get data from backend
    val breedGroup: String?,
    val bredFor: String?,
    val temperament: String?,
    val imageUrl: String?
)