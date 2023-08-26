package me.ecogaming.privategram.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.ecogaming.privategram.entity.Comment
import me.ecogaming.privategram.entity.DetailedPost
import me.ecogaming.privategram.entity.Post
import me.ecogaming.privategram.entity.Profile
import me.ecogaming.privategram.entity.ProxigramApiError
import me.ecogaming.privategram.network.ProxigramNetwork
import me.ecogaming.privategram.utils.SingleLiveData
import retrofit2.HttpException
import java.net.SocketTimeoutException

class PostViewModel : ViewModel() {

    val profile: MutableLiveData<Profile> = MutableLiveData()
    val post: MutableLiveData<Post> = MutableLiveData()

    val detailedPost: MutableLiveData<DetailedPost?> = MutableLiveData()
    val comments: MutableLiveData<List<Comment>?> = MutableLiveData()
    val error: SingleLiveData<ProxigramApiError?> = SingleLiveData()

    fun getDetailedPostAndComments(shortcode: String) {
        viewModelScope.launch {
            try {
                // add delay between requests to prevent error 403
                delay(500)
                detailedPost.value = ProxigramNetwork.retrofit.getPost(shortcode)
                delay(500)
                comments.value = ProxigramNetwork.retrofit.getPostComments(shortcode)
                error.postValue(null)
            } catch (e: HttpException) {
                detailedPost.postValue(null)
                comments.postValue(null)
                error.value = ProxigramApiError(statusCode = e.code(), message = e.message())
            } catch (e: SocketTimeoutException) {
                detailedPost.postValue(null)
                comments.postValue(null)
                error.value = ProxigramApiError(statusCode = -1, message = "timeout")
            }
        }
    }
}