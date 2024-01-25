package com.example.sahils_app.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sahils_app.AllUserrr
import com.example.sahils_app.Repository.AllUserRepository

class AllUserViewModel : ViewModel() {

    private val repository : AllUserRepository
    private val _allUsers = MutableLiveData<List<AllUserrr>>()
    val allUsers : LiveData<List<AllUserrr>> = _allUsers


    init {
        repository = AllUserRepository().getInstance()
        repository.loadUsers(_allUsers)

    }

}