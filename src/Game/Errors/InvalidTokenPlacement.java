package src.Game.Errors;


/**
 * Erreur signifiant que les coordonnées de placement d'un jeton sont invalides
 */
public class InvalidTokenPlacement extends Exception
{
    public InvalidTokenPlacement( int x, int y )
    {
        super( 
            "Invalid token placement:\n\tCan't place token at " + 
            x + " " + y + 
            "\n\tX and Y must be between 0 and 8 included."
        );
    }
}