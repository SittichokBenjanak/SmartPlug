package smart.projeck.kard.smartplug;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import io.netpie.microgear.Microgear;
import io.netpie.microgear.MicrogearEventListener;


public class hubActivity extends AppCompatActivity {

    private Microgear microgear = new Microgear(this);
    private String appid = "ControlRelay";
    private String key = "0dgTdRU3OOuJyf6";
    private String secret = "uMOacjhPHv4UdtdIi81ZmjEEK";
    private String alias = "Relay";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String string = bundle.getString("myKey");
            TextView myTextView =
                    (TextView)findViewById(R.id.textView4);
            myTextView.append(string+"\n");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        MicrogearCallBack callback = new MicrogearCallBack();
        microgear.connect(appid, key, secret, alias);
        microgear.setCallback(callback);
        microgear.subscribe("Topictest");
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
                                //microgear.publish("Topictest", String.valueOf(count)+".  Test message");
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
            //bundle.putString("myKey", "Now I'm connected with netpie");
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("Connected","Now I'm connected with netpie");
        }

        @Override
        public void onMessage(String topic, String message) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", topic+" : "+message);
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("Message",topic+" : "+message);
        }

        @Override
        public void onPresent(String token) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "New friend Connect :"+token);
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("present","New friend Connect :"+token);
        }

        @Override
        public void onAbsent(String token) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Friend lost :"+token);
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("absent","Friend lost :"+token);
        }

        @Override
        public void onDisconnect() {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Disconnected");
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("disconnect","Disconnected");
        }

        @Override
        public void onError(String error) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Exception : "+error);
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("exception","Exception : "+error);
        }

        @Override
        public void onInfo(String info) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Exception : "+info);
            msg.setData(bundle);
            //handler.sendMessage(msg);
            //Log.i("info","Info : "+info);
        }
    }

    public void ClickOnRelay1(View view) {

        microgear.chat("Relay","DEV41");

    }   // ClickOnRelay1

    public void ClickOnRelay2(View view) {

        microgear.chat("Relay","DEV31");

    }   // ClickOnRelay2

    public void ClickOnRelay3(View view) {

        microgear.chat("Relay","DEV21");

    }   // ClickOnRelay3

    public void ClickOnRelay4(View view) {

        microgear.chat("Relay","DEV11");

    }   // ClickOnRelay4


    /*  ปุ่ม OFF  */
    public void ClickOffRelay1(View view) {

        microgear.chat("Relay","DEV40");
    }   // ClickOffRelay1

    public void ClickOffRelay2(View view) {

        microgear.chat("Relay","DEV30");
    }   // ClickOffRelay2

    public void ClickOffRelay3(View view) {

        microgear.chat("Relay","DEV20");
    }   // ClickOffRelay3

    public void ClickOffRelay4(View view) {

        microgear.chat("Relay","DEV10");
    }   // ClickOffRelay4


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
