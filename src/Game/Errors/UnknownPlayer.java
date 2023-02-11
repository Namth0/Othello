package src.Game.Errors;


/**
 * Erreur indiquant que le joueur spécifié est invalide
 */
public class UnknownPlayer extends Exception
{ public UnknownPlayer( int player ) { super( "Unknown player : " + player + "\n\tMust be 1 or 2" ); } }
