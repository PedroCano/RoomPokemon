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
import com.example.roompokemon.entity.Pokemon;
import com.example.roompokemon.view.MainViewModel;
import com.example.roompokemon.view.PokemonAdapter;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private MainViewModel viewModel;
    private static final int PHOTO_SELECTED = 1;
    private ImageView ivImagen;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initComponentes();
    }

    private void initComponentes() {
        final EditText etNombre = findViewById(R.id.etNombre);
        final EditText etTipo = findViewById(R.id.etTipo);
        Button btInserta = findViewById(R.id.btInserta);
        ivImagen = findViewById(R.id.ivImagen);

        viewModel = MainActivity.viewModel;

        btInserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                //intent.putExtra(NOMBRE, etNombre.getText().toString());
                //intent.putExtra(TIPO, etTipo.getText().toString());
                if(etNombre.getText().toString().equalsIgnoreCase("") || etTipo.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(Main2Activity.this,"Inserte los campos requeridos", Toast.LENGTH_SHORT).show();
                }else{
                    Pokemon pokemon= new Pokemon();
                    pokemon.setNombre(etNombre.getText().toString());
                    pokemon.setTipo(etTipo.getText().toString());
                    pokemon.setFoto(imageUri.toString());
                    viewModel.insert(pokemon);
                    startActivity(intent);
                }
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
