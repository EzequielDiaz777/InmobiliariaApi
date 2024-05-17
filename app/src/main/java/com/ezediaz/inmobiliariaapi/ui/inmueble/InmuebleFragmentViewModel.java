package com.ezediaz.inmobiliariaapi.ui.inmueble;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.ezediaz.inmobiliariaapi.model.Inmueble;
import com.ezediaz.inmobiliariaapi.model.Propietario;
import com.ezediaz.inmobiliariaapi.model.Tipo;
import com.ezediaz.inmobiliariaapi.model.Uso;
import com.ezediaz.inmobiliariaapi.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleFragmentViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> mInmueble;
    private MutableLiveData<Boolean> mDisponible;
    private MutableLiveData<String> mGuardar;
    private MutableLiveData<Boolean> mHabilitar;

    private MutableLiveData<List<Tipo>> mTipo;
    private MutableLiveData<List<Uso>> mUso;

    public InmuebleFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getMInmueble() {
        if (mInmueble == null) {
            mInmueble = new MutableLiveData<>();
        }
        return mInmueble;
    }

    public LiveData<Boolean> getMDisponible() {
        if (mDisponible == null) {
            mDisponible = new MutableLiveData<>();
        }
        return mDisponible;
    }

    public LiveData<String> getMGuardar() {
        if (mGuardar == null) {
            mGuardar = new MutableLiveData<>();
        }
        return mGuardar;
    }

    public LiveData<Boolean> getMHabilitar() {
        if (mHabilitar == null) {
            mHabilitar = new MutableLiveData<>();
        }
        return mHabilitar;
    }

    public LiveData<List<Tipo>> getMTipo() {
        if (mTipo == null) {
            mTipo = new MutableLiveData<>();
        }
        return mTipo;
    }

    public LiveData<List<Uso>> getMUso() {
        if (mUso == null) {
            mUso = new MutableLiveData<>();
        }
        return mUso;
    }

    public void cargarInmueble(Bundle arguments) {
        if (arguments != null) {
            Inmueble inmueble = (Inmueble) arguments.getSerializable("inmueble");
            if (inmueble != null) {
                mInmueble.setValue(inmueble);
            }
        }
    }

    public void cambiarDisponibilidad(boolean disponible, int id) {
        String token = ApiClient.leerToken(getApplication());
        if (token != null) {
            ApiClient.MisEndPoints api = ApiClient.getEndPoints();
            Call<Void> call = api.inmuebleDisponible(token, id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        if (disponible) {
                            Toast.makeText(getApplication(), "Inmueble dado de alta", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplication(), "Inmueble dado de baja", Toast.LENGTH_LONG).show();
                        }
                        mDisponible.setValue(disponible);
                    } else {
                        Toast.makeText(getApplication(), "Fallo en la actualización del inmueble", Toast.LENGTH_LONG).show();
                        Log.d("salida", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable throwable) {
                    Log.d("salida", "Falla: " + throwable.getMessage());
                }
            });
        }
    }

    public Tipo obtenerTipo(int id) {
        final Tipo[] tipo = new Tipo[1];
        String token = ApiClient.leerToken(getApplication());
        if (token != null) {
            ApiClient.MisEndPoints api = ApiClient.getEndPoints();
            Call<Tipo> call = api.obtenerTipo(token, id);
            call.enqueue(new Callback<Tipo>() {
                @Override
                public void onResponse(Call<Tipo> call, Response<Tipo> response) {
                    if (response.isSuccessful()) {
                        tipo[0] = response.body();
                    } else {
                        Toast.makeText(getApplication(), "Falla en la recuperación del tipo de inmueble", Toast.LENGTH_LONG).show();
                        Log.d("salida", response.message());
                        tipo[0] = null;
                    }
                }

                @Override
                public void onFailure(Call<Tipo> call, Throwable throwable) {
                    Log.d("salida", "Falla: " + throwable.getMessage());
                    tipo[0] = null;
                }
            });
        }
        return tipo[0];
    }

    private Uso obtenerUso(int id) {
        final Uso[] uso = new Uso[1];
        String token = ApiClient.leerToken(getApplication());
        if (token != null) {
            ApiClient.MisEndPoints api = ApiClient.getEndPoints();
            Call<Uso> call = api.obtenerUso(token, id);
            call.enqueue(new Callback<Uso>() {
                @Override
                public void onResponse(Call<Uso> call, Response<Uso> response) {
                    if (response.isSuccessful()) {
                        uso[0] = response.body();
                    } else {
                        Toast.makeText(getApplication(), "Falla en la recuperación del uso de inmueble", Toast.LENGTH_LONG).show();
                        uso[0] = null;
                        Log.d("salida", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Uso> call, Throwable throwable) {
                    uso[0] = null;
                    Log.d("salida", "Falla: " + throwable.getMessage());
                }
            });
        }
        return uso[0];
    }

    public void agregarInmueble(String boton, Inmueble inmueble, String ambientes, String direccion, String precio, Spinner spinnerT, Spinner spinnerU) {
        if (boton.equals("Agregrar inmueble")) {
            mGuardar.setValue("Guardar inmueble");
            mHabilitar.setValue(true);
            spinnerT.setEnabled(true);
            ArrayAdapter<Tipo> tipoAdapter = new ArrayAdapter<>(spinnerT.getContext(), android.R.layout.simple_spinner_item);
            spinnerT.setAdapter(tipoAdapter);
            String token = ApiClient.leerToken(getApplication());
            if (token != null) {
                ApiClient.MisEndPoints api = ApiClient.getEndPoints();
                Call<List<Tipo>> call = api.obtenerTipos(token);
                call.enqueue(new Callback<List<Tipo>>() {
                    @Override
                    public void onResponse(Call<List<Tipo>> call, Response<List<Tipo>> response) {
                        if (response.isSuccessful()) {
                            response.body().forEach(tipo -> {
                                tipoAdapter.add(tipo);
                                spinnerT.setSelection(tipoAdapter.getPosition(tipo));
                            });
                            spinnerT.setSelection(0);
                        } else {
                            Toast.makeText(getApplication(), "Falla en el dado de alta del inmueble", Toast.LENGTH_LONG).show();
                            Log.d("salida", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Tipo>> call, Throwable throwable) {
                        Log.d("salida", "Falla: " + throwable.getMessage());
                    }
                });
            }
            spinnerU.setEnabled(true);
            ArrayAdapter<Uso> usoAdapter = new ArrayAdapter<>(spinnerU.getContext(), android.R.layout.simple_spinner_item);
            spinnerU.setAdapter(usoAdapter);
            ApiClient.MisEndPoints api = ApiClient.getEndPoints();
            Call<List<Uso>> call = api.obtenerUsos(token);
            call.enqueue(new Callback<List<Uso>>() {
                @Override
                public void onResponse(Call<List<Uso>> call, Response<List<Uso>> response) {
                    if (response.isSuccessful()) {
                        response.body().forEach(uso -> {
                            usoAdapter.add(uso);
                            spinnerU.setSelection(usoAdapter.getPosition(uso));
                        });
                        spinnerU.setSelection(0);
                    } else {
                        Toast.makeText(getApplication(), "Falla en el dado de alta del inmueble", Toast.LENGTH_LONG).show();
                        Log.d("salida", response.message());
                    }
                }
                @Override
                public void onFailure(Call<List<Uso>> call, Throwable throwable) {
                    Log.d("salida", "Falla: " + throwable.getMessage());
                }
            });
        } else {
            if (ambientes.isEmpty() || direccion.isEmpty() || precio.isEmpty()) {
                Toast.makeText(getApplication(), "Debe ingresar todos los datos antes de guardar el inmueble", Toast.LENGTH_LONG).show();
            } else {
                mGuardar.setValue("Agregrar inmueble");
                mHabilitar.setValue(false);
                String token = ApiClient.leerToken(getApplication());
                if (token != null) {
                    ApiClient.MisEndPoints api = ApiClient.getEndPoints();
                    inmueble.setAmbientes(Integer.parseInt(ambientes));
                    inmueble.setDireccion(direccion);
                    inmueble.setPrecio(Double.valueOf(precio));
                    Call<Inmueble> call = api.agregarInmueble(token, inmueble);
                    call.enqueue(new Callback<Inmueble>() {
                        @Override
                        public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                            if (response.isSuccessful()) {
                                inmueble.setId(9);
                                Toast.makeText(getApplication(), "Inmueble dado de alta con exito", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplication(), "Falla en el dado de alta del inmueble", Toast.LENGTH_LONG).show();
                                Log.d("salida", response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Inmueble> call, Throwable throwable) {
                            Log.d("salida", "Falla: " + throwable.getMessage());
                        }
                    });
                    inmueble.setId(9);
                    Log.d("inmueble", inmueble.getId()+"");
                    Log.d("inmueble", inmueble.getAmbientes()+"");
                    Log.d("inmueble", inmueble.getDireccion());
                    Log.d("inmueble", inmueble.getPrecio()+"");
                    Log.d("inmueble", inmueble.getTipoId()+"");
                    Log.d("inmueble", inmueble.getUsoId()+"");
                    Call<Inmueble> inmuebleCall = api.obtenerInmueble(token, inmueble.getId());
                    inmuebleCall.enqueue(new Callback<Inmueble>() {
                        @Override
                        public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                            if (response.isSuccessful()) {
                                response.body().setTipo(obtenerTipo(response.body().getTipoId()));
                                response.body().setUso(obtenerUso(response.body().getUsoId()));
                                mInmueble.postValue(response.body());
                            } else {
                                Toast.makeText(getApplication(), "Falla en la obtención del inmueble", Toast.LENGTH_LONG).show();
                                Log.d("salida", response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Inmueble> call, Throwable throwable) {
                            Log.d("salida", "Falla: " + throwable.getMessage());
                        }
                    });
                }
            }
        }
    }
}
