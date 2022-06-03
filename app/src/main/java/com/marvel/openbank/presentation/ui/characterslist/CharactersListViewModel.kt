package com.marvel.openbank.presentation.ui.characterslist

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.marvel.openbank.domain.RequestFailure
import com.marvel.openbank.domain.interactors.GetListCharactersInteractor
import com.marvel.openbank.domain.model.characters.ListCharactersModel
import com.marvel.openbank.presentation.base.BaseViewModel
import com.marvel.openbank.presentation.base.ViewModelState
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