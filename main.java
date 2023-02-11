import src.GUI.GUIController;


import src.*;
import src.Storage.*;


/**
 * @author Thomas HODSON
 * @author Othman BENCHERIF
 */


/**
 * Classe de démarrage du programme
 */
class Main
{
    /**
     * Début du programme
     * @param args
     */
    public static void main( String[] args )
    {
        ConfigManager cm = new ConfigManager("config/config.json");
        Boolean DEBUG = Boolean.parseBoolean( cm.getConfigData( "debug" ) );
        LangManager lm = new LangManager( "resources/i18n/" + cm.getConfigData( "lang" ) + ".json", DEBUG );
        ThemesManager tm = new ThemesManager( "resources/themes/" + cm.getConfigData( "theme" ) + ".json", DEBUG );
        DatabaseManager databaseManager = new DatabaseManager( DEBUG );
        if ( DEBUG ) System.out.println( "[DEMARRAGE]");
        try { databaseManager.connect( "db.sqlite3"); }
        catch( Exception e )
        {
            System.err.println( "Impossible de se connecter a la Base De Donnees." );
            System.exit( 1 );
        }
        new GUIController( cm, lm, tm, databaseManager );
        if ( DEBUG ) System.out.println( "[MISE EN ARRET]" );
    }
}
