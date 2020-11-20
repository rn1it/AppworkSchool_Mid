package com.rn1.publisher.homepage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    private val _navigateToPost = MutableLiveData<Boolean>()

    val navigateToPost: LiveData<Boolean>
    get() = _navigateToPost


    fun toPost(){
        Log.d("hi", "hi toPost")
        _navigateToPost.value = true
    }

    fun onDoneNavigate(){
        _navigateToPost.value = null
    }

}