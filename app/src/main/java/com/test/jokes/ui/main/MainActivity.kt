package com.test.jokes.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.test.data.jokes.models.mapped.JokesModel
import com.test.jokes.R
import com.test.jokes.ui.base.BaseActivity
import com.test.jokes.utils.observe
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel

    private val currencyAdapter = JokesAdapter()

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel()
        setListeners()
        observerViewModel()
//        rc_currency_rates.adapter = currencyAdapter
//        rc_currency_rates.itemAnimator = DefaultItemAnimator()
    }

    private fun observerViewModel() {
        with(viewModel) {
            observe(jokesItem, ::onJokesItem)
            observe(loading, ::onLoading)
            observe(error, ::onError)
        }
    }

    private fun setListeners() {
        currencyAdapter.setListener(object : JokesAdapter.Listener {
            override fun onAmountEntered(amount: Float) {

            }
        })
    }


    private fun onJokesItem(jokesRateModel: JokesModel?) {
        jokesRateModel?.let {
            currencyAdapter.updateList(it.value)
        }
    }


    private fun onError(error: String?) {
        Toast.makeText(this, error.orEmpty(), Toast.LENGTH_SHORT).show()
    }

    private fun onLoading(loading: Boolean?) {
        if (loading == true) {
            showProgressBar()
        } else {
            hideProgressBar()
        }
    }

    private fun showProgressBar() {
//        progress_circular.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
//        progress_circular?.visibility = View.GONE
    }

    private fun getViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }
}

