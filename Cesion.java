/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Yannick Villaescusa
 */
public class Cesion {
    static int NUMERO_CESION = 0;
 
    private int idCesion;
    private Moto motoImplicada;
    private Miembro cedente;
    private Miembro cesionario;
    private String fecha;
    
    /**
     * 
     * @param motoImplicada moto cedida  
     * @param cedente socio propietario de la moto 
     * @param cesionario socio receptor de la moto
     */
    public Cesion(Moto motoImplicada, Miembro cedente, Miembro cesionario) {
        this.motoImplicada = motoImplicada;
        this.cedente = cedente;
        this.cesionario = cesionario;
        setIdCesion();
        setFecha();   
    }
    
    /**
     * 
     * @param idCesion identificador asociado a la transaccion
     * @param fecha fecha perteneciente al registro de la cesion
     * @param cedente socio propietario de la moto 
     * @param cesionario socio receptor de la moto
     * @param motoImplicada moto cedida  
     */
    public Cesion(int idCesion, String fecha, Miembro cedente, Miembro cesionario, Moto motoImplicada) {
        this.idCesion = idCesion;
        this.fecha = fecha;
        this.cedente = cedente;
        this.cesionario = cesionario;
        this.motoImplicada = motoImplicada;
        
    }
    
    /**
     * 
     * @return devuelve el identificador de la cesion
     */
    public int getIdCesion() {
        return idCesion;
    }

    /**
     * Metodo que establece el identificador de la cesion
     */
    private void setIdCesion() {
        NUMERO_CESION = NUMERO_CESION + 1;
        this.idCesion = NUMERO_CESION;
    }
    
    /**
     * 
     * @return devuelve la moto implicada en la cesion
     */
    public Moto getMotoImplicada() {
        return motoImplicada;
    }
    
    /**
     * 
     * @return devuelve el miembro que cede la moto
     */
    public Miembro getCedente() {
        return cedente;
    }
    
    /**
     * 
     * @return devuelve el miembro que recibe la moto
     */
    public Miembro getCesionario() {
        return cesionario;
    }
    
    /**
     * 
     * @return devuelve la fecha de registro de la cesion
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Metodo que establece la fecha de la cesion tomando la fecha actual del sistema
     */
    private void setFecha() {
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        
        this.fecha = date.format(now);
    }   
}
