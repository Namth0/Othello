package src.Game.Errors;


/**
 * Erreur indiquant que le placement proposé est interdi
 */
public class PlacementForbidden extends Exception
{
    public PlacementForbidden( int x , int y )
    {
        super( 
            "Invalid token placement:\n\tCan't place token at " + 
            x + " " + y + 
            "\n\tGame rules forbidden placement."
        );
    }
}