package com.devmeist3r.taskapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.devmeist3r.taskapp.R
import com.devmeist3r.taskapp.databinding.FragmentHomeBinding
import com.devmeist3r.taskapp.ui.adapter.ViewPagerAdapter
import com.devmeist3r.taskapp.util.showBottomSheet
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabs()
        initListeners()
    }

    private fun initTabs() {
        val pageAdapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = pageAdapter

        pageAdapter.addFragment(TodoFragment(), R.string.status_task_todo)
        pageAdapter.addFragment(DoingFragment(), R.string.status_task_doing)
        pageAdapter.addFragment(DoneFragment(), R.string.status_task_done)

        binding.viewPager.offscreenPageLimit = pageAdapter.itemCount
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = getText(pageAdapter.getTitle(position))
        }.attach()
    }

    private fun initListeners() {
        binding.btnLogout.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        showBottomSheet(
            titleButton = R.string.text_button_dialog_confirm,
            titleDialog = R.string.text_title_dialog_confirm_logout,
            message = getString(R.string.text_message_dialog_confirm_logout),
            onClick = {
                Firebase.auth.signOut()
                findNavController().navigate(R.id.action_homeFragment_to_authentication)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}