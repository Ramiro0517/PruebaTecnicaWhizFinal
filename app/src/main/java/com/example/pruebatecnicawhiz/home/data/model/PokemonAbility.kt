package com.example.pruebatecnicawhiz.home.data.model

import com.google.gson.annotations.SerializedName

data class PokemonAbility (
    @SerializedName("abilities")
    val abilities: List<AbilityPok>
)

data class AbilityPok(
    @SerializedName("ability")
    val ability: Ability
)

data class Ability(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)