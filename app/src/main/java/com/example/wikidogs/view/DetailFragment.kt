package com.example.wikidogs.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.wikidogs.R
import com.example.wikidogs.databinding.FragmentDetailBinding
import com.example.wikidogs.model.DogPalette
import com.example.wikidogs.util.getProgressDrawable
import com.example.wikidogs.util.loadImage
import com.example.wikidogs.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private var dogUuid = 0

    private  lateinit var dataBinding: FragmentDetailBinding
    private lateinit var  viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root

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
         /*   dogPurpose.text = dog.bredFor
            dogTemperament.text = dog.temperament
            dogLifespan.text = dog.lifeSpan
            context?.let { dogImage.loadImage(dog.imageUrl, getProgressDrawable(it)) }*/

            dataBinding.dog = dog

            dog?.let {
                it.imageUrl?.let {
                    setupBackgroundColor(it)
                }
            }
        })
    }

    private fun setupBackgroundColor(url: String) {
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                        
                    }

                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        Palette.from(resource)
                                .generate { palette ->
                                    val intColor = palette?.vibrantSwatch?.rgb ?: 0
                                    val myPalette = DogPalette(intColor)
                                    dataBinding.palette = myPalette
                                }
                    }
                })
    }

}