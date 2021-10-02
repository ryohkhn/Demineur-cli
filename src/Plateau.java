import java.util.Random;

public class Plateau {
    public int hauteur;
    public int largeur;
    public int nbMines;
    public int nbDrapeaux;
    private boolean[][] mines;
    private int[][] etats;
    private int[][] adja;

    public Plateau(int hauteur,int largeur,int nbMines){
        if(hauteur<=1) this.hauteur=2;
        else this.hauteur=hauteur;
        if(largeur<=1) this.largeur=2;
        else this.largeur=largeur;
        if(nbMines<=1) this.nbMines=2;
        else this.nbMines=nbMines;
        this.nbDrapeaux=0;
        this.mines=new boolean[hauteur+2][largeur+2];
        this.etats=new int[hauteur+2][largeur+2];
        this.adja=new int[hauteur+2][largeur+2];
        this.ajouteMinesAlea();
        this.calculeAdjacence();
    }

    private void ajouteMinesAlea(){
        Random rd=new Random();
        int temp=nbMines; // récupère la valeur du nombre de mines pour ne pas la diminuer à la fin de la boucle while
        while(temp>0){
            int hauteurRd=rd.nextInt(hauteur)+1; // valeur aléatoire de la hauteur
            int largeurRd=rd.nextInt(largeur)+1; // valeur aléatoire de la largeur
            if(!mines[hauteurRd][largeurRd]){ // on vérifie si une bombe est déjà présente puis on décrémente temp
                mines[hauteurRd][largeurRd]=true;
                temp--;
            }
        }
    }

    private void calculeAdjacence(){
        int res;
        for(int i=1;i<mines.length-1;i++){
            for(int j=1;j<mines[i].length-1;j++){
                res=0;
                for(int k=j-1;k<=j+1;k++){ // boucle qui traite les trois cases à la hauteur i-1 de la case actuelle
                    if(mines[i-1][k]){
                        res++;
                    }
                }
                for(int k=j-1;k<=j+1;k++){ // boucle qui traite les trois cases à la hauteur i+1 de la case actuelle
                    if(mines[i+1][k]){
                        res++;
                    }
                }
                if(mines[i][j-1]){ // cas de la case à gauche de la case actuelle
                    res++;
                }
                if(mines[i][j+1]){ // cas de la case à droite de la case actuelle
                    res++;
                }
                adja[i][j]=res; // on attribue la valeur de la variable une fois toute la largeur parcourue
            }
        }
    }

    public void afficheTout(){
        String alphabet= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        // affichage du nombre de mines et de drapeaux dans un cadre
        System.out.print("********************\n* Mines / Drapeaux *\n*   "+this.nbMines+"  /     "+this.nbDrapeaux+"    *\n********************\n    ");
        for(int i=1;i<this.largeur+1;i++){
            System.out.print(i+" ");
        }
        System.out.print("\n----");
        for(int i=1;i<this.largeur+1;i++){
            System.out.print("--");
        }
        System.out.println();
        for(int i=1;i<mines.length-1;i++){
            System.out.print(alphabet.charAt(i-1)+" | "); // affichage des lettres en fonction de la valeur de la hauteur i
            for(int j=1;j<mines[i].length-1;j++){
                if(mines[i][j]){ // cas ou une mine est présente
                    System.out.print("* ");
                }
                else{
                    System.out.print(adja[i][j]+" "); // si pas de mine on affiche le nombre de mines adjacentes à la case
                }
                if(j==mines[i].length-2){
                    System.out.println(); // retour à la ligne une fois qu'on a atteint la dernière valeur de tableau
                }
            }
        }
    }

    public void revelerCase(int hauteur,int largeur){
        // on return directement dans le cas ou notre fonction récursive va sur une ligne ou colonne qui n'est pas dans le jeu
        if(hauteur>=this.etats.length-1 || largeur>=this.etats[0].length-1 || hauteur<=0 || largeur<=0){
            System.out.println("Veuillez entrer des coordonnées valables");
            return;
        }
        if(etats[hauteur][largeur]==1 || etats[hauteur][largeur]==2){ // cas ou la case est déjà révélée ou a un drapeau
            System.out.println("Votre case ne peut pas être révelée");
            return;
        }
        if(etats[hauteur][largeur]==0){ // cas ou on peut révéler la case
            etats[hauteur][largeur]=2;
        }
    }

    // j'ai décidé d'ajouter un boolean en argument pour que quand la fonction est appellée recursivement sur les 8 cases autour d'elle,
    // il n'y ait plus de print pour informer que la case ne peut pas être révélée, ou que les coordonnées sont fausses
    public void revelerCaseRec(int hauteur,int largeur,boolean rec){
        // on return directement dans le cas ou notre fonction récursive va sur une ligne ou colonne qui n'est pas dans le jeu
        if(hauteur>=this.etats.length-1 || largeur>=this.etats[0].length-1 || hauteur<=0 || largeur<=0){
            if(!rec){
                System.out.println("Veuillez entrer des coordonnées valables");
            }
            return;
        }
        if(etats[hauteur][largeur]==1 || etats[hauteur][largeur]==2){ // cas ou la case est déjà révélée ou a un drapeau
            if(!rec){
                System.out.println("Votre case ne peut pas être révélée");
            }
            return;
        }
        if (etats[hauteur][largeur] == 0){ // cas ou on peut révéler la case
            etats[hauteur][largeur]=2;
        }
        if(adja[hauteur][largeur]==0 && !mines[hauteur][largeur]){
            for(int k=largeur-1;k<=largeur+1;k++){ // récursion pour les trois cases au dessus de la case actuelle
                revelerCaseRec(hauteur-1,k,true);
            }
            for(int k=largeur-1;k<=largeur+1;k++){ // récursion pour les trois cases en dessous de la case actuelle
                revelerCaseRec(hauteur+1,k,true);
            }
            revelerCaseRec(hauteur,largeur-1,true); // récursion pour la case de gauche
            revelerCaseRec(hauteur,largeur+1,true); // récursion pour la case de droite
        }
    }

    public void drapeauCase(int hauteur,int largeur){
        if(hauteur>=this.etats.length-1 || largeur>=this.etats[0].length-1 || hauteur<=0 || largeur<=0){
            System.out.println("Veuillez entrer des coordonnées valables");
            return;
        }
        if(etats[hauteur][largeur]==1){ // cas ou on enlève un drapeau
            etats[hauteur][largeur]=0;
            this.nbDrapeaux--;
            return;
        }
        if(etats[hauteur][largeur]==2){ // cas ou la case est révélée
            System.out.println("Vous ne pouvez pas déposer un drapeau sur une case déjà révelée");
            return;
        }
        etats[hauteur][largeur]=1;
        this.nbDrapeaux++;
    }

    public void afficheCourant(){
        String alphabet= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        // affichage du nombre de mines et de drapeaux dans un cadre
        System.out.print("********************\n* Mines / Drapeaux *\n*   "+this.nbMines+"  /     "+this.nbDrapeaux+"    *\n********************\n    ");
        for(int i=1;i<this.largeur+1;i++){
            System.out.print(i+" ");
        }
        System.out.print("\n----");
        for(int i=1;i<this.largeur+1;i++){
            System.out.print("--");
        }
        System.out.println();
        for(int i=1;i<mines.length-1;i++){
            System.out.print(alphabet.charAt(i-1)+" | "); // affichage des lettres en fonction de la valeur de la hauteur i
            for(int j=1;j<mines[i].length-1;j++){
                if(etats[i][j]==0){
                    System.out.print(". "); // affichage des cases cachées
                }
                else if(etats[i][j]==1){
                    System.out.print("? "); // affichage des drapeaux
                }
                else if(etats[i][j]==2){
                    System.out.print(adja[i][j]+" "); // si pas de mine on affiche le nombre de mines adjacentes à la case
                }
                if(j==mines[i].length-2){
                    System.out.println(); // retour à la ligne une fois qu'on a atteint la dernière valeur de tableau
                }
            }
        }
    }

    public boolean jeuPerdu(){
        for (int i = 0; i <etats.length ; i++) {
            for (int j = 0; j < etats[i].length; j++) {
                if(etats[i][j]==2 && mines[i][j]) return true; // si une bombe est présente sur la case et qu'elle a été révélée, on return true
            }
        }
        return false;
    }

    public boolean jeuGagne(){
        for (int i = 1; i <etats.length-1 ; i++) {
            for (int j = 1; j < etats[i].length-1; j++) {
                // si sur la case il n'y a pas de bombe, il faut que la case soit révélée pour que la fonction ne return pas false
                if(!mines[i][j]){
                    if(etats[i][j]==0 || etats[i][j]==1) return false;
                }
            }
        }
        return true;
    }
}
