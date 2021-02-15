package com.example.wikidogs.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.wikidogs.R
import com.example.wikidogs.util.getProgressDrawable
import com.example.wikidogs.util.loadImage
import com.example.wikidogs.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private var dogUuid = 0

    private lateinit var  viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {        // ? is applied so that this is processed only if argument exist
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid

        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)        //instantiate viewModel

        viewModel.fetch(dogUuid)


        observeViewModel()


    }

    private fun observeViewModel() {
        viewModel.dogLiveData.observe(this, Observer { dog ->
            dogName.text = dog.dogBreed
            dogPurpose.text = dog.bredFor
            dogTemperament.text = dog.temperament
            dogLifespan.text = dog.lifeSpan
            context?.let { dogImage.loadImage(dog.imageUrl, getProgressDrawable(it)) }

        })
    }


}