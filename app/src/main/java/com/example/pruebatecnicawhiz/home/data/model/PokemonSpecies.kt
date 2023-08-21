package com.example.pruebatecnicawhiz.home.data.model

import com.google.gson.annotations.SerializedName

data class PokemonSpecies(
    @SerializedName("base_happiness")
    val base_happiness: Int,

    @SerializedName("capture_rate")
    val capture_rate: Int,

    @SerializedName("color")
    val color: ColorPokemon,

    @SerializedName("egg_groups")
    val egg_groups: List<EggGroup>,

    @SerializedName("evolution_chain")
    val evolution_chain: EvolutionChainPokemon
)

data class ColorPokemon(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
)

data class EggGroup(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
)

data class EvolutionChainPokemon(

    @SerializedName("url")
    val url: String
)
