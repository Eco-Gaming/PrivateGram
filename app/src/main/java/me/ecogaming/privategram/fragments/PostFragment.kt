package me.ecogaming.privategram.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.squareup.picasso.Picasso
import me.ecogaming.privategram.databinding.FragmentPostBinding
import me.ecogaming.privategram.viewmodels.PostViewModel

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null

    private val viewModel: PostViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.postHeaderUsername.text = viewModel.profile.value?.username ?: "failed getting username :("

        viewModel.post.value?.shortcode?.let { viewModel.getDetailedPostAndComments(it) }
        viewModel.detailedPost.observe(viewLifecycleOwner) {
            if (it != null) {
                Picasso.get().load(it.sidecard[0].url).into(binding.postImage)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}