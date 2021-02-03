package self_encryption;
// import java.util.Scanner;
// import java.sql.*;
// import java.io.*;

/**
 *
 * @author Ayush
 */
public class Self_encryption {

    public static void main(String[] args) {

//        ********************************************************************
//                          VERSION 1
//        ********************************************************************
       String data = "The data privacy laws are the need of the hour.";
       String key = "hard";
       KeyGenerator kGen = new KeyGenerator(key);
       String nK=kGen.getNumericKey();
       
//        System.out.println("generated key: "+nK);

       ArmstrongManager aMgr = new ArmstrongManager(nK);
       ColorManager cMgr = new ColorManager(nK);
       
       String encData = "";
       int temp;
       int i;
       for(i=0;i<data.length();i++)
       {
           temp = aMgr.encrypt(data.charAt(i));
           temp = cMgr.encrypt(temp);
           encData = encData + (char)temp;
       }
       
       String decData = "";
      
       for(i=0;i<encData.length();i++)
       {
           temp = cMgr.decrypt(encData.charAt(i));
           temp = aMgr.decrypt(temp);
           decData = decData + (char)temp;
       }
        
       System.out.println("================================================================================\n");
       System.out.println("data: "+ data );
       System.out.println("Encrypted Data: " + encData);
       System.out.println("Decrypted Data: "+ decData);
    }
    
}
