package src.Storage;


import java.sql.*;
import src.GUI.Models.*;


/**
 * Classe de gestion de la base de données
 */
public class DatabaseManager
{
    private Connection conn = null;
    private final Boolean DEBUG;


    /**
     * Constructeur
     * @param debug
     */
    public DatabaseManager( Boolean debug )
    {
        this.DEBUG = debug;
        if ( this.DEBUG ) System.out.println( "[CREATION DATABASE MANAGER]" );
    }


    /**
     * Connecte le programme java à la base de données
     * @throws SQLException
     * @param path
     */
    public void connect( String path ) throws SQLException
    {
        if ( this.DEBUG ) System.out.println( "[DATABASE MANAGER] > Connexion a la base de donnees" );
        conn = DriverManager.getConnection(
            "jdbc:sqlite:" + path
        );
        if ( conn == null ) throw ( new SQLException() );
    }


    /**
     * Renvoit les données ciblées
     * @param fields
     * @param table
     * @param filters
     * @return
     * @throws SQLException
     */
    public ResultSet getData( String fields, String table, String filters ) throws SQLException
    {
        if ( this.DEBUG ) System.out.println( "[DATABASE MANAGER] > Recherche de donnees : (champs: " + fields + " | table: " + table + " |  filtres: " + filters + ")" );
        String sqlCommand = "SELECT " + fields + " FROM " + table + " " + filters;
        Statement stmt = this.conn.createStatement();
        ResultSet rs = stmt.executeQuery( sqlCommand );
        return rs;
    }


    /**
     * Ajoute un nouveau joueur dans la table players de la base de donneés
     * @param username
     * @throws PlayerAlreadyExistsError
     * @throws SQLException
     */
    public void inserNewPlayer( String username ) 
        throws PlayerAlreadyExistsError, SQLException
    {
        if ( this.DEBUG ) System.out.println( "[DATABASE MANAGER] > Insertion d'un nouvel utilisateur : (pseudo: " + username + ")" );
        Boolean usernameAlreadyTaken = false;
        try
        {
            if ( searchPlayerByName(username).getString(1) != null ) usernameAlreadyTaken = false;
        }
        catch( SQLException e ) { usernameAlreadyTaken = true; }
        if ( usernameAlreadyTaken ) throw ( new PlayerAlreadyExistsError() );

        String command = "INSERT INTO players(username) VALUES(?)";
        PreparedStatement pstmt = conn.prepareStatement( command );
        pstmt.setString( 1, username );
        pstmt.executeUpdate();
    }


    /**
     * Renvoit le joueur enregistré avec le pseudo {name}
     * @param name
     * @return
     */
    public ResultSet searchPlayerByName( String name )
    {
        if ( this.DEBUG ) System.out.println( "[DATABASE MANAGER] > Recherche d'un utilisateur : (pseudo: " + name + ")" );
        ResultSet matchingPlayer;
        String sqlCommand = "SELECT id, username FROM players WHERE username LIKE ?";
        try
        {
            PreparedStatement pst = this.conn.prepareStatement(sqlCommand);
            pst.setString( 1, name );
            matchingPlayer = pst.executeQuery();
        } 
        catch ( SQLException e )
        {
            e.printStackTrace();
            matchingPlayer = null;
        }
        return matchingPlayer;
    }


    /**
     * Renvoit le joueur d'ID {id}
     * @param id
     * @return
     */
    public ResultSet getPlayerByID( int id )
    {
        if ( this.DEBUG ) System.out.println( "[DATABASE MANAGER] > Recherche d'un utilisateur : (id: " + id + ")" );
        ResultSet matchingPlayer;
        String sqlCommand = "SELECT username FROM players WHERE id = ?";
        try
        {
            PreparedStatement pst = this.conn.prepareStatement(sqlCommand);
            pst.setInt( 1, id );
            matchingPlayer = pst.executeQuery();
        } 
        catch ( SQLException e )
        {
            e.printStackTrace();
            matchingPlayer = null;
        }
        return matchingPlayer;
    }


    /**
     * Récupère les données de partie du joueur et renvoit un object {PlayerInfo}
     * @param playerId
     * @param username
     * @return
     * @throws PlayerDoesNotExistsException
     * @throws SQLException
     */
    public PlayerInfo getPlayerData( int playerId, String username ) throws PlayerDoesNotExistsException, SQLException
    {
        if ( this.DEBUG ) System.out.println( "[DATABASE MANAGER] > Recherche d'un utilisateur : (pseudo: " + username + " | id:" + playerId + ")" );
        ResultSet playerExists = searchPlayerByName( username );
        if ( playerExists == null || playerExists.getInt( 1 ) != playerId ) return null; 
        ResultSet result;
        PreparedStatement pst;
        int gameAsPlayerOne = 0;
        int gameAsPlayerTwo = 0;
        int victories = 0;
        int defeates = 0;
        String sqlCommand1 = "SELECT COUNT(player1) FROM games WHERE player1 = ?;";
        try
        {
            pst = this.conn.prepareStatement( sqlCommand1 );
            pst.setInt( 1, playerId );
            result = pst.executeQuery();
            gameAsPlayerOne = result.getInt( 1 );
        }
        catch ( Exception e ) { return null; }


        String sqlCommand2 = "SELECT COUNT(player2) FROM games WHERE player2 = ?;";
        try
        {
            pst = this.conn.prepareStatement( sqlCommand2 );
            pst.setInt( 1, playerId );
            result = pst.executeQuery();
            gameAsPlayerTwo = result.getInt( 1 );
        }
        catch ( Exception e ) { return null; }


        String sqlCommand3 = "SELECT COUNT(winner) FROM games WHERE winner = ?;";
        try
        {
            pst = this.conn.prepareStatement( sqlCommand3 );
            pst.setInt( 1, playerId );
            result = pst.executeQuery();
            victories = result.getInt( 1 );
        }
        catch ( Exception e ) { return null; }


        String sqlCommand4 = "SELECT COUNT(winner) FROM games WHERE ( player1 = ? OR player2 = ?) AND winner <> ? AND winner IS NOT NULL;";
        try
        {
            pst = this.conn.prepareStatement( sqlCommand4 );
            pst.setInt( 1, playerId );
            pst.setInt( 2, playerId );
            pst.setInt( 3, playerId );
            result = pst.executeQuery();
            defeates = result.getInt( 1 );
        }
        catch ( Exception e ) { return null; }
        
        return new PlayerInfo(
            username,
            gameAsPlayerOne,
            gameAsPlayerTwo,
            victories,
            defeates,
            gameAsPlayerOne + gameAsPlayerTwo - victories - defeates
        );
    }


    /**
     * Ajoute une partie à la base de données
     * @param joueur1
     * @param joueur2
     * @param gagnant
     * @param aiDifficulte
     */
    public void ajoutePartie( String joueur1, String joueur2, int gagnant, int aiDifficulte )
    {
        if ( this.DEBUG ) System.out.println( "[DATABASE MANAGER] > Ajout d'une nouvelle partie  : (Joueur 1: " + joueur1 + " | Joueur2: " + joueur2 + " | Gagnant: " + gagnant + " | Diff. IA: " +  aiDifficulte +  ")" );
        if ( joueur1 == null && joueur2 == null ) return;
        try
        {
            String command = "INSERT INTO games (player1, player2, winner) VALUES (?,?,?);"; 
            PreparedStatement pstmt = this.conn.prepareStatement( command );

            if ( searchPlayerByName( joueur1 ) == null )
            {
                pstmt.setInt( 1, 5 + aiDifficulte  );
                pstmt.setInt( 2, searchPlayerByName( joueur2 ).getInt( 1 ) );

            }
            else if ( searchPlayerByName( joueur2 ) == null )
            {
                pstmt.setInt( 1, searchPlayerByName( joueur1 ).getInt( 1 ) );
                pstmt.setInt( 2, 5 + aiDifficulte  );
            }
            else
            {
                pstmt.setInt( 1, searchPlayerByName( joueur1 ).getInt( 1 ) );
                pstmt.setInt( 2, searchPlayerByName( joueur2 ).getInt( 1 ) );
            }

            if ( gagnant != 0 ) pstmt.setInt( 3, ( gagnant == 1 ) ? searchPlayerByName( joueur1 ).getInt( 1 ) : searchPlayerByName( joueur2 ).getInt( 1 ) );
            else pstmt.setNull( 3, java.sql.Types.NULL );

            pstmt.executeUpdate();
        }
        catch ( Exception e ) { return; }     
    }
}