package com.vickikbt.covid_19statapp.ui.global

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vickikbt.covid_19statapp.R
import com.vickikbt.covid_19statapp.databinding.FragmentGlobalBinding
import com.vickikbt.covid_19statapp.util.coroutines.ScopedFragment
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

internal class GlobalFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var binding: FragmentGlobalBinding
    private lateinit var viewModel: GlobalFragmentViewModel
    private val factory: GlobalViewFragmentModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_global, container, false)
        viewModel = ViewModelProvider(this, factory).get(GlobalFragmentViewModel::class.java)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUI()
    }

    //TODO: Linting Adding progress to ViewUtils
    private fun bindUI() = launch {
        val currentGlobalStat = viewModel.globalStatistics.await()
        currentGlobalStat.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            binding.progressBarGlobal.visibility = View.GONE

            binding.textViewCases.text = it.cases.toString()
            binding.textViewDeaths.text = it.deaths.toString()
            binding.textViewRecovered.text = it.recovered.toString()
        })

        binding.textViewDate.text = viewModel.getDate()
    }

}
