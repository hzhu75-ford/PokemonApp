package com.example.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokemon.model.network.APIHelper
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val apiHelper: APIHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            return PokemonViewModel(apiHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")    }

}
