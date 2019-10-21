/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2;

import java.util.Vector;

/**
 *
 * @author Yannick Villaescusa
 */
public class Miembro {
    
    static int NUMERO_SOCIO = 0;

    private int numeroSocio;
    private String nombre;
    private int numeroMotos;
    private int importeCompra;
    private Vector<Moto> motos;
    
    /**
     * 
     * @param nombre string que corresponde al nombre del nuevo miembro
     */
    public Miembro(String nombre) {
        this.nombre = nombre;
        this.numeroMotos = 0;
        this.importeCompra = 0;
        this.motos = new Vector<>();
        setNumeroSocio();
    }
    
    /**
     * 
     * @param numeroSocio identificador del socio registrado
     * @param nombre string que corresponde al nombre del nuevo miembro
     */
    public Miembro(int numeroSocio, String nombre) {
        this.numeroSocio = numeroSocio;
        this.nombre = nombre;
        this.numeroMotos = 0; //YA IRE INCREMENTANDO A MEDIDA QUE LEA LAS MOTOS CORRESPONDIENTES AL MIEMBRO
        this.importeCompra = 0; //MISMA SITUACION
        this.motos = new Vector<>();
    }

    /**
     * 
     * devuelve el identificador correspondiente al socio
     */
    public int getNumeroSocio() {
        return numeroSocio;
    }

    /**
     * Metodo que establece el identificador del socio
     */
    private void setNumeroSocio() {
        NUMERO_SOCIO = NUMERO_SOCIO + 1;
        this.numeroSocio = NUMERO_SOCIO;
    }
    
    /**
     * 
     * devuelve el nombre del socio
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * 
     * devuelve el numero de motos del socio
     */
    public int getNumeroMotos() {
        return numeroMotos;
    }
    
    /**
     * 
     * devuelve el precio de compra total de las motos en propiedad del socio
     */
    public int getImporteCompra() {
        return importeCompra;
    }
    
    /**
     * 
     * devuelve un vector con todas las motos del socio
     */
    public Vector<Moto> getMotos() {
        return motos;
    }
    
    /**
     * 
     * @param moto nueva moto en propiedad del socio
     */
    public void addMoto(Moto moto) {
        motos.add(moto);
        numeroMotos = numeroMotos + 1;
        importeCompra = importeCompra + moto.getCosteCompra();
    }
    
    /**
     * 
     * @param moto moto que ha dejado de estar en propiedad del socio
     */
    public void deleteMoto(Moto moto) {
        motos.remove(moto);
        numeroMotos = numeroMotos - 1;
        importeCompra = importeCompra - moto.getCosteCompra();
    }
    
    /**
     * 
     * @param costeCompra coste de compra de una moto
     * @return TRUE si el hecho de añadir el precio de la moto al importe de compra total del usuario
     *          provocaria que superase el limite de compra de la asociacion
     *          FALSE si no lo superaria
     */
    public boolean comprobarLimiteCosteCompra(int costeCompra) {
        boolean sobrepasa = false;
        if(this.importeCompra + costeCompra > Asociacion.MAXIMO_IMPORTE_COMPRA)
            sobrepasa = true;
        
        return sobrepasa;
    }
}
