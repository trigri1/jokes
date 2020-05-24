package com.test.jokes.ui.main

import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import com.squareup.seismic.ShakeDetector
import com.test.data.jokes.models.mapped.Joke
import com.test.jokes.R
import com.test.jokes.ui.base.BaseFragment
import com.test.jokes.utils.observe
import com.test.jokes.utils.show
import com.test.jokes.utils.toast
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject


class MainFragment : BaseFragment(), ShakeDetector.Listener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainFragViewModel

    private val jokesAdapter = JokesAdapter()

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
        rc_jokes.adapter = jokesAdapter
        rc_jokes.itemAnimator = DefaultItemAnimator()
        detectShake()
    }

    private fun observerViewModel() {
        with(viewModel) {
            observe(jokesItem, ::onJokesItem)
            observe(loading, ::onLoading)
            observe(share, ::onShare)
            observe(error, ::onError)
        }
    }

    private fun onJokesItem(jokesRateModel: List<Joke>?) {
        jokesRateModel?.let {
            jokesAdapter.updateList(it)
        }
    }

    private fun onError(error: String?) = requireContext().toast(error.orEmpty())
    private fun onLoading(loading: Boolean?) = progress_circular.show(loading == true)

    private fun onShare(joke: String?) {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"
        share.putExtra(Intent.EXTRA_TEXT, joke.orEmpty())
        startActivity(Intent.createChooser(share, "Share Joke"))
    }

    private fun setListeners() {
        jokesAdapter.setListener(object : JokesAdapter.Listener {
            override fun onLikeClicked(joke: Joke) = viewModel.onLikeClicked(joke)
            override fun onUnLikeClicked(id: Long) = viewModel.onUnLikeJokeClicked(id)
            override fun onShareClicked(joke: String) = viewModel.onShareClicked(joke)
        })
    }

    private fun detectShake() {
        val sensorManager =
            requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        val sd = ShakeDetector(this)
        sd.start(sensorManager)
    }

    private fun getViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainFragViewModel::class.java)
    }

    override fun hearShake() {
        viewModel.getJokes()
    }
}