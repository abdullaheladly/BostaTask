package com.abdullah996.bostatask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.abdullah996.bostatask.R
import com.abdullah996.bostatask.util.ApiStatus
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val testViewModel:TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testViewModel.getAllUser().observe(this) {
            when (it.status) {
                ApiStatus.SUCCESS -> {
                    Toast.makeText(this, it.data.toString(), Toast.LENGTH_SHORT).show()


                }
                ApiStatus.ERROR -> {
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()

                }
                ApiStatus.LOADING -> {

                }
            }
        }
    }
}