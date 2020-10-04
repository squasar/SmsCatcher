package devop.automation.smscatcher.sms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.Telephony;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.Date;

public class SmsReader {
    private ArrayList<Sms> messages;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public SmsReader(Context context, int limit){
        this.messages = new ArrayList<Sms>(limit);
        getAllSms(context, limit);
    }
    public ArrayList<Sms> getMessages(){
        return this.messages;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getAllSms(Context context, int limit) {
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        int totalSMS = 0;
        if (c != null) {
            totalSMS = c.getCount();
            if (c.moveToFirst()) {
                if(limit>totalSMS){
                    limit = totalSMS;
                }
                for (int j = 0; j < totalSMS || limit>0; j++) {
                    String smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    Date dateFormat= new Date(Long.valueOf(smsDate));
                    String type;
                    switch (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)))) {
                        case Telephony.Sms.MESSAGE_TYPE_INBOX:
                            type = "inbox";
                            Sms message = new Sms(smsDate, number, body, dateFormat, type);
                            this.messages.add(message);
                            limit = limit - 1;
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_SENT:
                            type = "sent";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_OUTBOX:
                            type = "outbox";
                            break;
                        default:
                            type = "default val";
                            break;
                    }
                    c.moveToNext();
                }
            }
            c.close();
        }
    }
}
