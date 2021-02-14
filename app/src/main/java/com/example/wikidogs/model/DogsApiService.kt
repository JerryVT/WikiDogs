package com.example.wikidogs.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogsApiService {

    private val BASE_URL = "https://raw.githubusercontent.com"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())  //GSONConverterFactory is a google library that converts json to gson
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //converts list of objects (gson)into Single, a observable
        // Single is an observable which emits data once and finishes it
        .build()    //based on DogsApi
        .create(DogsApi::class.java)

    fun getDogs(): Single<List<DogBreed>> {
        return api.getDogs()
    }

}