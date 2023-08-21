package com.example.pruebatecnicawhiz.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pruebatecnicawhiz.databinding.PokemonItemBinding
import com.example.pruebatecnicawhiz.home.core.BaseViewHolder
import com.example.pruebatecnicawhiz.home.data.model.Pokemon

class PokemonAdapter(
    private val itemClickListener: OnPokemonClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private val pokemonList: ArrayList<Pokemon> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = PokemonsViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onPokemonClick(pokemonList[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is PokemonsViewHolder -> holder.bind(pokemonList[position])
        }
    }

    override fun getItemCount(): Int = pokemonList.size

    fun updateData(newData: List<Pokemon>) {
        pokemonList.clear()
        pokemonList.addAll(newData)
        notifyDataSetChanged()
    }

    private inner class PokemonsViewHolder(
        val binding: PokemonItemBinding,
        val context: Context
    ) : BaseViewHolder<Pokemon>(binding.root) {
        override fun bind(item: Pokemon) {
            binding.tvPokemonName.text = item.name
        }
    }

    interface OnPokemonClickListener {
        fun onPokemonClick(pokemon: Pokemon)
    }
}