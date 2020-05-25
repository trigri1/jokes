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
import com.test.data.offline.model.CharacterName
import com.test.jokes.R
import com.test.jokes.ui.base.BaseFragment
import com.test.jokes.utils.observe
import kotlinx.android.synthetic.main.fragment_settings.*
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
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel()
        observerViewModel()
        setListeners()
        detectShake()
    }

    override fun onDestroyView() {
        viewModel.setCharacterName(et_first_name.text.toString(), et_last_name.text.toString())
        super.onDestroyView()
    }

    override fun hearShake() {
        if (isAdded) {
            val newState = switch_offline?.isChecked?.not() ?: false
            switch_offline?.isChecked = newState
            viewModel.onDeviceShake(newState)
        }
    }

    private fun observerViewModel() {
        with(viewModel) {
            observe(characterName, ::onCharacterName)
            observe(offlineMode, ::onOfflineMode)
        }
    }

    private fun setListeners() {
        switch_offline.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onOfflineModeChanged(isChecked)
        }
    }

    private fun onCharacterName(characterName: CharacterName?) {
        characterName?.let {
            et_first_name.setText(it.firstName)
            et_last_name.setText(it.lastName)
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

    private fun getViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
    }
}