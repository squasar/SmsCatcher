package devop.automation.smscatcher.logic;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import devop.automation.smscatcher.email.WrittenMail;
import devop.automation.smscatcher.sms.Sms;
import devop.automation.smscatcher.sms.SmsReader;

public class Process {
    private static Process instance;
    private Context context;
    public static final long delayTime = 1000 * 60 * 20;//20 min
    private Process(Context context){
        this.context = context;
    }
    private void setContext(Context con){
        this.context = con;
    }
    public static Process getInstance(Context context){
        if(Process.instance == null){
            Process.instance = new Process(context);
        }else{
            Process.instance.setContext(context);
        }
        return Process.instance;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void execute(){
        String mail[];
        mail = buildMail(readSms(3));
        sendMail(mail[0], mail[1]);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private ArrayList<Sms> readSms(int limit){
        SmsReader reader = new SmsReader(this.context, limit);
        return reader.getMessages();
    }
    private String[] buildMail(ArrayList<Sms> messages){
        String [] result = new String[2];
        String subject = "DEVOP SmsCatcher";
        result[0]=subject;
        String body="";
        for(int i = 0; i<messages.size(); i++){
            Sms msg = (Sms) messages.get(i);
            body = body + "SMS # :" + (i+1) + "\n" +
                    "SMS Date :" + msg.getSmsDate() + "\n" +
                    "Number :" + msg.getNumber() + "\n" +
                    "Message :" + msg.getBody() + "\n"+
                    "Date Format :" + msg.getDateFormat().toString() + "\n" +
                    "SMS Type :" + msg.getType() + "\n";
        }
        result[1]=body;
        return result;
    }
    private void sendMail(String subject, String message){
        WrittenMail mail = new WrittenMail(this.context);
        mail.setSubject(subject);
        mail.setMessage(message);
        mail.send();
    }
}