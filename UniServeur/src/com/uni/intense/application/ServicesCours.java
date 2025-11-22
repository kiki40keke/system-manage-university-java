package com.uni.intense.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class ServicesCours extends App {


    public String EnregistrerOption(String Nom_Opt) {
        Random rd = new Random();
        int val = rd.nextInt(11 + 9999);
        String Id_Opt = "Options-" + val;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String datedujour = dtf.format(LocalDateTime.now());
        String mes = "";
        String req = "Insert into options values('" + Id_Opt + "','" + Nom_Opt + "','" + datedujour + "')";
        int verifier = 0;
        try (Connection conect = DriverManager.getConnection(URL, USER, PASS);
             Statement st = conect.createStatement()) {
            verifier = st.executeUpdate(req);
            if (verifier != 0) {
                mes = "L'Option a ete Creer, son Idendifiant unique est :" + Id_Opt;
            }
        } catch (SQLException e) {
            mes = "enregistrement echouer /n" + e.getMessage();
        }
        return mes;
    }

    public String EnregistrerCours(String Id_Opt, String codeniv, String codesession, String Nomcours) {
        Random rd = new Random();
        int val = rd.nextInt(11 + 9999);
        String Id_Cours = "Cours-" + val;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String datedujour = dtf.format(LocalDateTime.now());
        String mes = "";
        String req = "Insert into cours values('" + Id_Cours + "','" + Id_Opt + "','" + codeniv + "','" + codesession + "','" + Nomcours + "','" + datedujour + "')";
        int verifier = 0;
        try (Connection conect = DriverManager.getConnection(URL, USER, PASS);
             Statement st = conect.createStatement()) {
            verifier = st.executeUpdate(req);
            if (verifier != 0) {
                mes = "Le cours a ete Creer, son Idendifiant unique est :" + Id_Cours;
            }
        } catch (SQLException e) {
            mes = "enregistrement echouer /n" + e.getMessage();
        }
        return mes;
    }

    public ArrayList<String> remplirComboOptions() {
        ArrayList<String> Al = new ArrayList<>();
        String req = "select Nom_Opt from options";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Al.add(rs.getString("Nom_Opt"));
            }
        } catch (SQLException ex) {
        }
        return Al;
    }

    public ArrayList<String> remplirComboCours(String Id_Opt, String Niveau, String Session) {
        ArrayList<String> Al = new ArrayList<>();
        String req = "select Nom_Cours from Cours where Id_Opt='" + Id_Opt + "' and codeniv='" + Niveau + "' and codesession='" + Session + "'";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Al.add(rs.getString("Nom_Cours"));
            }
        } catch (SQLException ex) {
        }
        return Al;
    }

    public ArrayList<String> remplirComboPromotion() {
        ArrayList<String> Al = new ArrayList<>();
        String req = "SELECT promotion from etudiant GROUP by promotion";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Al.add(rs.getString("promotion"));
            }
        } catch (SQLException ex) {
        }
        return Al;
    }

    public String EnregistrerCoursSelectionner(String IdProfesseur, String Nom_Opt, String Nom_Cours, String Vacation) {
        Random rd = new Random();
        String Idcours = RechercherCodeCours(Nom_Opt, Nom_Cours);
        int v = rd.nextInt(3838292);
        String CoursSelect = "Cs-" + v;
        String mes = "";
        String req = "Insert into CoursSelects values('" + CoursSelect + "','" + IdProfesseur + "','" + Idcours + "','" + Vacation + "')";
        int verifier = 0;
        try (Connection conect = DriverManager.getConnection(URL, USER, PASS);
             Statement st = conect.createStatement()) {
            verifier = st.executeUpdate(req);
            if (verifier != 0) {
                mes = "Le cours a ete associer, son Idendifiant unique est :" + CoursSelect;
            }
        } catch (SQLException e) {
            mes = "enregistrement echouer /n" + e.getMessage();
        }
        return mes;
    }

    public String SuprimmerAttributionCours(String codecs) {
        String message = "";
        String req = "delete from CoursSelects where codecs='" + codecs + "'";
        int verifye = 0;
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement()) {
            verifye = st.executeUpdate(req);
            if (verifye != 0) {
                message = "Suppression reussi";
            }
        } catch (SQLException ex) {
            message = "Suppression echoue  \t" + ex.getMessage();
        }
        return message;
    }

    public int nombredeligne(String Id_prof) {
        int t = 0;
        String req = "Select count(*) as nombre from CoursSelects where Id_prof='" + Id_prof + "'";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) {
                t = rs.getInt("nombre");
            }
        } catch (SQLException ex) {
        }
        return t;
    }

    public String[] remplirlisetecours(String Id_prof) {
        int r = nombredeligne(Id_prof);
        String req = "SELECT codecs from CoursSelects where Id_prof='" + Id_prof + "'";
        String data[] = new String[r];
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            int i = 0;
            while (rs.next()) {
                data[i] = rs.getString("codecs");
                i++;
            }
        } catch (SQLException ex) {
        }
        return data;
    }

    public String InformationCoursSelect(String codecs) {
        String IdCours = null;
        String req = "SELECT C.Nom_Cours,Cs.Vacations,O.Nom_Opt  from CoursSelects Cs, Cours C,options O " +
                "where O.Id_Opt=C.Id_Opt and C.Id_Cours=Cs.Id_Cours and Cs.codecs='" + codecs + "'";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) {
                String Nomcours = rs.getString(1);
                String Vacations = rs.getString(2);
                String NomOptions = rs.getString(3);
                IdCours = "Options : " + NomOptions + "\n Cours : " + Nomcours + " \n Vacation : " + Vacations;
            } else {
                System.out.println("li pa  jwenn li se :" + IdCours);
            }
        } catch (SQLException ex) {
        }
        return IdCours;
    }

    public String RechercherCodeCours(String Nom_Opt, String Nom_Cours) {
        String IdCours = "nada";
        String Id_Opt = RechercherCodeOption(Nom_Opt);
        String req = "select Id_Cours from Cours where Id_Opt='" + Id_Opt + "' and Nom_Cours='" + Nom_Cours + "'";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) {
                IdCours = rs.getString("Id_Cours");
                System.out.println("li jwenn li se :" + IdCours);
            } else {
                System.out.println("li pa  jwenn li se :" + IdCours);
            }
            System.out.println("Nom option service: " + Nom_Opt + " " + Id_Opt);
            System.out.println("Nom cours service: " + Nom_Cours);
        } catch (SQLException ex) {
        }
        return IdCours;
    }

    public String RechercherAttributionCours(String Id_Cours, String Vacations) {
        String codecs = null;
        String req = "select codecs from CoursSelects where Id_Cours='" + Id_Cours + "' and Vacations='" + Vacations + "'";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) {
                codecs = rs.getString("codecs");
            }
        } catch (SQLException ex) {
        }
        return codecs;
    }

    public String RechercherCodeOption(String Nom_Opt) {
        String IdOption = "nada";
        String req = "select Id_Opt from options where Nom_Opt='" + Nom_Opt + "'";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) {
                IdOption = rs.getString("Id_Opt");
            }
        } catch (SQLException ex) {
        }
        return IdOption;
    }

    public String RechercherNomOptions(String Id_Opt) {
        String IdOption = "nada";
        String req = "select Nom_Opt from options where Id_Opt='" + Id_Opt + "'";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) {
                IdOption = rs.getString("Nom_Opt");
            }
        } catch (SQLException ex) {
        }
        return IdOption;
    }

    public String RechercherCours(String Id_Cours) {
        String IdOption = "nada";
        String req = "select Nom_Cours from cours where Id_Cours='" + Id_Cours + "'";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) {
                IdOption = rs.getString("Nom_Cours");
            }
        } catch (SQLException ex) {
        }
        return IdOption;
    }

    public int nombredeligneoptions(String req) {
        int t = 0;
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) {
                t = rs.getInt("nombre");
            }
        } catch (SQLException ex) {
        }
        return t;
    }

    public String[][] ListerOptions() {
        String req1 = "select Count(*) as nombre from options ";
        int r = nombredeligneoptions(req1);
        String req = "select * from options ";
        String data[][] = new String[r][2];
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            int i = 0;
            while (rs.next()) {
                String Id_Opt = rs.getString(1);
                String Nom_Opt = rs.getString(2);
                data[i][0] = Id_Opt;
                data[i][1] = Nom_Opt;
                i++;
            }
        } catch (SQLException ex) {
            System.out.println("En attente de solutions... " + ex.getMessage());
        }
        return data;
    }

    public String[][] ListerCours() {
        String req1 = "select Count(*) as nombre from cours ";
        int r = nombredeligneoptions(req1);
        String req = "select Id_Cours,Id_Opt,codeniv,codesession,Nom_Cours from cours  order by Id_Opt ";
        String data[][] = new String[r][4];
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            int i = 0;
            while (rs.next()) {
                String Id_Opt = rs.getString(2);
                String Nom_Opt = RechercherNomOptions(Id_Opt);
                String codeniv = rs.getString(3);
                String codesession = rs.getString(4);
                String Nom_Cours = rs.getString(5);

                data[i][0] = Nom_Cours;
                data[i][1] = Nom_Opt;
                data[i][2] = codeniv;
                data[i][3] = codesession;
                i++;
            }
        } catch (SQLException ex) {
        }
        return data;
    }
}