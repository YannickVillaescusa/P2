/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1epii;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nunañi
 */
public class Asociacion {
    private Vector<Moto> motos;
    private Vector<Miembro> miembros;
    private Vector<Cesion> cesiones;

    public Asociacion() {
        this.motos = new Vector<>();
        this.miembros = new Vector<>();
        this.cesiones = new Vector<>();

    }

    public void AñadirMiembro(Miembro miembro) {
        miembros.add(miembro);
    }

    public void AñadirMoto(Moto moto) {
        motos.add(moto);
    }
    
    public void AñadirCesion(Cesion cesion) {
        cesiones.add(cesion);
    }

    public Vector<Moto> getMotos() {
        return motos;
    }

    public Vector<Miembro> getMiembros() {
        return miembros;
    }

    public Vector<Cesion> getCesiones() {
        return cesiones;
    }
    
    public void CargarDatos(){
        File archivo = new File("./src/P2/Salida.txt");
        String cadena;
        
        int idMiembro;
        String nombre;
        
        String matricula, modelo;
        int cilindrada, importeMoto;
        
        int idCesion;
        String fecha, matriculaCesion;
        int idCedente, idCesionario;
        

        try {
            Scanner parser = new Scanner(archivo);
            while(parser.hasNextLine()){
                cadena = parser.nextLine(); 
                
                String[] tokens = cadena.split("\"");
                Miembro miembro = null;

                if(tokens[0].contains("ID miembro")){
                    idMiembro = Integer.parseInt(tokens[1]);
                    nombre = tokens[3];
                    miembro = new Miembro(idMiembro, nombre);
                    this.AñadirMiembro(miembro);
                    miembro.ActualizarNumeroSocio(miembros.size());
                }else if(tokens[0].contains("Matricula")){
                        Moto moto;
                        matricula = tokens[1];
                        modelo = tokens[3];
                        cilindrada = Integer.parseInt(tokens[5]);
                        importeMoto = Integer.parseInt(tokens[7]);
                        moto = new Moto(modelo, cilindrada, importeMoto, matricula);
                        this.AñadirMoto(moto);
                        getMiembros().lastElement().addMoto(moto);
                }else if(tokens[0].contains("ID Cesion")){
                        Cesion cesion;
                        idCesion = Integer.parseInt(tokens[1]);
                        fecha = tokens[3];
                        idCedente = Integer.parseInt(tokens[5]);
                        idCesionario = Integer.parseInt(tokens[7]);
                        matriculaCesion = tokens[9];
                        cesion = new Cesion(idCesion, fecha, EncontrarMiembro(idCedente), EncontrarMiembro(idCesionario), EncontrarMoto(matriculaCesion));
                        this.AñadirCesion(cesion);
                }
            }
 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Asociacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public Miembro EncontrarMiembro(int IDdueño) {
        Vector<Miembro> miembros = this.getMiembros();
        Miembro dueño = null;
        for(int i = 0; i < miembros.size(); i++){
            if(IDdueño == miembros.get(i).getNumeroSocio())
                dueño = miembros.get(i);
        } 
        return dueño;
    }

    public Moto EncontrarMoto(String matricula) {
        Vector<Moto> motos = this.getMotos();
        Moto moto = null;

        for(int i = 0; i < motos.size(); i++){
            if(matricula.equals(motos.get(i).getMatricula()))
                moto = motos.get(i);
        } 
        return moto;
    }
    
    public Cesion EncontrarCesion(int idCesion){
        Vector<Cesion> cesiones = this.getCesiones();
        Cesion cesion = null;
        for(int i = 0; i < cesiones.size(); i++){
            if(idCesion == cesiones.get(i).getIdCesion())
                cesion = cesiones.get(i);
        } 
        return cesion;
    }
    
    
}
