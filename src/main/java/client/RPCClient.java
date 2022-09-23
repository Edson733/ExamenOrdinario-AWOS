package client;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import server.Bean;
import server.Methods;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class RPCClient {
    static Scanner teclado = new Scanner(System.in);
    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        //Edson Miguel Peralta Valdez
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        Methods methods = new Methods();
        String opcion = "", nombre = "", apellido1 = "", apellido2 = "", curp = "", fecha_nac = "", ano = "", mes = "", dia = "";
        do {
            System.out.println("**********RFC**********");
            System.out.println("    1.- Registrar datos");
            System.out.println("    2.- Modificar datos");
            System.out.println("    3.- Consultar datos");
            System.out.println("    4.- Eliminar persona");
            System.out.println("    5.- Salir");
            System.out.print("Escoge la opcion que desees: ");
            opcion = teclado.next();
            if (isNumber(opcion)){
                switch (Integer.parseInt(opcion)){
                    case 1:
                        //Registrar
                        System.out.println("\n*****REGISTRAR*****");
                        System.out.print("Coloca tu Nombre: ");
                        nombre = teclado.next();
                        System.out.print("Coloca tu Apellido Paterno: ");
                        apellido1 = teclado.next();
                        System.out.print("Coloca tu Apellido Materno: ");
                        apellido2 = teclado.next();
                        System.out.print("Coloca tu CURP en Mayusculas: ");
                        curp = teclado.next();
                        System.out.print("Coloca tu Año de Nacimiento: ");
                        ano = teclado.next();
                        System.out.print("Coloca tu Mes de Nacimiento: ");
                        mes = teclado.next();
                        System.out.print("Coloca tu Dia de Nacimiento: ");
                        dia = teclado.next();
                        fecha_nac = ano + mes + dia;
                        String rfc = methods.rfc(nombre, apellido1, apellido2, fecha_nac);
                        Object[] variablesRegistrar = {nombre, apellido1, apellido2, curp, fecha_nac, rfc};
                        Boolean responseRegistrar = (Boolean) client.execute("Methods.registrar", variablesRegistrar);
                        System.out.println("El rfc es: " + rfc);
                        if (responseRegistrar != false){
                            System.out.println("\nRegistro Correctamente");
                        }else{
                            System.out.println("\nRegistro Incorrectamente");
                        }
                        break;
                    case 2:
                        //Modificar
                        System.out.println("\n******MODIFICAR******");
                        System.out.print("Coloca la CURP a modificar en mayusculas: ");
                        curp = teclado.next();
                        boolean resultCurp = methods.existeCurp(curp);
                        if (resultCurp == true){
                            System.out.print("Coloca el nuevo Nombre: ");
                            nombre = teclado.next();
                            System.out.print("Coloca el nuevo Apellido Paterno: ");
                            apellido1 = teclado.next();
                            System.out.print("Coloca el nuevo Apellido Materno: ");
                            apellido2 = teclado.next();
                            System.out.print("Coloca el nuevo Año de Nacimiento: ");
                            ano = teclado.next();
                            System.out.print("Coloca el nuevo Mes de Nacimiento: ");
                            mes = teclado.next();
                            System.out.print("Coloca el nuevo Dia de Nacimiento: ");
                            dia = teclado.next();
                            fecha_nac = ano + mes + dia;
                            String nueva_rfc = methods.rfc(nombre, apellido1, apellido2, fecha_nac);
                            boolean resultModificar = methods.modificar(nombre, apellido1, apellido2, curp, fecha_nac, nueva_rfc);
                            System.out.println("La nueva rfc es: " + nueva_rfc);
                            if (resultModificar != false){
                                System.out.println("\nModificacion Correctemente");
                            }else{
                                System.err.println("\nModificacion Incorrectamente");
                            }
                        }else{
                            System.err.println("\nLa CURP ingresada no existe, favor de ingresar una CURP que si exista");
                        }
                        break;
                    case 3:
                        //Consultar
                        String opc = "";
                        System.out.println("\n*****CONSULTAR*****");
                        System.out.println("    1.- Todos los registros");
                        System.out.println("    2.- Individualmente");
                        System.out.println("    3.- Salir");
                        System.out.print("Seleccione la opcion que desee: ");
                        opc = teclado.next();
                        if (isNumber(opc)){
                            switch (Integer.parseInt(opc)){
                                case 1:
                                    //Todos los registros
                                    System.out.println("\n***TODOS LOS REGISTROS***");
                                    String todo = methods.consultarTodo();
                                    System.out.println(todo);
                                    break;
                                case 2:
                                    //Individualmente
                                    System.out.println("\n***INDIVIDUALES***");
                                    System.out.print("Coloca la CURP a buscar en mayusculas: ");
                                    curp = teclado.next();
                                    String indiv = methods.consultarIndividual(curp);
                                    System.out.println(indiv);
                                    break;
                                case 3:
                                    System.err.println("\nUSTED HA SALIDO");
                                    break;
                                default:
                                    System.err.println("\nLA OPCION QUE ESCOGIO ES INCORRECTA");
                                    break;
                            }
                        }else{
                            System.err.println("\nINGRESA UN VALOR VALIDO");
                        }
                        break;
                    case 4:
                        //Eliminar
                        System.out.println("\n*****ELIMINAR*****");
                        System.out.print("Coloca la curp a eliminar en mayusculas: ");
                        curp = teclado.next();
                        boolean resultEliminar = methods.eliminar(curp);
                        if (resultEliminar != false){
                            System.out.println("\nEliminacion Correctamente");
                        }else{
                            System.out.println("\nEliminacion Incorrectamente");
                        }
                        break;
                    case 5:
                        System.err.println("\nUSTED HA SALIDO");
                        break;
                    default:
                        System.err.println("\nLA OPCION QUE ESCOGIO ES INCORRECTA");
                        break;
                }
            }else{
                System.err.println("\nINGRESA UN VALOR VALIDO");
            }
        }while(!opcion.equals("5"));
    }

    public static boolean isNumber(String number){
        try{
            int num = Integer.parseInt(number);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}

