
public class StartGame {
/**
 * La fonction main est le point d'entr�e du programme, elle est unique, lanc�e au d�but et obligatoire
 * @param args : arguments pass�s lors de l'appel depuis une invite de commandes
 */
	public static void main(String[] args) {
		GraphiqueQuizz quiz = new GraphiqueQuizz(5, "palaumae");
		quiz.start();
		quiz.displayResultats();
	}
}
