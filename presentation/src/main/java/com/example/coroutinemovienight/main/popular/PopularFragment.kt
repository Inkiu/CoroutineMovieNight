package com.example.coroutinemovienight.main.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.coroutinemovienight.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_popular.*
import javax.inject.Inject

class PopularFragment : DaggerFragment() {

    @Inject lateinit var sampleText: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_popular, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sampleTextView.text = sampleText
    }
}
