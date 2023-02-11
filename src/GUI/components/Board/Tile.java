package src.GUI.components.Board;


import javax.swing.JButton;
import src.ThemesManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;


/**
 * Composant graphique représentant une case du plateau de l'othellier
 */
public class Tile extends JButton 
{
    private int playerOnTile = 0;
    private Color bgColor;
    private Color possibleMoveBGColor;
    private Color playerOneColor;
    private Color playerTwoColor;
    private Boolean estCoupPossible = false; 
    private int size;
    int edges = 6;
    Polygon polygon;


    /**
     * Constructeur
     * @param themesManager
     * @param size
     */
    public Tile( ThemesManager themesManager, int size )
    {
        this.size = size;
        this.bgColor = themesManager.getColorFromThemeData( "boardTileColor" );
        this.possibleMoveBGColor = themesManager.getColorFromThemeData( "possibleMoveColor" );
        this.playerOneColor = themesManager.getColorFromThemeData( "player1Clr" );
        this.playerTwoColor = themesManager.getColorFromThemeData( "player2Clr" );
        this.setBackground( this.bgColor );
        this.setPreferredSize( new Dimension( size, size ) );
    }
    

    /**
     * Affiche le jeton du joueur si la case est possédée
     * Affiche un jeton vide si la case représente une case jouable
     * N'affiche rien sinon
     */
    protected void paintComponent( Graphics g )
    {
        if ( this.playerOnTile == 1 || this.playerOnTile == 2 )
        {
            g.setColor( ( this.playerOnTile == 1 ) ? this.playerOneColor : this.playerTwoColor );
            super.paintComponent( g );
            g.fillOval( 5, 5, this.size - 10, this.size - 10 );
        }
        else
        {
            g.setColor( ( this.estCoupPossible ) ? this.possibleMoveBGColor :  this.bgColor );
            super.paintComponent( g );
            g.fillOval( 5, 5, this.size - 10, this.size - 10 );
        }
        
    }


    /**
     * Affiche le jeton du joueur si la case est possédée
     * Affiche un jeton vide si la case représente une case jouable
     * N'affiche rien sinon
     */
    protected void paintBorder( Graphics g )
    {
        if ( this.playerOnTile == 1 || this.playerOnTile == 2 )
        {
            g.setColor( ( this.playerOnTile == 1 ) ? this.playerOneColor : this.playerTwoColor );
            g.drawOval( 5, 5, this.size - 10, this.size - 10 );
        }
        else
        {
            g.setColor( ( this.estCoupPossible ) ? this.possibleMoveBGColor :  this.bgColor );
            g.drawOval( 0,0,0,0 );
        }
    }


    /**
     * Change le propriétaire de la table
     * @param player
     */
    public void setPlayerOnTile( int player )
    {
        this.playerOnTile = player;
        repaint();
    }


    /**
     * Change le status de jouabilité de la case
     * @param status
     */
    public void setAsPossibleMove( Boolean status ) { this.estCoupPossible = status; repaint(); }
}