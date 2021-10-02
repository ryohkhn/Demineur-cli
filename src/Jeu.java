public class Jeu {
    private Joueur player;
    private Plateau demineur;

    public Jeu(Joueur joueur,Plateau plateau){
        this.player=joueur;
        this.demineur=plateau;
    }

    public void jouer(){
        if(!player.veutJouer()) return;
        int[] coord;
        player.demanderNom();
        int[] dim=player.demanderDimensions();
        int mines=player.demanderNbMines();
        demineur=new Plateau(dim[0],dim[1],mines);
        System.out.println("Bonne partie "+this.player.nom+" !");
        while(!demineur.jeuPerdu()){
            demineur.afficheCourant();
            if(player.demanderAction()=='r'){
                coord=player.demanderCoordonnes();
                demineur.revelerCaseRec(coord[0],coord[1],false);
            }
            else{
                coord=player.demanderCoordonnes();
                demineur.drapeauCase(coord[0],coord[1]);
            }
            if(demineur.jeuGagne()) break;
        }
        if(demineur.jeuPerdu()){
            System.out.println("Dommage vous avez révélé une bombe, vous réussirez peut-être la prochaine fois !");
        }
        else{
            System.out.println("Félicitations, vous avez gagné !");
        }
        this.jouer();
        player.finish();
    }
}
