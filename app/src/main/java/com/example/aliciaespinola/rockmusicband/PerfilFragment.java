package com.example.aliciaespinola.rockmusicband;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.widget.ShareDialog;

import java.io.InputStream;


public class PerfilFragment extends Fragment {
    private ShareDialog shareDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        shareDialog = new ShareDialog(this);

        View vistaPerfil = inflater.inflate(R.layout.fragment_perfil, container, false);

        String nombreBundle = getArguments().getString("name");
        String apellidoBundle = getArguments().getString("surname");
        String urlPerfil = getArguments().getString("imageUrl");

        new PerfilFragment.DownloadImage((ImageView)vistaPerfil.findViewById(R.id.fotoPerfil)).execute(urlPerfil);

        TextView nombre = (TextView) vistaPerfil.findViewById(R.id.nombre);
        TextView apellido = (TextView) vistaPerfil.findViewById(R.id.apellido);

        if(!nombreBundle.isEmpty()){
            nombre.setText(nombreBundle);
            apellido.setText(apellidoBundle);
        }else{
            nombre.setText(" ");
            apellido.setText(" ");
        }

        return  vistaPerfil;
    }

    public class DownloadImage extends AsyncTask<String,Void,Bitmap> {
        ImageView bmImage;

        public DownloadImage(ImageView bmImage){
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls){
            String urldisplay = urls[0];
            Bitmap mIconll = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIconll = BitmapFactory.decodeStream(in);
            }catch (Exception e){
                Log.e("ERROR",e.getMessage());
                e.printStackTrace();
            }
            return mIconll;
        }

        protected void onPostExecute(Bitmap result){
            bmImage.setImageBitmap(result);
        }
    }
}
