package com.example.wikidogs.model

import io.reactivex.Single
import retrofit2.http.GET

interface DogsApi {             //provide us available methods to acess api

    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs(): Single<List<DogBreed>>
}