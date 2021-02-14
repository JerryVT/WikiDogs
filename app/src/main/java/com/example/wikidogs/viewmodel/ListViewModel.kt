package com.example.wikidogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wikidogs.model.DogBreed
import com.example.wikidogs.model.DogsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

// Viewmodel does not know about the view
// a strong separation between viewmodel and view which is the the basic criteria for MVVM

class ListViewModel: ViewModel() {        // we are not passing any arguments
                                        // ViewModel does not require any default constructor..so none are passed

    private val dogsService = DogsApiService()
    private val disposable = CompositeDisposable()      // allows us to retrieve or to observe the observable that the api gives
                                                        // prevents from mempory leak
    val dogs = MutableLiveData<List<DogBreed>>()            // all data from repo and backend will be coming here
    val dogsLoadError = MutableLiveData<Boolean>()      //notify the listener to this variable that there is error
    val loading = MutableLiveData<Boolean>()

    fun refresh() {     // to decide from where to take info


    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.addAll(
            dogsService.getDogs()       // we want data to be fetched in background as if it runs in foreground, it can take time and make the app to wait
                                        // whiuch leads the app to crash
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<DogBreed>>(){
                    override fun onSuccess(dogList: List<DogBreed>) {
                        dogs.value = dogList
                        dogsLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    override fun onCleared() {          //livedata method to clean up everythinmg
        super.onCleared()
        disposable.clear()
    }

}