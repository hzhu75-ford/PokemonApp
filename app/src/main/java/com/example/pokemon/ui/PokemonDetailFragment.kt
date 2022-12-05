package com.example.pokemon.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.core.app.ShareCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentPokemonDetailsBinding
import com.example.pokemon.ui.pokemonDetail.PokemonDetailDescription
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
            .apply {
                composeView.setContent {
                    // You're in Compose world!
                    MaterialTheme {
                        PokemonDetailDescription(viewModel)
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
        val shareText = viewModel.selectedPokemonItem.value.let { pokemon ->
            if (pokemon == null) {
                ""
            } else {
                getString(R.string.share_text_pokemon,pokemon.name )
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
        viewModel.selectedPokemonItem.observe(viewLifecycleOwner) {
            it?.let {
                updateDataOnUI(it)
            }
        }
    }

    private fun updateDataOnUI(pokemonDetails: PokemonItem?) {
        Picasso.get().load(pokemonDetails?.imageURL).into(binding.detailImage)
    }
}