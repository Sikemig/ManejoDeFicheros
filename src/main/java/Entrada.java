import model.Coche;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Entrada {
    // Preparamos el programa, metodo main
    public static void main(String[] args) {

        // Creamos el file y preparamos tanto la extracción como la introducion de datos en el fichero coches.dat
        File file = new File("src/main/java/coches.dat");
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;


        //Creamos el ArrayList aquí para tenerlo preparado, aunque esté vacía
        ArrayList<Coche> listadoCoches = new ArrayList<>();

        // Comprobamos si existe
        if(file.exists()){      // si existe, cargamos en el el objectinputstream su contenido
            try {
                objectInputStream = new ObjectInputStream(new FileInputStream(file));

                // Necesitamos comprobar el contenido del fichero
                Object lectura = null;

                while((lectura = objectInputStream.readObject()) != null){
                    // Una vez comprobado, vamos añadiendo lo leido al array
                    listadoCoches.add((Coche) lectura);     //Hay que castear Coche para indicar que tipo de Object es
                }
            } catch (FileNotFoundException e) {     // Listado de posibles errores
                System.out.println("No se encuentra el archivo");
            } catch (ClassNotFoundException e) {
                System.out.println("No existe la clase Coche");
            } catch (IOException e) {
                System.out.println("Error en la lectura del fichero");
            } finally {
                // Finalmente cerramos el flujo de datos
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    System.out.println("Error en el cerrado del flujo de lectura");
                }
            }
        }


        // Preparamos el menú
        Scanner scanner = new Scanner(System.in);
        int opcion;


        // Metemos el menú en bucle para forzar al usuario a utilizar el botón de Terminar programa para finalizar
        do {
            System.out.println("Por favor, seleccione que opción desea realizar:");
            System.out.println("1-.Añadir nuevo coche");
            System.out.println("2-.Borrar coche por ID");
            System.out.println("3-.Consultar coche por ID");
            System.out.println("4-.Listado de coches");
            System.out.println("5.-Terminar programa");
            System.out.println("6.-.EXTRA.- Exportar a un archivo CSV");
            opcion = scanner.nextInt();

            // Preparamos la funcionalidad de cada opcion

            switch (opcion) {       // para mejorar visualmente el codigo, en cada case usaremos metodos que definiremos abajo
                case 1: // Añadir un coche al arraylist
                    // pedimos por scanner la información del coche a insertar
                    System.out.println("Por favor, introduce el id del coche");
                    int idAñadir = scanner.nextInt();
                    System.out.println("Por favor, introduce la matrícula del coche");
                    String matricula = scanner.next();
                    System.out.println("Por favor, introduce la marca del coche");
                    String marca = scanner.next();
                    System.out.println("Por favor, introduce el modelo del coche");
                    String modelo = scanner.next();
                    System.out.println("Por favor, introduce el color del coche");
                    String color = scanner.next();

                    //Con los datos, construimos el objeto Coche
                    Coche coche = new Coche(idAñadir,matricula,marca,modelo,color);
                    //Procedemos a comprobar si ya se encuentra en el listado
                    if(listadoCoches.contains(coche)){
                        System.out.println("Este coche ya está en el listado, no se ha añadido");
                    } else{
                        listadoCoches.add(coche);
                        System.out.println("Se ha añadido el coche a la lista");
                    }
                    break;

                case 2: //borrar un coche del arraylist
                    // pedimos por scanner el id del coche a borrar
                    System.out.println("Por favor, introduce el id del coche a eliminar");
                    int idBorrado = scanner.nextInt();
                    // recorremos el listado de coches
                    for(Coche cocheBorrar : listadoCoches){
                        // si el id de los coches coincide con el insertado, lo borramos de la lista
                        if(cocheBorrar.getId()==idBorrado){
                            listadoCoches.remove(cocheBorrar);
                            System.out.println("Se ha borrado el coche correctamente");
                        }
                    }
                    break;

                case 3: // consultar un coche del arraylist por id
                    // pedimos por scanner el id del coche a consultar
                    System.out.println("Por favor, introduce el id del coche a consultar");
                    int idCoche = scanner.nextInt();
                    // recorremos el listado de coches
                    for (Coche cocheConsulta: listadoCoches){
                        // Comparamos los resultados con el id que queremos consultar y mostramos su informacion
                        if(idCoche == cocheConsulta.getId()){
                            System.out.println("El id del coche es: " + cocheConsulta.getId());
                            System.out.println("La matricula es: " + cocheConsulta.getMatricula());
                            System.out.println("La marca del coche es: " + cocheConsulta.getMarca());
                            System.out.println("El modelo del coche es: " + cocheConsulta.getModelo());
                            System.out.println("El color del coche es: " + cocheConsulta.getColor());
                        }
                    }
                    break;

                case 4:
                    //Listamos todos los coches que tenemos recorriendo el array
                    for(Coche listado : listadoCoches){
                        System.out.println("El id del coche es: " + listado.getId());
                        System.out.println("La matricula es: " + listado.getMatricula());
                        System.out.println("La marca del coche es: " + listado.getMarca());
                        System.out.println("El modelo del coche es: " + listado.getModelo());
                        System.out.println("El color del coche es: " + listado.getColor() + "\n");
                    }
                    break;
                case 5:
                    // Opcion de fin de programa
                    System.out.println("Finalizando programa");

                    //Se inicia la exportación al fichero coches.dat
                    try {
                        objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                        //Realizamos un for para escribir cada objeto en una linea
                        for(Coche cocheEscritura : listadoCoches){
                            objectOutputStream.writeObject(cocheEscritura);
                        }
                    } catch (IOException e) {
                        System.out.println("Error en la escritura del fichero");
                    } finally {
                        try {
                            objectOutputStream.close();
                        } catch (IOException e) {
                            System.out.println("Error en el cerrado de escritura");
                        }
                    }
                    break;

                case 6: // Opcion para exportar los datos a un archivo .csv
                    // Para exportar a un csv necesitamos un fileWriter y separar cada campo del array por un ;
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter(new File("src/main/java/coches.csv"));

                        // Escribimos aquí el nombre de las columnas de cara al .csv y un salto de linea
                        fileWriter.write("ID;");
                        fileWriter.write("MATRICULA;");
                        fileWriter.write("MARCA;");
                        fileWriter.write("MODELO;");
                        fileWriter.write("COLOR;");
                        fileWriter.write("\n");

                        // Tenemos que recorrer el array para ver su informacion
                        for(Coche cocheCSV : listadoCoches){
                            // Ahora vamos escribiendo con el fireWriter cada campo
                            fileWriter.write(cocheCSV.getId()+";");
                            fileWriter.write(cocheCSV.getMatricula()+";");
                            fileWriter.write(cocheCSV.getMarca()+";");
                            fileWriter.write(cocheCSV.getModelo()+";");
                            fileWriter.write(cocheCSV.getColor()+";");
                            fileWriter.write("\n");
                        }
                        System.out.println("Se ha exportado el listado a un archivo .csv correctamente");
                    } catch (IOException e) {
                        System.out.println("Fallo en los permisos de escritura");
                    }finally { // Hay que cerrar la escritura
                        try {
                            fileWriter.close();
                        } catch (IOException e) {
                            System.out.println("Error en el cerrado del fichero");
                        }
                    }
                    break;
            }
        } while(opcion!=5);
    }
}
