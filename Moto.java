/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1epii;

/**
 *
 * @author Nunañi
 */
public class Moto {
    private String modelo;
    private int cilindrada;
    private int costeCompra;
    private String matricula;

    public Moto(String modelo, int cilindrada, int costeCompra, String matricula) {
        this.modelo = modelo;
        this.cilindrada = cilindrada;
        this.costeCompra = costeCompra;
        this.matricula = matricula;
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
    
    
}
