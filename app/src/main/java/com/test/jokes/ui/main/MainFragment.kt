package com.test.jokes.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import com.test.data.jokes.models.mapped.JokesModel
import com.test.jokes.R
import com.test.jokes.ui.base.BaseFragment
import com.test.jokes.utils.observe
import com.test.jokes.utils.show
import com.test.jokes.utils.toast
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainFragViewModel

    private val currencyAdapter = JokesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getViewModel()
        setListeners()
        observerViewModel()
        setupView()
    }

    private fun setupView() {
        rc_jokes.adapter = currencyAdapter
        rc_jokes.itemAnimator = DefaultItemAnimator()
    }

    private fun observerViewModel() {
        with(viewModel) {
            observe(jokesItem, ::onJokesItem)
            observe(loading, ::onLoading)
            observe(error, ::onError)
        }
    }

    private fun onJokesItem(jokesRateModel: JokesModel?) {
        jokesRateModel?.let {
            currencyAdapter.updateList(it.value)
        }
    }

    private fun onError(error: String?) = requireContext().toast(error.orEmpty())
    private fun onLoading(loading: Boolean?) = progress_circular.show(loading == true)

    private fun setListeners() {
        currencyAdapter.setListener(object : JokesAdapter.Listener {
            override fun onAmountEntered(amount: Float) {

            }
        })
    }

    private fun getViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainFragViewModel::class.java)
    }
}