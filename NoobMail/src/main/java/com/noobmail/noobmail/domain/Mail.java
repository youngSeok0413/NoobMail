package com.noobmail.noobmail.domain;

import com.mysql.cj.xdevapi.JsonParser;
import com.noobmail.noobmail.admin.Sha256;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

public class Mail implements DTO{
	private Map<String, String> dataSet;

	public Mail(){
		dataSet.put("id", "");
		dataSet.put("created_at", "");
		dataSet.put("sender", "");
		dataSet.put("receiver", "");
		dataSet.put("title", "");
		dataSet.put("main_text", "");
		dataSet.put("file_path", ""); //json array
		dataSet.put("garbage_confirmed", "");
		dataSet.put("delete_confirmed", "");
	}

	//id
	public String getId() {
		return dataSet.get("id");
	}

	public void setId() throws NoSuchAlgorithmException {
		if(dataSet.get("created_at").equals(""))
			setTimestamp();
		dataSet.replace("id", Sha256.encrypt(dataSet.get("created_at")
				+dataSet.get("sender")+dataSet.get("receiver")));
	}

	//timestamp
	public Timestamp getTimestamp() {
		return java.sql.Timestamp.valueOf(dataSet.get("created_at"));
	}

	public void setTimestamp() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		dataSet.replace("created_at", new SimpleDateFormat("yyyy/MM//dd HH:mm:ss").format(ts));
	}

	//receiver
	public String getReceiver() {
		return dataSet.get("receiver");
	}

	public void setReceiver(String receiver) {
		dataSet.replace("receiver", receiver);
	}

	//sender
	public String getSender() {
		return dataSet.get("sender");
	}

	public void setSender(String sender) {
		dataSet.replace("sender", sender);
	}

	//title
	public String getTitle() {
		return dataSet.get("title");
	}

	public void setTitle(String title) {
		dataSet.replace("title", title);
	}

	//text
	public String getText() {
		return dataSet.get("main_text");
	}

	public void setText(String text) {
		dataSet.replace("main_text", text);
	}

	//file path
	public JSONObject getFilePath() throws ParseException {
		JSONParser jsonParser = new JSONParser();
		return  new JSONObject(jsonParser.parse(dataSet.get("file_path")));
	}

	public void setFilePath(JSONObject filePath) {
		dataSet.replace("file_path", filePath.toString());
	}

	//garbage
	public boolean isGarbage_confirmed() {
		return dataSet.get("garbage_confirmed").equals("1") ? true : false;
	}

	public void setGarbage_confirmed(boolean garbage_confirmed) {
		if(garbage_confirmed){
			dataSet.replace("garbage_confirmed", "1");
		}else{
			dataSet.replace("garbage_confirmed", "-1");
		}
	}

	//delete
	public boolean isDelete_confirmed() {
		return dataSet.get("delete_confirmed").equals("1") ? true : false;
	}

	public void setDelete_confirmed(boolean delete_confirmed) {
		if(delete_confirmed){
			dataSet.replace("delete_confirmed", "1");
		}else{
			dataSet.replace("delete_confirmed", "-1");
		}
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
	public String values(Map<String, String> sets) throws ParseException {
		String fp = new String();
		JSONObject obj = getFilePath();
		for(int i = 0; i < obj.length(); i++){

		}

		return "\""+getId()+"\","
				+"now()"+","
				+"\""+getSender()+"\","
				+"\""+getReceiver()+"\","
				+"\""+getTitle()+"\","
				+"\""+getText()+"\","
				+"jsonObject("++")",";
	}
}
