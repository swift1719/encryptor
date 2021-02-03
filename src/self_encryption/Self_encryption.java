package self_encryption;
import java.util.Scanner;
import java.sql.*;
import java.io.*;

/**
 *
 * @author Ayush
 */
public class Self_encryption {

    public static void main(String[] args) {

//      *********************************************************************
//                            VERSION 2( Files Compatible)  
//      *********************************************************************

        Scanner sc = new Scanner(System.in);
        
        try
        {
            String src="",enc="",dec="",key="";
            
            System.out.println("\tWelcome to encryptor!!! \n\t--secure your files--\n=====================================\n");
            String ch="";
            do{
                
                System.out.print("Enter file path: ");
                src=sc.nextLine();
                System.out.print("Enter encrypted file path: ");
                enc=sc.nextLine();
                System.out.print("Enter decrypted file path: ");
                dec=sc.nextLine();          

//          =========================================================================
//          Establishing connection to the database
            String user = "root";
            String pass = "";
            String url = "jdbc:mysql://localhost:3306/test";
            
            Connection conn;
            Statement stmt;
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user,pass);
            stmt = conn.createStatement();
            System.out.println("Database connection established");

//          ========================================================================
//          Encrypting file
            System.out.print("Enter key to encrypt : ");
            key=sc.nextLine();
            Encryptor objEnc = new Encryptor(key);            
            objEnc.encrypt(src,enc);
            System.out.println("Encryption Done!!!");
//          ========================================================================
//          Storing encrypted file to database        
                    
            stmt.execute("create table if not exists filetable(filename varchar(40) not null primary key,data LONGBLOB)");
            

            PreparedStatement ps=conn.prepareStatement("insert into filetable values(?,?)");  
            
            FileInputStream fin=new FileInputStream(enc);
            ps.setString(1,src);  
            ps.setBinaryStream(2, fin);
            int i=ps.executeUpdate();  
            
            System.out.println("("+src+") file encrypted and stored in database"); 
  
            //code to delete enc file after storing it to database
            //code it up here
            
//          ==========================================================================
//          Retrieving enc file from the database

            PreparedStatement ps_stmt = conn.prepareStatement("select data from filetable where filename=?");
            ps_stmt.setString(1, src);
            ResultSet rs=ps_stmt.executeQuery();  

            FileOutputStream fout = new FileOutputStream(enc);
           
            while(rs.next()) 
            {
                InputStream input = rs.getBinaryStream("data");
                input.transferTo(fout);
            }
            fin.close();
            fout.close(); 

//          =========================================================================
//          Decrypting the enc file from the database 
            Decryptor objDec = new Decryptor(key);
            objDec.decrypt(enc,dec);
            System.out.println("Decryption Done!!!");
            
            
            System.out.println("Do you want to continue?(y/n)");
            ch=sc.nextLine();
            }while(ch.equalsIgnoreCase("y"));
             sc.close();
        }
        catch(Exception e)
        {
            System.out.println("Err : "+e.getMessage());
        }   
        
    

// //        ********************************************************************
// //                          VERSION 1
// //        ********************************************************************
//        String data = "The data privacy laws are the need of the hour.";
//        String key = "hard";
//        KeyGenerator kGen = new KeyGenerator(key);
//        String nK=kGen.getNumericKey();
       
// //        System.out.println("generated key: "+nK);

//        ArmstrongManager aMgr = new ArmstrongManager(nK);
//        ColorManager cMgr = new ColorManager(nK);
       
//        String encData = "";
//        int temp;
//        int i;
//        for(i=0;i<data.length();i++)
//        {
//            temp = aMgr.encrypt(data.charAt(i));
//            temp = cMgr.encrypt(temp);
//            encData = encData + (char)temp;
//        }
       
//        String decData = "";
      
//        for(i=0;i<encData.length();i++)
//        {
//            temp = cMgr.decrypt(encData.charAt(i));
//            temp = aMgr.decrypt(temp);
//            decData = decData + (char)temp;
//        }
        
//        System.out.println("================================================================================\n");
//        System.out.println("data: "+ data );
//        System.out.println("Encrypted Data: " + encData);
//        System.out.println("Decrypted Data: "+ decData);
    }
    
}
