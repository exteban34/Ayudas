package co.edu.udea.drai.ayudasdrai.co.edu.udea.drai.auidasdrai.model;

/**
 * Created by Programacion on 11/06/2015.
 */
public class TipoReporte {
    int id;
    String nombeTipo;

    public TipoReporte() {
    }

    public TipoReporte(int id) {
        this.id = id;
    }

    public TipoReporte(String nombeTipo) {
        this.nombeTipo = nombeTipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombeTipo() {
        return nombeTipo;
    }

    public void setNombeTipo(String nombeTipo) {
        this.nombeTipo = nombeTipo;
    }
}
