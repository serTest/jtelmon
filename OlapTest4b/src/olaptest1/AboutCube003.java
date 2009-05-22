 /*
 *
 * http://www.jpalo.net/javadoc/org/palo/api/Consolidation.html
 * http://www.jpalo.net/javadoc/org/palo/api/Dimension.html
 * http://www.jpalo.net/javadoc/org/palo/api/Hierarchy.html
 * http://www.jpalo.net/javadoc/overview-tree.html
 * http://www.jpalo.net/javadoc/org/palo/api/class-use/Element.html
 * http://www.jpalo.com/javadoc/org/palo/api/class-use/Consolidation.html
 * http://www.jpalo.net/javadoc/org/palo/api/impl/HierarchyImpl.html
 * http://www.jpalo.net/javadoc/org/palo/api/class-use/Dimension.html
 * http://www.jpalo.net/javadoc/org/palo/api/Axis.html#add(org.palo.api.Hierarchy)
 * http://www.jpalo.com/javadoc/org/palo/api/class-use/VirtualCubeDefinition.html
 * http://www.jpalo.net/javadoc/org/palo/api/class-use/Axis.html
  * http://www.jpalo.de/javadoc/org/palo/api/utils/class-use/ElementPath.html
  * http://www.jpalo.com/javadoc/org/palo/api/CubeView.html#addAxis(java.lang.String,%20java.lang.String)
  * http://www.jpalo.com/javadoc/org/palo/api/CubeView.html
  * http://www.sherito.org/2008/10/22/1224686940000.html
  * http://cwebbbi.spaces.live.com/?_c11_BlogPart_BlogPart=blogview&_c=BlogPart&partqs=cat%3DMDX
  * http://www.jedox.com/en/news/press_jedox/Palo_Open_Source_OLAP_Server_now_supports_MDX_and_Excel_Pivot_Tables.html
  * http://www.jedox.com/assets/images/news/pressekit/jedox_presseinfo_pivot_engl.jpg
  * http://www.olap4j.org/api/org/olap4j/mdx/AxisNode.html
  * http://www.olap4j.org/api/org/olap4j/mdx/HierarchyNode.html
  * http://www.olap4j.org/api/org/olap4j/Axis.html
  * http://sqlblog.com/blogs/mosha/default.aspx
  * http://www.jpalo.net/javadoc/org/palo/api/Connection.html
  * http://www.jpalo.net/javadoc/org/palo/api/ConnectionConfiguration.html
  * http://www.jpalo.net/javadoc/org/palo/api/ConnectionFactory.html
  * http://www.jpalo.net/javadoc/org/palo/api/ConnectionFactory.html
  *
  *
 */
 

package olaptest1;

/**
 *
 * @author Alex
 */


import org.palo.api.Database;
import org.palo.api.Dimension;
import org.palo.api.Hierarchy;
import org.palo.api.Cube;
import org.palo.api.ConnectionFactory;
import org.palo.api.ConnectionConfiguration;
import org.palo.api.Element;
import org.palo.api.Consolidation;
import java.sql.*;




public class AboutCube003 {

    /**
     * @param args the command line arguments
     */



static void AboutCube001() {
    // Database demo =  conn.getDatabaseByName("Demo");
    Database demo = null;
    Dimension views = demo.getDimensionByName("#viewrows");
    Element view = views.getElementByName("1226926093266");
    views.removeElement(view);

}

static void AboutCube003() {
    ConnectionConfiguration ccfg = new ConnectionConfiguration("127.0.0.1", "7777");
    ccfg.setUser("admin");
    ccfg.setPassword("admin");
    org.palo.api.Connection conn = ConnectionFactory.getInstance().newConnection(ccfg);
    // Database demo2 =  conn.getDatabaseByName("Demo");
    Database demo2 =  null;
    try {
        demo2 =  conn.addDatabase("Paloka");
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
    hie1.rename("UOU");
    hie2.addElement("2003", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2004", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2005", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Metro", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Kaufland", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Real", Element.ELEMENTTYPE_NUMERIC);
    Element parent, child;
    Element[] elements = new Element[3];
    parent = hie1.getElementByName("Timisoara");
    Consolidation[] consolidations = new Consolidation[3];
    child = hie1.addElement("1stAgent", Element.ELEMENTTYPE_NUMERIC);
    consolidations[0] = hie1.newConsolidation(child, parent, 1);
    child = hie1.addElement("2ndAgent", Element.ELEMENTTYPE_NUMERIC);
    consolidations[1] = hie1.newConsolidation(child, parent, 1);
    child = hie1.addElement("3rdAgent", Element.ELEMENTTYPE_NUMERIC);
    consolidations[2] = hie1.newConsolidation(child, parent, 1);
    elements[1]=child;
    parent.updateConsolidations(consolidations);
    Cube cube = null;
    try {
        cube = demo2.addCube("cubeSales", dimensions);
    } catch (Exception e) {
        System.err.println("Cube creation failed");
    }
    String COORDINATES1[] = {"1stAgent","2003","Metro"};
    cube.setData(COORDINATES1, new Double(247.26));
    String COORDINATES2[] = {"2ndAgent","2003","Metro"};
    cube.setData(COORDINATES2, new Double(11.01));
    // cube.setData(elements, new Double(247.26));
    // cube.setData(dimensions[0].getHierarchyByName("UOU").getRootElements(), new Double(247.26));
}


static void CreateCube003() {
        org.palo.api.Connection connection = ConnectionFactory.getInstance().newConnection("127.0.0.1", "7777", "admin", "admin");
        Database db2 = connection.addDatabase("PangramOLAP");
        Dimension dim1 = db2.addDimension("dimWarehouse");
        Dimension dim2 = db2.addDimension("dimYears");
        Dimension dim3 = db2.addDimension("dimCustomers");
        dim1.addElement("Timisoara", Element.ELEMENTTYPE_NUMERIC);
        dim1.addElement("Bucuresti", Element.ELEMENTTYPE_NUMERIC);
        dim1.addElement("Cluj", Element.ELEMENTTYPE_NUMERIC);
        dim2.addElement("2003", Element.ELEMENTTYPE_NUMERIC);
        dim2.addElement("2004", Element.ELEMENTTYPE_NUMERIC);
        dim2.addElement("2005", Element.ELEMENTTYPE_NUMERIC);
        dim3.addElement("Metro", Element.ELEMENTTYPE_NUMERIC);
        dim3.addElement("Kaufland", Element.ELEMENTTYPE_NUMERIC);
        dim3.addElement("Real", Element.ELEMENTTYPE_NUMERIC);
        Dimension[] dimensions = new Dimension[3];
        dimensions[0]=dim1;
        dimensions[1]=dim2;
        dimensions[2]=dim3;
        Element parent, child;
        parent = dim3.getElementByName("Real");
        Consolidation[] consolidations = new Consolidation[3];
        child = dim3.addElement("1st Element", Element.ELEMENTTYPE_NUMERIC);
        consolidations[0] = dim3.newConsolidation(child, parent, 1);
        child = dim3.addElement("2nd Element", Element.ELEMENTTYPE_NUMERIC);
        consolidations[1] = dim3.newConsolidation(child, parent, 1);
        child = dim3.addElement("3rd Element", Element.ELEMENTTYPE_NUMERIC);
        consolidations[2] = dim3.newConsolidation(child, parent, 1);
        parent.updateConsolidations(consolidations);
        Cube cube = db2.addCube("cubeSales", dimensions);
        String COORDINATES[] = {"Timisoara","2004","Metro"};
        cube.setData(COORDINATES, new Double(247.26));
}


static void CreateTheCube(){
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

    org.palo.api.Connection conn_dest = ConnectionFactory.getInstance().newConnection("127.0.0.1", "7777", "admin", "admin");
    Database odb = conn_dest.addDatabase("pangram_olap");

try {
    // SELECT categ_id,denumire FROM categorie WHERE fisier='numere_lucru' ORDER BY denumire ;
    // String SQL_sursa = "select nrdoc, data, valoare, reclamant, cant, um, simb, denmat, cls "+
    //     "from depit_aviz_2008";
    String SQL_sursa = " SELECT categ_id,denumire FROM categorie WHERE fisier='numere_lucru' ORDER BY denumire ";
    ResultSet rs_sursa = null;
    rs_sursa = stmt_sursa.executeQuery(SQL_sursa);

    org.palo.api.Dimension dim1 = odb.addDimension("dimWarehouse");
    org.palo.api.Dimension dim2 = odb.addDimension("dimYears");
    org.palo.api.Dimension dim3 = odb.addDimension("dimCustomers");

    String illegal_char = " ";
    String legal_char = "";
    while (rs_sursa.next()) {
        String mS_denumire = rs_sursa.getString("denumire");
        String mS_den2 = mS_denumire.replaceAll(illegal_char, legal_char);
        // String mS_den3 = mS_den2;
        System.out.println(mS_denumire);
        dim1.addElement(mS_den2, Element.ELEMENTTYPE_NUMERIC);
    }
    System.out.println("Transfer OK");
    // m_vector.addElement(new StockData(symbol, name, last, open, change, changePr, volume));
    rs_sursa.close();
    stmt_sursa.close();
    conn_sursa.close();

        dim2.addElement("2003", Element.ELEMENTTYPE_NUMERIC);
        dim2.addElement("2004", Element.ELEMENTTYPE_NUMERIC);
        dim2.addElement("2005", Element.ELEMENTTYPE_NUMERIC);
        dim3.addElement("Metro", Element.ELEMENTTYPE_NUMERIC);
        dim3.addElement("Kaufland", Element.ELEMENTTYPE_NUMERIC);
        dim3.addElement("Real", Element.ELEMENTTYPE_NUMERIC);

        Dimension[] dimensions = new Dimension[3];
        dimensions[0]=dim1;
        dimensions[1]=dim2;
        dimensions[2]=dim3;

        Cube cube = odb.addCube("cubeSales", dimensions);
        String COORDINATES[] = {"Timisoara","2004","Metro"};
        cube.setData(COORDINATES, new Double(247.26));

}

catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}

}

private void CreateCube001(java.awt.event.ActionEvent evt) {
        //CreateCube_ActionPerformed
        org.palo.api.Connection connection = ConnectionFactory.getInstance().newConnection("127.0.0.1", "7777", "admin", "admin");
        // http://forum.palo.net/thread.php?postid=1428
        //Database db1 = connection.getDatabaseByName("whDBj");
        //Cube cube = db1.getCubeByName("cubeSales");
        //db1.removeCube(cube);

        Database db2 = connection.addDatabase("PangramOLAP");
        Dimension dim1 = db2.addDimension("dimWarehouse");
        Dimension dim2 = db2.addDimension("dimYears");
        Dimension dim3 = db2.addDimension("dimCustomers");
        // dim3.SUBDIMENSION.Hierarchy ?

/*
        dim1.addElement("Timisoara", Element.ELEMENTTYPE_STRING);
        dim1.addElement("Bucuresti", Element.ELEMENTTYPE_STRING);
        dim1.addElement("Cluj", Element.ELEMENTTYPE_STRING);
        dim2.addElement("2003", Element.ELEMENTTYPE_STRING);
        dim2.addElement("2004", Element.ELEMENTTYPE_STRING);
        dim2.addElement("2005", Element.ELEMENTTYPE_STRING);
        dim3.addElement("Metro", Element.ELEMENTTYPE_STRING);
        dim3.addElement("Kaufland", Element.ELEMENTTYPE_STRING);
        dim3.addElement("Real", Element.ELEMENTTYPE_STRING);
*/
        dim1.addElement("Timisoara", Element.ELEMENTTYPE_NUMERIC);
        dim1.addElement("Bucuresti", Element.ELEMENTTYPE_NUMERIC);
        dim1.addElement("Cluj", Element.ELEMENTTYPE_NUMERIC);
        dim2.addElement("2003", Element.ELEMENTTYPE_NUMERIC);
        dim2.addElement("2004", Element.ELEMENTTYPE_NUMERIC);
        dim2.addElement("2005", Element.ELEMENTTYPE_NUMERIC);
        dim3.addElement("Metro", Element.ELEMENTTYPE_NUMERIC);
        dim3.addElement("Kaufland", Element.ELEMENTTYPE_NUMERIC);
        dim3.addElement("Real", Element.ELEMENTTYPE_NUMERIC);

        Dimension[] dimensions = new Dimension[3];
        dimensions[0]=dim1;
        dimensions[1]=dim2;
        dimensions[2]=dim3;

        Cube cube = db2.addCube("cubeSales", dimensions);
        String COORDINATES[] = {"Timisoara","2004","Metro"};
        cube.setData(COORDINATES, new Double(247.26));
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
        // TODO code application logic here
        // CreateTheCube();
        // CreateCube003();
        AboutCube003();
    }

}
