package src.Storage;


import src.Game.BoardTile;
import src.Game.Game;
import src.Game.Board;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Classe de gestion
 */
public class GameSaving 
{
    private final Boolean DEBUG;
    private final String cheminStockage;
    private static final char caseVide = '-';
    private static final char caseJoueur1 = '@';
    private int taillePlateau;


    /**
     * Constructeur
     * @param cheminStockage
     * @param taillePlateau
     * @param debug
     */
    public GameSaving( String cheminStockage, int taillePlateau, Boolean debug )
    {
        this.DEBUG = debug;
        if ( this.DEBUG ) System.out.println( "[CREATION GAME SAVING]" );
        this.cheminStockage = cheminStockage;
        this.taillePlateau = taillePlateau;
    }


    /**
     * Enregistre la partie en cours sour le nom {nomPartie}. Retourne une erreur si le nom est déjà pris
     * @param nomPartie
     * @param partie
     * @throws GameAlreadyExistsException
     */
    public void saveGame( String nomPartie, Game partie )
    throws GameAlreadyExistsException
    {
        if ( this.DEBUG ) System.out.println( "[GAME SAVING] > Sauvegarde partie en cours : (nom: " + nomPartie + ")" );
        if ( partieDejaExistente( nomPartie + ".txt" ) ) throw ( new GameAlreadyExistsException( nomPartie ) );
        String sortie = partie.getPlateau().toString() + Integer.toString( partie.getJoueur() ) + " " + Integer.toString( partie.getTours() ) + "\n";
        sortie += ":" + Integer.toString( partie.getJoueur1() ) + " " + Integer.toString( partie.getJoueur2() );
        try
        {
            FileWriter fw = new FileWriter( this.cheminStockage + "/" + nomPartie + ".txt" );
            BufferedWriter out = new BufferedWriter( fw );
            out.write( sortie );
            out.close();
            fw.close();
        }
        catch ( IOException e ) { e.printStackTrace(); }
    }   
    

    /**
     * Charge une partie sauvegardée. Retourne une erreur si le nom de la partie ciblée {nomParie} renvoit un fichier vide
     * @param nomPartie
     * @return
     * @throws GameDoesNotExistsException
     */
    public Game loadGame( String nomPartie )
    throws GameDoesNotExistsException
    {
        if ( this.DEBUG ) System.out.println( "[GAME SAVING] > Chargement partie enregistree : (nom: " + nomPartie + ")" );
        if ( !partieDejaExistente( nomPartie ) ) throw new GameDoesNotExistsException( nomPartie );
        Game game;
        try
        {
            FileReader fr = new FileReader( this.cheminStockage + "/" + nomPartie );
            BufferedReader br = new BufferedReader( fr );
            
            BoardTile[][] plateau = new BoardTile[this.taillePlateau][this.taillePlateau];
            int joueur = 0; int tours = 0; String toursString = ""; int row = 0;

            int idJ1 = 0;
            int idJ2 = 0;

            String line = "";
            while ( ( line = br.readLine() ) != null )
            {
                if ( line.charAt( 0 ) == ':' )
                {
                    int z = 1;
                    String temp = "";
                    while ( z < line.length() )
                    {
                        if ( line.charAt(z) == ' ' )
                        {
                            idJ1 = Integer.parseInt( temp );
                            temp = "";
                        }
                        else { temp += String.valueOf( line.charAt( z ) ); }
                        z++;
                    }
                    idJ2 = Integer.parseInt( temp );
                }
                else if ( Character.isDigit( line.charAt(0) ) )
                {
                    joueur = Integer.parseInt( String.valueOf( line.charAt(0) ) );
                    for ( int k = 2; k < line.length(); k++ ) { toursString += String.valueOf( line.charAt(k) ); }
                    tours = Integer.valueOf( toursString );
                }
                else
                {
                    for ( int k = 0; k < line.length(); k++ )
                    { plateau[row][k] = new BoardTile( ( line.charAt(k) == caseVide ) ? 0 : ( ( line.charAt(k) == caseJoueur1 ) ? 1 : 2 ), row, k ); }
                }
                row++;
            }
            game = new Game( tours, joueur, new Board( plateau ), idJ1, idJ2 );
        }
        catch ( Exception e ) { e.printStackTrace(); game = null; }
        return game;
    }


    /**
     * Détermine si un fichier de sauvegarde de partie existe sous le nom {nom}
     * @param nom
     * @return
     */
    public Boolean partieDejaExistente( String nom )
    {
        if ( this.DEBUG ) System.out.println( "[GAME SAVING] > Verifie que la partie existe : (nom: " + nom + ")" );
        String[] fichiers = listeDesParties();
        if ( fichiers == null || fichiers.length <= 0 ) return false;
        for ( int f = 0; f < fichiers.length; f++ )
        { if ( fichiers[f].equals( nom ) ) return true; }
        return false;
    }


    /**
     * Retourne la liste des parties sauvegardées
     * @return
     */
    public String[] listeDesParties()
    {
        if ( this.DEBUG ) System.out.println( "[GAME SAVING] > Retourne la liste des parties en enregistrees" );
        File lfichier = new File( this.cheminStockage );
        return lfichier.list();
    }
}