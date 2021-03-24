package com.example.sportsmates.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.sportsmates.databinding.ProfileFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    val viewModel: ProfileViewModel by viewModel()

    private var _binding: ProfileFragmentBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.fetchUserData()
        attachEventObservers()

    }

    private fun attachEventObservers() {
        viewModel.userData.observe(this, Observer { user ->

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }

}