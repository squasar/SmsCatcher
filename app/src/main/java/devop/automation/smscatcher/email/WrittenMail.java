package devop.automation.smscatcher.email;

import android.content.Context;

public class WrittenMail {
    private static final String mailTo = "alici mail adresi";
    protected static final String EMAIL ="gonderici gmail adresi";
    protected static final String PASSWORD ="gonderici gmail sifresi";
    private String subject;
    private String message;
    private Context con;
    public WrittenMail(Context contex){
        this.con = contex;
    }
    public void send(){
        this.sendEmail();
    }
    private void sendEmail() {
        SendMail sm = new SendMail(con, mailTo, subject, message);
        sm.execute();
    }
    public String getSubject(){
        return this.subject;
    }
    public void setSubject(String subject){
        this.subject = subject;
    }
    public String getMessage(){
        return this.message;
    }
    public void setMessage(String message){
        this.message = message;
    }
}
