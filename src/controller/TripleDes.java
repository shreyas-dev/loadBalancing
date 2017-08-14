package controller;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
public class TripleDes{
	static String key="AllHailShreyas";
	public static String encrypt(String text) 
    {
		String encrypt="";
		 try 
	     {
	         // Create key and cipher
	         Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	         Cipher cipher = Cipher.getInstance("AES");
	         // encrypt the text
	         cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	         byte[] encrypted = cipher.doFinal(text.getBytes());
	         encrypt=new String(encrypted);
	     }
	     catch(Exception e) 
	     {
	         e.printStackTrace();
	     }
		 return encrypt;
    }
	public static String decrypt(String text) 
    {
		String decrypted="";
		try 
		{
			// Create key and cipher
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			// decrypt the text
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			decrypted = new String(cipher.doFinal(text.getBytes()));
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return decrypted;
    }
	public static void main() {
		String s="asasa";
		System.out.println(encrypt(s));
		s=encrypt(s);
		System.out.println(decrypt(s));
		
	}
}
