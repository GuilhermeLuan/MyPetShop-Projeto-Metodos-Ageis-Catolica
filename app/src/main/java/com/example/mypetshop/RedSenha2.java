package com.example.mypetshop;

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

public class RedSenha2 extends AppCompatActivity {
    private ImageView go_back_button;
    private EditText code_input1,code_input2,code_input3,code_input4,code_input5;
    private Button bt_send,bt_send2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_red_senha2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        startComponents();
        go_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    red_Activity();
            }
        });
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarCodigo();
            }
        });
        bt_send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chamar função da tela do kaion
            }
        });
    }

    private void verificarCodigo(){
        //Aqui vou programar para enviar o codigo
    }
    private void red_Activity(){
        //Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        //startActivity(intent); Mudar esses dois itens quando tiver as outras telas
    }
    private void startComponents(){
        go_back_button = findViewById(R.id.imageView_careleft);
        bt_send = findViewById(R.id.bt_redfine);
        bt_send2 = findViewById(R.id.bt_redfine2);
        code_input1 = findViewById(R.id.code_input1);
        code_input2 = findViewById(R.id.code_input2);
        code_input3 = findViewById(R.id.code_input3);
        code_input4 = findViewById(R.id.code_input4);
        code_input5 = findViewById(R.id.code_input5);

    }
}