package com.example.pruebatecnicawhiz.home.data.mapper

import com.example.pruebatecnicawhiz.home.data.local.entities.PokemonEntity
import com.example.pruebatecnicawhiz.home.data.model.Pokemon

object PokemonMapper {
    fun Pokemon.toEntity(): PokemonEntity {
        return PokemonEntity(
            name = name,
            url = url
        )
    }

    fun PokemonEntity.toModel(): Pokemon {
        return Pokemon(
            name = name,
            url = url
        )
    }
}
