package src.GUI.components.Labels;


import javax.swing.JLabel;
import src.ThemesManager;
import java.awt.Font;


/**
 * Composant graphique : label customisé 
 */
public class Credit extends JLabel
{
    /**
     * Constructeur
     * @param text
     * @param themeManager
     */
    public Credit( String text, ThemesManager themeManager )
    {
        this.setText(text);
        this.setFont( 
            new Font(
                themeManager.getThemeData("creditFontFamilly"),
                themeManager.getFontWeightFromThemeData( "creditFontWeight" ),
                themeManager.getFontSizeFromThemeData( "creditFontSize" )
            )
        );
        this.setForeground(
            themeManager.getColorFromThemeData("standartTextClr")
        );
        this.setHorizontalAlignment(CENTER);
    }
}
