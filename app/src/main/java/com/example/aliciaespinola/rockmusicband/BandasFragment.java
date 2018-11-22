package com.example.aliciaespinola.rockmusicband;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import static com.facebook.FacebookSdk.getApplicationContext;


public class BandasFragment extends Fragment  {

    public EditText et_codigo, et_nombre, et_genero, et_descripcion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vistaBandas = inflater.inflate(R.layout.fragment_bandas, container, false);

        et_codigo = (EditText)vistaBandas.findViewById(R.id.txt_codigo);
        et_nombre = (EditText)vistaBandas.findViewById(R.id.txt_nombre);
        et_genero = (EditText)vistaBandas.findViewById(R.id.txt_genero);
        et_descripcion = (EditText)vistaBandas.findViewById(R.id.txt_descripcion);

        return vistaBandas;
    }
}