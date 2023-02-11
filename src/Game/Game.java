package src.Game;


/**
 * Représentation backend d'une partie
 */
public class Game
{
    private int tours;
    private int joueur;
    private Board plateau;
    private final int idJoueur1;
    private final int idJoueur2;


    /**
     * Constructeur de la classe représentatrice d'une partie
     * @param tours
     * @param joueur
     * @param plateau
     * @param idJoueur1
     * @param idJoueur2
     */
    public Game( int tours, int joueur, Board plateau, int idJoueur1, int idJoueur2 )
    {
        this.tours = tours;
        this.joueur = joueur;
        this.plateau = plateau;
        this.idJoueur1 = idJoueur1;
        this.idJoueur2 = idJoueur2;
    }


    /**
     * Retourne le plateau
     * @return
     */
    public Board getPlateau() { return this.plateau; }

    
    /**
     * Retourne le joueur
     * @return
     */
    public int getJoueur() { return this.joueur; }
    
    
    /**
     * Retourne le nombre de tours
     * @return
     */
    public int getTours() { return this.tours; }


    /**
     * Passe au tour suivant et mets à jour le joueur
     */
    public void tourSuivant()
    {
        this.joueur = ( joueur == 1 ) ? 2 : 1;
        this.tours++;
    }

 
    /**
     * Place un jeton sur le plateau et mets à jour le nombre de tours et le joueur suivant
     * @param row
     * @param col
     */
    public void placeJetons( int row, int col ) 
    { 
        this.plateau.getTile( row, col ).setOwner( this.joueur ); 
        this.tourSuivant();
    }

    
    /**
     * Change de propriétaire un jeton en prise
     * @param row
     * @param col
     */
    public void placeJetonEnPrise( int row, int col )
    {
        this.plateau.getTile( row, col ).setOwner( this.joueur ); 
    }


    /**
     * Retourne l'ID du joueur 1
     * @return
     */
    public int getJoueur1() { return this.idJoueur1; }
    

    /**
     * Retourne l'ID du joueur 2
     * @return
     */
    public int getJoueur2() { return this.idJoueur2; }
}