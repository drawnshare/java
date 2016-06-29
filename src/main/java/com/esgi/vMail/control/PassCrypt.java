package com.esgi.vMail.control;

import java.io.*;
import java.math.*;
import java.util.*;
import javax.crypto.*;
import java.security.*;
import javax.crypto.spec.*;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.interfaces.*;
import java.text.Format;


/**
 * Cette classe propose des méthodes permettant de crypter et décrypter des
 * messages avec l'algorithme de Blowfish.
 */
public class PassCrypt {
	public final static int KEY_SIZE = 128;  // [32..448]

	private Key secretKey;


	public PassCrypt() {
	}


	public Key getSecretKey() {
		return secretKey;
	}


	/**
	 * Retourne toutes les informations de la clé sous forme d'un tableau de
	 * bytes. Elle peut ainsi être stockée puis reconstruite ultérieurement en
	 * utilisant la méthode setSecretKey(byte[] keyData)
	 */
	public byte[] getSecretKeyInBytes() {
		return secretKey.getEncoded();
	}


	public void setSecretKey(Key secretKey) {
		this.secretKey = secretKey;
	}


	/**
	 * Permet de reconstruire la clé secrète à partir de ses données, stockées
	 * dans un tableau de bytes.
	 */
	public void setSecretKey(byte[] keyData) {
		secretKey = new SecretKeySpec(keyData, "Blowfish");
	}


	public void generateKey() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("Blowfish");
			keyGen.init(KEY_SIZE);
			secretKey = keyGen.generateKey();
		}
		catch (Exception e) {System.out.println(e);}
	}


	public byte[] crypt(byte[] plaintext) {
		try {
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipher.doFinal(plaintext);
		}
		catch (Exception e) {System.out.println(e);}
		return null;
	}

	public String encryptB64(String plaintext) {
		BASE64Encoder encoder = new BASE64Encoder();
		return new String(encoder.encode(crypt(plaintext)));
	}

	public String decryptB64(String cipherText) {
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			return this.decryptInString(decoder.decodeBuffer(cipherText));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public byte[] crypt(String plaintext) {
		return crypt(plaintext.getBytes());
	}


	public byte[] decryptInBytes(byte[] ciphertext) {
		try {
			System.out.println(ciphertext);
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return cipher.doFinal(ciphertext);
		}
		catch (Exception e) {System.out.println(e);System.out.println("PassCrypt.decryptInBytes()");}
		return null;
	}


	public String decryptInString(byte[] ciphertext) {
		return new String(this.decryptInBytes(ciphertext));
	}
}
