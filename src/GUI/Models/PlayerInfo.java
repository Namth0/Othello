package src.GUI.Models;


/**
 * Représentation d'un joueur 
 */
public class PlayerInfo 
{
    private final String username;
    private final int gameAsPlayerOne;
    private final int gameAsPlayerTwo;
    private final int victories;
    private final int defeates;
    private final int draws;
    private final int games;
    private final double victoryRatio;
    private final double defeateRatio;
    private final double drawRatio;


    /**
     * Constructeur
     * @param username
     * @param gameAsPlayerOne
     * @param gameAsPlayerTwo
     * @param victories
     * @param defeates
     * @param draws
     */
    public PlayerInfo( String username, int gameAsPlayerOne, int gameAsPlayerTwo, int victories, int defeates, int draws )
    {
        this.username = username;
        this.gameAsPlayerOne = gameAsPlayerOne;
        this.gameAsPlayerTwo = gameAsPlayerTwo;
        this.victories = victories;
        this.defeates = defeates;
        this.draws = draws;
        this.games = victories + defeates + draws;
        this.victoryRatio = ( this.games == 0 ) ? 0.0 : (double) victories / this.games;
        this.defeateRatio = ( this.games == 0 ) ? 0.0 : (double) defeates / this.games;
        this.drawRatio = ( this.games == 0 ) ? 0.0 : (double) draws / this.games;
    }


    /**
     * Retourne le pseudo du joueur
     * @return
     */
    public String getUsername() { return this.username; }


    /**
     * Retourne le nombre de partie en tant que joueur 1
     * @return
     */
    public int getGameAsPlayerOne() { return this.gameAsPlayerOne; }


    /**
     * Retourne le nombre de partie en tant que joueur 2
     * @return
     */
    public int getGameAsPlayerTwo() { return this.gameAsPlayerTwo; }


    /**
     * Retourne le nombre de victoires
     * @return
     */
    public int getVictories() { return this.victories; }


    /**
     * Retourne le nombre de défaites
     * @return
     */
    public int getDefeates() { return this.defeates; }


    /**
     * Retourne le nombre de parties nulles
     * @return
     */
    public int getDraws() { return this.draws; }


    /**
     * Retourne le nombre de partie
     * @return
     */
    public int getGames() { return this.games; }


    /**
     * Retourne le ratio de victoires
     * @return
     */
    public double getVictoryRatio() { return this.victoryRatio; }


    /**
     * Retourne le ratio de défaites
     * @return
     */
    public double getDefeateRatio() { return this.defeateRatio; }


    /**
     * Retourne le ratio de parties nulles 
     * @return
     */
    public double getDrawRatio() { return this.drawRatio; }
}