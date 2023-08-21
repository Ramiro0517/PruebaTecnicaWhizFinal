package com.example.pruebatecnicawhiz.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.pruebatecnicawhiz.home.core.Resource
import com.example.pruebatecnicawhiz.home.data.model.Pokemon
import com.example.pruebatecnicawhiz.home.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers

class PokemonViewModel(private val repo: PokemonRepository):ViewModel() {

    val pokemons: LiveData<List<Pokemon>> = repo.getPokemonListRemoteLocal().asLiveData()

    fun fetchPokemonList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getPokemonListRemote()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun getPokemonInfoByName(name: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getPokemonInfoByName(name)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun getNamesEvolutionPokemon(url: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getNamesEvolutionPokemon(url)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun getPokemonAbilities(name: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getPokemonAbilities(name)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class PokemonViewModelFactory(private val repo: PokemonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PokemonRepository::class.java).newInstance(repo)
    }
}



