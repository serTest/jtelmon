 
package olaptest5;

/**
 *
 * @author netlander
 */

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.palo.api.Connection;
import org.palo.api.Database;
import org.palo.api.Dimension;
import org.palo.api.Hierarchy;
//import org.palo.api.Cube;
import org.palo.api.ConnectionFactory;
//import org.palo.api.ConnectionConfiguration;
import org.palo.api.Element;
import org.palo.api.Consolidation;



public class mbDepozit {

 static class Depozit{
    private String denumire;
    public ArrayList <String> listOfAgents;
    public Depozit(String denDep){
        this.denumire=denDep;
        this.listOfAgents = new ArrayList <String>();
    }
    public void rename(String denDep){
        this.denumire=denDep;
    }
    String getDen(){
        return this.denumire;
    }
    public void addAg(String denAg){
        // this.denumire=denDep;
        this.listOfAgents.add(denAg);
    }
}

  static void CreateDimDepoAg(){
    // SQL4 AGENTI & DEPOZITE
    String SQL_AgByDep = " SELECT numere_lucru.nrlc_id, numere_lucru.nick, numere_lucru.denumire, " +
             " numere_lucru.categorie_id, categorie.denumire as categorie_denumire, " +
             " numere_lucru.grupa_id, numere_lucru.clasa_id "+
             " FROM numere_lucru, categorie " +
             " WHERE numere_lucru.categorie_id=categorie.categ_id AND numere_lucru.sw_0='a' " +
             " ORDER BY categorie.denumire, numere_lucru.denumire ";
    String SQL_Sursa = SQL_AgByDep;
    List<Depozit> DepoAgList = new ArrayList<Depozit>();
    DepoAgList  =  getDepoAgListBySQL (SQL_Sursa);
    SetDimDepoAgByList(DepoAgList, "DepoAg");
}


  static List<Depozit> getDepoAgListBySQL(String SQL_sursa) {
    List<Depozit> listOfDepos = new ArrayList<Depozit>();
    String url_sursa = "jdbc:postgresql://gate.montebanato.ro:5432/pangram_week_2008";
    // String url_sursa = "jdbc:postgresql://192.168.1.26:5432/pangram_week_2008";
    String username_sursa = "postgres";
    String password_sursa = "telinit";
    java.sql.Connection conn_sursa = null;
    java.sql.Statement stmt_sursa = null;
    try {
        Class.forName("org.postgresql.Driver");
        System.out.println("Driver PostgreSQL OK");
    } catch (Exception e) {
        System.err.println("Failed to load Postgres Driver");
        System.err.println(e);
    }
    try {
        conn_sursa = DriverManager.getConnection(url_sursa,username_sursa,password_sursa);
        stmt_sursa = conn_sursa.createStatement();
        System.out.println("Connection to source PostgreSQL OK");
    } catch (Exception e) {
        System.err.println("Connection to Postgres failed");
        System.err.println(e);
    }
try {
    ResultSet rs_sursa = null;
    rs_sursa = stmt_sursa.executeQuery(SQL_sursa);
    String illegal_char = " ";
    String legal_char = "";
    String mS_denDep =" ", mS_denAg =" ";
    Depozit iDepo = null;
    while (rs_sursa.next()) {
        mS_denAg = rs_sursa.getString("nick").replaceAll(illegal_char, legal_char);
        mS_denDep = rs_sursa.getString("categorie_denumire").replaceAll(illegal_char, legal_char);
        if ( (iDepo !=null) && ( iDepo.getDen().equals(mS_denDep)) ){
            iDepo.listOfAgents.add(mS_denAg);
            if(mS_denDep.trim().equals("ARAD")){
                System.out.println(mS_denAg + "  " + Integer.toString(iDepo.listOfAgents.size()));
            }
        }  
        else{
            iDepo = new Depozit(" ");
            iDepo.rename(mS_denDep.trim());
            listOfDepos.add(iDepo);
            // System.out.println(iDepo.getDen());
        }
    }
    System.out.println("Transfer OK");
    rs_sursa.close();
    stmt_sursa.close();
    conn_sursa.close();
    } catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
    }
    return listOfDepos;
    }

static void SetDimDepoAgByList( List<Depozit> DepoAgList, String mS_dimensiune ){
    Connection conn_dest = ConnectionFactory.getInstance().newConnection("127.0.0.1", "7777", "admin", "admin");
    Database odb = conn_dest.addDatabase("pangram_olap6");
try {
    Iterator vItr2 = DepoAgList.iterator();
    Dimension[] dimensions = new Dimension[1];
    Dimension dim1 = odb.addDimension(mS_dimensiune);
    Hierarchy hie1 = dim1.getDefaultHierarchy();
    dimensions[0]=dim1;
    String illegal_char = " ";
    String legal_char = "";
    String mS_denumire, mS_DenDep;
    ArrayList <String> listOfAgents;
    Element parent, child;
    int index2=0, size=0;
    Consolidation[] consolidations ;
    Iterator vItr = null;
    while (vItr2.hasNext()){
        Depozit iDepo;
        iDepo = (Depozit) vItr2.next();
        mS_denumire = iDepo.getDen();
        if(!mS_denumire.substring(0, 1).equals("0")){
            mS_denumire = mS_denumire.replaceAll(illegal_char, legal_char);
            System.out.println(" __DEPOZIT__ " + mS_denumire);
            hie1.addElement( mS_denumire , Element.ELEMENTTYPE_NUMERIC);
            index2=0;
            parent = hie1.getElementByName(mS_denumire);
            listOfAgents = iDepo.listOfAgents;
            size = listOfAgents.size();
            System.out.println(" __NR.Agenti = " + Integer.toString(size));
            consolidations = new Consolidation[size];
            vItr = listOfAgents.iterator();
            while (vItr.hasNext()){
                mS_DenDep = (String) vItr.next();
                System.out.println(" agent : " + mS_DenDep);
                child = hie1.addElement(mS_DenDep, Element.ELEMENTTYPE_NUMERIC);
                consolidations[index2] = hie1.newConsolidation(child, parent, 1);
                index2=index2+1;
            }
            parent.updateConsolidations(consolidations);
            // break;
        }
    }
} catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}
}

}
