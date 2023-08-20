package me.ecogaming.privategram.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.ecogaming.privategram.entity.Profile

class ProfileViewModel : ViewModel() {

    val profile: MutableLiveData<Profile> = MutableLiveData()
}