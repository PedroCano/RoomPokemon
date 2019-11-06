package com.example.roompokemon.model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.roompokemon.dao.PokemonDao;
import com.example.roompokemon.database.PokemonDatabase;
import com.example.roompokemon.entity.Pokemon;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class Repository {

    private PokemonDao pokemonDao;
    private LiveData<List<Pokemon>> pokemonsLive;

    public Repository(Context contexto){
        PokemonDatabase db = PokemonDatabase.getDatabase(contexto);
        pokemonDao = db.getPokemonDao();
        pokemonsLive = pokemonDao.getAllLive();
    }

    public void populateDb(){
        for (int i = 0; i < 100; i++) {
           /* Pokemon pokemon = new Pokemon();
            pokemon.setNombre("Gyarados "+i);
            pokemon.setTipo("Agua "+i);
            insertPokemon(pokemon);*/
        }
    }

    public LiveData<List<Pokemon>> getPokemonsLive(){
        return pokemonsLive;
    }

    public void insertPokemon(Pokemon pokemon){
        new InsertThread().execute(pokemon);
    }

    public void deletePokemon(Pokemon pokemon){
        new DeleteThread().execute(pokemon);
    }

    public void editPokemon(Pokemon pokemon){
        new EditThread().execute(pokemon);
    }

    public Pokemon getPokemon(long id){
        try {
            return new GetThread().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class InsertThread extends AsyncTask<Pokemon, Void, Void> {
        @Override
        protected Void doInBackground(Pokemon... pokemons) {
            pokemonDao.insert(pokemons[0]);
            Log.v("xyz", pokemons[0].toString());
            return null;
        }
    }

    private class DeleteThread extends AsyncTask<Pokemon, Void, Void> {
        @Override
        protected Void doInBackground(Pokemon... pokemons) {
            pokemonDao.delete(pokemons[0]);
            Log.v("xyz", pokemons[0].toString());
            return null;
        }
    }

    private class EditThread extends AsyncTask<Pokemon, Void, Void> {
        @Override
        protected Void doInBackground(Pokemon... pokemons) {
            pokemonDao.edit(pokemons[0]);
            Log.v("xyz", pokemons[0].toString());
            return null;
        }
    }

    private class GetThread extends AsyncTask<Long, Void, Pokemon> {
        @Override
        protected Pokemon doInBackground(Long... id) {
            return pokemonDao.get(id[0]);
        }
    }

}
