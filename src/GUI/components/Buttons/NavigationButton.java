package src.GUI.components.Buttons;


import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JButton;
import src.ThemesManager;


/**
 * Composant graphique : bouton customisé de navigation
 */
public class NavigationButton extends JButton
{
    /**
     * Constructeur
     * @param text
     * @param _themesManager
     */
    public NavigationButton( String text, ThemesManager _themesManager )
    {
        this.setText(text);
        this.setBackground(
            _themesManager.getColorFromThemeData("complementaryClr")
        );
        this.setForeground(
            _themesManager.getColorFromThemeData("compelementaryTextColor")
        );
        this.setFocusPainted( false );
        this.setFont(
            new Font(
                _themesManager.getThemeData("startGameButtonFontFamily"),
                _themesManager.getFontWeightFromThemeData( "startGameButtonFontWeight" ),
                _themesManager.getFontSizeFromThemeData( "startGameButtonFontSize" )
            )
        );
        this.setPreferredSize(new Dimension( 200, 75 ) );
    }
}
