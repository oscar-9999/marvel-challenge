package com.marvel.openbank.presentation.ui.characterslist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.marvel.openbank.databinding.ItemCharacterBinding
import com.marvel.openbank.domain.model.characters.ResultCharacterModel
import com.squareup.picasso.Picasso

class ListCharactersViewHolder(val binding: ItemCharacterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun setCharacter(resultCharacterModel: ResultCharacterModel) {
        val urlImage =
            resultCharacterModel.thumbnail.path + "." + resultCharacterModel.thumbnail.extension
        Picasso.get().load(urlImage).into(binding.ivCharacter)
        binding.tvTitleCharacter.text = resultCharacterModel.name
    }
}