package com.example.pruebatecnicawhiz.home.repository

import com.example.pruebatecnicawhiz.home.data.model.Pokemon
import com.example.pruebatecnicawhiz.home.data.model.PokemonSpecies
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend  fun getPokemonListRemote()
    suspend  fun insertPokemon(pokemon: Pokemon)
    fun getPokemonListRemoteLocal(): Flow<List<Pokemon>>
    suspend fun getPokemonInfoByName(name: String): PokemonSpecies
    suspend fun getNamesEvolutionPokemon(url: String): List<String>
    suspend fun getPokemonAbilities(name: String): List<String>
}