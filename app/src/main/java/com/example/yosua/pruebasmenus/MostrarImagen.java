package com.example.yosua.pruebasmenus;

import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MostrarImagen extends AppCompatActivity {

    private ImageView imageView;
    private int idImagen;
    private String tituloImagen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_imagen);

        imageView = (ImageView)findViewById(R.id.imageView2);

        Bundle bundle = getIntent().getExtras();
        idImagen = bundle.getInt("idImagen");
        tituloImagen = bundle.getString("tituloImagen");
        imageView.setImageResource(idImagen);

        registerForContextMenu(imageView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Opciones");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucontextual, menu);
    }

    @Override
    public boolean onContextItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemGuardarImagen:
                guardarImagenEnGaleria();
                break;
            case R.id.itemEstablecerFondo:
                establecerFondoPantalla();
                break;
            case R.id.itemCompartirImagen:
                compartirImagen();
                break;
        }

        return true;
    }

    //PERMISOS AÑADIDOS EN EL MANIFEST
    private void establecerFondoPantalla () {
        WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
        try {
            wm.setResource(idImagen);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast toast = Toast.makeText(this, "Fondo establecido", Toast.LENGTH_SHORT);
        toast.show();
    }

    //PERMISOS AÑADIDOS EN EL MANIFEST
    private void compartirImagen () {
        //Creamos un bitmap que sea la imagen que vamos a compartir
        Bitmap imagen = BitmapFactory.decodeResource(getResources(), idImagen);
        //Creamos un nuevo intent que va a ser el que realice la acción de enviar el archivo
        Intent intentCompartir = new Intent(Intent.ACTION_SEND);
        intentCompartir.setType("image/jpeg");

        //Creamos un contenedor que contenga todos los datos de la imagen
        ContentValues contenedorDatos = new ContentValues();
        contenedorDatos.put(MediaStore.Images.Media.TITLE, tituloImagen);
        contenedorDatos.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contenedorDatos);

        //Realizamos la compersión de la imagen y le asignamos un formato
        OutputStream os;
        try {
            os = getContentResolver().openOutputStream(uri);
            imagen.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        intentCompartir.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intentCompartir, "Compatir imagen"));
    }

    private void guardarImagenEnGaleria() {
        //Creamos un bitmap que sea la imagen que vamos a compartir
        Bitmap imagen = BitmapFactory.decodeResource(getResources(), idImagen);

        //Obtentemos el directorio root de donde colgará nuestro directorio para guardar las imágenes
        String root = Environment.getExternalStorageDirectory().toString();

        //Creamos el directorio
        File directorio = new File(root + "/pictures/carpeta_pruebas");
        directorio.mkdirs();

        //Establecemos el nombre del archivo
        String nombreArchivo = tituloImagen +".jpg";

        //Creamos el fichero en la ruta y con el nombre definidos anteriormente
        File fichero = new File (directorio, nombreArchivo);

        //Si el fichero existe lo borramos para sobreescribirlo
        if (fichero.exists ()) {
            fichero.delete ();
        }

        //Realizamos la compresión de la imagen y la almacenamos
        try {
            FileOutputStream out = new FileOutputStream(fichero);
            imagen.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast toast = Toast.makeText(this, "Imagen guardada", Toast.LENGTH_SHORT);
        toast.show();
    }

}
