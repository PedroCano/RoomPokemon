package com.example.roompokemon.view;

import android.app.Application;

import com.example.roompokemon.entity.Pokemon;
import com.example.roompokemon.model.Repository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;

    private LiveData<List<Pokemon>> pokemons;
    private Pokemon pokemon;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        pokemons = repository.getPokemonsLive();
    }

    public LiveData<List<Pokemon>> getPokemons(){
        return pokemons;
    }

    public void insert(Pokemon pokemon){
        repository.insertPokemon(pokemon);
    }

    public void delete(Pokemon pokemon){
        repository.deletePokemon(pokemon);
    }

    public void edit(Pokemon pokemon){
        repository.editPokemon(pokemon);

    }

    public Pokemon getPokemon(long id){
        return repository.getPokemon(id);
    }

    public void ordenar(){
        repository.getPokemonsLive();
    }
}
