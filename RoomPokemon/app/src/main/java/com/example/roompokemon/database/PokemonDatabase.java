package com.example.roompokemon.database;

import android.content.Context;

import com.example.roompokemon.dao.PokemonDao;
import com.example.roompokemon.entity.Pokemon;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Pokemon.class}, version = 1, exportSchema = false)
public abstract class PokemonDatabase extends RoomDatabase {

    public abstract PokemonDao getPokemonDao();

    private static volatile PokemonDatabase INSTANCIA; //accede a la posición real en la memoria y no a la caché

    //Synchroniced significa que no empieza la siguiente hebra hasta que no termine la anterior
    public static PokemonDatabase getDatabase(final Context context) {//Devuelve el objeto db con el que podremos acceder a la base de datos
        if (INSTANCIA == null) {
            synchronized (PokemonDatabase.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context.getApplicationContext(),
                            PokemonDatabase.class, "pokemon.sqlite")
                            .build();
                }
            }
        }
        return INSTANCIA;
    }
}
