/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uni.intense.application;


import com.uni.intense.domaine.EvaluationsNormal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author kelly
 */
public class EvaluationsNormalDal extends App{
    
    public String EnregistrerEvaluation(EvaluationsNormal Eva){
     String mes="";
    
        
        String req="Insert into  EvaluationsNormal values('"+Eva.getCodeEvaluationsNormal()+"','"+Eva.getCodepalmares()+"','"+Eva.getTypesNormal()+"','"+Eva.getDescriptionsNormal()+"','"+Eva.getCoeficientNormal()+"','"+Eva.getDateEvaluationsNormal()+"')";
       int verifier=0;
       try{
           // Etablir la connection
          Connection con = getConnection();
          Statement st = con.createStatement();
               //Utiliser une methode du Statement pour executer la requete
               verifier=st.executeUpdate(req);
               if(verifier!=0){
                
                   mes="L'evaluation a ete Creer, son Idendifiant unique est :"+Eva.getCodeEvaluationsNormal();
                   System.out.println(mes);
               }
               
               con.close(); st.close();
       }
       catch (SQLException e){
           mes="enregistrement echouer /n"+e.getMessage();
       }
        return mes;
    
    }
    
    public ArrayList<String> RechercherEvaluation( String codeEvaluationsNormal){
             ArrayList<String> Al=new ArrayList<String>();
       String message=null;
        //Preparer la requete
       String req="select O.Nom_Opt,C.Nom_Cours,S.NomSession,N.Niveau,P.promotion,V.NomVacation,\n" +
"E.Codepalmares,E.TypesNormal,E.DescriptionsNormal,E.CoeficientNormal\n" +
"from Options O,Cours C, Sessionx S , Niveaux N ,palmares P , Vacations V,EvaluationsNormal E where\n" +
"O.Id_Opt=C.Id_Opt and  N.codeniv=C.codeniv and  S.codesession=C.codesession\n" +
" and C.Id_Cours=P.Id_Cours and V.NomVacation=P.NomVacation  and P.codepalmares=E.codepalmares \n" +
" and E.codeEvaluationsNormal='"+codeEvaluationsNormal+"'";
    // String req="select Id_Cours from Cours natural join options where Nom_Opt='Genie Electronique' and Nom_Cours='Analyse Numerique'";
         try
        {
            //Etablir la connexion
           Connection con =DriverManager.getConnection("jdbc:mysql://localhost/GestionUniversite","root","");
            //Creer Statement
            Statement st=con.createStatement();
            //Executer la requete
            //Creer un objet de type ResultSet
            ResultSet rs=st.executeQuery(req);
            if(rs.next())
            {
                Al.add(rs.getString(1));
                Al.add(rs.getString(2));
                Al.add(rs.getString(3));
                Al.add(rs.getString(4));
                Al.add(rs.getString(5));
                Al.add(rs.getString(6));
                Al.add(rs.getString(7));
                Al.add(rs.getString(8));
                Al.add(rs.getString(9));
                Al.add(rs.getString(10));
            }
             con.close(); st.close();
          
        }
          catch(SQLException ex)
              
        {
           message="enregistrement echouer /n"+ex.getMessage();
    }
       return Al;
    }  
    
        public int nombredeligne(String codeEvaluationsNormal){
               int t=0;
              
 // String req="select count(*) as nombre from etudiant natural join NotesNormal where codeEvaluationsNormal='"+codeEvaluationsNormal+"'";
    //Preparer la requete
     String req="select count(*) as nombre\n" +
" from etudiant E,Palmares P, EvaluationsNormal D \n" +
" where E.promotion=P.promotion and  E.NomVacation=P.NomVacation  and E.Id_Opt=P.Id_Opt \n" +
" and P.codepalmares=D.codepalmares and D.codeEvaluationsNormal='"+codeEvaluationsNormal+"'";
        try
        {
            //Etablir la connexion
            Connection con =DriverManager.getConnection("jdbc:mysql://localhost/GestionUniversite","root","");
            //Creer Statement
            Statement st=con.createStatement();
            //Executer la requete
            //Creer un objet de type ResultSet
            ResultSet rs=st.executeQuery(req);
            int i=0;
            if(rs.next())
            {
              //data = new String[i][3];
                 int nom = rs.getInt("nombre");
      t=nom;

            }
             
            //Fermer la connexion
            con.close();st.close();
        }
        catch(SQLException ex)
        {
            
        }
        return t;
   }
    
    public String[][] ListerNotes(String codeEvaluationsNormal){
       
                int r=nombredeligne(codeEvaluationsNormal);
           //   String req="select Id_Etud,Nom_Etud,Prenom_Etud,notenormal from etudiant natural join NotesNormal where codeEvaluationsNormal='"+codeEvaluationsNormal+"'";
                      String req = "select E.Id_Etud,E.Nom_Etud,E.Prenom_Etud,D.CoeficientNormal,D.codeEvaluationsNormal \n" +
" from etudiant E,Palmares P, EvaluationsNormal D,Cours C ,nivoetudiants N \n" +
" where N.codeniv=C.codeniv and  C.Id_Cours=P.Id_Cours and  N.Id_Etud=E.Id_Etud and E.promotion=P.promotion and  E.NomVacation=P.NomVacation  and E.Id_Opt=P.Id_Opt \n" +
" and P.codepalmares=D.codepalmares and D.codeEvaluationsNormal='"+codeEvaluationsNormal+"'";
           int t=0;
               String data[][] = new String[r][5];
              
    //Preparer la requete
      
        try
        {
            //Etablir la connexion
            Connection con =DriverManager.getConnection("jdbc:mysql://localhost/GestionUniversite","root","");
            //Creer Statement
            Statement st=con.createStatement();
            //Executer la requete
            //Creer un objet de type ResultSet
            ResultSet rs=st.executeQuery(req);
            int i=0;
            while(rs.next())
            {
                
               String Id_Etud =rs.getString(1);
              String  Nom_Etud=rs.getString(2);
               String Prenom_Etud=rs.getString(3);
              String  notenormal=RechercherNotes(Id_Etud, codeEvaluationsNormal);
                       if(notenormal==null){
                       notenormal="0";
                       }   
                       String CoeficientNormal=rs.getString(4);
//      
        data[i][0] = Id_Etud;
        data[i][1] = Nom_Etud;
        data[i][2] = Prenom_Etud;
        data[i][3] = notenormal;
        data[i][4] = CoeficientNormal;
        i++;

            }
             
            //Fermer la connexion
            con.close();st.close();
        }
        catch(SQLException ex)
        {
            
        }
        return data;
   }
    
     public int nombredeligneEva(){
               int t=0;
              
 // String req="select count(*) as nombre from etudiant natural join NotesNormal where codeEvaluationsNormal='"+codeEvaluationsNormal+"'";
    //Preparer la requete
     String req="select count(*) as nombre from EvaluationsNormal ";
        try
        {
            //Etablir la connexion
            Connection con =DriverManager.getConnection("jdbc:mysql://localhost/GestionUniversite","root","");
            //Creer Statement
            Statement st=con.createStatement();
            //Executer la requete
            //Creer un objet de type ResultSet
            ResultSet rs=st.executeQuery(req);
            int i=0;
            if(rs.next())
            {
              //data = new String[i][3];
                 int nom = rs.getInt("nombre");
      t=nom;

            }
             
            //Fermer la connexion
            con.close();st.close();
        }
        catch(SQLException ex)
        {
            
        }
        return t;
   }
    
    public String[][] ListerEvaluation(){
       
                int r=nombredeligneEva();
           //   String req="select Id_Etud,Nom_Etud,Prenom_Etud,notenormal from etudiant natural join NotesNormal where codeEvaluationsNormal='"+codeEvaluationsNormal+"'";
           String req="select E.codeEvaluationsNormal , O.Nom_Opt,C.Nom_Cours,S.NomSession,N.Niveau,P.promotion,V.NomVacation,\n" +
"E.Codepalmares,E.TypesNormal,E.DescriptionsNormal,E.CoeficientNormal\n" +
"from Options O,Cours C, Sessionx S , Niveaux N ,palmares P , Vacations V,EvaluationsNormal E where\n" +
"O.Id_Opt=C.Id_Opt and  N.codeniv=C.codeniv and  S.codesession=C.codesession\n" +
" and C.Id_Cours=P.Id_Cours and V.NomVacation=P.NomVacation  and P.codepalmares=E.codepalmares ";
           int t=0;
               String data[][] = new String[r][11];
             
    //Preparer la requete
      
        try
        {
            //Etablir la connexion
            Connection con =DriverManager.getConnection("jdbc:mysql://localhost/GestionUniversite","root","");
            //Creer Statement
            Statement st=con.createStatement();
            //Executer la requete
            //Creer un objet de type ResultSet
            ResultSet rs=st.executeQuery(req);
            int i=0;
            while(rs.next())
            {
                String codeEvaluationsNormal=rs.getString(1);
                String Nom_Opt=rs.getString(2);
               // System.out.println(Nom_Opt);
                String Nom_Cours=rs.getString(3);
                String NomSession=rs.getString(4);
                String Niveau=rs.getString(5);
                String promotion=rs.getString(6);
                String NomVacation=rs.getString(7);
                String Codepalmares=rs.getString(8);
                String TypesNormal=rs.getString(9);
                String DescriptionsNormal=rs.getString(10);
                String CoeficientNormal=rs.getString(11);
             //   System.out.println(CoeficientNormal);
             
      data[i][0] = codeEvaluationsNormal;
        data[i][1] = Nom_Opt;
           data[i][2] = Nom_Cours;
              data[i][3] = NomSession;
                 data[i][4] = Niveau;
                    data[i][5] = promotion;
                       data[i][6] = NomVacation;
                          data[i][7] = Codepalmares;
                             data[i][8] = TypesNormal;
                                data[i][9] = DescriptionsNormal;
                                   data[i][10] = CoeficientNormal;
       
        i++;

            }
             
            //Fermer la connexion
            con.close();st.close();
        }
        catch(SQLException ex)
        {
            
        }
        return data;
   }
    
     public String RechercherNotes( String Id_Etud,String codeEvaluationsNormal){
       String nombre=null;
        //Preparer la requete
       String req="Select notenormal from NotesNormal natural join Etudiant where Id_Etud='"+Id_Etud+"' and codeEvaluationsNormal='"+codeEvaluationsNormal+"'";
    // String req="select Id_Cours from Cours natural join options where Nom_Opt='Genie Electronique' and Nom_Cours='Analyse Numerique'";
         try
        {
            //Etablir la connexion
            Connection con =DriverManager.getConnection("jdbc:mysql://localhost/GestionUniversite","root","");
            //Creer Statement
            Statement st=con.createStatement();
            //Executer la requete
            //Creer un objet de type ResultSet
            ResultSet rs=st.executeQuery(req);
            if(rs.next())
            {
        
                nombre=rs.getString("notenormal"); 
             //   System.out.println("li jwenn li se :"+ nombre);
               
            }
            
            else{
                //   System.out.println("li pa  jwenn li se :"+ nombre);
            }
            
          con.close(); st.close(); 
        }
          catch(SQLException ex)
        {
           
    }
       return nombre;
    }
    //
    
    //
     public String RechercherSommationcoef( String Codepalmares){
       String nombre=null;
        //Preparer la requete
       String req="select sum(CoeficientNormal) as nombre from EvaluationsNormal natural join palmares where codepalmares='"+Codepalmares+"'";
    // String req="select Id_Cours from Cours natural join options where Nom_Opt='Genie Electronique' and Nom_Cours='Analyse Numerique'";
         try
        {
            //Etablir la connexion
            java.sql.Connection con =DriverManager.getConnection("jdbc:mysql://localhost/GestionUniversite","root","");
            //Creer Statement
            java.sql.Statement st=con.createStatement();
            //Executer la requete
            //Creer un objet de type ResultSet
            ResultSet rs=st.executeQuery(req);
            if(rs.next())
            {
        
                nombre=rs.getString("nombre"); 
             //   System.out.println("li jwenn li se :"+ nombre);
               
            }
            
            else{
                //   System.out.println("li pa  jwenn li se :"+ nombre);
            }
            
          con.close(); st.close(); 
        }
          catch(SQLException ex)
        {
           
    }
       return nombre;
    }
        
     public String RechercherNomcours( String Codepalmares){
       String nombre=null;
        //Preparer la requete
       String req="select Nom_Cours from cours natural join palmares where codepalmares='"+Codepalmares+"'";
    // String req="select Id_Cours from Cours natural join options where Nom_Opt='Genie Electronique' and Nom_Cours='Analyse Numerique'";
         try
        {
            //Etablir la connexion
            java.sql.Connection con =DriverManager.getConnection("jdbc:mysql://localhost/GestionUniversite","root","");
            //Creer Statement
            java.sql.Statement st=con.createStatement();
            //Executer la requete
            //Creer un objet de type ResultSet
            ResultSet rs=st.executeQuery(req);
            if(rs.next())
            {
        
                nombre=rs.getString("Nom_Cours"); 
           //     System.out.println("li jwenn li se :"+ nombre);
               
            }
            
            else{
                //   System.out.println("li pa  jwenn li se :"+ nombre);
            }
            
           con.close(); st.close();
        }
          catch(SQLException ex)
        {
           
    }
       return nombre;
    }
     
      public String RechercherNomOption( String Codepalmares){
       String nombre=null;
        //Preparer la requete
       String req="select Nom_Opt from options natural join palmares where codepalmares='"+Codepalmares+"'";
    // String req="select Id_Cours from Cours natural join options where Nom_Opt='Genie Electronique' and Nom_Cours='Analyse Numerique'";
         try
        {
            //Etablir la connexion
            java.sql.Connection con =DriverManager.getConnection("jdbc:mysql://localhost/GestionUniversite","root","");
            //Creer Statement
            java.sql.Statement st=con.createStatement();
            //Executer la requete
            //Creer un objet de type ResultSet
            ResultSet rs=st.executeQuery(req);
            if(rs.next())
            {
                nombre=rs.getString("Nom_Opt"); 
             //   System.out.println("li jwenn li se :"+ nombre); 
            }
            
            else{
                //   System.out.println("li pa  jwenn li se :"+ nombre);
            }
            
           con.close(); st.close();
        }
          catch(SQLException ex)
        {
           
    }
       return nombre;
    }
       public String RechercherCodeEvaluation( String Codepalmares, String TypesNormal, String DescriptionsNormal){
       String codeEvaluationsNormal=null;
        //Preparer la requete
       String req="select codeEvaluationsNormal from EvaluationsNormal where Codepalmares='"+Codepalmares+"' and TypesNormal='"+TypesNormal+"' and DescriptionsNormal='"+DescriptionsNormal+"'";

         try
        {
            //Etablir la connexion
            java.sql.Connection con =DriverManager.getConnection("jdbc:mysql://localhost/GestionUniversite","root","");
            //Creer Statement
            java.sql.Statement st=con.createStatement();
            //Executer la requete
            //Creer un objet de type ResultSet
            ResultSet rs=st.executeQuery(req);
            if(rs.next())
            {
              
                codeEvaluationsNormal=rs.getString("codeEvaluationsNormal"); 
              //  System.out.println("li jwenn li codeev se :"+ codeEvaluationsNormal);
               
            }
            
            else{
                 //  System.out.println("li pa  jwenn li codeev se :"+ codeEvaluationsNormal);
            }
            con.close(); st.close(); 
          
        }
          catch(SQLException ex)
        {
           
    }
       return codeEvaluationsNormal;
    }
         
}


