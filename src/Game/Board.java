package src.Game;


/**
 * Représentation backend du plateau
 */
public class Board 
{
    private BoardTile[][] tiles;
    private static final char caseVide = '-';
    private static final char caseJoueur1 = '@';
    private static final char caseJoueur2 = 'O';


    /**
     * Constructeur de la classe de représentation du plateau. Génère le plateau de début de partie.
     * @param tailleTableau
     */
    public Board( int tailleTableau )
    {
        this.tiles = new BoardTile[tailleTableau][tailleTableau];
        for ( int i = 0; i < tailleTableau; i++ )
        {
            for ( int j = 0; j < tailleTableau; j++ )
            {
                this.tiles[i][j] = new BoardTile( i, j );
            }
        }
        this.tiles[(int) tailleTableau / 2 - 1][(int) tailleTableau / 2 - 1].setOwner( 1 );
        this.tiles[(int) tailleTableau / 2 - 1][(int) tailleTableau / 2].setOwner( 2 );
        this.tiles[(int) tailleTableau / 2][(int) tailleTableau / 2 - 1].setOwner( 2 );
        this.tiles[(int) tailleTableau / 2][(int) tailleTableau / 2].setOwner( 1 );
    }

    
    /**
     * Constructeur de la classe de représentation du plateau. Duplique le plateau placé en paramètres
     * @param board
     */
    public Board( BoardTile[][] board )
    { 
        this.tiles = new BoardTile[board.length][board.length];
        for ( int row = 0; row < board.length; row++ )
        {
            for ( int col = 0; col < board[row].length; col++ )
            {
                this.tiles[row][col] = new BoardTile(
                    board[row][col].getOwner(),
                    board[row][col].getCoordonates()[0],
                    board[row][col].getCoordonates()[1]
                );
            }
        }
    }
    

    /**
     * Retourne la case aux coordonnées ({row}, {col})
     * @param row
     * @param col
     * @return
     */
    public BoardTile getTile( int row, int col )
    { return this.tiles[row][col]; }


    /**
     * Retourne le plateau
     * @return
     */
    public BoardTile[][] getTiles() { return this.tiles; }


    /**
     * Retourne la représentation au format {String} de la classe
     */
    @Override
    public String toString()
    {
        String result = "";
        for ( int row = 0; row < this.tiles.length; row++ )
        {
            for ( int col = 0; col < this.tiles[row].length; col++ )
            {
                switch ( this.tiles[row][col].getOwner() )
                {
                    case 0:
                        result += caseVide;
                        break;
                    case 1:
                        result += caseJoueur1;
                        break;
                    default:
                        result += caseJoueur2;
                        break;
                }
            }
            result += "\n";
        }
        return result;
    }
}