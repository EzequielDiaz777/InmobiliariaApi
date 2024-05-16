package com.ezediaz.inmobiliariaapi.ui.inmueble;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.ezediaz.inmobiliariaapi.databinding.FragmentInmuebleBinding;
import com.ezediaz.inmobiliariaapi.model.Inmueble;

public class InmuebleFragment extends Fragment {
    private FragmentInmuebleBinding binding;
    private InmuebleFragmentViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        vm = new ViewModelProvider(this).get(InmuebleFragmentViewModel.class);
        vm.getMFarmacia().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            String t;
            @Override
            public void onChanged(Inmueble farmacia) {
                //binding.tvNombre.setText(farmacia.getNombre());
                //binding.ivFoto.setImageResource(farmacia.getFoto());
                t = binding.tvDireccion.getText().toString() + " " + farmacia.getDireccion();
                binding.tvDireccion.setText(t);
                //t = binding.tvHorario.getText().toString() + " " + farmacia.getHorario();
                //binding.tvHorario.setText(t);
            }
        });
        vm.cargarInmueble(getArguments());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
