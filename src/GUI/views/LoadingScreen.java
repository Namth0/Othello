package src.GUI.views;


import javax.swing.*;
import src.LangManager;
import src.ThemesManager;
import src.GUI.components.Labels.Title;
import src.GUI.components.Buttons.NavigationButton;
import src.GUI.components.Buttons.StartGameButton;
import src.GUI.components.Labels.Credit;
import java.awt.Dimension;


/**
 * Ecran d'accueil
 */
public class LoadingScreen extends JPanel
{
     private final Boolean DEBUG;
     private StartGameButton startGame;
     private Title title;
     private Credit credits;
     private ThemesManager themesManager;
     private LangManager langManager;
     private NavigationButton openAccountsScreenButton;
     private int windowWidth;
     private int windowHeight;


     /**
      * Constructeur
      * @param themesManager
      * @param langManager
      * @param windowWidth
      * @param windowHeight
      * @param debug
      */
     public LoadingScreen(  ThemesManager themesManager, LangManager langManager, int windowWidth, int windowHeight, Boolean debug  )
     {
          this.DEBUG = debug;
          if ( this.DEBUG ) System.out.println( "[CREATION LOADING SCREEN]" );
          this.themesManager = themesManager;
          this.langManager = langManager;
          this.windowWidth = windowWidth;
          this.windowHeight = windowHeight;

          this.setBackground( themesManager.getColorFromThemeData( "backgroundClr" ) );
          this.setLayout( null );
     }


     /**
      * Création des composants de la page
      */
     public void initializeComponents()
     {
          if ( this.DEBUG ) System.out.println( "[LOADING SCREEN] > initialisation des composants" );
          Dimension size;

          this.title = new Title( langManager.getLangData( "othello" ), themesManager );
          this.credits = new Credit( langManager.getLangData( "credits" ), themesManager );
          this.startGame = new StartGameButton (langManager.getLangData( "play" ), themesManager );
          this.openAccountsScreenButton = new NavigationButton( this.langManager.getLangData("accounts" ), themesManager );

          this.add( this.title );
          this.add( this.credits );
          this.add( this.startGame );
          this.add( this.openAccountsScreenButton );

          size = this.title.getPreferredSize();
          this.title.setBounds(
               (this.windowWidth - size.width) / 2,
               (this.windowHeight - size.height) / 4,
               size.width, size.height
          );

          size = this.startGame.getPreferredSize();
          this.startGame.setBounds(
          (this.windowWidth - size.width) / 2,
          (this.windowHeight - size.height) / 2,
          size.width, size.height
          );

          size = credits.getPreferredSize();
          this.credits.setBounds(
               ( this.windowWidth - ( size.width + 50 ) ),
               ( this.windowHeight - ( 5 * size.height ) ),
               size.width,
               size.height
          );

          size = this.openAccountsScreenButton.getPreferredSize();
          this.openAccountsScreenButton.setBounds(
               10, 10, size.width, size.height
          );
     }


     /**
      * Retourne le bouton de redirection vers la page de jeu
      * @return
      */
     public StartGameButton getStartGameButton() { return this.startGame; }


     /**
      * Retourne le bouton de redirection vers la page des comptes
      * @return
      */
     public NavigationButton getNavigationButton() { return this.openAccountsScreenButton; }
     

     /**
      * Mets à jour le status d'affichage des composants 
      * @param status
      */
     public void updateComponents( Boolean status )
     {    
          if ( this.DEBUG ) System.out.println( "[LOADING SCREEN] > MAJ des composants : (status: " + status + ")" );
          this.startGame.setVisible( status );
          this.title.setVisible( status );
          this.credits.setVisible( status );
          this.openAccountsScreenButton.setVisible( status );
     }
}