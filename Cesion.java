/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1epii;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Nunañi
 */
public class Cesion {
    static int NUMERO_CESION = 0;
 
    private int idCesion;
    private Moto motoImplicada;
    private Miembro cedente;
    private Miembro cesionario;
    private String fecha;

    public Cesion(Moto motoImplicada, Miembro cedente, Miembro cesionario) {
        this.motoImplicada = motoImplicada;
        this.cedente = cedente;
        this.cesionario = cesionario;
        setIdCesion();
        setFecha();
        
    }

    Cesion(int idCesion, String fecha, Miembro cedente, Miembro cesionario, Moto motoImplicada) {
        this.idCesion = idCesion;
        this.fecha = fecha;
        this.cedente = cedente;
        this.cesionario = cesionario;
        this.motoImplicada = motoImplicada;
        
    }

    public int getIdCesion() {
        return idCesion;
    }

    private void setIdCesion() {
        NUMERO_CESION = NUMERO_CESION + 1;
        this.idCesion = NUMERO_CESION;
    }

    public Moto getMotoImplicada() {
        return motoImplicada;
    }

    public Miembro getCedente() {
        return cedente;
    }

    public Miembro getCesionario() {
        return cesionario;
    }

    public String getFecha() {
        return fecha;
    }

    private void setFecha() {
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        
        this.fecha = date.format(now);
    }
    
    
}
