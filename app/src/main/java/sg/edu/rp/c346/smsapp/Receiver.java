package sg.edu.rp.c346.smsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                SmsMessage currentMessage;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = bundle.getString("format");
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0], format);
                } else {
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0]);
                }
                String senderNum = currentMessage.getDisplayOriginatingAddress();
                String message = currentMessage.getDisplayMessageBody();

                Toast.makeText(context, "Sender Number: " + senderNum +
                        ", Message: " + message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Error: " + e);
        }

    }
}
