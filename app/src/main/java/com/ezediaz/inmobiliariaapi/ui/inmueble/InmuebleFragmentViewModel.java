package com.ezediaz.inmobiliariaapi.ui.inmueble;

import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.ezediaz.inmobiliariaapi.model.Inmueble;

public class InmuebleFragmentViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> mInmueble;
    public InmuebleFragmentViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<Inmueble> getMFarmacia() {
        if(mInmueble == null){
            mInmueble = new MutableLiveData<>();
        }
        return mInmueble;
    }
    public void cargarInmueble(Bundle arguments) {
        if (arguments != null) {
            Inmueble inmueble = (Inmueble) arguments.getSerializable("inmueble");
            if (inmueble != null) {
                mInmueble.setValue(inmueble);
            }
        }
    }
}
