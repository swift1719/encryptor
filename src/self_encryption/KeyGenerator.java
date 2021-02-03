
package self_encryption;

/*
Using the permutation and combinations of armstrong numbers, we will generate
a numeric key from the key string provided by input.
*/
public class KeyGenerator {
   
    //private: the below data is for the use of this class only
    //static : one time allocation for all the objects of this class, as the 
    //        below data doesn't need to be manipulated by objects of this class        
    private static int armstrongNumber[]={153,370,371,407};
    private static int baseTable[]= {1234, 1243, 1324, 1342, 1423, 1432, 2134, 2143, 2314, 2341, 2413, 2431, 3124, 3142, 3214, 3241, 3412, 3421, 4123, 4132, 4213, 4231, 4312, 4321};
    
    //non-static data belongs to object of the class(as every time user provides 
    //new key ,we need to generate a new numeric key)
    private String key,numericKey;
    
    KeyGenerator(String k)
    {
        //this is implicitly declared and used
        this.key = k;
        this.numericKey = "";
    }
    
    String getNumericKey()
    {
        if(numericKey.equals(""))
            generateNumericKey();
        
        return numericKey;
    }
    
    private void generateNumericKey()
    {
        int tot=0,i;
        
        //summing up ascii values of characters in key provided by user
        for(i=0;i<key.length();i++)
        {
            tot +=key.charAt(i);
//            System.out.print(tot+" ");
        }
//        System.out.println("------------------------------------------");
        
        //using the above obtained sum, now we will select a permutation from 
        //the array containing all permutation of 1234 (as there exists 4 
        //armstrong numbers)
        int permutation = baseTable[tot % baseTable.length];
//        System.out.println("total : "+tot+"\npermu selected "+permutation);
        String temp = "";
        //now using the permutation selected from the array of permutations
        //we will form key by selecting the armstrong numbers from array of
        //armstrong numbers in the order they appear the selected permutation
        //and generate a 1st part of numeric key
        while(permutation>0)
        {
            temp = armstrongNumber[permutation%10 -1] + temp;
            permutation/=10;
        }
        
        //Final numeric key is obtained by appending the sum of ascii values of 
        //characters within the key provided by user
        numericKey = temp + tot;//final key = part 1 + part 2 
        
//        displayGeneratedKey();
    }
    
    void displayGeneratedKey()
    {
        System.out.println("Original Key : " + key);
        System.out.println("NumericKey : " + numericKey);
    }
    
}
