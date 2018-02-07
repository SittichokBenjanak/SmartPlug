package smart.projeck.kard.smartplug;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Explicit ประกาศตัวแปรเพื่อรับค่า User, Password
    private EditText UsereditText, PasswordEditText;
    private String userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind Widger ให้ ตัวแปร UsereditText, PasswordEditText รับค่าจากช่องกรอก User กับ Password
        bindWidget();

    }   // onCreate


    private void bindWidget() {
        UsereditText = findViewById(R.id.editText);
        PasswordEditText = findViewById(R.id.editText2);

    }   // bindWidget

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



    public void ClickLogin(View view) {
        //ถ้ากด Login Method นี่จะทำงาน
        userString = UsereditText.getText().toString().trim();
        passwordString = PasswordEditText.getText().toString().trim();

        if (userString.equals("") || passwordString.equals("")){
            // ถ้าฃ่องกรอก User กับ Password อันไหนไม่ได้กรอกจะทำงาน
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.errorDialog(MainActivity.this,"Please check space","KeyIn User and Password");

        } else {
            //microgear.chat("Relay","DEV11");
        } // else

    }   // ClickLogin

    public void ClickRegister(View view) {
            //microgear.chat("Relay","DEV10");
        // กด New Register จะส่งไปหน้าสมัครสมาชิก
        startActivity(new Intent(MainActivity.this,RegisterActivity.class));

    }   // ClickRegister



}   // Main Class
