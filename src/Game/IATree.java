package src.Game;


/**
 * Classe représentant l'arbre d'évaluation de l'algorithme MINMAX
 */
public class IATree 
{
    private IANode root;
    private Board initialBoard;
    private final String typeIA;


    /**
     * Constructeur
     * @param root
     * @param initialBoard
     * @param typeIA
     */
    public IATree ( IANode root, Board initialBoard, String typeIA )
    {
        this.root = root;
        this.initialBoard = new Board( initialBoard.getTiles() );
        this.typeIA = typeIA;
    }


    /**
     * Change le plateau initial
     * @param newInitialBoard
     */
    public void setInitialBoard( Board newInitialBoard )
    { 
        this.initialBoard = ( newInitialBoard == null ) ? this.initialBoard :  new Board( newInitialBoard.getTiles() );
    }


    /**
     * Retourne la représentation au format {String} de la classe
     */
    @Override
    public String toString()
    {
        String result = this.typeIA + "\n";
        result += "PLATEAU INITIAL:\n";
        result += this.initialBoard.toString() + "\n";
        IANode temp = this.root;
        result += temp.toString( 0 );
        return result;
    }
}