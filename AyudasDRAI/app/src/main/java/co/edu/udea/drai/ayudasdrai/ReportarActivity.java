package co.edu.udea.drai.ayudasdrai;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;


public class ReportarActivity extends ActionBarActivity {

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
    public void reportar(View view) throws JSONException {

       Log.i("",contruirJSON().toString());
       Toast.makeText(this,contruirJSON().toString(),Toast.LENGTH_LONG).show();


    };

    public JSONObject contruirJSON() throws JSONException {
        JSONObject tipojson=new JSONObject();
        JSONObject reportejson = new JSONObject();
        reportejson.put("usuario",edNombre.getText().toString());
        reportejson.put("correoUsuario","correo@correo.com");
        tipojson.put("idTipo",(spSolicitud.getSelectedItemPosition()+1));
        reportejson.put("tipo",tipojson);
        reportejson.put("bloque",edBloque.getText().toString());
        reportejson.put("aula",edAula.getText().toString());
        reportejson.put("descripcion",edDescripcion.getText().toString());

    return reportejson;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reportar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
