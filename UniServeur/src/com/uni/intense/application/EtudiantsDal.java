package com.uni.intense.application;

import com.uni.intense.domaine.Etudiants;
import com.uni.intense.domaine.Paiement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class EtudiantsDal extends App {

    public String EnregistreEtudiant(Etudiants Etud){
        String message="";
        String req="Insert into Etudiant values('"+Etud.getCodeEtud()+"','"+Etud.getNom()+"','"+Etud.getPrenom()+"','"+Etud.getSexe()+"','"+Etud.getDateNaiss()+"','"+Etud.getNif()+"','"+Etud.getAdresse()+"','"+Etud.getEmail()+"','"+Etud.getPhone()+"','"+Etud.getNomResponsable()+"','"+Etud.getPhoneResponsable()+"','"+Etud.getVacation()+"','"+Etud.getNomOption()+"','"+Etud.getDatEnregistr()+"','"+Etud.getPromotion()+"','"+Etud.getNiveau()+"','"+Etud.getUrl()+"')";
        int verifye=0;
        try (Connection con = getConnection(); Statement st = con.createStatement()) {
            verifye=st.executeUpdate(req);
            if(verifye!=0) {
                Paiement paie=new Paiement();
                PaiementDal dal=new PaiementDal();
                dal.RechercherPaiement2(Etud.getNomOption(), paie);
                double fraisInscription2=paie.getFraisInscription();
                if(fraisInscription2!=0.0){
                    ReinscrireEtud("1", Etud.getCodeEtud());
                    double fraisDentres2 =paie.getFraisDentres();
                    double fraisSession2=paie.getFraisSession();
                    dal.EnregistrerComptePaiement(Etud.getCodeEtud(),Etud.getNomOption(), fraisInscription2, fraisDentres2, fraisSession2);
                    message=Etud.getCodeEtud();
                } else {
                    message="Une modalite de paiement n'as pas ete enregistrer pour l'option de cet etudiant";
                    SuprimmerEtudiant(Etud.getCodeEtud());
                }
            }
        } catch(SQLException ex) {
            message="Enregistrement echoue    \t"+ex.getMessage();
        }
        return message;
    }

    public Etudiants RechercherEudiant(String CodeEtud, Etudiants Etud){
        String req="Select Id_Etud,Nom_Etud,Prenom_Etud,sexe,datNaiss,nif,adresse,email,phone,nomResponsable,numeroRefference,NomVacation,Nom_Opt,promotion,codeniv,urlphoto from Etudiant natural join options where Id_Etud='"+CodeEtud+"'";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(req)) {
            if(rs.next()) {
                Etud.setCodeEtud(rs.getString(1));
                Etud.setNom(rs.getString(2));
                Etud.setPrenom(rs.getString(3));
                Etud.setSexe(rs.getString(4));
                Etud.setDateNaiss(rs.getString(5));
                Etud.setNif(rs.getString(6));
                Etud.setAdresse(rs.getString(7));
                Etud.setEmail(rs.getString(8));
                Etud.setPhone(rs.getString(9));
                Etud.setNomResponsable(rs.getString(10));
                Etud.setPhoneResponsable(rs.getString(11));
                Etud.setVacation(rs.getString(12));
                Etud.setNomOption(rs.getString(13));
                Etud.setPromotion(rs.getString(14));
                Etud.setNiveau(rs.getString(15));
                Etud.setUrl(rs.getString(16));
            }
        } catch(SQLException ex) {
            System.out.println(""+ex.getMessage());
        }
        return Etud;
    }

    public Etudiants RechercherNifEudiant(String req, Etudiants Etud){
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(req)) {
            if(rs.next()) {
                Etud.setCodeEtud(rs.getString(1));
                Etud.setNom(rs.getString(2));
                Etud.setPrenom(rs.getString(3));
                Etud.setSexe(rs.getString(4));
                Etud.setDateNaiss(rs.getString(5));
                Etud.setNif(rs.getString(6));
                Etud.setAdresse(rs.getString(7));
                Etud.setEmail(rs.getString(8));
                Etud.setPhone(rs.getString(9));
                Etud.setNomResponsable(rs.getString(10));
                Etud.setPhoneResponsable(rs.getString(11));
                Etud.setVacation(rs.getString(12));
                Etud.setNomOption(rs.getString(13));
                Etud.setPromotion(rs.getString(14));
                Etud.setNiveau(rs.getString(15));
            }
        } catch(SQLException ex) {
            System.out.println(""+ex.getMessage());
        }
        return Etud;
    }

    public String ModifierEtudiant(String CodeEtud, String Nom,String Prenom,String adresse,String email,String phone,String nomResponsable,String numeroRefference){
        String message="";
        String req="update Etudiant set Nom_Etud='"+Nom+"',Prenom_Etud='"+Prenom+"',adresse='"+adresse+"',email='"+email+"',phone='"+phone+"',nomResponsable='"+nomResponsable+"',numeroRefference='"+numeroRefference+"' where Id_Etud='"+CodeEtud+"'";
        int verifye=0;
        try (Connection con = getConnection(); Statement st = con.createStatement()) {
            verifye=st.executeUpdate(req);
            if(verifye!=0) {
                message="Modification fait avec succes!";
            }
        } catch(SQLException ex) {
            message="Modification echoue  \t"+ex.getMessage();
        }
        return message;
    }

    public String ModifierNiveauEtudiant(String CodeEtud, String codeniv){
        String message="";
        String req="update Etudiant set codeniv='"+codeniv+"' where Id_Etud='"+CodeEtud+"'";
        int verifye=0;
        try (Connection con = getConnection(); Statement st = con.createStatement()) {
            verifye=st.executeUpdate(req);
            if(verifye!=0) {
                message="Modification fait avec succes!";
            }
        } catch(SQLException ex) {
            message="Modification echoue  \t"+ex.getMessage();
        }
        return message;
    }

    public String ModifierUrlPhoto(String CodeEtud, String url){
        String message="";
        String req="update Etudiant set urlphoto='"+url+"' where Id_Etud='"+CodeEtud+"'";
        int verifye=0;
        try (Connection con = getConnection(); Statement st = con.createStatement()) {
            verifye=st.executeUpdate(req);
            if(verifye!=0) {
                message="Modification fait avec succes!";
            }
        } catch(SQLException ex) {
            message="Modification echoue  \t"+ex.getMessage();
        }
        return message;
    }

    public String ReinscrireEtud(String Niv,String CodeEtud){
        String message="";
        Random rd=new Random();
        int val=rd.nextInt(111+9999);
        String codeNivEt="Niv-"+val;
        String req="insert into NivoEtudiants values('"+codeNivEt+"','"+CodeEtud+"','"+Niv+"')";
        int verifye=0;
        try (Connection con = getConnection(); Statement st = con.createStatement()) {
            verifye=st.executeUpdate(req);
            if(verifye!=0) {
                ModifierNiveauEtudiant(CodeEtud, Niv);
                message="L'etudiant a ete Reeinscrit et maintenant au niveau !"+Niv;
            }
        } catch(SQLException ex){
            message="Erreur"+ex.getMessage();
            System.out.println(message);
        }
        return message;
    }

    public String SuprimmerEtudiant(String Id_Etud){
        String message="";
        String req="delete from Etudiant where Id_Etud='"+Id_Etud+"'";
        int verifye=0;
        try (Connection con = getConnection(); Statement st = con.createStatement()) {
            verifye=st.executeUpdate(req);
            if(verifye!=0) {
                message="Suppression reussi";
            }
        } catch(SQLException ex) {
            message="Suppression echoue  \t"+ex.getMessage();
        }
        return message;
    }

    public int nombredeligne(){
        int t=0;
        String req="Select count(*) as nombre from Etudiant";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(req)) {
            if(rs.next()) {
                t=rs.getInt("nombre");
            }
        } catch(SQLException ex) {}
        return t;
    }

    public String[][] ListerEtudiant(){
        Etudiants Etud=new Etudiants();
        int r=nombredeligne();
        String req="Select * from Etudiant";
        String data[][] = new String[r][17];
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(req)) {
            int i=0;
            while(rs.next()){
                Etud.setCodeEtud(rs.getString(1));
                Etud.setNom(rs.getString(2));
                Etud.setPrenom(rs.getString(3));
                Etud.setSexe(rs.getString(4));
                Etud.setDateNaiss(rs.getString(5));
                Etud.setNif(rs.getString(6));
                Etud.setAdresse(rs.getString(7));
                Etud.setEmail(rs.getString(8));
                Etud.setPhone(rs.getString(9));
                Etud.setNomResponsable(rs.getString(10));
                Etud.setPhoneResponsable(rs.getString(11));
                Etud.setVacation(rs.getString(12));
                Etud.setNomOption(rs.getString(13));
                Etud.setDatEnregistr(rs.getString(14));
                Etud.setPromotion(rs.getString(15));
                Etud.setNiveau(rs.getString(16));
                Etud.setUrl(rs.getString(17));

                data[i][0] = Etud.getCodeEtud();
                data[i][1] = Etud.getNom();
                data[i][2] = Etud.getPrenom();
                data[i][3] = Etud.getSexe();
                data[i][4] = Etud.getDateNaiss();
                data[i][5] = Etud.getNif();
                data[i][6] = Etud.getAdresse();
                data[i][7] = Etud.getEmail();
                data[i][8] = Etud.getPhone();
                data[i][9] = Etud.getNomResponsable();
                data[i][10] = Etud.getPhoneResponsable();
                data[i][11] = Etud.getVacation();
                ServicesCours ser = new ServicesCours();
                String nom= ser.RechercherNomOptions(Etud.getNomOption());
                data[i][12] = nom;
                data[i][13] = Etud.getDatEnregistr();
                data[i][14] = Etud.getPromotion();
                data[i][15] = Etud.getNiveau();
                data[i][16] = Etud.getUrl();

                i++;
            }
        } catch(SQLException ex) {}
        return data;
    }
}