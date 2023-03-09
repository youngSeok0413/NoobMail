package com.noobmail.noobmail.domain;

import com.noobmail.noobmail.admin.Sha256;

import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Map;

public class Account implements DTO {

	private Map<String, String> dataSet;
	private String password;

	public Account(){
		dataSet.put("id","");
		dataSet.put("username","");
		dataSet.put("pass","");
		dataSet.put("secession_confirmed","");
	}

	//id
	public void setId(String val){
		dataSet.replace("id", val);
	}

	public String getId(){
		return dataSet.get("id");
	}

	//pwd
	public void setPassword(String pwd){
		this.password = pwd;
	}

	//username
	public void setUsername(String val){
		dataSet.replace("username", val);
	}

	public String getUsername(){
		return dataSet.get("username");
	}

	//pass
	public void setPass() throws NoSuchAlgorithmException {
		if(dataSet.get("pass").equals("")){
			dataSet.replace("pass", Sha256.encrypt(getId()+password));
		}
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
				+"\""+dataSet.get("pass")+"\","
				+getSecession_confirmed();
	}
}


