package com.test.jokes.ui.myjokes.addjoke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.test.jokes.R
import com.test.jokes.ui.base.BaseFragment
import javax.inject.Inject

class AddJokeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AddJokeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_joke, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel()
        observerViewModel()
    }

    private fun observerViewModel() {
        with(viewModel) {
//            observe(navigation, ::onNavigation)
        }
    }
    
    private fun getViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddJokeViewModel::class.java)
    }
}