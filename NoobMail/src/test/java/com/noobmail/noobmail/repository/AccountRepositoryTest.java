package com.noobmail.noobmail.repository;

import com.noobmail.noobmail.domain.Account;
import com.noobmail.noobmail.domain.AccountSession;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

class AccountRepositoryTest {

    AccountRepository rep;

    @AfterEach
    void after(){
        try {
            rep.executeQuery("delete from account");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            rep.executeQuery("delete from mailbox");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void before(){
        rep = new AccountRepository();
    }

    @Test
    void save() {
        Account acc1 = new Account();
        acc1.setId("id1");
        acc1.setUsername("user1");
        acc1.setPassword("pass1");
        acc1.setSecession_confirmed(-1);

        try {
            System.out.println(rep.save(acc1).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Account acc2 = new Account();
        acc2.setId("id2");
        acc2.setUsername("user2");
        acc2.setPassword("pass2");
        acc2.setSecession_confirmed(-1);

        try {
            System.out.println(rep.save(acc2).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(rep.findAll().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    void validateDuplication() throws JSONException {
        Account acc1 = new Account();
        acc1.setId("id1");
        acc1.setUsername("user1");
        acc1.setPassword("pass1");
        acc1.setSecession_confirmed(-1);

        try {
            System.out.println(rep.save(acc1).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("id1 :"+rep.validateDuplication("id1"));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("id2 :"+rep.validateDuplication("id2"));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void vertificate() {
        Account acc1 = new Account();
        acc1.setId("id1");
        acc1.setUsername("user1");
        acc1.setPassword("pass1");
        acc1.setSecession_confirmed(-1);

        Account acc2 = new Account();
        acc1.setId("id2");
        acc1.setUsername("user2");
        acc1.setPassword("pass2");
        acc1.setSecession_confirmed(-1);

        try {
            System.out.println(rep.save(acc1).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(rep.findAll().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(rep.vertificate(acc1)){
                System.out.println("You logged in!!");
            }else{
                System.out.println("You failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            if(rep.vertificate(acc2)){
                System.out.println("You logged in!!");
            }else{
                System.out.println("You failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getSessionInfo() {
        Account acc1 = new Account();
        acc1.setId("id1");
        acc1.setUsername("user1");
        acc1.setPassword("pass1");
        acc1.setSecession_confirmed(-1);

        try {
            System.out.println(rep.save(acc1).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            AccountSession as = rep.getSessionInfo(acc1);
            System.out.println(as.getId() + "," + as.getUsername() + ","+as.getSecession_confirmed());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void secede() throws NoSuchAlgorithmException {
        Account account = new Account();
        account.setId("id1");
        account.setUsername("user1");
        account.setPassword("pwd1");

        AccountSession as1;
        AccountSession as2;

        try {
            System.out.println("Saved : " + rep.save(account).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("secede : " + rep.secede(account, "I do not agree to delete my account"));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            as1 = rep.getSessionInfo(account);
            System.out.println("id : " + as1.getId());
            System.out.println("username : "+as1.getUsername());
            System.out.println("sc : "+as1.getSecession_confirmed());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("secede : " + rep.secede(account, "I agree to delete my account"));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            as2 = rep.getSessionInfo(account);
            System.out.println("id : " + as2.getId());
            System.out.println("username : "+as2.getUsername());
            System.out.println("sc : "+as2.getSecession_confirmed());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void update(){
        Account account = new Account();
        account.setId("id1");
        account.setUsername("user1");
        account.setPassword("pwd1");
        account.setSecession_confirmed(-1);

        AccountSession as1;

        try {
            System.out.println("Saved : " + rep.save(account).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            rep.update("secession_confirmed = true" , "id=" + "\"" + account.getId() + "\"");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            as1 = rep.getSessionInfo(account);
            System.out.println("id : " + as1.getId());
            System.out.println("username : "+as1.getUsername());
            System.out.println("sc : "+as1.getSecession_confirmed());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}