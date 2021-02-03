
package self_encryption;

import java.io.*;

public class Decryptor {
    
    KeyGenerator kGen;
    
    public Decryptor(String key)
    {
        kGen = new KeyGenerator(key);
    }
    
    public void decrypt(String src,String trgt)throws Exception
    {
        FileInputStream fin = new FileInputStream(src);
        
        FileOutputStream fout = new FileOutputStream(trgt);
        
        String numericKey = kGen.getNumericKey();
        //for level 1 of encryption
        ArmstrongManager aMgr = new ArmstrongManager(numericKey);
        //for level 2 of encryption
        ColorManager cMgr = new ColorManager(numericKey);
        
        byte buffer[] = new byte[2048];
        int n,i;
        int temp;
        
        while((n = fin.read(buffer)) != -1)
        {
            for(i = 0 ;i < n ;i++)
            {
                temp = cMgr.decrypt(buffer[i] & 0xFF);
                temp = aMgr.decrypt(temp);
                buffer[i] = (byte)temp;
            }
            //writing into result
            fout.write(buffer,0,n);
        }
        fin.close();
        fout.close();
    }
    
}
