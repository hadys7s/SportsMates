package com.example.sportsmates.discover

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsmates.databinding.DiscoverFragmentBinding
import com.example.sportsmates.signUp.data.model.User
import org.koin.android.viewmodel.ext.android.viewModel

class ContactsFragment : Fragment() {

    private val viewModel: ContactsViewModel by viewModel()
    private var _binding: DiscoverFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var contactsAdapter: ContactsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DiscoverFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupList()
        viewModel._listOfUsersEvent.observe(this, Observer {
            setUsers(it)
        })
    }

    private fun setupList() {
        binding.contactsList.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setUsers(usersList: List<User>?) {
        contactsAdapter = ContactsAdapter(usersList, activity)
        binding.contactsList.adapter = contactsAdapter
        contactsAdapter.onItemClick = { user ->
            ContactsDetail.start(activity, user)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        fun newInstance() = ContactsFragment()
    }


}