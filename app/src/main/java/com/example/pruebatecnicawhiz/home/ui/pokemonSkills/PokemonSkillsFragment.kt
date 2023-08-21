package com.example.pruebatecnicawhiz.home.ui.pokemonSkills

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
import com.example.pruebatecnicawhiz.databinding.FragmentPokemonSkillsBinding
import com.example.pruebatecnicawhiz.home.adapters.SimpleStringAdapter
import com.example.pruebatecnicawhiz.home.core.Resource
import com.example.pruebatecnicawhiz.home.data.remote.PokemonDataSource
import com.example.pruebatecnicawhiz.home.data.service.RetrofitClient
import com.example.pruebatecnicawhiz.home.repository.PokemonRepositoryImpl
import com.example.pruebatecnicawhiz.home.viewmodel.PokemonViewModel
import com.example.pruebatecnicawhiz.home.viewmodel.PokemonViewModelFactory


class PokemonSkillsFragment : Fragment(R.layout.fragment_pokemon_skills) {

    private lateinit var binding: FragmentPokemonSkillsBinding
    private lateinit var pokemonName: String

    private val viewModel by viewModels<PokemonViewModel> {
        PokemonViewModelFactory(
            PokemonRepositoryImpl(
                PokemonDataSource(RetrofitClient.webservice)
            )
        )
    }

    private val adapter by lazy {
        SimpleStringAdapter { clickedString ->
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pokemonName = it.getString("pokemonName").toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPokemonSkillsBinding.bind(view)

        binding.rvPokemonSkills.layoutManager = LinearLayoutManager(context)
        binding.rvPokemonSkills.adapter = adapter

        viewModel.getPokemonAbilities(pokemonName).observe(viewLifecycleOwner, Observer {
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