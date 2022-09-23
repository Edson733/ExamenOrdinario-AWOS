package server;

import utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Dao {
    public boolean registrar(String nombre, String apellido1, String apellido2, String curp, String fecha_nac, String rfc){
        boolean result = false;
        try(
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement pstm = connection.prepareStatement("insert into rfc(nombre, apellido1, apellido2, curp, fecha_nac, rfc) values(?, ?, ?, ?, ?, ?);");
        ){
            pstm.setString(1, nombre);
            pstm.setString(2, apellido1);
            pstm.setString(3, apellido2);
            pstm.setString(4, curp);
            pstm.setString(5, fecha_nac);
            pstm.setString(6, rfc);
            result = pstm.executeUpdate()==1;
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String consultarTodo(){
        String todo = "";
        try{
            Connection connection = MySQLConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from rfc;");
            while(rs.next()){
                todo =  todo +
                        " - "+ rs.getString("nombre") + " / " + rs.getString("apellido1") +
                        " / " + rs.getString("apellido2") + " / " + rs.getString("curp") +
                        " / " + rs.getString("fecha_nac") + " / " + rs.getString("rfc") + "\n";
            }
            rs.close();
            statement.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return todo;
    }

    public String consultarIndividual(String curp){
        String indiv = "";
        try(
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement pstm = connection.prepareStatement("select * from rfc where curp = ?;");
        ){
            pstm.setString(1, curp);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                indiv = rs.getString("nombre") + " / " + rs.getString("apellido1") +
                        " / " + rs.getString("apellido2") + " / " + rs.getString("curp") +
                        " / " + rs.getString("fecha_nac") + " / " + rs.getString("rfc");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return indiv;
    }

    public boolean eliminar(String curp){
        boolean result = false;
        try(
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement pstm = connection.prepareStatement("delete from rfc where curp = ?;");
        ){
            pstm.setString(1, curp);
            result = pstm.executeUpdate()==1;
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean existeCurp(String curp){
        boolean result = false;
        String algo = "";
        try(
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement pstm = connection.prepareStatement("select * from rfc where curp = ?;")
        ){
            pstm.setString(1, curp);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                algo = rs.getString("nombre") + " / " + rs.getString("apellido1") +
                        " / " + rs.getString("apellido2") + " / " + rs.getString("curp") +
                        " / " + rs.getString("fecha_nac") + " / " + rs.getString("rfc");
            }
            if (algo != null){
                result = true;
            }else{
                result = false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean modificar(String nombre, String apellido1, String apellido2, String curp, String fecha_nac, String nueva_rfc){
        boolean result = false;
        try(
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement pstm = connection.prepareStatement("update rfc set nombre = ?, apellido1 = ?, apellido2 = ?, fecha_nac = ?, rfc = ? where curp = ?;");
        ){
            pstm.setString(1, nombre);
            pstm.setString(2, apellido1);
            pstm.setString(3, apellido2);
            pstm.setString(4, fecha_nac);
            pstm.setString(5, nueva_rfc);
            pstm.setString(6, curp);
            result = pstm.executeUpdate()==1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
