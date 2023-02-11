package src.GUI.components.Charts;


import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Font;


/**
 * Composant graphique : Bar du graphique
 */
public class BarChart extends JButton
{
    private Color color;
    private Color borderColor;
    private final int width;
    private double value = 0.5;
    int edges = 4;
    int x[] = new int[ edges ]; 
    int y[] = new int[ edges ]; 
    Polygon polygon;


    /**
     * Constructeur
     * @param label
     * @param color
     * @param borderColor
     * @param width
     * @param height
     * @param font
     */
    public BarChart( String label, Color color, Color borderColor, int width, int height, Font font )
    {
        super( label );
        this.setFont( font );
        this.color = color;
        this.borderColor = borderColor;
        this.width = width;
        Dimension size = this.getPreferredSize();
        size.height = height;
        size.width = width;
        setPreferredSize( size );
        setContentAreaFilled( false );
    }


    /**
     * Change la valeur du graphique
     * @param newValue
     */
    public void setValue( double newValue )
    { 
        this.value = newValue; 
    }


    /**
     * Dessine le graphique
     */
    protected void paintComponent( Graphics g )
    {
        g.setColor( this.color );
        x[0] = 0; 
        y[0] = getSize().height;
        x[1] = 0; 
        y[1] = getSize().height - (int) (this.value * getSize().height);
        x[2] = getSize().width; 
        y[2] = getSize().height - (int) (this.value * getSize().height);
        x[3] = getSize().width; 
        y[3] = getSize().height;
        g.fillPolygon( x, y, edges );
        super.paintComponent( g );
    }


    /**
     * Dessine le graphique
     */
    protected void paintBorder( Graphics g )
    {
        g.setColor( this.borderColor );
        g.drawPolygon( x, y, edges );
    }

    
    /**
     * Mets à jour la valeur du graphique
     */
    public void updateValue( double v )
    {
        this.value = v;
        repaint();
    }

}
