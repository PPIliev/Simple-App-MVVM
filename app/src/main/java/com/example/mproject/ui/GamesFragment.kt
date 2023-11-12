package com.example.mproject.ui

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mproject.MainActivity
import com.example.mproject.R
import com.example.mproject.adapters.GameCategoryAdapter
import com.example.mproject.adapters.GamesAdapter
import com.example.mproject.data.repository.ApiRepository
import com.example.mproject.data.response.GameCategoryResponse
import com.example.mproject.data.response.GameListResponse
import com.example.mproject.databinding.FragmentGamesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class GamesFragment : Fragment() {

    private lateinit var binding: FragmentGamesBinding

    private val apiViewModel: ApiViewModel by viewModels()

    // Field Injection
    @Inject
    lateinit var apiRepository: ApiRepository

    @Inject
    lateinit var gamesAdapter: GamesAdapter

    @Inject
    lateinit var categoriesAdapter: GameCategoryAdapter

    private lateinit var categories: List<GameCategoryResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //LiveData
        //apiViewModel.loadAllGamesList()
        apiViewModel.loadGamesList()
        categories = apiViewModel.categories

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGamesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.apply {

        categoriesAdapter.differ.submitList(categories)
        rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }

        categoriesAdapter.setonItemClickListener {
            categoriesAdapter.updateSelectedItem(categories.indexOf(it))
            if (it.name == "See All") {
                apiViewModel.loadGamesList()
            } else {
                apiViewModel.loadCategoriesList(it.name)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                apiViewModel.gameListResponse.collect { response ->
                    gamesAdapter.differ.submitList(response)
                    rvGames.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = gamesAdapter
                    }

                    gamesAdapter.setOnItemClickListener {
                        val direction = GamesFragmentDirections.actionGamesFragmentToGameDetailsFragment(it.id, it.thumbnail)
                        findNavController().navigate(direction)
                    }


                }
            }
        }


        //LiveData
//        apiViewModel.gamesList.observe(viewLifecycleOwner) {respone ->
//            gamesAdapter.differ.submitList(respone)
//            rvGames.apply {
//                layoutManager = LinearLayoutManager(requireContext())
//                adapter = gamesAdapter
//            }
//
//            gamesAdapter.setOnItemClickListener {
//                (activity as MainActivity).keepSplash = true
//                val direction = GamesFragmentDirections.actionGamesFragmentToGameDetailsFragment(it.id, it.thumbnail)
//                findNavController().navigate(direction)
//
//            }
//
//        }
    }


}




}