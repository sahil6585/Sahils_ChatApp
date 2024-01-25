package com.example.sahils_app.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sahils_app.FriendUserr
import com.example.sahils_app.Repository.FriendUserRepository
import com.example.sahils_app.Repository.UserRepository
import com.example.sahils_app.Userr

class FriendModel : ViewModel() {

    private val repository : FriendUserRepository
    private val _allUsers = MutableLiveData<List<FriendUserr>>()
    val allUsers : LiveData<List<FriendUserr>> = _allUsers


    init {

        repository = FriendUserRepository().getInstance()
        repository.loadUsers(_allUsers)

    }

}