package com.example.roompokemon.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.roompokemon.Main2Activity;
import com.example.roompokemon.Main3Activity;
import com.example.roompokemon.MainActivity;
import com.example.roompokemon.R;
import com.example.roompokemon.entity.Pokemon;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>{

    private LayoutInflater inflater;
    private List<Pokemon> pokemonList;
    private int contador = 0;
    public static final String EDITNOMBRE="nombre";
    public static final String EDITTIPO="tipo";
    public static final String ID = "id";
    private MainViewModel viewModel;
    private Context context;

    public PokemonAdapter(Context context){ //Context necesario para obtener el objeto LayoutInflater que es con el cual puedo darle forma a los layouts de Item al poblar el RecyclerView
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v("xyzyx","onCreateViewHolder" + contador);
        contador++;
        View itemView = inflater.inflate(R.layout.item, parent,false);
        return new PokemonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PokemonAdapter.PokemonViewHolder holder, final int position) {
        Log.v("xyzyx","onBindViewHolder" + contador);
        if (pokemonList != null) {
            final Pokemon current = pokemonList.get(position);
            holder.tvNombre.setText(current.getNombre());
            holder.tvTipo.setText(current.getTipo());

            Glide.with(context)
                    .load(Uri.parse(current.getFoto()))
                    .override(500, 500)// prueba de escalado
                    .into(holder.ivFoto);

            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Main3Activity.class);
                    intent.putExtra(ID, current.getId());
                    v.getContext().startActivity(intent);
                }
            });

        } else {
            // En caso de que no haya elementos
            holder.tvNombre.setText("No pokemon available");
        }

    }

    @Override
    public int getItemCount() {
        Log.v("xyzyx","getItemCount" + contador);
        int elementos = 0;
        if (pokemonList != null) {
            elementos = pokemonList.size();
        }
        return elementos;
    }

    public void setPokemons(List<Pokemon> userList){
        this.pokemonList = userList;
        notifyDataSetChanged(); //se utiliza para saber si hay cambios para que se actualice
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNombre;
        private final TextView tvTipo;
        private final ImageView ivFoto;
        private CardView cv;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            cv = itemView.findViewById(R.id.cv);
        }
    }

}
