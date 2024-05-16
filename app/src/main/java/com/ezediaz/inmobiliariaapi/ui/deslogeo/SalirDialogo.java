package com.ezediaz.inmobiliariaapi.ui.deslogeo;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.ezediaz.inmobiliariaapi.LoginActivityViewModel;
import com.ezediaz.inmobiliariaapi.request.ApiClient;

public class SalirDialogo extends AndroidViewModel {
    public SalirDialogo(@NonNull Application application) {
        super(application);
    }

    public static void mostrarDialogo(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmar salida");
        builder.setMessage("¿Está seguro que desea salir?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Desloguear", ApiClient.leerToken(context.getApplicationContext()));
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
