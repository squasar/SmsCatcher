package devop.automation.smscatcher.sms;

import java.util.Date;

public class Sms {
    private String smsDate;
    private String number;
    private String body;
    private Date dateFormat;
    private String type;
    public Sms(String smsDate, String number, String body, Date dateFormat, String type){
        this.smsDate = smsDate;
        this.number = number;
        this.body = body;
        this.dateFormat = dateFormat;
        this.type = type;
    }
    public String getSmsDate(){
        return this.smsDate;
    }
    public String getNumber(){
        return this.number;
    }
    public String getBody(){
        return this.body;
    }
    public Date getDateFormat(){
        return this.dateFormat;
    }
    public String getType(){
        return this.type;
    }
}
