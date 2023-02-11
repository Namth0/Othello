package src.GUI.components.Board;


import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;


/**
 * Composant graphique représentant la bordure du plateau de l'othellier
 */
public class Border extends JButton 
{

    /**
     * Constructeur
     * @param size
     * @param label
     * @param f
     * @param bg
     * @param fg
     */
    public Border( int size, String label, Font f, Color bg, Color fg )
    {
        super( label );
        this.setFont( f );
        this.setForeground( fg );
        this.setBackground( bg );
        this.setEnabled( false );
        this.setPreferredSize( new Dimension( size, size ) );
    }
}
