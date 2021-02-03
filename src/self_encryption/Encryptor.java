package self_encryption;
import java.io.*;

public class Encryptor {
    
    KeyGenerator kGen;
    
    public Encryptor(String key)
    {
        kGen = new KeyGenerator(key);
    }
    
    public void encrypt(String src,String trgt)throws Exception
    {
        //  opening the source file for reading, if the source file doesn't
        //exist then throw exception
        FileInputStream fin = new FileInputStream(src);
        
        //opening the target file for writing 
        //file will be created or overwritten
        //invalid path or permission issue may cause FileNotFoundException
        FileOutputStream fout = new FileOutputStream(trgt);
        
        //getting the key
        String nK=kGen.getNumericKey();
        
        //for level 1 of encryption
        ArmstrongManager aMgr = new ArmstrongManager(nK);
        
        //for level 2 of encryption
        ColorManager cMgr = new ColorManager(nK);
        
        byte buffer[] = new byte[2048];
        int n,i;
        int temp;
//                System.out.println("Encryptor");
        
        while((n = fin.read(buffer)) != -1)
        {
            //processing the individual buffer
            for(i = 0;i < n; i++)
            {
                
                temp = aMgr.encrypt(buffer[i]&0xFF);
//                System.out.println("after armstrong encryption: "+temp);
                temp = cMgr.encrypt(temp);
//                System.out.println("After color encryption: "+temp);
                buffer[i] = (byte)temp;
            }
            //writing into the result
            fout.write(buffer, 0, n);
        }
        //close the files
        fin.close();
        fout.close();
    }
}
