package com.example.firebasewithphone

import androidx.lifecycle.MutableLiveData

class LiveData {

    val count = MutableLiveData<Int>()

    fun setCount(counter : Int){
      count.value = counter
    }
}