package com.example.sahils_app.Repository

import androidx.lifecycle.MutableLiveData
import com.example.sahils_app.AllUserrr
import com.example.sahils_app.Userr
import com.google.firebase.database.*

class AllUserRepository {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("request_Profile")

    @Volatile private var INSTANCE : AllUserRepository ?= null

    fun getInstance() : AllUserRepository{
        return INSTANCE ?: synchronized(this){

            val instance = AllUserRepository()
            INSTANCE = instance
            instance
        }


    }


    fun loadUsers(userList : MutableLiveData<List<AllUserrr>>){

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _userList : List<AllUserrr> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(AllUserrr::class.java)!!
                    }
                    userList.postValue(_userList)
                }catch (e : Exception){ }
            }
            override fun onCancelled(error: DatabaseError) {}


        })


    }

}