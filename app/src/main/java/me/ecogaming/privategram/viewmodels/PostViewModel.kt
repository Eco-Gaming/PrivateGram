package me.ecogaming.privategram.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.ecogaming.privategram.entity.Comment
import me.ecogaming.privategram.entity.Post
import me.ecogaming.privategram.entity.ProxigramApiError
import me.ecogaming.privategram.network.ProxigramNetwork
import me.ecogaming.privategram.utils.SingleLiveData
import retrofit2.HttpException
import java.net.SocketTimeoutException

class PostViewModel : ViewModel() {

    val post: MutableLiveData<Post> = MutableLiveData()
    val comments: MutableLiveData<List<Comment>?> = MutableLiveData()
    val error: SingleLiveData<ProxigramApiError?> = SingleLiveData()

    fun getComments(shortcode: String) {
        viewModelScope.launch {
            try {
                comments.value = ProxigramNetwork.retrofit.getPostComments(shortcode)
                error.postValue(null)
            } catch (e: HttpException) {
                comments.postValue(null)
                error.value = ProxigramApiError(statusCode = e.code(), message = e.message())
            } catch (e: SocketTimeoutException) {
                comments.postValue(null)
                error.value = ProxigramApiError(statusCode = -1, message = "timeout")
            }
        }
    }
}