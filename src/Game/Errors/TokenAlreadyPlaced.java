package src.Game.Errors;


/**
 * Erreur indiquant que le jeton est déjà placé
 */
public class TokenAlreadyPlaced extends Exception 
{
    public TokenAlreadyPlaced( int x, int y )
    {
        super(
            "Invalid token placement:\n\tCan't place token at " + 
            x + " " + y + 
            "\n\tThe tile must be free."
        );
    }    
}
