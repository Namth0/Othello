package src.GUI.components.Labels;


import src.ThemesManager;
import javax.swing.JLabel;
import java.awt.Font;


/**
 * Composant graphique : label customisé
 */
public class Title extends JLabel 
{
    /**
     * Constructeur
     * @param text
     * @param themeManager
     */
    public Title( String text, ThemesManager themeManager )
    {
        this.setText(text);
        this.setFont( 
            new Font(
                themeManager.getThemeData("titleFontFamily"),
                themeManager.getFontWeightFromThemeData( "titleFontWeight" ),
                themeManager.getFontSizeFromThemeData( "titleFontSize" )
            )
        );
        this.setForeground(
            themeManager.getColorFromThemeData("standartTextClr")
        );
        this.setHorizontalAlignment(CENTER);
    }
}
