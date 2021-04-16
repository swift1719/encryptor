package self_encryption;
import java.util.Scanner;
/**
 *
 * @author Ayush
 */
public class Self_encryption {

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) throws Exception {

//      *********************************************************************
//                            VERSION 2( Files Compatible)  
//      *********************************************************************

        Scanner sc = new Scanner(System.in);

        String src="",enc="",key="";
        DatabaseConnectivity dbc;
        System.out.println("\tWelcome to encryptor!!! \n\t--secure your files--\n=====================================");
        String ch="";

//  =========================================================================
//  Establishing connection to the database
        String user = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/test";
        dbc=new DatabaseConnectivity(user,pass,url);
        dbc.connect();
        int choice;
        do{
            System.out.print("\tPress:\n\t1.to encrypt and store in DB\n\t2.to retreive from DB\n\t");
            choice=sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1:
                    System.out.print("Enter file path: ");
                    src=sc.nextLine();
                    enc="G:/encryted."+src.substring(src.length()-3);        
                    System.out.print("Enter key to encrypt : ");
                    key=sc.nextLine();
                    
                    //  ========================================================================
                    //  Encrypting file
                    Encryptor objEnc = new Encryptor(key);            
                    objEnc.encrypt(src,enc);
                    System.out.println("Encryption Done!!!");

                    //  ========================================================================
                    //  Storing encrypted file to database           
                    dbc.storeToDB(src, enc);

                    break;
                case 2:
                    System.out.print("Enter file path: ");
                    src=sc.nextLine();
                    enc="G:/encryted."+src.substring(src.length()-3); 
                    System.out.print("Enter your key to decrypt : ");
                    key=sc.nextLine();
                    //  ========================================================================
                    //  Retrieving enc file from the database
                    boolean found=dbc.retreiveFromDB(src, enc);

                    //  ========================================================================
                    //  Decrypting the enc file from the database 
                    if(found==true){
                        Decryptor objDec = new Decryptor(key);
                        objDec.decrypt(enc,src);
                        System.out.println("Decryption Done!!!");   
                    }

                    break;
                default:
                    System.out.println("Please select a valid option...");
            }
            
            System.out.println("Do you want to continue?(y/n)");
            ch=sc.nextLine();
        }while(ch.equalsIgnoreCase("y"));
        
//  ========================================================================
//  Closing connection
    dbc.disconnect();
    sc.close(); 
    
    }   
}
