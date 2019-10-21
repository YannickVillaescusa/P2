/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2;

/**
 *
 * @author Yannick Villaescusa
 */
public class Moto {
    private String modelo;
    private int cilindrada;
    private int costeCompra;
    private String matricula;
    private int otrosGastos;
    
    /**
     * 
     * @param modelo modelo de la moto registrada
     * @param cilindrada cilindrada de la moto registrada
     * @param costeCompra coste de compra de la moto registrada
     * @param matricula matricula de la moto registrada
     * @param otrosGastos gastos adicionales asociados a la moto registrada
     */
    public Moto(String modelo, int cilindrada, int costeCompra, String matricula, int otrosGastos) {
        this.modelo = modelo;
        this.cilindrada = cilindrada;
        this.costeCompra = costeCompra;
        this.matricula = matricula;
        this.otrosGastos = otrosGastos;
    }
    
    /**
     * 
     * devuelve el modelo de la moto
     */
    public String getModelo() {
        return modelo;
    }
    
    /**
     * 
     * @return devuelve la cilindrada de la moto
     */
    public int getCilindrada() {
        return cilindrada;
    }
    
    /**
     * 
     * devuelve el coste de compra de la moto
     */
    public int getCosteCompra() {
        return costeCompra;
    }
    
    /**
     * 
     * devuelve la matricula de la moto
     */
    public String getMatricula() {
        return matricula;
    }
    
    /**
     * 
     * devuelve los gastos adicionales asociados a la moto
     */
    public int getOtrosGastos() {
        return otrosGastos;
    }
    
    /**
     * 
     * @param otrosGastos gastos a añadir a los gastos adicionales ya existentes de la moto
     */
    protected void añadirOtrosGastos(int otrosGastos) {
        this.otrosGastos = this.otrosGastos + otrosGastos;
    }
}
