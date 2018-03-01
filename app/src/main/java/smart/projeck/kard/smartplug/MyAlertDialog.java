package smart.projeck.kard.smartplug;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

// สร้างเม็ดตอดที่ชื่อว่า MyAlertDialog ทำการ public ไว้ เพื่อที่จะสามารถเรียกใช้ได้ทุกหน้า
public class MyAlertDialog {

    /* ทำการเขียนให้มีการรับค่าเข้าตั้งหมด 3 ตัว
    1.คือ ตัวแปรชื่อ context มีชนิดข้อมูลเป็น Context
    2.คือ ตัวแปรชื่อ strTitle มีชนิดข้อมูลเป็น String
    3.คือ ตัวแปรชื่อ strMessage มีชนิดข้อมูลเป็น String
    */
    public void errorDialog(Context context, String strTitle, String strMessage) {

        // ทำการสร้าง dialog
        AlertDialog.Builder objBuilder = new AlertDialog.Builder(context);

        // ทำการเซ็ตรูปภาพที่ชื่อว่า ic_action_alert มาแสดงบน dialog
        objBuilder.setIcon(R.drawable.ic_action_alert);

        /* ทำการเซ็ตข้อความที่จะโชว์แสดงให้ผู้ใช้อ่าน ข้อความที่จะแสดง คือ ข้อความที่ผู้ใช้ส่งค่ามา
            เช่น ตัวแปร strMessage ได้ค่ามาคือ สวัสดี ก็จะนำความว่า สวัสดีไปโชว์
         */
        objBuilder.setMessage(strMessage);

        // เรียกใช้เมดตอด setCancelable(false) คือ เวลา AlertDialog ให้กด Ok เท่านั้น กดกลับไม่ได้
        objBuilder.setCancelable(false);

        // ถ้าผู้ใช้กดปุ่ม OK โปรแกรมจะทำการเรียกใช้เม็ดตอด dismiss(); เพื่อทำการให้ dialog ปิดลง
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // เรียกใช้เม็ดตอด show(); เพื่อให้ dialog แสดง
        objBuilder.show();

    }   // errorDialog

}   // Main Calss
