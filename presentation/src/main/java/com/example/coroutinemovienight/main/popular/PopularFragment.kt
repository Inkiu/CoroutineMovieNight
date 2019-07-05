package com.example.coroutinemovienight.main.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.coroutinemovienight.R
import com.example.coroutinemovienight.common.BaseFragment
import com.example.coroutinemovienight.common.BaseViewModel
import com.example.coroutinemovienight.common.ImageLoader
import javax.inject.Inject

class PopularFragment : BaseFragment() {

    @Inject lateinit var vmFactory: PopularVMFactory
    @Inject lateinit var imageLoader: ImageLoader
    private val viewModel: PopularViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(PopularViewModel::class.java)
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_popular, container, false)
}
