package com.example.wachanga.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wachanga.R
import com.example.wachanga.WachangaApplication
import com.example.wachanga.domain.Note
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class MainFragment : MvpAppCompatFragment(), MainView {

    private val layoutResId = R.layout.fragment_main

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WachangaApplication.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun showNotes(notes: List<Note>) {
        TODO("Not yet implemented")
    }

}
