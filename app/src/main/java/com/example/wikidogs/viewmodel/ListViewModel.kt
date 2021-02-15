package com.example.wikidogs.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wikidogs.model.DogBreed
import com.example.wikidogs.model.DogDatabase
import com.example.wikidogs.model.DogsApiService
import com.example.wikidogs.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

// Viewmodel does not know about the view
// a strong separation between viewmodel and view which is the the basic criteria for MVVM

// class ListViewModel: ViewModel() {        // we are not passing any arguments
                                        // ViewModel does not require any default constructor..so none are passed
class ListViewModel(application: Application): BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 *60 * 1000 *1000 * 1000L  //in nanonseconds

    private val dogsService = DogsApiService()
    private val disposable = CompositeDisposable()      // allows us to retrieve or to observe the observable that the api gives
                                                        // prevents from mempory leak
    val dogs = MutableLiveData<List<DogBreed>>()            // all data from repo and backend will be coming here
    val dogsLoadError = MutableLiveData<Boolean>()      //notify the listener to this variable that there is error
    val loading = MutableLiveData<Boolean>()

    fun refresh() {     // to decide from where to take info based on time data was fetched from online

        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchfromDatabase()
        } else {
            fetchFromRemote()
        }

    }

    fun refreshBypassCache() {      //forcing data to be fectched from endpoint
        fetchFromRemote()
    }

    private fun fetchfromDatabase() {
        loading.value = true
        launch {
            val dogs : List<DogBreed> = DogDatabase(getApplication()).dogDao().getAllDogs()
            dogsRetrieved(dogs)
            Toast.makeText(getApplication(), "Dogs retrieved from database", Toast.LENGTH_SHORT).show()
        }
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

                        storeDogsLocally(dogList)

                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )

    }

    private fun dogsRetrieved(dogList: List<DogBreed>) {
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun storeDogsLocally(list: List<DogBreed>) {
            launch {
                val dao = DogDatabase(getApplication()).dogDao()
                dao.deleteAllDogs()
                val result = dao.insertAll(*list.toTypedArray())
                var i = 0
                while(i < list.size) {
                    list[i].uuid = result[i].toInt()
                    ++i
                }
                dogsRetrieved(list)
            }
        prefHelper.saveUpdatedTime(System.nanoTime())
    }

    override fun onCleared() {          //livedata method to clean up everythinmg
        super.onCleared()
        disposable.clear()
    }

}