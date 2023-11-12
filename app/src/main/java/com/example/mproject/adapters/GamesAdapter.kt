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
import kotlinx.coroutines.delay
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

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.set(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder(): RecyclerView.ViewHolder(binding.root) {

        fun set(item: GameListResponse) {
            binding.apply {
                tvGameName.text = item.title
                tvDeveloper.text = item.developer
                tvCategory.text = item.genre

                ivGameImage.load(item.thumbnail) {
                    crossfade(true)
//                    placeholder(R.drawable.)
                    scale(Scale.FILL)
                }

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }

            }
        }

    }


    private var onItemClickListener: ((GameListResponse)-> Unit)? = null

    fun setOnItemClickListener (listener: ((GameListResponse) -> Unit)) {
        onItemClickListener = listener
    }



    private val differCallback = object : DiffUtil.ItemCallback<GameListResponse>() {
        override fun areItemsTheSame(oldItem: GameListResponse, newItem: GameListResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GameListResponse, newItem: GameListResponse): Boolean {
            return oldItem == newItem
        }
    }

     val differ = AsyncListDiffer(this, differCallback)



}