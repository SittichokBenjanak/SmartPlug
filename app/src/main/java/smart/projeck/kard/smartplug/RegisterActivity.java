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

    // ประกาศตัวแปรชื่อ buttonRegister มีชนิดข้อมูลเป็น Button
    private Button buttonRegister;

    // ประกาศตัวแปรชื่อ editTextEmail, editTextPassword มีชนิดข้อมูลเป็น EditText
    private EditText editTextEmail, editTextPassword;

    // ประกาศตัวแปรชื่อ progressDialog มีชนิดข้อมูลเป็น ProgressDialog
    private ProgressDialog progressDialog;

    // ประกาศตัวแปรชื่อ firebaseAuth มีชนิดข้อมูลเป็น FirebaseAuth
    private FirebaseAuth firebaseAuth;



    /* โค้ด
     @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        คือโค้ดที่มันสร้างให้เองเราไม่ได้เขียนมันจะสร้างให้เองอัตโนมัติ
        }
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /* ตัวแปรชื่อ firebaseAuth ซึ่งมีชนิดข้อมูลเป็น FirebaseAuth รับความสามารถจาก FirebaseAuth.getInstance();
           เมื่อตัวแปรชื่อ firebaseAuth รับความสามารถแล้ว เราสามารถเอาตัวแปร firebaseAuth ไปทำการเช็ค Email กับ Password
           ที่ผู้ใช้กรอกได้เลย เพราะตัวแปร firebaseAuth ได้ทำการเชื่อมต่อกับ server FirebaseAuth เรียบร้อยแล้ว
         */
        firebaseAuth = FirebaseAuth.getInstance();

        /* ตัวแปรชื่อ progressDialog ซึ่งมีชนิดข้อมูลเป็น ProgressDialog รับความสามารถจาก new ProgressDialog(this);
           ความสามารถที่ได้มา คือ ตัวหมุนที่จะแสดงให้ผู้ใช้อ่านตอนทำการกดปุ่ม OK ในขณะที่รอการเช็คข้อมูล email และ password
         */
        progressDialog = new ProgressDialog(this);

        /* ตัวแปรชื่อ buttonRegister ซึ่งมีชนิดข้อมูลเป็น Button ทำการผูกกับ button2 ที่เป็นชื่อของ Layout ที่เราออกแบบเป็นปุ่มกด OK
           เพื่อที่จะได้รับรู้ว่าผู้ใช้ได้กดปุ่ม OK
         */
        buttonRegister = findViewById(R.id.button2);

        /* ตัวแปรชื่อ editTextEmail ซึ่งมีชนิดข้อมูลเป็น EditText ทำการผูกกับ Emailedit ที่เป็นชื่อของ Layout ที่เราออกแบบเป็นช่องที่กรอก Email
           เพื่อที่จะได้รับค่าที่ผู้ใช้กรอก Email มา เช่นถ้าผู้ใช้กรอก test@hotmail.com
           ตัวแปรชื่อ editTextEmail ก็จะมีค่า = test@hotmail.com
         */
        editTextEmail = findViewById(R.id.Emailedit);

         /* ตัวแปรชื่อ editTextPassword ซึ่งมีชนิดข้อมูลเป็น EditText ทำการผูกกับ Passwordedit ที่เป็นชื่อของ Layout ที่เราออกแบบเป็นช่องที่กรอก Password
           เพื่อที่จะได้รับค่าที่ผู้ใช้กรอก Password มา เช่นถ้าผู้ใช้กรอก test134
           ตัวแปรชื่อ editTextPassword ก็จะมีค่า = test134
         */
        editTextPassword = findViewById(R.id.Passwordedit);

        // ทำการเรียกใช้เม็ดตอด setOnClickListener() เพื่อทำการตั้งการทำงาน คือ ถ้าผู้ใช้กดปุ่ม OK เม็ดตอดที่ชื่อว่า onClick() จะทำงาน
        buttonRegister.setOnClickListener(this);

    }   // onCreate

    //  @Override คือการเรียกใช้เม็ดตอดที่เราได้ทำการประกาศไปคือ setOnClickListener(); ระบบจะทำการให้เรา @Override เม็ดตอดของ onClick() โดยอัตโนมัติ
    @Override
    public void onClick(View view) {
        // ถ้ามีการคลิก ปุ่ม Ok จริงจะทำการเรียกใช้เม็ดตลอด registerUser();
            if (view == buttonRegister) {
                registerUser();
            }   // if 1

    }   // onClick

    /*
         เม็ดตอดชื่อ registerUser() จะทำงานก็ต่อเมื่อผู้ใช้กดปุ่ม OK
    */
    private void registerUser() {

        /* ประกาศตัวแปรที่มีชื่อว่า email มีชนิดข้อมูลเป็น String ให้ทำการรับค่าของตัวแปรชื่อ editTextEmail
           และทำการใช้เม็ดตอด getText() เพื่อรับค่าที่เป็น Text มา แล้วทำการเรียกใช้เม็ดตอด toString() เพื่อที่จะแปลงค่าที่เป็น Text
           ไปเป็นค่า String แทน
           แล้วทำการเรียกใช้เม็ดตอด trim() ต่อ เพื่อทำการตัดช่องว่างออก เช่น ถ้าผู้ใช้กรอกข้อมูลมาว่า test   123 ซึ่งมีช่องว่า
           ระบบจะทำการตัดช่องว่างออก เหลือค่าแค่ test123
           ***** ในกรณีนี้ ค่าที่มีชนิดข้อมูลเป็น String จะไม่สามารถรับค่าตรงๆจากตัวแปรชนิดข้อมูลที่เป็น editText ได้
           เราจึงต้องใช้เม็ดตอด getText() กับ เม็ดตอด toString() มาแปลงค่าให้เหมือนกันก่อนที่จะส่งค่าได้**********
         */
        String email = editTextEmail.getText().toString().trim();

        /* ประกาศตัวแปรที่มีชื่อว่า password มีชนิดข้อมูลเป็น String ให้ทำการรับค่าของตัวแปรชื่อ editTextPassword
           และทำการใช้เม็ดตอด getText() เพื่อรับค่าที่เป็น Text มา แล้วทำการเรียกใช้เม็ดตอด toString() เพื่อที่จะแปลงค่าที่เป็น Text
           ไปเป็นค่า String แทน
           แล้วทำการเรียกใช้เม็ดตอด trim() ต่อ เพื่อทำการตัดช่องว่างออก เช่น ถ้าผู้ใช้กรอกข้อมูลมาว่า test   123 ซึ่งมีช่องว่า
           ระบบจะทำการตัดช่องว่างออก เหลือค่าแค่ test123
           ***** ในกรณีนี้ ค่าที่มีชนิดข้อมูลเป็น String จะไม่สามารถรับค่าตรงๆจากตัวแปรชนิดข้อมูลที่เป็น editText ได้
           เราจึงต้องใช้เม็ดตอด getText() กับ เม็ดตอด toString() มาแปลงค่าให้เหมือนกันก่อนที่จะส่งค่าได้**********
         */
        String password = editTextPassword.getText().toString().trim();

        /*ถ้าตัวแปรชื่อ email ซึ่งมีชนิดข้อมูลเป็น String ไม่มีค่าส่งมาหรือมีค่าเท่ากับว่างเปล่าจะมี Dialog แสดงเตือนว่า "Please check space"
         คือ กรุณาเช็คช่องที่กรอก Email ว่าว่างรึป่าว และจะมีคำบรรยายว่า KeyIn Email กรุณากรอกข้อมูล Email ด้วย
         */
        if (TextUtils.isEmpty(email)) {
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.errorDialog(RegisterActivity.this,"Please check space","KeyIn Email");
            return;
        }   // if

        /*ถ้าตัวแปรชื่อ password ซึ่งมีชนิดข้อมูลเป็น String ไม่มีค่าส่งมาหรือมีค่าเท่ากับว่างเปล่าจะมี Dialog แสดงเตือนว่า "Please check space"
         คือ กรุณาเช็คช่องที่กรอก Password ว่าว่างรึป่าว และจะมีคำบรรยายว่า KeyIn Password กรุณากรอกข้อมูล Password ด้วย
         */
        if (TextUtils.isEmpty(password)) {
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.errorDialog(RegisterActivity.this,"Please check space","KeyIn Password");
            return;
        }   // if

        /*
        ทำการเซ็ตข้อความ Registering User... ให้ตัวแปรชื่อ progressDialog ซึ่งมีชนิดข้อมูลเป็น ProgressDialog โดยการเรียกใช้เม็ดตอด setMessage();
         */
        progressDialog.setMessage("Registering User...");

        /*
        ทำการเรียกใช้เม็ดตอด show(); ให้ตัวแปรชื่อ progressDialog ซึ่งมีชนิดข้อมูลเป็น ProgressDialog แสดงตัวหมุนๆ บนหน้าจอแอพ
         */
        progressDialog.show();

        /*
        ทำการเรียกใช้เม็ดตอด signInWithEmailAndPassword() เพื่อทำการเช็คค่า Email กับ Password ที่ผู้ใช้กรอกเข้ามาว่ามีข้อมูลทางฝั้ง database บน server หรือไม่
         */
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // ถ้าเช็คแล้วว่า ข้อมูลมีจริงหรือถูกต้องจะทำการแสดงข้อความโชว์บอกว่า Register OK เป็นเวลา 3.5 วิ
                Toast.makeText(RegisterActivity.this, "Register OK", Toast.LENGTH_SHORT).show();

                // เม็ดตอด finish(); คือ การปิดหน้าต่างที่อยู่อย่างสมบูรณ์ จะไม่สามารถกดปุ่มย้อนกลับมาได้
                finish();

                // ทำการส่งไปที่หน้า Login จากเดิมเราอยู่หน้า สมัครสมาชิก คือ RegisterActivity ย้ายไปยัง MainActivity คือ หน้า Login
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));

                // ทำการเรียกใช้เม็ดตอด dismiss(); เพื่อให้ตัวแปร progressDialog ซึ่งมีชนิดข้อมูลเป็น ProgressDialog ทำการหยุดตัวหมุนๆ บนหน้าจอแอพ
                progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // ถ้าเช็คแล้วว่า ข้อมูลไม่มีหรือไม่ถูกต้องจะทำการแสดงข้อความโชว์บอกว่า Register Fail เป็นเวลา 3.5 วิ
                Toast.makeText(RegisterActivity.this, "Register Fail", Toast.LENGTH_SHORT).show();

                // ทำการเรียกใช้เม็ดตอด dismiss(); เพื่อให้ตัวแปร progressDialog ซึ่งมีชนิดข้อมูลเป็น ProgressDialog ทำการหยุดตัวหมุนๆ บนหน้าจอแอพ
                progressDialog.dismiss();
            }
        });

    }   // registerUser

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

        // ทำการเซ็ตข้อความที่จะโชว์แสดงให้ผู้ใช้อ่าน ข้อความที่จะแสดง คือ Are you sure Canceled Register?
        dialog.setMessage("Are you sure Canceled Register?");

        // ถ้าผู้ใช้กดปุ่ม Yes โปรแกรมจะทำการเรียกใช้เม็ดตอด finish(); เพื่อทำการปิดหน้าต่างสมัครสมาชิกลง และทำการส่งไปหน้า Login
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //ปิดหน้าต่างสมัครสมาชิกลง
                finish();

                //ทำการส่งไปหน้า Login
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });

        // ถ้าผู้ใช้กดปุ่ม No โปรแกรมจะทำการเรียกใช้เม็ดตอด cancel(); เพื่อทำการให้ dialog ปิดลง แต่ผู้ใช้จะยังอยู่หน้าสมัครสมาชิก
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();

    }   // onBackPressed

}   // Main Class
