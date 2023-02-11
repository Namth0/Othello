package src;


import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * Classe de gestion des données de configuration
 */
public class ConfigManager 
{
    private JSONObject mainJsonObject;


    /**
     * Constructeur
     * @param configFilePath
     */
    public ConfigManager( String configFilePath)
    {
        this.loadDataToJson( this.loadDataFromFile( configFilePath ) );
    }  


    /**
     * Conversion données format String au format JSON
     * @param fileData
     */
    private void loadDataToJson( String fileData )
    {
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
     * Retourne la valeur de configuration associée à la clé {key}
     * @param key
     * @return
     */
    public String getConfigData( String key )
    {
        String data = "";
        try { data = (String) this.mainJsonObject.get( key ); }
        catch ( Exception e ) { e.printStackTrace(); }
        return data;
    }
}