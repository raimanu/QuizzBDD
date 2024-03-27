
public class StartGame {
/**
 * La fonction main est le point d'entrée du programme, elle est unique, lancée au début et obligatoire
 * @param args : arguments passés lors de l'appel depuis une invite de commandes
 */
	public static void main(String[] args) {
		GraphiqueQuizz quiz = new GraphiqueQuizz(5, "palaumae");
		quiz.start();
		quiz.displayResultats();
	}
}
