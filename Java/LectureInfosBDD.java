import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;
import javax.crypto.spec.SecretKeySpec;

public class LectureInfosBDD {
	private String dbName;
	private String url;
	private String username;
	private String password;
	private String chemin = ".\\srvProperties\\Myproperties.properties";

	/**
	 * Constructeur sans argument
	 * @throws NoSuchAlgorithmException 
	 */
	public LectureInfosBDD() throws NoSuchAlgorithmException {
		try {
			File f = new File(chemin);
			if(!f.isFile()) {
				creationFichier();
			}
			lectureFichier();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Permet la création et le remplissage du fichier
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 */
	public void creationFichier() throws NoSuchAlgorithmException, IOException {
		// On instancie un nouvel objet Properties
		Properties myProps = new Properties();
		ChiffrerDechiffrer ch = new ChiffrerDechiffrer();
		ch.generateSecretKey();
		// On y insère des paires [clé,valeur]
		myProps.setProperty("dbName", ch.chiffrer("quizz"));
		myProps.setProperty("url", ch.chiffrer("jdbc:postgresql://localhost:5432/"));
		myProps.setProperty("username", ch.chiffrer("postgres"));
		myProps.setProperty("password", ch.chiffrer("palaumae"));
		//On transforme la clé secrète en String via json
		String encodedKey = Base64.getEncoder().encodeToString(ch.getMyDesKey().getEncoded());
		myProps.setProperty("key", encodedKey);
		try {
			// On stocke le fichier sur le disque
			OutputStream out = new FileOutputStream(chemin);
			myProps.store(out, "log BDD");
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de lire les informations contenu dans le fichier properties
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void lectureFichier() throws FileNotFoundException, IOException {
		Properties myProps = new Properties();
		//On charge le fichier
		myProps.load(new FileInputStream(chemin));
		ChiffrerDechiffrer ch = new ChiffrerDechiffrer();
		//On récupère la clé au format String et on la transforme en secretKey
		String encodedKey = myProps.getProperty("key");
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		ch.setMyDesKey(new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES"));
		dbName = ch.dechiffrer(myProps.getProperty("dbName"));
		url = ch.dechiffrer(myProps.getProperty("url"));
		username = ch.dechiffrer(myProps.getProperty("username"));
		password = ch.dechiffrer(myProps.getProperty("password"));
	}

	public String getDbName() {
		return dbName;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
