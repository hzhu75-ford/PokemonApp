package com.example.pokemon.model.network

import com.example.pokemon.model.response.pokemon.PokemonItems
import com.example.pokemon.model.response.pokemoninfo.PokemonInfo
import retrofit2.http.GET
import retrofit2.http.Url

interface RetroAPIService {
    @GET
    suspend fun getPokemonList(@Url url:String): PokemonItems

    @GET
    suspend fun getPokemonDetails(@Url url: String): PokemonInfo
}
