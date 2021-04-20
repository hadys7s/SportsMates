package com.example.sportsmates.discover

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsmates.chat.MessagesActivity
import com.example.sportsmates.databinding.DiscoverFragmentBinding
import com.example.sportsmates.ext.displayWarningToast
import com.example.sportsmates.ext.stopShimmer
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
        attachEventObservers()

    }

    private fun attachEventObservers() {
        viewModel.retriveUsersSuccess.observe(this, Observer {
            stopShimmer(binding.shimmerViewContainer)
            setUsers(it)
        })
        viewModel.retriveUsersError.observe(this, Observer {
            stopShimmer(binding.shimmerViewContainer)
            displayWarningToast(" ", it)
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
        contactsAdapter.onItemClick = { user, targetImage ->
            openActivityWithTransitionAnimation(user, targetImage,1)
        }
        contactsAdapter.onBtnClick = { user,targetImage ->
            openActivityWithTransitionAnimation(user, targetImage,2)
        }
    }

    private fun openActivityWithTransitionAnimation(
        user: User,
        targetImage: ImageView,
        targetActivity: Int
    ) {
        val option =
            ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, targetImage, "img")
                .toBundle()
        if (targetActivity == 1) {
            ContactsDetails.start(activity, user, option!!)
        } else {
            MessagesActivity.start(activity, user, option!!)
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
