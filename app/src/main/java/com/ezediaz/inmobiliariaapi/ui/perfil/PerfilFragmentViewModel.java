package com.ezediaz.inmobiliariaapi.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ezediaz.inmobiliariaapi.LoginActivity;
import com.ezediaz.inmobiliariaapi.model.Propietario;
import com.ezediaz.inmobiliariaapi.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragmentViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> mPropietario;
    private Context context;

    public PerfilFragmentViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Propietario> getMPropietario() {
        if(mPropietario == null){
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }

    public void miPerfil(){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        if (token != null) {
            ApiClient.MisEndPoints api = ApiClient.getEndPoints();
            Call<Propietario> call = api.miPerfil("Bearer " + token);
            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        mPropietario.setValue(response.body());
                    } else {
                        Log.d("salida", "Incorrecto");
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable throwable) {
                    Log.d("salida", "Falla");
                }
            });
        } else {
            Intent intent = new Intent(getApplication(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpiar la pila de actividades
            getApplication().startActivity(intent);
        }
    }
}