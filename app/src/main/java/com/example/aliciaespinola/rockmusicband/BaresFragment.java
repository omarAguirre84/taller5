package com.example.aliciaespinola.rockmusicband;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class BaresFragment extends Fragment {
    public EditText et_nombreBar, et_direccionBar, et_horarioBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vistaBares= inflater.inflate(R.layout.fragment_bares, container, false);

        et_nombreBar = (EditText)vistaBares.findViewById(R.id.txt_nombreBar);
        et_direccionBar = (EditText)vistaBares.findViewById(R.id.txt_direccion);
        et_horarioBar = (EditText)vistaBares.findViewById(R.id.txt_horarios);

        return vistaBares;
    }
}
