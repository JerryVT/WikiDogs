package com.example.wikidogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wikidogs.model.DogBreed

// Viewmodel does not know about the view
// a strong separation between viewmodel and view which is the the basic criteria for MVVM

class ListViewModel: ViewModel() {        // we are not passing any arguments
                                        // ViewModel does not require any default constructor..so none are passed

    val dogs = MutableLiveData<List<DogBreed>>()            // all data from repo and backend will be coming here
    val dogsLoadError = MutableLiveData<Boolean>()      //notify the listener to this variable that there is error
    val loading = MutableLiveData<Boolean>()

    fun refresh() {     // to refresh the information

        val dog1 = DogBreed("1", "Corgi", "15 years", "breedGroup", "bredFor", "temparement", "")
        val dog2 = DogBreed("2", "Labrador", "10 years", "breedGroup", "bredFor", "temparement", "")
        val dog3 = DogBreed("3", "Rotwailer", "20 years", "breedGroup", "bredFor", "temparement", "")

        val dogList: ArrayList<DogBreed> = arrayListOf<DogBreed>(dog1, dog2, dog3)  //creating the list of dogs

        dogs.value = dogList        //passing the data to mutable live data variable we have created
        dogsLoadError.value = false // at the moment no error
        loading.value = false   //
    }
}