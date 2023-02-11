package src.GUI.components.Labels;


import src.ThemesManager;
import javax.swing.JLabel;
import java.awt.Font;


/**
 * Composants graphiques : label customisé
 */
public class PlayerInfoLabel extends JLabel  
{
    /**
     * Constructeur
     * @param text
     * @param themeManager
     */
    public PlayerInfoLabel( String text, ThemesManager themeManager )
    {
        this.setText(text);
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
        this.setHorizontalAlignment( LEFT );
    }
}
