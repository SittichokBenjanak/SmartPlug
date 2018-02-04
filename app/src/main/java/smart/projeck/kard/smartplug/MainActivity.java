package smart.projeck.kard.smartplug;

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

    }


    private void bindWidget() {
        UsereditText = findViewById(R.id.editText);
        PasswordEditText = findViewById(R.id.editText2);

    }   // bindWidget

    public void ClickLogin(View view) {
        //ถ้ากด Login Method นี่จะทำงาน
        userString = UsereditText.getText().toString().trim();
        passwordString = PasswordEditText.getText().toString().trim();

        if (userString.equals("") || passwordString.equals("")){
            // ถ้าฃ่องกรอก User กับ Password อันไหนไม่ได้กรอกจะทำงาน
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.errorDialog(MainActivity.this,"Please check space","กรุณากรอกข้อมูลให้ครบ");

        } else {

        }



    }   // ClickLogin

    public void ClickRegister(View view) {
        // กด New Register จะส่งไปหน้าสมัครสมาชิก
        startActivity(new Intent(MainActivity.this,RegisterActivity.class));

    }   // ClickRegister


}   // Main Class
