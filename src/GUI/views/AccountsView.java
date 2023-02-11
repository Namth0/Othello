package src.GUI.views;


import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JPanel;
import javax.swing.JTextField;
import src.LangManager;
import src.ThemesManager;
import src.GUI.components.Charts.BarChart;
import src.GUI.components.Labels.PlayerInfoLabel;
import src.Storage.DatabaseManager;
import src.GUI.components.Buttons.NavigationButton;
import src.GUI.Models.PlayerInfo;


/**
 * Page de consultation des données des comptes
 */
public class AccountsView extends JPanel
{
    private final Boolean DEBUG;
    private ThemesManager _themesManager;
    private LangManager _langManager;
    private static final String emptyData = " : ...";
    private PlayerInfoLabel playerUsername;
    private PlayerInfoLabel playerGameAsPlayer1;
    private PlayerInfoLabel playerGameAsPlayer2;
    private PlayerInfoLabel playerVictories;
    private PlayerInfoLabel playerDefeates;
    private PlayerInfoLabel playerDraws;
    private BarChart victoryRate;
    private BarChart defeateRate;
    private BarChart drawRate;
    private JTextField usernameInput;
    private JButton searchUsernameButton;
    private NavigationButton returnToHomeScreenButton;
    private final int windowWidth;
    private final int windowHeight;
    private DatabaseManager databaseManager;


    /**
     * Constructeur
     * @param _themesManager
     * @param _langManager
     * @param windowHeight
     * @param windowWidth
     * @param databaseManager
     * @param debug
     */
    public AccountsView( ThemesManager _themesManager, LangManager _langManager, int windowHeight, int windowWidth, DatabaseManager databaseManager, Boolean debug )
    {
        this.DEBUG = debug;
        if ( this.DEBUG ) System.out.println( "[CREATION ACCOUTNS VIEW]" );
        this._themesManager = _themesManager;
        this._langManager = _langManager;
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        this.databaseManager = databaseManager;
        this.setBackground( _themesManager.getColorFromThemeData( "backgroundClr" ) );
        this.setLayout( null );
        this.setSize( new Dimension( (int) windowWidth / 2, (int) windowHeight / 2) );
    }


    /**
     * Création des composants
     */
    public void initializeComponents()
    {
        if ( this.DEBUG ) System.out.println( "[ACCOUTNS VIEW] > Initialisation des composants" );
        Dimension size;
        this.createLabels();
        this.createChart();
        this.drawPlayerSerchBox();
        this.returnToHomeScreenButton = new NavigationButton( this._langManager.getLangData("menu"), _themesManager );
        size = this.returnToHomeScreenButton.getPreferredSize();
        this.add( this.returnToHomeScreenButton );
        this.returnToHomeScreenButton.setBounds(
            10, 10, size.width, size.height
        );
    }


    /**
     * Création des labels d'informations
     */
    private void createLabels()
    {
        if ( this.DEBUG ) System.out.println( "[ACCOUTNS VIEW] > Placements des informations" );
        Dimension size;

        this.playerUsername         = new PlayerInfoLabel( this._langManager.getLangData("username" ) + emptyData, this._themesManager );
        this.playerGameAsPlayer1    = new PlayerInfoLabel( this._langManager.getLangData( "gameAsPlayerOne" ) + emptyData, this._themesManager );
        this.playerGameAsPlayer2    = new PlayerInfoLabel( this._langManager.getLangData( "gameAsPlayerTwo" ) + emptyData, this._themesManager );
        this.playerVictories        = new PlayerInfoLabel( this._langManager.getLangData( "amountVictories" ) + emptyData, this._themesManager );
        this.playerDefeates         = new PlayerInfoLabel( this._langManager.getLangData( "amountDefeates" ) + emptyData, this._themesManager );
        this.playerDraws            = new PlayerInfoLabel( this._langManager.getLangData( "amountDraws" ) + emptyData, this._themesManager );
        this.add( this.playerUsername );
        this.add( this.playerGameAsPlayer1 );
        this.add( this.playerGameAsPlayer2 );
        this.add( this.playerVictories );
        this.add( this.playerDefeates );
        this.add( this.playerDraws );
        PlayerInfoLabel[] labels = new PlayerInfoLabel[] 
        { this.playerUsername, this.playerGameAsPlayer1, this.playerGameAsPlayer2, 
          this.playerVictories, this.playerDefeates, this.playerDraws };
        int labelHeight = this.playerUsername.getPreferredSize().height;
        int maxLabelWidth = Collections.max( 
            Arrays.asList( 
                this.playerUsername.getPreferredSize().width, this.playerGameAsPlayer1.getPreferredSize().width,
                this.playerGameAsPlayer2.getPreferredSize().width, this.playerVictories.getPreferredSize().width,
                this.playerDefeates.getPreferredSize().width, this.playerDraws.getPreferredSize().width
            ) 
        );
        for ( int i = 0; i < 6; i++ )
        {
            size = labels[i].getPreferredSize();
            labels[i].setBounds( 
                (int) ( ( this.windowWidth / 2 ) - maxLabelWidth + 150 ), 
                (int) ( ( ( this.windowHeight / 4 ) - labelHeight ) / 4 + i * labelHeight  + 50) , 
                700, size.height
            );
        }
    }


    /**
     * Création du graphique
     */
    private void createChart()
    {
        if ( this.DEBUG ) System.out.println( "[ACCOUTNS VIEW] > Affichage du graphique" );
        Dimension size;

        this.victoryRate = new BarChart(
            this._langManager.getLangData( "victories" ),
            this._themesManager.getColorFromThemeData( "victoryRateChartBackground" ),
            this._themesManager.getColorFromThemeData( "victoryRateChartOutline"),
            (int) 300,
            (int) 300,
            new Font(
                this._themesManager.getThemeData("chartFontFamily"),
                this._themesManager.getFontWeightFromThemeData( "chartFontWeight" ),
                this._themesManager.getFontSizeFromThemeData( "chartFontSize" )
            )
        );
        this.defeateRate = new BarChart(
            this._langManager.getLangData( "defeates" ),
            this._themesManager.getColorFromThemeData( "defeateRateChartBackground" ),
            this._themesManager.getColorFromThemeData( "defeateRateChartOutline"),
            (int) 300,
            (int) 300,
            new Font(
                this._themesManager.getThemeData("chartFontFamily"),
                this._themesManager.getFontWeightFromThemeData( "chartFontWeight" ),
                this._themesManager.getFontSizeFromThemeData( "chartFontSize" )
            )
        );
        this.drawRate = new BarChart(
            this._langManager.getLangData( "draws" ),
            this._themesManager.getColorFromThemeData( "drawRateChartBackground" ),
            this._themesManager.getColorFromThemeData( "drawRateChartOutline"),
            (int) 300,            
            (int) 300,
            new Font(
                this._themesManager.getThemeData("chartFontFamily"),
                this._themesManager.getFontWeightFromThemeData( "chartFontWeight" ),
                this._themesManager.getFontSizeFromThemeData( "chartFontSize" )
            )
        );
        this.add( this.victoryRate );
        this.add( this.defeateRate );
        this.add( this.drawRate );
        BarChart[] bars = new BarChart[] { this.victoryRate, this.defeateRate, this.drawRate };
        for ( int i = 0; i < bars.length; i++ )
        {
            size = bars[i].getPreferredSize();
            bars[i].setBounds(
                (int) this.windowWidth + i * 300 - 100,
                300, size.width, size.height
            );
            bars[i].setValue( (i + 1) * 0.3 );
        }
    }

    
    /**
     * Création du formulaire
     */
    private void drawPlayerSerchBox()
    {
        if ( this.DEBUG ) System.out.println( "[ACCOUTNS VIEW] > Affichage du formulaire" );
        this.usernameInput = new JTextField();
        this.usernameInput.setFont(
            new Font(
                this._themesManager.getThemeData("searchUsernameFontFamily"),
                this._themesManager.getFontWeightFromThemeData( "searchUsernameFontWeight" ),
                this._themesManager.getFontSizeFromThemeData( "searchUsernameFontSize" )
            )
        );
        this.searchUsernameButton = new JButton(
            this._langManager.getLangData( "search" )
        );
        this.add( this.usernameInput );
        this.add( this.searchUsernameButton );

        this.usernameInput.setPreferredSize(
            new Dimension( 300,  60      
            )
        );
        this.searchUsernameButton.setPreferredSize(
            new Dimension( 300, 60 )
        );
        this.usernameInput.setBounds(
            (int) ( this.windowWidth / 2 ),
            (int) ( this.windowHeight / 3 ) + 150,
            300, 60
        );


        this.searchUsernameButton.setBounds(
            (int) ( this.windowWidth / 2 ) + 350,
            (int) ( this.windowHeight / 3 ) + 150,
            300, 60
        );
        this.searchUsernameButton.addActionListener( e -> {
            String usernameSearched = this.usernameInput.getText();
            ResultSet playerExists = this.databaseManager.searchPlayerByName( usernameSearched );
            int size;
            try { size = ( playerExists.getInt( 1 ) == 0 ) ? 0 : 1; } catch ( Exception er ) { size = 0; }
            if ( size > 0 )
            {
                int id = 0;
                PlayerInfo pi;
                try { id = playerExists.getInt( 1 ); } catch ( SQLException se ) { return; }
                try { pi = this.databaseManager.getPlayerData( id, usernameSearched ); } catch ( Exception se ) { return; }
                setPlayerUsername( pi.getUsername() );
                setPlayerGameAsPlayer1( pi.getGameAsPlayerOne() );
                setPlayerGameAsPlayer2( pi.getGameAsPlayerTwo() );
                setPlayerVictories( pi.getVictories() );
                setPlayerDefeates( pi.getDefeates() );
                setPlayerDraws( pi.getDraws() );
                this.victoryRate.setValue( pi.getVictoryRatio() );
                this.defeateRate.setValue( pi.getDefeateRatio() );
                this.drawRate.setValue( pi.getDrawRatio() );
            }
            else
            {
                PlayerInfo pi =  new PlayerInfo(
                    "?", 1, 1, 1, 1, 1
                );
                setPlayerUsername( pi.getUsername() );
                setPlayerGameAsPlayer1( pi.getGameAsPlayerOne() );
                setPlayerGameAsPlayer2( pi.getGameAsPlayerTwo() );
                setPlayerVictories( pi.getVictories() );
                setPlayerDefeates( pi.getDefeates() );
                setPlayerDraws( pi.getDraws() );
                this.victoryRate.setValue( pi.getVictoryRatio() );
                this.defeateRate.setValue( pi.getDefeateRatio() );
                this.drawRate.setValue( pi.getDrawRatio() );
            } 
        });
    }


    /**
     * Change le pseudo du joueur étudié
     * @param username
     */
    public void setPlayerUsername( String username )
    { this.playerUsername.setText( this._langManager.getLangData("username" ) + " : " + username ); }


    /**
     * Change le nombre de partie faites en tant que joueur 1
     * @param amount
     */
    public void setPlayerGameAsPlayer1( int amount )
    { this.playerGameAsPlayer1.setText( this._langManager.getLangData( "gameAsPlayerOne" ) + " : " + String.valueOf( amount ) ); }


    /**
     * Change le nombre de partie faites en tant que joueur 2
     * @param amount
     */
    public void setPlayerGameAsPlayer2( int amount )
    { this.playerGameAsPlayer2.setText( this._langManager.getLangData( "gameAsPlayerTwo" ) + " : " + String.valueOf( amount ) ); }


    /**
     * Change le nombre de victoires
     * @param amount
     */
    public void setPlayerVictories( int amount )
    { this.playerVictories.setText( this._langManager.getLangData( "amountVictories" ) + " : " + String.valueOf( amount ) ); }


    /**
     * Change le nombre de défaites
     * @param amount
     */
    public void setPlayerDefeates( int amount )
    { this.playerDefeates.setText( this._langManager.getLangData( "amountDefeates" ) + " : " + String.valueOf( amount ) ); }


    /**
     * Modifie la valeur du nombre de parties nulles
     * @param amount
     */
    public void setPlayerDraws( int amount )
    { this.playerDraws.setText( this._langManager.getLangData( "amountDraws" ) + " : " + String.valueOf( amount ) ); }  


    /**
     * Retourne le bouton de navigation vers la page d'accueil
     * @return
     */
    public NavigationButton getNavigationButton() { return this.returnToHomeScreenButton; }


    /**
     * Affiche ou cache les composants graphiques de la fenêtre
     * @param status
     */
    public void updateComponents( Boolean status )
    {
        if ( this.DEBUG ) System.out.println( "[ACCOUTNS VIEW] > Mise a jour des composants" );
        this.playerUsername.setVisible( status );
        this.playerGameAsPlayer1.setVisible( status );
        this.playerGameAsPlayer2.setVisible( status );
        this.playerVictories.setVisible( status );
        this.playerDefeates.setVisible( status );
        this.playerDraws.setVisible( status );
        this.victoryRate.setVisible( status );
        this.defeateRate.setVisible( status );
        this.drawRate.setVisible( status );
        this.usernameInput.setVisible( status );
        this.searchUsernameButton.setVisible( status );
        this.returnToHomeScreenButton.setVisible( status );
    }
}