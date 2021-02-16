package com.example.wikidogs.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wikidogs.R
import com.example.wikidogs.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val dogsListAdapter = DogsListAdapter(arrayListOf())  //adapter takes an empty array and later we pass array

    override fun onCreateView(                          //onCreateView is a lifecycle class


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)  // allows system to create menu
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)

            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)  //instantiate view model
                //now instance of view model i available in the fragment
        viewModel.refresh()

        dogsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogsListAdapter           //attaching adapter
        }

        refreshLayout.setOnRefreshListener {
            dogsList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refreshBypassCache()
            refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    fun observeViewModel() {  //use the variables

        viewModel.dogs.observe(this, Observer { dogs ->
            dogs?.let {
                dogsList.visibility = View.VISIBLE
                dogsListAdapter.updateDogList(dogs)
            }
        })  // observe the livedata

        viewModel.dogsLoadError.observe(this, Observer { isError ->
            isError?.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->  //true ->Widget Spinner is visible..else not..so  both values have significance
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE        //it means isloading
                if (it) {           //when loading, we dont want to display error message and list
                    listError.visibility = View.GONE
                    dogsList.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {      // for mennu option to bring settings screen
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)  //inflate layout and pass it to menu variable

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {       //overriding the function used to do operation when menu item is clicked

       when(item.itemId) {
           R.id.actionSettings -> {
              view?.let { Navigation.findNavController(it).navigate(ListFragmentDirections.actionSettings()) }
           }
       }

        return super.onOptionsItemSelected(item)

    }
}