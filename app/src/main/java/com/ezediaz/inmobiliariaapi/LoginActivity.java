package com.ezediaz.inmobiliariaapi;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.ezediaz.inmobiliariaapi.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private LoginActivityViewModel vm;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vm = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(LoginActivityViewModel.class);
        vm.deslogueo();
        binding.btnLogearse.setOnClickListener(view -> {
            String email = binding.etEmail.getText().toString();
            String password = binding.etPassword.getText().toString();
            vm.logueo(email, password);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.checkLoggedIn();
    }
}
