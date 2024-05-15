package com.ezediaz.exploradordefarmacias;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MenuActivityViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mTipoDeMapa = new MutableLiveData<>("normal");
    private static final MutableLiveData<String> mIdioma = new MutableLiveData<>("es");
    public MenuActivityViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getTipoDeMapa() {
        return mTipoDeMapa;
    }
    public void setTipoDeMapa(String tipoDeMapa) {
        mTipoDeMapa.setValue(tipoDeMapa);
    }
    public LiveData<String> getMIdioma() {
        return mIdioma;
    }
    public void setMIdioma(String idioma) {
        mIdioma.setValue(idioma);
    }
}