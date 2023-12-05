package sio.devoir2graphiques.Tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class GraphiqueController
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public GraphiqueController() {
        cnx = ConnexionBDD.getCnx();
    }
    // A vous de jouer

    public HashMap<Integer,Double> getDataGraphique1(){
        HashMap<Integer,Double> ageSalaire = new HashMap<>();
        try {
            ps = cnx.prepareStatement("SELECT ageEmp, AVG(salaireEmp) AS moyenneSalaire\n" +
                    "FROM employe\n" +
                    "GROUP BY ageEmp;");
            rs = ps.executeQuery();

            while (rs.next())
            {
                ageSalaire.put(rs.getInt(1),rs.getDouble(2) );
            }
            rs.close();
            ps.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ageSalaire;
    }
    public HashMap<String,Integer> getDataGraphique2() {
        HashMap<String,Integer> trancheAge = new HashMap<>();
        try{
            ps = cnx.prepareStatement("SELECT COUNT(*), employe.ageEmp, employe.sexe\n" +
                    "FROM employe\n" +
                    "GROUP BY sexe , ageEmp\n" +
                    "ORDER BY ageEmp, sexe");
            rs = ps.executeQuery();

            while (rs.next())
            {
                trancheAge.put(rs.getString(1),rs.getInt(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trancheAge;
    }
    public HashMap<String, String> getDataGraphique3()
    {   HashMap<String,String> regroupSexe = new HashMap<>();
        try{
            ps = cnx.prepareStatement("SELECT sexe, COUNT(*) AS nombre, (COUNT(*) / (SELECT COUNT(*) FROM employe)) * 100 AS pourcentage " +
                    "FROM employe " +
                    "GROUP BY sexe;");
            rs = ps.executeQuery();

            while (rs.next())
            {
                regroupSexe.put(rs.getString(1),rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return regroupSexe;
    }




}
