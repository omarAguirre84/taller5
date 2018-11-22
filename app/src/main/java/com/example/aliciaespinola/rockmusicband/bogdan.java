package com.example.aliciaespinola.rockmusicband;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.facebook.share.widget.ShareDialog;

public class bogdan extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ShareDialog shareDialog;
    private Button logout;
    private PerfilFragment pf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bogdan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shareDialog = new ShareDialog(this);

        Bundle inBundle = getIntent().getExtras();
        String name = inBundle.get("name").toString();
        String surname = inBundle.get("surname").toString();
        String imageUrl = inBundle.get("imageUrl").toString();

        Bundle inBundle2 = new Bundle();
        inBundle2.putString("name",name);
        inBundle2.putString("surname",surname);
        inBundle2.putString("imageUrl",imageUrl);
        pf =  new PerfilFragment();
        pf.setArguments(inBundle2);

        /*Button logout = (Button)findViewById(R.id.Logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent login  = new Intent(bogdan.this, MainActivity.class);
                startActivity(login);
                finish();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor, pf).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bogdan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Manejar los clics del elemento de la barra de acción aquí. La barra de acción
        // Manejar automáticamente los clics en el botón Inicio / Arriba, durante tanto tiempo.
        // a medida que especifique una actividad principal en AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_camera) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new ListadoFragment()).commit();
        } else if (id == R.id.nav_gallery) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new BaresFragment()).commit();
        } else if (id == R.id.nav_slideshow) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, new BandasFragment()).commit();
        } else if (id == R.id.nav_perfil) {
            fragmentManager.beginTransaction().replace(R.id.contenedor, pf).commit();
        } else if (id == R.id.nav_salir) {
            LoginManager.getInstance().logOut();
            Intent login  = new Intent(bogdan.this, MainActivity.class);
            startActivity(login);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Méotdo para dar de alta Bandas

    public void Registrar(View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        BandasFragment fragment = (BandasFragment) getSupportFragmentManager().findFragmentById(R.id.contenedor);
        String codigo = fragment.et_codigo.getText().toString();
        String nombre = fragment.et_nombre.getText().toString();
        String genero = fragment.et_genero.getText().toString();
        String descripcion = fragment.et_descripcion.getText().toString();

        if(!codigo.isEmpty()&& !nombre.isEmpty()&& !genero.isEmpty() && !descripcion.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("genero", genero);
            registro.put("descripcion", descripcion);


            BaseDeDatos.insert("banda", null, registro);

            BaseDeDatos.close();

            fragment.et_codigo.setText("");
            fragment.et_nombre.setText("");
            fragment.et_genero.setText("");
            fragment.et_descripcion.setText("");


            Toast.makeText(this,"Se Registro La Banda " + nombre , Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this,"Debes llenar todos los campos",Toast.LENGTH_SHORT).show();
        }
    }
    //Método para consultar una banda

    public void Buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

        BandasFragment fragment = (BandasFragment) getSupportFragmentManager().findFragmentById(R.id.contenedor);

        String codigo = fragment.et_codigo.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila = BaseDeDatabase.rawQuery
                    ("select nombre, genero, descripcion from banda where codigo =" + codigo, null);

            if(fila.moveToFirst()){
                fragment.et_nombre.setText(fila.getString(0));
                fragment.et_genero.setText(fila.getString(1));
                fragment.et_descripcion.setText(fila.getString(2));

                BaseDeDatabase.close();
            } else {
                Toast.makeText(this,"No existe la banda", Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
            }

        } else {
            Toast.makeText(this, "Debes introducir el código de la banda", Toast.LENGTH_SHORT).show();
        }
    }
    //Método para eliminar una banda

    public void Eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper
                (this, "administracion", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        BandasFragment fragment = (BandasFragment) getSupportFragmentManager().findFragmentById(R.id.contenedor);

        String codigo = fragment.et_codigo.getText().toString();

        if(!codigo.isEmpty()){

            int cantidad = BaseDatabase.delete("banda", "codigo=" + codigo, null);
            BaseDatabase.close();

            fragment.et_codigo.setText("");
            fragment.et_nombre.setText("");
            fragment.et_genero.setText("");
            fragment.et_descripcion.setText("");

            if(cantidad == 1){
                Toast.makeText(this, "Banda eliminada exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "La banda no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Debes de introducir el código de la banda", Toast.LENGTH_SHORT).show();
        }
    }
    //Método para modificar un artículo o producto

    public void Modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        BandasFragment fragment = (BandasFragment) getSupportFragmentManager().findFragmentById(R.id.contenedor);

        String codigo = fragment.et_codigo.getText().toString();
        String nombre = fragment.et_nombre.getText().toString();
        String genero = fragment.et_genero.getText().toString();
        String descripcion = fragment.et_descripcion.getText().toString();


        if(!codigo.isEmpty() && !nombre.isEmpty() && !genero.isEmpty() && !descripcion.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("genero", genero);
            registro.put("descripcion", descripcion);

            int cantidad = BaseDatabase.update("banda", registro, "codigo=" + codigo, null);
            BaseDatabase.close();

            if(cantidad == 1){
                Toast.makeText(this, "Banda modificada correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "La banda no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    //Méotdo para dar de alta Bares

    public void RegistrarBar(View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        BaresFragment fragment = (BaresFragment) getSupportFragmentManager().findFragmentById(R.id.contenedor);

        String nombreBar = fragment.et_nombreBar.getText().toString();
        String direccion = fragment.et_direccionBar.getText().toString();
        String horarios = fragment.et_horarioBar.getText().toString();

        if(!nombreBar.isEmpty()&& !direccion.isEmpty() && !horarios.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("nombreBar", nombreBar);
            registro.put("direccion", direccion);
            registro.put("horario", horarios);


            BaseDeDatos.insert("bares", null, registro);

            BaseDeDatos.close();

            fragment.et_nombreBar.setText("");
            fragment.et_direccionBar.setText("");
            fragment.et_horarioBar.setText("");


            Toast.makeText(this,"Se Registro el Bar " + nombreBar , Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this,"Debes llenar todos los campos",Toast.LENGTH_SHORT).show();
        }
    }
    //Método para consultar un bar

    public void BuscarBar(View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

        BaresFragment fragment = (BaresFragment) getSupportFragmentManager().findFragmentById(R.id.contenedor);

        String nombreBar = fragment.et_nombreBar.getText().toString();

        if(!nombreBar.isEmpty()){
            Cursor fila = BaseDeDatabase.rawQuery
                    ("select direccion, horario from bares where nombreBar =" + "'"+nombreBar+"'", null);

            if(fila.moveToFirst()){
                fragment.et_direccionBar.setText(fila.getString(0));
                fragment.et_horarioBar.setText(fila.getString(1));

                BaseDeDatabase.close();
            } else {
                Toast.makeText(this,"No existe el bar", Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
            }

        } else {
            Toast.makeText(this, "Debes introducir el normbre del bar", Toast.LENGTH_SHORT).show();
        }
    }
    //Método para eliminar un bar

    public void EliminarBar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper
                (this, "administracion", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        BaresFragment fragment = (BaresFragment) getSupportFragmentManager().findFragmentById(R.id.contenedor);

        String nombreBar = fragment.et_nombreBar.getText().toString();

        if(!nombreBar.isEmpty()){

            int cantidad = BaseDatabase.delete("bares", "nombreBar=" + "'"+nombreBar+"'", null);
            BaseDatabase.close();

            fragment.et_nombreBar.setText("");
            fragment.et_direccionBar.setText("");
            fragment.et_horarioBar.setText("");

            if(cantidad == 1){
                Toast.makeText(this, "Bar eliminado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El Bar no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Debes introducir el nombre del Bar", Toast.LENGTH_SHORT).show();
        }
    }
    //Método para modificar un Bar

    public void ModificarBar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        BaresFragment fragment = (BaresFragment) getSupportFragmentManager().findFragmentById(R.id.contenedor);

        String nombreBar = fragment.et_nombreBar.getText().toString();
        String direccionBar = fragment.et_direccionBar.getText().toString();
        String horarioBar = fragment.et_horarioBar.getText().toString();


        if(!nombreBar.isEmpty() && !direccionBar.isEmpty() && !horarioBar.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("nombreBar", nombreBar);
            registro.put("direccion", direccionBar);
            registro.put("horario", horarioBar);

            int cantidad = BaseDatabase.update("bares", registro, "nombreBar=" + "'"+nombreBar+"'", null);
            BaseDatabase.close();

            if(cantidad == 1){
                Toast.makeText(this, "Bar modificado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El Bar no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
