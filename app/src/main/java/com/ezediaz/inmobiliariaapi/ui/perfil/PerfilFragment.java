package com.ezediaz.inmobiliariaapi.ui.perfil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.ezediaz.inmobiliariaapi.databinding.FragmentPerfilBinding;
import com.ezediaz.inmobiliariaapi.model.Propietario;

public class PerfilFragment extends Fragment {
    private PerfilFragmentViewModel vm;
    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(PerfilFragmentViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        vm.getMPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etDNI.setText(propietario.getDni());
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etTelefono.setText(propietario.getTelefono());
                binding.etEmailPerfil.setText(propietario.getEmail());
            }
        });
        vm.miPerfil();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}