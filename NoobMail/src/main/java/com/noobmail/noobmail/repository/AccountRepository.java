package com.noobmail.noobmail.repository;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.noobmail.noobmail.admin.Sha256;
import com.noobmail.noobmail.domain.Account;
import com.noobmail.noobmail.domain.AccountSession;
import org.json.JSONException;
import org.json.JSONObject;

//1 = true, -1 = false

public class AccountRepository extends CrudRepository{

    public AccountRepository() {
        super("account");
    }

    //create account : true = account registered/false : not
    public Boolean save(Account account) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        if(!validateDuplication(account.getId())){
            super.save(columns(), values(account));
            return true;
        }else{
            return false;
        }
    }

    //validate duplication
    public Boolean validateDuplication(String id) throws SQLException, ClassNotFoundException {
        return super.exist("id="+"\""+id+"\" and secession_confirmed=-1");
    }

    //log-in, account should be not null
    public Boolean vertificate(Account account) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        return super.exist("id="+"\""+account.getId()+"\""
                +"and pass="+"\""+ account.getKey().toString()+"\""
                +"and  secession_confirmed=-1");
    }

    //get userInfo
    public AccountSession getSessionInfo(Account account) throws SQLException, JSONException, ClassNotFoundException {
        AccountSession info = new AccountSession();

        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("username");
        columns.add("secession_confirmed");

        JSONObject obj = super.findOne(columns, "id="+"\""+account.getId()+"\"", EMPTY());

        info.setValidate(true);
        info.setId(obj.getJSONObject("0").getString("id"));
        info.setUsername(obj.getJSONObject("0").getString("username"));
        info.setSecession_confirmed(obj.getJSONObject("0").getInt("secession_confirmed"));

        System.out.println("info : " + info.getSecession_confirmed());

        return info;
    }

    //delete
    public Boolean secede(Account account, String confirm) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        String CONF = "I agree to delete my account";

        if(!vertificate(account)){
            System.out.println("wrong pwd");
        }else if(!CONF.equals(confirm)){
            System.out.println("wrong confirmed");
        }else{
            super.update("secession_confirmed=1", "id="+"\"" + account.getId() + "\"");
            return true;
        }

        return false;
    }

    private String columns(){
        return "id" + ","
                +"username" + ","
                +"pass" + ","
                + "secession_confirmed";
    }

    private String values(Account account) throws NoSuchAlgorithmException {
        return "\"" + account.getId()+"\"" + ","
                +"\"" + account.getUsername() +"\"" + ","
                +"\"" + account.getKey() +"\"" + ","
                + account.getSecession_confirmed();
    }
}
