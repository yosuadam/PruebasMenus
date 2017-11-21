package com.example.yosua.pruebasmenus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ItemImagen> listaItems;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaItems = new ArrayList<ItemImagen>();
        cargarLista();

        AdaptadorItemImagen adaptador = new AdaptadorItemImagen(this);
        listView = (ListView) findViewById(R.id.lvwImagenes);
        listView.setAdapter(adaptador);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, MostrarImagen.class);
                intent.putExtra("idImagen", listaItems.get(i).getIdImagen());
                intent.putExtra("tituloImagen", listaItems.get(i).getTituloImagen());
                startActivity(intent);
            }
        });
    }


    private void cargarLista () {
        listaItems.add(new ItemImagen("CapitanAmerica_01", R.mipmap.ic_capitanamerica, R.drawable.i001_capitanamerica));
        listaItems.add(new ItemImagen("JuegoDeTronos_01", R.mipmap.ic_jdt, R.drawable.i002_jdt));
        listaItems.add(new ItemImagen("Pikachu_01", R.mipmap.ic_pikachu, R.drawable.i003_pikachu));
        //listaItems.add(new ItemImagen("Rara_01", R.mipmap.ic_rara1, R.drawable.i004_rara1));
        listaItems.add(new ItemImagen("Rara_02", R.mipmap.ic_rara2, R.drawable.i005_rara2));
        listaItems.add(new ItemImagen("StarWars_01", R.mipmap.ic_starwars, R.drawable.i006_starwars));
        listaItems.add(new ItemImagen("Tren", R.mipmap.ic_tren, R.drawable.i007_tren));
        listaItems.add(new ItemImagen("Mujer", R.mipmap.ic_mujer, R.drawable.i008_mujer));
        listaItems.add(new ItemImagen("Planeta", R.mipmap.ic_planeta, R.drawable.i009_planeta));
        listaItems.add(new ItemImagen("Paisaje_01", R.mipmap.ic_paisaje1, R.drawable.i010_paisaje));
        listaItems.add(new ItemImagen("Anime", R.mipmap.ic_anime, R.drawable.i011_anime));

    }


    class AdaptadorItemImagen extends ArrayAdapter<ItemImagen> {
        AppCompatActivity appCompatActivity;

        AdaptadorItemImagen (AppCompatActivity context) {
            super(context, R.layout.lvw_item_imagen, listaItems);
            appCompatActivity = context;
        }

        public View getView (int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.lvw_item_imagen, null);

            TextView textView = (TextView) item.findViewById(R.id.textView);
            textView.setText(listaItems.get(position).getTituloImagen());

            ImageView imageView = (ImageView) item.findViewById(R.id.imageView);
            imageView.setImageResource(listaItems.get(position).getIdIcono());

            return(item);
        }
    }
}
