import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class ChiffrerDechiffrer {

	private SecretKey myDesKey = null;

	/**
	 * Permet de chiffrer un texte
	 * 
	 * @param chaine : texte à chiffrer
	 * @return : tableau de byte représentant le texte chiffré
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
			// System.out.println("chaine cryptée : " + chaine);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chaine;
	}

	/**
	 * Permet de déchiffrer un texte chiffré préalablement
	 * 
	 * @param chaine : tableau de byte représentant la chaine chiffrée
	 * @return : une chaine déchiffrée
	 */
	public String dechiffrer(String chaine) {
		try {
			Cipher desCipher;
			desCipher = Cipher.getInstance("DES");
			desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
			byte[] textDecrypted = desCipher.doFinal(chaine.getBytes());
			chaine = new String(textDecrypted);
			// System.out.println("chaine decryptée : " + chaine);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chaine;
	}

	/**
	 * Permet de récupérer la clé de chiffrement
	 * 
	 * @return la clé de chiffrement
	 */
	public SecretKey getMyDesKey() {
		return myDesKey;
	}

	/**
	 * Permet d'associer une clé à l'objet de la classe
	 * @param myDesKey la clé à associer à l'objet
	 */
	public void setMyDesKey(SecretKey myDesKey) {
		this.myDesKey = myDesKey;
	}

	/**
	 * Permet de générer une clé secrette, à faire avant le chiffrement
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public void generateSecretKey() throws NoSuchAlgorithmException {
		KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
		myDesKey = keygenerator.generateKey();
	}
}
