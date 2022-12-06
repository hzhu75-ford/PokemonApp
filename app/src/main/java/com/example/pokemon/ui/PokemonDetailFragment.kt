package com.example.pokemon.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ShareCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentPokemonDetailsBinding
import com.example.pokemon.model.response.pokemoninfo.PokemonInfo
import com.example.pokemon.viewmodel.PokemonItem
import com.example.pokemon.viewmodel.PokemonViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

private const val TAG = "PokemonDetailFragment"
class PokemonDetailFragment : Fragment() {
    private var _binding: FragmentPokemonDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val pokemonViewModel: PokemonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
            .apply {
                callback = object : Callback {
                    override fun addPokemon(pokemon: PokemonInfo?) {
                        pokemon?.let {
                            hideAppBarFab(fab)
                            pokemonViewModel.addPokemonToCollection()
                            Snackbar.make(root, R.string.added_pokemon_to_collection, Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
                }

                composeView.setContent {
                    // You're in Compose world!
                    MaterialTheme {
                        PokemonDetailDescription(pokemonViewModel)
                    }
                }

                toolbar.setNavigationOnClickListener { view ->
                    view.findNavController().navigateUp()
                }

                toolbar.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_share -> {
                            createShareIntent()
                            true
                        }
                        else -> false
                    }
                }


            }
        return binding.root
    }

    @Suppress("DEPRECATION")
    private fun createShareIntent() {
        val shareText = pokemonViewModel.selectedPokemonItem.value.let { pokemon ->
            if (pokemon == null) {
                ""
            } else {
                getString(R.string.share_text_pokemon, pokemon.name)
            }
        }
        val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
            .setText(shareText)
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(shareIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pokemonViewModel.selectedPokemonItem.observe(viewLifecycleOwner) {
            it?.let {
                updateDataOnUI(it)
            }
        }
    }

    private fun updateDataOnUI(pokemonDetails: PokemonItem?) =
        Picasso.get().load(pokemonDetails?.imageURL).into(binding.detailImage)

    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }

    interface Callback {
        fun addPokemon(pokemon: PokemonInfo?)
    }

}