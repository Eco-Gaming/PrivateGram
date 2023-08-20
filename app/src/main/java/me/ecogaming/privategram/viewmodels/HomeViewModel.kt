package me.ecogaming.privategram.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.ecogaming.privategram.entity.Profile
import me.ecogaming.privategram.entity.ProxigramApiError
import me.ecogaming.privategram.network.ProxigramNetwork
import me.ecogaming.privategram.utils.SingleLiveData
import retrofit2.HttpException
import java.net.SocketTimeoutException

class HomeViewModel : ViewModel() {

    val profile: SingleLiveData<Profile?> = SingleLiveData()
    val error: SingleLiveData<ProxigramApiError?> = SingleLiveData()

    fun getProfile(username: String) {
        viewModelScope.launch {
            try {
                profile.value = ProxigramNetwork.retrofit.getProfile(username)
                error.postValue(null)
            } catch (e: HttpException) {
                profile.postValue(null)
                error.value = ProxigramApiError(statusCode = e.code(), message = e.message())
            } catch (e: SocketTimeoutException) {
                profile.postValue(null)
                error.value = ProxigramApiError(statusCode = -1, message = "timeout")
            }
        }
    }
}