package com.example.pokemon.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemon.R
import com.example.pokemon.viewmodel.PokemonItem
import com.example.pokemon.viewmodel.PokemonViewModel
import com.example.pokemon.viewmodel.StatsDetails
import com.google.android.material.composethemeadapter.MdcTheme

@Composable
fun PokemonDetailDescription(pokemonViewModel: PokemonViewModel) {

    val pokemonSelected by pokemonViewModel.selectedPokemonItem.observeAsState()


    pokemonSelected?.let { PokemonDetailContent(it) }

}

@Composable
fun PokemonDetailContent(pokemon: PokemonItem) {
    Surface {

        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_normal))){
            PokemonName(pokemon.name)
            PokemonStats(pokemon.stats)
            PokemonAbilities(pokemon.abilities)

        }

    }

}

@Composable
fun PokemonAbilities(abilities: String) {
    Column(Modifier.fillMaxWidth()) {
        val normalPadding = dimensionResource(R.dimen.margin_normal)

        Text(text = "Abilities: $abilities" ,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.padding(normalPadding)
        )
    }
}

@Composable
fun PokemonStats(stats: List<StatsDetails>?) {
    Column(Modifier.fillMaxWidth()) {
        val normalPadding = dimensionResource(R.dimen.margin_normal)

        Text(text = stats?.let{ it.joinToString(separator = " ") { statsDetails ->  "${statsDetails.name}: ${statsDetails.effort}/${statsDetails.baseStat}"}} ?:"N/A" ,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(normalPadding)
        )
    }

}

@Composable
fun PokemonName(name: String) {
    Text(text = name,
        style = MaterialTheme.typography.h4,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            .wrapContentWidth(Alignment.CenterHorizontally))
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PokemonNamePreview(){
    val pokemon = PokemonItem(
        name = "Pidgey",
        imageURL = "",
        stats = emptyList(),
        abilities = "Keen-eye/Tangled-feet/Big-pecks",
        types = "Attack:0/45 Defense: 0/40 Speed: 1/56"
    )
    MdcTheme{
        PokemonDetailContent(pokemon)
    }
}

@Composable
@Preview
private fun PokemonStatsPreview(){
    val types = listOf(
        StatsDetails("Attack",0,45),
        StatsDetails("Defense",0,45),
        StatsDetails("Speed",1,70))
    MdcTheme{
        PokemonStats(types)
    }
}


@Preview
@Composable
private fun PokemonAbilitiesPreview(){
    val abilities = "Keen-eye/Tangled-feet/Big-pecks"
    MdcTheme{
        PokemonAbilities(abilities)
    }
}
