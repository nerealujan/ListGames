package com.example.listgames.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listgames.GameViewModel
import com.example.listgames.MainActivity.Companion.gameID
import com.example.listgames.R
import com.example.listgames.adapter.GamesAdapter
import com.example.listgames.databinding.FragmentMainBinding
import com.example.listgames.model.dto.GameItemDTO

class MainFragment : Fragment(), GamesAdapter.OnCoverClickListener {

    private lateinit var viewModel: GameViewModel

    private lateinit var recyclerViewCover: RecyclerView
    private lateinit var gamesAdapter: GamesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.fragment_main, container, false
            )
        recyclerViewCover = binding.recyclerViewCover

        val activity = requireActivity()
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(activity.application)
            .create(GameViewModel::class.java)

        recyclerViewCover.layoutManager = GridLayoutManager(context, 1)
        gamesAdapter = GamesAdapter(this, viewModel.getGameItem())
        binding.recyclerViewCover.adapter = gamesAdapter
        recyclerViewCover.itemAnimator?.changeDuration = 0


        viewModel.isDataLoading.observe(viewLifecycleOwner, {
            binding.progressBarLoad.visibility = if (it != null && it) View.VISIBLE else View.GONE
        })

        binding.progressBarLoad.visibility = View.GONE

        return binding.root
    }

    override fun onCoverClick(position: Int, gameDTO: GameItemDTO) {
        gameID = gameDTO.id
        view?.findNavController()?.navigate(R.id.action_topFragment_to_detailFragment)
    }

}