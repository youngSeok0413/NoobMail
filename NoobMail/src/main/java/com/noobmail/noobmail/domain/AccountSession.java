package com.noobmail.noobmail.domain;

import java.util.Map;

public class AccountSession implements DTO{
    private Map<String, String> dataSet;

    public Boolean getValidate() {
        return validate;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

    private Boolean validate;

    public AccountSession(){
        dataSet.put("id","");
        dataSet.put("username","");
        dataSet.put("secession_confirmed","");
    }

    //id
    public void setId(String val){
        dataSet.replace("id", val);
    }

    public String getId(){
        return dataSet.get("id");
    }

    //username
    public void setUsername(String val){
        dataSet.replace("username", val);
    }

    public String getUsername(){
        return dataSet.get("username");
    }

    //secession_confirmed
    public void setSecession_confirmed(boolean val){
        if(val){
            dataSet.replace("secession_confirmed", "1");
        }else{
            dataSet.replace("secession_confirmed", "-1");
        }
    }

    public Boolean getSecession_confirmed(){
        return dataSet.get("secession_confirmed").equals("1") ? true : false;
    }

    @Override
    public String columns(Map<String, String> sets) {
        if(sets.isEmpty()) sets = this.dataSet;

        String columns = new String();

        for(Map.Entry<String, String> entry : dataSet.entrySet())
            columns += entry.getKey() + ",";

        if(!columns.isEmpty() || columns.length() == 0)
            columns.substring(0, columns.length() -1);

        return columns;
    }

    @Override
    public String values(Map<String, String> sets) {
        return "\""+getId()+"\","
                +"\""+getUsername()+"\","
                +getSecession_confirmed();
    }
}
