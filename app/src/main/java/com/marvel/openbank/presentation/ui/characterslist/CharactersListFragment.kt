package com.marvel.openbank.presentation.ui.characterslist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.marvel.openbank.R
import com.marvel.openbank.databinding.FragmentListCharactersBinding
import com.marvel.openbank.domain.model.characters.ListCharactersModel
import com.marvel.openbank.domain.model.characters.ResultCharacterModel
import com.marvel.openbank.presentation.base.BaseFragment
import com.marvel.openbank.presentation.base.ViewModelState
import com.marvel.openbank.presentation.ui.characterdetail.CharacterDetailFragment.Companion.KEY_CHARACTER_ID
import com.marvel.openbank.presentation.ui.characterslist.adapter.ListCharactersAdapter
import com.marvel.openbank.presentation.widget.spinner.SpinnerLoading
import com.marvel.openbank.utils.EndlessRecyclerViewScrollListener
import com.marvel.openbank.utils.getTimeStamp
import com.marvel.openbank.utils.md5
import javax.inject.Inject

class CharactersListFragment :
    BaseFragment<CharactersListViewModel, FragmentListCharactersBinding>(R.layout.fragment_list_characters),
    ListCharactersAdapter.ClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private val adapter: ListCharactersAdapter = ListCharactersAdapter(listener = this)

    @Inject
    lateinit var spinnerLoading: SpinnerLoading

    private var endlessScrollListener: EndlessRecyclerViewScrollListener? = null

    lateinit var apikey: String

    lateinit var privateKey: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        (activity as AppCompatActivity).supportActionBar?.hide()
        navController = view.findNavController()
        initLiveData()
        initToolbar()
        initRecycler()
        initUI()
        viewModel.getListCharacters(
            10,
            getTimeStamp(),
            apikey,
            md5(getTimeStamp(), privateKey, apikey)
        )
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.lToolbar.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
    }

    private fun initUI() {
        binding.srListCharacters.setOnRefreshListener(this)
        apikey = this.getString(R.string.KEY_MARVEL_PUBLIC_KEY)
        privateKey = this.getString(R.string.KEY_MARVEL_PRIVATE_KEY)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvListCharacters.layoutManager = linearLayoutManager
        endlessScrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(
                page: Int,
                totalItemsCount: Int,
                view: RecyclerView,
            ) {
                viewModel.getListCharacters(
                    limit = viewModel.limit + 10,
                    getTimeStamp(), apikey,
                    md5(getTimeStamp(), privateKey, apikey)
                )
            }
        }.also {
            binding.rvListCharacters.addOnScrollListener(it)
        }

        binding.rvListCharacters.adapter = adapter
    }

    private fun initLiveData() {
        viewModel.listCharacters.observe(this.viewLifecycleOwner) {
            manageViewState(it)
        }
    }

    private fun manageViewState(viewModelState: ViewModelState<ListCharactersModel>) {
        when (viewModelState) {
            is ViewModelState.InProgress -> showLoading()
            is ViewModelState.Loaded -> {
                hideLoading()
                successListCharacters(viewModelState.value)
            }
            is ViewModelState.ApiError -> {
                showError(viewModelState.message)
            }
            is ViewModelState.DefaultError -> {
                hideLoading()
                showError(getString(R.string.default_error_message))
            }
        }
    }

    private fun successListCharacters(listCharacters: ListCharactersModel) {
        if (listCharacters.results.isNotEmpty()) {
            if (viewModel.limit == 10) {
                fillListCharacters(listCharacters.results)
            } else {
                updateListCharacters(listCharacters.results)
            }
        } else {
            showError(getString(R.string.default_error_message))
        }
        hideLoading()
    }


    private fun initRecycler() {
        activity?.let {
            binding.rvListCharacters.layoutManager =
                LinearLayoutManager(it, RecyclerView.HORIZONTAL, false)
            setDividerHorizontal(binding.rvListCharacters)
        }
    }


    private fun setDividerHorizontal(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        activity?.let {
            val drawableVertical =
                ContextCompat.getDrawable(it, R.drawable.divider_horizontal)
            val itemDecoratorVertical = DividerItemDecoration(it, DividerItemDecoration.VERTICAL)
            drawableVertical?.let {
                itemDecoratorVertical.setDrawable(drawableVertical)
                recyclerView.addItemDecoration(itemDecoratorVertical)
            }
        }
    }

    private fun fillListCharacters(listCharacters: List<ResultCharacterModel>) {
        binding.srListCharacters.isRefreshing = false
        adapter.setCharacters(listCharacters)
        endlessScrollListener?.resetState()
        adapter.notifyDataSetChanged()
    }

    private fun updateListCharacters(listCharacters: List<ResultCharacterModel>) {
        binding.srListCharacters.isRefreshing = false
        adapter.addMoreCharacters(listCharacters)
        adapter.notifyDataSetChanged()
    }


    private fun hideSwipeLoading() {
        binding.srListCharacters.isRefreshing = false
    }

    override fun showLoading() {
        spinnerLoading.show(requireContext())
    }

    override fun hideLoading() {
        spinnerLoading.dismiss()
    }

    override fun onRefresh() {
        viewModel.getListCharacters(
            10, getTimeStamp(), apikey,
            md5(getTimeStamp(), privateKey, apikey)
        )
    }

    override fun getViewBinding() = FragmentListCharactersBinding.inflate(layoutInflater)

    override fun rowClick(character: ResultCharacterModel) {
        val data = Bundle()
        data.putInt(KEY_CHARACTER_ID, character.id)
        navigate(
            R.id.action_charactersListFragment_to_characterDetailFragment,
            data
        )
    }
}