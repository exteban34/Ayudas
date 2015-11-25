package co.edu.udea.drai.ayudasdrai;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import co.edu.udea.drai.ayudasdrai.co.edu.udea.drai.ayudasdrai.util.GetJson;


public class ReportarActivity extends ActionBarActivity {

    /**
     * Objetos asociados a la vista
     */
    Spinner spSolicitud;
    EditText edNombre;
    EditText edBloque;
    EditText edAula;
    EditText edDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar);
        edNombre = (EditText) findViewById(R.id.editTextNombre);
        edBloque = (EditText) findViewById(R.id.editTextBloque);
        edAula = (EditText) findViewById(R.id.editTextAula);
        edDescripcion = (EditText) findViewById(R.id.editTextDescripcion);
        spSolicitud= (Spinner) findViewById(R.id.spinnerSolicitud);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.solicitudes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSolicitud.setAdapter(adapter);

    }

    /**
     * Evento click asociado al boton de "ENVIAR REPORTE",
     * verifica la correctitud de los datos y ejecuta la tarea asincrona con el evento POST
     * @throws JSONException
     * @throws IOException
     */
    public void reportar(View view) throws JSONException, IOException {

        if(algunCampoNulo()){
            Toast.makeText(this,R.string.error_campo_nulo,Toast.LENGTH_LONG).show();

        }else {
            Log.i("Objeto JSON enviado", construirJSON().toString());
           new SendPost().execute("http://172.21.37.158:8084/AyudasDRAI/rest/Reporte");
        }
        limpiarCampos();



    }

    public JSONObject construirJSON() throws JSONException {
        JSONObject tipojson=new JSONObject();
        JSONObject reportejson = new JSONObject();
        reportejson.put("usuario",edNombre.getText().toString());
        reportejson.put("correoUsuario","correo@correo.com");
        tipojson.put("idTipo",(spSolicitud.getSelectedItemPosition()+1));
        reportejson.put("tipo",tipojson);
        reportejson.put("bloque",edBloque.getText().toString());
        reportejson.put("aula", edAula.getText().toString());
        reportejson.put("descripcion", edDescripcion.getText().toString());
    return reportejson;
    }

    public boolean esNulo(EditText edt){

        if(edt.getText()==null || edt.getText().toString().equals("")){
            return true;
        }
        return false;
    }

    public boolean algunCampoNulo(){

        if(esNulo(edNombre)|| esNulo(edBloque) || esNulo(edAula)){
            return true;
        }
        return false;
    }

    public void limpiarCampos(){
        edNombre.setText("");
        edAula.setText("");
        edBloque.setText("");
        edDescripcion.setText("");

    }

    public void verAdmins(View view){
        new LeerAdmins().execute("http://172.21.37.158:8084/AyudasDRAI/rest/Admin");
    }

    private class LeerAdmins extends AsyncTask<String, Void, String> {


        protected String doInBackground(String... urls) {
            return GetJson.getJson(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {

                Log.i("RESULT", result);
                JSONArray admins = new JSONArray(result);

                for (int i = 0; i < admins.length(); i++) {
                    JSONObject adminJson = admins.getJSONObject(i);
                    Toast.makeText(getApplicationContext(),adminJson.getString("nombreAdministrador")+" , "+
                            adminJson.getString("correoAdministrador"),Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Ocurre algun problema al consultar los datos."
                        +"\n Por favor verifica tu conexion a internet e intentalo nuevamente",Toast.LENGTH_LONG).show();
            }
        }
    }

    private class SendPost extends AsyncTask<String, Void, Void> {


        protected Void doInBackground(String... urls) {
            //can catch a variety of wonderful things
            String result = null;
            try {
                //constants
                result = " ";
                URL url = new URL("http://172.21.37.158:8084/AyudasDRAI/rest/Reporte");
                String message = construirJSON().toString();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();

                //do somehting with response
                /*InputStream is = conn.getInputStream();
                result = is.toString();
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();*/

                //String contentAsString = readIt(is,len);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                //clean up
                //os.close();
                //is.close();
                //conn.disconnect();
            }


            return null;
        }
    }



}
