package com.example.mproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.mproject.R
import com.example.mproject.data.response.GameListResponse
import com.example.mproject.databinding.ItemGamesBinding
import javax.inject.Inject

class GamesAdapter @Inject constructor(): RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    private lateinit var binding: ItemGamesBinding
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemGamesBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(): RecyclerView.ViewHolder(binding.root) {

        fun set(item: GameListResponse) {
            binding.apply {
                tvGameName.text = item.title
                tvDeveloper.text = item.developer
                tvPlatform.text = item.platform

                ivGameImage.load(item.thumbnail) {
                    crossfade(true)
//                    placeholder(R.drawable.)
                    scale(Scale.FILL)
                }

            }
        }

    }

//    class GamesDiffUtils(private val oldItem:List<GameListResponse>, private val newItem:List<MoviesEntity>) : DiffUtil.Callback(){
//        override fun getOldListSize(): Int {
//            return oldItem.size
//        }
//
//        override fun getNewListSize(): Int {
//            return newItem.size
//        }
//
//        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//            // === data type is compred here
//            return oldItem[oldItemPosition] === newItem[newItemPosition]
//        }
//
//        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//            return oldItem[oldItemPosition] === newItem[newItemPosition]
//        }
//
//    }

    private val differCallback = object : DiffUtil.ItemCallback<GameListResponse>() {
        override fun areItemsTheSame(oldItem: GameListResponse, newItem: GameListResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GameListResponse, newItem: GameListResponse): Boolean {
            return oldItem == newItem // You can use the default equals() method
        }
    }

     val differ = AsyncListDiffer(this, differCallback)



}