package src.GUI;


import src.*;
import src.GUI.views.*;
import java.awt.*;
import src.Game.GameLogic;
import src.Storage.DatabaseManager;
import java.awt.event.*;
import javax.swing.JFrame;


/**
 * Classe de gestion de l'interface
 */
public class GUIController extends JFrame
{
    private ConfigManager _configManager;
    private final Boolean DEBUG;
    private LoadingScreen _loadingScreen;
    private AccountsView _accountsViewScreen;
    private GameView _gameViewScreen;
    private boolean isLoadingScreenActive = false;
    private boolean isAccountsViewActive = false;
    private boolean isGameViewActive = false;
    private int windowHeight;
    private int windowWidth;



    /**
     * Constructeur
     * @param configManager
     * @param langManager
     * @param themesManager
     * @param databaseManager
     */
    public GUIController( ConfigManager configManager, LangManager langManager, ThemesManager themesManager, DatabaseManager databaseManager )
    {
        setLayout( null );
        this._configManager = configManager;
        this.DEBUG = Boolean.parseBoolean( this._configManager.getConfigData( "debug" ) );
        if ( this.DEBUG ) System.out.println( "[CREATION GUI CONTROLLER]" );
        int width = 1; 
        int height = -1;
        if ( !Boolean.parseBoolean( this._configManager.getConfigData("fullScreen") ) )
        {
            width = Integer.parseInt(this._configManager.getConfigData("windowWidth"));
            height = Integer.parseInt(this._configManager.getConfigData("windowHeight"));
        }
        else { width = -1; height = -1; }
        if ( width == -1 && height == -1 ) 
        { 
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);  
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            width = size.width;
            height = size.height;
        }
        else { this.setSize( width, height ); }

        this.windowWidth = width;
        this.windowHeight = height;
        this._accountsViewScreen = new AccountsView( themesManager, langManager, width, height, databaseManager, this.DEBUG );
        this._loadingScreen = new LoadingScreen( themesManager, langManager, width, height, this.DEBUG );
        this._gameViewScreen = new GameView( 
            themesManager, langManager, width, height, 
            Integer.valueOf( this._configManager.getConfigData( "tailleTableau") ),
            databaseManager,
            new GameLogic( 
                Integer.valueOf( this._configManager.getConfigData( "tailleTableau") ) + (Integer.valueOf( this._configManager.getConfigData( "tailleTableau") ) % 2), 
                Integer.valueOf( this._configManager.getConfigData( "minMaxCeil" ) ),
                Boolean.valueOf( this._configManager.getConfigData("debug") ),
                Integer.valueOf( this._configManager.getConfigData("iaDelay" ) )   
            ),
            Boolean.valueOf( this._configManager.getConfigData("debug") ),
            this._configManager.getConfigData( "pathToGameSaved" )
        );

        this.setBackground( themesManager.getColorFromThemeData("backgroundClr") );
        this._accountsViewScreen.initializeComponents();
        this._gameViewScreen.initializeComponents();
        this._loadingScreen.initializeComponents();
        this.showLoadingScreen();
        this.setNavigationEvents();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }


    /**
     * Associe aux boutons de navigations leurs évènements respectifs
     */
    private void setNavigationEvents()
    {
        if ( this.DEBUG ) System.out.println( "[GUI CONTROLLER] > Mise en place des evenements de navigations" );
        this._accountsViewScreen.getNavigationButton().addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    eraseCurrentScreen();
                    showLoadingScreen();
                }
            }
        );

        this._loadingScreen.getNavigationButton().addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    eraseCurrentScreen();
                    showAccountsViewScreen();
                }
            }
        );

        this._loadingScreen.getStartGameButton().addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    eraseCurrentScreen();
                    showGameView();
                }
            }
        );

        this._gameViewScreen.getNavigationButton().addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e )
                {
                    retourMenuDepuisPartie();
                }
            }
        );
    }


    /**
     * Ouvre la page d'acceuil
     */
    private void retourMenuDepuisPartie()
    {
        if ( this.DEBUG ) System.out.println( "[GUI CONTROLLER] > Retour au menu" );
        this._gameViewScreen.quitterPartie();
        eraseCurrentScreen();
        showLoadingScreen();
    }


    /**
     * Chache les composants affichés sur la fenêtre
     */
    private void eraseCurrentScreen()
    {
        if ( this.DEBUG ) System.out.println( "[GUI CONTROLLER] > Suppression de la page affichee" );
        if ( this.isLoadingScreenActive ) 
        {
            this.isLoadingScreenActive = false;
            this.remove( this._loadingScreen );
        }
        
        if ( this.isAccountsViewActive )
        {
            this.isAccountsViewActive = false;
            this.remove( this._accountsViewScreen );
        }

        if ( this.isGameViewActive )
        { 
            this.isGameViewActive = false;
            this.remove( this._gameViewScreen );
        }

        this._accountsViewScreen.updateComponents( false );
        this._loadingScreen.updateComponents( false );
        this._gameViewScreen.updateComponents( false );

        this.setVisible( true );
    } 


    /**
     * Affiche l'écran d'accueil
     */
    private void showLoadingScreen()
    {   
        if ( this.DEBUG ) System.out.println( "[GUI CONTROLLER] > Affiche l'ecran d'accueil" );
        this.isLoadingScreenActive = true;
        this.add( _loadingScreen );
        this._loadingScreen.updateComponents( true );
        this._loadingScreen.setBounds( 0, 0, this.windowWidth, this.windowHeight  );
    }


    /**
     * Affiche la page des comptes
     */
    private void showAccountsViewScreen()
    {
        if ( this.DEBUG ) System.out.println( "[GUI CONTROLLER] > Affiche l'ecran des comptes" );
        this.isAccountsViewActive = true;
        this.add( this._accountsViewScreen );
        this._accountsViewScreen.updateComponents( true );
        this._accountsViewScreen.setBounds( 0, 0, this.windowWidth, this.windowHeight  );
    }


    /**
     * Affiche la page de jeu
     */
    private void showGameView()
    {
        if ( this.DEBUG ) System.out.println( "[GUI CONTROLLER] > Affiche l'ecran de la partie" );
        this.isGameViewActive = true;
        this.add( this._gameViewScreen );
        this._gameViewScreen.updateComponents( true );
        this._gameViewScreen.setBounds( 0, 0, this.windowWidth, this.windowHeight );
    }
}