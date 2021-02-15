package com.example.wikidogs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.wikidogs.R
import com.example.wikidogs.databinding.ItemDogBinding
import com.example.wikidogs.model.DogBreed
import com.example.wikidogs.util.getProgressDrawable
import com.example.wikidogs.util.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*

//ListAdapter provides an interface between model and view
                         // This is constructor
class DogsListAdapter(val dogsList: ArrayList<DogBreed>): RecyclerView.Adapter<DogsListAdapter.DogViewHolder>(), DogClickListener {

    fun updateDogList(newDogsList: List<DogBreed>) {            //whenever there is update on data
        dogsList.clear()
        dogsList.addAll(newDogsList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
       // val view = inflater.inflate(R.layout.item_dog, parent, false) // we dont require this view instaantiation

        val view = DataBindingUtil.inflate<ItemDogBinding>(inflater, R.layout.item_dog, parent, false)
        return DogViewHolder(view)      //binding view to ViewHolder

    }

    override fun getItemCount() = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {

        /*holder.view.name.text = dogsList[position].dogBreed     //uses findViewbyId runnin in background..resource consuming
        holder.view.lifespan.text = dogsList[position].lifeSpan
        holder.view.setOnClickListener {
           val action =  ListFragmentDirections.actionDetailFragment()
            action.dogUuid = dogsList[position].uuid
            Navigation.findNavController(it).navigate(action)*/

        holder.view.dog = dogsList[position]
        holder.view.listener = this         //

        }

        //holder.view.imageView.loadImage(dogsList[position].imageUrl, getProgressDrawable(holder.view.imageView.context))



    override fun onDogClicked(v: View) {

        val uuid : Int = v.dogId.toString().toInt()
        val action =  ListFragmentDirections.actionDetailFragment()
        action.dogUuid = uuid
        Navigation.findNavController(v).navigate(action)

    }

    class DogViewHolder(var view: ItemDogBinding) : RecyclerView.ViewHolder(view.root) {    // this is the view holder to be used in ListAdapter

        //ItemDogBinding is automatically created when we have declared tag data in layout
        ///need real view hoder..hence view.root
    }

}

