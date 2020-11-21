package com.farzonestudios.spanningdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.farzonestudios.spanningdemo.recycler.CustomAdapter

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var adapter: CustomAdapter? = null
    private var viewModel: MainViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, getViewModelFactory()).get(MainViewModel::class.java)
        adapter = CustomAdapter()

        setupRecyclerViewAndSubscriptions()
    }

    private fun setupRecyclerViewAndSubscriptions() {
        view?.findViewById<RecyclerView>(R.id.recyclerViewMain)?.let {
            it.adapter = adapter
        }

        viewModel?.data?.observe(viewLifecycleOwner) {
            adapter?.updateData(it)
        }
    }

    private fun getViewModelFactory() =
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        viewModel = null
    }
}