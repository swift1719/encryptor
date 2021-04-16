/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package self_encryption;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Ayush
 */
public class DatabaseConnectivity {
    String user,pass,url;
    Connection conn;
    Statement stmt;
    DatabaseConnectivity(String user,String pass,String url){
        this.pass=pass;
        this.url=url;
        this.user=user;
    }
    void connect() throws ClassNotFoundException, SQLException{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(this.url, this.user,this.pass);
            stmt = conn.createStatement();
            System.out.println("Database connection established");

    }
    @SuppressWarnings("ConvertToTryWithResources")
    void storeToDB(String src,String enc) throws FileNotFoundException, IOException{
        try {
            stmt.execute("create table if not exists filetable(filename varchar(40) not null primary key,data LONGBLOB)");
            
            PreparedStatement ps=conn.prepareStatement("insert into filetable values(?,?)");
            
            FileInputStream fin=new FileInputStream(enc);
            ps.setString(1,src);  
            ps.setBinaryStream(2, fin);
            int i=ps.executeUpdate();  
            fin.close();
            
            System.out.println("("+src+") file encrypted and stored in database");
            
            //delete encrypted file after storing it to database
            File file=new File(enc);
            if(file.delete()){
                System.out.println("Encrypted src - "+enc+" file deleted successfully!!!");
            }else{
                System.out.println("Failed to delete.");
            }
        } catch (SQLException ex) {
            System.out.println("\t"+ex.getMessage());
        }
    }
    @SuppressWarnings("ConvertToTryWithResources")
    boolean retreiveFromDB(String src,String enc) throws FileNotFoundException, IOException{
        boolean found=true;
        try {
            PreparedStatement ps_stmt = conn.prepareStatement("select data from filetable where filename=?");
            ps_stmt.setString(1, src);
            ResultSet rs=ps_stmt.executeQuery();
            if(rs.next()==false){
                System.out.println("No such file found in Database.");
                found=false;
            }else{
                FileOutputStream fout = new FileOutputStream(enc);
                
                do{
                    InputStream input = rs.getBinaryStream("data");
                    input.transferTo(fout);
                }while(rs.next());
                fout.close();
            }
        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseConnectivity.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }finally{
            return found;
        }
    }
    void disconnect() throws SQLException{
        conn.close();
    }
}
