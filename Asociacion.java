/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Noah Villaescusa
 */
public class Asociacion {
    
    static int MAXIMO_IMPORTE_COMPRA = 6000;
    
    private Vector<Moto> motos;
    private Vector<Miembro> miembros;
    private Vector<Cesion> cesiones;
    
    /**
     * Constructor vacio de la clase Asociacion
     */
    public Asociacion() {
        this.motos = new Vector<>();
        this.miembros = new Vector<>();
        this.cesiones = new Vector<>();
    }
    
    /**
     * 
     * @param miembro miembro a añadir a la lista de miembros de la asociacion
     */
    public void AñadirMiembro(Miembro miembro) {
        miembros.add(miembro);
    }
    
    /**
     * 
     * @param moto moto a añadir a la lista de motos de la asociacion
     */
    public void AñadirMoto(Moto moto) {
        motos.add(moto);
    }
    
    /**
     * 
     * @param cesion cesion a añadir a la lista de cesiones de la asociacion
     */
    public void AñadirCesion(Cesion cesion) {
        cesiones.add(cesion);
    }
    
    /**
     * 
     * devuelve la lista de motos asociada a la asociacion
     */
    public Vector<Moto> getMotos() {
        return motos;
    }
    
    /**
     * 
     * devuelve la lista de miembros asociada a la asociacion
     */
    public Vector<Miembro> getMiembros() {
        return miembros;
    }
    
    /**
     * 
     * devuelve la lista de cesiones asociada a la asociacion
     */
    public Vector<Cesion> getCesiones() {
        return cesiones;
    }
    
    /**
     * 
     * @param MAXIMO_IMPORTE_COMPRA nuevo limite de compra total maximo establecido por el usuario
     */
    public static void setLimiteMaximoImporteCompra(int MAXIMO_IMPORTE_COMPRA) {
        Asociacion.MAXIMO_IMPORTE_COMPRA = MAXIMO_IMPORTE_COMPRA;
    }
    
    /**
     * Metodo para cargar datos de la asociacion desde un archivo de texto
     */
    public void CargarDatos(){
        File archivo = new File("./src/P2/Salida.txt");
        String nombre, matricula, modelo, fecha, matriculaCesion, cadena;
        int idMiembro,idCedente, idCesionario, idCesion;
        int cilindrada, importeMoto, otrosGastos;
        
        try {
            Scanner parser = new Scanner(archivo);
            while(parser.hasNextLine()){
                cadena = parser.nextLine(); 
                
                String[] tokens = cadena.split("\"");
                Miembro miembro = null;
                
                if(tokens[0].contains("Max Importe:")) {
                    Asociacion.MAXIMO_IMPORTE_COMPRA = Integer.parseInt(tokens[1]);                
                }
                if(tokens[0].contains("ID miembro")) {
                    idMiembro = Integer.parseInt(tokens[1]);
                    nombre = tokens[3];
                    miembro = new Miembro(idMiembro, nombre);
                    this.AñadirMiembro(miembro);
                    Miembro.NUMERO_SOCIO = miembros.size();
                } else if(tokens[0].contains("Matricula")) {
                        Moto moto;
                        matricula = tokens[1];
                        modelo = tokens[3];
                        cilindrada = Integer.parseInt(tokens[5]);
                        importeMoto = Integer.parseInt(tokens[7]);
                        otrosGastos = Integer.parseInt(tokens[9]);
                        moto = new Moto(modelo, cilindrada, importeMoto, matricula, otrosGastos);
                        this.AñadirMoto(moto);
                        getMiembros().lastElement().addMoto(moto);
                } else if(tokens[0].contains("ID Cesion")) {
                        Cesion cesion;
                        idCesion = Integer.parseInt(tokens[1]);
                        fecha = tokens[3];
                        idCedente = Integer.parseInt(tokens[5]);
                        idCesionario = Integer.parseInt(tokens[7]);
                        matriculaCesion = tokens[9];
                        cesion = new Cesion(idCesion, fecha, EncontrarMiembro(idCedente), EncontrarMiembro(idCesionario), EncontrarMoto(matriculaCesion));
                        this.AñadirCesion(cesion);
                        Cesion.NUMERO_CESION = cesiones.size();
                }
            }
         } catch (FileNotFoundException ex) {
            Logger.getLogger(Asociacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param IDdueño id del socio del cual queremos obtener los datos
     * @return socio al que pertenece la id correspondiente
     */
    public Miembro EncontrarMiembro(int IDdueño) {
        Vector<Miembro> miembros = this.getMiembros();
        Miembro dueño = null;
        for(int i = 0; i < miembros.size(); i++){
            if(IDdueño == miembros.get(i).getNumeroSocio()) {
                dueño = miembros.get(i);
            }
        } 
        return dueño;
    }
    
    /**
     * 
     * @param miembro miembro que ha sido desvinculado de la asociacion
     */
    public void EliminarMiembro(Miembro miembro) {
        miembros.remove(miembro);
    }
    
    /**
     * 
     * @param matricula matricula de la moto de la cual queremos obtener los datos
     * @return moto a la que pertenece la matricula correspondiente
     */
    public Moto EncontrarMoto(String matricula) {
        Vector<Moto> motos = this.getMotos();
        Moto moto = null;

        for(int i = 0; i < motos.size(); i++){
            if(matricula.equals(motos.get(i).getMatricula())) {
                moto = motos.get(i);
            }
        } 
        return moto;
    }
    
    /**
     * 
     * @param moto moto que ha sido desvinculada de la asociacion
     */
    public void EliminarMoto(Moto moto) {
        motos.remove(moto);
    }
    
    /**
     * 
     * @param idCesion id de la cesion de la cual queremos obtener los datos
     * @return datos de la cesion
     */
    public Cesion EncontrarCesion(int idCesion){
        Vector<Cesion> cesiones = this.getCesiones();
        Cesion cesion = null;
        for(int i = 0; i < cesiones.size(); i++){
            if(idCesion == cesiones.get(i).getIdCesion()) {
                cesion = cesiones.get(i);
            }
        } 
        return cesion;
    }
}
