package src.Game;


import java.util.ArrayList;
import java.util.Random;


/**
 * Ensemble des outils pour gérer la partie
 */
public class GameLogic 
{
    private final int boardLenght;
    private final int minMaxCeil;
    private final Boolean DEBUG;
    private final int iaDelay;

    
    /**
     * Constructeur de la classe de gestion de partie
     * @param boardLenght
     * @param minMaxCeil
     * @param debug
     * @param iaDelay
     */
    public GameLogic( int boardLenght, int minMaxCeil, Boolean debug, int iaDelay ) 
    { 
        this.DEBUG = debug;
        if ( this.DEBUG ) System.out.println( "[CREATION GAME LOGIC]" );
        this.boardLenght = boardLenght;
        this.minMaxCeil = minMaxCeil;
        this.iaDelay = iaDelay;
    }


    /**
     * Retourne un coup aléatoirement parmis les coups possibles
     * @param listeDesCoupsPossibles
     * @return
     */
	public BoardTile coupNaif( ArrayList<BoardTile> listeDesCoupsPossibles )
	{
        if ( this.DEBUG ) System.out.println( "[GAME LOGIC] > coup naif" );
        if ( listeDesCoupsPossibles == null || listeDesCoupsPossibles.size() <= 0 ) return null; 
		return listeDesCoupsPossibles.get( new Random().nextInt( listeDesCoupsPossibles.size() ) );
	}


    /**
     * Retourne le meilleur coup parmis les coups possibles sans générations de plateaux annexes
     * @param plateau
     * @param listeDesCoupsPossibles
     * @param joueur
     * @return
     */
	public BoardTile coupPartielNaif( Board plateau, ArrayList<BoardTile> listeDesCoupsPossibles, int joueur )
	{
        if ( this.DEBUG ) System.out.println( "[GAME LOGIC] > coup partiellement naif" );
        if ( listeDesCoupsPossibles == null || listeDesCoupsPossibles.size() <= 0 ) return null;
		int[] valeurCoups = new int[listeDesCoupsPossibles.size()];
        for ( int m = 0; m < listeDesCoupsPossibles.size(); m++ )
		{ valeurCoups[m] = evaluerCoup( plateau, listeDesCoupsPossibles.get(m), joueur ); }
		int mini = ( valeurCoups.length > 0 ) ? valeurCoups[0] : Integer.MAX_VALUE ;
        int minIndex = ( mini == Integer.MAX_VALUE ) ? -1 : 0;
        for ( int k = 0; k < valeurCoups.length; k++ )
        {
            if ( mini > valeurCoups[k] )
            {
                mini = valeurCoups[k];
                minIndex = k;
            }
        }
        return ( minIndex == -1 ) ? null : listeDesCoupsPossibles.get( minIndex );
	}


    /**
     * Retourne la valeur estimée d'un coup
     * @param plateau
     * @param coupEtudie
     * @param joueur
     * @return
     */
	private int evaluerCoup( Board plateau, BoardTile coupEtudie, int joueur )
	{
        if ( this.DEBUG ) System.out.println( "[GAME LOGIC] > Evaluer le coup" );
		int x = coupEtudie.getCoordonates()[0];
		int y = coupEtudie.getCoordonates()[1];
		int casesVidesAutour = 0;
		int casesEnnemiesAutour = 0;
		for ( int i = -1; i < 2; i++ )
		{
			for ( int j = -1; j < 2; j++ )
			{
				if ( x + j < 0 || x + j >= this.boardLenght || y + i < 0 || y + i >= this.boardLenght ) { casesVidesAutour++; }
				else
				{
					if ( plateau.getTile( y + i, x + j ).getOwner() == ( ( joueur == 1 ) ? 2 : 1 ) ) casesEnnemiesAutour++;
				}
			}
		}
		return (int) Math.round( getCaseEnPrise( plateau, joueur, coupEtudie ) * 0.5 + casesVidesAutour - casesEnnemiesAutour );
	}


    /**
     * Fonction récursive représentant l'analyse par l'algoritme MINMAX-AB
     * @param plateau
     * @param estTypeMin
     * @param joueur
     * @param profondeur
     * @param valeurParent
     * @return
     */
    private int alphaBetaPredictionAux( Board plateau, Boolean estTypeMin, int joueur, int profondeur, int valeurParent )
    {
        if ( profondeur == this.minMaxCeil ) return evaluerPosition( plateau, joueur );
        ArrayList<BoardTile> listeCoupsJouables = getListCoupsValides( plateau, joueur );
        if ( listeCoupsJouables.size() == 0 ) return ( estTypeMin ) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Board positionEngendree;
        int valeurEtudiee;
        int[] coordonates;
        int valeurFinale = ( estTypeMin ) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for ( int k = 0; k < listeCoupsJouables.size(); k++ )
        {
            coordonates = listeCoupsJouables.get(k).getCoordonates();
            positionEngendree = new Board( plateau.getTiles() );
            positionEngendree.getTile( coordonates[0], coordonates[1] ).setOwner( joueur );
            valeurEtudiee = alphaBetaPredictionAux(
                positionEngendree, 
                !estTypeMin, ( joueur == 1 ) ? 2 : 1, 
                profondeur + 1, valeurFinale
            );
            if ( !estTypeMin && valeurEtudiee > valeurParent ) return valeurEtudiee;
            if ( !estTypeMin && valeurFinale < valeurEtudiee ) { valeurFinale = valeurEtudiee; }
            else if ( estTypeMin && valeurFinale > valeurEtudiee ) { valeurFinale = valeurEtudiee; }
        }
        return valeurFinale;
    }


    /**
     * retourne le meilleur coup parmis les coups possibles selon l'algorithme MINMAX-AB
     * @param plateau
     * @param joueur
     * @param listeCoupsJouables
     * @return
     */
    public BoardTile alphaBetaPrediction( Board plateau, int joueur, ArrayList<BoardTile> listeCoupsJouables )
    {
        if ( this.DEBUG ) System.out.println( "[GAME LOGIC] > Prediction alpha beta" );
        if ( listeCoupsJouables == null || listeCoupsJouables.size() <= 0 ) 
        {
            return null;
        }
        int[] valeursCoups = new int[ listeCoupsJouables.size() ];
        Board positionEngendree;
        for ( int move = 0; move < listeCoupsJouables.size(); move++ )
        {
            positionEngendree = new Board( plateau.getTiles() );
            positionEngendree.getTile( listeCoupsJouables.get(move).getCoordonates()[0], listeCoupsJouables.get(move).getCoordonates()[1] ).setOwner( joueur );
            valeursCoups[move] = alphaBetaPredictionAux( positionEngendree, true, ( joueur == 1 ) ? 2 : 1, 1, Integer.MIN_VALUE );
        }
        int maxi = ( valeursCoups.length == 0 ) ? Integer.MIN_VALUE : valeursCoups[0];
        int maxIndex = ( valeursCoups.length == 0 ) ? -1 : 0;
        for ( int k = 0; k < valeursCoups.length; k++ )
        {
            if ( maxi < valeursCoups[k] )
            {
                maxi = valeursCoups[k];
                maxIndex = k;
            }
        }
        return ( maxIndex >= 0 ) ? listeCoupsJouables.get(maxIndex) : null;
    }


    /**
     * retourne le meilleur coup parmis les coups possibles selon l'algorithme MINMAX
     * @param plateau
     * @param joueur
     * @param coupAdvrese
     * @return
     */
    public BoardTile minMaxPredictionMove( Board plateau, int joueur, BoardTile coupAdvrese )
    {
        if ( this.DEBUG ) System.out.println( "[GAME LOGIC] > Min max prediction" );
        ArrayList<BoardTile> listeCoupsJouables = getListCoupsValides( plateau, joueur );
        if ( listeCoupsJouables.size() == 0 ) return null;
        int[] moveValues = new int[listeCoupsJouables.size()];
        Board temp;

        IATree arbreDePrediction;
        IANode coupPrecedent = new IANode( coupAdvrese, Integer.MIN_VALUE, true );

        for ( int m = 0; m < listeCoupsJouables.size(); m++ )
        {
            temp = new Board( plateau.getTiles() );
            temp.getTile( listeCoupsJouables.get(m).getCoordonates()[0], listeCoupsJouables.get(m).getCoordonates()[1] ).setOwner( joueur );
            moveValues[m] = ennemi( temp, joueur, 0, coupPrecedent );
        }

        arbreDePrediction = new IATree( coupPrecedent, new Board( plateau.getTiles() ), "MIN-MAX" );

        if ( this.DEBUG ) System.out.println( arbreDePrediction.toString() );

        int mini = ( moveValues.length > 0 ) ? moveValues[0] : Integer.MAX_VALUE ;
        int minIndex = ( mini == Integer.MAX_VALUE ) ? -1 : 0;
        for ( int k = 0; k < moveValues.length; k++ )
        {
            if ( mini > moveValues[k] )
            {
                mini = moveValues[k];
                minIndex = k;
            }
        }
        return ( minIndex == -1 ) ? null : listeCoupsJouables.get( minIndex );
    }
    

    /**
     * Retourne le meilleur coup à une position pour le joueur étudié
     * @param noeud
     * @param niveau
     * @param joueur
     * @param nodeToAppend
     * @return
    */
    public int ami( Board noeud, int niveau, int joueur, IANode nodeToAppend )
    {
        Board n;
        ArrayList<BoardTile> l;
        BoardTile coup;
        int eval;
        int k = 0;
        if ( niveau == this.minMaxCeil ) { eval = evaluerPosition( noeud, joueur ); } 
        else
        {
            l = getListCoupsValides( noeud, joueur );
            eval = Integer.MIN_VALUE;
            while ( k < l.size() )
            {
                coup = l.get( k );
                nodeToAppend.addPosition( new IANode( l.get( k ) , 0, false) );
                n = engendrer( noeud, coup );
                eval = Math.max(
                    eval, ennemi( n, niveau + 1, ( joueur == 1 ) ? 2 : 1, nodeToAppend.getPosition( k ) )
                );
                nodeToAppend.getPosition( k ).setScore( eval );
            }
        }
        return eval;
    }


    /**
     * Retourne le meilleur coup à une position pour le joueur ennemi
     * @param noeud
     * @param niveau
     * @param joueur
     * @param nodeToAppend
     * @return
     */
    public int ennemi( Board noeud, int niveau, int joueur, IANode nodeToAppend )
    {
        Board n;
        ArrayList<BoardTile> l;
        BoardTile coup;
        int eval;
        int k = 0;

        if ( niveau == this.minMaxCeil ) { eval = evaluerPosition( noeud, joueur ); } 
        else
        {
            l = getListCoupsValides( noeud, joueur );
            eval = Integer.MAX_VALUE;
            while ( k < l.size() )
            {
                coup = l.get( k );
                nodeToAppend.addPosition( new IANode( l.get( k ), 0, true ) );
                n = engendrer( noeud, coup );
                eval = Math.min(
                    eval, ami( n, niveau + 1, ( joueur == 1 ) ? 2 : 1, nodeToAppend.getPosition( k ) )
                );
                nodeToAppend.getPosition( k ).setScore( eval );
            }
        }
        return eval;
    }


    /**
     * Génère une position pour l'étudier
     * @param plateau
     * @param coupAPlacer
     * @return
     */
    private Board engendrer( Board plateau, BoardTile coupAPlacer )
    {
        Board newBoard = new Board( plateau.getTiles() );
        int x = coupAPlacer.getCoordonates()[0];
        int y = coupAPlacer.getCoordonates()[1];
        newBoard.getTile( x, y ).setOwner( coupAPlacer.getOwner() );
        return newBoard;
    }


    /**
     * Evalue une position
     * @param plateau
     * @param joueur
     * @return
     */
    private int evaluerPosition( Board plateau, int joueur )
    {
        if ( this.DEBUG ) System.out.println( "[GAME LOGIC] > Evaluation de position" );
        ArrayList<BoardTile> casesComptees = new ArrayList<BoardTile>();
        ArrayList<Integer[]> casesVidesComptees = new ArrayList<Integer[]>();

        double possessionDeTerrain = 0.0;
        int casesVidesAutour = 0;
        int ennemiAutour = 0;
        BoardTile temp;
        int x; int y;
        int casesAmi = 0;
        int casesEnnemi = 0;
        for ( int row = 0; row < this.boardLenght; row++ )
        {
            for ( int col = 0; col < this.boardLenght; col++ )
            {
                temp = plateau.getTile(row, col);
                if ( temp.getOwner() == joueur )
                { 
                    casesAmi++;
                    x = temp.getCoordonates()[0];
                    y = temp.getCoordonates()[1];
                    
                    for ( int i = -1; i < 2; i++ )
                    {    
                        for ( int j = -1; j < 2; j++ )
                        {
                            if ( y + i < 0 || y + i >= this.boardLenght || x + j < 0 || x + j >= this.boardLenght )
                            { 
                                if ( !casesVidesComptees.contains( new Integer[]{ y + i, x + j } ) )
                                {
                                    casesVidesComptees.add( new Integer[]{ y + i, x + j } );
                                    casesVidesAutour++;
                                }
                            }
                            else
                            {
                                if ( plateau.getTile( y + i, x + j ).getOwner() == ( ( joueur == 1 ) ? 2 : 1 ) && 
                                    !casesComptees.contains( plateau.getTile( y + i, x + j ) ) )
                                {
                                    casesComptees.add( plateau.getTile( y + i, x + j ) );
                                    ennemiAutour++;
                                }
                            }
                        }
                    }
                }
                else if ( temp.getOwner() != 0 ) { casesEnnemi++; }
            }
        }
        possessionDeTerrain = ( casesEnnemi != 0 ) ? casesAmi / casesEnnemi : 0;
        return (int) Math.round( possessionDeTerrain * 0.5 + casesVidesAutour - ennemiAutour );
    }

    
    /**
     * Retourne le nombre de cases en prises par un coup
     * @param plateau
     * @param joueur
     * @param coupEtudie
     * @return
     */
    private int getCaseEnPrise( Board plateau, int joueur, BoardTile coupEtudie )
    {
        if ( this.DEBUG ) System.out.println( "[GAME LOGIC] > Compte le nombre de cases en prise" );
        if ( coupEtudie.getOwner() != 0 ) return -1;
        return (
            casesEnPrisesNord( plateau, joueur, coupEtudie ).size() +
            casesEnPrisesSud( plateau, joueur, coupEtudie ).size() +
            casesEnPrisesEst( plateau, joueur, coupEtudie ).size() +
            casesEnPrisesOuest( plateau, joueur, coupEtudie ).size() +
            casesEnPrisesNordEst( plateau, joueur, coupEtudie ).size() +
            casesEnPrisesNordOuest( plateau, joueur, coupEtudie ).size() +
            casesEnPrisesSudEst( plateau, joueur, coupEtudie ).size() +
            casesEnPrisesSudOuest( plateau, joueur, coupEtudie ).size()
        );
    }


    /**
     * Retourne la liste des cases en prises par un coup
     * @param plateau
     * @param joueur
     * @param coupEtudie
     * @return
     */
    public ArrayList<BoardTile> getListeCasesEnPrise(  Board plateau, int joueur, BoardTile coupEtudie  )
    {
        if ( this.DEBUG ) System.out.println( "[GAME LOGIC] > Retourne la liste de cases en prise" );
        ArrayList<BoardTile> result = new ArrayList<BoardTile>();
        if ( coupEtudie.getOwner() != 0 ) return result;
        result.addAll( this.casesEnPrisesNord( plateau, joueur, coupEtudie ) );
        result.addAll( this.casesEnPrisesEst( plateau, joueur, coupEtudie ) );
        result.addAll( this.casesEnPrisesSud( plateau, joueur, coupEtudie ) );
        result.addAll( this.casesEnPrisesOuest( plateau, joueur, coupEtudie ) );
        result.addAll( this.casesEnPrisesNordEst( plateau, joueur, coupEtudie ) );
        result.addAll( this.casesEnPrisesNordOuest( plateau, joueur, coupEtudie ) );
        result.addAll( this.casesEnPrisesSudEst( plateau, joueur, coupEtudie ) );
        result.addAll( this.casesEnPrisesSudOuest( plateau, joueur, coupEtudie ) );
        return result;
    }


    /**
     * Retourne la liste des coups possibles
     * @param plateau
     * @param joueur
     * @return
     */
    public ArrayList<BoardTile> getListCoupsValides( Board plateau, int joueur )
    {
        if ( this.DEBUG ) System.out.println( "[GAME LOGIC] > Retourne la liste de coups valides" );
        ArrayList<BoardTile> listeCoups = new ArrayList<BoardTile>();
        for ( int row = 0; row < this.boardLenght; row++ )
        {
            for ( int col = 0; col < this.boardLenght; col++ )
            {
                if ( plateau.getTile( row, col ).getOwner() == 0 )
                {

                    int nord = casesEnPrisesNord( plateau, joueur, plateau.getTile( row, col ) ).size();
                    int sud = casesEnPrisesSud( plateau, joueur, plateau.getTile( row, col ) ).size();
                    int est = casesEnPrisesEst( plateau, joueur, plateau.getTile( row, col ) ).size();
                    int ouest = casesEnPrisesOuest( plateau, joueur, plateau.getTile( row, col ) ).size();
                    int nordest = casesEnPrisesNordEst( plateau, joueur, plateau.getTile( row, col ) ).size();
                    int nordouest = casesEnPrisesNordOuest( plateau, joueur, plateau.getTile( row, col ) ).size();
                    int sudest = casesEnPrisesSudEst( plateau, joueur, plateau.getTile( row, col ) ).size();
                    int sudouest = casesEnPrisesSudOuest( plateau, joueur, plateau.getTile( row, col ) ).size();

                    if ( this.DEBUG )  System.out.println("Pour (" + row + ", " + col + ") | nord= " + nord + " | sud=" + sud + " | est= " + est  + 
                            " | ouest=" + ouest + " | ne=" + nordest + " | no=" + nordouest + " | se=" + sudest + " | so=" + sudest);
                    if (
                        nord > 0 || sud  > 0 || est > 0 || ouest > 0 ||
                        nordest > 0 || nordouest > 0 || sudest > 0 || sudouest > 0
                    ) listeCoups.add( plateau.getTile( row, col ) );
                }
            } 
        }
        return listeCoups;
    }


    /**
     * Retourne la liste des cases en prises au dessus du coup
     * @param plateau
     * @param joueur
     * @param coupEtudie
     * @return
     */
    private ArrayList<BoardTile> casesEnPrisesNord( Board plateau, int joueur, BoardTile coupEtudie )
    {
        ArrayList<BoardTile> listeCoups = new ArrayList<BoardTile>();
        if ( coupEtudie.getOwner() != 0 ) return listeCoups;
        
        BoardTile temp;
        int k = 1;
        while ( coupEtudie.getCoordonates()[0] - k >= 0 )
        {
            temp = plateau.getTile( coupEtudie.getCoordonates()[0] - k, coupEtudie.getCoordonates()[1] );
            if ( temp.getOwner() == ( ( joueur == 1) ? 2 : 1 ) ) 
                listeCoups.add( new BoardTile( temp.getCoordonates()[0], temp.getCoordonates()[1] ) );
            else if ( temp.getOwner() == 0 ) return new ArrayList<BoardTile>();
            else return listeCoups;
            k++;
        }
        return new ArrayList<BoardTile>();
    }


    /**
     * Retourne la liste des cases en prises en dessous du coup
     * @param plateau
     * @param joueur
     * @param coupEtudie
     * @return
     */
    private ArrayList<BoardTile> casesEnPrisesSud( Board plateau, int joueur, BoardTile coupEtudie )
    {
        ArrayList<BoardTile> listeCoups = new ArrayList<BoardTile>();
        if ( coupEtudie.getOwner() != 0 ) return listeCoups;
        
        BoardTile temp;
        int k = 1;
        while ( coupEtudie.getCoordonates()[0] + k < this.boardLenght )
        {
            temp = plateau.getTile( coupEtudie.getCoordonates()[0] + k, coupEtudie.getCoordonates()[1] );
            if ( temp.getOwner() == ( ( joueur == 1) ? 2 : 1 ) ) 
                listeCoups.add( new BoardTile( temp.getCoordonates()[0], temp.getCoordonates()[1] ) );
            else if ( temp.getOwner() == 0 ) return new ArrayList<BoardTile>();
            else return listeCoups;
            k++;
        }
        return new ArrayList<BoardTile>();
    }


    /**
     * Retourne la liste des cases en prises a gauche du coup
     * @param plateau
     * @param joueur
     * @param coupEtudie
     * @return
     */
    private ArrayList<BoardTile> casesEnPrisesOuest( Board plateau, int joueur, BoardTile coupEtudie )
    {
        ArrayList<BoardTile> listeCoups = new ArrayList<BoardTile>();
        if ( coupEtudie.getOwner() != 0 ) return listeCoups;
        
        BoardTile temp;
        int k = 1;
        while ( coupEtudie.getCoordonates()[1] - k >= 0 )
        {
            temp = plateau.getTile( coupEtudie.getCoordonates()[0], coupEtudie.getCoordonates()[1] - k );
            if ( temp.getOwner() == ( ( joueur == 1) ? 2 : 1 ) ) 
                listeCoups.add( new BoardTile( temp.getCoordonates()[0], temp.getCoordonates()[1] ) );
            else if ( temp.getOwner() == 0 ) return new ArrayList<BoardTile>();
            else return listeCoups;
            k++;
        }
        return new ArrayList<BoardTile>();
    }

    
    /**
     * Retourne la liste des cases en prises a droite du coup
     * @param plateau
     * @param joueur
     * @param coupEtudie
     * @return
     */
    private ArrayList<BoardTile> casesEnPrisesEst( Board plateau, int joueur, BoardTile coupEtudie )
    {
        ArrayList<BoardTile> listeCoups = new ArrayList<BoardTile>();
        if ( coupEtudie.getOwner() != 0 ) return listeCoups;
        
        BoardTile temp;
        int k = 1;
        while ( coupEtudie.getCoordonates()[1] + k < this.boardLenght )
        {
            temp = plateau.getTile( coupEtudie.getCoordonates()[0], coupEtudie.getCoordonates()[1] + k );
            if ( temp.getOwner() == ( ( joueur == 1) ? 2 : 1 ) ) 
                listeCoups.add( new BoardTile( temp.getCoordonates()[0], temp.getCoordonates()[1] ) );
            else if ( temp.getOwner() == 0 ) return new ArrayList<BoardTile>();
            else return listeCoups;
            k++;
        }
        return new ArrayList<BoardTile>();
    }

    
    /**
     * Retourne la liste des cases en prises sur la diagonale en haut à gauche
     * @param plateau
     * @param joueur
     * @param coupEtudie
     * @return
     */
    private ArrayList<BoardTile> casesEnPrisesNordOuest( Board plateau, int joueur, BoardTile coupEtudie )
    {
        ArrayList<BoardTile> listeCoups = new ArrayList<BoardTile>();
        if ( coupEtudie.getOwner() != 0 ) return listeCoups;
        BoardTile temp;
        int k = 1;
        while ( coupEtudie.getCoordonates()[0] - k >= 0 && coupEtudie.getCoordonates()[1] - k >= 0 )
        {
            temp = plateau.getTile( coupEtudie.getCoordonates()[0] - k, coupEtudie.getCoordonates()[1] - k );
            if ( temp.getOwner() == ( ( joueur == 1) ? 2 : 1 ) )
                listeCoups.add( new BoardTile( temp.getCoordonates()[0], temp.getCoordonates()[1] ) );
            else if ( temp.getOwner() == 0 ) return new ArrayList<BoardTile>();
            else return listeCoups;
            k++;
        }
        return new ArrayList<BoardTile>();
    }

    
    /**
     * Retourne la liste des cases en prises sur la diagonale en haut à droite
     * @param plateau
     * @param joueur
     * @param coupEtudie
     * @return
     */
    private ArrayList<BoardTile> casesEnPrisesNordEst( Board plateau, int joueur, BoardTile coupEtudie )
    {
        ArrayList<BoardTile> listeCoups = new ArrayList<BoardTile>();
        if ( coupEtudie.getOwner() != 0 ) return listeCoups;
        BoardTile temp;
        int k = 1;
        while ( coupEtudie.getCoordonates()[0] - k >= 0 && coupEtudie.getCoordonates()[1] + k < this.boardLenght )
        {
            temp = plateau.getTile( coupEtudie.getCoordonates()[0] - k, coupEtudie.getCoordonates()[1] + k );
            if ( temp.getOwner() == ( ( joueur == 1) ? 2 : 1 ) )
                listeCoups.add( new BoardTile( temp.getCoordonates()[0], temp.getCoordonates()[1] ) );
            else if ( temp.getOwner() == 0 ) return new ArrayList<BoardTile>();
            else return listeCoups;
            k++;
        }
        return new ArrayList<BoardTile>();
    }

    
    /**
     * Retourne la liste des cases en prises sur la diagonale en bas à droite
     * @param plateau
     * @param joueur
     * @param coupEtudie
     * @return
     */
    private ArrayList<BoardTile> casesEnPrisesSudEst( Board plateau, int joueur, BoardTile coupEtudie )
    {
        ArrayList<BoardTile> listeCoups = new ArrayList<BoardTile>();
        if ( coupEtudie.getOwner() != 0 ) return listeCoups;
        BoardTile temp;
        int k = 1;
        while ( coupEtudie.getCoordonates()[0] + k < this.boardLenght && coupEtudie.getCoordonates()[1] + k < this.boardLenght )
        {
            temp = plateau.getTile( coupEtudie.getCoordonates()[0] + k, coupEtudie.getCoordonates()[1] + k );
            if ( temp.getOwner() == ( ( joueur == 1) ? 2 : 1 ) )
                listeCoups.add( new BoardTile( temp.getCoordonates()[0], temp.getCoordonates()[1] ) );
            else if ( temp.getOwner() == 0 ) return new ArrayList<BoardTile>();
            else return listeCoups;
            k++;
        }
        return new ArrayList<BoardTile>();
    }

    
    /**
     * Retourne la liste des cases en prises sur la diagonale en bas à gauche
     * @param plateau
     * @param joueur
     * @param coupEtudie
     * @return
     */
    private ArrayList<BoardTile> casesEnPrisesSudOuest( Board plateau, int joueur, BoardTile coupEtudie )
    {
        ArrayList<BoardTile> listeCoups = new ArrayList<BoardTile>();
        if ( coupEtudie.getOwner() != 0 ) return listeCoups;
        BoardTile temp;
        int k = 1;
        while ( coupEtudie.getCoordonates()[0] + k < this.boardLenght && coupEtudie.getCoordonates()[1] - k >= 0 )
        {
            temp = plateau.getTile( coupEtudie.getCoordonates()[0] + k, coupEtudie.getCoordonates()[1] - k );
            if ( temp.getOwner() == ( ( joueur == 1) ? 2 : 1 ) )
                listeCoups.add( new BoardTile( temp.getCoordonates()[0], temp.getCoordonates()[1] ) );
            else if ( temp.getOwner() == 0 ) return new ArrayList<BoardTile>();
            else return listeCoups;
            k++;
        }
        return new ArrayList<BoardTile>();
    }


    /**
     * Retourne le score des deux joueurs
     * @param plateau
     * @return
     */
    public int[] getScore( Board plateau )
    {   
        if ( this.DEBUG ) System.out.println( "[GAME LOGIC] > Retounrne le score des joueurs" );
        if ( plateau == null ) return null;
        int scoreJoueur1 = 0;
        int scoreJoueur2 = 0;
        
        for ( int ligne = 0; ligne < plateau.getTiles().length; ligne++ )
        {
            for ( int c = 0; c < plateau.getTiles()[ligne].length; c++ )
            {
                scoreJoueur1 += ( plateau.getTiles()[ligne][c].getOwner() == 1 ) ? 1 : 0;
                scoreJoueur2 += ( plateau.getTiles()[ligne][c].getOwner() == 2 ) ? 1 : 0;
            }
        }
        return new int[]{ scoreJoueur1, scoreJoueur2 };
    }


    /**
     * Retourne le délai imposé à l'IA
     * @return
     */
    public int getIADelay() { return this.iaDelay; }
}

