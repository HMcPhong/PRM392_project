package com.example.prm392front.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392front.respond.ApiResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.example.prm392front.R;
import com.example.prm392front.api.ApiClient;
import com.example.prm392front.model.User;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etUsername, etEmail, etPassword, etPhone, etAddress;
    Button btnRegister;
    TextView tvGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etRegUsername);
        etEmail    = findViewById(R.id.etRegEmail);
        etPassword = findViewById(R.id.etRegPassword);
        etPhone    = findViewById(R.id.etRegPhone);
        etAddress  = findViewById(R.id.etRegAddress);
        btnRegister = findViewById(R.id.btnRegister);
        tvGoLogin   = findViewById(R.id.tvGoLogin);

        btnRegister.setOnClickListener(v -> doRegister());
        tvGoLogin.setOnClickListener(v -> finish()); // go back to login
    }

    private void doRegister() {
        String username = Objects.requireNonNull(etUsername.getText()).toString().trim();
        String email    = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(etPassword.getText()).toString().trim();
        String phone    = Objects.requireNonNull(etPhone.getText()).toString().trim();
        String address  = Objects.requireNonNull(etAddress.getText()).toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username, email and password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User();
        newUser.setUserName(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhoneNumber(phone);
        newUser.setAddress(address);

        btnRegister.setEnabled(false);

        // Register as customer by default
        ApiClient.getApiService().registerCustomer(newUser)
                .enqueue(new Callback<ApiResponse<User>>() {
                    @Override
                    public void onResponse(@NonNull Call<ApiResponse<User>> call, @NonNull Response<ApiResponse<User>> response) {
                        btnRegister.setEnabled(true);
                        if (response.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registered successfully! Please login.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed. Username may already exist.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse<User>> call, @NonNull Throwable t) {
                        btnRegister.setEnabled(true);
                        Toast.makeText(RegisterActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
