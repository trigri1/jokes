package com.test.jokes.ui.myjokes.addjoke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.test.jokes.R
import com.test.jokes.ui.base.BaseFragment
import com.test.jokes.utils.observe
import com.test.jokes.utils.toast
import kotlinx.android.synthetic.main.fragment_add_joke.*
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
        setListeners()
    }

    private fun setListeners() {
        tv_save.setOnClickListener {
            viewModel.onSaveJokeClicked(et_new_jokes.text.toString())
        }
    }

    private fun observerViewModel() {
        with(viewModel) {
            observe(error, ::onError)
        }
    }

    private fun onError(error: String?) = requireContext().toast(error.orEmpty())

    private fun getViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddJokeViewModel::class.java)
    }
}