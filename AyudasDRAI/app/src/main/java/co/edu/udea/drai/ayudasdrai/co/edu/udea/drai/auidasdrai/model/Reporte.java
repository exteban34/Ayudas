package co.edu.udea.drai.ayudasdrai.co.edu.udea.drai.auidasdrai.model;

import java.sql.Date;

/**
 * Created by Programacion on 11/06/2015.
 */
public class Reporte {
    int id;
    String usuario;
    String correoUsuario;
    Date fecha;
    String bloque;
    String aula;
    String descripcion;
    TipoReporte tipo;

    public Reporte() {
    }

    public Reporte(int id, String usuario, String correoUsuario, Date fecha, String bloque, String aula, String descripcion, TipoReporte tipo) {
        this.id = id;
        this.usuario = usuario;
        this.correoUsuario = correoUsuario;
        this.fecha = fecha;
        this.bloque = bloque;
        this.aula = aula;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public Reporte(String usuario, String correoUsuario, Date fecha, String bloque, String aula, String descripcion, TipoReporte tipo) {
        this.usuario = usuario;
        this.correoUsuario = correoUsuario;
        this.fecha = fecha;
        this.bloque = bloque;
        this.aula = aula;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoReporte getTipo() {
        return tipo;
    }

    public void setTipo(TipoReporte tipo) {
        this.tipo = tipo;
    }
}
