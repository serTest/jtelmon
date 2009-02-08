 
package olaptest4;

/**
 *
 * @author netlander
 */

import org.palo.api.Connection;
import org.palo.api.Database;
import org.palo.api.Dimension;
import org.palo.api.Hierarchy;
import org.palo.api.Cube;
import org.palo.api.ConnectionFactory;
import org.palo.api.ConnectionConfiguration;
import org.palo.api.Element;
import org.palo.api.Consolidation;
import java.sql.*;
import java.util.Vector;
import java.util.Iterator;



public class testConnection {

static Vector getVectorBySQL006(String SQL_sursa) {
    Vector vS_den = new Vector(1);
    // int index1=0;
    String url_sursa = "jdbc:postgresql://gate.montebanato.ro:5432/pangram_week_2008";
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
    // String SQL_sursa = " SELECT numere_lucru.nrlc_id, numere_lucru.nick, numere_lucru.denumire, numere_lucru.categorie_id, categorie.denumire as categorie_denumire, numere_lucru.grupa_id, numere_lucru.clasa_id FROM numere_lucru, categorie WHERE numere_lucru.categorie_id=categorie.categ_id AND numere_lucru.sw_0='a' AND numere_lucru.categorie_id = '0249' ORDER BY numere_lucru.denumire ";
    ResultSet rs_sursa = null;
    rs_sursa = stmt_sursa.executeQuery(SQL_sursa);
    String illegal_char = " ";
    String legal_char = "";
    String mS_denumire;
    while (rs_sursa.next()) {
        mS_denumire = rs_sursa.getString("nick").replaceAll(illegal_char, legal_char);
        vS_den.add(mS_denumire.trim()) ;
        System.out.println(mS_denumire);
    }
    System.out.println("Transfer OK");
    // System.out.println(Integer.toString(index1));
    rs_sursa.close();
    stmt_sursa.close();
    conn_sursa.close();
    } catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
    }
    return vS_den;
    }

static void SetDimDepoByVector( Vector vS_denumire, String mS_dimensiune){
    Connection conn_dest = ConnectionFactory.getInstance().newConnection("127.0.0.1", "7777", "admin", "admin");
    Database odb = conn_dest.addDatabase("pangram_olap5");
try {
    Vector vS_denDepo = new Vector(1);
    vS_denDepo = vS_denumire;
    Dimension dim1 = odb.addDimension(mS_dimensiune);
    Hierarchy hie1 = dim1.getDefaultHierarchy();
    Iterator vItr = vS_denDepo.iterator();
    String illegal_char = " ";
    String legal_char = "";
    String mS_denumire;
    while (vItr.hasNext()){
        mS_denumire = (String) vItr.next();
        mS_denumire = mS_denumire.replaceAll(illegal_char, legal_char);
        hie1.addElement( mS_denumire , Element.ELEMENTTYPE_NUMERIC);
    }
    Dimension[] dimensions = new Dimension[1];
    dimensions[0]=dim1;
} catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}
}

static void CreateDepos3(){
    // org.palo.api.Connection conn_dest = ConnectionFactory.getInstance().newConnection("127.0.0.1", "7777", "admin", "admin");
    // Database odb = conn_dest.addDatabase("pangram_olap4");
    String SQL_Depozite = " SELECT categ_id,denumire as nick FROM categorie WHERE fisier='numere_lucru' ORDER BY denumire ";
    String SQL_Sursa = SQL_Depozite;
    Vector vS_denDepo = new Vector(1);
    vS_denDepo = getVectorBySQL006(SQL_Sursa);
    SetDimDepoByVector( vS_denDepo, "dimDepo");
}

static void CreateDepos2(){
    // org.palo.api.Connection conn_dest = ConnectionFactory.getInstance().newConnection("127.0.0.1", "7777", "admin", "admin");
    // Database odb = conn_dest.addDatabase("pangram_olap4");
    String SQL_sursa = " SELECT categ_id,denumire as nick FROM categorie WHERE fisier='numere_lucru' ORDER BY denumire ";
    Vector vS_denDepo = new Vector(1);
    vS_denDepo = getVectorBySQL006(SQL_sursa);
    SetDimDepoByVector( vS_denDepo, "dimDepo");
}

static void CreateDepos1(){
    org.palo.api.Connection conn_dest = ConnectionFactory.getInstance().newConnection("127.0.0.1", "7777", "admin", "admin");
    Database odb = conn_dest.addDatabase("pangram_olap4");
try {
    String SQL_sursa = " SELECT categ_id,denumire as nick FROM categorie WHERE fisier='numere_lucru' ORDER BY denumire ";
    Vector vS_denDepo = new Vector(1);
    vS_denDepo = getVectorBySQL006(SQL_sursa);
    Dimension dim1 = odb.addDimension("dimDepo");
    Hierarchy hie1 = dim1.getDefaultHierarchy();
    Iterator vItr = vS_denDepo.iterator();
    String illegal_char = " ";
    String legal_char = "";
    String mS_denumire;
    while (vItr.hasNext()){
        mS_denumire = (String) vItr.next();
        mS_denumire = mS_denumire.replaceAll(illegal_char, legal_char);
        hie1.addElement( mS_denumire , Element.ELEMENTTYPE_NUMERIC);
    }
    Dimension[] dimensions = new Dimension[1];
    dimensions[0]=dim1;
}
catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}
}




static void TheBigVector005() {
    Vector vS_denumire = new Vector(1);
    // int index1=0;
    String SQL_sursa = " SELECT numere_lucru.nrlc_id, numere_lucru.nick, numere_lucru.denumire, numere_lucru.categorie_id, categorie.denumire as categorie_denumire, numere_lucru.grupa_id, numere_lucru.clasa_id FROM numere_lucru, categorie WHERE numere_lucru.categorie_id=categorie.categ_id AND numere_lucru.sw_0='a' AND numere_lucru.categorie_id = '0249' ORDER BY numere_lucru.denumire ";
    vS_denumire = getVectorBySQL006(SQL_sursa);
    ConnectionConfiguration ccfg = new ConnectionConfiguration("127.0.0.1", "7777");
    ccfg.setUser("admin");
    ccfg.setPassword("admin");
    Connection conn = ConnectionFactory.getInstance().newConnection(ccfg);
    Database demo2 =  null;
    try {
        demo2 =  conn.addDatabase("Consolidation4");
    } catch (Exception e) {
        System.err.println("Database already created");
        System.err.println(e);
    }
    Dimension[] dimensions = new Dimension[3];
    Dimension dim1 = demo2.addDimension("dimWarehouse");
    Dimension dim2 = demo2.addDimension("dimYears");
    Dimension dim3 = demo2.addDimension("dimCustomers");
    Hierarchy hie1 = dim1.getDefaultHierarchy();
    Hierarchy hie2 = dim2.getDefaultHierarchy();
    Hierarchy hie3 = dim3.getDefaultHierarchy();
    dimensions[0]=dim1;
    dimensions[1]=dim2;
    dimensions[2]=dim3;
    hie1.addElement("Timisoara", Element.ELEMENTTYPE_NUMERIC);
    hie1.addElement("Bucuresti", Element.ELEMENTTYPE_NUMERIC);
    hie1.addElement("Cluj", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2003", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2004", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2005", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Metro", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Kaufland", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Real", Element.ELEMENTTYPE_NUMERIC);
    Element parent, child;
    int index2=0;
    parent = hie1.getElementByName("Timisoara");
    Consolidation[] consolidations = new Consolidation[vS_denumire.size()];
    Iterator vItr = vS_denumire.iterator();
    while (vItr.hasNext()){
        child = hie1.addElement((String) vItr.next(), Element.ELEMENTTYPE_NUMERIC);
        consolidations[index2] = hie1.newConsolidation(child, parent, 1);
        index2=index2+1;
    }
    System.out.println(Integer.toString(index2));
    parent.updateConsolidations(consolidations);
    try {
        Cube cube = null;
        cube = demo2.addCube("cubeSales", dimensions);
    } catch (Exception e) {
        System.err.println("Cube creation failed");
        System.err.println(e);
    }
}

static void TheBigCube001() {
    // Agentii TM
    String[] aS_denumire = new String[100];
    int index1=0;
    String url_sursa = "jdbc:postgresql://gate.montebanato.ro:5432/pangram_week_2008";
    String username_sursa = "postgres";
    String password_sursa = "telinit";
    java.sql.Connection conn_sursa = null;
    java.sql.Statement stmt_sursa = null;
    try {
        Class.forName("org.postgresql.Driver");
        System.out.println("Driver PostgreSQL OK");
    } catch (Exception e) {
        System.err.println("Failed to load Postgres Driver");
    }
    try {
        conn_sursa = DriverManager.getConnection(url_sursa,username_sursa,password_sursa);
        stmt_sursa = conn_sursa.createStatement();
        System.out.println("Connection to source PostgreSQL OK");
    } catch (Exception e) {
        System.err.println("Connection to Postgres failed");
    }
    //org.palo.api.Connection conn_dest = ConnectionFactory.getInstance().newConnection("127.0.0.1", "7777", "admin", "admin");
    //Database odb = conn_dest.addDatabase("pangram_olap");
// SQL1 AGENTI:
// SELECT nrlc_id, nick, denumire, categorie_id, grupa_id, clasa_id
     // FROM numere_lucru WHERE sw_0='a' ORDER BY denumire,nick
// SQL2 Depozite
    // SELECT categ_id,denumire FROM categorie WHERE fisier='numere_lucru' ORDER BY denumire
// SQL3 AGENTI & DEPOZITE :
// SELECT numere_lucru.nrlc_id, numere_lucru.nick, numere_lucru.denumire, numere_lucru.categorie_id, 
        // categorie.denumire as categorie_denumire, numere_lucru.grupa_id, numere_lucru.clasa_id
        // FROM numere_lucru, categorie
        // WHERE numere_lucru.categorie_id=categorie.categ_id AND numere_lucru.sw_0='a'
        // ORDER BY numere_lucru.denumire
// Agentii din TM : [categorie_id = '0249']
// SELECT numere_lucru.nrlc_id, numere_lucru.nick, numere_lucru.denumire, numere_lucru.categorie_id, categorie.denumire as categorie_denumire, numere_lucru.grupa_id, numere_lucru.clasa_id FROM numere_lucru, categorie WHERE numere_lucru.categorie_id=categorie.categ_id AND numere_lucru.sw_0='a' AND numere_lucru.categorie_id = '0249' ORDER BY numere_lucru.denumire
try {
    String SQL_sursa = " SELECT numere_lucru.nrlc_id, numere_lucru.nick, numere_lucru.denumire, numere_lucru.categorie_id, categorie.denumire as categorie_denumire, numere_lucru.grupa_id, numere_lucru.clasa_id FROM numere_lucru, categorie WHERE numere_lucru.categorie_id=categorie.categ_id AND numere_lucru.sw_0='a' AND numere_lucru.categorie_id = '0249' ORDER BY numere_lucru.denumire ";
    ResultSet rs_sursa = null;
    rs_sursa = stmt_sursa.executeQuery(SQL_sursa);
    String illegal_char = " ";
    String legal_char = "";
    String mS_denumire;
    while (rs_sursa.next()) {
        // mS_denumire = "T"+rs_sursa.getString("nick");
        mS_denumire = rs_sursa.getString("nick");
        aS_denumire[index1]= mS_denumire.replaceAll(illegal_char, legal_char);
        // String mS_den3 = mS_den2;
        // System.out.println(mS_denumire);

        System.out.println(aS_denumire[index1]);
        // dim1.addElement(mS_den2, Element.ELEMENTTYPE_NUMERIC);
        index1=index1+1;
    }
    System.out.println("Transfer OK");
    System.out.println(Integer.toString(index1));
    rs_sursa.close();
    stmt_sursa.close();
    conn_sursa.close();

    } catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}
    ConnectionConfiguration ccfg = new ConnectionConfiguration("127.0.0.1", "7777");
    ccfg.setUser("admin");
    ccfg.setPassword("admin");
    Connection conn = ConnectionFactory.getInstance().newConnection(ccfg);
    // Database demo2 =  conn.getDatabaseByName("Demo");
    Database demo2 =  null;
    try {
        demo2 =  conn.addDatabase("Consolidation2");
    } catch (Exception e) {
        System.err.println("Cube already created");
    }
    Dimension[] dimensions = new Dimension[3];
    Dimension dim1 = demo2.addDimension("dimWarehouse");
    Dimension dim2 = demo2.addDimension("dimYears");
    Dimension dim3 = demo2.addDimension("dimCustomers");
    Hierarchy hie1 = dim1.getDefaultHierarchy();
    Hierarchy hie2 = dim2.getDefaultHierarchy();
    Hierarchy hie3 = dim3.getDefaultHierarchy();
    dimensions[0]=dim1;
    dimensions[1]=dim2;
    dimensions[2]=dim3;
    hie1.addElement("Timisoara", Element.ELEMENTTYPE_NUMERIC);
    hie1.addElement("Bucuresti", Element.ELEMENTTYPE_NUMERIC);
    hie1.addElement("Cluj", Element.ELEMENTTYPE_NUMERIC);
    // hie1.rename("UOU");
    hie2.addElement("2003", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2004", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2005", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Metro", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Kaufland", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Real", Element.ELEMENTTYPE_NUMERIC);

    Element parent, child;
    // Element[] elements = new Element[index1];
    int index2;
    parent = hie1.getElementByName("Timisoara");
    Consolidation[] consolidations = new Consolidation[index1];
    for(index2=0;index2<index1;index2++ ){
        child = hie1.addElement(aS_denumire[index2], Element.ELEMENTTYPE_NUMERIC);
        consolidations[index2] = hie1.newConsolidation(child, parent, 1);
    }
    parent.updateConsolidations(consolidations);

    Cube cube = null;
    try {
        cube = demo2.addCube("cubeSales", dimensions);
    } catch (Exception e) {
        System.err.println("Cube creation failed");
    }
    //String COORDINATES1[] = {"1stAgent","2003","Metro"};
    //cube.setData(COORDINATES1, new Double(247.26));
    //String COORDINATES2[] = {"2ndAgent","2003","Metro"};
    //cube.setData(COORDINATES2, new Double(11.01));

}

protected void testConnection(){

String url_sursa = "jdbc:postgresql://192.168.1.200:5432/pangram_main_2008";
String username_sursa = "postgres";
String password_sursa = "telinit";
String url_destinatie = "jdbc:postgresql://192.168.1.200:5432/retur_marfa";
String username_destinatie = "postgres";
String password_destinatie = "telinit";

try {
    Class.forName("org.postgresql.Driver");
    System.out.println("Driver PostgreSQL OK");
} catch (Exception e) {
    System.err.println("Failed to load Postgres Driver");
}

java.sql.Connection conn_sursa = null;
java.sql.Connection conn_destinatie = null;
Statement stmt_sursa = null;
Statement stmt_destinatie = null;

try {
    conn_sursa = DriverManager.getConnection(url_sursa,username_sursa,password_sursa);
    stmt_sursa = conn_sursa.createStatement();
    conn_destinatie = DriverManager.getConnection(url_destinatie,username_destinatie,password_destinatie);
    stmt_destinatie = conn_destinatie.createStatement();
    System.out.println("Connection to source PostgreSQL OK");
} catch (Exception e) {
    System.err.println("Connection to Postgres failed");
}

try {

    String SQL_sursa = "select nrdoc, data, valoare, reclamant, cant, um, simb, denmat, cls "+
        "from depit_aviz_2008";
    ResultSet rs_sursa = null;
    rs_sursa = stmt_sursa.executeQuery(SQL_sursa);
    String SQL_destinatie = "delete from aviz_2009";
    stmt_destinatie.execute(SQL_destinatie);
    String illegal_char = "'";
    String legal_char = " ";
    while (rs_sursa.next()) {
        String mS_nrdoc = rs_sursa.getString("nrdoc");
        Date mD_data = rs_sursa.getDate("data");
        String mS_data = mD_data.toString();
        double md_valoare = rs_sursa.getDouble("valoare");
        // int mi_valoare = rs_sursa.getInt("valoare")
        String mS_reclamant = rs_sursa.getString("reclamant");
        double md_cant = rs_sursa.getDouble("cant");
        double md_um = rs_sursa.getDouble("um");
        String mS_simb = rs_sursa.getString("simb");
        String mS_denmat = rs_sursa.getString("denmat");
        mS_denmat = mS_denmat.replaceAll(illegal_char, legal_char);
        System.out.println(mS_denmat);
        String mS_cls = rs_sursa.getString("cls");
        SQL_destinatie = "insert into aviz_2009(nrdoc, data, valoare, reclamant, cant, um, simb, denmat, cls) values "+
            "('" +mS_nrdoc+ "', '" +mS_data+ "' , "+md_valoare+ ",'" +mS_reclamant+ "'," +md_cant+
            "," +md_um+ ",'" +mS_simb+ "','" +mS_denmat+ "','" +mS_cls+"')";
        System.out.println(SQL_destinatie);
        stmt_destinatie.execute(SQL_destinatie);
    }
    System.out.println("Transfer OK");
    // m_vector.addElement(new StockData(symbol, name, last, open, change, changePr, volume));
    rs_sursa.close();
    stmt_sursa.close();
    conn_sursa.close();
    stmt_destinatie.close();
    conn_destinatie.close();
}

catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}

}

public static void main(String[] args) {
        // CreateTheCube();
        // CreateCube003();
        // AboutCube003();
        // TheBigCube001();
        // VectorDemo test = new VectorDemo();
        
    // TheBigVector005();
    // CreateDepos1();
    CreateDepos3();
}
}
