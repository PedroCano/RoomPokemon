package com.example.roompokemon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.roompokemon.entity.Pokemon;
import com.example.roompokemon.view.MainViewModel;
import com.example.roompokemon.view.PokemonAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static MainViewModel viewModel;
    private static final int PHOTO_SELECTED = 1;
    private ImageView ivFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ivFoto = findViewById(R.id.ivFoto);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        RecyclerView rvList = findViewById(R.id.rvLista);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        final PokemonAdapter adapter = new PokemonAdapter(this);
        rvList.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getPokemons().observe(this, new Observer<List<Pokemon>>() { //observe es para que si hacemos un cambio (inertar, borrar..) se actualiza la lista
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                adapter.setPokemons(pokemons);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.ordenarTipo) {
            viewModel.ordenar();
            return true;
        }

        if (id == R.id.ordenarNombre) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SELECTED && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            Glide.with(this)
                    .load(imageUri)
                    .override(500, 500)// prueba de escalado
                    .into(ivFoto);
        }
    }
}
