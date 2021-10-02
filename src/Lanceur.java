public class Lanceur {

    public static void main(String[] args) {
        Joueur player=new Joueur();
        Plateau plateau=new Plateau(3,3,1);
        Jeu partie=new Jeu(player,plateau);
        partie.jouer();
    }
}
