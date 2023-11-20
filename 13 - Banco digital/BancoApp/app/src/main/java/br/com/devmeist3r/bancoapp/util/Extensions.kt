package br.com.devmeist3r.bancoapp.util

import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.devmeist3r.bancoapp.R

fun Fragment.initToolbar(toolbar: Toolbar, homeAsUpEnabled: Boolean = true) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
    (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
}