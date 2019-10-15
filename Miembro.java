/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1epii;

import java.util.Vector;

/**
 *
 * @author Nunañi
 */
public class Miembro {
    
    static int NUMERO_SOCIO = 0;

    private int numeroSocio;
    private String nombre;
    private int numeroMotos;
    private int importeCompra;
    private Vector<Moto> motos;

    public Miembro(String nombre) {
        this.nombre = nombre;
        this.numeroMotos = 0;
        this.importeCompra = 0;
        this.motos = new Vector<>();
        setNumeroSocio();
    }

    public Miembro(int numeroSocio, String nombre) {
        this.numeroSocio = numeroSocio;
        this.nombre = nombre;
        this.numeroMotos = 0; //YA IRE INCREMENTANDO A MEDIDA QUE LEA LAS MOTOS CORRESPONDIENTES AL MIEMBRO
        this.importeCompra = 0; //MISMA SITUACION
        this.motos = new Vector<>();
    }

    
    public int getNumeroSocio() {
        return numeroSocio;
    }

    private void setNumeroSocio() {
        NUMERO_SOCIO = NUMERO_SOCIO + 1;
        this.numeroSocio = NUMERO_SOCIO;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumeroMotos() {
        return numeroMotos;
    }

    public int getImporteCompra() {
        return importeCompra;
    }

    public Vector<Moto> getMotos() {
        return motos;
    }

    public void addMoto(Moto moto) {
        motos.add(moto);
        numeroMotos = numeroMotos + 1;
        importeCompra = importeCompra + moto.getCosteCompra();
    }

    public void deleteMoto(Moto moto) {
        motos.remove(moto);
        numeroMotos = numeroMotos - 1;
        importeCompra = importeCompra - moto.getCosteCompra();    }

    public void ActualizarNumeroSocio(int numeroSocios) {
        NUMERO_SOCIO = numeroSocios;
    }

    boolean comprobarLimiteCosteCompra(int costeCompra) {
        boolean sobrepasa = false;
        if(this.importeCompra + costeCompra > 6000)
            sobrepasa = true;
        
        return sobrepasa;
    }
   
}
