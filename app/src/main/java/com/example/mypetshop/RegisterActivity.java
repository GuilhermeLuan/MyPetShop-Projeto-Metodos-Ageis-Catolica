package com.example.mypetshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    private ImageView imageView_careleft, imageView_show_hide_pwd;
    private EditText editText_nameRegister, editText_lastNameRegister, editText_CPF,
            editText_Cellphone, editText_Email, editText_Password;

    private Button bt_register;

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

    }

    //Alterar esse parte para quando tiver a tela de login pronta
    private void loginView(){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
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