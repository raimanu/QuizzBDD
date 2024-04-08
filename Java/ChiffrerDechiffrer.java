import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class ChiffrerDechiffrer {

	private SecretKey myDesKey = null;

	/**
	 * Permet de chiffrer un texte
	 * 
	 * @param chaine : texte � chiffrer
	 * @return : tableau de byte repr�sentant le texte chiffr�
	 */
	public String chiffrer(String chaine) {
		byte[] textEncrypted = null;
		try {
			// Fonction permettant de chiffrer
			Cipher desCipher;
			desCipher = Cipher.getInstance("DES");
			byte[] text = chaine.getBytes("UTF8");
			desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
			textEncrypted = desCipher.doFinal(text);
			chaine = new String(textEncrypted);
			// System.out.println("chaine crypt�e : " + chaine);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chaine;
	}

	/**
	 * Permet de d�chiffrer un texte chiffr� pr�alablement
	 * 
	 * @param chaine : tableau de byte repr�sentant la chaine chiffr�e
	 * @return : une chaine d�chiffr�e
	 */
	public String dechiffrer(String chaine) {
		try {
			Cipher desCipher;
			desCipher = Cipher.getInstance("DES");
			desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
			byte[] textDecrypted = desCipher.doFinal(chaine.getBytes());
			chaine = new String(textDecrypted);
			// System.out.println("chaine decrypt�e : " + chaine);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chaine;
	}

	/**
	 * Permet de r�cup�rer la cl� de chiffrement
	 * 
	 * @return la cl� de chiffrement
	 */
	public SecretKey getMyDesKey() {
		return myDesKey;
	}

	/**
	 * Permet d'associer une cl� � l'objet de la classe
	 * @param myDesKey la cl� � associer � l'objet
	 */
	public void setMyDesKey(SecretKey myDesKey) {
		this.myDesKey = myDesKey;
	}

	/**
	 * Permet de g�n�rer une cl� secrette, � faire avant le chiffrement
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public void generateSecretKey() throws NoSuchAlgorithmException {
		KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
		myDesKey = keygenerator.generateKey();
	}
}
