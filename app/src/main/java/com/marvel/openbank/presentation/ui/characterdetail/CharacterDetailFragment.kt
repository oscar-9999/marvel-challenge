package com.marvel.openbank.presentation.ui.characterdetail

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import com.marvel.openbank.R
import com.marvel.openbank.databinding.FragmentDetailCharacterBinding
import com.marvel.openbank.domain.model.characters.ResultCharacterModel
import com.marvel.openbank.presentation.base.BaseFragment
import com.marvel.openbank.presentation.base.ViewModelState
import com.marvel.openbank.presentation.widget.spinner.SpinnerLoading
import com.marvel.openbank.utils.getTimeStamp
import com.marvel.openbank.utils.md5
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CharacterDetailFragment :
    BaseFragment<CharacterDetailViewModel, FragmentDetailCharacterBinding>(
        R.layout.fragment_detail_character) {

    lateinit var apikey: String
    lateinit var privateKey: String

    @Inject
    lateinit var spinnerLoading: SpinnerLoading

    companion object {
        const val KEY_CHARACTER_ID = "key_character_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        initLiveData()
        initListeners()
        initUI()
    }

    private fun initLiveData() {
        viewModel.characterDetails.observe(this.viewLifecycleOwner) {
            manageViewState(it)
        }
    }

    private fun manageViewState(viewModelState: ViewModelState<ResultCharacterModel>) {
        when (viewModelState) {
            is ViewModelState.InProgress -> showLoading()
            is ViewModelState.Loaded -> {
                hideLoading()
                fillCharactersDetails(viewModelState.value)
            }
            is ViewModelState.ApiError -> {
                hideLoading()
                showError(viewModelState.message)
            }
            is ViewModelState.DefaultError -> {
                hideLoading()
                showError(getString(R.string.default_error_message))
            }
        }
    }


    private fun initListeners() {
        binding.ivBack.setOnClickListener { navController.navigateUp() }
        binding.ivShare.setOnClickListener { share() }
    }

    private fun initUI() {
        apikey = this.getString(R.string.KEY_MARVEL_PUBLIC_KEY)
        privateKey = this.getString(R.string.KEY_MARVEL_PRIVATE_KEY)
        viewModel.getCharacterDetails(
            getTimeStamp(), apikey,
            md5(getTimeStamp(), privateKey, apikey)
        )
    }

    private fun share() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Share")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun getViewBinding() = FragmentDetailCharacterBinding.inflate(layoutInflater)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navController.navigateUp()
            return true
        }
        return false
    }

    private fun fillCharactersDetails(resultCharacterModel: ResultCharacterModel) {
        val urlImage =
            resultCharacterModel.thumbnail.path + "." + resultCharacterModel.thumbnail.extension

        Picasso.get().load(urlImage).into(binding.ivDetailCharacter)
        binding.tvTitleCharacter.text = resultCharacterModel.name

        if (resultCharacterModel.urls.urls.isNotEmpty()) {
            binding.tvDetailTitle.visibility = View.VISIBLE
            binding.tvUrlDetailLink.visibility = View.VISIBLE
            binding.tvUrlDetailLink.text = resultCharacterModel.urls.urls[0].url
            binding.tvUrlDetailLink.movementMethod = LinkMovementMethod.getInstance()
        } else {
            binding.tvDetailTitle.visibility = View.GONE
            binding.tvUrlDetailLink.visibility = View.GONE
        }
    }

    override fun showLoading() {
        spinnerLoading.show(requireContext())
    }

    override fun hideLoading() {
        spinnerLoading.dismiss()
    }
}