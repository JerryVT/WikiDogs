package com.example.wikidogs.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wikidogs.model.DogBreed
import com.example.wikidogs.model.DogDatabase
import kotlinx.coroutines.launch
import java.util.*

class DetailViewModel(application: Application): BaseViewModel(application) {

    val dogLiveData = MutableLiveData<DogBreed>()        //since creation, we need constructor, hence empty brackets

    fun fetch(uuid: Int) {

        launch {
            val dog = DogDatabase(getApplication()).dogDao().getDog(uuid)

            dogLiveData.value = dog    //assigning to mutable live data we created which holds data
            //no need of error or loading
        }


    }
}