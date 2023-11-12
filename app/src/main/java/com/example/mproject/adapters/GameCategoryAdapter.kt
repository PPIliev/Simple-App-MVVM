package com.example.mproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mproject.R
import com.example.mproject.data.response.GameCategoryResponse
import com.example.mproject.data.response.GameListResponse
import com.example.mproject.databinding.ItemCategoryBinding
import com.example.mproject.databinding.ItemGamesBinding
import javax.inject.Inject

class GameCategoryAdapter @Inject constructor() :
    RecyclerView.Adapter<GameCategoryAdapter.ViewHolder>() {

    private lateinit var binding: ItemCategoryBinding
    private var selectedItemPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: GameCategoryResponse) {
            binding.apply {
                categoryName.text = item.name

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                    notifyDataSetChanged()
                }

                if (adapterPosition == selectedItemPosition) {
                    root.setBackgroundResource(R.drawable.bg_rounded_colored_corners)
                }

            }
        }
    }

    private var onItemClickListener: ((GameCategoryResponse) -> Unit)? = null
    fun setonItemClickListener(listener: (GameCategoryResponse) -> Unit) {
        onItemClickListener = listener
    }

    fun updateSelectedItem(position: Int) {
        val previousSelectedItem = selectedItemPosition
        selectedItemPosition = position
        notifyItemChanged(previousSelectedItem)
        notifyItemChanged(position)
    }

    private val differCallback = object : DiffUtil.ItemCallback<GameCategoryResponse>() {
        override fun areItemsTheSame(
            oldItem: GameCategoryResponse,
            newItem: GameCategoryResponse
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: GameCategoryResponse,
            newItem: GameCategoryResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

}