package com.example.pokemon.model.response.pokemoninfo

data class Sprites(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null,
) {
    fun getImageURL() = back_default?:back_shiny?:front_default?:front_shiny
}
