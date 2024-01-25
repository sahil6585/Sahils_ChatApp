package com.example.sahils_app.Repository

import androidx.lifecycle.MutableLiveData
import com.example.sahils_app.FriendUserr
import com.example.sahils_app.Userr
import com.google.firebase.database.*

class FriendUserRepository {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("UserProfile")

    @Volatile private var INSTANCE : FriendUserRepository ?= null

    fun getInstance() : FriendUserRepository{
        return INSTANCE ?: synchronized(this){

            val instance = FriendUserRepository()
            INSTANCE = instance
            instance
        }


    }


    fun loadUsers(userList : MutableLiveData<List<FriendUserr>>){

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                try {

                    val _userList : List<FriendUserr> = snapshot.children.map { dataSnapshot ->

                        dataSnapshot.getValue(FriendUserr::class.java)!!

                    }

                    userList.postValue(_userList)

                }catch (e : Exception){


                }


            }

            override fun onCancelled(error: DatabaseError) {
            }


        })


    }

}