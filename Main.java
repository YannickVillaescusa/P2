/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P2;

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
 * @author Noah Villaescusa
 */
public class Main {
    private Asociacion asociacion;
    
    /**
     * Constructor de la clase Main que interpreta las entradas de texto por consola 
     */
    public Main() { 
        asociacion = new Asociacion();
        asociacion.CargarDatos();
        
        System.out.println("Asociación cultural de amigos de las motos antiguas\n");
        System.out.println("Introduzca el importe maximo del importe de compra permitido: ");
        
        Asociacion.MAXIMO_IMPORTE_COMPRA = Integer.parseInt(new Scanner(System.in).nextLine());
        
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
                        MostrarCesiones();
                        MostrarMenu();
                        break;
                    case 7:
                        IncrementarOtrosGastosMoto();
                        MostrarMenu();
                        break;
                    case 8:
                        EliminarMiembro();
                        MostrarMenu();
                        break;
                    case 9:
                        MostrarMiembrosMasCesiones();
                        MostrarMenu();
                        break;                            
                    case 10:
                        SalirPrograma();
                        break;
                    default:
                        System.out.println("Opcion incorrecta. Introduce la opcion: ");
                        numero = 10;
                        break;
                } 
            } while (numero > 0 && numero <= 10);
        } catch (Exception e) {
            System.out.println("Error: No has introducido un numero");
        }  
    }
    
    /**
     * Metodo encargado de mostrar el menu principal 
     */
    private void MostrarMenu() {
        System.out.println("1. Registrar un nuevo miembro");
        System.out.println("2. Registrar una nueva motocicleta");
        System.out.println("3. Registrar una cesión");
        System.out.println("4. Listar en pantalla los miembros con motos en posesión");
        System.out.println("5. Listar todas las motos");
        System.out.println("6. Mostrar las cesiones realizadas");
        System.out.println("7. Incrementar otros gastos a una moto");
        System.out.println("8. Eliminar un miembro de la asociacion");
        System.out.println("9. Mostrar socio con mas cesiones recibidas");
        System.out.println("10. Salir del programa\n");
        System.out.println("Opcion: ");
    }
    
    /**
     * Metodo encargado de registrar un nuevo miembro
     */
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

    /**
     * Metodo encargado de registrar una nueva motocicleta
     */
    private void RegistrarMotocicleta() {
        Moto moto = null;
        String modelo, matricula;
        String cadena;
        boolean matriculaNoValida = false;
        boolean matriculaNoUnica = false;
        int cilindrada = 0;
        int otrosGastos;

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
            
            if(matriculaNoValida == true) {
                System.out.println("Error: Formato incorrecto de matricula. Formato:(0000xxx)\nIntroduzca la matricula:");
            } else {
                matriculaNoUnica = ComprobarMatriculaUnica(matricula);
            }
            if(matriculaNoUnica == true) {
                System.out.println("Error: Matricula perteneciente a otra motocicleta.\nIntroduzca la contraseña: ");
            }
        } while (ComprobarCampoVacio(matricula) || matriculaNoValida == true || matriculaNoUnica == true);
        
        System.out.println("\nOtros gastos correspondientes a la motocicleta: ");

        do {
            cadena = scanner.nextLine();
        } while (ComprobarCampoVacio(cadena));
        
        otrosGastos = Integer.parseInt(cadena);
        
        System.out.println("\nID del dueño de la motocicleta: ");
        MostrarMiembros();
        
        do {
            cadena = scanner.nextLine();
        } while (ComprobarCampoVacio(cadena));
        
        int IDdueño = Integer.parseInt(cadena);

        Miembro dueño = asociacion.EncontrarMiembro(IDdueño); 
        
        if(dueño.comprobarLimiteCosteCompra(costeCompra)) {
            System.out.println("Error: El miembro elegido supera el importe permitido;\n\tCancelando operacion...");
        } else { 
            moto = new Moto(modelo, cilindrada, costeCompra, matricula, otrosGastos);
        
            dueño.addMoto(moto);

            asociacion.AñadirMoto(moto);
            System.out.println("\nMoto añadida correctamente");
        }
        PausarOutput();
    }
    
    /**
     * Metodo encargado de registrar una nueva cesion
     */
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
            
            if(cedente == null) {
                System.out.println("Error: Identificacion incorrecta, vuelve a introducir la ID");
            } else if(cedente.getNumeroMotos() == 0) {
                System.out.println("Error: Este miembro no tiene motos en posesion, vuelve a introducir la ID");
            }
        } while (cedente == null || cedente.getNumeroMotos() == 0);

        System.out.println("\nMatricula de la moto implicada en la cesion: ");
        MostrarMotos(cedente);
        
        do {            
            matricula = scanner.nextLine();
            motoImplicada = EncontrarMoto(matricula, cedente);
            if(motoImplicada == null) {
                System.out.println("Error: Matricula incorrecta, vuelve a introducir la matricula: ");
            }
        } while (motoImplicada == null);     

        System.out.println("\nID del miembro al cual se le cede la moto: ");
        MostrarMiembros();

        do {            
            idCesionario = Integer.parseInt(scanner.nextLine());
            
            if(idCesionario == idCedente) {
                System.out.println("Error: El cedente y el cesionario no pueden ser la misma persona,"
                                + "vuelve a introducir la ID del cesionario: ");
            }
        } while (idCesionario == idCedente);

        Miembro cesionario = asociacion.EncontrarMiembro(idCesionario);   

        if(cesionario.comprobarLimiteCosteCompra(motoImplicada.getCosteCompra())) {
            System.out.println("Error: El miembro elegido supera el importe permitido;\n\tCancelando operacion...");
        } else {
            cesion = new Cesion(motoImplicada, cedente, cesionario);
        
            cesionario.addMoto(motoImplicada);
            cedente.deleteMoto(motoImplicada);
            asociacion.AñadirCesion(cesion);
            System.out.println("\nCesion realizada correctamente");   
        }
        PausarOutput();
    }
    
    /**
     * 
     * @param cedente socio que ha dejado la asociacion
     * @param motoImplicada motocicleta del socio a ceder
     * @return TRUE si la cesion se ha realizado con exito debido a no superar el limite de compra total del cesionario
     */
    private boolean RegistrarCesion(Miembro cedente, Moto motoImplicada) {
        Cesion cesion;
        
        int idCesionario;       
        boolean cesionRealizada = false;
       
        Scanner scanner = new Scanner(System.in);  
        
        System.out.println("\nID del miembro al cual se le cede la moto: ");
        MostrarMiembros();
  
        idCesionario = Integer.parseInt(scanner.nextLine());

        Miembro cesionario = asociacion.EncontrarMiembro(idCesionario);   

        if(cesionario.comprobarLimiteCosteCompra(motoImplicada.getCosteCompra())){
            cesionRealizada = false;
            System.out.println("Error: El miembro elegido supera el importe permitido;\n\tCancelando operacion...");
        
        } else {
            cesion = new Cesion(motoImplicada, cedente, cesionario);
        
            cesionario.addMoto(motoImplicada);
            cedente.deleteMoto(motoImplicada);
            asociacion.AñadirCesion(cesion);
            System.out.println("\nCesion realizada correctamente");   
            cesionRealizada = true;
        }
        PausarOutput();
        
        return cesionRealizada;
    }
    
    /**
     * Metodo encargado de mostrar las motos pertenecientes a cada miembro
     */
    private void ListarMiembrosMotosEnPosesion() {
        Vector<Miembro> miembros = asociacion.getMiembros();
        
        for(int i = 0; i < miembros.size(); i++){
            Miembro miembro = miembros.get(i);
            MostrarMiembro(miembro);

            if(miembros.get(i).getNumeroMotos() > 0) {
                MostrarMotos(miembro);
            }
            System.out.println("\n");
        }        
        PausarOutput();
    }
    
    /**
     * Metodo encargado de mostrar todas las motos y su correspondiente miembro
     */
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
    
    /**
     * Metodo encargado de incrementar los gastos adicionales de una motocicleta
     */
    private void IncrementarOtrosGastosMoto() {
        int otrosGastos;
        String cadena;
        Moto moto;

        System.out.println("\nID del dueño de la motocicleta: ");
        MostrarMiembros();
        
        Scanner scanner = new Scanner(System.in);

        do {
            cadena = scanner.nextLine();
        } while (ComprobarCampoVacio(cadena));
        
        int IDmiembro = Integer.parseInt(cadena);

        Miembro miembro = asociacion.EncontrarMiembro(IDmiembro);
        
        System.out.println("\nMatricula de la moto implicada en la cesion: ");
        MostrarMotos(miembro);
        
        do {            
            cadena = scanner.nextLine();
            moto = EncontrarMoto(cadena, miembro);
            if(moto == null) {
                System.out.println("Error: Matricula incorrecta, vuelve a introducir la matricula: ");
            }
        } while (moto == null);
        
        System.out.println("\nIntroduce los gastos adicionales de la moto: ");
        
        do {
            cadena = scanner.nextLine();
        } while (ComprobarCampoVacio(cadena));
        
        otrosGastos = Integer.parseInt(cadena);
        
        moto.añadirOtrosGastos(otrosGastos);
        
        System.out.println("\nGastos añadidos correctamente");
        PausarOutput(); 
    }
    
    /**
     * Metodo encargado de eliminar un miembro de la asociacion
     */
    private void EliminarMiembro() {
        String cadena;
        int identificador;
        int numeroMotos;
        boolean cesionRealizada = false;
        
        System.out.println("Introduce el identificador del miembro que deseas eliminar de la asociacion");
        Scanner scanner = new Scanner(System.in);

        MostrarMiembros();
        System.out.println("\nID: ");
        
        do {
            cadena = scanner.nextLine();
        } while (ComprobarCampoVacio(cadena));
        
        identificador = Integer.parseInt(cadena);

        Miembro miembro = asociacion.EncontrarMiembro(identificador);

        if(miembro != null) {
            asociacion.EliminarMiembro(miembro);
        }
        numeroMotos = miembro.getNumeroMotos();
        if(numeroMotos > 0) {
            System.out.println("\nEl miembro eliminado tiene " + numeroMotos + " motos. Se deben ceder todas las motos.");
            System.out.println( "ATENCION: Si la cesion no puede realizarse la moto será eliminada de la asociacion\n");
            
            for(int i = 0; i < numeroMotos; i++){              
                Moto moto = miembro.getMotos().get(0);
                
                MostrarMoto(moto);
                
                cesionRealizada = RegistrarCesion(miembro, moto);
                
                if(!cesionRealizada)
                    asociacion.EliminarMoto(moto);
            }
        }
        
        System.out.println("Se han realizado todas las cesiones");
        PausarOutput();
    }
    
    /**
     * Metodo encargado de interrumpir la ejecucion del programa y volcar los datos en un fichero
     */
    private void SalirPrograma() {
        String nombre;
        File archivo;
        BufferedWriter bw;
        
        System.out.println("Introduce el nombre del fichero donde se guardaran todos los datos:");
        
        Scanner sc = new Scanner(System.in);
        nombre = sc.nextLine();
        
        archivo = new File("./src/P2/" + nombre + ".txt");
        
        try {
            bw = new BufferedWriter(new FileWriter(archivo));
            EscribirFichero(bw);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.exit(0);
    }
    
    /**
     * Metodo encargado de pausar la consola y generar espaciado 
     */
    private void PausarOutput() {
        System.out.println("Pulsa cualquier tecla para continuar...");
          new java.util.Scanner(System.in).nextLine();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    
    /**
     * Metodo encargado de mostrar todos los miembros de la asociacion
     */
    private void MostrarMiembros() {
        Vector<Miembro> miembros = asociacion.getMiembros();
        
        for(int i = 0; i < miembros.size(); i++){
            Miembro miembro = miembros.get(i);
            MostrarMiembro(miembro);
        }
    }
    
    /**
     * 
     * @param miembro miembro del que se mostrara toda la informacion
     */
    private void MostrarMiembro(Miembro miembro){
        System.out.println("ID miembro: " + miembro.getNumeroSocio() + "\tNombre: " + miembro.getNombre() +  "\tNumero de motos: " + miembro.getNumeroMotos() 
                            + "\tImporte de compra: " + miembro.getImporteCompra() + " €\n");
    }
    
    /**
     * 
     * @param miembro miembro del que se mostrara toda la informacion
     * @param bw buffer para escritura en salida por fichero
     */
    private void VolcarMiembro(Miembro miembro, BufferedWriter bw){
        try {
            bw.write("ID miembro: \"" + miembro.getNumeroSocio() + "\"\tNombre: \"" + miembro.getNombre() +  "\"\tNumero de motos: \"" + miembro.getNumeroMotos()
                    + "\"\tImporte de compra: \"" + miembro.getImporteCompra() + "\" €");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param dueño miembro del que se muestran todas las motos en propiedad
     */
    private void MostrarMotos(Miembro dueño) {
        for(int i = 0; i < dueño.getMotos().size(); i++) {
            Moto moto = dueño.getMotos().get(i);
            MostrarMoto(moto);
        }
    }
    
    /**
     * 
     * @param dueño miembro del que se muestran todas las motos en propiedad
     * @param bw buffer para escritura en salida por fichero
     */
    private void VolcarMotos(Miembro dueño, BufferedWriter bw) {
        for(int i = 0; i < dueño.getMotos().size(); i++) {
            Moto moto = dueño.getMotos().get(i);
            VolcarMoto(moto, bw);
            try {
                bw.newLine();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * 
     * @param moto moto de la cual se mostrará la informacion
     */
    private void MostrarMoto(Moto moto){
        System.out.println("\tMatricula: " + moto.getMatricula() + "\tModelo: " + moto.getModelo() + "\tCilindrada: " 
                                + moto.getCilindrada() + "\tCoste de compra: " + moto.getCosteCompra() + " €" + "\tOtrosGastos: " + moto.getOtrosGastos() + " €\n");
    }
    
    /**
     * 
     * @param matricula matricula de la moto que se desea encontrar
     * @param miembro miembro al que pertenece la moto
     * @return moto del miembro que coincide con la matricula 
     */
    private Moto EncontrarMoto(String matricula, Miembro miembro) {
        Vector<Moto> motos = miembro.getMotos();
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
     * @param moto moto de la cual se mostrará la informacion
     * @param bw buffer para escritura en salida por fichero
     */
    private void VolcarMoto(Moto moto, BufferedWriter bw){
        try {
            bw.write("\tMatricula: \"" + moto.getMatricula() + "\"\tModelo: \"" + moto.getModelo() + "\"\tCilindrada: \""
                    + moto.getCilindrada() + "\"\tCoste de compra: \"" + moto.getCosteCompra() + "\" €" + "\tOtrosGastos: \"" + moto.getOtrosGastos() + "\" €");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    /**
     * Metodo encargado de mostrar todos las cesiones de la asociacion
     */
    private void MostrarCesiones() {
        Vector<Cesion> cesiones = asociacion.getCesiones();
        
        for(int i = 0; i < cesiones.size(); i++){
            Cesion cesion = cesiones.get(i);
            MostrarCesion(cesion);
        }
        
        String cadena;
        Cesion cesion = null;
        
        if(cesiones.size() > 0) {
            do {            
                System.out.println("\nSi desea mas detalles de una cesion porfavor introduzca la ID de la cesion,"
                            + " Si no es asi introduzca 'N': ");
                Scanner sc = new Scanner(System.in);
                cadena = sc.nextLine();
            
                if(!"N".equals(cadena)) {
                cesion = asociacion.EncontrarCesion(Integer.parseInt(cadena));
                }
                if(!"N".equals(cadena) && cesion != null) {
                    MostrarCesionDetalle(cesion); 
                }
            } while (!"N".equals(cadena));
        }      
        PausarOutput();
    }

    /**
     * Metodo encargado de mostrar una cesion
     * @param cesion cesion de la cual se mostrará la informacion
     */
    private void MostrarCesion(Cesion cesion) {
        System.out.println("ID Cesion:" + cesion.getIdCesion() + "\tFecha: " + cesion.getFecha() + "\tMiembro cedente: " + cesion.getCedente().getNombre() + "\tMiembro cesionario: " + cesion.getCesionario().getNombre()
                            + "\tMoto implicada: " + cesion.getMotoImplicada().getModelo());
    }

    /**
     * Metodo encargado de mostrar una cesion con todos los detalles
     * @param cesion cesion de la cual se mostrará la informacion completa
     */
    private void MostrarCesionDetalle(Cesion cesion) {
        System.out.println("\nID Cesion:" + cesion.getIdCesion() +  " Fecha: " + cesion.getFecha());
        System.out.println("\tMiembro cedente: ");
        MostrarMiembro(cesion.getCedente());
        System.out.println("\tMiembro cesionario: ");
        MostrarMiembro(cesion.getCesionario());
        System.out.println("\tMoto implicada: ");
        MostrarMoto(cesion.getMotoImplicada());
    }
    
    /**
     * Metodo que vuelca las cesiones en el output indicado
     * @param bw buffer para escritura en salida por fichero
     */
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
    
    /**
     * Metodo que vuela una cesion en el output indicado
     * @param cesion cesion de la cual se volcará la informacion
     * @param bw buffer para escritura en salida por fichero
     */
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
    
    /**
     * Metodo que comprueba si una cadena esta vacia 
     * @param cadena cadena que se desea comprobar si esta vacia
     * @return si esta vacia la cadena o no
     */
    private boolean ComprobarCampoVacio(String cadena){
        boolean vacio = false;
        if(cadena.isEmpty()) {
            System.out.println("Error: Campo vacio, porfavor introduzca el campo correctamente: ");
            vacio = true;    
        }
        return vacio;
    }

    /**
     * Metodo que engloba toda la escritura en el output indicado
     * @param bw buffer para escritura en salida por fichero
     */
    private void EscribirFichero(BufferedWriter bw) {
        Vector<Miembro> miembros = asociacion.getMiembros();

        try {
            bw.write("Max Importe: \"" + Asociacion.MAXIMO_IMPORTE_COMPRA + "\"\n");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i = 0; i < miembros.size(); i++){
            Miembro miembro = miembros.get(i);
            VolcarMiembro(miembro, bw);
            try {
                bw.newLine();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            if(miembros.get(i).getNumeroMotos() > 0) {
                VolcarMotos(miembro, bw);
            }

        }        
        
        VolcarCesiones(bw);
        try {
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Metodo que comprueba el formato de una matricula
     * @param matricula matricula que se desea comprobar el formato
     * @return si es un formato correcto o no
     */
    private boolean ComprobarFormatoMatricula(String matricula) {
        boolean matriculaNoValida = false;
        if(matricula.length() != 7) {
            matriculaNoValida = true;
        } else {
            String num = matricula.substring(0, 4);
            String alfa = matricula.substring(4, 7);
                
            for(int i = 0; i < alfa.length(); i++)
                if(!Character.isAlphabetic(alfa.charAt(i))) {
                    matriculaNoValida = true;
                }
            if(matriculaNoValida == false) {
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

    /**
     * Metodo que comprueba que la matricula es unica
     * @param matricula matricula que se desea comprobar
     * @return si la matricula ya existe o es unica
     */
    private boolean ComprobarMatriculaUnica(String matricula) {
        Vector<Moto> motos = asociacion.getMotos();
        boolean matriculaNoUnica = false;
        
        for(int i = 0; i < motos.size(); i++){
            Moto moto = motos.get(i);
            
            if(matricula.equals(moto.getMatricula())) {
                matriculaNoUnica = true;
            }
        }
        return matriculaNoUnica;
    }

    /**
     * Metodo que muestra los miembros con mas cesiones y las motos implicadas
     */
    private void MostrarMiembrosMasCesiones() {
        Vector<Cesion> cesiones = asociacion.getCesiones();
        Vector<Miembro> miembros = asociacion.getMiembros();
        Vector<Miembro> miembrosMax = new Vector<>();
        int indice, numCesiones = 0, maximo = 0;
        
        for (indice = 0; indice < miembros.size();indice++){
            
            for(int i = 0; i < cesiones.size(); i++)
                if(miembros.get(indice).getNumeroSocio() == cesiones.get(i).getCesionario().getNumeroSocio()) {
                    numCesiones++;
                }

            if(numCesiones >= maximo) {
                maximo = numCesiones;
                miembrosMax.add(miembros.get(indice));
                
            }
            numCesiones = 0;
        }
        
        if(miembrosMax.size()==1) {
            System.out.println("El miembro que más cesiones ha recibido es: " + miembrosMax.get(0).getNombre() + " con " + maximo + " cesiones.\n");
        } else if (miembrosMax.size() > 1) {
            System.out.println("Los miembros que más cesiones han recibido con " + maximo + " cesiones son: ");
        } else {
            System.out.println("No hay cesiones realizadas.");
        }
        
        for(int i = 0; i < miembrosMax.size();i++) {
            MostrarMiembro(miembrosMax.get(i));
                
            System.out.println("Las motos implicadas son: \n");
            for( int j = 0; j < cesiones.size(); j++){
                if(miembrosMax.get(i).getNumeroSocio() == cesiones.get(j).getCesionario().getNumeroSocio()) {
           
                System.out.println("\tMoto implicada: ");
                MostrarMoto(cesiones.get(j).getMotoImplicada());
                }            
            }           
        } 
        PausarOutput();
    }
}