package com.example.pruebatecnicawhiz.home.ui.pokemon

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebatecnicawhiz.R
import com.example.pruebatecnicawhiz.databinding.FragmentPokemonBinding
import com.example.pruebatecnicawhiz.home.adapters.PokemonAdapter
import com.example.pruebatecnicawhiz.home.core.Resource
import com.example.pruebatecnicawhiz.home.data.model.Pokemon
import com.example.pruebatecnicawhiz.home.data.remote.PokemonDataSource
import com.example.pruebatecnicawhiz.home.data.service.RetrofitClient
import com.example.pruebatecnicawhiz.home.repository.PokemonRepositoryImpl
import com.example.pruebatecnicawhiz.home.viewmodel.PokemonViewModel
import com.example.pruebatecnicawhiz.home.viewmodel.PokemonViewModelFactory


class PokemonFragment : Fragment(R.layout.fragment_pokemon) {

    private lateinit var binding: FragmentPokemonBinding

    private val viewModel by viewModels<PokemonViewModel> { PokemonViewModelFactory(PokemonRepositoryImpl(
        PokemonDataSource(RetrofitClient.webservice)
    )) }

    private val pokemonAdapter: PokemonAdapter by lazy {
        PokemonAdapter(
            itemClickListener = object : PokemonAdapter.OnPokemonClickListener {
                override fun onPokemonClick(pokemon: Pokemon) {
                    val bundle = bundleOf("pokemonName" to pokemon.name)
                    findNavController().navigate(R.id.action_pokemonFragment_to_pokemonSpeciesFragment, bundle)
                }
            }
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPokemonBinding.bind(view)

        binding.rvPokemons.layoutManager = LinearLayoutManager(context)
        binding.rvPokemons.adapter = pokemonAdapter

        viewModel.fetchPokemonList().observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d("Livedata","Loading")
                }
                is Resource.Success -> {
                    Log.d("Livedata","Success ${it.data}")
                    binding.progressBar.visibility = View.GONE
                   // binding.tvPokemon.text = it.data.results[0].name
                }
                is Resource.Failure -> {
                    Log.d("Error","Failure ${it.exception}")
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

        viewModel.pokemons.observe(viewLifecycleOwner, { pokemons ->
            pokemonAdapter.updateData(pokemons)
        })
    }

}