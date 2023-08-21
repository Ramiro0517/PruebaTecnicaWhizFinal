package com.example.pruebatecnicawhiz.home.ui.pokemonEvolutionary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebatecnicawhiz.R
import com.example.pruebatecnicawhiz.databinding.FragmentEvolutionaryLineBinding
import com.example.pruebatecnicawhiz.home.adapters.SimpleStringAdapter
import com.example.pruebatecnicawhiz.home.core.Resource
import com.example.pruebatecnicawhiz.home.data.remote.PokemonDataSource
import com.example.pruebatecnicawhiz.home.data.service.RetrofitClient
import com.example.pruebatecnicawhiz.home.repository.PokemonRepositoryImpl
import com.example.pruebatecnicawhiz.home.viewmodel.PokemonViewModel
import com.example.pruebatecnicawhiz.home.viewmodel.PokemonViewModelFactory


class PokemonEvolutionaryLineFragment : Fragment(R.layout.fragment_evolutionary_line) {

    private lateinit var binding: FragmentEvolutionaryLineBinding
    private lateinit var urlEvolutionChain: String

    private val viewModel by viewModels<PokemonViewModel> {
        PokemonViewModelFactory(
            PokemonRepositoryImpl(
                PokemonDataSource(RetrofitClient.webservice)
            )
        )
    }

    private val adapter by lazy {
        SimpleStringAdapter { clickedString ->
            // Manejar el clic en un elemento aquí, por ejemplo:
            Toast.makeText(requireContext(), "Clicked: $clickedString", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            urlEvolutionChain = it.getString("urlEvolutionChain").toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEvolutionaryLineBinding.bind(view)

        binding.rvPokemonEvolutionaryLine.layoutManager = LinearLayoutManager(context)
        binding.rvPokemonEvolutionaryLine.adapter = adapter

        viewModel.getNamesEvolutionPokemon(urlEvolutionChain).observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    Log.d("Livedata", "Success ${it.data}")
                    binding.progressBar.visibility = View.GONE
                    adapter.updateData(it.data)

                }

                is Resource.Failure -> {
                    Log.d("Error", "Failure ${it.exception}")
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

}