package com.example.mypetshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class Login extends AppCompatActivity {

    private TextView sign_in_button;
    private EditText email_input;
    private EditText password_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Init();

        sign_in_button.setOnClickListener(v -> {
            String email = email_input.getText().toString();
            String password = password_input.getText().toString();

            if(email.isEmpty() || password.isEmpty()){
                ShowErroMessage("Preencha todos os campos.", v);
            }else{
                SignIn(email, password, v);
            }
        });
        
    }
    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();

        if(authUser != null){
            GoToHomeScreen();
        }
    }
    private void Init(){
        sign_in_button = findViewById(R.id.sign_in_button);
        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);
    }

    private void SignIn(String email, String password, View v){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                GoToHomeScreen();
            }else {
                String errorMessage = "Erro ao logar usuário.";
                try {
                    throw Objects.requireNonNull(task.getException());
                }catch (FirebaseAuthInvalidCredentialsException e){
                    String errorCode = e.getErrorCode();
                    if(errorCode.equals("ERROR_INVALID_EMAIL")){
                        errorMessage = "Usuário não encontrado.";
                    }else if(errorCode.equals("ERROR_INVALID_CREDENTIAL")){
                        errorMessage = "Senha incorreta.";
                    }
                }
                catch (Exception e) {
                    Log.e("authentication", "Erro ao logar usuário: " + e.getMessage());
                }
                ShowErroMessage(errorMessage, v);
            }
        });
    }

    private void GoToHomeScreen(){
        Intent intent = new Intent(Login.this, Building.class);
        startActivity(intent);
    }
    private void ShowErroMessage(String message, View v){
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(getColor(R.color.green_normal));
        snackbar.setTextColor(getColor((R.color.white)));
        snackbar.show();
    }

}