package smart.projeck.kard.smartplug;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyAlertDialog {

    public void errorDialog(Context context, String strTitle, String strMessage) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(context);
        objBuilder.setIcon(R.drawable.ic_action_alert);
        objBuilder.setTitle(strTitle);
        objBuilder.setMessage(strMessage);
        objBuilder.setCancelable(false);  // Cancelable(false) คือ เวลา AlertDialog ให้กด Ok เท่านั้น กดกลับไม่ได้
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        objBuilder.show();

    }   // errorDialog

}   // Main Calss
