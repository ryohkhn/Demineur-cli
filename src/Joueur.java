import java.util.Scanner;

public class Joueur {
    public String nom;
    private Scanner scanReponse;

    public Joueur(){
        this.nom="Anonyme";
        scanReponse=new Scanner(System.in);
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void finish(){
        scanReponse.close();
    }

    public boolean veutJouer(){
        System.out.println("Voulez-vous jouer (oui/non) ?");
        String rep=scanReponse.next();
        if(rep.equals("oui")){
            return true;
        }
        if(rep.equals("non")){
            return false;
        }
        System.out.println("Vous devez répondre oui ou non.");
        return veutJouer();
    }

    public String demanderNom(){
        System.out.println("Quel est votre pseudo ?");
        String rep = scanReponse.next();
        this.setNom(rep); // on change le nom du joueur en plus de le return
        return rep;
    }

    public int[] demanderDimensions(){
        int[] res=new int[2];
        System.out.println("Quelle valeur de hauteur souhaitez-vous pour votre démineur ? (minimum 2)");
        int rep = scanReponse.nextInt();
        if(rep<2) res[0]=2;
        else res[0]=rep;
        System.out.println("Quelle valeur de largeur souhaitez-vous pour votre démineur ? (minimum 2)");
        rep=scanReponse.nextInt();
        if(rep<2) res[1]=2;
        else res[1]=rep;
        return res;
    }

    public int demanderNbMines(){
        System.out.println("Combien de mines souhaitez-vous ?");
        int rep=scanReponse.nextInt();
        if(rep<0){
            System.out.println("Donnez un nombre supérieur à 0.");
            return demanderNbMines(); // si jamais le nombre est négatif on return la fonction pour refaire la demande
        }
        return rep;
    }

    public char demanderAction(){
        System.out.println("Voulez-vous révéler une case (r) ou placer/enlever un drapeau (d) ?");
        String rep=scanReponse.next();
        if(!rep.equals("r") && !rep.equals("d")){
            System.out.println("Vous devez répondre par r pour révéler une case ou par d pour placer un drapeau");
            return demanderAction(); // si le string envoyé n'est pas r ou d on return la fonction pour refaire la demande
        }
        return rep.charAt(0);
    }

    public int[] demanderCoordonnes(){
        String alphabet="abcdefghijklmnopqrstuvwxyz"; // alphabet pour récupérer l'index correspondant
        int[] res=new int[2];
        System.out.println("En quelle hauteur voulez-vous faire votre action (lettre) ?");
        String rep=scanReponse.next().toLowerCase(); // on utilise la fonction String.toLowerCase() pour ne pas avoir de problème avec les majuscules
        // si la fonction indexOf ne trouve pas le caractère envoyé via le scanner dans le string alphabet il return -1, si c'est le cas on relance la fonction
        if((alphabet.indexOf(rep.charAt(0)))<0){
            System.out.println("Vous devez entrer une lettre présente dans l'alphabet pour le premier caractère.");
            return demanderCoordonnes();
        }
        res[0]=alphabet.indexOf(rep.charAt(0))+1;
        System.out.println("En quelle largeur voulez-vous faire votre action (chiffre) ?");
        int rep2=scanReponse.nextInt();
        if(rep2<0){
            System.out.println("Vous devez entrer une valeur supérieur à 0.");
            return demanderCoordonnes(); // si la coordonnée de largeur est négative on relance la fonction
        }
        res[1]=rep2;
        return res;

    }
}
