package com.example.roompokemon.dao;

import com.example.roompokemon.entity.Pokemon;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PokemonDao {

    @Delete
    int delete(Pokemon pokemon);

    @Update
    int edit(Pokemon pokemon);

    @Insert
    long insert(Pokemon pokemon);

    @Query("select * from pokemon where id = :id")
    Pokemon get(long id);

    @Query("select * from pokemon order by tipo desc")
    List<Pokemon> getAll();

    @Query("select * from pokemon order by tipo desc")
    LiveData<List<Pokemon>> getAllLive();
}
