package com.example.prm392front.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.example.prm392front.R;
import com.example.prm392front.api.ApiClient;
import com.example.prm392front.model.User;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etUsername, etPassword;
    Button btnLogin;
    TextView tvGoRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername   = findViewById(R.id.etUsername);
        etPassword   = findViewById(R.id.etPassword);
        btnLogin     = findViewById(R.id.btnLogin);
        tvGoRegister = findViewById(R.id.tvGoRegister);

        btnLogin.setOnClickListener(v -> doLogin());

        tvGoRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );
    }

    @SuppressLint("SetTextI18n")
    private void doLogin() {
        String username = Objects.requireNonNull(etUsername.getText()).toString().trim();
        String password = Objects.requireNonNull(etPassword.getText()).toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        btnLogin.setEnabled(false);
        btnLogin.setText("Logging in...");

        ApiClient.getApiService().login(username, password)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        btnLogin.setEnabled(true);
                        btnLogin.setText("Login");
                        if (response.isSuccessful() && response.body() != null) {
                            User user = response.body();
                            // Save user info to SharedPreferences (acts like a session)
                            SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
                            prefs.edit()
                                    .putInt("userID", user.getUserID())
                                    .putString("userName", user.getUserName())
                                    .putString("role", user.getRole())
                                    .apply();

                            Toast.makeText(LoginActivity.this, "Welcome, " + user.getUserName(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish(); // prevent going back to login
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        btnLogin.setEnabled(true);
                        btnLogin.setText("Login");
                        Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
