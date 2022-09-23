package server;

import java.security.SecureRandom;

public class Methods {
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String curp;
    private String fecha_nac;

    public Methods() {
    }

    public Methods(String nombre, String apellido1, String apellido2, String curp, String fecha_nac) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.curp = curp;
        this.fecha_nac = fecha_nac;
    }

    public boolean registrar(String nombre, String apellido1, String apellido2, String curp, String fecha_nac, String rfc){
        Dao dao = new Dao();
        return dao.registrar(nombre, apellido1, apellido2, curp, fecha_nac, rfc);
    }

    public boolean modificar(String nombre, String apellido1, String apellido2, String curp, String fecha_nac, String nueva_rfc){
        Dao dao = new Dao();
        return dao.modificar(nombre, apellido1, apellido2, curp, fecha_nac, nueva_rfc);
    }

    public boolean existeCurp(String curp){
        Dao dao = new Dao();
        return dao.existeCurp(curp);
    }

    public String consultarTodo(){
        Dao dao = new Dao();
        return dao.consultarTodo();
    }

    public String consultarIndividual(String curp){
        Dao dao = new Dao();
        return dao.consultarIndividual(curp);
    }

    public boolean eliminar(String curp){
        Dao dao = new Dao();
        return dao.eliminar(curp);
    }

    public String rfc(String nombre, String apellido1, String apellido2, String fecha_nac){
        String nomLetra = nombre.substring(0,1);
        String ape1Letra = apellido1.substring(0,2);
        String ape2Letra = apellido2.substring(0,1);
        String fecha = fecha_nac.substring(2,8);
        String random = generateRandomString(3);
        String rfc = ape1Letra + ape2Letra + nomLetra + fecha + random;
        return rfc.toUpperCase();
    }

    public String generateRandomString(int length) {
        // Puede personalizar los personajes que desea agregar a
        // las cadenas al azar
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";

        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();

        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // 0-62 (exclusive), retornos aleatorios 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }
}
