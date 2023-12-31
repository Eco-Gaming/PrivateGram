package me.ecogaming.privategram.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import me.ecogaming.privategram.R
import me.ecogaming.privategram.databinding.FragmentHomeBinding
import me.ecogaming.privategram.viewmodels.HomeViewModel
import me.ecogaming.privategram.viewmodels.ProfileViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val viewModel: HomeViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_main, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_about -> {
                            findNavController().navigate(R.id.action_homeFragment_to_aboutFragment)
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner, Lifecycle.State.RESUMED
        )

        binding.buttonOpenProfile.setOnClickListener {
            loading(true)
            openProfile()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loading(false)
        _binding = null
    }

    private fun openProfile() {
        val profileName = binding.textInputProfileName.text.toString()
        if (profileName.isEmpty()) {
            val text = getString(R.string.error_empty_profile)
            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.getProfile(profileName)
        viewModel.profile.observe(viewLifecycleOwner) {
            if (it != null) {
                profileViewModel.profile.value = it
                if (it.isPrivate) {
                    val text = getString(R.string.error_profile_private)
                    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
                } else {
                    findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
                    viewModel.profile.removeObservers(viewLifecycleOwner)
                }
                loading(false)
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            if (it != null) {
                val errorInfo = it.statusCode.toString() + (if (it.message.isNotEmpty()) " " + it.message else "")
                var text = getString(R.string.error_could_not_open_profile, errorInfo)
                if (it.statusCode == 404) {
                    text = getString(R.string.error_could_not_find_profile)
                } else if (it.statusCode == -1) {
                    text = getString(R.string.error_timeout)
                }
                Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
                loading(false)
            }
        }
    }

    private fun loading(isLoading: Boolean) {
        binding.buttonOpenProfile.isClickable = !isLoading
        binding.searchProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}