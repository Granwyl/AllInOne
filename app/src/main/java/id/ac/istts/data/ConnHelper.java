package id.ac.istts.data;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnHelper {
    Connection conn;
    String username, password, port, ip, database;
    @SuppressLint("NewApi")
    public Connection connclass(){
        ip = "140.0.149.117";
        database = "allinone";
        username = "root";
        password = "";
        port = "3306";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connURL = null;

        try {
            //Class.forName("net.sourceforge.jtds.jdbc.Driver");
            //connURL= "jdbc:jtds:sqlserver://"+ ip + ":"+ port+";"+ "databasename="+ database+";user="+username+";password="+password+";";
            //connection = DriverManager.getConnection(connURL);
        }catch (Exception ex){
            //Log.e("error",ex.getMessage());
        }
        return connection;
    }
}
