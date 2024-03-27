import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ConsoleQuizz {
  private int score = -1;
  private long timeElapsed = -1;
  private int nbreQuestion = 0;
  Scanner clavier = new Scanner(System.in);

  /**
   * Constructeur de la classe ConsoleQuizz
   *
   * @param nbreQuestion : Le nombre de questions à poser au joueur
   */
  public ConsoleQuizz(int nbreQuestion) {
    this.nbreQuestion = nbreQuestion;
  }

  /**
   * Constructeur sans arguments de la classe ConsoleQuizz
   *
   * @param nbreQuestion : Le nombre de questions à poser au joueur est demandé à ce dernier
   */
  public ConsoleQuizz() {
    System.out.println("veuillez entrer le nombre de questions auxquelles vous voulez répondre : ");
    String userAnswer = clavier.nextLine();
    nbreQuestion = Integer.parseInt(userAnswer);
  }

  /**
   * Coeur du quizz, pose les questions, vérifie les réponses et compte le score
   */
  public void start() {
    try {
      long startTime = System.currentTimeMillis();
      ArrayList<Question> questions = generate(nbreQuestion);
      score = 0;
      for (int i = 0; i < nbreQuestion; i++) {
        System.out.println("Quelle est la capitale de ce pays: " + questions.get(i).getPays());
        String userAnswer = clavier.nextLine();

        if (userAnswer.equalsIgnoreCase(questions.get(i).getVille())) {
          score++;
          System.out.println("Bonne Reponse");
        } else {
          System.out.println("Mauvaise Reponse");
          System.out.println("la bonne reponse etait: " + questions.get(i).getVille());
        }
      }
      long endTime = System.currentTimeMillis();
      timeElapsed = endTime - startTime;
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

  }

  /**
   * Permet de convertir le temps de milliseconde à seconde
   *
   * @param timeInMilliSeconds : temps en milliseconde
   * @return : temps en seconde
   */
  private int getTimeElapsedInSeconds(long timeInMilliSeconds) {
    return (int) (timeInMilliSeconds / 1000);
  }

  /**
   * Affiche le résultat au joueur, un message d'erreur sinon
   */
  public void displayResultats() {
    if (score != -1 && (int)timeElapsed != -1) {
      displayScore();
      displayTimeElapsed();
    } else {
      System.out.println("Vous n'avez rien répondu ou une erreur est survenue");
    }
  }

  /**
   * Affiche le temps utilisé par le joueur pour répondre aux questions
   */
  private void displayTimeElapsed() {
    System.out.printf("Il vous a fallu environ %d secondes pour repondre aux %d questions",
        getTimeElapsedInSeconds(timeElapsed), nbreQuestion);
  }

  /**
   * Affiche le score final
   */
  private void displayScore() {
    System.out.printf("votre score final est de: %d/%d\n ", score, nbreQuestion);

  }

  /**
   * Permet de créer un tableau de x questions-réponses, x étant choisit au lancement du jeu
   * @param nbreQuestions : nombre de question choisit au lancement du jeu
   * @return : un tableau de questions
   */
  public ArrayList<Question> generate(int nbreQuestions) {
    String[][] data = getData();
    ArrayList<Question> questions = new ArrayList<Question>();

    if (nbreQuestions > data.length) {
      throw new IllegalArgumentException("On ne peut generer que: " + data.length + " questions maximun");
    }
    for (int i = 0; i < getData().length; i++) {
      questions.add(new Question(data[i][0], data[i][1]));
    }
    Collections.shuffle(questions);
    return questions;
  }

  /**
   * Base de données non persistantes
   *
   * @return un tableau à deux dimensions contenant les couples pays-capitale au format brut
   */
  private static String[][] getData() {
    String[][] data = { { "Senegal", "Dakar" }, { "France", "Paris" }, { "Haiti", "Paup" },
        { "Argemtine", "Brenos.A" }, { "Brasil", "brasilia" }, { "Italie", "Rome" }, { "Perou", "Lima" },
        { "Allemagne", "Berlin" }, { "Canada", "Otawa" } };
    return data;
  }
}
