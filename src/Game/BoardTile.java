package src.Game;


/**
 * Représentation backend d'une case de l'othellier
 */
public class BoardTile 
{
    private int playerOnTile = 0;
    private int row;
    private int column;

    
    /**
     * Constructeur de la classe représentatrice d'une case du plateau. Aucun joueur ne possède la case
     * @param row
     * @param column
     */
    public BoardTile( int row, int column )
    {
        this.row = row;
        this.column = column;
    }


    /**
     * Constructeur de la classe représentatrice d'une case du plateau.
     * @param playerOnTile
     * @param row
     * @param column
     */
    public BoardTile( int playerOnTile, int row, int column )
    {
        this.playerOnTile = playerOnTile;
        this.row = row;
        this.column = column;
    }


    /**
     * Retourne les coordonnées de la case
     * @return
     */
    public int[] getCoordonates() { return new int[]{ this.row, this.column }; }

    
    /**
     * Indique si la case est libre
     * @return
     */
    public boolean isTileOwned() { return this.playerOnTile != 0; }

    
    /**
     * Retourne le propriétaire de la case
     * @return
     */
    public int getOwner() { return this.playerOnTile; }


    /**
     * Change le propriétaire de la case
     * @param newOwner
     */
    public void setOwner( int newOwner ) { this.playerOnTile = newOwner; }


    /**
     * Représentation au format String de la classe
     * @return
     */
    @Override
    public String toString()
    {
        return "(" + Integer.toString( this.row ) + ", " + Integer.toString( this.column ) + ") > JOUEUR " + ( ( this.playerOnTile == 1 ) ? "NOIR" : ( this.playerOnTile == 2 ) ? "BLANC" : "VIDE" );
    }
}