package src.Storage;


/**
 * Exception lorsque le joueur ciblé n'existe pas
 */
public class PlayerDoesNotExistsException extends Exception 
{
    public PlayerDoesNotExistsException( int id ) 
    { super( "Could not find player of id: " + String.valueOf( id ) ); }
}
