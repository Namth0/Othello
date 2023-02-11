package src.GUI.components.Buttons;


import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.Font;
import src.ThemesManager;


/**
 * Composant graphique : Bouton customisé pour lancer une partie
 */
public class StartGameButton extends JButton
{
    /**
     * Constructeur
     * @param text
     * @param _themesManager
     */
    public StartGameButton( String text, ThemesManager _themesManager )
    {
        this.setText(text);
        this.setBackground(
            _themesManager.getColorFromThemeData("complementaryClr")
        );
        this.setForeground(
            _themesManager.getColorFromThemeData("compelementaryTextColor")
        );

        this.setFont(
            new Font(
                _themesManager.getThemeData("startGameButtonFontFamily"),
                _themesManager.getFontWeightFromThemeData( "startGameButtonFontWeight" ),
                _themesManager.getFontSizeFromThemeData( "startGameButtonFontSize" )
            )
        );
        this.setPreferredSize( new Dimension(500, 100 ) );
    }
}
