package com.example.pokemon.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.pokemon.databinding.FragmentPokemonDetailsBinding
import com.example.pokemon.viewmodel.PokemonItem
import com.example.pokemon.viewmodel.PokemonViewModel
import com.squareup.picasso.Picasso

private const val TAG = "PokemonDetailFragment"
class PokemonDetailFragment : Fragment() {
    private var _binding: FragmentPokemonDetailsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PokemonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedPokemonItem.observe(viewLifecycleOwner) {
            it?.let {
                updateDataOnUI(it)
            }
        }
    }

    //Update Pokemon Details on UI
    private fun updateDataOnUI(pokemonDetails: PokemonItem?) {
        Log.d(TAG, "${pokemonDetails?.abilities}")
        binding.pokemonName.text = pokemonDetails?.name.orEmpty()
        binding.pokemonAbilities.text = pokemonDetails?.abilities.orEmpty()
        val statsDetails = pokemonDetails?.stats?.joinToString(separator = " ") { "${it.name}: ${it.effort}/${it.baseStat}"}
        binding.pokemonStats.text = statsDetails
        Picasso.get().load(pokemonDetails?.imageURL).into(binding.pokemonImage)
    }
}