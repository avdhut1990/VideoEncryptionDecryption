package com.toolbox.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;

public class EncryptDecryptVideo {

	public void encryptVideoFile(File src, String destDir, SecretKey skey) {
		String filename = src.getName();
		File encryptedFile = new File(destDir, filename.substring(0, filename.lastIndexOf("."))+"_enc.ddd");
		try {
	        FileInputStream fis = new FileInputStream(src);
	        FileOutputStream fos = new FileOutputStream(encryptedFile);
	        int read;
	        
	        if(!encryptedFile.exists())
	            encryptedFile.createNewFile();
	        
	        Cipher encipher = Cipher.getInstance("AES");
	        
	        encipher.init(Cipher.ENCRYPT_MODE, skey);
	        CipherInputStream cis = new CipherInputStream(fis, encipher);

	        while((read = cis.read()) != -1) {
				fos.write((char)read);
				fos.flush();
			}
	        cis.close();
	    	fis.close();
	    	fos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public File decryptVideoFile(File src, SecretKey skey) throws Exception {
		String workingDir = System.getProperty("user.dir");
		File decryptedFile = new File(workingDir, "movie.mp4");
		FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(decryptedFile);
        int read;

        if(!decryptedFile.exists())
            decryptedFile.createNewFile();
        
        Cipher decipher = Cipher.getInstance("AES");
        decipher.init(Cipher.DECRYPT_MODE, skey);
        CipherOutputStream cos = new CipherOutputStream(fos, decipher);
        
        while((read = fis.read()) != -1) {
			cos.write(read);
			cos.flush();
        }
    	cos.close();
    	fis.close();
    	fos.close();

    	return decryptedFile;
	}
	
}
