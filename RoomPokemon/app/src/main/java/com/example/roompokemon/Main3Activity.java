package com.example.roompokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.roompokemon.dao.PokemonDao;
import com.example.roompokemon.entity.Pokemon;
import com.example.roompokemon.view.MainViewModel;
import com.example.roompokemon.view.PokemonAdapter;

import java.util.List;

public class Main3Activity extends AppCompatActivity {

    private MainViewModel viewModel;
    private Pokemon pokemon;
    private EditText etNombre;
    private EditText etTipo;
    private ImageView ivImagen;
    public static final String ID = "id";
    private static final int PHOTO_SELECTED = 1;
    private Uri imageUri;
    private Uri enlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        initComponentes();
    }

    private void initComponentes() {
        etNombre = findViewById(R.id.etNombre);
        etTipo = findViewById(R.id.etTipo);
        Button btEdita = findViewById(R.id.btEdita);
        Button btBorra = findViewById(R.id.btBorra);
        ivImagen = findViewById(R.id.ivImagen);

        viewModel = MainActivity.viewModel;

        Intent intent = getIntent();
        //etNombre.setText(intent.getStringExtra(PokemonAdapter.EDITNOMBRE));
        //etTipo.setText(intent.getStringExtra(PokemonAdapter.EDITTIPO));
        final long id = intent.getLongExtra(ID,0);
        pokemon = viewModel.getPokemon(id);
        etNombre.setText(pokemon.getNombre());
        etTipo.setText(pokemon.getTipo());
        enlace = Uri.parse(pokemon.getFoto());
        Glide.with(Main3Activity.this)
                .load(Uri.parse(pokemon.getFoto()))
                .override(500, 500)// prueba de escalado
                .into(ivImagen);

        btEdita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Main3Activity.this, MainActivity.class);

                /*Pokemon pokemon = viewModel.getPokemon(id);
                pokemon.setNombre(etNombre.getText().toString());
                pokemon.setTipo(etTipo.getText().toString());*/

                editar();

                viewModel.edit(pokemon);
                startActivity(intent2);

            }
        });

        btBorra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Main3Activity.this, MainActivity.class);
                
                viewModel.delete(pokemon);
                startActivity(intent2);

            }
        });

        ivImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PHOTO_SELECTED);
            }
        });

    }

    private void editar() {
        pokemon.setNombre(etNombre.getText().toString());
        pokemon.setTipo(etTipo.getText().toString());
        if(imageUri == null){
            pokemon.setFoto(String.valueOf(enlace));
        }else{
            pokemon.setFoto(String.valueOf(imageUri));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SELECTED && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            Glide.with(this)
                    .load(imageUri)
                    .override(500, 500)// prueba de escalado
                    .into(ivImagen);
        }
    }


}
