package com.marvel.openbank.presentation.ui.characterdetail

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.marvel.openbank.domain.RequestFailure
import com.marvel.openbank.domain.interactors.GetCharacterDetailsInteractor
import com.marvel.openbank.domain.model.characters.ListCharactersModel
import com.marvel.openbank.domain.model.characters.ResultCharacterModel
import com.marvel.openbank.presentation.base.BaseViewModel
import com.marvel.openbank.presentation.base.ViewModelState
import javax.inject.Inject

class CharacterDetailViewModel @Inject constructor(private val getCharacterDetailsInteractor: GetCharacterDetailsInteractor) :
    BaseViewModel() {

    val characterDetails = MutableLiveData<ViewModelState<ResultCharacterModel>>()

    var characterId: Int = -1

    override fun init(arguments: Bundle) {
        if (!arguments.isEmpty) {
            characterId = arguments.getSerializable(CharacterDetailFragment.KEY_CHARACTER_ID) as Int
        }
    }

    fun getCharacterDetails(ts: String, apikey: String, hash: String) {
        characterDetails.postValue(ViewModelState.InProgress)
        getCharacterDetailsInteractor.setParams(characterId, ts, apikey, hash)
        async(getCharacterDetailsInteractor.execute()) {
            successGetCharacterDetails(it)
        }
    }

    private fun successGetCharacterDetails(listCharacters: ListCharactersModel) {
        if (listCharacters.results.isNotEmpty()) {
            characterDetails.postValue(ViewModelState.Loaded(listCharacters.results[0]))
        } else {
            characterDetails.postValue(ViewModelState.DefaultError)
        }
    }

    override fun errorManager(requestFailure: RequestFailure) {
        characterDetails.postValue(ViewModelState.ApiError(requestFailure.message))
    }
}