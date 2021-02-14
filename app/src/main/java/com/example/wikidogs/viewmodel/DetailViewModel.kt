package com.example.wikidogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wikidogs.model.DogBreed

class DetailViewModel: ViewModel() {

    val dogLiveData = MutableLiveData<DogBreed>()        //since creation, we need constructor, hence empty brackets

    fun fetch() {
        val dog = DogBreed("1", "Corgi", "15 years", "breedGroup", "bredFor", "temparement", "")
        dogLiveData.value = dog     //assigning to mutable live data we created which holds data
        //no need of error or loading
    }
}