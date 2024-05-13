package com.ezediaz.inmobiliariaapi;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ezediaz.inmobiliariaapi.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {
    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void logueo(String usuario, String clave){
        ApiClient.MisEndPoints api = ApiClient.getEndPoints();
        Call<String> call = api.login(usuario, clave);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Log.d("salida", response.body());
                } else {
                    Log.d("salida", "Incorrecto");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.d("salida", "Falla");
            }
        });
    }
}
