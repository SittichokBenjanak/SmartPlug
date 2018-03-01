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

    /*ประกาศตัวแปรชื่อ maileditText, passeditText มีชนิดข้อมูลเป็น EditText
    private คือ หน้าอื่นจะไม่สามารถเรียกใช้ตัวแปร maileditText, passeditText ได้
    */
    private EditText maileditText, passeditText;

    /*ประกาศตัวแปรชื่อ progressDialog มีชนิดข้อมูลเป็น ProgressDialog
    private คือ หน้าอื่นจะไม่สามารถเรียกใช้ตัวแปร progressDialog ได้
    */
    private ProgressDialog progressDialog;

    /*ประกาศตัวแปรชื่อ firebaseAuth มีชนิดข้อมูลเป็น FirebaseAuth
    private คือ หน้าอื่นจะไม่สามารถเรียกใช้ตัวแปร firebaseAuth ได้
    */
    private FirebaseAuth firebaseAuth;

    /* โค้ด
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        คือโค้ดที่มันสร้างให้เองเราไม่ได้เขียนมันจะสร้างให้เองอัตโนมัติ
        }
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* ตัวแปรชื่อ firebaseAuth ซึ่งมีชนิดข้อมูลเป็น FirebaseAuth รับความสามารถจาก FirebaseAuth.getInstance();
           เมื่อตัวแปรชื่อ firebaseAuth รับความสามารถแล้ว เราสามารถเอาตัวแปร firebaseAuth ไปทำการเช็ค Email กับ Password
           ที่ผู้ใช้กรอกได้เลย เพราะตัวแปร firebaseAuth ได้ทำการเชื่อมต่อกับ server FirebaseAuth เรียบร้อยแล้ว
         */
        firebaseAuth = FirebaseAuth.getInstance();

        /* ตัวแปรชื่อ progressDialog ซึ่งมีชนิดข้อมูลเป็น ProgressDialog รับความสามารถจาก new ProgressDialog(this);
           ความสามารถที่ได้มา คือ ตัวหมุนที่จะแสดงให้ผู้ใช้อ่านตอนทำการกดปุ่ม Login ในขณะที่รอการเช็คข้อมูล email และ password
         */
        progressDialog = new ProgressDialog(this);

        /* ตัวแปรชื่อ maileditText ซึ่งมีชนิดข้อมูลเป็น EditText ทำการผูกกับ Usereditext ที่เป็นชื่อของ Layout ที่เราออกแบบเป็นช่องที่กรอก Email
           เพื่อที่จะได้รับค่าที่ผู้ใช้กรอก Email มา เช่นถ้าผู้ใช้กรอก test@hotmail.com
           ตัวแปรชื่อ maileditText ก็จะมีค่า = test@hotmail.com
         */
        maileditText = findViewById(R.id.Usereditext);

        /* ตัวแปรชื่อ passeditText ซึ่งมีชนิดข้อมูลเป็น EditText ทำการผูกกับ Passeditext ที่เป็นชื่อของ Layout ที่เราออกแบบเป็นช่องที่กรอก Password
           เพื่อที่จะได้รับค่าที่ผู้ใช้กรอก Password มา เช่นถ้าผู้ใช้กรอก test1234
           ตัวแปรชื่อ passeditText ก็จะมีค่า = test1234
         */
        passeditText = findViewById(R.id.Passeditext);

    }   // onCreate

        /*
         เม็ดตอดชื่อ onBackPressed() จะทำงานก็ต่อเมื่อผู้ใช้กดปุ่มย้อนกลับ
         */
    public void onBackPressed() {
        // ทำการสร้าง dialog ที่จะแสดงตอนผู้ใช้กดปุ่มย้อนกลับ
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);

        // ทำการเซ็ตรูปภาพที่ชื่อว่า ic_action_alert มาแสดงบน dialog
        dialog.setIcon(R.drawable.ic_action_alert);

        // ทำการเช็ตให้ dialog มีปุ่มยกเลิกให้ผู้ใช้กด
        dialog.setCancelable(true);

        // ทำการเซ็ตข้อความที่จะโชว์แสดงให้ผู้ใช้อ่าน ข้อความที่จะแสดง คือ Do you want to close the program?
        dialog.setMessage("Do you want to close the program?");

        // ถ้าผู้ใช้กดปุ่ม Yes โปรแกรมจะทำการเรียกใช้เม็ดตอด finish(); เพื่อทำการปิดโปรแรกมลง
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        // ถ้าผู้ใช้กดปุ่ม No โปรแกรมจะทำการเรียกใช้เม็ดตอด cancel(); เพื่อทำการให้ dialog ปิดลง แต่ผู้ใช้จะยังอยู่หน้า Login
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // เรียกใช้เม็ดตอด show(); เพื่อให้ dialog แสดง
        dialog.show();
    }   // onBackPressed


    /*
         เม็ดตอดชื่อ ClickRegister() จะทำงานก็ต่อเมื่อผู้ใช้กดที่ New Register
    */
    public void ClickRegister(View view) {
        // เม็ดตอด finish(); คือ การปิดหน้าต่างที่อยู่อย่างสมบูรณ์ จะไม่สามารถกดปุ่มย้อนกลับมาได้
        finish();

        // ทำการส่งไปที่หน้าสมัครสมาชิกใหม่ จากเดิมเราอยู่หน้า Login คือ MainActivity ย้ายไปยัง RegisterActivity คือ หน้าสมัครสมาชิกใหม่
        startActivity(new Intent(MainActivity.this,RegisterActivity.class));

    }   // ClickRegister


    /*
         เม็ดตอดชื่อ ClickLogin() จะทำงานก็ต่อเมื่อผู้ใช้กดปุ่ม Login
    */
    public void ClickLogin(View view) {
        /* ประกาศตัวแปรที่มีชื่อว่า email มีชนิดข้อมูลเป็น String ให้ทำการรับค่าของตัวแปรชื่อ maileditText
           และทำการใช้เม็ดตอด getText() เพื่อรับค่าที่เป็น Text มา แล้วทำการเรียกใช้เม็ดตอด toString() เพื่อที่จะแปลงค่าที่เป็น Text
           ไปเป็นค่า String แทน
           แล้วทำการเรียกใช้เม็ดตอด trim() ต่อ เพื่อทำการตัดช่องว่างออก เช่น ถ้าผู้ใช้กรอกข้อมูลมาว่า test   123 ซึ่งมีช่องว่า
           ระบบจะทำการตัดช่องว่างออก เหลือค่าแค่ test123
           ***** ในกรณีนี้ ค่าที่มีชนิดข้อมูลเป็น String จะไม่สามารถรับค่าตรงๆจากตัวแปรชนิดข้อมูลที่เป็น editText ได้
           เราจึงต้องใช้เม็ดตอด getText() กับ เม็ดตอด toString() มาแปลงค่าให้เหมือนกันก่อนที่จะส่งค่าได้**********
         */
        String email = maileditText.getText().toString().trim();

        /* ประกาศตัวแปรที่มีชื่อว่า password มีชนิดข้อมูลเป็น String ให้ทำการรับค่าของตัวแปรชื่อ passeditText
           และทำการใช้เม็ดตอด getText() เพื่อรับค่าที่เป็น Text มา แล้วทำการเรียกใช้เม็ดตอด toString() เพื่อที่จะแปลงค่าที่เป็น Text
           ไปเป็นค่า String แทน
           แล้วทำการเรียกใช้เม็ดตอด trim() ต่อ เพื่อทำการตัดช่องว่างออก เช่น ถ้าผู้ใช้กรอกข้อมูลมาว่า test   123 ซึ่งมีช่องว่า
           ระบบจะทำการตัดช่องว่างออก เหลือค่าแค่ test123
           ***** ในกรณีนี้ ค่าที่มีชนิดข้อมูลเป็น String จะไม่สามารถรับค่าตรงๆจากตัวแปรชนิดข้อมูลที่เป็น editText ได้
           เราจึงต้องใช้เม็ดตอด getText() กับ เม็ดตอด toString() มาแปลงค่าให้เหมือนกันก่อนที่จะส่งค่าได้**********
         */
        String password = passeditText.getText().toString().trim();

        /*ถ้าตัวแปรชื่อ email ซึ่งมีชนิดข้อมูลเป็น String ไม่มีค่าส่งมาหรือมีค่าเท่ากับว่างเปล่าจะมี Dialog แสดงเตือนว่า "Please check space"
         คือ กรุณาเช็คช่องที่กรอก Email ว่าว่างรึป่าว และจะมีคำบรรยายว่า KeyIn Email กรุณากรอกข้อมูล Email ด้วย
         */
        if (TextUtils.isEmpty(email)) {
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.errorDialog(MainActivity.this,"Please check space","KeyIn Email");
            return;
        }   // if

        /*ถ้าตัวแปรชื่อ password ซึ่งมีชนิดข้อมูลเป็น String ไม่มีค่าส่งมาหรือมีค่าเท่ากับว่างเปล่าจะมี Dialog แสดงเตือนว่า "Please check space"
         คือ กรุณาเช็คช่องที่กรอก Password ว่าว่างรึป่าว และจะมีคำบรรยายว่า KeyIn Password กรุณากรอกข้อมูล Password ด้วย
         */
        if (TextUtils.isEmpty(password)) {
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.errorDialog(MainActivity.this,"Please check space","KeyIn Password");
            return;
        }   // if

        /*
        ทำการเซ็ตข้อความ SingIn ... ให้ตัวแปรชื่อ progressDialog ซึ่งมีชนิดข้อมูลเป็น ProgressDialog โดยการเรียกใช้เม็ดตอด setMessage();
         */
        progressDialog.setMessage("SingIn ...");

        /*
        ทำการเรียกใช้เม็ดตอด show(); ให้ตัวแปรชื่อ progressDialog ซึ่งมีชนิดข้อมูลเป็น ProgressDialog แสดงตัวหมุนๆ บนหน้าจอแอพ
         */
        progressDialog.show();

        /*
        ทำการเรียกใช้เม็ดตอด signInWithEmailAndPassword() เพื่อทำการเช็คค่า Email กับ Password ที่ผู้ใช้กรอกเข้ามาว่ามีข้อมูลทางฝั้ง database บน server หรือไม่
         */
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // ถ้าเช็คแล้วว่า ข้อมูลมีจริงหรือถูกต้องจะทำการแสดงข้อความโชว์บอกว่า SingIn OK เป็นเวลา 3.5 วิ
                Toast.makeText(MainActivity.this, "SingIn OK", Toast.LENGTH_SHORT).show();

                // เม็ดตอด finish(); คือ การปิดหน้าต่างที่อยู่อย่างสมบูรณ์ จะไม่สามารถกดปุ่มย้อนกลับมาได้
                finish();

                // ทำการส่งไปที่หน้าควบคุมปลั๊ก จากเดิมเราอยู่หน้า Login คือ MainActivity ย้ายไปยัง hubActivity คือ หน้าควบคุมปลั๊ก
                startActivity(new Intent(MainActivity.this,hubActivity.class));

                // ทำการเรียกใช้เม็ดตอด dismiss(); เพื่อให้ตัวแปร progressDialog ซึ่งมีชนิดข้อมูลเป็น ProgressDialog ทำการหยุดตัวหมุนๆ บนหน้าจอแอพ
                progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // ถ้าเช็คแล้วว่า ข้อมูลไม่มีหรือไม่ถูกต้องจะทำการแสดงข้อความโชว์บอกว่า SingIn Fail เป็นเวลา 3.5 วิ
                Toast.makeText(MainActivity.this, "SingIn Fail", Toast.LENGTH_SHORT).show();

                // ทำการเรียกใช้เม็ดตอด dismiss(); เพื่อให้ตัวแปร progressDialog ซึ่งมีชนิดข้อมูลเป็น ProgressDialog ทำการหยุดตัวหมุนๆ บนหน้าจอแอพ
                progressDialog.dismiss();

            }
        });
    }

}   // Main Class
