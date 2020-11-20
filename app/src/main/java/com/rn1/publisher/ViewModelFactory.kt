package com.rn1.publisher

import com.rn1.publisher.homepage.HomeViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rn1.publisher.postpage.PostViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel() as T
            modelClass.isAssignableFrom(PostViewModel::class.java) -> PostViewModel() as T
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}