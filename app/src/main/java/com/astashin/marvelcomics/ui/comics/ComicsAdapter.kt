package com.astashin.marvelcomics.ui.comics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.astashin.marvelcomics.R
import com.astashin.marvelcomics.databinding.ItemComicBinding
import com.astashin.marvelcomics.model.Comic

class ComicsAdapter(private val comicsList: List<Comic>) : RecyclerView.Adapter<ComicsAdapter.ComicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val binding: ItemComicBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_comic, parent, false)
        return ComicViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return comicsList.size
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bind(comicsList[position])
    }

    class ComicViewHolder(private val binding: ItemComicBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comic: Comic) {
            binding.comic = comic
        }
    }
}