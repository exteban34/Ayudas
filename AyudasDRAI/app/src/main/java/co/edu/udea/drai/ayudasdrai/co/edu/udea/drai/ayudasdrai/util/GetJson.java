package co.edu.udea.drai.ayudasdrai.co.edu.udea.drai.ayudasdrai.util;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clase encargada de leer el texto JSON en una direccion y retornarlo en un String.
 * @author Heinner Esteban Alvarez <exteban34@gmail.com>
 * @version 1.0 07/09/2015
 *
 */
public class GetJson{
    /**
     *
     * @param urlservice  url de acceso al servicio
     * @return String que contiene el objeto JSON
     */
    public static String getJson(String urlservice) {


        URL url;
        HttpURLConnection urlConnection = null;
        String response=":v";


        try {
            url = new URL(urlservice);
            urlConnection = (HttpURLConnection) url.openConnection();
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200){
                String responseString = readStream(urlConnection.getInputStream());
                Log.v("CatalogClient", responseString);
                response = responseString;
            }else{
                Log.v("CatalogClient", "Response code:"+ responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }

        return response;

    }

    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}