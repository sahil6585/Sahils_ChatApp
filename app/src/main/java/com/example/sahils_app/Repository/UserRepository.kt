package com.example.sahils_app.Repository

import androidx.lifecycle.MutableLiveData
import com.example.sahils_app.Userr
import com.google.firebase.database.*

class UserRepository {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("UserProfile")

    @Volatile private var INSTANCE : UserRepository ?= null

    fun getInstance() : UserRepository{
        return INSTANCE ?: synchronized(this){

            val instance = UserRepository()
            INSTANCE = instance
            instance
        }


    }


    fun loadUsers(userList : MutableLiveData<List<Userr>>){

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                try {

                    val _userList : List<Userr> = snapshot.children.map { dataSnapshot ->

                        dataSnapshot.getValue(Userr::class.java)!!

                    }

                    userList.postValue(_userList)

                }catch (e : Exception){


                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }

}