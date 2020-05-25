package com.test.jokes.ui.main

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.test.jokes.R
import com.test.jokes.ui.base.BaseActivity
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

    private var selectedFragment = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel()
        setupDrawer()
    }

    private fun setupDrawer() {
        setNavigationView()
        setActionBar()
        setupDrawerToggle()
    }

    private fun setActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setNavigationView() {
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        nav_view.setupWithNavController(navController)
        nav_view.setCheckedItem(R.id.mainFragment)
        nav_view.setNavigationItemSelectedListener { menuItem ->
            drawer_layout.closeDrawers()
            if (selectedFragment != menuItem.itemId) {
                selectedFragment = menuItem.itemId
                when (selectedFragment) {
                    R.id.menu_fragment_jokes_list -> {
                        toolbar.title = getString(R.string.jokes_title)
                        navController.navigate(R.id.mainFragment)
                    }
                    R.id.menu_fragment_my_jokes -> {
                        toolbar.title = getString(R.string.my_list_title)
                        navController.navigate(R.id.myJokesFragment)
                    }
                    R.id.menu_fragment_setting -> {
                        toolbar.title = getString(R.string.settings_title)
                        navController.navigate(R.id.settingsFragment)
                    }
                }
            }
            menuItem.isChecked = true
            true
        }
    }

    private fun setupDrawerToggle() {
        val mDrawerToggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.app_name,
            R.string.app_name
        )
        mDrawerToggle.syncState()
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

