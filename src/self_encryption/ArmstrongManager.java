
package self_encryption;


public class ArmstrongManager {
    
    private String numericKey;
    private int enc_index,dec_index;
    
    ArmstrongManager(String nk)
    {
        numericKey = nk;
        enc_index=-1;
        dec_index=-1;
    }
    
    int encrypt(int data)
    {
        try
        {
//            System.out.println("\n--------\nArmstrong MGR: ");
            int tem=data ^ numericKey.charAt(++enc_index);
//            System.out.println("nKatEncIndex: "+numericKey.charAt(enc_index));
//            System.out.print("enc data: ");
//            System.out.print(data+" : "+tem+"\n");

            return tem;
        }
        catch(StringIndexOutOfBoundsException e)
        {
            enc_index=-1;
            return data ^ numericKey.charAt(++enc_index);
        }
    }
    
    int decrypt(int data)
    {
        try
        {
            return data ^ numericKey.charAt(++dec_index);
        }
        catch(StringIndexOutOfBoundsException e)
        {
            dec_index = -1;
            return data ^ numericKey.charAt(++dec_index);
        }
    }
}
