package com.example.pruebatecnicawhiz.home.data.remote

import com.example.pruebatecnicawhiz.home.constants.AppConstants
import com.example.pruebatecnicawhiz.home.data.model.Pokemon
import com.example.pruebatecnicawhiz.home.data.model.PokemonList
import com.example.pruebatecnicawhiz.home.data.model.PokemonSpecies
import com.example.pruebatecnicawhiz.home.data.service.WebService

class PokemonDataSource(private val webService: WebService) {

    suspend fun getPokemonList():PokemonList {
       return webService.getPokemonList(AppConstants.LIMIT)
    }

    suspend fun getPokemonInfoByName(name: String): PokemonSpecies {
        return webService.getPokemonByName(name)
    }

    suspend fun getNamesEvolutionPokemon(url: String) =  webService.getEvolutionChainWithDynamicUrl(url)

    suspend fun getPokemonAbilities(name: String) =  webService.getPokemonAbilities(name)

}