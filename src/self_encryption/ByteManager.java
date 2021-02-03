
package self_encryption;

public class ByteManager {
    
    static int[] byteToNibbles(int x)
    {
        int arr[] = new int[2];
        arr[0] = (x & 255) >> 4;
        arr[1] = (x & 15);
//        System.out.println("\n**********\nByte Manager.");
//        System.out.println("part1: "+arr[0]+" --- "+"part2: "+arr[1]);
        return arr;
    }
    
    static int nibblesToByte(int nibbles[])
    {
        return ((nibbles[0] & 15) << 4) | ((nibbles[1] & 15));
    }
    
}
