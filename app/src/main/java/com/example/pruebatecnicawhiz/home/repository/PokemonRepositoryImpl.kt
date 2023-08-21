package com.example.pruebatecnicawhiz.home.repository

import com.example.pruebatecnicawhiz.home.PokemonApp
import com.example.pruebatecnicawhiz.home.data.mapper.PokemonMapper.toEntity
import com.example.pruebatecnicawhiz.home.data.mapper.PokemonMapper.toModel
import com.example.pruebatecnicawhiz.home.data.model.Chain
import com.example.pruebatecnicawhiz.home.data.model.Evolution
import com.example.pruebatecnicawhiz.home.data.model.Pokemon
import com.example.pruebatecnicawhiz.home.data.model.PokemonSpecies
import com.example.pruebatecnicawhiz.home.data.remote.PokemonDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepositoryImpl(private val dataSource: PokemonDataSource) : PokemonRepository {

    private val pokemonDao = PokemonApp.database.pokemonDao()

    override suspend fun getPokemonListRemote() {
        val pokemonList = dataSource.getPokemonList()
        pokemonList.results.map {
            insertPokemon(it)
        }
    }

    override suspend fun insertPokemon(pokemon: Pokemon) = pokemonDao.insert(pokemon.toEntity())

    override fun getPokemonListRemoteLocal(): Flow<List<Pokemon>> {
        return pokemonDao.getAllPokemon().map { entities ->
            entities.map { it.toModel()}
        }
    }

    override suspend fun getPokemonInfoByName(name: String) = dataSource.getPokemonInfoByName(name)

    override suspend fun getNamesEvolutionPokemon(url: String): List<String> {
        val evolutionChain = dataSource.getNamesEvolutionPokemon(url)
        val pokemonNames = mutableListOf<String>()
        pokemonNames.add(evolutionChain.chain.species.name)
        evolutionChain.chain.evolvesTo.forEach { evolution ->
            extractNames(evolution, pokemonNames)
        }

        return pokemonNames
    }

    override suspend fun getPokemonAbilities(name: String): List<String> {
        val pokemonAbilities = dataSource.getPokemonAbilities(name)
        val abilityNames = pokemonAbilities.abilities.map { it.ability.name }
        return abilityNames
    }

    private fun extractNames(evolution: Evolution?, pokemonNames: MutableList<String>) {
        if (evolution == null) return

        pokemonNames.add(evolution.species.name)
        evolution.evolvesTo.forEach { nextEvolution ->
            extractNames(nextEvolution, pokemonNames)
        }
    }

}