package me.ecogaming.privategram.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import me.ecogaming.privategram.R
import me.ecogaming.privategram.adapters.ProfileFeedAdapter
import me.ecogaming.privategram.databinding.FragmentProfileBinding
import me.ecogaming.privategram.entity.Post
import me.ecogaming.privategram.entity.Profile
import me.ecogaming.privategram.viewmodels.PostViewModel
import me.ecogaming.privategram.viewmodels.ProfileViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val viewModel: ProfileViewModel by activityViewModels()
    private val postViewModel: PostViewModel by activityViewModels()

    private lateinit var profile: Profile

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        profile = viewModel.profile.value!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchProgressBar.visibility = View.VISIBLE

        val recyclerView = binding.feedRecyclerView

        (activity as AppCompatActivity).supportActionBar?.title = profile.username

        // Load and display the profile picture using Glide
        Glide.with(this)
            .load(profile.profilePicture)
            .apply(RequestOptions.circleCropTransform()) // Apply circle crop transformation for rounded profile pictures
            .placeholder(R.drawable.generic_profile_placeholder) // Placeholder image while loading
            // .error(R.drawable.error_image) // Error image if loading fails
            .into(binding.profilePicture)

        binding.textPostAmount.text = getString(R.string.profile_post_amount, profile.mediaCount.toString())
        binding.textFollowerAmount.text = getString(R.string.profile_follower_amount, profile.followers.toString())
        binding.textFollowingAmount.text = getString(R.string.profile_following_amount, profile.following.toString())

        binding.textFullname.text = profile.fullname
        binding.textBiography.text = profile.biography.trim()

        viewModel.getProfileFeed(profile.username)
        viewModel.posts.observe(viewLifecycleOwner) {
            if (it != null) {
                val adapter = ProfileFeedAdapter({ post -> adapterOnClick(post) }, it)
                recyclerView.adapter = adapter
                binding.searchProgressBar.visibility = View.GONE
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            if (it != null) {
                // TODO: add error handling
                binding.searchProgressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun adapterOnClick(post: Post) {
        postViewModel.profile.value = profile
        postViewModel.post.value = post
        findNavController().navigate(R.id.action_profileFragment_to_postFragment)
    }
}