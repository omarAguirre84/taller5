package com.example.aliciaespinola.rockmusicband;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends Activity {
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private LoginButton loginButton;


    //para login developer
    //alicia_qpmacir_espinola@tfbnw.net
    //alicia22


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                irMenuPrincipal(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                Toast.makeText(getApplicationContext(),"Entrando a RockMusicBand...",Toast.LENGTH_SHORT).show();
                irMenuPrincipal(profile);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Cancel...",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(),"Error connection...",Toast.LENGTH_SHORT).show();
            }
        });
        loginButton.setReadPermissions("user_friends");
        //loginButton.registerCallback(callbackManager, callback);
    }


    private void irMenuPrincipal(Profile profile){
        if(profile!= null){
            Intent cambioPantalla = new Intent(this,bogdan.class);
            cambioPantalla.putExtra("name",profile.getFirstName());
            cambioPantalla.putExtra("surname",profile.getLastName());
            cambioPantalla.putExtra("imageUrl",profile.getProfilePictureUri(200,200).toString());
            startActivity(cambioPantalla);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        irMenuPrincipal(profile);
    }
    @Override
    protected void onPause(){
       super.onPause();
    }

    protected void onStop(){
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
