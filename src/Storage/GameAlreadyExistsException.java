package src.Storage;


/**
 * Erreur si la partie existe déjà
 */
public class GameAlreadyExistsException extends Exception
{
    public GameAlreadyExistsException( String nom )
    { super( "Le nom : <" + nom + "> est déjà utilisé." ); }    
}