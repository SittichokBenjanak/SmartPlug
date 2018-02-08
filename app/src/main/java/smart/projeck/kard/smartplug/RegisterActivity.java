package smart.projeck.kard.smartplug;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail, editTextPassword;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // เรียนใช้ Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        // เรียนกใช้ progressDialog
        progressDialog = new ProgressDialog(this);

        // ให้ตัวแปรรับค่า Button กับ EditText, TextView
        buttonRegister = findViewById(R.id.button2);
        editTextEmail = findViewById(R.id.Emailedit);
        editTextPassword = findViewById(R.id.Passwordedit);



        // ผูก buttonให้คลิกได้
        buttonRegister.setOnClickListener(this);

    }   // onCreate

    @Override
    public void onClick(View view) {
        // ถ้าคลิก ปุ่ม Ok

            if (view == buttonRegister) {
                registerUser();

            }   // if 1


    }   // onClick

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.errorDialog(RegisterActivity.this,"Please check space","KeyIn Email");
            return;
        }   // if

        if (TextUtils.isEmpty(password)) {
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.errorDialog(RegisterActivity.this,"Please check space","KeyIn Password");
            return;
        }   // if

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(RegisterActivity.this, "Register OK", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Register Fail", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }   // registerUser


    public void onBackPressed() {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setIcon(R.drawable.ic_action_alert);
        dialog.setCancelable(true);
        dialog.setMessage("Are you sure Canceled Register?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }   // onBackPressed
}   // Main Class
