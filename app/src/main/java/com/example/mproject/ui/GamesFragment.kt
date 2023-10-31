package com.example.mproject.ui

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mproject.R
import com.example.mproject.adapters.GamesAdapter
import com.example.mproject.data.repository.ApiRepository
import com.example.mproject.data.response.GameListResponse
import com.example.mproject.databinding.FragmentGamesBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class GamesFragment : Fragment() {

    private lateinit var binding: FragmentGamesBinding

    @Inject
    lateinit var apiRepository: ApiRepository

    @Inject
    lateinit var gamesAdapter: GamesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGamesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.apply {
        progressBarGames.visibility = View.VISIBLE
        apiRepository.getAllGames().enqueue(object : Callback<List<GameListResponse>> {
            override fun onResponse(call: Call<List<GameListResponse>>, response: Response<List<GameListResponse>>) {
                if (response.isSuccessful) {
                    val gameList = response.body()
                    progressBarGames.visibility = View.GONE // Hide the progress bar
                    gamesAdapter.differ.submitList(gameList)
                    rvGames.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = gamesAdapter
                    }

                    gamesAdapter.setOnItemClickListener {
                        val direction = GamesFragmentDirections.actionGamesFragmentToGameDetailsFragment(it.id, it.thumbnail)
                        findNavController().navigate(direction)
                    }

                } else {
                    // Handle non-200 responses here
                    when (response.code()) {
                        404 -> {
                            Toast.makeText(requireContext(), "Object not found: Game or endpoint not found", Toast.LENGTH_SHORT).show()
                        }
                        500 -> {
                            Toast.makeText(requireContext(), "Something wrong on our end (unexpected server errors)", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<GameListResponse>>, t: Throwable) {
                // Handle network failure here
                t.printStackTrace() // Log the error for debugging
                Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show()
                progressBarGames.visibility = View.GONE
            }
        })
    }
}




}