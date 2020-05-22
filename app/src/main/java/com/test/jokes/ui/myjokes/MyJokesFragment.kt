package com.test.jokes.ui.myjokes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.test.jokes.R
import com.test.jokes.ui.base.BaseFragment
import com.test.jokes.ui.myjokes.MyJokesViewModel.Navigation
import com.test.jokes.utils.observe
import kotlinx.android.synthetic.main.fragment_my_jokes.*
import javax.inject.Inject

class MyJokesFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MyJokesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_jokes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel()
        observerViewModel()
        setListener()
    }

    private fun observerViewModel() {
        with(viewModel) {
            observe(navigation, ::onNavigation)
        }
    }

    private fun onNavigation(navigation: Navigation?) {
        if (navigation == Navigation.AddJoke) {
            findNavController().navigate(R.id.addJokeFragment)
        }
    }

    private fun setListener() {
        fab_add_joke.setOnClickListener {
            viewModel.onAddJokeClicked()
        }
    }

    private fun getViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyJokesViewModel::class.java)
    }
}