package com.astashin.marvelcomics.characters.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.astashin.marvelcomics.R
import com.astashin.marvelcomics.databinding.ItemCharackterBinding
import com.astashin.marvelcomics.model.Character

class CharactersAdapter(private val charactersList: List<Character>) : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemCharackterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_charackter, parent, false)
        return CharacterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return charactersList.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(charactersList[position])
    }

    class CharacterViewHolder(private val binding: ItemCharackterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            binding.character = character
        }
    }
}