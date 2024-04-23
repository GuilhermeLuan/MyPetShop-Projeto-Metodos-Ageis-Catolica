package com.example.mypetshop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {
    private ImageView imageView_careleft, imageView_show_hide_pwd;
    private EditText editText_nameRegister, editText_lastNameRegister, editText_CPF,
            editText_Cellphone, editText_Email, editText_Password;

    private Button bt_register;

    String[] mensagens = {
            "Preencha todos os campos",
            "Cadastro realizado com sucesso",
            "A senha deve ter pelo menos 8 caracteres.",
            "Insira um CPF valido!",
            "CPF já está registrado!"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startComponents();

        imageView_careleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginView();
            }
        });

        showHidePwd();

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText_nameRegister.getText().toString();
                String lastName = editText_lastNameRegister.getText().toString();
                String cpf = editText_CPF.getText().toString();
                String email = editText_Email.getText().toString();
                String password = editText_Password.getText().toString();
                String cellphoneNumber = editText_Cellphone.getText().toString();

                if(name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || cellphoneNumber.isEmpty()){
                    showSnackbar(v, mensagens[0]);
                }
                //Verifico o cpf apenas se tem 11 caracteres, pra facilitar os testes e não ter que usar dados pessoais.
                else if (cpf.length() < 11) {
                    showSnackbar(v, mensagens[3]);
                } else if (password.length() < 8) {
                    showSnackbar(v, mensagens[2]);
                } else {
                    registerUser(v);
                }
            }
        });

    }

    private void registerUser(View v){

    }

    //Alterar esse parte para quando tiver a tela de login pronta
    private void loginView(){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void showHidePwd(){
        imageView_show_hide_pwd.setImageResource(R.drawable.ic_eye_closed);
        imageView_show_hide_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_Password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    editText_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageView_show_hide_pwd.setImageResource(R.drawable.ic_eye_closed);
                }
                else {
                    editText_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageView_show_hide_pwd.setImageResource(R.drawable.ic_eye);
                }
            }
        });
    }

    private void showSnackbar(View v, String mensagem) {
        Snackbar snackbar = Snackbar.make(v, mensagem, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(Color.WHITE);
        snackbar.setTextColor(Color.BLACK);
        snackbar.show();
    }

    private void startComponents(){
        imageView_careleft = findViewById(R.id.imageView_careleft);
        imageView_show_hide_pwd = findViewById(R.id.imageView_show_hide_pwd);
        editText_nameRegister = findViewById(R.id.editText_nameRegister);
        editText_lastNameRegister = findViewById(R.id.editText_lastNameRegister);
        editText_CPF = findViewById(R.id.editText_CPF);
        editText_Cellphone = findViewById(R.id.editText_Cellphone);
        editText_Email = findViewById(R.id.editText_Email);
        editText_Password = findViewById(R.id.editText_Password);
        bt_register = findViewById(R.id.bt_register);
    }
}