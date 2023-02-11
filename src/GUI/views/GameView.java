package src.GUI.views;


import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import src.LangManager;
import src.ThemesManager;
import src.GUI.components.Labels.Credit;
import src.GUI.components.Labels.PlayerInfoLabel;
import src.Game.Board;
import src.Game.BoardTile;
import src.Game.Game;
import src.Game.GameLogic;
import src.Storage.DatabaseManager;
import src.Storage.GameSaving;
import src.GUI.components.Buttons.SelectPlayerButton;
import src.GUI.components.Buttons.StartGameButton;
import src.GUI.components.Buttons.NavigationButton;
import src.GUI.components.Board.*;
import java.awt.event.*;
import java.util.ArrayList;


/**
 * Page de la partie
 */
public class GameView extends JPanel
{
    private PlayerInfoLabel scorePlayer1;
    private PlayerInfoLabel scorePlayer2;
    private SelectPlayerButton player1Info;
    private SelectPlayerButton player2Info;
    private SelectPlayerButton selectEasyIaP1;
    private SelectPlayerButton selectEasyIaP2;
    private SelectPlayerButton selectNormalIaP1;
    private SelectPlayerButton selectNormalIaP2;
    private SelectPlayerButton selectHardIaP1;
    private SelectPlayerButton selectHardIaP2;
    private SelectPlayerButton loadGameButton;
    private StartGameButton startGameButton;
    private Credit indicateurDeTour;
    private NavigationButton returnToHomeScreenButton;
    private Tile[][] board;
    private Border[] corners;
    private Border[] borderTop;
    private Border[] borderRight;
    private Border[] borderLeft;
    private Border[] borderBottom;
    private ThemesManager themesManager;
    private LangManager langManager;
    private int windowWidth; 
    private int windowHeight;
    private int tailleTableau;
    private static String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", 
                                        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
    private Boolean isEasyIaPlayer1 = false;
    private Boolean isEasyIaPlayer2 = false;
    private Boolean isNormalIaPlayer1 = false;
    private Boolean isNormalIaPlayer2 = false;
    private Boolean isHardIaPlayer1 = false;
    private Boolean isHardIaPlayer2 = false;
    private Boolean isPlayerPlayer1 = false;
    private Boolean isPlayerPlayer2 = false;
    private Boolean partieEnCours = false;
    private DatabaseManager dbManager;
    private Game partie;
    private GameLogic gameManager;
    private ArrayList<BoardTile> coupsPossibles;
    private final Boolean DEBUG;
    private String joueur1Pseudo;
    private String joueur2Pseudo;
    private GameSaving gameSavesManager;


    /**
     * Constructeur
     * @param themesManager
     * @param langManager
     * @param windowWidth
     * @param windowHeight
     * @param tailleTableau
     * @param dbManager
     * @param gameManager
     * @param debug
     * @param cheminStockageParties
     */
    public GameView( ThemesManager themesManager, LangManager langManager, int windowWidth, int windowHeight, int tailleTableau, DatabaseManager dbManager, GameLogic gameManager, Boolean debug, String cheminStockageParties )
    {
        this.DEBUG = debug;
        if ( this.DEBUG ) System.err.println( "[CREATION GAME VIEW]" );
        this.themesManager = themesManager;
        this.langManager = langManager;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.tailleTableau = tailleTableau + ( tailleTableau % 2 );
        this.board = new Tile[tailleTableau][tailleTableau];
        this.corners = new Border[4];
        this.borderTop = new Border[tailleTableau];
        this.borderLeft = new Border[tailleTableau];
        this.borderBottom = new Border[tailleTableau];
        this.borderRight = new Border[tailleTableau];
        this.dbManager = dbManager;
        this.gameSavesManager = new GameSaving( cheminStockageParties, this.tailleTableau, debug );
        int tileSize = Math.min( windowHeight, windowWidth ) / 20;
        this.gameManager = gameManager;
        this.setBackground( themesManager.getColorFromThemeData( "backgroundClr" ) );
        this.setLayout( null );
        Color borderColor = this.themesManager.getColorFromThemeData( "boardBackgroundColor" );
        Color fgColor = this.themesManager.getColorFromThemeData( "boardForegroundColor" );
        Font borderFont = new Font(
            this.themesManager.getThemeData("boardFontFamily"),
            this.themesManager.getFontWeightFromThemeData( "boardFontWeight" ),
            this.themesManager.getFontSizeFromThemeData( "boardFontSize" )
        );
        for ( int row = 0; row < tailleTableau; row ++ )
        { for ( int col = 0; col < tailleTableau; col++ ) 
            { this.board[row][col] = new Tile( this.themesManager, tileSize ); } 
        }
        for ( int i = 0; i < 4; i++ )
        { this.corners[i] = new Border( tileSize, " ", borderFont, borderColor, fgColor ); }
        for ( int i = 0; i < tailleTableau; i++ )
        {
            this.borderBottom[i] = new Border( tileSize, this.getAlphaLabelFromIndex( i ), borderFont, borderColor, fgColor );
            this.borderTop[i] = new Border( tileSize, this.getAlphaLabelFromIndex( i ), borderFont, borderColor, fgColor );
            this.borderLeft[i] = new Border( tileSize, String.valueOf( i + 1 ), borderFont, borderColor, fgColor );
            this.borderRight[i] = new Border( tileSize, String.valueOf( i + 1 ), borderFont, borderColor, fgColor );
        }
    }   


    /**
     * Création des composants de la page
     */
    public void initializeComponents()
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Initialisation des composants" );
        this.player1Info = new SelectPlayerButton(langManager.getLangData("selectPlayer"), themesManager);
        this.player2Info = new SelectPlayerButton(langManager.getLangData("selectPlayer"), themesManager);
        this.selectEasyIaP1 = new SelectPlayerButton(langManager.getLangData("easyIA"), themesManager);
        this.selectEasyIaP2 = new SelectPlayerButton(langManager.getLangData("easyIA"), themesManager);
        this.selectNormalIaP1 = new SelectPlayerButton(langManager.getLangData("normalIA"), themesManager);
        this.selectNormalIaP2 = new SelectPlayerButton(langManager.getLangData("normalIA"), themesManager);
        this.selectHardIaP1 = new SelectPlayerButton(langManager.getLangData("hardIA"), themesManager);
        this.selectHardIaP2 = new SelectPlayerButton(langManager.getLangData("hardIA"), themesManager);
        this.loadGameButton = new SelectPlayerButton( this.langManager.getLangData("loadAGame"), themesManager );
        this.startGameButton = new StartGameButton( this.langManager.getLangData("startGame"), themesManager );
        this.indicateurDeTour = new Credit( this.langManager.getLangData( "waitingForGame" ), themesManager );
        this.setEventsOnSelectPlayersButtons();
        this.scorePlayer1 =  new PlayerInfoLabel(langManager.getLangData("player") + " 1 : ", themesManager);
        this.scorePlayer2 =  new PlayerInfoLabel(langManager.getLangData("player") + " 2 : ", themesManager);
        this.returnToHomeScreenButton = new NavigationButton( langManager.getLangData( "menu" ), themesManager );
        this.drawBoard();
    }


    /**
     * Affichage du plateau
     */
    private void drawBoard()
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Affichage du plateau" );
        int tileSize =  this.corners[0].getPreferredSize().width;
        int[] positionBoard = new int[] 
        { 
            ( this.windowWidth - ( tileSize * ( this.tailleTableau + 2 ) ) ) / 2,
            ( this.windowHeight - ( tileSize * ( this.tailleTableau + 2 ) ) ) / 2
        };
        placeTiles( positionBoard, tileSize );
        placeCorners( positionBoard, tileSize );
        placeBorders( positionBoard, tileSize );
        placeLabels( tileSize );
    }


    /**
     * Placement des cases du plateau
     * @param positionBoard
     * @param tileSize
     */
    private void placeTiles( int[] positionBoard, int tileSize )
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Affichage des cases du plateau" );
        for ( int row = 0; row < this.tailleTableau; row++ )
        {
            for ( int col = 0; col < this.tailleTableau; col++ )
            {
                this.add( this.board[row][col] );
                this.board[row][col].setBounds(
                    positionBoard[0] + tileSize * ( row + 1 ),
                    positionBoard[1] + tileSize * ( col + 1 ),
                    tileSize, tileSize
                );
            }
        }
    }


    /**
     * Placement des coins du plateau
     * @param positionBoard
     * @param tileSize
     */
    private void placeCorners( int[] positionBoard, int tileSize )
    { 
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Affichage des coins du plateau" );
        this.add( this.corners[0] );
        this.add( this.corners[1] );
        this.add( this.corners[2] );
        this.add( this.corners[3] );
        this.corners[0].setBounds( positionBoard[0], positionBoard[1], tileSize, tileSize );
        this.corners[1].setBounds( positionBoard[0], positionBoard[1] + ( this.tailleTableau + 1 ) * tileSize, tileSize, tileSize );
        this.corners[2].setBounds( positionBoard[0] + ( this.tailleTableau + 1 ) * tileSize, positionBoard[1], tileSize, tileSize );
        this.corners[3].setBounds( positionBoard[0] + ( this.tailleTableau + 1 ) * tileSize, positionBoard[1] + ( this.tailleTableau + 1 ) * tileSize, tileSize, tileSize );
    }


    /**
     * Placement des bordures du plateau
     * @param positionBoard
     * @param tileSize
     */
    private void placeBorders( int[] positionBoard, int tileSize )
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Affichage des bordures du plateau" );
        for ( int i = 0; i < this.tailleTableau; i++ )
        {
            this.add( this.borderTop[i] );
            this.add( this.borderBottom[i] );
            this.add( this.borderLeft[i] );
            this.add( this.borderRight[i] );

            this.borderTop[i].setBounds( positionBoard[0] + (i + 1) * tileSize, positionBoard[1], tileSize, tileSize );
            this.borderBottom[i].setBounds( positionBoard[0] + (i + 1) * tileSize, positionBoard[1] + ( this.tailleTableau + 1 ) * tileSize, tileSize, tileSize );
            this.borderLeft[i].setBounds( positionBoard[0], positionBoard[1] + (i + 1) * tileSize, tileSize, tileSize );
            this.borderRight[i].setBounds( positionBoard[0] + ( this.tailleTableau + 1 ) * tileSize, positionBoard[1] + (i + 1) * tileSize, tileSize, tileSize );
        }
    }


    /**
     * Placement du reste des composants
     * @param tileSize
     */
    private void placeLabels( int tileSize )
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Affichage du reste des composants sur le plateau" );
        Dimension size;
        SelectPlayerButton[] boutonsSelectionJoueur1 = new SelectPlayerButton[] 
        { this.player1Info, this.selectEasyIaP1, this.selectNormalIaP1, this.selectHardIaP1 };
        SelectPlayerButton[] boutonsSelectionJoueur2 = new SelectPlayerButton[] 
        { this.player2Info, this.selectEasyIaP2, this.selectNormalIaP2, this.selectHardIaP2 };
        int step = 0;
        for ( int i = 0; i < boutonsSelectionJoueur1.length; i++ )
        {
            this.add( boutonsSelectionJoueur1[i] );
            this.add( boutonsSelectionJoueur2[i] );
            size = boutonsSelectionJoueur1[i].getPreferredSize();
            boutonsSelectionJoueur1[i].setBounds(
                ( this.windowWidth - tileSize * 10 ) / 8,
                ( this.windowHeight - tileSize * 10 ) / 2 + step,
                size.width, size.height
            );
            boutonsSelectionJoueur2[i].setBounds(
                ( this.windowWidth - tileSize * 10 ) / 8,
                this.windowHeight - ( this.windowHeight - tileSize * 10 ) / 2 - size.height - step,
                size.width, size.height
            );
            step += size.height + 5;
        }
        this.add( this.loadGameButton );
        size = this.loadGameButton.getPreferredSize();
        this.loadGameButton.setBounds(
            (this.windowWidth - size.width) / 2,
            this.windowHeight - 200,
            size.width, size.height 
        );
        this.add( this.scorePlayer1 );
        this.add( this.scorePlayer2 );
        this.add( this.startGameButton );
        this.add( this.indicateurDeTour );
        this.add( this.returnToHomeScreenButton );
        size = this.startGameButton.getPreferredSize();
        this.startGameButton.setBounds(
            ( this.windowWidth - size.width ) / 2, 10,
            size.width,
            size.height
        );
        this.indicateurDeTour.setBounds(
            ( this.windowWidth - this.indicateurDeTour.getPreferredSize().width ) / 2, size.height + 20,
            this.indicateurDeTour.getPreferredSize().width, this.indicateurDeTour.getPreferredSize().height
        );
        size = this.scorePlayer1.getPreferredSize();
        this.scorePlayer1.setBounds(
            this.windowWidth - ( this.windowWidth - tileSize * 10 ) / 8 - size.width,
            ( this.windowHeight - tileSize * 10 ) / 2,
            700, size.height
        );
        size = this.scorePlayer2.getPreferredSize();
        this.scorePlayer2.setBounds(
            this.windowWidth - ( this.windowWidth - tileSize * 10 ) / 8 - size.width,
            this.windowHeight - ( this.windowHeight - tileSize * 10 ) / 2 - size.height,
            700, size.height
        );
        size = this.returnToHomeScreenButton.getPreferredSize();
        this.returnToHomeScreenButton.setBounds(
            10, 10, size.width, size.height  
        );
    }


    /**
     * Association des évènemments aux différents boutons
     */
    private void setEventsOnSelectPlayersButtons()
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Association des evenements des boutons" );
        this.player1Info.addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e ) { selectPlayerAsPlayer( 1 ); }
            }
        );

        this.player2Info.addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e ) { selectPlayerAsPlayer( 2 ); }
            }
        );

        this.selectEasyIaP1.addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e ) { selectIaAsPlayer( 1, 1 ); }
            }
        );

        this.selectEasyIaP2.addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e ) { selectIaAsPlayer( 2, 1 ); }
            }
        );

        this.selectNormalIaP1.addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e ) { selectIaAsPlayer( 1, 2 ); }
            }
        );

        this.selectNormalIaP2.addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e ) { selectIaAsPlayer( 2, 2 ); }
            }
        );

        this.selectHardIaP1.addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e ) { selectIaAsPlayer( 1, 3 ); }
            }
        );

        this.selectHardIaP2.addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e ) { selectIaAsPlayer( 2, 3 ); }
            }
        );

        this.startGameButton.addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e ) { startGame(); }
            }
        );

        this.loadGameButton.addActionListener(
            new ActionListener()
            {
                @Override
                public void actionPerformed( ActionEvent e ) { chargerPartie(); }
            }
        );

        for ( int row = 0; row < this.tailleTableau; row++ )
        {
            for ( int col = 0; col < this.tailleTableau; col++ )
            { this.board[row][col].addActionListener( getButtonAction( row, col ) ); }
        }
    }


    /**
     * Retourne la fonction appelée lorsqu'une case du plateau est cliquée
     * @param row
     * @param col
     * @return
     */
    private ActionListener getButtonAction( int row, int col ) 
    {
        return new ActionListener() 
        {
            @Override
            public void actionPerformed( ActionEvent e ) { caseCliquee( row, col ); }
        };
    } 


    /**
     * Sélectionne un joueur humain en tant que joueur {player}
     * @param player
     */
    private void selectPlayerAsPlayer( int player )
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Selection d'un joueur en tant que joueur " + player );
        String username = JOptionPane.showInputDialog( this.langManager.getLangData("enterUsername" ) );
        if ( username == null || username.equals( "" )  ) return;
        if ( pseudoEstIa( username ) ) return;
        try 
        { 
            if ( dbManager.searchPlayerByName(username).getString(1) == null ) this.dbManager.inserNewPlayer( username ); 
        } catch ( Exception e ) { e.printStackTrace();; }

        if ( player == 1 )
        {
            if ( (this.langManager.getLangData( "player" ) + " 2 : " + username).equals( this.scorePlayer2.getText() ) ) return;
            isEasyIaPlayer1 = false;
            isNormalIaPlayer1 = false;
            isHardIaPlayer1 = false;
            isPlayerPlayer1 = true;   
            this.scorePlayer1.setText( 
                this.langManager.getLangData( "player" ) + " 1 : " + username
            );
            this.joueur1Pseudo = username;
        }
        else
        {
            if ( (this.langManager.getLangData( "player" ) + " 1 : " + username).equals( this.scorePlayer1.getText() ) ) return;
            isEasyIaPlayer2 = false;
            isNormalIaPlayer2 = false;
            isHardIaPlayer2 = false;
            isPlayerPlayer2 = true;
            this.scorePlayer2.setText( 
                this.langManager.getLangData( "player" ) + " 2 : " + username
            );
            this.joueur2Pseudo = username;
        }
    }


    /**
     * Vérifie que le joueur humain choisi n'est pas une IA
     * @param pseudo
     * @return
     */
    private Boolean pseudoEstIa( String pseudo )
    { return pseudo.equals("AI naive") || pseudo.equals("AI partiellement naive") || pseudo.equals("AI min max"); }


    /**
     * Sélectionne une IA en tant que joueur {player} 
     * @param player
     * @param iaDifficulty
     */
    private void selectIaAsPlayer( int player, int iaDifficulty )
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Selection d'une ia de difficulte " + iaDifficulty + " en tant que joueur " + player );
        if ( player == 1 )
        {
            this.isEasyIaPlayer1 = iaDifficulty == 1;
            this.isNormalIaPlayer1 = iaDifficulty == 2;
            this.isHardIaPlayer1 = iaDifficulty == 3;
            this.isPlayerPlayer1 = false;   
            this.scorePlayer1.setText(
                ( iaDifficulty == 1 ) ? this.langManager.getLangData("scoreIaEasyP1") : 
                ( iaDifficulty == 2 ) ? this.langManager.getLangData("scoreIaNormalP1") : 
                                        this.langManager.getLangData("scoreIaHardP1")
            );
            this.joueur1Pseudo = null;
        }
        else
        {
            this.isEasyIaPlayer2 = iaDifficulty == 1;
            this.isNormalIaPlayer2 = iaDifficulty == 2;
            this.isHardIaPlayer2 = iaDifficulty == 3;
            this.isPlayerPlayer2 = false;
            this.scorePlayer2.setText(
                ( iaDifficulty == 1 ) ? this.langManager.getLangData("scoreIaEasyP2") : 
                ( iaDifficulty == 2 ) ? this.langManager.getLangData("scoreIaNormalP2") : 
                                        this.langManager.getLangData("scoreIaHardP2")
            );
            this.joueur2Pseudo = null;
        }

        
    }


    /**
     * Lance une partie
     */
    private void startGame()
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Debut d'une partie" );
        if ( 
            ( !this.isEasyIaPlayer1 && !this.isNormalIaPlayer1 && !this.isHardIaPlayer1 && !this.isPlayerPlayer1 ) ||
            ( !this.isEasyIaPlayer2 && !this.isNormalIaPlayer2 && !this.isHardIaPlayer2 && !this.isPlayerPlayer2 ) ) 
        {
            JOptionPane.showMessageDialog(null, this.langManager.getLangData("mustSelectUsers" ) );
            return;
        }
        int idJ1 = 0;
        int idJ2 = 0;
        try 
        {
            if ( this.joueur1Pseudo == null) idJ1 = ( this.isEasyIaPlayer1 ) ? 6 : ( this.isHardIaPlayer1 ) ? 8 : 7;
            else idJ1 = this.dbManager.searchPlayerByName( this.joueur1Pseudo ).getInt( 1 );
            if ( this.joueur2Pseudo == null) idJ1 = ( this.isEasyIaPlayer2 ) ? 6 : ( this.isHardIaPlayer2 ) ? 8 : 7;
            else idJ1 = this.dbManager.searchPlayerByName( this.joueur2Pseudo ).getInt( 1 );
        }
        catch( Exception e ) { return; }
        this.partie = new Game( 
            0, 1, new Board( this.tailleTableau ),
            idJ1, idJ2  
        );
        this.indicateurDeTour.setText( 
            this.langManager.getLangData( "turns" ) + " (" + String.valueOf( this.partie.getTours() ) + ")  : " +
            this.langManager.getLangData( "player" ) + " 1"
        ); 
        this.coupsPossibles = this.gameManager.getListCoupsValides( this.partie.getPlateau(), 1 );
        updateBoard();
        if ( this.DEBUG )
        {
            System.out.println( "Il y a " + coupsPossibles.size() + " coups possibles" );
            for ( int c = 0; c < this.coupsPossibles.size(); c++ )
            { System.out.println( " o " + this.coupsPossibles.get( c ).toString() );}
        }
        this.partieEnCours = true;
        if ( !this.isPlayerPlayer1 ) tourIA();
    }


    /**
     * Retourne un label litéral unique depuis un index 
     * @param idx
     * @return
    */
    private String getAlphaLabelFromIndex( int idx )
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] Retourne un label unique depuis un index" );
        String res = "";
        Boolean running = true;
        if ( idx < 0 ) return res;
        int temp = idx;
        while ( running )
        {
            res += letters[ temp % 26 ];
            running = temp >= letters.length;
            temp = (int) Math.ceil( temp / letters.length );
        }
        return res;
    }


    /**
     * Retourne le bouton de navigation vers la page d'accueil
     * @return
     */
    public NavigationButton getNavigationButton() { return this.returnToHomeScreenButton; }


    /**
     * Affiche ou cache les composants graphiques
     * @param status
     */
    public void updateComponents( Boolean status )
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > MAJ des composants" );
        this.scorePlayer1.setVisible( status );
        this.scorePlayer2.setVisible( status );
        this.player1Info.setVisible( status );
        this.player2Info.setVisible( status );
        this.selectEasyIaP1.setVisible( status );
        this.selectNormalIaP1.setVisible( status );
        this.selectHardIaP1.setVisible( status );
        this.selectEasyIaP2.setVisible( status );
        this.selectNormalIaP2.setVisible( status );
        this.selectHardIaP2.setVisible( status );
        this.startGameButton.setVisible( status );

        this.returnToHomeScreenButton.setVisible( status );
        for ( int row = 0; row < this.board.length; row++ )
        {
            for ( int col = 0; col < this.board[row].length; col++ )
            {
                this.board[row][col].setVisible( status );
            }
        }
        for ( int c = 0; c < this.corners.length; c++ ) { this.corners[c].setVisible( status ); }
        for ( int b = 0; b < this.borderTop.length; b++ ) 
        { 
            this.borderTop[b].setVisible( status );
            this.borderRight[b].setVisible( status );
            this.borderLeft[b].setVisible( status );
            this.borderBottom[b].setVisible( status );
        }
    }


    /**
     * Détermine si le coup est possible
     * @param row
     * @param col
     * @return
     */
    private Boolean estCoupPossible( int row, int col )
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Retourne si le coup est possible : (row: " + row + ", col: " + col + ")" );
        if ( this.partie.getPlateau().getTile(row, col).getOwner() != 0 || this.coupsPossibles.size() <= 0 ) return false;
        int[] temp;
        for ( int coup = 0; coup < this.coupsPossibles.size(); coup++ )
        { 
            temp = this.coupsPossibles.get( coup ).getCoordonates();
            if ( temp[0] == row && temp[1] == col ) return true;
        }
        return false;
    }



    /**
     * Appelée lorsqu'une case de l'hotellier est cliquée
     * Vérifie qu'une partie est en cours
     * Place le coup s'il est valide
     * Fais jouer l'IA choisie si une IA joue
     * @param row
     * @param col
     */
    private void caseCliquee( int row, int col )
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Joue le coup sur la case cliquee : (row: " + row + ", col: " + col + ")" );
        if ( !partieEnCours )
        {
            if ( this.DEBUG ) System.out.println( "Aucune partie n'est en cours." );
            return;
        }
        
        if ( this.DEBUG )
        {
            System.out.print( "Le joueur " + this.partie.getJoueur() + " joue sur la case (" + row + ", " + col + ")" );
            System.out.println( " > Il y a " + this.coupsPossibles.size() + " coups jouables" );
        }

        if ( !this.estCoupPossible( row, col ) ) return;
        if ( this.DEBUG ) System.out.println( "Le coup choisi est (" + row + ", " + col + ")" );

        ArrayList<BoardTile> coupsEnPrise = this.gameManager.getListeCasesEnPrise( 
            this.partie.getPlateau(), 
            this.partie.getJoueur(), 
            new BoardTile( row, col )
        );

        if ( this.DEBUG ) System.out.println("Il y a " + coupsEnPrise.size() + " case(s) en prise(s) : ");

        int[] temp;
        for ( int coup = 0; coup <  coupsEnPrise.size(); coup++ )
        {
            temp = coupsEnPrise.get( coup ).getCoordonates();
            this.partie.placeJetonEnPrise( temp[0], temp[1] );
        }

        this.partie.placeJetons( row, col );
        this.coupsPossibles = this.gameManager.getListCoupsValides( this.partie.getPlateau() , this.partie.getJoueur() );

        updateBoard();
        this.indicateurDeTour.setText( 
            this.langManager.getLangData( "turns" ) + " (" + String.valueOf( this.partie.getTours() ) + ")  : " +
            this.langManager.getLangData( "player" ) + " " + String.valueOf( this.partie.getJoueur() )
        ); 

        int[] scores = this.gameManager.getScore( this.partie.getPlateau() );

        this.scorePlayer1.setText(
            this.langManager.getLangData("scoreP1") + " : " + Integer.toString( scores[0] )
        );

        this.scorePlayer2.setText(
            this.langManager.getLangData("scoreP2") + " : " + Integer.toString( scores[1] )
        );

        if ( this.coupsPossibles == null || this.coupsPossibles.size() <= 0 )
        {
            if ( this.DEBUG ) System.out.println( "La partie est terminee." );
            this.partieTerminee();
        }
        
        if ( ( this.partie.getJoueur() == 1 && !this.isPlayerPlayer1 ) ||
             ( this.partie.getJoueur() == 2 && !this.isPlayerPlayer2 ) )
        {
            // try { Thread.sleep( (long) this.gameManager.getIADelay() ); } catch ( InterruptedException ie ) {} // Ne marche pas ??
            tourIA();
        }
    }


    /**
     * Place le coup de l'IA
     */
    private void tourIA()
    {
        if ( this.DEBUG ) System.out.println("[GAME VIEW] > Tour de l'IA" );
        
        
        BoardTile coupIA;
        
        if ( this.partie.getJoueur() == 1 )
        {
            coupIA = ( this.isEasyIaPlayer1 ) ?   this.gameManager.coupNaif( this.coupsPossibles ) :
                     ( this.isNormalIaPlayer1 ) ? this.gameManager.coupPartielNaif( this.partie.getPlateau(), this.coupsPossibles, 1 ) :
                                                  this.gameManager.alphaBetaPrediction( this.partie.getPlateau(), 1,this.coupsPossibles );
        }
        else
        {
            coupIA = ( this.isEasyIaPlayer2 ) ?   this.gameManager.coupNaif( this.coupsPossibles ) :
                     ( this.isNormalIaPlayer2 ) ? this.gameManager.coupPartielNaif( this.partie.getPlateau(), this.coupsPossibles, 2 ) :
                                                  this.gameManager.alphaBetaPrediction( this.partie.getPlateau(), 2, this.coupsPossibles );
        }
        if ( this.DEBUG )
        {
            if ( coupIA == null ) { return; }
            
        }
        caseCliquee( coupIA.getCoordonates()[0], coupIA.getCoordonates()[1] );
    }   


    /**
     * Détermine si le coup est valide
     * @param row
     * @param col
     * @return
    */
    private Boolean caseEstCoupValide( int row, int col )
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Determine si la case est un coup valide : (row: " + row + ", col: " + col + ")" );
        if ( this.coupsPossibles == null || this.coupsPossibles.size() <= 0 ) return false;
        for ( int coup = 0; coup < this.coupsPossibles.size(); coup++ )
        {
            if ( this.coupsPossibles.get( coup ).getCoordonates()[0] == row && this.coupsPossibles.get( coup ).getCoordonates()[1] == col ) return true;
        }
        return false;
    }


    /**
     * Met à jour l'affichage du tableau
     */
    private void updateBoard()
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Met a jour le tableau" );
        for ( int row = 0; row < this.tailleTableau; row++ )
        {
            for ( int col = 0; col < this.tailleTableau; col++ )
            {
                this.board[row][col].setAsPossibleMove( this.caseEstCoupValide( row, col ) );
                this.board[row][col].setPlayerOnTile( this.partie.getPlateau().getTile(row, col).getOwner() );
            } 
        }
    }


    /**
     * Mets fin à la partie en cours et détermine le vainqueur
     */
    private void partieTerminee()
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > La partie est terminee" );
        this.partieEnCours = false;
        int[] scores = this.gameManager.getScore( this.partie.getPlateau() );
        if ( scores[0] != scores[1] ) JOptionPane.showMessageDialog(null, this.langManager.getLangData( "victoryPlayer" ) + " " + ( ( scores[0] < scores[1] ) ? "2" : "1" ) + " | " + Integer.toString( scores[0] ) + " - " + Integer.toString( scores[1] ) );
        else JOptionPane.showMessageDialog(null, this.langManager.getLangData( "draw" ) + " | " + Integer.toString( scores[0] ) + " - " + Integer.toString( scores[1] ) );
        
        int niveauIa = 0;
        if ( this.joueur1Pseudo == null || this.joueur2Pseudo == null )
        {
            niveauIa = ( this.joueur1Pseudo == null ) ?
                ( ( this.isEasyIaPlayer1 ) ? 1 : (  this.isNormalIaPlayer1 ) ? 2 : 3 ) :
                ( ( this.isEasyIaPlayer2 ) ? 1 : (  this.isNormalIaPlayer2 ) ? 2 : 3 );
        }
 
        this.dbManager.ajoutePartie( 
            this.joueur1Pseudo,
            this.joueur2Pseudo,
            ( ( scores[0] == scores[1] ) ? 0 : ( scores[0] < scores[1] ) ? 2 : 1 ),
            niveauIa
        );
    }


    /**
     * Charge une partie sauvegardée
     */
    public void chargerPartie()
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Charge une partie enregistree" );
        String[] listePartiesEnregistrees = this.gameSavesManager.listeDesParties();
        if ( listePartiesEnregistrees == null || listePartiesEnregistrees.length <= 0 ) 
        {
            JOptionPane.showMessageDialog( null, this.langManager.getLangData("noGameSaved") );
            return;
        }
        Object[] options = new Object[ listePartiesEnregistrees.length ];
        for ( int opt = 0; opt < listePartiesEnregistrees.length; opt++ )
        { options[opt] = (Object) listePartiesEnregistrees[ opt ]; }

        Game partie;
        String partieAOuvrir = ( String ) JOptionPane.showInputDialog(null, this.langManager.getLangData( "loadGame" ), "", JOptionPane.ERROR_MESSAGE, null, options, options[0]);
        if ( partieAOuvrir == null ) return;
        try { partie = this.gameSavesManager.loadGame( partieAOuvrir ); }
        catch ( Exception e ) {e.printStackTrace();  return; }
        if ( partie == null ) return;
        this.partie = partie;
        this.coupsPossibles = this.gameManager.getListCoupsValides( this.partie.getPlateau(), this.partie.getJoueur() );
        try
        {
            if ( estJoueurUneIa( this.partie.getJoueur1() ) ) this.joueur1Pseudo = null;
            else this.joueur1Pseudo = this.dbManager.getPlayerByID( this.partie.getJoueur1() ).getString( 1 );
            
            if ( estJoueurUneIa( this.partie.getJoueur2() ) ) this.joueur2Pseudo = null;
            else this.joueur2Pseudo = this.dbManager.getPlayerByID( this.partie.getJoueur2() ).getString( 1 );
        }
        catch ( Exception e )
        {
            System.out.println( "Impossible de recup les joueurs" );
            return;
        }
        if ( this.joueur1Pseudo == null )
        {
            this.isPlayerPlayer1 = false;
            this.isEasyIaPlayer1 = this.partie.getJoueur1() == 6;
            this.isNormalIaPlayer1 = this.partie.getJoueur1() == 7;
            this.isHardIaPlayer1 = this.partie.getJoueur1() == 8;
        }
        else 
        {
            this.isPlayerPlayer1 = true;
            this.isEasyIaPlayer1 = false;
            this.isNormalIaPlayer1 = false;
            this.isHardIaPlayer1 = false;
        }
        if ( this.joueur1Pseudo == null )
        {
            this.isPlayerPlayer2 = false;
            this.isEasyIaPlayer2 = this.partie.getJoueur2() == 6;
            this.isNormalIaPlayer2 = this.partie.getJoueur2() == 7;
            this.isHardIaPlayer2 = this.partie.getJoueur2() == 8;
        }
        else 
        {
            this.isPlayerPlayer2 = true;
            this.isEasyIaPlayer2 = false;
            this.isNormalIaPlayer2 = false;
            this.isHardIaPlayer2 = false;
        }
        updateBoard();
        this.partieEnCours = true;
        if ( ( this.partie.getJoueur() == 1 && !this.isPlayerPlayer1 ) ||( this.partie.getJoueur() == 2 && !this.isPlayerPlayer2 ) ) tourIA();
    }


    /**
     * Détermine si le joueur est une IA
     * @param id
     * @return
     */
    private Boolean estJoueurUneIa( int id )
    {
        return id == 6 || id == 7 || id == 8;
    }


    /**
     * Quitte la partie en cours
     * Demande si le joueur souhaite sauvegarder la partie en cours
    */
    public void quitterPartie()
    {
        if ( this.DEBUG ) System.out.println( "[GAME VIEW] > Quitte la partie en cours" );
        if ( !this.partieEnCours ) return;
        Object[] options = new Object[] { this.langManager.getLangData( "yes" ), this.langManager.getLangData( "no" ) }; 
        try
        {
            String reponse = (String) JOptionPane.showInputDialog( null, this.langManager.getLangData( "saveGame" ), "", JOptionPane.ERROR_MESSAGE, null, options, options[0] );
            if ( !reponse.equals( options[0] ) ) return;

            String nomPartie = JOptionPane.showInputDialog( null, this.langManager.getLangData( "saveGameAs" ));
            if ( nomPartie == null || nomPartie.equals( "" ) ) return;
            this.gameSavesManager.saveGame( nomPartie, this.partie );
        }
        catch ( Exception e ) { e.printStackTrace(); return; }
    }

}