package src;


import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.awt.Font;
import java.awt.Color;


/**
 * Classe de gestion des thèmes
 */
public class ThemesManager 
{
    private JSONObject mainJsonObject;
    private final Boolean DEBUG;


    /**
     * Constructeur
     * @param themeFilePath
     * @param debug
     */
    public ThemesManager( String themeFilePath, Boolean debug )
    {
        this.DEBUG = debug;
        if ( this.DEBUG ) System.out.println( "[CREATION THEME MANAGER]" );
        this.loadDataToJson( this.loadDataFromFile( themeFilePath ) );
    }  


    /**
     * Conversion données format String au format JSON
     * @param fileData
     */
    private void loadDataToJson( String fileData )
    {
        if ( this.DEBUG ) System.out.println( "[THEME MANAGER] > Chargement des donnees au format JSON" );
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
        if ( this.DEBUG ) System.out.println( "[THEME MANAGER] > Chargement du fichier JSON" );
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
     * Retourne une données de thème associée à la clé {key}
     * @param key
     * @return
     */
    public String getThemeData( String key )
    {
        if ( this.DEBUG ) System.out.println( "[THEME MANAGER] > Recherche de la cle : <" + key + ">" );
        String data = "";
        try { data = (String) this.mainJsonObject.get( key ); }
        catch ( Exception e ) { e.printStackTrace(); }
        return data;
    }


    /**
     * Récupère l'épaisseur de texte associée à la clé {key} et récupère la valeur {int} dans l'enum FONT
     */
    public int getFontWeightFromThemeData( String key )
    {
        if ( this.DEBUG ) System.out.println( "[THEME MANAGER] > Recherche de l'epaisseur de texte : <" + key + ">" );
        String data = "";
        try { data = (String) this.mainJsonObject.get( key ); }
        catch ( Exception e ) { e.printStackTrace(); }
        switch( data )
        {
            case "BOLD":
                return Font.BOLD;
            case "ITALIC":
                return Font.ITALIC;
            default:
                return Font.PLAIN;
        }
    }


    /**
     * Récupère la taille de texte associée à la clé {key} et la convertie en format {int}
     * @param key
     * @return
     */
    public int getFontSizeFromThemeData( String key )
    {
        if ( this.DEBUG ) System.out.println( "[THEME MANAGER] > Recherche d'une taille de texte : <" + key + ">" );
        String data = "";
        try { data = (String) this.mainJsonObject.get( key ); }
        catch ( Exception e ) {  e.printStackTrace(); }
        return (data.equals("")) ? 0:Integer.parseInt(data);
    }


    /**
     * Récupère la couleur associée à la clé {key} et la convertie en l'obejct Color.
     * @param key
     * @return
     */
    public Color getColorFromThemeData( String key )
    {
        if ( this.DEBUG ) System.out.println( "[THEME MANAGER] > Recherche d'une couleur : <" + key + ">" );
        String data = "";
        try { data = (String) this.mainJsonObject.get( key ); }
        catch ( Exception e ) { e.printStackTrace(); }
        if (data.equals("")) return null;
        data = data.replace("#", "");
        switch ( data.length() )
        {
            case 6:
                return new Color (
                    Integer.valueOf(data.substring(0, 2), 16),
                    Integer.valueOf(data.substring(2, 4), 16),
                    Integer.valueOf(data.substring(4, 6), 16)
                );
            case 8:
                return new Color (
                    Integer.valueOf(data.substring(0, 2), 16),
                    Integer.valueOf(data.substring(2, 4), 16),
                    Integer.valueOf(data.substring(4, 6), 16),
                    Integer.valueOf(data.substring(6, 8), 16)
                );
            default:
                return new Color( 0, 0, 0 );
        }
    }
}