package com.example.mypetshop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mypetshop.callbacks.CheckCpfCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private ImageView go_back_button, imageView_show_hide_pwd;
    private EditText editText_nameRegister, editText_lastNameRegister, editText_CPF,
            editText_Cellphone, editText_Email, editText_Password;

    private Button bt_register;

    String[] mensagens = {
            "Preencha todos os campos",
            "Cadastro realizado com sucesso",
            "A senha deve ter pelo menos 8 caracteres.",
            "Insira um CPF valido!",
            "CPF já está registrado!",
            "Insira um número de telefone valido!",
            "O CPF já está em uso. Por favor, tente outro."
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

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.white));

        startComponents();

        go_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity();
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
                //Verifico se tem mais ou menos de 11 numeros no cpf, apenas para facilitar os testes e não ter que usar dados pessoais.
                else if (cpf.length() != 11) {
                    showSnackbar(v, mensagens[3]);
                }
                //Verifico se tem mais ou menos de 11 numeros no telefone, apenas para facilitar os testes e não ter que usar dados pessoais.

                else if (cellphoneNumber.length() != 11) {
                    showSnackbar(v, mensagens[5]);
                } else if (password.length() < 8) {
                    showSnackbar(v, mensagens[2]);
                } else {
                    checkIfCpfIsUnique(cpf, new CheckCpfCallback() {
                        @Override
                        public void onCheckComplete(boolean isUnique) {
                            if (!isUnique) {
                                showSnackbar(v, mensagens[6]);
                            } else {
                                registerUser(v);
                            }
                        }
                    });
                }
            }
        });

    }

    private void registerUser(View v){
        String email = editText_Email.getText().toString();
        String password = editText_Password.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    saveUserData();
                    showSnackbar(v, mensagens[1]);
                    mainActivity();
                } else {
                    String error;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthUserCollisionException e) {
                        error = "Esta conta já foi cadastrada.";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        error = "E-mail invalido!";
                    } catch (Exception e){
                        error = "Erro ao cadastrar usuário.";
                    }
                    showSnackbar(v, error);
                }
            }
        });
    }

    private void saveUserData(){
        String name = editText_nameRegister.getText().toString();
        String lastName = editText_lastNameRegister.getText().toString();
        String cellphoneNumber = editText_Cellphone.getText().toString();
        String cpf = editText_CPF.getText().toString();
        String email = editText_Email.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> users = new HashMap<>();
        users.put("name", name);
        users.put("lastName", lastName);
        users.put("cellPhoneNumber", cellphoneNumber);
        users.put("cpf", cpf);
        users.put("email", email);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Users").document(userID);
        documentReference.set(users).addOnSuccessListener(new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao salvar os dados");

            }
        })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db", "Erro ao salvar os dados" + e);

            }
        });
    }

    //Alterar esse parte para quando tiver a tela de login pronta
    private void loginActivity(){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //Alterar esse parte para quando tiver a tela "em construção" pronta
    private void mainActivity(){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void showHidePwd(){
        imageView_show_hide_pwd.setImageResource(R.drawable.ic_eye);
        imageView_show_hide_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_Password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    editText_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageView_show_hide_pwd.setImageResource(R.drawable.ic_eye);
                }
                else {
                    editText_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageView_show_hide_pwd.setImageResource(R.drawable.ic_eye_closed);
                }
            }
        });
    }

    private void showSnackbar(View v, String mensagem) {
        Snackbar snackbar = Snackbar.make(v, mensagem, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(Color.GREEN);
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public void checkIfCpfIsUnique(String cpf, CheckCpfCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Consulta para verificar se algum usuário tem o mesmo CPF
        db.collection("Users")
                .whereEqualTo("cpf", cpf)  // Filtra por CPF
                .get()  // Obtém a resposta
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        boolean isUnique = querySnapshot.isEmpty();  // Se estiver vazio, é único
                        callback.onCheckComplete(isUnique);
                    } else {
                        // Se houve erro, considere não único para segurança
                        callback.onCheckComplete(false);
                    }
                });
    }

    private void startComponents(){
        go_back_button = findViewById(R.id.imageView_careleft);
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