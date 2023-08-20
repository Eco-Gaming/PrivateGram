package me.ecogaming.privategram.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.ecogaming.privategram.entity.Post
import me.ecogaming.privategram.entity.Profile
import me.ecogaming.privategram.entity.ProxigramApiError
import me.ecogaming.privategram.network.ProxigramNetwork
import me.ecogaming.privategram.utils.SingleLiveData
import retrofit2.HttpException
import java.net.SocketTimeoutException

class ProfileViewModel : ViewModel() {

    val profile: MutableLiveData<Profile> = MutableLiveData()
    var posts: MutableLiveData<List<Post>?> = MutableLiveData()
    val error: SingleLiveData<ProxigramApiError?> = SingleLiveData()

    fun getProfileFeed(username: String) {
        viewModelScope.launch {
            try {
                posts.value = ProxigramNetwork.retrofit.getProfileFeed(username)
                error.postValue(null)
            } catch (e: HttpException) {
                posts.postValue(null)
                error.value = ProxigramApiError(statusCode = e.code(), message = e.message())
            } catch (e: SocketTimeoutException) {
                posts.postValue(null)
                error.value = ProxigramApiError(statusCode = -1, message = "timeout")
            }
        }
    }
}