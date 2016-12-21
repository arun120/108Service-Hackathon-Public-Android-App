package com.b2b.home.a108public;

import android.os.Environment;
import android.util.Log;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Home on 13-11-2016.
 */
public class Database {

    public static int registerPublic(Customer_Details c){
        Dbdetails db=new Dbdetails();
        int cust_id=0;
        Connection conn=null;
        Statement stmt=null;
        try {
            Class.forName(db.getDriver());
            conn= DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPass());
            stmt=conn.createStatement();
            stmt.executeUpdate("insert into customer_details values("+null+",'"+c.getName()+"','"+c.getMobile()+
                    "','"+c.getAddress()+"')");
            ResultSet rs=stmt.executeQuery("select cust_id from customer_details where mobile like '"+c.getMobile()+"'");
            if(rs.next())
                cust_id=rs.getInt("cust_id");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
            if(stmt!=null)
                stmt.close();
            if(conn!=null)

                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        return cust_id;
    }


    public static  int updateDescription(Customer_Case c){

        Dbdetails db=new Dbdetails();
        int update=0;
        Connection conn=null;
        Statement stmt=null;
        try {
            Class.forName(db.getDriver());
            conn= DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPass());
            stmt=conn.createStatement();
            update=stmt.executeUpdate("update customer_case set description ='"+c.getDescription()+"' where case_id like '"+c.getCase_id()+"'");


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null)
                    stmt.close();
                if(conn!=null)

                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return update;
    }

    public static String getSMSurl(Customer_Case cc){
        String surl=Dbdetails.getServerpath()+"addCase?custid="+cc.getCust_id()+
                "&type="+cc.getType()+"&lat="+cc.getLatitude()+"&lon="+cc.getLongitude()+
                "&people="+cc.getNo_ppl_affected();

        surl=surl.replace("+","%2B");
    return surl;
    }

    public static  String addCase(Customer_Case cc){

        HttpURLConnection connection = null;
        String s = "";

        String surl=getSMSurl(cc);
        URL url = null;
        try {
            url = new URL(surl);

        connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();
        char c;
        while ((c = (char) input.read()) != (char) -1)
            s += c;

           // Log.i("Server return",s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }
    public static  int updateImage(String caseid){

        Dbdetails db=new Dbdetails();
        int update=0;
        Connection conn=null;
        PreparedStatement stmt=null;
        try {
            Class.forName(db.getDriver());
            conn= DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPass());
            stmt=conn.prepareStatement("update customer_case set image =? where case_id like '"+caseid+"'");
            File file=new File(String.valueOf(Environment.getExternalStorageDirectory()+"/image.png"));
            FileInputStream is=new FileInputStream(file);
            stmt.setBinaryStream(1,is,(int)file.length());
            stmt.executeUpdate();
            is.close();



        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null)
                    stmt.close();
                if(conn!=null)

                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return update;
    }

    public static Driver_Details  findDriver(Customer_Case c){
        Dbdetails db=new Dbdetails();
        Driver_Details driver_id=null;
        Connection conn=null;
        Statement stmt=null;
        try {
            Class.forName(db.getDriver());
            conn= DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPass());
            stmt=conn.createStatement();

            ResultSet rs=stmt.executeQuery("select a.driver_id,name,mobile  from case_link a,driver_details b where a.driver_id=b.driver_id and case_id like '"+c.getCase_id()+"'");
            if(rs.next()){
                    driver_id=new Driver_Details();
                    driver_id.setDriver_id(rs.getString("driver_id"));
                    driver_id.setMobile(rs.getString("mobile"));
                    driver_id.setName(rs.getString("name"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null)
                    stmt.close();
                if(conn!=null)

                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return driver_id;
    }



    public static Driver_Location  driverLocation(String id){
        Dbdetails db=new Dbdetails();
        String driver_id=null;
        Connection conn=null;
        Statement stmt=null;
        Driver_Location loc=new Driver_Location();
        loc.setDriver_id(id);
        try {
            Class.forName(db.getDriver());
            conn= DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPass());
            stmt=conn.createStatement();

            ResultSet rs=stmt.executeQuery("select * from driver_location where driver_id like '"+id+"'");
            if(rs.next()) {
                 loc.setLongitude(rs.getString("longitude"));
                loc.setLatitude(rs.getString("latitude"));

            }
            else{
                loc=null;
            }
            } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null)
                    stmt.close();
                if(conn!=null)

                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return loc;
    }



    public static  String isSimilar(Customer_Case cc){

        HttpURLConnection connection = null;
        String s = "";

        String surl=getSMSurl(cc);
        surl=surl.replace("addCase","isSimilar");
        URL url = null;
        try {
            url = new URL(surl);

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream input = connection.getInputStream();
            char c;
            while ((c = (char) input.read()) != (char) -1)
                s += c;

            // Log.i("Server return",s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }


    public static Customer_Case getCase(String caseid){
        Connection conn=null;
        Statement stmt=null;
        Dbdetails db=new Dbdetails();

        Customer_Case cust=new Customer_Case();

        try{
            Class.forName(db.getDriver());
            conn= DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPass());
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("select * from customer_case where case_id like '"+caseid+"'");
            if(rs.next()) {

                cust.setCase_id(rs.getString("case_id"));
                cust.setDescription(rs.getString("description"));
                cust.image = rs.getBinaryStream("image");

                File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Portal/");
                if(!f.exists())
                    f.mkdir();
                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Image.jpg");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = cust.image.read(data)) != -1) {
                    // allow canceling with back button
                    output.write(data, 0, count);


                }

                if(output!=null)
            output.close();
                if(cust.image!=null)
                cust.image.close();
            }

        }catch(Exception e){e.printStackTrace();}finally{try {
            if(stmt!=null)stmt.close();if(conn!=null)conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }
        return cust;
    }

}
