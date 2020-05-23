package com.test.jokes.ui.myjokes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.test.data.jokes.models.mapped.Joke
import com.test.jokes.R
import com.test.jokes.ui.base.BaseFragment
import com.test.jokes.ui.myjokes.MyJokesViewModel.Navigation
import com.test.jokes.utils.observe
import com.test.jokes.utils.toast
import kotlinx.android.synthetic.main.fragment_my_jokes.*
import javax.inject.Inject

class MyJokesFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MyJokesViewModel

    private val userJokesAdapter = UserJokesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_jokes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        getViewModel()
        observerViewModel()
        setListener()
    }


    private fun setListener() {
        fab_add_joke.setOnClickListener {
            viewModel.onAddJokeClicked()
        }

        userJokesAdapter.setListener(object : UserJokesAdapter.Listener {
            override fun onDeleteClicked(joke: Joke) {
                viewModel.onDeleteJokeClicked(joke)
            }
        })
    }

    private fun observerViewModel() {
        with(viewModel) {
            observe(userJokes, ::onJokesList)
            observe(error, ::onError)
            observe(navigation, ::onNavigation)
        }
    }

    private fun setupView() {
        rc_jokes.adapter = userJokesAdapter
        rc_jokes.itemAnimator = DefaultItemAnimator()
    }

    private fun onJokesList(list: List<Joke>?) {
        list?.let {
            userJokesAdapter.updateList(it)
        }
    }

    private fun onError(error: String?) = requireContext().toast(error.orEmpty())

    private fun onNavigation(navigation: Navigation?) {
        if (navigation == Navigation.AddJoke) {
            findNavController().navigate(R.id.addJokeFragment)
        }
    }

    private fun getViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyJokesViewModel::class.java)
    }
}