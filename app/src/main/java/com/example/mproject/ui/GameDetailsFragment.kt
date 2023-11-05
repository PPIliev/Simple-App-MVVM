package com.example.mproject.ui

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.size.Scale
import com.example.mproject.MainActivity
import com.example.mproject.R
import com.example.mproject.data.repository.ApiRepository
import com.example.mproject.data.response.GameDetailsResponse
import com.example.mproject.databinding.FragmentGameDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
@AndroidEntryPoint
class GameDetailsFragment : Fragment() {

    private lateinit var binding: FragmentGameDetailsBinding
    private val detailsViewModel: DetailsViewModel by viewModels()
    private val args: GameDetailsFragmentArgs by navArgs()

    private var gameId = 0

    @Inject
    lateinit var apiRepository: ApiRepository




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameId = args.gameid
        if (gameId > 0) {
            detailsViewModel.loadGameDetails(gameId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            (activity as MainActivity).keepSplash = true

            detailsViewModel.gamesDetailsList.observe(viewLifecycleOwner) { response ->
                tvGameTitle.text = response.title
                tvDescription.text = response.description
                tvMinSysReqOS.text = response.minimum_system_requirements.os
                tvMinSysReqProcessor.text = response.minimum_system_requirements.processor
                tvMinSysReqMemory.text = response.minimum_system_requirements.memory
                tvMinSysReqGraphic.text = response.minimum_system_requirements.graphics
                tvMinSysReqStorage.text = response.minimum_system_requirements.storage


                ivGameImage.load(args.gameThumbnail) {
                    crossfade(true)
//                    placeholder(R.drawable.)
                    scale(Scale.FILL)
                }

                detailsViewModel.loading.observe(viewLifecycleOwner) {
                    if (it) {
                        progressBarGameDetails.visibility = View.VISIBLE
                        cardView.visibility = View.INVISIBLE
                        ivGameImage.visibility = View.INVISIBLE
                        tvGameTitle.visibility = View.INVISIBLE
                    } else {
                        progressBarGameDetails.visibility = View.INVISIBLE
                        cardView.visibility = View.VISIBLE
                        ivGameImage.visibility = View.VISIBLE
                        tvGameTitle.visibility = View.VISIBLE
                    }
                }


            }
        }

    }




}