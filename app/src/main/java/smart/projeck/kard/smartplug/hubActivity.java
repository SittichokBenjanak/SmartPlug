package smart.projeck.kard.smartplug;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;
import io.netpie.microgear.Microgear;
import io.netpie.microgear.MicrogearEventListener;

public class hubActivity extends AppCompatActivity {

    /*ประกาศตัวแปรชื่อ myRef มีชนิดข้อมูลเป็น DatabaseReference
    public คือ หน้าอื่นสามารถเรียกใช้ตัวแปร myRef ได้
    */
    public DatabaseReference myRef;

    /*ประกาศตัวแปรชื่อ microgear มีชนิดข้อมูลเป็น Microgear
     และรับความสามารถจาก new Microgear(this);
     เราสามารถเอาตัวแปร microgear ไปทำการเชื่อมต่อกับ Netpie ได้เลยเพราะทำการรับความสามารถมาแล้ว
    private คือ หน้าอื่นจะไม่สามารถเรียกใช้ตัวแปร microgear ได้
    */
    private Microgear microgear = new Microgear(this);

    /*ประกาศตัวแปรชื่อ appid มีชนิดข้อมูลเป็น String
    และรับค่าตัวอักษร ControlRelay ไปเก็บไว้ที่ตัวแปร appid
    ตอนนี้ตัวแปร appid มีค่า = ControlRelay
    private คือ หน้าอื่นจะไม่สามารถเรียกใช้ตัวแปร appid ได้
    */
    private String appid = "ControlRelay";

    /*ประกาศตัวแปรชื่อ key มีชนิดข้อมูลเป็น String
    และรับค่าตัวอักษร 0dgTdRU3OOuJyf6 ไปเก็บไว้ที่ตัวแปร key
    ตอนนี้ตัวแปร key มีค่า = 0dgTdRU3OOuJyf6
    private คือ หน้าอื่นจะไม่สามารถเรียกใช้ตัวแปร key ได้
    */
    private String key = "0dgTdRU3OOuJyf6";

    /*ประกาศตัวแปรชื่อ secret มีชนิดข้อมูลเป็น String
    และรับค่าตัวอักษร uMOacjhPHv4UdtdIi81ZmjEEK ไปเก็บไว้ที่ตัวแปร secret
    ตอนนี้ตัวแปร secret มีค่า = uMOacjhPHv4UdtdIi81ZmjEEK
    private คือ หน้าอื่นจะไม่สามารถเรียกใช้ตัวแปร secret ได้
    */
    private String secret = "uMOacjhPHv4UdtdIi81ZmjEEK";

    /*ประกาศตัวแปรชื่อ alias มีชนิดข้อมูลเป็น String
    และรับค่าตัวอักษร Relay ไปเก็บไว้ที่ตัวแปร alias
    ตอนนี้ตัวแปร alias มีค่า = Relay
    private คือ หน้าอื่นจะไม่สามารถเรียกใช้ตัวแปร alias ได้
    */
    private String alias = "Relay";

    /*
     เป็นเม็ดตอดที่ netpie บังคับให้สร้างใช้ในการเชื่อมต่อ กับ netpie
     Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
     };

     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {


        }
    };

    /*  โค้ด
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        คือโค้ดที่มันสร้างให้เองเราไม่ได้เขียนมันจะสร้างให้เองอัตโนมัติ
        }
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        /* ประกาศตัวแปรชื่อ database มีชนิดข้อมูลเป็น FirebaseDatabase
            และทำการรับความสามารถของ FirebaseDatabase.getInstance(); มา
            คือ เราสามารถทำตัวแปรชื่อ database มีชนิดข้อมูลเป็น FirebaseDatabase สามารถรับค่าจากฐานข้อมูลที่อยู่บนserverได้แล้ว
         */
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        /*
           ทำการเรียกใช้เม็ดตอด getReference(); ให้รับค่าจากฐานข้อมูลที่อยู่บน server
           ไปเก็บไว้ในตัวแปรชื่อ myRef ที่มีชนิดข้อมูลเป็น DatabaseReference
         */
        myRef = database.getReference();

        // เรียกใช้เม็ดตอด setconnectfribase(); ให้เม็ดตอด setconnectfribase(); ทำงาน
        setconnectfribase();

        /*
            เป็นเม็ดตอดที่ netpie บังคับให้สร้างใช้ในการเชื่อมต่อ กับ netpie
        MicrogearCallBack callback = new MicrogearCallBack();
        microgear.connect(appid, key, secret, alias);
        microgear.setCallback(callback);
        microgear.subscribe("Relay");
        (new Thread(new Runnable()
        {
            int count = 1;
            @Override
            public void run()
            {
                while (!Thread.interrupted())
                    try
                    {
                        runOnUiThread(new Runnable() // start actions in UI thread
                        {
                            @Override
                            public void run(){
                                //microgear.publish("ControlRelay", String.valueOf(count)+".  Test message");
                                //count++;
                            }
                        });
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                    }
            }
        })).start();

        */
        MicrogearCallBack callback = new MicrogearCallBack();
        microgear.connect(appid, key, secret, alias);
        microgear.setCallback(callback);
        microgear.subscribe("Relay");
        (new Thread(new Runnable()
        {
            int count = 1;
            @Override
            public void run()
            {
                while (!Thread.interrupted())
                    try
                    {
                        runOnUiThread(new Runnable() // start actions in UI thread
                        {
                            @Override
                            public void run(){
                                //microgear.publish("ControlRelay", String.valueOf(count)+".  Test message");
                                //count++;
                            }
                        });
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                    }
            }
        })).start();
        
    }   // onCreate


    /*
        เม็ดตอดนี้จะทำงานก็ต่อเมื่อผู้ใช้เข้ามาที่หน้าควบคุมปลั๊ก
     */
    private void setconnectfribase() {
            /*
                ทำการเรียกใช้เม็ดตอด addValueEventListener() เพื่อที่จะดึงค่าข้อมูลจากฐานข้อมูลจาก server คือ fribase database นั่นเอง
             */
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            /*
                เรียกใช้เม็ดตอด onDataChange() คือ ถ้าข้อมูลมีการเปลี่ยนแปลงค่าเมื่อไหร่ เม็ดตอดนี้จะทำงานทันที
                เพราะ database ของเราเป็นแบบ Real time
             */
            public void onDataChange(DataSnapshot dataSnapshot) {

                /*
                    ประกาศตัวแปรชื่อ map มีชนิดข้อมูลเป็น Map
                    ทำการรับความสามารถจากเม็ดตอด dataSnapshot.getValue();
                    ทำให้ตัวแปร map สามารถ รับค่าข้อมูลจาก database ได้ แค่เพียงกำหนดค่าฟืวที่จะดึงข้อมูล
                 */
                Map map = (Map) dataSnapshot.getValue();

                /*
                    ประกาศตัวแปรชื่อ value1 มีชนิดข้อมูลเป็น String
                    ทำการรับความสามารถจากเม็ดตอด String.valueOf(map.get("Name1"));
                    ทำให้ตัวแปร value1 สามารถ รับค่าข้อมูลจากฟิวที่มืชื่อว่า Name1 ได้
                    เช่น ฟิว Name1 มีค่าคือ พัดลม
                    ตัวแปร value1 ซึ่งมีชนิดข้อมูลเป็น String จะมีค่า = พัดลม
                 */
                String value1 = String.valueOf(map.get("Name1"));

                /*
                    ประกาศตัวแปรชื่อ value2 มีชนิดข้อมูลเป็น String
                    ทำการรับความสามารถจากเม็ดตอด String.valueOf(map.get("Name2"));
                    ทำให้ตัวแปร value2 สามารถ รับค่าข้อมูลจากฟิวที่มืชื่อว่า Name2 ได้
                    เช่น ฟิว Name2 มีค่าคือ ทีวี
                    ตัวแปร value2 ซึ่งมีชนิดข้อมูลเป็น String จะมีค่า = ทีวี
                 */
                String value2 = String.valueOf(map.get("Name2"));

                /*
                    ประกาศตัวแปรชื่อ value3 มีชนิดข้อมูลเป็น String
                    ทำการรับความสามารถจากเม็ดตอด String.valueOf(map.get("Name3"));
                    ทำให้ตัวแปร value3 สามารถ รับค่าข้อมูลจากฟิวที่มืชื่อว่า Name3 ได้
                    เช่น ฟิว Name3 มีค่าคือ ตู้เย็น
                    ตัวแปร value3 ซึ่งมีชนิดข้อมูลเป็น String จะมีค่า = ตู้เย็น
                 */
                String value3 = String.valueOf(map.get("Name3"));

                /*
                    ประกาศตัวแปรชื่อ value4 มีชนิดข้อมูลเป็น String
                    ทำการรับความสามารถจากเม็ดตอด String.valueOf(map.get("Name4"));
                    ทำให้ตัวแปร value4 สามารถ รับค่าข้อมูลจากฟิวที่มืชื่อว่า Name4 ได้
                    เช่น ฟิว Name4 มีค่าคือ คอมพิวเตอร์
                    ตัวแปร value4 ซึ่งมีชนิดข้อมูลเป็น String จะมีค่า = คอมพิวเตอร์
                 */
                String value4 = String.valueOf(map.get("Name4"));


                // ประกาศตัวแปรชื่อ Name1, Name2, Name3, Name4 มีชนิดข้อมูลเป็น TextView
                TextView Name1, Name2, Name3, Name4;

                // ตัวแปรชื่อ Name1 ซึ่งมีชนิดข้อมูลเป็น EditText ทำการผูกกับ textView6 ที่เป็นชื่อของ Layout ที่เราออกแบบเป็นข้อความ text
                Name1 = findViewById(R.id.textView6);

                // ตัวแปรชื่อ Name2 ซึ่งมีชนิดข้อมูลเป็น EditText ทำการผูกกับ textView9 ที่เป็นชื่อของ Layout ที่เราออกแบบเป็นข้อความ text
                Name2 = findViewById(R.id.textView9);

                // ตัวแปรชื่อ Name3 ซึ่งมีชนิดข้อมูลเป็น EditText ทำการผูกกับ textView12 ที่เป็นชื่อของ Layout ที่เราออกแบบเป็นข้อความ text
                Name3 = findViewById(R.id.textView12);

                // ตัวแปรชื่อ Name4 ซึ่งมีชนิดข้อมูลเป็น EditText ทำการผูกกับ textView15 ที่เป็นชื่อของ Layout ที่เราออกแบบเป็นข้อความ text
                Name4 = findViewById(R.id.textView15);

                /*
                    ทำการเรียกใช้เม็ดตอด setText(); ซึ่งทำการใส่ค่าตัวแปร value1 มาด้วย
                    เช่น value1 มีค่า = พัดลม จะนำคำว่าพัดลม ไปทำการเซ็คค่าให้กับ Name1
                    ซึ่งตัวแปรชื่อ Name1 มีชนิดข้อมูลเป็น EditText ได้ทำการผูกกับ textView6 ที่เป็นชื่อของ Layout ที่เราออกแบบเป็นข้อความ text ไว้แล้ว
                    มันจะทำการรับค่าคำว่า พัดลม ไปแสดงบนหน้าจอว่า พัดลม
                 */
                Name1.setText(value1);

                /*
                    ทำการเรียกใช้เม็ดตอด setText(); ซึ่งทำการใส่ค่าตัวแปร value2 มาด้วย
                    เช่น value2 มีค่า = ทีวี จะนำคำว่า ทีวี ไปทำการเซ็คค่าให้กับ Name2
                    ซึ่งตัวแปรชื่อ Name2 มีชนิดข้อมูลเป็น EditText ได้ทำการผูกกับ textView9 ที่เป็นชื่อของ Layout ที่เราออกแบบเป็นข้อความ text ไว้แล้ว
                    มันจะทำการรับค่าคำว่า ทีวี ไปแสดงบนหน้าจอว่า ทีวี
                 */
                Name2.setText(value2);

                /*
                    ทำการเรียกใช้เม็ดตอด setText(); ซึ่งทำการใส่ค่าตัวแปร value3 มาด้วย
                    เช่น value3 มีค่า = ตู้เย็น จะนำคำว่า ตู้เย็น ไปทำการเซ็คค่าให้กับ Name3
                    ซึ่งตัวแปรชื่อ Name3 มีชนิดข้อมูลเป็น EditText ได้ทำการผูกกับ textView12 ที่เป็นชื่อของ Layout ที่เราออกแบบเป็นข้อความ text ไว้แล้ว
                    มันจะทำการรับค่าคำว่า ตู้เย็น ไปแสดงบนหน้าจอว่า ตู้เย็น
                 */
                Name3.setText(value3);

                /*
                    ทำการเรียกใช้เม็ดตอด setText(); ซึ่งทำการใส่ค่าตัวแปร value4 มาด้วย
                    เช่น value4 มีค่า = คอมพิวเตอร์ จะนำคำว่า คอมพิวเตอร์ ไปทำการเซ็คค่าให้กับ Name4
                    ซึ่งตัวแปรชื่อ Name4 มีชนิดข้อมูลเป็น EditText ได้ทำการผูกกับ textView15 ที่เป็นชื่อของ Layout ที่เราออกแบบเป็นข้อความ text ไว้แล้ว
                    มันจะทำการรับค่าคำว่า คอมพิวเตอร์ ไปแสดงบนหน้าจอว่า คอมพิวเตอร์
                 */
                Name4.setText(value4);


                /*
                    ประกาศตัวแปรชื่อ Relayvalue1 มีชนิดข้อมูลเป็น String
                    ทำการรับความสามารถจากเม็ดตอด String.valueOf(map.get("Relay1"));
                    ทำให้ตัวแปร Relayvalue1 สามารถ รับค่าข้อมูลจากฟิวที่มืชื่อว่า Relay1 ได้
                    เช่น ฟิว Relay1 มีค่าคือ 1
                    ตัวแปร Relayvalue1 ซึ่งมีชนิดข้อมูลเป็น String จะมีค่า = 1
                 */
                String Relayvalue1 = String.valueOf(map.get("Relay1"));

                /*
                    ประกาศตัวแปรชื่อ Relayvalue2 มีชนิดข้อมูลเป็น String
                    ทำการรับความสามารถจากเม็ดตอด String.valueOf(map.get("Relay2"));
                    ทำให้ตัวแปร Relayvalue2 สามารถ รับค่าข้อมูลจากฟิวที่มืชื่อว่า Relay2 ได้
                    เช่น ฟิว Relay2 มีค่าคือ 2
                    ตัวแปร Relayvalue2 ซึ่งมีชนิดข้อมูลเป็น String จะมีค่า = 2
                 */
                String Relayvalue2 = String.valueOf(map.get("Relay2"));

                /*
                    ประกาศตัวแปรชื่อ Relayvalue3 มีชนิดข้อมูลเป็น String
                    ทำการรับความสามารถจากเม็ดตอด String.valueOf(map.get("Relay3"));
                    ทำให้ตัวแปร Relayvalue3 สามารถ รับค่าข้อมูลจากฟิวที่มืชื่อว่า Relay3 ได้
                    เช่น ฟิว Relay3 มีค่าคือ 3
                    ตัวแปร Relayvalue3 ซึ่งมีชนิดข้อมูลเป็น String จะมีค่า = 3
                 */
                String Relayvalue3 = String.valueOf(map.get("Relay3"));

                /*
                    ประกาศตัวแปรชื่อ Relayvalue4 มีชนิดข้อมูลเป็น String
                    ทำการรับความสามารถจากเม็ดตอด String.valueOf(map.get("Relay4"));
                    ทำให้ตัวแปร Relayvalue4 สามารถ รับค่าข้อมูลจากฟิวที่มืชื่อว่า Relay4 ได้
                    เช่น ฟิว Relay4 มีค่าคือ 4
                    ตัวแปร Relayvalue4 ซึ่งมีชนิดข้อมูลเป็น String จะมีค่า = 4
                 */
                String Relayvalue4 = String.valueOf(map.get("Relay4"));

                // ประกาศตัวแปรชื่อ radioButton1, radioButton2, radioButton3, radioButton4 มีชนิดข้อมูลเป็น RadioButton
                RadioButton radioButton1, radioButton2, radioButton3, radioButton4;

                // ตัวแปรชื่อ radioButton1 ซึ่งมีชนิดข้อมูลเป็น RadioButton ทำการผูกกับ radioButton ที่เป็นชื่อของ Layout ที่เราออกแบบเป็น ปุ่มไฟแสดงสถานะเปิดปิดของปลั๊ก
                radioButton1 = findViewById(R.id.radioButton);

                // ตัวแปรชื่อ radioButton2 ซึ่งมีชนิดข้อมูลเป็น RadioButton ทำการผูกกับ radioButton2 ที่เป็นชื่อของ Layout ที่เราออกแบบเป็น ปุ่มไฟแสดงสถานะเปิดปิดของปลั๊ก
                radioButton2 = findViewById(R.id.radioButton2);

                // ตัวแปรชื่อ radioButton3 ซึ่งมีชนิดข้อมูลเป็น RadioButton ทำการผูกกับ radioButton3 ที่เป็นชื่อของ Layout ที่เราออกแบบเป็น ปุ่มไฟแสดงสถานะเปิดปิดของปลั๊ก
                radioButton3 = findViewById(R.id.radioButton3);

                // ตัวแปรชื่อ radioButton4 ซึ่งมีชนิดข้อมูลเป็น RadioButton ทำการผูกกับ radioButton4 ที่เป็นชื่อของ Layout ที่เราออกแบบเป็น ปุ่มไฟแสดงสถานะเปิดปิดของปลั๊ก
                radioButton4 = findViewById(R.id.radioButton4);

                /* ทำการเรียกใช้เม็ดตอด setEnabled(false); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton1 ไม่ทำงาน
                   ซึ่งตัวแปรชื่อ radioButton1 มีชนิดข้อมูลเป็น RadioButton ได้ทำการผูกกับ radioButton ที่เป็นชื่อของ Layout
                   ที่เราออกแบบเป็น ปุ่มไฟแสดงสถานะเปิดปิดของปลั๊กเรียบร้อยแล้ว เราจึงสามารถเขียนคำสั่งเรียกใช้ผ่านตัวแปร radioButton1 ได้โดยตรง
                 */
                radioButton1.setEnabled(false);

                /* ทำการเรียกใช้เม็ดตอด setEnabled(false); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton2 ไม่ทำงาน
                   ซึ่งตัวแปรชื่อ radioButton2 มีชนิดข้อมูลเป็น RadioButton ได้ทำการผูกกับ radioButton2 ที่เป็นชื่อของ Layout
                   ที่เราออกแบบเป็น ปุ่มไฟแสดงสถานะเปิดปิดของปลั๊กเรียบร้อยแล้ว เราจึงสามารถเขียนคำสั่งเรียกใช้ผ่านตัวแปร radioButton2 ได้โดยตรง
                 */
                radioButton2.setEnabled(false);

                /* ทำการเรียกใช้เม็ดตอด setEnabled(false); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton3 ไม่ทำงาน
                   ซึ่งตัวแปรชื่อ radioButton3 มีชนิดข้อมูลเป็น RadioButton ได้ทำการผูกกับ radioButton3 ที่เป็นชื่อของ Layout
                   ที่เราออกแบบเป็น ปุ่มไฟแสดงสถานะเปิดปิดของปลั๊กเรียบร้อยแล้ว เราจึงสามารถเขียนคำสั่งเรียกใช้ผ่านตัวแปร radioButton3 ได้โดยตรง
                 */
                radioButton3.setEnabled(false);

                /* ทำการเรียกใช้เม็ดตอด setEnabled(false); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton4 ไม่ทำงาน
                   ซึ่งตัวแปรชื่อ radioButton4 มีชนิดข้อมูลเป็น RadioButton ได้ทำการผูกกับ radioButton4 ที่เป็นชื่อของ Layout
                   ที่เราออกแบบเป็น ปุ่มไฟแสดงสถานะเปิดปิดของปลั๊กเรียบร้อยแล้ว เราจึงสามารถเขียนคำสั่งเรียกใช้ผ่านตัวแปร radioButton4 ได้โดยตรง
                 */
                radioButton4.setEnabled(false);

                // ถ้าตัวแปรชื่อ Relayvalue1 มีค่า = 1 จะทำงานใน if
                if (Relayvalue1.equals("1")) {

                    // ทำการเรียกใช้เม็ดตอด setEnabled(true); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton1 ทำงาน
                    radioButton1.setEnabled(true);

                    // ทำการเรียกใช้เม็ดตอด setChecked(true); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton1 ไฟติด
                    radioButton1.setChecked(true);

                    // ทำการเรียกใช้เม็ดตอด setText("ON"); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton1 เปลี่ยนข้อความเป้น ON
                    radioButton1.setText("ON");


                    // ถ้าตัวแปรชื่อ Relayvalue1 มีค่า = 0 จะทำงานใน else if
                } else if (Relayvalue1.equals("0")) {

                    // ทำการเรียกใช้เม็ดตอด setChecked(false); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton1 ไฟดับ
                    radioButton1.setChecked(false);

                    // ทำการเรียกใช้เม็ดตอด setText("OFF"); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton1 เปลี่ยนข้อความเป้น OFF
                    radioButton1.setText("OFF");
                }

                // ถ้าตัวแปรชื่อ Relayvalue2 มีค่า = 1 จะทำงานใน if
                if (Relayvalue2.equals("1")) {

                    // ทำการเรียกใช้เม็ดตอด setEnabled(true); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton2 ทำงาน
                    radioButton2.setEnabled(true);

                    // ทำการเรียกใช้เม็ดตอด setChecked(true); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton2 ไฟติด
                    radioButton2.setChecked(true);

                    // ทำการเรียกใช้เม็ดตอด setText("ON"); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton2 เปลี่ยนข้อความเป้น ON
                    radioButton2.setText("ON");

                    // ถ้าตัวแปรชื่อ Relayvalue2 มีค่า = 0 จะทำงานใน else if
                } else if (Relayvalue2.equals("0")) {

                    // ทำการเรียกใช้เม็ดตอด setChecked(false); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton2 ไฟดับ
                    radioButton2.setChecked(false);

                    // ทำการเรียกใช้เม็ดตอด setText("OFF"); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton2 เปลี่ยนข้อความเป้น OFF
                    radioButton2.setText("OFF");
                }

                // ถ้าตัวแปรชื่อ Relayvalue3 มีค่า = 1 จะทำงานใน if
                if (Relayvalue3.equals("1")) {

                    // ทำการเรียกใช้เม็ดตอด setEnabled(true); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton3 ทำงาน
                    radioButton3.setEnabled(true);

                    // ทำการเรียกใช้เม็ดตอด setChecked(true); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton3 ไฟติด
                    radioButton3.setChecked(true);

                    // ทำการเรียกใช้เม็ดตอด setText("ON"); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton3 เปลี่ยนข้อความเป้น ON
                    radioButton3.setText("ON");

                    // ถ้าตัวแปรชื่อ Relayvalue3 มีค่า = 0 จะทำงานใน else if
                } else if (Relayvalue3.equals("0")) {

                    // ทำการเรียกใช้เม็ดตอด setChecked(false); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton3 ไฟดับ
                    radioButton3.setChecked(false);

                    // ทำการเรียกใช้เม็ดตอด setText("OFF"); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton3 เปลี่ยนข้อความเป้น OFF
                    radioButton3.setText("OFF");
                }

                // ถ้าตัวแปรชื่อ Relayvalue4 มีค่า = 1 จะทำงานใน if
                if (Relayvalue4.equals("1")) {

                    // ทำการเรียกใช้เม็ดตอด setEnabled(true); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton4 ทำงาน
                    radioButton4.setEnabled(true);

                    // ทำการเรียกใช้เม็ดตอด setChecked(true); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton4 ไฟติด
                    radioButton4.setChecked(true);

                    // ทำการเรียกใช้เม็ดตอด setText("ON"); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton4 เปลี่ยนข้อความเป้น ON
                    radioButton4.setText("ON");

                    // ถ้าตัวแปรชื่อ Relayvalue4 มีค่า = 0 จะทำงานใน else if
                } else if (Relayvalue4.equals("0")) {

                    // ทำการเรียกใช้เม็ดตอด setChecked(false); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton4 ไฟดับ
                    radioButton4.setChecked(false);

                    // ทำการเรียกใช้เม็ดตอด setText("OFF"); ทำให้ปุ่มไฟเปิดปิดที่ชื่อว่า radioButton4 เปลี่ยนข้อความเป้น OFF
                    radioButton4.setText("OFF");
                }

            }

            /*
                เรียกใช้เม็ดตอด onCancelled() คือ ถ้าเชื่อมต่อฐานข้อมูลไม่ได้ เม็ดตอดนี้จะทำงาน
                แต่ในที่นี้เราไม่ได้ใช้คำสั่งอะไรในเม็ดตอดนี้เราปล่อยว่างไว้เฉยๆ
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // เราไม่ได้เขียนคำสั่งอะไร คือ ถ้ามัน error ก็จะไม่ทำอะไร
            }

        });
    }   // setconnectfribase


    /*  *************************************************************************
        ******************************** ปุ่ม ON **********************************
        *************************************************************************
     */

    /*
        เรียกใช้เม็ดตอด ClickOnRelay1() ถ้ามีการกดปุ่ม ON ของปลั๊กที่ 1 เม็ดตอดนี้จะทำงาน
     */
    public void ClickOnRelay1(View view) {

        /* ตัวแปรชื่อ microgear ซึ่งมีชนิดข้อมูลเป็น Microgear ได้ทำการเชื่อมต่อกับ netpie
           ผ่านคำสั่ง microgear.connect(appid, key, secret, alias); ที่เราเขียนโค้ดไว้ด้านบนเรียบร้อยแล้ว
           เราจึงสามารถทำการเขียนคำสั่งส่งค่าไปยัง netpie ได้เลย ผ่านตัวแปร microgear

           ในที่นี้เราเลยใช้เม็ดตอด chat(); ในการส่งค่า DEV41 ไปยังห้องที่มีชื่อว่า Relay
         */
        microgear.chat("Relay","DEV41");

        /*
           ประกาศตัวแปรชื่อ resetStatus ซึ่งมีชนิดข้อมูลเป็น Map<String, Object> ให้ทำการรับความสามารถของ HashMap<String, Object>();
           เพื่อที่จะให้ตัวแปร resetStatus สามารถแก้ไขฐานข้อมูลที่อยู่ทางฝั่งของ Firebase database ได้
         */
        final Map<String, Object> resetStatus = new HashMap<String, Object>();

        /*
           เรียกใช้เม็ดตอด put(); ในการส่งค่า 1 ไปยังตารางที่มีชื่อว่า Relay1
         */
        resetStatus.put("Relay1", "1");

        /*
           เรียกใช้เม็ดตอด updateChildren() เพื่อที่จะอัปเดทค่า 1 ไปยังตารางที่มีชื่อว่า Relay1
           โดยทำการรับค่ามาจากตัวแปร resetStatus และทำการส่งค่าไปยังตัวแปร myRef
           ตัวแปรชื่อ myRef ที่มีชนิดข้อมูลเป็น DatabaseReference จึงสามารถอัปเดทข้อมูลไปยังฐานข้อมูลได้
         */
        myRef.updateChildren(resetStatus);

    }   // ClickOnRelay1

    /*
        เรียกใช้เม็ดตอด ClickOnRelay2() ถ้ามีการกดปุ่ม ON ของปลั๊กที่ 2 เม็ดตอดนี้จะทำงาน
     */
    public void ClickOnRelay2(View view) {

        /* ตัวแปรชื่อ microgear ซึ่งมีชนิดข้อมูลเป็น Microgear ได้ทำการเชื่อมต่อกับ netpie
           ผ่านคำสั่ง microgear.connect(appid, key, secret, alias); ที่เราเขียนโค้ดไว้ด้านบนเรียบร้อยแล้ว
           เราจึงสามารถทำการเขียนคำสั่งส่งค่าไปยัง netpie ได้เลย ผ่านตัวแปร microgear

           ในที่นี้เราเลยใช้เม็ดตอด chat(); ในการส่งค่า DEV31 ไปยังห้องที่มีชื่อว่า Relay
         */
        microgear.chat("Relay","DEV31");

        /*
           ประกาศตัวแปรชื่อ resetStatus ซึ่งมีชนิดข้อมูลเป็น Map<String, Object> ให้ทำการรับความสามารถของ HashMap<String, Object>();
           เพื่อที่จะให้ตัวแปร resetStatus สามารถแก้ไขฐานข้อมูลที่อยู่ทางฝั่งของ Firebase database ได้
         */
        final Map<String, Object> resetStatus = new HashMap<String, Object>();

        /*
           เรียกใช้เม็ดตอด put(); ในการส่งค่า 1 ไปยังตารางที่มีชื่อว่า Relay2
         */
        resetStatus.put("Relay2", "1");

        /*
           เรียกใช้เม็ดตอด updateChildren() เพื่อที่จะอัปเดทค่า 1 ไปยังตารางที่มีชื่อว่า Relay2
           โดยทำการรับค่ามาจากตัวแปร resetStatus และทำการส่งค่าไปยังตัวแปร myRef
           ตัวแปรชื่อ myRef ที่มีชนิดข้อมูลเป็น DatabaseReference จึงสามารถอัปเดทข้อมูลไปยังฐานข้อมูลได้
         */
        myRef.updateChildren(resetStatus);

    }   // ClickOnRelay2

    /*
        เรียกใช้เม็ดตอด ClickOnRelay3() ถ้ามีการกดปุ่ม ON ของปลั๊กที่ 3 เม็ดตอดนี้จะทำงาน
     */
    public void ClickOnRelay3(View view) {

        /* ตัวแปรชื่อ microgear ซึ่งมีชนิดข้อมูลเป็น Microgear ได้ทำการเชื่อมต่อกับ netpie
           ผ่านคำสั่ง microgear.connect(appid, key, secret, alias); ที่เราเขียนโค้ดไว้ด้านบนเรียบร้อยแล้ว
           เราจึงสามารถทำการเขียนคำสั่งส่งค่าไปยัง netpie ได้เลย ผ่านตัวแปร microgear

           ในที่นี้เราเลยใช้เม็ดตอด chat(); ในการส่งค่า DEV21 ไปยังห้องที่มีชื่อว่า Relay
         */
        microgear.chat("Relay","DEV21");

        /*
           ประกาศตัวแปรชื่อ resetStatus ซึ่งมีชนิดข้อมูลเป็น Map<String, Object> ให้ทำการรับความสามารถของ HashMap<String, Object>();
           เพื่อที่จะให้ตัวแปร resetStatus สามารถแก้ไขฐานข้อมูลที่อยู่ทางฝั่งของ Firebase database ได้
         */
        final Map<String, Object> resetStatus = new HashMap<String, Object>();

        /*
           เรียกใช้เม็ดตอด put(); ในการส่งค่า 1 ไปยังตารางที่มีชื่อว่า Relay3
         */
        resetStatus.put("Relay3", "1");

        /*
           เรียกใช้เม็ดตอด updateChildren() เพื่อที่จะอัปเดทค่า 1 ไปยังตารางที่มีชื่อว่า Relay3
           โดยทำการรับค่ามาจากตัวแปร resetStatus และทำการส่งค่าไปยังตัวแปร myRef
           ตัวแปรชื่อ myRef ที่มีชนิดข้อมูลเป็น DatabaseReference จึงสามารถอัปเดทข้อมูลไปยังฐานข้อมูลได้
         */
        myRef.updateChildren(resetStatus);

    }   // ClickOnRelay3

    /*
        เรียกใช้เม็ดตอด ClickOnRelay4() ถ้ามีการกดปุ่ม ON ของปลั๊กที่ 4 เม็ดตอดนี้จะทำงาน
     */
    public void ClickOnRelay4(View view) {

        /* ตัวแปรชื่อ microgear ซึ่งมีชนิดข้อมูลเป็น Microgear ได้ทำการเชื่อมต่อกับ netpie
           ผ่านคำสั่ง microgear.connect(appid, key, secret, alias); ที่เราเขียนโค้ดไว้ด้านบนเรียบร้อยแล้ว
           เราจึงสามารถทำการเขียนคำสั่งส่งค่าไปยัง netpie ได้เลย ผ่านตัวแปร microgear

           ในที่นี้เราเลยใช้เม็ดตอด chat(); ในการส่งค่า DEV11 ไปยังห้องที่มีชื่อว่า Relay
         */
        microgear.chat("Relay","DEV11");

        /*
           ประกาศตัวแปรชื่อ resetStatus ซึ่งมีชนิดข้อมูลเป็น Map<String, Object> ให้ทำการรับความสามารถของ HashMap<String, Object>();
           เพื่อที่จะให้ตัวแปร resetStatus สามารถแก้ไขฐานข้อมูลที่อยู่ทางฝั่งของ Firebase database ได้
         */
        final Map<String, Object> resetStatus = new HashMap<String, Object>();

        /*
           เรียกใช้เม็ดตอด put(); ในการส่งค่า 1 ไปยังตารางที่มีชื่อว่า Relay4
         */
        resetStatus.put("Relay4", "1");

        /*
           เรียกใช้เม็ดตอด updateChildren() เพื่อที่จะอัปเดทค่า 1 ไปยังตารางที่มีชื่อว่า Relay4
           โดยทำการรับค่ามาจากตัวแปร resetStatus และทำการส่งค่าไปยังตัวแปร myRef
           ตัวแปรชื่อ myRef ที่มีชนิดข้อมูลเป็น DatabaseReference จึงสามารถอัปเดทข้อมูลไปยังฐานข้อมูลได้
         */
        myRef.updateChildren(resetStatus);

    }   // ClickOnRelay4

     /* *************************************************************************
        ******************************** ปุ่ม OFF *********************************
        *************************************************************************
     */

    /*
        เรียกใช้เม็ดตอด ClickOffRelay1() ถ้ามีการกดปุ่ม OFF ของปลั๊กที่ 1 เม็ดตอดนี้จะทำงาน
     */
    public void ClickOffRelay1(View view) {

        /* ตัวแปรชื่อ microgear ซึ่งมีชนิดข้อมูลเป็น Microgear ได้ทำการเชื่อมต่อกับ netpie
           ผ่านคำสั่ง microgear.connect(appid, key, secret, alias); ที่เราเขียนโค้ดไว้ด้านบนเรียบร้อยแล้ว
           เราจึงสามารถทำการเขียนคำสั่งส่งค่าไปยัง netpie ได้เลย ผ่านตัวแปร microgear

           ในที่นี้เราเลยใช้เม็ดตอด chat(); ในการส่งค่า DEV40 ไปยังห้องที่มีชื่อว่า Relay
         */
        microgear.chat("Relay","DEV40");

        /*
           ประกาศตัวแปรชื่อ resetStatus ซึ่งมีชนิดข้อมูลเป็น Map<String, Object> ให้ทำการรับความสามารถของ HashMap<String, Object>();
           เพื่อที่จะให้ตัวแปร resetStatus สามารถแก้ไขฐานข้อมูลที่อยู่ทางฝั่งของ Firebase database ได้
         */
        final Map<String, Object> resetStatus = new HashMap<String, Object>();

        /*
           เรียกใช้เม็ดตอด put(); ในการส่งค่า 0 ไปยังตารางที่มีชื่อว่า Relay1
         */
        resetStatus.put("Relay1", "0");

        /*
           เรียกใช้เม็ดตอด updateChildren() เพื่อที่จะอัปเดทค่า 0 ไปยังตารางที่มีชื่อว่า Relay1
           โดยทำการรับค่ามาจากตัวแปร resetStatus และทำการส่งค่าไปยังตัวแปร myRef
           ตัวแปรชื่อ myRef ที่มีชนิดข้อมูลเป็น DatabaseReference จึงสามารถอัปเดทข้อมูลไปยังฐานข้อมูลได้
         */
        myRef.updateChildren(resetStatus);

    }   // ClickOffRelay1

    /*
        เรียกใช้เม็ดตอด ClickOffRelay2() ถ้ามีการกดปุ่ม OFF ของปลั๊กที่ 2 เม็ดตอดนี้จะทำงาน
     */
    public void ClickOffRelay2(View view) {

        /* ตัวแปรชื่อ microgear ซึ่งมีชนิดข้อมูลเป็น Microgear ได้ทำการเชื่อมต่อกับ netpie
           ผ่านคำสั่ง microgear.connect(appid, key, secret, alias); ที่เราเขียนโค้ดไว้ด้านบนเรียบร้อยแล้ว
           เราจึงสามารถทำการเขียนคำสั่งส่งค่าไปยัง netpie ได้เลย ผ่านตัวแปร microgear

           ในที่นี้เราเลยใช้เม็ดตอด chat(); ในการส่งค่า DEV30 ไปยังห้องที่มีชื่อว่า Relay
         */
        microgear.chat("Relay","DEV30");

        /*
           ประกาศตัวแปรชื่อ resetStatus ซึ่งมีชนิดข้อมูลเป็น Map<String, Object> ให้ทำการรับความสามารถของ HashMap<String, Object>();
           เพื่อที่จะให้ตัวแปร resetStatus สามารถแก้ไขฐานข้อมูลที่อยู่ทางฝั่งของ Firebase database ได้
         */
        final Map<String, Object> resetStatus = new HashMap<String, Object>();

        /*
           เรียกใช้เม็ดตอด put(); ในการส่งค่า 0 ไปยังตารางที่มีชื่อว่า Relay2
         */
        resetStatus.put("Relay2", "0");

        /*
           เรียกใช้เม็ดตอด updateChildren() เพื่อที่จะอัปเดทค่า 0 ไปยังตารางที่มีชื่อว่า Relay2
           โดยทำการรับค่ามาจากตัวแปร resetStatus และทำการส่งค่าไปยังตัวแปร myRef
           ตัวแปรชื่อ myRef ที่มีชนิดข้อมูลเป็น DatabaseReference จึงสามารถอัปเดทข้อมูลไปยังฐานข้อมูลได้
         */
        myRef.updateChildren(resetStatus);

    }   // ClickOffRelay2

    /*
        เรียกใช้เม็ดตอด ClickOffRelay3() ถ้ามีการกดปุ่ม OFF ของปลั๊กที่ 3 เม็ดตอดนี้จะทำงาน
     */
    public void ClickOffRelay3(View view) {

        /* ตัวแปรชื่อ microgear ซึ่งมีชนิดข้อมูลเป็น Microgear ได้ทำการเชื่อมต่อกับ netpie
           ผ่านคำสั่ง microgear.connect(appid, key, secret, alias); ที่เราเขียนโค้ดไว้ด้านบนเรียบร้อยแล้ว
           เราจึงสามารถทำการเขียนคำสั่งส่งค่าไปยัง netpie ได้เลย ผ่านตัวแปร microgear

           ในที่นี้เราเลยใช้เม็ดตอด chat(); ในการส่งค่า DEV20 ไปยังห้องที่มีชื่อว่า Relay
         */
        microgear.chat("Relay","DEV20");

        /*
           ประกาศตัวแปรชื่อ resetStatus ซึ่งมีชนิดข้อมูลเป็น Map<String, Object> ให้ทำการรับความสามารถของ HashMap<String, Object>();
           เพื่อที่จะให้ตัวแปร resetStatus สามารถแก้ไขฐานข้อมูลที่อยู่ทางฝั่งของ Firebase database ได้
         */
        final Map<String, Object> resetStatus = new HashMap<String, Object>();

        /*
           เรียกใช้เม็ดตอด put(); ในการส่งค่า 0 ไปยังตารางที่มีชื่อว่า Relay3
         */
        resetStatus.put("Relay3", "0");

        /*
           เรียกใช้เม็ดตอด updateChildren() เพื่อที่จะอัปเดทค่า 0 ไปยังตารางที่มีชื่อว่า Relay3
           โดยทำการรับค่ามาจากตัวแปร resetStatus และทำการส่งค่าไปยังตัวแปร myRef
           ตัวแปรชื่อ myRef ที่มีชนิดข้อมูลเป็น DatabaseReference จึงสามารถอัปเดทข้อมูลไปยังฐานข้อมูลได้
         */
        myRef.updateChildren(resetStatus);

    }   // ClickOffRelay3

    /*
        เรียกใช้เม็ดตอด ClickOffRelay4() ถ้ามีการกดปุ่ม OFF ของปลั๊กที่ 4 เม็ดตอดนี้จะทำงาน
     */
    public void ClickOffRelay4(View view) {

        /* ตัวแปรชื่อ microgear ซึ่งมีชนิดข้อมูลเป็น Microgear ได้ทำการเชื่อมต่อกับ netpie
           ผ่านคำสั่ง microgear.connect(appid, key, secret, alias); ที่เราเขียนโค้ดไว้ด้านบนเรียบร้อยแล้ว
           เราจึงสามารถทำการเขียนคำสั่งส่งค่าไปยัง netpie ได้เลย ผ่านตัวแปร microgear

           ในที่นี้เราเลยใช้เม็ดตอด chat(); ในการส่งค่า DEV10 ไปยังห้องที่มีชื่อว่า Relay
         */
        microgear.chat("Relay","DEV10");

        /*
           ประกาศตัวแปรชื่อ resetStatus ซึ่งมีชนิดข้อมูลเป็น Map<String, Object> ให้ทำการรับความสามารถของ HashMap<String, Object>();
           เพื่อที่จะให้ตัวแปร resetStatus สามารถแก้ไขฐานข้อมูลที่อยู่ทางฝั่งของ Firebase database ได้
         */
        final Map<String, Object> resetStatus = new HashMap<String, Object>();

        /*
           เรียกใช้เม็ดตอด put(); ในการส่งค่า 0 ไปยังตารางที่มีชื่อว่า Relay4
         */
        resetStatus.put("Relay4", "0");

        /*
           เรียกใช้เม็ดตอด updateChildren() เพื่อที่จะอัปเดทค่า 0 ไปยังตารางที่มีชื่อว่า Relay4
           โดยทำการรับค่ามาจากตัวแปร resetStatus และทำการส่งค่าไปยังตัวแปร myRef
           ตัวแปรชื่อ myRef ที่มีชนิดข้อมูลเป็น DatabaseReference จึงสามารถอัปเดทข้อมูลไปยังฐานข้อมูลได้
         */
        myRef.updateChildren(resetStatus);

    }   // ClickOffRelay4

    /* *************************************************************************
        ******************************** ปุ่ม Edit *********************************
        *************************************************************************
     */

    /*
        เรียกใช้เม็ดตอด ClickEdit1() ถ้ามีการกดปุ่ม Edit ของปลั๊กที่ 1 เม็ดตอดนี้จะทำงาน
     */
    public void ClickEdit1(View view) {

        /* ประกาศตัวแปรชื่อ builder มีชนิดข้อมูลเป็น AlertDialog.Builder และเป็นตัวแปรที่กำหนดเป็นแบบ final คือ ไม่สามารถเปลี่ยนแปลงค่าได้
           ให้ตัวแปร builder ทำการรับความสามารถของ เม็ดตอด AlertDialog.Builder(hubActivity.this) ในการสร้าง AlertDialog ในหน้า hubActivity
         */
        final AlertDialog.Builder builder = new AlertDialog.Builder(hubActivity.this);

        /* ประกาศตัวแปรชื่อ inflater มีชนิดข้อมูลเป็น LayoutInflater ให้ทำการรับความสามารถของเม็ดตอด getLayoutInflater();
           ทำให้ตัวแปร inflater สามารถรับค่าจาก Layout ได้
         */
        LayoutInflater inflater = getLayoutInflater();

        /*
           ประกาศตัวแปรชื่อ view1 มีชนิดข้อมูลเป็น View ทำการรับความสามารถจากเม็ดตอด inflate(R.layout.editname, null);
           ในการเรียกใช้หน้า layout ที่มีชื่อว่า editname
         */
        View view1 = inflater.inflate(R.layout.editname, null);

        /*
            เรียกใช้เม็ดตอด setView(view1);
            คือ เซตค่าให้หน้า layout ที่มีชื่อว่า editname แสดงบนหน้าจอแอพ
         */
        builder.setView(view1);

        /*
            ประกาศตัวแปรชื่อ Editename1 มีชนิดข้อมูลเป็น EditText และเป็นตัวแปรที่กำหนดเป็นแบบ final คือ ไม่สามารถเปลี่ยนแปลงค่าได้
            และทำการผูกกับกล่องข้อความที่ให้กรอกชื่อปลั๊ก ที่มีชื่อว่า editText
         */
        final EditText Editename1 = view1.findViewById(R.id.editText);

        /*
            เมื่อ Dialog จะมีให้กด Ok ถ้ากดปุ่ม ok เม็ดตอด onClick() จะทำงาน
         */
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*
                    ประกาศตัวแปรชื่อ editname1 มีชนิดข้อมูลเป็น String และทำการรับค่าของตัวแปร Editename1
                    และทำการเรียกใช้เม็ดตอด getText() คือ รับค่าตัวอักษร
                    และทำการเรียกใช้เม็ดตอด toString() คือ ให้ทำการแปลงชนิดของค่าจาก EditText เป็น String
                    และทำการเรียกใช้เม็ดตอด trim() คือ ตัดช่องว่างออกทั้งหมด
                 */
                String editname1 = Editename1.getText().toString().trim();

                /*
                    ถ้า editname1 = ความว่างเปล่า จะทำใน if
                 */
                if (editname1.equals("")) {

                    // จะแสดงข้อความ Please enter name บนหน้าจอแอพเป็นเวลา 3.5 วิ
                    Toast.makeText(hubActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();

                } else {

                    /*
                        ประกาศตัวแปรชื่อ resetStatus ซึ่งมีชนิดข้อมูลเป็น Map<String, Object> ให้ทำการรับความสามารถของ HashMap<String, Object>();
                        เพื่อที่จะให้ตัวแปร resetStatus สามารถแก้ไขฐานข้อมูลที่อยู่ทางฝั่งของ Firebase database ได้
                    */
                    final Map<String, Object> resetStatus = new HashMap<String, Object>();

                    /*
                        เรียกใช้เม็ดตอด put(); ในการส่งค่า editname1 ไปยังตารางที่มีชื่อว่า Name1
                    */
                    resetStatus.put("Name1", editname1);

                     /*
                        เรียกใช้เม็ดตอด updateChildren() เพื่อที่จะอัปเดทค่า editname1 ไปยังตารางที่มีชื่อว่า Name1
                        โดยทำการรับค่ามาจากตัวแปร resetStatus และทำการส่งค่าไปยังตัวแปร myRef
                        ตัวแปรชื่อ myRef ที่มีชนิดข้อมูลเป็น DatabaseReference จึงสามารถอัปเดทข้อมูลไปยังฐานข้อมูลได้
                    */
                    myRef.updateChildren(resetStatus);

                    // แสดงข้อความบนหน้าจอว่า Change successfully เป็นเวลา 3.5 วิ
                    Toast.makeText(hubActivity.this, "Change successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ถ้ากดปุ่ม Cancel เม็ดตอดนี้จะทำงาน จะทำการปิดหน้า dialog ลง
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        // ทำการสร้าง dialog
        builder.create();

        // ให้ dialog แสดงบนหน้าจอแอพ
        builder.show();

    }   //ClickEdit1

    /*
        เรียกใช้เม็ดตอด ClickEdit2() ถ้ามีการกดปุ่ม Edit ของปลั๊กที่ 2 เม็ดตอดนี้จะทำงาน
     */
    public void ClickEdit2(View view) {

        /* ประกาศตัวแปรชื่อ builder มีชนิดข้อมูลเป็น AlertDialog.Builder และเป็นตัวแปรที่กำหนดเป็นแบบ final คือ ไม่สามารถเปลี่ยนแปลงค่าได้
           ให้ตัวแปร builder ทำการรับความสามารถของ เม็ดตอด AlertDialog.Builder(hubActivity.this) ในการสร้าง AlertDialog ในหน้า hubActivity
        */
        final AlertDialog.Builder builder = new AlertDialog.Builder(hubActivity.this);

        /* ประกาศตัวแปรชื่อ inflater มีชนิดข้อมูลเป็น LayoutInflater ให้ทำการรับความสามารถของเม็ดตอด getLayoutInflater();
           ทำให้ตัวแปร inflater สามารถรับค่าจาก Layout ได้
        */
        LayoutInflater inflater = getLayoutInflater();

        /*
           ประกาศตัวแปรชื่อ view1 มีชนิดข้อมูลเป็น View ทำการรับความสามารถจากเม็ดตอด inflate(R.layout.editname, null);
           ในการเรียกใช้หน้า layout ที่มีชื่อว่า editname
        */
        View view1 = inflater.inflate(R.layout.editname, null);

        /*
            เรียกใช้เม็ดตอด setView(view1);
            คือ เซตค่าให้หน้า layout ที่มีชื่อว่า editname แสดงบนหน้าจอแอพ
        */
        builder.setView(view1);

        /*
        ประกาศตัวแปรชื่อ Editename1 มีชนิดข้อมูลเป็น EditText และเป็นตัวแปรที่กำหนดเป็นแบบ final คือ ไม่สามารถเปลี่ยนแปลงค่าได้
        และทำการผูกกับกล่องข้อความที่ให้กรอกชื่อปลั๊ก ที่มีชื่อว่า editText
        */
        final EditText Editename1 = view1.findViewById(R.id.editText);

        /*
            เมื่อ Dialog จะมีให้กด Ok ถ้ากดปุ่ม ok เม็ดตอด onClick() จะทำงาน
        */
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*
                    ประกาศตัวแปรชื่อ editname1 มีชนิดข้อมูลเป็น String และทำการรับค่าของตัวแปร Editename1
                    และทำการเรียกใช้เม็ดตอด getText() คือ รับค่าตัวอักษร
                    และทำการเรียกใช้เม็ดตอด toString() คือ ให้ทำการแปลงชนิดของค่าจาก EditText เป็น String
                    และทำการเรียกใช้เม็ดตอด trim() คือ ตัดช่องว่างออกทั้งหมด
                */
                String editname1 = Editename1.getText().toString().trim();

                /*
                    ถ้า editname1 = ความว่างเปล่า จะทำใน if
                */
                if (editname1.equals("")) {

                    // จะแสดงข้อความ Please enter name บนหน้าจอแอพเป็นเวลา 3.5 วิ
                    Toast.makeText(hubActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                } else {

                    /*
                    ประกาศตัวแปรชื่อ resetStatus ซึ่งมีชนิดข้อมูลเป็น Map<String, Object> ให้ทำการรับความสามารถของ HashMap<String, Object>();
                    เพื่อที่จะให้ตัวแปร resetStatus สามารถแก้ไขฐานข้อมูลที่อยู่ทางฝั่งของ Firebase database ได้
                    */
                    final Map<String, Object> resetStatus = new HashMap<String, Object>();

                    /*
                    เรียกใช้เม็ดตอด put(); ในการส่งค่า editname1 ไปยังตารางที่มีชื่อว่า Name2
                    */
                    resetStatus.put("Name2", editname1);

                    /*
                    เรียกใช้เม็ดตอด updateChildren() เพื่อที่จะอัปเดทค่า editname1 ไปยังตารางที่มีชื่อว่า Name2
                    โดยทำการรับค่ามาจากตัวแปร resetStatus และทำการส่งค่าไปยังตัวแปร myRef
                    ตัวแปรชื่อ myRef ที่มีชนิดข้อมูลเป็น DatabaseReference จึงสามารถอัปเดทข้อมูลไปยังฐานข้อมูลได้
                    */
                    myRef.updateChildren(resetStatus);

                    // แสดงข้อความบนหน้าจอว่า Change successfully เป็นเวลา 3.5 วิ
                    Toast.makeText(hubActivity.this, "Change successfully", Toast.LENGTH_SHORT).show();
                }


            }
        });

                    // ถ้ากดปุ่ม Cancel เม็ดตอดนี้จะทำงาน จะทำการปิดหน้า dialog ลง
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        // ทำการสร้าง dialog
        builder.create();

        // ให้ dialog แสดงบนหน้าจอแอพ
        builder.show();

    }   //ClickEdit2

    /*
        เรียกใช้เม็ดตอด ClickEdit3() ถ้ามีการกดปุ่ม Edit ของปลั๊กที่ 3 เม็ดตอดนี้จะทำงาน
     */
    public void ClickEdit3(View view) {

        /* ประกาศตัวแปรชื่อ builder มีชนิดข้อมูลเป็น AlertDialog.Builder และเป็นตัวแปรที่กำหนดเป็นแบบ final คือ ไม่สามารถเปลี่ยนแปลงค่าได้
            ให้ตัวแปร builder ทำการรับความสามารถของ เม็ดตอด AlertDialog.Builder(hubActivity.this) ในการสร้าง AlertDialog ในหน้า hubActivity
        */
        final AlertDialog.Builder builder = new AlertDialog.Builder(hubActivity.this);

        /* ประกาศตัวแปรชื่อ inflater มีชนิดข้อมูลเป็น LayoutInflater ให้ทำการรับความสามารถของเม็ดตอด getLayoutInflater();
            ทำให้ตัวแปร inflater สามารถรับค่าจาก Layout ได้
        */
        LayoutInflater inflater = getLayoutInflater();

        /*
            ประกาศตัวแปรชื่อ view1 มีชนิดข้อมูลเป็น View ทำการรับความสามารถจากเม็ดตอด inflate(R.layout.editname, null);
            ในการเรียกใช้หน้า layout ที่มีชื่อว่า editname
        */
        View view1 = inflater.inflate(R.layout.editname, null);

        /*
            เรียกใช้เม็ดตอด setView(view1);
            คือ เซตค่าให้หน้า layout ที่มีชื่อว่า editname แสดงบนหน้าจอแอพ
        */
        builder.setView(view1);

        /*
        ประกาศตัวแปรชื่อ Editename1 มีชนิดข้อมูลเป็น EditText และเป็นตัวแปรที่กำหนดเป็นแบบ final คือ ไม่สามารถเปลี่ยนแปลงค่าได้
        และทำการผูกกับกล่องข้อความที่ให้กรอกชื่อปลั๊ก ที่มีชื่อว่า editText
        */
        final EditText Editename1 = view1.findViewById(R.id.editText);

        /*
        เมื่อ Dialog จะมีให้กด Ok ถ้ากดปุ่ม ok เม็ดตอด onClick() จะทำงาน
        */
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*
                ประกาศตัวแปรชื่อ editname1 มีชนิดข้อมูลเป็น String และทำการรับค่าของตัวแปร Editename1
                และทำการเรียกใช้เม็ดตอด getText() คือ รับค่าตัวอักษร
                และทำการเรียกใช้เม็ดตอด toString() คือ ให้ทำการแปลงชนิดของค่าจาก EditText เป็น String
                และทำการเรียกใช้เม็ดตอด trim() คือ ตัดช่องว่างออกทั้งหมด
                */
                String editname1 = Editename1.getText().toString().trim();

                /*
                ถ้า editname1 = ความว่างเปล่า จะทำใน if
                */
                if (editname1.equals("")) {

                    // จะแสดงข้อความ Please enter name บนหน้าจอแอพเป็นเวลา 3.5 วิ
                    Toast.makeText(hubActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                } else {

                    /*
                    ประกาศตัวแปรชื่อ resetStatus ซึ่งมีชนิดข้อมูลเป็น Map<String, Object> ให้ทำการรับความสามารถของ HashMap<String, Object>();
                    เพื่อที่จะให้ตัวแปร resetStatus สามารถแก้ไขฐานข้อมูลที่อยู่ทางฝั่งของ Firebase database ได้
                    */
                    final Map<String, Object> resetStatus = new HashMap<String, Object>();

                    /*
                    เรียกใช้เม็ดตอด put(); ในการส่งค่า editname1 ไปยังตารางที่มีชื่อว่า Name3
                    */
                    resetStatus.put("Name3", editname1);

                    /*
                    เรียกใช้เม็ดตอด updateChildren() เพื่อที่จะอัปเดทค่า editname1 ไปยังตารางที่มีชื่อว่า Name3
                    โดยทำการรับค่ามาจากตัวแปร resetStatus และทำการส่งค่าไปยังตัวแปร myRef
                    ตัวแปรชื่อ myRef ที่มีชนิดข้อมูลเป็น DatabaseReference จึงสามารถอัปเดทข้อมูลไปยังฐานข้อมูลได้
                    */
                    myRef.updateChildren(resetStatus);

                    // แสดงข้อความบนหน้าจอว่า Change successfully เป็นเวลา 3.5 วิ
                    Toast.makeText(hubActivity.this, "Change successfully", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // ถ้ากดปุ่ม Cancel เม็ดตอดนี้จะทำงาน จะทำการปิดหน้า dialog ลง
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        // ทำการสร้าง dialog
        builder.create();

        // ให้ dialog แสดงบนหน้าจอแอพ
        builder.show();

    }   //ClickEdit3

    /*
        เรียกใช้เม็ดตอด ClickEdit4() ถ้ามีการกดปุ่ม Edit ของปลั๊กที่ 4 เม็ดตอดนี้จะทำงาน
     */
    public void ClickEdit4(View view) {

        /* ประกาศตัวแปรชื่อ builder มีชนิดข้อมูลเป็น AlertDialog.Builder และเป็นตัวแปรที่กำหนดเป็นแบบ final คือ ไม่สามารถเปลี่ยนแปลงค่าได้
            ให้ตัวแปร builder ทำการรับความสามารถของ เม็ดตอด AlertDialog.Builder(hubActivity.this) ในการสร้าง AlertDialog ในหน้า hubActivity
        */
        final AlertDialog.Builder builder = new AlertDialog.Builder(hubActivity.this);

        /* ประกาศตัวแปรชื่อ inflater มีชนิดข้อมูลเป็น LayoutInflater ให้ทำการรับความสามารถของเม็ดตอด getLayoutInflater();
            ทำให้ตัวแปร inflater สามารถรับค่าจาก Layout ได้
        */
        LayoutInflater inflater = getLayoutInflater();

        /*
        ประกาศตัวแปรชื่อ view1 มีชนิดข้อมูลเป็น View ทำการรับความสามารถจากเม็ดตอด inflate(R.layout.editname, null);
        ในการเรียกใช้หน้า layout ที่มีชื่อว่า editname
        */
        View view1 = inflater.inflate(R.layout.editname, null);

        /*
        เรียกใช้เม็ดตอด setView(view1);
        คือ เซตค่าให้หน้า layout ที่มีชื่อว่า editname แสดงบนหน้าจอแอพ
        */
        builder.setView(view1);

        /*
        ประกาศตัวแปรชื่อ Editename1 มีชนิดข้อมูลเป็น EditText และเป็นตัวแปรที่กำหนดเป็นแบบ final คือ ไม่สามารถเปลี่ยนแปลงค่าได้
        และทำการผูกกับกล่องข้อความที่ให้กรอกชื่อปลั๊ก ที่มีชื่อว่า editText
        */
        final EditText Editename1 = view1.findViewById(R.id.editText);

        /*
        เมื่อ Dialog จะมีให้กด Ok ถ้ากดปุ่ม ok เม็ดตอด onClick() จะทำงาน
        */
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*
                ประกาศตัวแปรชื่อ editname1 มีชนิดข้อมูลเป็น String และทำการรับค่าของตัวแปร Editename1
                และทำการเรียกใช้เม็ดตอด getText() คือ รับค่าตัวอักษร
                และทำการเรียกใช้เม็ดตอด toString() คือ ให้ทำการแปลงชนิดของค่าจาก EditText เป็น String
                และทำการเรียกใช้เม็ดตอด trim() คือ ตัดช่องว่างออกทั้งหมด
                */
                String editname1 = Editename1.getText().toString().trim();

                /*
                ถ้า editname1 = ความว่างเปล่า จะทำใน if
                */
                if (editname1.equals("")) {

                    // จะแสดงข้อความ Please enter name บนหน้าจอแอพเป็นเวลา 3.5 วิ
                    Toast.makeText(hubActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                } else {

                    /*
                    ประกาศตัวแปรชื่อ resetStatus ซึ่งมีชนิดข้อมูลเป็น Map<String, Object> ให้ทำการรับความสามารถของ HashMap<String, Object>();
                    เพื่อที่จะให้ตัวแปร resetStatus สามารถแก้ไขฐานข้อมูลที่อยู่ทางฝั่งของ Firebase database ได้
                    */
                    final Map<String, Object> resetStatus = new HashMap<String, Object>();

                    /*
                    เรียกใช้เม็ดตอด put(); ในการส่งค่า editname1 ไปยังตารางที่มีชื่อว่า Name4
                    */
                    resetStatus.put("Name4", editname1);

                    /*
                    เรียกใช้เม็ดตอด updateChildren() เพื่อที่จะอัปเดทค่า editname1 ไปยังตารางที่มีชื่อว่า Name4
                    โดยทำการรับค่ามาจากตัวแปร resetStatus และทำการส่งค่าไปยังตัวแปร myRef
                    ตัวแปรชื่อ myRef ที่มีชนิดข้อมูลเป็น DatabaseReference จึงสามารถอัปเดทข้อมูลไปยังฐานข้อมูลได้
                    */
                    myRef.updateChildren(resetStatus);

                    // แสดงข้อความบนหน้าจอว่า Change successfully เป็นเวลา 3.5 วิ
                    Toast.makeText(hubActivity.this, "Change successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ถ้ากดปุ่ม Cancel เม็ดตอดนี้จะทำงาน จะทำการปิดหน้า dialog ลง
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        // ทำการสร้าง dialog
        builder.create();

        // ให้ dialog แสดงบนหน้าจอแอพ
        builder.show();

    }   //ClickEdit4

    /*
        เป็นเม็ดตอดที่ netpie บังคับให้สร้างใช้ในการเชื่อมต่อ กับ netpie

        protected void onDestroy() {
        super.onDestroy();
        microgear.disconnect();
    }

    protected void onResume() {
        super.onResume();
        microgear.bindServiceResume();
    }

    class MicrogearCallBack implements MicrogearEventListener{
        @Override
        public void onConnect() {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("ControlRelay", "Now I'm connected with netpie");
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("Connected","Now I'm connected with netpie");


        }

        @Override
        public void onMessage(String topic, String message) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("ControlRelay", topic+" : "+message);
            msg.setData(bundle);

            //handler.sendMessage(msg);
            //Log.i("Message",topic+" : "+message);
        }

        @Override
        public void onPresent(String token) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("ControlRelay", "New friend Connect :"+token);
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("present","New friend Connect :"+token);
        }

        @Override
        public void onAbsent(String token) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("ControlRelay", "Friend lost :"+token);
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("absent","Friend lost :"+token);
        }

        @Override
        public void onDisconnect() {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("ControlRelay", "Disconnected");
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("disconnect","Disconnected");
        }

        @Override
        public void onError(String error) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("ControlRelay", "Exception : "+error);
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("exception","Exception : "+error);
        }

        @Override
        public void onInfo(String info) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("ControlRelay", "Exception : "+info);
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("info","Info : "+info);
        }
     */
    protected void onDestroy() {
        super.onDestroy();
        microgear.disconnect();
    }

    protected void onResume() {
        super.onResume();
        microgear.bindServiceResume();
    }

    class MicrogearCallBack implements MicrogearEventListener{
        @Override
        public void onConnect() {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("ControlRelay", "Now I'm connected with netpie");
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("Connected","Now I'm connected with netpie");

        }

        @Override
        public void onMessage(String topic, String message) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("ControlRelay", topic+" : "+message);
            msg.setData(bundle);

            //handler.sendMessage(msg);
            //Log.i("Message",topic+" : "+message);
        }

        @Override
        public void onPresent(String token) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("ControlRelay", "New friend Connect :"+token);
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("present","New friend Connect :"+token);
        }

        @Override
        public void onAbsent(String token) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("ControlRelay", "Friend lost :"+token);
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("absent","Friend lost :"+token);
        }

        @Override
        public void onDisconnect() {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("ControlRelay", "Disconnected");
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("disconnect","Disconnected");
        }

        @Override
        public void onError(String error) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("ControlRelay", "Exception : "+error);
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("exception","Exception : "+error);
        }

        @Override
        public void onInfo(String info) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("ControlRelay", "Exception : "+info);
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("info","Info : "+info);
        }
    }

    public void onBackPressed() {

        // ทำการสร้าง dialog ที่จะแสดงตอนผู้ใช้กดปุ่มย้อนกลับ
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);

        // ทำการเซ็ตรูปภาพที่ชื่อว่า ic_action_alert มาแสดงบน dialog
        dialog.setIcon(R.drawable.ic_action_alert);

        // ทำการเช็ตให้ dialog มีปุ่มยกเลิกให้ผู้ใช้กด
        dialog.setCancelable(true);

        // ทำการเซ็ตข้อความที่จะโชว์แสดงให้ผู้ใช้อ่าน ข้อความที่จะแสดง คือ Do you want to logout
        dialog.setMessage("Do you want to logout?");

        // ถ้าผู้ใช้กดปุ่ม Yes โปรแกรมจะทำการเรียกใช้เม็ดตอด finish(); เพื่อทำการปิดโปรแรกมลง และส่งหน้า Login
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(new Intent(hubActivity.this,MainActivity.class));
            }
        });

        // ถ้าผู้ใช้กดปุ่ม No โปรแกรมจะทำการเรียกใช้เม็ดตอด cancel(); เพื่อทำการให้ dialog ปิดลง แต่ผู้ใช้จะยังอยู่หน้า ควบคุมปลั๊ก
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }   // onBackPressed


}   // Main Class
