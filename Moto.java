/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2;

/**
 *
 * @author Nunañi
 */
public class Moto {
    private String modelo;
    private int cilindrada;
    private int costeCompra;
    private String matricula;
    private int otrosGastos;

    public Moto(String modelo, int cilindrada, int costeCompra, String matricula, int otrosGastos) {
        this.modelo = modelo;
        this.cilindrada = cilindrada;
        this.costeCompra = costeCompra;
        this.matricula = matricula;
        this.otrosGastos = otrosGastos;
    }

    public String getModelo() {
        return modelo;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public int getCosteCompra() {
        return costeCompra;
    }

    public String getMatricula() {
        return matricula;
    }

    public int getOtrosGastos() {
        return otrosGastos;
    }

    void añadirOtrosGastos(int otrosGastos) {
        this.otrosGastos = this.otrosGastos + otrosGastos;
    }
    
    
}
