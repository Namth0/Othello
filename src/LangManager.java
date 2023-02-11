package src;


import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * Classe de gestion des textes
 */
public class LangManager
{
    private JSONObject mainJsonObject;
    private final Boolean DEBUG;


    /**
     * Constructeur
     * @param langFilePath
     * @param debug
     */
    public LangManager( String langFilePath, Boolean debug )
    {
        this.DEBUG = debug;
        if ( this.DEBUG ) System.out.println( "[CREATION LANG MANAGER]" );
        this.loadDataToJson( this.loadDataFromFile( langFilePath ) );
    }  


    /**
     * Conversion données format String au format JSON
     * @param fileData
     */
    private void loadDataToJson( String fileData )
    {
        if ( this.DEBUG ) System.out.println( "[LANG MANAGER] > Chargement des donnees au format JSON" );
        try
        {
            JSONParser parser = new JSONParser();
            Object object = parser.parse( fileData );
            this.mainJsonObject = (JSONObject) object;
        }
        catch ( Exception e ) { e.printStackTrace(); }
    }


    /**
     * Récupération au format String des données stockées dans un fichier JSON
     * @param filePath
     * @return
     */
    private String loadDataFromFile( String filePath ) 
    {
        if ( this.DEBUG ) System.out.println( "[LANG MANAGER] > Chargement du fichier JSON" );
        String data = "";
        String line;
        try
        {
            BufferedReader bufferedReader = new BufferedReader(
                new FileReader( filePath, Charset.forName( "UTF-8" ) )
            );
            while( ( line = bufferedReader.readLine() ) != null ) { data += line + "\n"; }
            bufferedReader.close();
        }
        catch ( Exception e ) { e.printStackTrace(); }
        return data;
    }


    /**
     * Récupère le texte associé à la clé {key}
     * @param key
     * @return
     */
    public String getLangData( String key )
    {
        if ( this.DEBUG ) System.out.println( "[LANG MANAGER] > Recherche de la cle : <" + key + ">" );
        String data = "";
        try { data = (String) this.mainJsonObject.get( key ); }
        catch ( Exception e ) { e.printStackTrace(); }
        return data;
    }


    /**
     * Récupère le texte associé à la clé {key} et le retourne en majuscule
     * @param key
     * @return
     */
    public String getUppercaseData( String key )
    {
        if ( this.DEBUG ) System.out.println( "[LANG MANAGER] > Recherche de la cle : <" + key + "> puis conversion en majuscules" );
        String data = "";
        try { data = (String) this.mainJsonObject.get( key ); }
        catch ( Exception e ) { e.printStackTrace(); }
        return data.toUpperCase();
    }

}