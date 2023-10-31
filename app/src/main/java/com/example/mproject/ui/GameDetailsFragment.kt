package com.example.mproject.ui

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.size.Scale
import com.example.mproject.R
import com.example.mproject.data.repository.ApiRepository
import com.example.mproject.data.response.GameDetailsResponse
import com.example.mproject.databinding.FragmentGameDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
@AndroidEntryPoint
class GameDetailsFragment : Fragment() {

    private lateinit var binding: FragmentGameDetailsBinding

    private val args: GameDetailsFragmentArgs by navArgs()


    @Inject
    lateinit var apiRepository: ApiRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.gameid
        val gameThumbnail = args.gameThumbnail

        binding.apply {
            progressBarGameDetails.visibility = View.VISIBLE
            apiRepository.getGameDetails(id).enqueue(object: Callback<GameDetailsResponse> {
                override fun onResponse(call: Call<GameDetailsResponse>, response: Response<GameDetailsResponse>) {
                    if (response.isSuccessful) {
                        progressBarGameDetails.visibility = View.INVISIBLE
                        response.body().let {
                            tvDescription.text = it!!.description
                            tvMinSysReqOS.text = it.minimum_system_requirements.os
                            tvMinSysReqProcessor.text = it.minimum_system_requirements.processor
                            tvMinSysReqMemory.text = it.minimum_system_requirements.memory
                            tvMinSysReqGraphic.text = it.minimum_system_requirements.graphics
                            tvMinSysReqStorage.text = it.minimum_system_requirements.storage



                            ivGameImage.load(gameThumbnail) {
                                crossfade(true)
//                    placeholder(R.drawable.)
                                scale(Scale.FILL)
                            }

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

                override fun onFailure(call: Call<GameDetailsResponse>, t: Throwable) {
                    t.printStackTrace() // Log the error for debugging
                    Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show()
                    progressBarGameDetails.visibility = View.GONE                }

            })
        }

    }


}