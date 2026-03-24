package com.example.prm392front.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.prm392front.R;
import com.example.prm392front.api.ApiClient;
import com.example.prm392front.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName, tvRole, tvInitial, tvEmail, tvPhone, tvAddress;
    Button btnLogout;
    int currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Profile");
        }

        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        currentUserID = prefs.getInt("userID", -1);

        tvName    = findViewById(R.id.tvProfileName);
        tvRole    = findViewById(R.id.tvProfileRole);
        tvInitial = findViewById(R.id.tvProfileInitial);
        tvEmail   = findViewById(R.id.tvProfileEmail);
        tvPhone   = findViewById(R.id.tvProfilePhone);
        tvAddress = findViewById(R.id.tvProfileAddress);
        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> logout());
        loadProfile();
    }

    private void loadProfile() {
        ApiClient.getApiService().getUserById(currentUserID)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            User u = response.body();
                            tvName.setText(u.getUserName());
                            tvRole.setText(u.getRole());
                            tvInitial.setText(u.getUserName().substring(0, 1).toUpperCase());
                            tvEmail.setText(u.getEmail() != null ? u.getEmail() : "—");
                            tvPhone.setText(u.getPhoneNumber() != null ? u.getPhoneNumber() : "—");
                            tvAddress.setText(u.getAddress() != null ? u.getAddress() : "—");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        Toast.makeText(ProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void logout() {
        getSharedPreferences("MyApp", MODE_PRIVATE).edit().clear().apply();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
