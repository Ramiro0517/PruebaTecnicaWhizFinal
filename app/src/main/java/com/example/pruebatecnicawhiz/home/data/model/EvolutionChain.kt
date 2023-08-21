package com.example.pruebatecnicawhiz.home.data.model

import com.google.gson.annotations.SerializedName

data class EvolutionChain(
    @SerializedName("chain") val chain: Chain
)

data class Chain(
    @SerializedName("evolves_to") val evolvesTo: List<Evolution>,
    @SerializedName("species") val species: Species
)

data class Evolution(
    @SerializedName("evolves_to") val evolvesTo: List<Evolution>,
    @SerializedName("species") val species: Species
)

data class Species(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
