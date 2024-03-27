import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class GraphiqueQuizz {
	private int score;
	private long timeElapsed;
	private boolean done = false;
	private int nbreQuestions;
	Scanner clavier = new Scanner(System.in);
  Connection connection = null;

  String[] questions;
  Question question;

	/**
	 * Constructeur de la classe ConsoleQuizz
	 * 
	 * @param nbreQuestion : Le nombre de questions � poser au joueur
   * Crée une connection avec une base de donnée PSQL
	 */
	public GraphiqueQuizz(int nbreQuestion, String password) {
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/quizz", "postgres", password);
      if (connection != null) {
        System.out.println("Connected to the database!");
      } else {
        System.out.println("Failed to make connection!");
      }
    } catch (Exception e) {
      System.out.println(e);
    }
		nbreQuestions = nbreQuestion;
	}

	/**
	 * Coeur du quizz, pose les questions, v�rifie les r�ponses et compte le score
   * Les questions sont prisent aléatoirement dans la base de donnée mais jamais ne se répètent
	 */
	public void start() {
		String userAnswer;
		try {
			long startTime = System.currentTimeMillis();
      for (int i = 0; i < nbreQuestions; i++) {
        int j = new Random().nextInt(1, 13);
        Requete requete = new Requete(connection);
        questions = requete.getQuestion(j);
        question = new Question(questions[0], questions[1]);
				userAnswer = JOptionPane.showInputDialog(null, question.getPays());
				if (userAnswer.equalsIgnoreCase(question.getVille())) {
					score++;
					JOptionPane.showMessageDialog(null, "Bonne Reponse");
				} else {
					JOptionPane.showMessageDialog(null, "Mauvaise Reponse");
					JOptionPane.showMessageDialog(null, "la bonne reponse etait: " + question.getVille());
				}
			}
			done = true;
			long endTime = System.currentTimeMillis();
			timeElapsed = endTime - startTime;
		} catch (IllegalArgumentException e) {
			done = false;
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Permet de convertir le temps de milliseconde � seconde
	 * 
	 * @param timeInMilliSeconds : temps en milliseconde
	 * @return : temps en seconde
	 */
	private int getTimeElapsedInSeconds(long timeInMilliSeconds) {
		return (int) (timeInMilliSeconds / 1000);
	}

	/**
	 * Affiche le r�sultat au joueur, un message d'erreur sinon
	 */
	public void displayResultats() {
		if (done) {
			displayScore();
			displayTimeElapsed();
		}
	}

	/**
	 * Affiche le temps utilis� par le joueur pour r�pondre aux questions
	 */
	private void displayTimeElapsed() {
		JOptionPane.showMessageDialog(null,
				String.format("Il vous a fallu environ %d secondes pour repondre aux questions",
						getTimeElapsedInSeconds(timeElapsed), nbreQuestions));

	}

	/**
	 * Affiche le score final
	 */
	private void displayScore() {
		JOptionPane.showMessageDialog(null, String.format("votre score final est de: %d/%d\n ", score, nbreQuestions));

	}
}
