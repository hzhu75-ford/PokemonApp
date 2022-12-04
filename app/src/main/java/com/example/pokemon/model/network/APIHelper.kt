package com.example.pokemon.model.network

class APIHelper(private val apiService: RetroAPIService) {
    suspend fun getPokemonList(url: String) = apiService.getPokemonList(url)
    suspend fun getPokemonDetails(url: String) = apiService.getPokemonDetails(url)
}
