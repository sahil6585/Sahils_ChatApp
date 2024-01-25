package com.example.sahils_app.Fragments



import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sahils_app.Adapter.AllUserAdapter
import com.example.sahils_app.Adapter.RequestAdapter
import com.example.sahils_app.Model.AllUserViewModel
import com.example.sahils_app.Model.UserViewModel
import com.example.sahils_app.R
import com.example.sahils_app.databinding.FragmentRequestBinding

private lateinit var viewModel : AllUserViewModel
private lateinit var userRecyclerView: RecyclerView
@SuppressLint("StaticFieldLeak")
lateinit var adapterr: AllUserAdapter
private var binding: FragmentRequestBinding? = null

class AllUserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userRecyclerView = view.findViewById(R.id.recyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(context)
        userRecyclerView.setHasFixedSize(true)
        adapterr = AllUserAdapter(requireContext())
        userRecyclerView.adapter = adapterr

        viewModel = ViewModelProvider(this).get(AllUserViewModel::class.java)

        viewModel.allUsers.observe(viewLifecycleOwner, Observer {
            adapterr.updateUserList(it)
        })

    }
}
