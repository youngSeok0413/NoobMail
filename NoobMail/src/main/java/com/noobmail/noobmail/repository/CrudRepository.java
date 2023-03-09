package com.noobmail.noobmail.repository;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class CrudRepository {
    String table = new String();
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private final String driver = "com.mysql.jdbc.Driver";
    private final String userName = "root";
    private final String password = "1111";
    private final String dbms = "mysql";
    private final String serverName = "localhost";
    private final String portNumber = "3306";
    private final String dbName = "NoobMail";

    public CrudRepository(String table){
        this.table= table;
    }

    //exta query(controll)
    public void executeQuery(String query) throws SQLException, ClassNotFoundException {
        open();
        stmt.executeUpdate(query);
        close();
    }

    //insert
    public void save(String columns, String values) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        String query = "insert into " + table + "("+ columns +") " + " values(" + values + ");";
        open();
        stmt.executeUpdate(query);
        close();
    }

    //select condition, extra must be not null
    public JSONObject findOne(List<String> labels, String condition , String extra) throws SQLException, ClassNotFoundException, JSONException {
        JSONObject result = new JSONObject();
        Integer resultIndex = 0;
        String columns = toString(labels);

        String query = "select " + columns +" from "+ table+ " where " + condition + " " + extra;

        open();
        rs = stmt.executeQuery(query);
        while(rs.next()){
            JSONObject js = new JSONObject();
            for(String col : labels){
                js.put(col, rs.getString(col));
            }
            result.put(resultIndex.toString(), js);
            resultIndex++;
        }
        close();

        return result;
    }

    private String toString(List<String> labels){
        String str = new String();

        if(!labels.isEmpty()){
            for(String label : labels){
                str += label + ",";
            }

            StringBuffer sb = new StringBuffer(str);
            sb.deleteCharAt(str.length()-1);
            str = sb.toString();
        }else{
            str = "*";
        }

        return str;
    }

    //select all
    public JSONObject findAll() throws SQLException, ClassNotFoundException, JSONException {
        JSONObject result = new JSONObject();
        Integer resultIndex = 0;
        String query = "select * from " + table;
        open();

        rs = stmt.executeQuery(query);
        ResultSetMetaData metaData = rs.getMetaData();

        int count = metaData.getColumnCount();
        String columnName[] = new String[count];
        for(int i = 1; i <=count; i++){
            columnName[i-1] = metaData.getColumnLabel(i);
        }

        while(rs.next()){
            JSONObject js = new JSONObject();
            for(int i = 1; i <=count; i++){
                js.put(columnName[i-1], rs.getString(i));
            }
            result.put(resultIndex.toString(), js);
            resultIndex++;
        }

        close();

        return result;
    }

    //update
    public void update(String after, String condition) throws SQLException, ClassNotFoundException {
        String query = "update " + table + " set " + after + " where " + condition;
        open();
        stmt.executeUpdate(query);
        close();
    }

    //delete
    public void delete(String condition) throws SQLException, ClassNotFoundException {
        String query = "delete from " + table + " where " + condition;
        open();
        stmt.executeUpdate(query);
        close();
    }

    //commit
    public void commit() throws SQLException, ClassNotFoundException {
        String query = "commit";
        open();
        stmt.executeUpdate(query);
        close();
    }

    //exists
    public Boolean exist(String condition) throws SQLException, ClassNotFoundException {

        boolean ext = false;

        String query = "select exists("+"select * from " + table + " where " + condition + ") As t";

        open();
        rs = stmt.executeQuery(query);
        rs.isBeforeFirst();

        if(rs.next()){
            if(rs.getBoolean("t")) {
                ext =  true;
            }
        }

        close();

        return ext;
    }

    //empty
    public String EMPTY(){
        return "";
    }

    private void open() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        conn = DriverManager.getConnection(
                "jdbc:" + this.dbms + "://" +
                        this.serverName +
                        ":" + this.portNumber + "/" + dbName,
                connectionProps);

        stmt = conn.createStatement();
    }

    private void close() throws SQLException {
        if(conn != null && !conn.isClosed()){
            conn.close();
            conn = null;
        }
        if(stmt != null && stmt.isClosed()){
            stmt.close();
            stmt = null;
        }
        if(rs != null && rs.isClosed()){
            rs.close();
            rs = null;
        }
    }

}
