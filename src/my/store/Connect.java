package my.store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Connect {
    Connection c;
    Statement s;
    public void conn(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c= DriverManager.getConnection("jdbc:mysql://localhost:3306/my_store?serverTimezone=UTC",
                    "root","");
            s= c.createStatement();
            System.out.println("DATABASE CONNECTED");
        }
        catch (Exception e){
            System.out.println("CONNECTION ERROR");
            System.out.println(e);
        }

    }


}
