package com.example.pokemon.model.response.pokemoninfo


data class PokemonInfo(
    val abilities: List<Ability>,
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    )