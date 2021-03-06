package co.edu.udea.drai.ayudasdrai;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;

import co.edu.udea.drai.ayudasdrai.co.edu.udea.drai.ayudasdrai.util.GetJson;

/**
 * Clase Encargada de exponer el formato para realizar la solicitud y enviar la solicitud a traves
 * del metodo POST expuesto en el servicio del Back End AyudasDRAI.
 * @author Heinner Esteban Alvarez <exteban34@gmail.com>
 * @version 1.0 23/11/2015
 *
 */
public class ReportarActivity extends AppCompatActivity{

    /**
     * Objetos asociados a la vista
     */
    Spinner spSolicitud;
    Spinner spBloque;
    EditText edNombre;
    EditText edAula;
    EditText edDescripcion;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar);
        edNombre = (EditText) findViewById(R.id.editTextNombre);

        edAula = (EditText) findViewById(R.id.editTextAula);
        edDescripcion = (EditText) findViewById(R.id.editTextDescripcion);
        spSolicitud= (Spinner) findViewById(R.id.spinnerSolicitud);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.solicitudes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSolicitud.setAdapter(adapter);
        spBloque= (Spinner) findViewById(R.id.spinnerBloque);
        ArrayAdapter<CharSequence> adapterbloques = ArrayAdapter.createFromResource(this,
                R.array.bloques, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBloque.setAdapter(adapterbloques);


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
        }else if (!aulaValida()){
           Toast.makeText(this,R.string.error_aula_invalida,Toast.LENGTH_LONG).show();
           }else {
                Log.i("Objeto JSON enviado", construirJSON().toString());
                new SendPost().execute(getResources().getString(R.string.base_url)
                        +getResources().getString(R.string.reporte_rest));
                }
    }

    /**
     * Metodo encargado de construir un objeto JSON a partir de los datos ingresados en
     * el formulario.
     * @return JSONObject con los datos en formato JSON.
     * @throws JSONException
     */
    public JSONObject construirJSON() throws JSONException {
        JSONObject tipojson=new JSONObject();
        JSONObject reportejson = new JSONObject();
        reportejson.put("usuario",edNombre.getText().toString());
        reportejson.put("correoUsuario","correo@correo.com");
        tipojson.put("idTipo",(spSolicitud.getSelectedItemPosition()+1));
        reportejson.put("tipo",tipojson);
        reportejson.put("bloque",spBloque.getSelectedItem().toString());
        reportejson.put("aula", edAula.getText().toString());
        reportejson.put("descripcion", edDescripcion.getText().toString());
    return reportejson;
    }

    /**
     * Metodo que verifica si un campo de texto se encunetra nulo o sin informacion.
     * @param edt campo de texto a evaluar nulidad.
     * @return
     */
    public boolean esNulo(EditText edt){

        if(edt.getText()==null || edt.getText().toString().equals("")){
            return true;
        }
        return false;
    }

    /**
     * Metodo que verifica la existencia de informacion en los campos obligatorios del formulario.
     * @return
     */
    public boolean algunCampoNulo(){
            if(esNulo(edNombre)|| esNulo(edAula)){
            return true;
        }
        return false;
    }

    /**
     * Metodo encargado de limpiar los datos del formulario.
     */
    public void limpiarCampos(){
        edNombre.setText("");
        edAula.setText("");
        edDescripcion.setText("");

    }

    /**
     * Metodo encargado de comprobar si el aula y el bloque especificados
     * son validos en la logica de negocio.
     * @return
     */
    public boolean aulaValida(){
        boolean valida=false;
        String[] aulasValidas;
        String aula=spBloque.getSelectedItem().toString() +"-"+ edAula.getText().toString();
        aulasValidas= getResources().getStringArray(R.array.aulas_validas);
        if(Arrays.asList(aulasValidas).contains(aula)){
                valida = true;
        }
        return valida;
    }

    /**
     * Clase Asincrona encargada de enviar el objeto en formato JSON a traves
     * del metodo POST expuesto en el servicio del Back End
     * @author Heinner Esteban Alvarez <exteban34@gmail.com>
     * @version 1.0 25/11/2015
     */
    private class SendPost extends AsyncTask<String, Void, String> {
        private String mensaje= ":v";

        protected void onPreExecute() {
            pDialog = new ProgressDialog(ReportarActivity.this);
            pDialog.setMessage(getString(R.string.enviando_solicitud));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... urls) {
            //can catch a variety of wonderful things
            String line = " :v ";
            try {
                //constants
                URL url = new URL(getResources().getString(R.string.base_url)
                        +getResources().getString(R.string.reporte_rest));
                String message = construirJSON().toString();
                Log.i("Objeto antes de enviar", message);
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
                /*InputStream is = new Bu
                result = is.toString();
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();*/
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    os.close();
                    reader.close();
                    conn.disconnect();
                    Log.i("Input POST", line);
                    mensaje=line;
                    return line;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (RuntimeException e){
                e.printStackTrace();
            }

            return line;
        }
        protected void onPostExecute(String result) {
            mostrarMensaje(mensaje);
        }

        public void mostrarMensaje(String message){

            pDialog.dismiss();
            if(message.equals("Se ha almacenado el reporte exitosamente")){
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getApplicationContext(),R.string.error_tiempo_tarea,Toast.LENGTH_LONG).show();
            }
            mensaje= ":v";
            limpiarCampos();

        }


    }


    /**
     * Evento asociado al boton ver administradores.
     * @param view
     */
   /* public void verAdmins(View view){
        new LeerAdmins().execute(getResources().getString(R.string.base_url)
                +getResources().getString(R.string.admin_rest));
    }
    */
    /**
     * Clase Asincrona encargada de consultar el arreglo JSON  con los administradores
     * @author Heinner Esteban Alvarez <exteban34@gmail.com>
     * @version 1.0 24/11/2015
     */
   /* private class LeerAdmins extends AsyncTask<String, Void, String> {
        String mensaje = "";


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
                Toast.makeText(getApplicationContext(),R.string.error_conexion,Toast.LENGTH_LONG).show();
            }
        }
    }
    */



}
