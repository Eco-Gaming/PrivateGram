package me.ecogaming.privategram.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.ecogaming.privategram.entity.TaggedPostsResults
import me.ecogaming.privategram.network.ProxigramNetwork

class SearchViewModel : ViewModel() {

    val foundPosts: MutableLiveData<TaggedPostsResults> = MutableLiveData()

    fun getTaggedPosts(tag: String) {
        viewModelScope.launch {
            foundPosts.value = ProxigramNetwork.retrofit.getTaggedPosts(tag)
        }
    }
}