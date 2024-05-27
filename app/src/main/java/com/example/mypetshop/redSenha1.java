package com.example.mypetshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class redSenha1 extends AppCompatActivity {
    private ImageView go_back_button;
    private EditText editText_email;
    private Button bt_sendEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_red_senha1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        startComponents();
        go_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity();
            }
        });
        //Fazer ligação com cod do Jefferson ->
        bt_sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText_email.getText().toString();
            }
        });
    }
    private void loginActivity(){
        /*Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);*/
    }
    private void startComponents(){
        go_back_button = findViewById(R.id.go_back_button);
        editText_email = findViewById(R.id.Text);
        bt_sendEmail = findViewById(R.id.bt_register);
    }
}