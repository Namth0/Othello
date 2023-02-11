package src.Storage;


/**
 * Erreur si la partie ciblée n'existe pas
 */
public class GameDoesNotExistsException extends Exception
{
    public GameDoesNotExistsException( String nom )
    { super( "Aucune partie enregistrée sous le nom <" + nom + ">" ); }    
}