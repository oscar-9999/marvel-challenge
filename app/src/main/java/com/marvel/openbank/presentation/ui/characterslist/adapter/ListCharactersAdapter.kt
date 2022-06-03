package com.marvel.openbank.presentation.ui.characterslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marvel.openbank.R
import com.marvel.openbank.databinding.ItemCharacterBinding
import com.marvel.openbank.domain.model.characters.ResultCharacterModel

class ListCharactersAdapter(private val listener: ClickListener) :
    RecyclerView.Adapter<ListCharactersViewHolder>() {

    private var characters: MutableList<ResultCharacterModel> = ArrayList()

    fun setCharacters(characters: List<ResultCharacterModel>) {
        this.characters = characters.toMutableList()
    }

    fun addMoreCharacters(characters: List<ResultCharacterModel>) {
        this.characters.addAll(characters)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCharactersViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        return ListCharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListCharactersViewHolder, position: Int) {
        val character: ResultCharacterModel = characters[position]
        holder.setCharacter(character)
        holder.binding.root.setOnClickListener { listener.rowClick(character) }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    interface ClickListener {
        fun rowClick(character: ResultCharacterModel)
    }
}
