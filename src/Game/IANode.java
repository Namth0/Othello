package src.Game;


import java.util.ArrayList;


/**
 * Classe de représentation d'un noeud de l'arbre de recherche d'un coup
 */
public class IANode
{
    private BoardTile coup;
    private int score;
    private ArrayList<IANode> positionsEngeandrees;
    private Boolean estMinNode;


    /**
     * Constructeur
     * @param coup
     * @param score
     * @param estMinNode
     */
    public IANode( BoardTile coup, int score, Boolean estMinNode ) 
    { 
        this.coup = coup;
        this.score = score;
        this.positionsEngeandrees = new ArrayList<IANode>();
        this.estMinNode = estMinNode;
    }

    
    /**
     * Constructeur généré depuis ses positions engendrées
     * @param coup
     * @param positionsEngeandrees
     */
    public IANode( BoardTile coup, ArrayList<IANode> positionsEngeandrees ) 
    { 
        this.coup = coup;
        this.positionsEngeandrees = positionsEngeandrees;
    }

    
    /**
     * Ajoute une position à la liste des positions engeandrées. Vérifie que la position n'est pas déjà dans la liste
     * @param position
     */
    public void addPosition( IANode position ) 
    { 
        if ( !this.positionsEngeandrees.contains( position ) ) this.positionsEngeandrees.add( position );
    }

    
    /**
     * Retourne la position engendrée d'index : index
     * @param index
     * @return
     */
    public IANode getPosition( int index )
    {
        if ( index < 0 || index >= this.positionsEngeandrees.size() ) return null;
        return this.positionsEngeandrees.get( index );
    }


    /**
     * Retourne le coup représenté par le noeud
     * @return
     */
    public BoardTile getCoup() { return this.coup; }


    /**
     * Retourne les noeuds représentants les positions engendrées
     * @return
     */
    public ArrayList<IANode> getSousPositions()
    { return this.positionsEngeandrees; }


    /**
     * Retourne le type du noeud
     * @return
     */
    public Boolean estTypeMinNode() { return this.estMinNode; }


    /**
     * Change le score du noeud
     * @param nouveauScore
     */
    public void setScore( int nouveauScore ) { this.score = nouveauScore; }


    /**
     * Représentation au format {String} de la classe
     * @param tab
     * @return
     */
    public String toString( int tab )
    {
        String result = "";
        for ( int k = 0; k < tab; k++ ) { result += "\t"; }
        result += "[POSITION ENGENDREE] : " + this.coup.toString() + " (SCORE : + " + Integer.toString( this.score ) +  ") " + "\n";
        for ( int k = 0; k < this.positionsEngeandrees.size(); k++ )
        {
            result += this.positionsEngeandrees.get(k).toString( tab + 1 );
        }
        return result; 
    }
}