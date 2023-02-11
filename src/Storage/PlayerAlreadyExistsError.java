package src.Storage;


/**
 * Exception lorsque le programme tente de dupliquer un joueur
 */
public class PlayerAlreadyExistsError extends Exception
{
    public PlayerAlreadyExistsError()
    { super( "Username already used." ); }
}
