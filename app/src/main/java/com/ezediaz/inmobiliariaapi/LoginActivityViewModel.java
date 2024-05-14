package com.ezediaz.inmobiliariaapi;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ezediaz.inmobiliariaapi.MenuActivity;
import com.ezediaz.inmobiliariaapi.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {
    private SharedPreferences sharedPreferences;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
    }

    public void logueo(String usuario, String clave) {
        ApiClient.MisEndPoints api = ApiClient.getEndPoints();
        Call<String> call = api.login(usuario, clave);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    guardarToken(token);
                    Log.d("salida", "Inicio de sesión exitoso");

                    // Iniciar la actividad principal después de un inicio de sesión exitoso
                    iniciarMenuActivity();
                } else {
                    Toast.makeText(getApplication(), "Email o contraseña incorrecta", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Falla en el inicio de sesión", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void guardarToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public void deslogueo() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();
    }

    private void iniciarMenuActivity() {
        Intent intent = new Intent(getApplication(), MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpiar la pila de actividades
        getApplication().startActivity(intent);
    }

    // Método para verificar si el usuario está autenticado y abrir la actividad principal si es así
    public void checkLoggedIn() {
        String token = sharedPreferences.getString("token", null);
        if (token != null) {
            iniciarMenuActivity();
        }
    }
}
