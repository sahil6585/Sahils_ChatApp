package com.example.sahils_app.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sahils_app.Repository.UserRepository
import com.example.sahils_app.Userr

class UserViewModel : ViewModel() {

    private val repository : UserRepository
    private val _allUsers = MutableLiveData<List<Userr>>()
    val allUsers : LiveData<List<Userr>> = _allUsers


    init {

        repository = UserRepository().getInstance()
        repository.loadUsers(_allUsers)

    }

}