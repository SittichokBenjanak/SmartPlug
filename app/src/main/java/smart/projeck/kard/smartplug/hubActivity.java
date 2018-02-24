package smart.projeck.kard.smartplug;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
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

    public DatabaseReference myRef;

    private Microgear microgear = new Microgear(this);
    private String appid = "ControlRelay";
    private String key = "0dgTdRU3OOuJyf6";
    private String secret = "uMOacjhPHv4UdtdIi81ZmjEEK";
    private String alias = "Relay";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);


        // รับค่า firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        // รับค่าชื่อ 4 ชื่อ จาก firbase
        setconnectfribase();

        // ตั้งเงื่อนไขให้ปุ่มแสดงเปิดหรือปิด


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



    private void setconnectfribase() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();

                // รับค่าชื่อปลั๊กทั้ง 4 ชื่อ จาก firebase ไปอยู่ในตัวแปร value1, value2, value3, value4
                String value1 = String.valueOf(map.get("Name1"));
                String value2 = String.valueOf(map.get("Name2"));
                String value3 = String.valueOf(map.get("Name3"));
                String value4 = String.valueOf(map.get("Name4"));

                // ประกาศตัวแปร TextView เพื่อรับค่าชื่อ 4 ชื่อ จาก value1, value2, value3, value4 ไปอยู่ใน Name1, Name2, Name3, Name4
                TextView Name1, Name2, Name3, Name4;

                // ผูก textView6 เข้ากับ Name1, ผูก textView9 เข้ากับ Name2, ผูก textView12 เข้ากับ Name3, ผูก textView15 เข้ากับ Name4
                Name1 = findViewById(R.id.textView6);
                Name2 = findViewById(R.id.textView9);
                Name3 = findViewById(R.id.textView12);
                Name4 = findViewById(R.id.textView15);

                // ทำการเซ็ตค่าให้โชว์หน้าแอพ เช่น  พัดลม, ตู้เย็น, ทีวี, หม้อหุงข้าว
                Name1.setText(value1);
                Name2.setText(value2);
                Name3.setText(value3);
                Name4.setText(value4);

                // ----------------------------------------------------------------

                String Relayvalue1 = String.valueOf(map.get("Relay1"));
                String Relayvalue2 = String.valueOf(map.get("Relay2"));
                String Relayvalue3 = String.valueOf(map.get("Relay3"));
                String Relayvalue4 = String.valueOf(map.get("Relay4"));

                RadioButton radioButton1, radioButton2, radioButton3, radioButton4;

                radioButton1 = findViewById(R.id.radioButton);
                radioButton2 = findViewById(R.id.radioButton2);
                radioButton3 = findViewById(R.id.radioButton3);
                radioButton4 = findViewById(R.id.radioButton4);

                radioButton1.setEnabled(false);
                radioButton2.setEnabled(false);
                radioButton3.setEnabled(false);
                radioButton4.setEnabled(false);

                // status 1
                if (Relayvalue1.equals("1")) {
                    radioButton1.setEnabled(true);
                    radioButton1.setChecked(true);
                    radioButton1.setText("ON");

                } else if (Relayvalue1.equals("0")) {
                    radioButton1.setChecked(false);
                    radioButton1.setText("OFF");
                }

                // status 2
                if (Relayvalue2.equals("1")) {
                    radioButton2.setEnabled(true);
                    radioButton2.setChecked(true);
                    radioButton2.setText("ON");

                } else if (Relayvalue2.equals("0")) {
                    radioButton2.setChecked(false);
                    radioButton2.setText("OFF");
                }

                // status 3
                if (Relayvalue3.equals("1")) {
                    radioButton3.setEnabled(true);
                    radioButton3.setChecked(true);
                    radioButton3.setText("ON");

                } else if (Relayvalue3.equals("0")) {
                    radioButton3.setChecked(false);
                    radioButton3.setText("OFF");
                }

                // status 4
                if (Relayvalue4.equals("1")) {
                    radioButton4.setEnabled(true);
                    radioButton4.setChecked(true);
                    radioButton4.setText("ON");

                } else if (Relayvalue4.equals("0")) {
                    radioButton4.setChecked(false);
                    radioButton4.setText("OFF");
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }



        });
    }   // setconnectfribase


    /*************** ปุ่ม ON  ***************/
    public void ClickOnRelay1(View view) {

        //microgear.chat("Relay","DEV41");
        final Map<String, Object> resetStatus = new HashMap<String, Object>();
        resetStatus.put("Relay1", "1");
        myRef.updateChildren(resetStatus);

    }   // ClickOnRelay1

    public void ClickOnRelay2(View view) {

        //microgear.chat("Relay","DEV31");
        final Map<String, Object> resetStatus = new HashMap<String, Object>();
        resetStatus.put("Relay2", "1");
        myRef.updateChildren(resetStatus);

    }   // ClickOnRelay2

    public void ClickOnRelay3(View view) {

        //microgear.chat("Relay","DEV21");
        final Map<String, Object> resetStatus = new HashMap<String, Object>();
        resetStatus.put("Relay3", "1");
        myRef.updateChildren(resetStatus);

    }   // ClickOnRelay3

    public void ClickOnRelay4(View view) {

        //microgear.chat("Relay","DEV11");
        final Map<String, Object> resetStatus = new HashMap<String, Object>();
        resetStatus.put("Relay4", "1");
        myRef.updateChildren(resetStatus);

    }   // ClickOnRelay4


    /**************  ปุ่ม OFF  *****************/
    public void ClickOffRelay1(View view) {

        //microgear.chat("Relay","DEV40");
        final Map<String, Object> resetStatus = new HashMap<String, Object>();
        resetStatus.put("Relay1", "0");
        myRef.updateChildren(resetStatus);

    }   // ClickOffRelay1

    public void ClickOffRelay2(View view) {

        //microgear.chat("Relay","DEV30");
        final Map<String, Object> resetStatus = new HashMap<String, Object>();
        resetStatus.put("Relay2", "0");
        myRef.updateChildren(resetStatus);

    }   // ClickOffRelay2

    public void ClickOffRelay3(View view) {

        //microgear.chat("Relay","DEV20");
        final Map<String, Object> resetStatus = new HashMap<String, Object>();
        resetStatus.put("Relay3", "0");
        myRef.updateChildren(resetStatus);

    }   // ClickOffRelay3

    public void ClickOffRelay4(View view) {

        //microgear.chat("Relay","DEV10");
        final Map<String, Object> resetStatus = new HashMap<String, Object>();
        resetStatus.put("Relay4", "0");
        myRef.updateChildren(resetStatus);

    }   // ClickOffRelay4

    /************************ ปุ่ม Edit ***************************/

    public void ClickEdit1(View view) {


    }   //ClickEdit1

    public void ClickEdit2(View view) {

    }   //ClickEdit2
    public void ClickEdit3(View view) {

    }   //ClickEdit3
    public void ClickEdit4(View view) {

    }   //ClickEdit4




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
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setIcon(R.drawable.ic_action_alert);
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to logout?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(new Intent(hubActivity.this,MainActivity.class));
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
