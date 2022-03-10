package com.example.listgames.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.listgames.GameViewModel
import com.example.listgames.MainActivity.Companion.gameID
import com.example.listgames.R
import com.example.listgames.databinding.FragmentDetailBinding
import com.example.listgames.model.dto.GameItemDTO
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private var gameId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding: FragmentDetailBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.fragment_detail, container, false
            )

        viewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(GameViewModel::class.java)
        gameId = gameID


        GlobalScope.launch(Dispatchers.Main) {
            val game = async(Dispatchers.IO) {
                viewModel.getGameById(gameId)
            }
            setViews(game, binding)
        }

        return binding.root
    }


    private suspend fun setViews(game: Deferred<GameItemDTO>, binding: FragmentDetailBinding) {

        while (!game.isCompleted) {
            binding.progressBarLoading.visibility = View.VISIBLE
        }
        val gameItem = game.await()
        binding.progressBarLoading.visibility = View.GONE

        if(gameItem != null){

            if(gameItem.screenshots != null){
                for (i in 0..gameItem.screenshots.size - 1){
                    if (gameItem.screenshots[i].image_id.isNotEmpty()){
                        Picasso.get().load(
                            "https:///images.igdb.com/igdb/image/upload/t_thumb/" +
                                    gameItem.screenshots.get(0).image_id + ".jpg"
                        ).into(binding.imageViewBigCover)
                        break
                    }

                }
            }
            binding.textViewTitle.text = gameItem.name
            binding.textViewSummary.text = gameItem.summary
        }

    }
}