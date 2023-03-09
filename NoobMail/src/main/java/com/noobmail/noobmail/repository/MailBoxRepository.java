package com.noobmail.noobmail.repository;

import com.noobmail.noobmail.domain.Account;
import com.noobmail.noobmail.domain.Mail;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MailBoxRepository extends CrudRepository{

    public MailBoxRepository() {
        super("mailbox");
    }

    //save mail written
    public void save(Mail mail) throws NoSuchAlgorithmException, SQLException, ClassNotFoundException {
        super.save(columns(), values(mail));
    }

    private String columns(){
        return "id" + ","
                +"sender" + ","
                +"receiver" + ","
                + "title" +","
                + "main_text" + ","
                +"file_path" + ","
                +"garbage_confirmed" + ","
                + "delete_confirmed";
    }

    private String values(Mail mail) throws NoSuchAlgorithmException {
        return "\"" + mail.getId() +"\"" +  ","
                +"\"" + mail.getReceiver() +"\"" +  ","
                +"\"" + mail.getSender() +"\"" +  ","
                +"\"" + mail.getTitle() +"\"" +  ","
                +"\"" + mail.getText()+"\"" + ","
                + mail.getFilePath().toString()+","
                + mail.getGarbage_confirmed().toString()+ ","
                + mail.getDelete_confirmed().toString();
    }

    //get mails sent to user
    public JSONObject getAllMailsUserGot(String user) throws SQLException, JSONException, ClassNotFoundException {

        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("created_at");
        columns.add("sender");
        columns.add("receiver");
        columns.add("title");
        columns.add("main_text");
        columns.add("file_path");
        columns.add("garbage_confirmed");
        columns.add("delete_confirmed");

        return findOne(columns, "receiver=\""+user+"\"&garbage_confirmed=false" +
                "&delete_confirmed=false", "order by created_at desc");
    }

    //get garbage can
    public JSONObject getGarbage(String user) throws SQLException, JSONException, ClassNotFoundException {

        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("created_at");
        columns.add("sender");
        columns.add("receiver");
        columns.add("title");
        columns.add("main_text");
        columns.add("file_path");
        columns.add("garbage_confirmed");
        columns.add("delete_confirmed");

        return findOne(columns, "sender=\""+user+"\"&delete_confirmed=false&" +
                        "garbage_confirmed=true",
                "order by created_at desc");
    }

    //get mails user wrote
    public JSONObject getAllMailsUserWrote(String user) throws SQLException, JSONException, ClassNotFoundException {

        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("created_at");
        columns.add("sender");
        columns.add("receiver");
        columns.add("title");
        columns.add("main_text");
        columns.add("file_path");
        columns.add("garbage_confirmed");
        columns.add("delete_confirmed");

        return findOne(columns, "sender=\""+user+"\"&delete_confirmed=false&" +
                        "garbage_confirmed=false",
                "order by created_at desc");
    }

    //get mails user still on writing
    public JSONObject getAllTempMails(String user) throws SQLException, JSONException, ClassNotFoundException {

        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("created_at");
        columns.add("sender");
        columns.add("receiver");
        columns.add("title");
        columns.add("main_text");
        columns.add("file_path");
        columns.add("garbage_confirmed");
        columns.add("delete_confirmed");

        return findOne(columns, "sender=\""+user+"\"&receiver is null&" +
                        "delete_confirmed=false&" +
                        "garbage_confirmed=false",
                "order by created_at desc");
    }

    //mail -> garbage
    public void garbage(Mail mail) throws NoSuchAlgorithmException, SQLException, ClassNotFoundException {
            update("garbage_confirmed=true".toString(), "id="+mail.getId());
    }

    //confirm mail to  delete
    public void delete(Mail mail) throws NoSuchAlgorithmException, SQLException, ClassNotFoundException {
        if(mail.getGarbage_confirmed().equals(true)){
                update("garbage_confirmed=true", "id="+mail.getId());
        }
    }
}
