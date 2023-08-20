package me.ecogaming.privategram.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import me.ecogaming.privategram.databinding.FragmentProfileBinding
import me.ecogaming.privategram.viewmodels.ProfileViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val viewModel: ProfileViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testTextView.text = viewModel.profile.value?.username ?: ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}