package com.marvel.inditex.presentation.ui.characterslist

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.marvel.inditex.domain.RequestFailure
import com.marvel.inditex.domain.interactors.GetListCharactersInteractor
import com.marvel.inditex.domain.model.characters.ListCharactersModel
import com.marvel.inditex.presentation.base.BaseViewModel
import com.marvel.inditex.presentation.base.ViewModelState
import javax.inject.Inject

class CharactersListViewModel @Inject constructor(private val getListCharactersInteractor: GetListCharactersInteractor) :
    BaseViewModel() {

    var limit = 10

    val listCharacters = MutableLiveData<ViewModelState<ListCharactersModel>>()

    fun getListCharacters(limit: Int, ts: String, apikey: String, hash: String) {
        this.limit=limit
        listCharacters.postValue(ViewModelState.InProgress)

        getListCharactersInteractor.setParams(limit, ts, apikey, hash)
        async(getListCharactersInteractor.execute()) {
            listCharacters.postValue(ViewModelState.Loaded(it))
        }
    }

    override fun init(arguments: Bundle) = Unit

    override fun errorManager(requestFailure: RequestFailure) {
        listCharacters.postValue(ViewModelState.ApiError(requestFailure.message))
    }
}