package src.GUI.components.Buttons;


import javax.swing.JButton;
import src.ThemesManager;
import java.awt.Font;
import java.awt.Dimension;


/**
 * Composant graphqiue : Bouton customisé de sélection d'un joueur
 */
public class SelectPlayerButton extends JButton
{
    /**
     * Constructeur
     * @param label
     * @param themeManager
     */
    public SelectPlayerButton(  String label, ThemesManager themeManager )
    {
        super( label );
        this.setFont( 
            new Font(
                themeManager.getThemeData("playerInfoFontFamily"),
                themeManager.getFontWeightFromThemeData( "playerInfoFontWeight" ),
                themeManager.getFontSizeFromThemeData( "playerInfoFontSize" )
            )
        );
        this.setForeground(
            themeManager.getColorFromThemeData("standartTextClr")
        );
        this.setBackground( themeManager.getColorFromThemeData( "complementaryClr" ) );
        this.setPreferredSize( 
            new Dimension( 
                themeManager.getFontSizeFromThemeData( "playerInfoFontSize" )* 10 ,
                10 + themeManager.getFontSizeFromThemeData( "playerInfoFontSize" ) 
        ));
    }
}
