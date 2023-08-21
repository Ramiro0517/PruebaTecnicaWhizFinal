package com.example.pruebatecnicawhiz.home

import android.app.Application
import com.example.pruebatecnicawhiz.home.data.local.database.PokemonDatabase

class PokemonApp : Application() {

    companion object {
        lateinit var database: PokemonDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = PokemonDatabase.getDatabase(this)
    }
}