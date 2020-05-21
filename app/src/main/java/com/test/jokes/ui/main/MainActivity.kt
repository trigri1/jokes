package com.test.jokes.ui.main

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.test.jokes.R
import com.test.jokes.ui.base.BaseActivity
import com.test.jokes.utils.observe
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel

    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel()
        observerViewModel()
        setupDrawer()
    }

    private fun observerViewModel() {
        with(viewModel) {
            observe(navigation, ::onNavigation)
        }
    }

    private fun onNavigation(navigation: MainViewModel.Navigation?) {
        when (navigation) {

        }
    }

    private fun setupDrawer() {
        setSupportActionBar(toolbar)
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        nav_view.setupWithNavController(navController)

        nav_view.setNavigationItemSelectedListener { menuItem ->
            drawer_layout.closeDrawers()
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.menu_fragment_main -> {
                    navController.navigate(R.id.mainFragment)
                }
                R.id.menu_fragment_my_jokes -> {
                }
                R.id.menu_fragment_setting -> {
                }
            }

            true
        }

    }

    private fun getViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            return super.onBackPressed()
        }
    }
}

