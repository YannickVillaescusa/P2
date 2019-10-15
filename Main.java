/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1epii;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nunañi
 */
public class Main {
    private Asociacion asociacion;

    public Main() { 
        asociacion = new Asociacion();
        
        //CARGA DATOS CON FICHERO SALIDA.TXT
        asociacion.CargarDatos();
        System.out.println("Asociación cultural de amigos de las motos antiguas ");
        MostrarMenu();
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            int numero;
            do {
               String cadena = scanner.nextLine();
               numero = Integer.parseInt(cadena);

               switch(numero){
                    case 1:
                        RegistrarMiembro();
                        MostrarMenu();
                        break;
                    case 2:
                        RegistrarMotocicleta();
                        MostrarMenu();
                        break;
                    case 3:
                        RegistrarCesion();
                        MostrarMenu();
                        break;
                    case 4:
                        ListarMiembrosMotosEnPosesion();
                        MostrarMenu();
                        break;
                    case 5:
                        ListarMotosYMiembros();
                        MostrarMenu();
                        break;
                    case 6:
                        MostrarCesionesRealizadas();
                        MostrarMenu();
                        break;
                    case 7:
                        SalirPrograma();
                        break;
                    default:
                        System.out.println("Opcion incorrecta. Introduce la opcion: ");
                        numero = 7;
                        break;
                } 
            } while (numero > 0 && numero <= 7);
        } catch (Exception e) {
            System.out.println("Error: No has introducido un numero");
        }  
    }
    
    private void MostrarMenu() {
        System.out.println("1. Registrar un nuevo miembro ");
        System.out.println("2. Registrar una nueva motocicleta  ");
        System.out.println("3. Registrar una cesión  ");
        System.out.println("4. Listar en pantalla los miembros con motos en posesión  ");
        System.out.println("5. Listar todas las motos  ");
        System.out.println("6. Mostrar las cesiones realizadas  ");
        System.out.println("7. Salir del programa\n   ");
        
        System.out.println("Opcion: ");
    }

    private void RegistrarMiembro() {
        Miembro miembro;
        String nombre;
        
        System.out.println("\nIntroduce el nombre del nuevo miembro: ");
        Scanner scanner = new Scanner(System.in);
        
        do {
            nombre = scanner.nextLine();
        } while (ComprobarCampoVacio(nombre));
 
        miembro = new Miembro(nombre);       
        asociacion.AñadirMiembro(miembro);
        
        System.out.println("\nMiembro añadido correctamente");
        PausarOutput();
    }

    private void RegistrarMotocicleta() {
        Moto moto = null;
        String modelo, matricula;
        String cadena;
        boolean matriculaNoValida = false;
        boolean matriculaNoUnica = false;
        int cilindrada = 0;
        
        System.out.println("\nIntroduce los datos de la motocicleta: ");
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nModelo: ");
        
        do {
            modelo = scanner.nextLine();
        } while (ComprobarCampoVacio(modelo));
        
        System.out.println("\nCilindrada: ");
        
        do {
            cadena = scanner.nextLine();
        } while (ComprobarCampoVacio(cadena));
                        
        cilindrada = Integer.parseInt(cadena);

        System.out.println("\nCoste de compra: ");
        
        do {
            cadena = scanner.nextLine();
        } while (ComprobarCampoVacio(cadena));
        
        int costeCompra = Integer.parseInt(cadena);
        
        System.out.println("\nMatricula: ");
        
        do {
            matricula = scanner.nextLine();
            matriculaNoValida = ComprobarFormatoMatricula(matricula);
            
            if(matriculaNoValida == true)
                System.out.println("Error: Formato incorrecto de matricula. Formato:(0000xxx)\nIntroduzca la matricula:");
            else
                matriculaNoUnica = ComprobarMatriculaUnica(matricula);
            
            if(matriculaNoUnica == true)
                System.out.println("Error: Matricula perteneciente a otra motocicleta.\nIntroduzca la contraseña: ");
        } while (ComprobarCampoVacio(matricula) || matriculaNoValida == true || matriculaNoUnica == true);
        
        System.out.println("\nID del dueño de la motocicleta: ");
        MostrarMiembros();
        
        do {
            cadena = scanner.nextLine();
        } while (ComprobarCampoVacio(cadena));
        
        int IDdueño = Integer.parseInt(cadena);

        Miembro dueño = asociacion.EncontrarMiembro(IDdueño); 
        
        if(dueño.getImporteCompra() + costeCompra > 6000)
            System.out.println("Error: El miembro elegido supera el importe permitido;\n\tCancelando operacion...");
        else{
            moto = new Moto(modelo, cilindrada, costeCompra, matricula);
        
            dueño.addMoto(moto);

            asociacion.AñadirMoto(moto);
            System.out.println("\nMoto añadida correctamente");
        }
        PausarOutput();
    }

    private void RegistrarCesion() {
        Cesion cesion;
        Moto motoImplicada = null;
        Miembro cedente = null;
        
        int idCedente, idCesionario;
        String matricula;
        
        System.out.println("\nIntroduce los datos de la cesion: ");
        Scanner scanner = new Scanner(System.in);   

        System.out.println("\nID del dueño de la moto: ");
        MostrarMiembros();
        
        do {          
            idCedente = Integer.parseInt(scanner.nextLine());
            cedente = asociacion.EncontrarMiembro(idCedente);
            
            if(cedente == null)
                System.out.println("Error: Identificacion incorrecta, vuelve a introducir la ID");
            else if(cedente.getNumeroMotos() == 0)
                System.out.println("Error: Este miembro no tiene motos en posesion, vuelve a introducir la ID");
            
        } while (cedente == null || cedente.getNumeroMotos() == 0);

        
        
        System.out.println("\nMatricula de la moto implicada en la cesion: ");
        MostrarMotos(cedente);
        
        do {            
            matricula = scanner.nextLine();
            motoImplicada = asociacion.EncontrarMoto(matricula);
            if(motoImplicada == null)
                System.out.println("Error: Matricula incorrecta, vuelve a introducir la matricula: ");
        } while (motoImplicada == null);     

        System.out.println("\nID del miembro al cual se le cede la moto: ");
        MostrarMiembros();

        do {            
            idCesionario = Integer.parseInt(scanner.nextLine());
            
            if(idCesionario == idCedente)
                System.out.println("Error: El cedente y el cesionario no pueden ser la misma persona,"
                                + "vuelve a introducir la ID del cesionario: ");
        } while (idCesionario == idCedente);

        Miembro cesionario = asociacion.EncontrarMiembro(idCesionario);   

        cesion = new Cesion(motoImplicada, cedente, cesionario);
        
        cesionario.addMoto(motoImplicada);
        cedente.deleteMoto(motoImplicada);
        asociacion.AñadirCesion(cesion);
        System.out.println("\nCesion realizada correctamente");
        
        PausarOutput();
    }

    private void ListarMiembrosMotosEnPosesion() {
        Vector<Miembro> miembros = asociacion.getMiembros();
        
        for(int i = 0; i < miembros.size(); i++){
            Miembro miembro = miembros.get(i);
            MostrarMiembro(miembro);

            if(miembros.get(i).getNumeroMotos() > 0)
                MostrarMotos(miembro);
            
            System.out.println("\n");
        }        
        PausarOutput();
    }

    private void ListarMotosYMiembros() {
        Vector<Moto> motos = asociacion.getMotos();
        Vector<Miembro> miembros = asociacion.getMiembros();

        for(int i = 0; i < motos.size(); i++){
            Moto moto = motos.get(i);
            MostrarMoto(moto);
            boolean encontrada = false;
            int contador = 0;

            do {                
                Miembro miembro = miembros.get(contador);
                if(!miembro.getMotos().isEmpty() && miembro.getMotos().contains(moto)){
                    MostrarMiembro(miembro);
                    System.out.println("\n");
                    encontrada = true;
                }
                contador++;
            } while (encontrada != true && contador <= miembros.size());    
        }
        PausarOutput();
    }

    private void MostrarCesionesRealizadas() {
        MostrarCesiones();      
        PausarOutput();
    }

    private void SalirPrograma() {
        String nombre;
        File archivo;
        BufferedWriter bw;
        
        System.out.println("Introduce el nombre del fichero donde se guardaran todos los datos:");
        
        Scanner sc = new Scanner(System.in);
        nombre = sc.nextLine();
        
        archivo = new File(nombre + ".txt");
        
        try {
            bw = new BufferedWriter(new FileWriter(archivo));
            EscibirFichero(bw);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.exit(0);
    }

    private void PausarOutput() {
        System.out.println("Pulsa cualquier tecla para continuar...");
          new java.util.Scanner(System.in).nextLine();
    }

    private void MostrarMiembros() {
        Vector<Miembro> miembros = asociacion.getMiembros();
        
        for(int i = 0; i < miembros.size(); i++){
            Miembro miembro = miembros.get(i);
            MostrarMiembro(miembro);
        }
    }
    
    //NO ES NECESARIA YA QUE VOLCAMOS MIEMBRO A MIEMBRO CON SUS RESPECTIVAS MOTOS
    /*private void VolcarMiembros(BufferedWriter bw) {
        Vector<Miembro> miembros = asociacion.getMiembros();
        
        for(int i = 0; i < miembros.size(); i++){
            Miembro miembro = miembros.get(i);
            VolcarMiembro(miembro, bw);
        }
    }*/
    
    private void MostrarMiembro(Miembro miembro){
        System.out.println("ID miembro: " + miembro.getNumeroSocio() + "\tNombre: " + miembro.getNombre() +  "\tNumero de motos: " + miembro.getNumeroMotos() 
                            + "\tImporte de compra: " + miembro.getImporteCompra() + " €");
    }
    
    private void VolcarMiembro(Miembro miembro, BufferedWriter bw){
        try {
            bw.write("ID miembro: \"" + miembro.getNumeroSocio() + "\"\tNombre: \"" + miembro.getNombre() +  "\"\tNumero de motos: \"" + miembro.getNumeroMotos()
                    + "\"\tImporte de compra: \"" + miembro.getImporteCompra() + "\" €");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void MostrarMotos(Miembro dueño) {
        for(int i = 0; i < dueño.getMotos().size(); i++){
            Moto moto = dueño.getMotos().get(i);
            MostrarMoto(moto);
        }
    }
    
    private void VolcarMotos(Miembro dueño, BufferedWriter bw) {
        for(int i = 0; i < dueño.getMotos().size(); i++){
            Moto moto = dueño.getMotos().get(i);
            VolcarMoto(moto, bw);
            try {
                bw.newLine();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void MostrarMoto(Moto moto){
        System.out.println("Matricula: " + moto.getMatricula() + "\tModelo: " + moto.getModelo() + "\tCilindrada: " 
                                + moto.getCilindrada() + "\tCoste de compra: " + moto.getCosteCompra() + " €");
    }
    
    private void VolcarMoto(Moto moto, BufferedWriter bw){
        try {
            bw.write("Matricula: \"" + moto.getMatricula() + "\"\tModelo: \"" + moto.getModelo() + "\"\tCilindrada: \""
                    + moto.getCilindrada() + "\"\tCoste de compra: \"" + moto.getCosteCompra() + "\" €");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    private void MostrarCesiones() {
        Vector<Cesion> cesiones = asociacion.getCesiones();
        
        for(int i = 0; i < cesiones.size(); i++){
            Cesion cesion = cesiones.get(i);
            MostrarCesion(cesion);
        }
        
        String cadena;
        Cesion cesion = null;
        
        if(cesiones.size() > 0){
            do {            
                System.out.println("\nSi desea mas detalles de una cesion porfavor introduzca la ID de la cesion,"
                            + " Si no es asi introduzca 'N': ");
                Scanner sc = new Scanner(System.in);
                cadena = sc.nextLine();
            
                if(!"N".equals(cadena))
                cesion = asociacion.EncontrarCesion(Integer.parseInt(cadena));
        
                if(!"N".equals(cadena) && cesion != null)
                    MostrarCesionDetalle(cesion); 
            
            } while (!"N".equals(cadena));
        }
        
    }

    private void MostrarCesion(Cesion cesion) {
        System.out.println("ID Cesion:" + cesion.getIdCesion() + "\tFecha: " + cesion.getFecha() + "\tMiembro cedente: " + cesion.getCedente().getNombre() + "\tMiembro cesionario: " + cesion.getCesionario().getNombre()
                            + "\tMoto implicada: " + cesion.getMotoImplicada().getModelo());
    }
    
    

    private void MostrarCesionDetalle(Cesion cesion) {
        System.out.println("\nID Cesion:" + cesion.getIdCesion() +  " Fecha: " + cesion.getFecha());
        System.out.println("\tMiembro cedente: ");
        MostrarMiembro(cesion.getCedente());
        System.out.println("\tMiembro cesionario: ");
        MostrarMiembro(cesion.getCesionario());
        System.out.println("\tMoto implicada: ");
        MostrarMoto(cesion.getMotoImplicada());
    }
    
    private void VolcarCesiones(BufferedWriter bw){
        Vector<Cesion> cesiones = asociacion.getCesiones();
        Cesion cesion;
        for(int i = 0; i < cesiones.size(); i++){
            cesion = cesiones.get(i);
            VolcarCesion(cesion, bw);
            try {
                bw.newLine();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void VolcarCesion(Cesion cesion, BufferedWriter bw) {
        try {
            bw.write("ID Cesion: \"" + cesion.getIdCesion() +  "\"\tFecha: \"" + cesion.getFecha() + "\"");
            bw.write("\tMiembro cedente: \"" + cesion.getCedente().getNumeroSocio() + "\"");
            bw.write("\tMiembro cesionario: \"" + cesion.getCesionario().getNumeroSocio() + "\"");
            bw.write("\tMoto implicada: \"" + cesion.getMotoImplicada().getMatricula() + "\"");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private boolean ComprobarCampoVacio(String cadena){
        boolean vacio = false;
        if(cadena.isEmpty()){
            System.out.println("Error: Campo vacio, porfavor introduzca el campo correctamente: ");
            vacio = true;    
        }
        return vacio;
    }

    private void EscibirFichero(BufferedWriter bw) {
        Vector<Miembro> miembros = asociacion.getMiembros();

        for(int i = 0; i < miembros.size(); i++){
            Miembro miembro = miembros.get(i);
            VolcarMiembro(miembro, bw);
            try {
                bw.newLine();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            if(miembros.get(i).getNumeroMotos() > 0)
                VolcarMotos(miembro, bw);
                

        }        
        
        VolcarCesiones(bw);  
        
    }

    private boolean ComprobarFormatoMatricula(String matricula) {
        boolean matriculaNoValida = false;
        if(matricula.length() != 7){
            matriculaNoValida = true;
        }
        else{
            String num = matricula.substring(0, 4);
            String alfa = matricula.substring(4, 7);
                
            for(int i = 0; i < alfa.length(); i++)
                if(!Character.isAlphabetic(alfa.charAt(i)))
                    matriculaNoValida = true;
                
            if(matriculaNoValida == false){
                for(int j = 0; j < num.length(); j++){
                    try {
                        Integer.parseInt(num);
                    } catch (NumberFormatException e) {
                        matriculaNoValida = true;
                    }
                }
            }
                
        }
        return matriculaNoValida;
    }

    private boolean ComprobarMatriculaUnica(String matricula) {
        Vector<Moto> motos = asociacion.getMotos();
        boolean matriculaNoUnica = false;
        
        for(int i = 0; i < motos.size(); i++){
            Moto moto = motos.get(i);
            
            if(matricula.equals(moto.getMatricula()))
                matriculaNoUnica = true;
        }
        return matriculaNoUnica;
    }
}
