package com.test.jokes.ui.settings

import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.squareup.seismic.ShakeDetector
import com.test.jokes.R
import com.test.jokes.ui.base.BaseFragment
import com.test.jokes.utils.observe
import com.test.jokes.utils.toast
import kotlinx.android.synthetic.main.fragment_settins.*
import javax.inject.Inject


class SettingsFragment : BaseFragment(), ShakeDetector.Listener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settins, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel()
        observerViewModel()
        setListeners()
        detectShake()
    }

    private fun observerViewModel() {
        with(viewModel) {
            observe(offlineMode, ::onOfflineMode)
        }
    }

    private fun setListeners() {
        switch_offline.setOnCheckedChangeListener { _, isChecked ->
            requireContext().toast(isChecked.toString())
            viewModel.onOfflineModeChanged(isChecked)
        }
    }

    private fun onOfflineMode(offlineModeState: Boolean?) {
        switch_offline.isChecked = offlineModeState == true
    }

    private fun detectShake() {
        val sensorManager = requireContext().getSystemService(SENSOR_SERVICE) as SensorManager?
        val sd = ShakeDetector(this)
        sd.start(sensorManager)
    }

    override fun hearShake() {
        val newState = switch_offline?.isChecked?.not() ?: false
        switch_offline?.isChecked = newState
    }

    private fun getViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
    }
}