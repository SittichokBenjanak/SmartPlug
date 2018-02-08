package smart.projeck.kard.smartplug;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // Explicit ประกาศตัวแปรเพื่อรับค่า User, Password
    private EditText maileditText, passeditText;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // เรียนใช้ Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        maileditText = findViewById(R.id.Usereditext);
        passeditText = findViewById(R.id.Passeditext);


    }   // onCreate

    public void onBackPressed() {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setIcon(R.drawable.ic_action_alert);
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to close the program?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }   // onBackPressed



    public void ClickRegister(View view) {
            //microgear.chat("Relay","DEV10");
        // กด New Register จะส่งไปหน้าสมัครสมาชิก

        finish();
        startActivity(new Intent(MainActivity.this,RegisterActivity.class));

    }   // ClickRegister

    public void ClickLogin(View view) {

        String email = maileditText.getText().toString().trim();
        String password = passeditText.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.errorDialog(MainActivity.this,"Please check space","KeyIn Email");
            return;
        }   // if

        if (TextUtils.isEmpty(password)) {
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.errorDialog(MainActivity.this,"Please check space","KeyIn Password");
            return;
        }   // if

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "SingIn OK", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(MainActivity.this,hubActivity.class));
                progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "SingIn Fail", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

    }


}   // Main Class
