package com.uni.intense.application;

import com.uni.intense.domaine.Employe;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmployerDal extends App{



    public String EnregistrerEmployer(Employe Pro) {
        String mes = "";
        String req = "Insert into personne values('" + Pro.getCode() + "','" + Pro.getNom() + "','" + Pro.getPrenom() + "','" + Pro.getSexe() + "','" +
                Pro.getNif() + "','" + Pro.getAdresse() + "','" + Pro.getTelephone() + "','" + Pro.getEmail() + "'," +
                "'" + Pro.getDateNaissance() + "','" + Pro.getStatut() + "','" + Pro.getUrl() + "')";
        int verifier = 0;
        int verifier2 = 0;
        try (Connection conect = DriverManager.getConnection(URL, USER, PASS);
             Statement st = conect.createStatement()) {
            verifier = st.executeUpdate(req);
            if (verifier != 0) {
                String req2 = "Insert into employe values('" + Pro.getCode() + "','" + Pro.getFonction() + "','" + Pro.getSalaire() + "','" + Pro.getDateEmbauche() + "','" + Pro.getUrldoc() + "')";
                verifier2 = st.executeUpdate(req2);
                if (verifier2 != 0) {
                    mes = Pro.getCode();
                }
            }
        } catch (SQLException e) {
            mes = "enregistrement echouer /n" + e.getMessage();
        }
        return mes;
    }

    public Employe RechercherEmployer(String code, Employe Pro) {
        String req = "Select code, nom, prenom, sexe, nif, adresse, telephone, email, dateNaissance, statut, fonction, salaire, urlphoto, urldoc from personne natural join employe where code='" + code + "'";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) {
                Pro.setCode(rs.getString(1));
                Pro.setNom(rs.getString(2));
                Pro.setPrenom(rs.getString(3));
                Pro.setSexe(rs.getString(4));
                Pro.setNif(rs.getString(5));
                Pro.setAdresse(rs.getString(6));
                Pro.setTelephone(rs.getString(7));
                Pro.setEmail(rs.getString(8));
                Pro.setDateNaissance(rs.getString(9));
                Pro.setStatut(rs.getString(10));
                Pro.setFonction(rs.getString(11));
                Pro.setSalaire(rs.getInt(12));
                Pro.setUrl(rs.getString(13));
                Pro.setUrldoc(rs.getString(14));
            }
        } catch (SQLException ex) {
        }
        return Pro;
    }

    public String ModifierEmployer(String nom, String prenom, String telephone, String email, String adresse, String Statut, int salaire, String code) {
        String message = "";
        String req = "update personne set nom='" + nom + "',prenom='" + prenom + "',telephone='" + telephone + "',email='" + email + "',adresse='" + adresse + "',Statut='" + Statut + "' where code='" + code + "'";
        int verifier2 = 0;
        int verifye = 0;
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement()) {
            verifye = st.executeUpdate(req);
            if (verifye != 0) {
                message = "Modification reussi";
                String req2 = "update employe set salaire='" + salaire + "' where code='" + code + "'";
                verifier2 = st.executeUpdate(req2);
                // message reste identique qu'il y ait un update ou non sur employe
            }
        } catch (SQLException ex) {
            message = "Modification echoue  \t" + ex.getMessage();
        }
        return message;
    }

    public String SuprimmerEmployer(String code) {
        String message = "";
        String req = "delete from personne where code='" + code + "'";
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

    public int nombredeligne() {
        int t = 0;
        String req = "Select count(*) as nombre from personne";
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

    public String[][] ListerEmployer() {
        Employe Pro = new Employe();
        int r = nombredeligne();
        String req = "Select code, nom, prenom, sexe, nif, adresse, telephone, email, dateNaissance, statut, fonction, salaire from personne natural join employe";
        String data[][] = new String[r][12];
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            int i = 0;
            while (rs.next()) {
                Pro.setCode(rs.getString(1));
                Pro.setNom(rs.getString(2));
                Pro.setPrenom(rs.getString(3));
                Pro.setSexe(rs.getString(4));
                Pro.setNif(rs.getString(5));
                Pro.setAdresse(rs.getString(6));
                Pro.setTelephone(rs.getString(7));
                Pro.setEmail(rs.getString(8));
                Pro.setDateNaissance(rs.getString(9));
                Pro.setStatut(rs.getString(10));
                Pro.setFonction(rs.getString(11));
                Pro.setSalaire(rs.getInt(12));
                data[i][0] = Pro.getCode();
                data[i][1] = Pro.getNom();
                data[i][2] = Pro.getPrenom();
                data[i][3] = Pro.getSexe();
                data[i][4] = Pro.getNif();
                data[i][5] = Pro.getAdresse();
                data[i][6] = Pro.getTelephone();
                data[i][7] = Pro.getEmail();
                data[i][8] = Pro.getDateNaissance();
                data[i][9] = Pro.getStatut();
                data[i][10] = Pro.getFonction();
                data[i][11] = String.valueOf(Pro.getSalaire());
                i++;
            }
        } catch (SQLException ex) {
            System.out.println("En attente de solutions...");
        }
        return data;
    }

    public ArrayList<String> remplirCombofonction() {
        ArrayList<String> Al = new ArrayList<>();
        String req = "select nomfonction from fonction";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Al.add(rs.getString("nomfonction"));
            }
        } catch (SQLException ex) {
        }
        return Al;
    }

    public ArrayList<String> recherchersalaire(String nomfonction) {
        ArrayList<String> Al = new ArrayList<>();
        String req = "select salaire_minimum,salaire_maximum from fonction where nomfonction='" + nomfonction + "' ";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            if (rs.next()) {
                Al.add(rs.getString("salaire_minimum"));
                Al.add(rs.getString("salaire_maximum"));
            }
        } catch (SQLException ex) {
        }
        return Al;
    }

    public String ModifierUrlPhoto(String Codeprof, String url) {
        String message = "";
        String req = "update personne set urlphoto='" + url + "' where code='" + Codeprof + "'";
        int verifye = 0;
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement()) {
            verifye = st.executeUpdate(req);
            if (verifye != 0) {
                message = "Modification fait avec succes!";
            }
        } catch (SQLException ex) {
            message = "Modification echoue  \t" + ex.getMessage();
        }
        return message;
    }

    public String ModifierUrldoc(String Codeprof, String url) {
        String message = "";
        String req = "update employe set urldoc='" + url + "' where code='" + Codeprof + "'";
        int verifye = 0;
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement()) {
            verifye = st.executeUpdate(req);
            if (verifye != 0) {
                message = "Modification fait avec succes!";
            }
        } catch (SQLException ex) {
            message = "Modification echoue  \t" + ex.getMessage();
        }
        return message;
    }
}