package com.raywenderlich.listmaker.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.listmaker.R
import com.raywenderlich.listmaker.TaskList
import com.raywenderlich.listmaker.databinding.FragmentMainBinding


class MainFragment(val clickListener: MainFragmentInteractionListener) : Fragment(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {
    interface MainFragmentInteractionListener {
        fun listItemTapped(list: TaskList)
    }

    private lateinit var binding: FragmentMainBinding

    companion object {
        fun newInstance(clickListener: MainFragmentInteractionListener) = MainFragment(clickListener)
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.listsRecview.layoutManager = LinearLayoutManager(requireContext())

        //binding.listsRecview.adapter = ListSelectionRecyclerViewAdapter()
        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(requireActivity()))).get(MainViewModel::class.java)

        val recyclerViewAdapter = ListSelectionRecyclerViewAdapter(viewModel.lists, this)
        binding.listsRecview.adapter = recyclerViewAdapter
        viewModel.onListAdded = {
            recyclerViewAdapter.listsUpdated()
        }
    }

    override fun listItemClicked(list: TaskList) {
        clickListener.listItemTapped(list)
    }

}