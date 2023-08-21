package com.example.pruebatecnicawhiz.home.ui.pokemonSpecies

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.pruebatecnicawhiz.R
import com.example.pruebatecnicawhiz.databinding.FragmentPokemonSpeciesBinding
import com.example.pruebatecnicawhiz.home.core.Resource
import com.example.pruebatecnicawhiz.home.data.remote.PokemonDataSource
import com.example.pruebatecnicawhiz.home.data.service.RetrofitClient
import com.example.pruebatecnicawhiz.home.repository.PokemonRepositoryImpl
import com.example.pruebatecnicawhiz.home.viewmodel.PokemonViewModel
import com.example.pruebatecnicawhiz.home.viewmodel.PokemonViewModelFactory

class PokemonSpeciesFragment : Fragment(R.layout.fragment_pokemon_species) {

    private lateinit var binding: FragmentPokemonSpeciesBinding
    private lateinit var pokemonName: String

    private var urlEvolutionChain: String? = null

    private val viewModel by viewModels<PokemonViewModel> {
        PokemonViewModelFactory(
            PokemonRepositoryImpl(
                PokemonDataSource(RetrofitClient.webservice)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pokemonName = it.getString("pokemonName").toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPokemonSpeciesBinding.bind(view)

        viewModel.getPokemonInfoByName(pokemonName).observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    Log.d("Livedata", "Success ${it.data}")
                    binding.progressBar.visibility = View.GONE

                    binding.tvPokemonName.text = pokemonName.toUpperCase()
                    binding.tvPokemonSpeciesValue.text = it.data.base_happiness.toString()
                    binding.tvPokemonCapturaValue.text = it.data.capture_rate.toString()
                    binding.tvPokemonColorValue.text = it.data.color.name

                    val arrayStringGroups = it.data.egg_groups.map { it.name }.toTypedArray()
                        .joinToString(separator = ",")
                    binding.tvPokemonGroupOfEggsValue.text = arrayStringGroups

                    urlEvolutionChain = it.data.evolution_chain.url

                }

                is Resource.Failure -> {
                    Log.d("Error", "Failure ${it.exception}")
                    binding.progressBar.visibility = View.GONE
                }
            }
        })

        binding.btnLineaEvolutiva.setOnClickListener {
            val bundle = bundleOf("urlEvolutionChain" to urlEvolutionChain)
            findNavController().navigate(R.id.action_pokemonSpeciesFragment_to_pokemonEvolutionaryLineFragment, bundle)
        }

        binding.btnHabilidades.setOnClickListener {
            val bundle = bundleOf("pokemonName" to pokemonName)
            findNavController().navigate(R.id.action_pokemonSpeciesFragment_to_pokemonSkillsFragment, bundle)
        }
    }
}