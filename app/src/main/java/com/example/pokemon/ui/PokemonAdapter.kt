package com.example.pokemon.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.databinding.FragmentPokemonListBinding
import com.example.pokemon.databinding.ViewHolderLoadMoreBinding
import com.example.pokemon.viewmodel.LoadMoreItems
import com.example.pokemon.viewmodel.PokemonBaseItem
import com.example.pokemon.viewmodel.PokemonItem
import com.squareup.picasso.Picasso

private const val VIEW_TYPE_POKEMON_ITEM = 0
private const val VIEW_TYPE_LOAD_MORE_ITEM = 1

class PokemonAdapter(private val onClick: (PokemonBaseItem) -> Unit) :
    ListAdapter<PokemonBaseItem, RecyclerView.ViewHolder>(PokemonDiffCallback) {

    /* ViewHolder for Pokemon items, takes in the inflated view and the onClick behavior. */
    class PokemonViewHolder(binding: FragmentPokemonListBinding, val onClick: (PokemonItem) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        private val nameTextView: TextView = binding.pokemonName
        private val imageView: ImageView = binding.pokemonImage
        private val typesTextView: TextView = binding.pokemonTypes
        private var currentPokemon: PokemonItem? = null

        init {
            itemView.setOnClickListener {
                currentPokemon?.let {
                    onClick(it)

                }
            }
        }

        /* Bind pokemon name, image and types. */
        fun bind(pokemon: PokemonItem) {
            with(pokemon) {
                currentPokemon = this
                nameTextView.text = name
                typesTextView.text = types
                imageURL?.let {
                    Picasso.get().load(it).into(imageView)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is PokemonItem -> VIEW_TYPE_POKEMON_ITEM
            is LoadMoreItems -> VIEW_TYPE_LOAD_MORE_ITEM
        }
    }

    /* Creates and inflates view and return PokemonViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            VIEW_TYPE_POKEMON_ITEM -> {
                val binding = FragmentPokemonListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return PokemonViewHolder(binding, onClick)
            }
            else ->{
                val binding = ViewHolderLoadMoreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LoadMoreViewHolder(binding, onClick)
            }
        }

    }

    /* Gets current pokemon and uses it to bind view. */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PokemonViewHolder -> {
                val pokemon = getItem(position)
                holder.bind(pokemon as PokemonItem)
            }
            is LoadMoreViewHolder -> {
                val loadMoreItem = getItem(position)
                holder.bind(loadMoreItem as LoadMoreItems)
            }
        }

    }

    /* ViewHolder for Load More, takes in the inflated view and the onClick behavior. */
    class LoadMoreViewHolder(binding: ViewHolderLoadMoreBinding, val onClick: (PokemonBaseItem) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        private val loadMoreText: TextView = binding.loadMore
        private var loadMoreItem: LoadMoreItems? = null

        init {
            itemView.setOnClickListener {
                loadMoreItem?.let {
                    onClick(it)
                }
            }
        }

        /* Bind Load more name. */
        fun bind(item: LoadMoreItems) {
            loadMoreItem = item
            loadMoreText.apply {
                text = resources.getString(item.loadingStatus)
                isClickable = item.isEnabled
            }
        }
    }
}

object PokemonDiffCallback : DiffUtil.ItemCallback<PokemonBaseItem>() {
    override fun areItemsTheSame(oldItem: PokemonBaseItem, newItem: PokemonBaseItem): Boolean =
        when{
            (oldItem is PokemonItem && newItem is PokemonItem) ->
                oldItem.name == newItem.name
            (oldItem is LoadMoreItems && newItem is LoadMoreItems) -> true
            else -> false
        }

    override fun areContentsTheSame(oldItem: PokemonBaseItem, newItem: PokemonBaseItem): Boolean  =
        when{
            (oldItem is PokemonItem && newItem is PokemonItem) ->
                oldItem.name == newItem.name
            (oldItem is LoadMoreItems && newItem is LoadMoreItems) ->
                oldItem.loadingStatus == newItem.loadingStatus
            else -> false
        }
}