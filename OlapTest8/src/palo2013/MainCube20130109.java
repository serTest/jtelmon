package palo2013;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * http://www.jedox.com/community/palo-forum/index.php?page=Thread&threadID=673
 *  ? palo C Api ! 
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 
 *  http://sourceforge.net/projects/palokettleplug/
 *  ! Kettle Pentaho ETL ? 
 *  http://oxsala.googlecode.com/svn-history/r21/trunk/WebPalo/src/java/palo/PaloManager.java
 *   ? WebPalo written in Java !
 * 
 *  netlander @ 2009-02.03 + 2012-12-12
 *  Licence Type : GPL v3
 *  http://subversion.netbeans.org/
 *  http://pantestmb.wiki.zoho.com/Subversion.html
 *  http://www.flickr.com/photos/24834074@N04/3244400077/sizes/o/
 *  http://www.flickr.com/photos/24834074@N04/3245228168/sizes/o/
 *  http://www.flickr.com/photos/24834074@N04/3249769952/sizes/o/
 *  http://www.flickr.com/photos/24834074@N04/3249770060/sizes/o/
 *  http://jtelmon.googlecode.com/svn/
 *  OlapTest7b, JTelMob875, RubyApplication1
 * 
 *  MainCube20130109 : smallCube4();
 * 
 * 
 * http://palo.svn.sourceforge.net/viewvc/palo/molap/client/3.X/libraries/Java/legacy/paloapi/doc/
 * 
 */

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.palo.api.Connection;
import org.palo.api.ConnectionConfiguration;
import org.palo.api.Database;
import org.palo.api.Dimension;
import org.palo.api.Hierarchy;
import org.palo.api.ConnectionFactory;
import org.palo.api.Element;
import org.palo.api.Consolidation;
import org.palo.api.Cube;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainCube20130109 {

    static String url_sursa_postgres1 = "jdbc:postgresql://gate.montebanato.ro:5432/pangram_week_2008";
    static String url_sursa_postgres2 = "jdbc:postgresql://192.168.61.207:5432/pangram_main_2008";
    static String url_sursa_postgres  = url_sursa_postgres2;
    static String olap_db_name        = "olap8_2013feb";
    static String olap_server_ip1      = "192.168.61.8";
    static String olap_server_ip2      = "192.168.61.21";
    static String olap_server_ip      = olap_server_ip1;
    static String olap_server_port1    = "7777";
    static String olap_server_port2    = "7921";
    static String olap_server_port    = olap_server_port1;
    static String olap_server_user    = "admin";
    static String olap_server_pass    = "admin";
    
static class Produs{
    private String denumire;
    private String clasa;
    private String grupa;
    private String categorie;
    public Produs(String denProd){
        this.denumire=denProd;
        this.clasa=null;
        this.categorie=null;
        this.grupa=null;
    }
    public Produs(String denProd, String denCls, String denCat, String denGru){
        this.denumire=denProd;
        this.clasa=denCls;
        this.categorie=denCat;
        this.grupa=denGru;
    }

    public void rename(String denProd){
        this.denumire=denProd;
    }
    String getDenumire(){
        return this.denumire;
    }
    public void addClasa(String denCls){
        this.clasa=denCls;
    }
    String getClasa(){
        return this.clasa;
    }
    public void addCategorie(String denCat){
        this.categorie=denCat;
    }
    String getCategorie(){
        return this.categorie;
    }
    public void addGrupa(String denGru){
        this.grupa=denGru;
    }
    String getGrupa(){
        return this.grupa;
    }
}

static class Client{
    private String id;
    private String denumire;
    private String clasa;
    private String grupa;
    private String categorie;
    public Client(String denCli){
        this.denumire=denCli;
        this.clasa=null;
        this.categorie=null;
        this.grupa=null;
    }
    public Client(String sid, String denCli, String denCls, String denCat, String denGru){
        this.denumire=denCli;
        this.clasa=denCls;
        this.categorie=denCat;
        this.grupa=denGru;
        this.id=sid;
    }

    public void rename(String denDep){
        this.denumire=denDep;
    }
    String getID(){
        return this.id;
    }
    String getDenumire(){
        return this.denumire;
    }
    public void addClasa(String denCls){
        this.clasa=denCls;
    }
    String getClasa(){
        return this.clasa;
    }
    public void addCategorie(String denCat){
        this.categorie=denCat;
    }
    String getCategorie(){
        return this.categorie;
    }
    public void addGrupa(String denGru){
        this.grupa=denGru;
    }
    String getGrupa(){
        return this.grupa;
    }
}


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


static void SetDimProductByList1( List<Produs> ProductList, String mS_dimensiune ){
    Connection conn_dest = ConnectionFactory.getInstance().newConnection(olap_server_ip, olap_server_port, olap_server_user, olap_server_pass);
    Database odb = conn_dest.getDatabaseByName(olap_db_name);
    System.out.println(" START INSERTING PRODUCTS INTO PALO " );
try {
    Iterator vItr2 = ProductList.iterator();
    Dimension[] dimensions = new Dimension[1];
    Dimension dim1 = odb.addDimension(mS_dimensiune);
    Hierarchy hie1 = dim1.getDefaultHierarchy();
    dimensions[0]=dim1;
    String illegal_char = " ";
    String legal_char = "";
    String mS_denumire;
    ArrayList <String> listOfProducts;
    Element parent, child;
    int index2=0, size=0;
    Consolidation[] consolidations ;
    Iterator vItr = null;
    while (vItr2.hasNext()){
        Produs iProd;
        iProd = (Produs) vItr2.next();
        mS_denumire = iProd.getDenumire();
        if(!mS_denumire.substring(0, 1).equals("0")){
            mS_denumire = mS_denumire.replaceAll(illegal_char, legal_char);
            System.out.println(" __PRODUS__ " + mS_denumire);
            hie1.addElement( mS_denumire , Element.ELEMENTTYPE_NUMERIC);
        }
    }
} catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}
}


  static void CreateDimProduct(){
      /*
select nomstoc.stoc_id, nomstoc.simbol, nomstoc.denumire, 
clasa.denumire as clasa, grupa.denumire as grupa, 
categorie.denumire as categorie from nomstoc, categorie, grupa, clasa  
where nomstoc.sw_0='a' AND  
nomstoc.categorie_id=categorie.categ_id  
AND nomstoc.grupa_id=grupa.grupa_id  
AND nomstoc.clasa_id=clasa.clasa_id  
ORDER BY clasa, grupa, categorie  
* 
select distinct n.stoc_id,n.simbol,n.denumire,n.categorie_id,n.grupa_id,n.clasa_id,cl.denumire as clasa,gr.denumire as grupa
from facturi_v_c f 
inner join facturi_cv_c c on f.fact_id=c.fact_id 
inner join nomstoc n on c.stoc_id=n.stoc_id 
inner join categorie cat on n.categorie_id=cat.categ_id
inner join grupa gr on n.grupa_id=gr.grupa_id
inner join clasa cl on n.clasa_id=cl.clasa_id
where f.data_f>'2013-01-01'
       */
    String SQL_Product = " select distinct n.stoc_id,n.simbol,n.denumire,n.categorie_id,n.grupa_id,n.clasa_id,cl.denumire as clasa,gr.denumire as grupa, cat.denumire as categorie from facturi_v_c f inner join facturi_cv_c c on f.fact_id=c.fact_id inner join nomstoc n on c.stoc_id=n.stoc_id inner join categorie cat on n.categorie_id=cat.categ_id inner join grupa gr on n.grupa_id=gr.grupa_id inner join clasa cl on n.clasa_id=cl.clasa_id where f.data_f>'2013-01-01' " ;
    String SQL_Sursa = SQL_Product;
    List<Produs> ProductList = new ArrayList<Produs>();
    ProductList  =  getProductListBySQL (SQL_Sursa);
    SetDimProductByList1( ProductList, "Produse" );
}

  static List<Produs> getProductListBySQL(String SQL_sursa) {
    List<Produs> ProdusList = new ArrayList<Produs>();
    Produs iProd = null;
    String url_sursa = url_sursa_postgres;
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
        System.out.println("Connection to " + url_sursa + " OK");
    } catch (Exception e) {
        System.err.println("Connection to Postgres failed");
        System.err.println(e);
    }
try {
    ResultSet rs_sursa = null;
    rs_sursa = stmt_sursa.executeQuery(SQL_sursa);
    String illegal_char = " ";
    String legal_char = "-";
    String mS_denCli =" ", mS_denCls =" ", mS_denCat =" ", mS_denGru =" ";
    Client iClient = null;
    while (rs_sursa.next()) {
        mS_denCli = rs_sursa.getString("denumire").trim().replaceAll(illegal_char, legal_char)+"---"+rs_sursa.getString("stoc_id");
        mS_denCat = rs_sursa.getString("categorie").replaceAll(illegal_char, legal_char);
        mS_denGru = rs_sursa.getString("grupa").replaceAll(illegal_char, legal_char);
        mS_denCls = rs_sursa.getString("clasa").replaceAll(illegal_char, legal_char);
        iProd = new Produs(mS_denCli);
        ProdusList.add(iProd);
        System.out.println(mS_denCli + "  " + mS_denCls + "  " + mS_denGru + "  " + mS_denCat);
    }
    System.out.println(" OK : products transferred from postgres ! ");
    rs_sursa.close();
    stmt_sursa.close();
    conn_sursa.close();
    } catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
    }
    return ProdusList;
    }



  static void CreateDimCustomer(){
      /*
         SELECT tert_id, terti.denumire as client,
         clasa.denumire as clasa, 
         grupa.denumire as grupa, 
         categorie.denumire as categorie 
         FROM  terti, categorie, grupa, clasa 
         WHERE terti.categorie_id=categorie.categ_id 
	     AND terti.grupa_id=grupa.grupa_id 
	     AND terti.clasa_id=clasa.clasa_id 
	     AND terti.sw_0='a'  
         ORDER BY clasa, grupa, categorie 
         * 
select distinct t.cui,t.sediu as denumire,t.categorie_id,t.grupa_id,t.clasa_id  
from facturi_v_c f 
inner join vterti t on f.tert_id=t.tert_id 
inner join categorie cat on t.categorie_id=cat.categ_id 
inner join grupa gr on t.grupa_id=gr.grupa_id 
inner join clasa cl on t.clasa_id=cl.clasa_id 
where f.data_f>'2012-01-01' 
* 
select distinct t.tert_id,t.cui,t.plt,t.denumire as client, cat.denumire as categorie, gr.denumire as grupa, cl.denumire as clasa , t.categorie_id,t.grupa_id,t.clasa_id  
from facturi_v_c f inner join vterti t on f.tert_id=t.tert_id 
inner join categorie cat on t.categorie_id=cat.categ_id 
inner join grupa gr on t.grupa_id=gr.grupa_id 
inner join clasa cl on t.clasa_id=cl.clasa_id 
where f.data_f>'2012-01-01' order by 2
       */
    String SQL_Customer = " select distinct t.tert_id,t.cui,t.plt,t.denumire as client, cat.denumire as categorie, gr.denumire as grupa, cl.denumire as clasa , t.categorie_id,t.grupa_id,t.clasa_id from facturi_v_c f inner join vterti t on f.tert_id=t.tert_id inner join categorie cat on t.categorie_id=cat.categ_id inner join grupa gr on t.grupa_id=gr.grupa_id inner join clasa cl on t.clasa_id=cl.clasa_id where f.data_f>'2012-01-01' order by 2 ";
    String SQL_Sursa = SQL_Customer;
    List<Client> CustomerList = new ArrayList<Client>();
    CustomerList  =  getCustomerListBySQL (SQL_Sursa);
    SetDimCustomerByList(CustomerList, "Customer");
}


  static void SetDimCustomerByList( List<Client> CustomerList, String mS_dimensiune ){
    Connection conn_dest = ConnectionFactory.getInstance().newConnection(olap_server_ip, olap_server_port, olap_server_user, olap_server_pass);
    Database odb = conn_dest.getDatabaseByName(olap_db_name);
try {
    Iterator vItr2 = CustomerList.iterator();
    Integer iNoCust = new Integer(CustomerList.size());
    // Integer iIndex = new Integer(1);
    Dimension[] dimensions = new Dimension[1];
    Dimension dim1 = odb.addDimension(mS_dimensiune);
    Hierarchy hie1 = dim1.getDefaultHierarchy();
    dimensions[0]=dim1;
    String illegal_char = " ";
    String legal_char = "-";
    String mS_denumire;
    String mS_id;
    Element parent, child;
    int index1=0, size=0;
    Consolidation[] consolidations ;
    Iterator vItr = null;
    // 2008-02.08 ~ new variable iClient;
    System.out.println("   NR.Clienti =  " + iNoCust.toString());
    while (vItr2.hasNext()){
        index1=index1+1;
        Client iClient;
        iClient = (Client) vItr2.next();
        mS_denumire = iClient.getDenumire();
        mS_id= iClient.getID();
        //if(!mS_denumire.substring(0, 1).equals("0")){
            // mS_denumire = mS_denumire.replaceAll(illegal_char, legal_char);
            // mS_id = mS_id.replaceAll(illegal_char, legal_char);
            System.out.println(" __Client_" + index1 + "  " + mS_denumire);
            mS_denumire = mS_denumire.concat("---"+mS_id);
            hie1.addElement( mS_denumire , Element.ELEMENTTYPE_NUMERIC);
        //}
    }
} catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}
}


  static List<Client> getCustomerListBySQL(String SQL_sursa) {
    List<Client> CustomerList = new ArrayList<Client>();
    String url_sursa = url_sursa_postgres;
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
    String legal_char = "-";
    String mS_iD= " ", mS_denCli =" ", mS_denCls =" ", mS_denCat =" ", mS_denGru =" ";
    Client iClient = null;
    while (rs_sursa.next()) {
        mS_iD = rs_sursa.getString("tert_id").trim().replaceAll(illegal_char, legal_char);
        mS_denCli = rs_sursa.getString("client").trim().replaceAll(illegal_char, legal_char);
        mS_denCat = rs_sursa.getString("categorie").trim().replaceAll(illegal_char, legal_char);
        mS_denGru = rs_sursa.getString("grupa").trim().replaceAll(illegal_char, legal_char);
        mS_denCls = rs_sursa.getString("clasa").trim().replaceAll(illegal_char, legal_char);
        System.out.println(mS_denCli + "  " + mS_denCls + "  " + mS_denGru + "  " + mS_denCat);
        iClient = new Client(mS_iD,mS_denCli,mS_denCls,mS_denCat,mS_denGru);
        CustomerList.add(iClient);
    }
    System.out.println("Transfer OK");
    rs_sursa.close();
    stmt_sursa.close();
    conn_sursa.close();
    } catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
    }
    return CustomerList;
    }



  static void CreateDimDepoAg(){
      /*
    SQL4 AGENTI & DEPOZITE
    String SQL_AgByDep = " SELECT numere_lucru.nrlc_id, numere_lucru.nick, numere_lucru.denumire as denumire_agent, " +
             " numere_lucru.categorie_id, categorie.denumire as categorie_depozit, " +
             " numere_lucru.grupa_id, numere_lucru.clasa_id "+
             " FROM numere_lucru, categorie " +
             " WHERE numere_lucru.categorie_id=categorie.categ_id AND numere_lucru.sw_0='a' " +
             " ORDER BY categorie.denumire, numere_lucru.denumire ";
select distinct nl.nrlc_id,nl.nick,nl.denumire as dnumire_agent,cat.denumire as categorie_depozit,nl.categorie_id,nl.grupa_id,nl.clasa_id  
from facturi_v_c f 
inner join numere_lucru nl on f.nrlc_id=nl.nrlc_id 
inner join categorie cat on nl.categorie_id=cat.categ_id
where f.data_f>'2012-01-01' order by 3
     */
    String SQL_AgByDep = "select distinct nl.nrlc_id,nl.nick,nl.denumire as denumire_agent,cat.denumire as categorie_depozit,nl.categorie_id,nl.grupa_id,nl.clasa_id from facturi_v_c f inner join numere_lucru nl on f.nrlc_id=nl.nrlc_id inner join categorie cat on nl.categorie_id=cat.categ_id where f.data_f>'2012-01-01' order by 4,3  ";
    String SQL_Sursa = SQL_AgByDep;
    List<Depozit> DepoAgList = new ArrayList<Depozit>();
    DepoAgList  =  getDepoAgListBySQL (SQL_Sursa);
    SetDimDepoAgByList(DepoAgList, "DepoAg");
}


  static List<Depozit> getDepoAgListBySQL(String SQL_sursa) {
    List<Depozit> listOfDepos = new ArrayList<Depozit>();
    String url_sursa = url_sursa_postgres;
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
    String legal_char = "-";
    String mS_denDep =" ", mS_denAg =" ";
    Depozit iDepo = null;
    while (rs_sursa.next()) {
        mS_denAg = rs_sursa.getString("denumire_agent").trim().replaceAll(illegal_char, legal_char)+"---"+rs_sursa.getString("nrlc_id");
        mS_denDep = rs_sursa.getString("categorie_depozit").replaceAll(illegal_char, legal_char);
        if ( (iDepo !=null) && ( iDepo.getDen().equals(mS_denDep)) ){
            iDepo.listOfAgents.add(mS_denAg);
            if(mS_denDep.trim().equals("RESITA")){
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
    Connection conn_dest = ConnectionFactory.getInstance().newConnection(olap_server_ip, olap_server_port, olap_server_user, olap_server_pass);
    Database odb = conn_dest.getDatabaseByName(olap_db_name);
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

static void CreatePaloDB( ){
    Connection conn_dest = ConnectionFactory.getInstance().newConnection(olap_server_ip, olap_server_port, olap_server_user, olap_server_pass);
    try {
        Database odb = conn_dest.addDatabase(olap_db_name);
    } catch (Exception e) {
        System.err.println("OLAP database " + olap_db_name + " already created ! ");
    }

}


static void smallCube1() {
    // String[] aS_denumire = new String[100];
    ConnectionConfiguration ccfg = new ConnectionConfiguration(olap_server_ip, olap_server_port);
    ccfg.setUser(olap_server_user);
    ccfg.setPassword(olap_server_pass);
    Connection conn = ConnectionFactory.getInstance().newConnection(ccfg);
    Database demo2 =  null;
    try {
        demo2 =  conn.addDatabase("Pangram1");
    } catch (Exception e) {
        System.err.println("Cube already created");
        System.exit(1);
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
    Cube cube = null;
    try {
        cube = demo2.addCube("cubeSales", dimensions);
        String COORDINATES1[] = {"Timisoara","2003","Metro"};
        cube.setData(COORDINATES1, new Double(247.26));
        conn.disconnect();
    } catch (Exception e) {
        System.err.println("Cube creation failed");
    }
    conn.disconnect();
}

static void smallCube2() {
    // Agentii TM
    String[] aS_denumire = new String[100];
    int index1=0;
    String url_sursa = url_sursa_postgres;
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
try {
    String SQL_sursa = " SELECT numere_lucru.nrlc_id, numere_lucru.nick, numere_lucru.denumire as denumire_agent , numere_lucru.categorie_id, clasa.denumire as clasa_denumire, numere_lucru.grupa_id, numere_lucru.clasa_id FROM numere_lucru, clasa WHERE numere_lucru.clasa_id=clasa.clasa_id AND numere_lucru.sw_0='a' AND numere_lucru.clasa_id = '0082' ORDER BY numere_lucru.denumire";
    // Doar TM - agentii MzTim
    
    ResultSet rs_sursa = null;
    rs_sursa = stmt_sursa.executeQuery(SQL_sursa);
    String illegal_char = " ";
    String legal_char = "-";
    String mS_denumire;
    //int indexOf;
    while (rs_sursa.next()) {
        mS_denumire = rs_sursa.getString("denumire_agent").trim();
        // indexOf = mS_denumire.indexOf("-");
        // if (indexOf>0)
           // mS_denumire=mS_denumire.substring(0, indexOf);
        //mS_denumire.replaceAll(illegal_char, legal_char);
        //aS_denumire[index1]= mS_denumire;
        aS_denumire[index1]= mS_denumire.replaceAll(illegal_char, legal_char);
        System.out.println(aS_denumire[index1]);
        index1=index1+1;
    }
    System.out.println("Transfer OK");
    System.out.println(Integer.toString(index1)+" records ");
    rs_sursa.close();
    stmt_sursa.close();
    conn_sursa.close();
} catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}  
    ConnectionConfiguration ccfg = new ConnectionConfiguration(olap_server_ip, olap_server_port);
    ccfg.setUser(olap_server_user);
    ccfg.setPassword(olap_server_pass);
    Connection conn = ConnectionFactory.getInstance().newConnection(ccfg);
    Database demo2 =  null;
    try {
        demo2 =  conn.addDatabase("Pangram2");
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
    hie2.addElement("2003", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2004", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2005", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Metro", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Kaufland", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Real", Element.ELEMENTTYPE_NUMERIC);
    Element parent, child;
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
        //String COORDINATES1[] = {"Timisoara","2003","Metro"};
        String COORDINATES1[] = {"BERENDEI","2003","Metro"};
        String COORDINATES2[] = {"BICHEL","2003","Metro"};
        cube.setData(COORDINATES1, new Double(247.26));
        cube.setData(COORDINATES2, new Double(2.74));
    } catch (Exception e) {
        System.err.println("Cube creation failed");
        System.err.println(e);
        System.exit(1);
    }
}


static void smallCube3() {
    //  TM Agents : clasa_id = '0082'
    // smallcube3 = smallcube2 + Map<Object,String>
    String[] aS_denumire = new String[100];
    
    Map<Object,String> mp=new HashMap<Object, String>();
    
    int index1=0;
    String url_sursa = url_sursa_postgres;
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
try {
    String SQL_sursa = " SELECT numere_lucru.nrlc_id as id_agent, numere_lucru.nick, numere_lucru.denumire as denumire_agent , numere_lucru.categorie_id, clasa.denumire as clasa_denumire, numere_lucru.grupa_id, numere_lucru.clasa_id FROM numere_lucru, clasa WHERE numere_lucru.clasa_id=clasa.clasa_id AND numere_lucru.sw_0='a' AND numere_lucru.clasa_id = '0082' ORDER BY numere_lucru.denumire";
    // Doar TM - agentii MzTim
    
    ResultSet rs_sursa = null;
    rs_sursa = stmt_sursa.executeQuery(SQL_sursa);
    String illegal_char = " ";
    String legal_char = "-";
    String mS_denumire;
    String mS_id;
    //int indexOf;
    while (rs_sursa.next()) {
        mS_denumire = rs_sursa.getString("denumire_agent").trim().replaceAll(illegal_char, legal_char);
        mS_id       = rs_sursa.getString("id_agent");
        // indexOf = mS_denumire.indexOf("-");
        // if (indexOf>0)
           // mS_denumire=mS_denumire.substring(0, indexOf);
        //mS_denumire.replaceAll(illegal_char, legal_char);
        //aS_denumire[index1]= mS_denumire;
        //aS_denumire[index1]= mS_denumire.replaceAll(illegal_char, legal_char);
        aS_denumire[index1]= mS_denumire;
        mp.put(mS_id, mS_denumire);
        System.out.println(mS_id + " " + aS_denumire[index1]);
        index1=index1+1;
    }
    System.out.println("Transfer OK");
    System.out.println(Integer.toString(index1)+" records ");
    rs_sursa.close();
    stmt_sursa.close();
    conn_sursa.close();
} catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}  
    ConnectionConfiguration ccfg = new ConnectionConfiguration(olap_server_ip, olap_server_port);
    ccfg.setUser(olap_server_user);
    ccfg.setPassword(olap_server_pass);
    Connection conn = ConnectionFactory.getInstance().newConnection(ccfg);
    Database demo2 =  null;
    try {
        demo2 =  conn.addDatabase("Pangram2");
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
    hie2.addElement("2011", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2012", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2013", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Metro", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Kaufland", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Real", Element.ELEMENTTYPE_NUMERIC);
    Element parent, child;
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
        //String COORDINATES1[] = {"BERENDEI","2003","Metro"};
        String COORDINATES1[] = {mp.get("0100658"),"2013","Metro"};
        cube.setData(COORDINATES1, new Double(247.26));
        
        //String COORDINATES2[] = {"BICHEL","2003","Metro"};
        String COORDINATES2[] = {mp.get("0100849"),"2013","Metro"};
        cube.setData(COORDINATES2, new Double(2.74));
        
    } catch (Exception e) {
        System.err.println("Cube creation failed");
        System.err.println(e);
        System.exit(1);
    }
}

static void smallCube4() {
    //  TM Agents : clasa_id = '0082'
    // smallcube3 = smallcube2 + Map<Object,String>
    String[] aS_denumire = new String[100];
    
    Map<Object,String> productMap=new HashMap<Object, String>();
    
    String mS_id="0";
    double md_valoare=0;
    double md_val_tva;

    
    int index1=0;
    String url_sursa = url_sursa_postgres;
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
try {
    String SQL_sursa1 = " SELECT numere_lucru.nrlc_id as id_agent, numere_lucru.nick, numere_lucru.denumire as denumire_agent , numere_lucru.categorie_id, clasa.denumire as clasa_denumire, numere_lucru.grupa_id, numere_lucru.clasa_id FROM numere_lucru, clasa WHERE numere_lucru.clasa_id=clasa.clasa_id AND numere_lucru.sw_0='a' AND numere_lucru.clasa_id = '0082' ORDER BY numere_lucru.denumire";
    // Doar TM - agentii MzTim
    
    String SQL_sursa2 = " select cln.denumire as filiala, nl1.nrlc_id as agent_id ,nl1.denumire as agent,t.cui,t.plt,t.denumire as client,sum(f.valoare_vn+f.val_chelt_expl_1+f.val_chelt_expl_2+f.val_disc_expl)::numeric(12,2) as valoare,sum(f.val_tva_c_nor+f.val_tva_c_red)::numeric(12,2) as val_tva,sum(f.valoare_vn+f.val_chelt_expl_1+f.val_chelt_expl_2+f.val_disc_expl+f.val_tva_c_nor+f.val_tva_c_red)::numeric(12,2) as valoare_tot,max(clt.denumire)::char(35) as clasat,MAX(grt.denumire)::char(35) as grupat,max(s.denloc)::char(45) as loc,MAX(j.denj) as jud from facturi_v_c f inner join tip_doc td on f.tipd_id=td.tipd_id and SUBSTR(td.contd,1,3)<>'418' inner join gestiuni g on f.gestiune_id=g.gest_id inner join numere_lucru nl1 on f.nrlc_id=nl1.nrlc_id inner join clasa cln on nl1.clasa_id=cln.clasa_id inner join vterti t on f.tert_id=t.tert_id inner join clasa clt on t.clasa_id=clt.clasa_id inner join grupa grt on t.grupa_id=grt.grupa_id inner join siruta s on t.siruta=s.siruta inner join jud j on s.jud=j.jud where f.data_f between '2012-01-01' and '2012-12-31' and UPPER(SUBSTR(f.cassa,1,1))='F' and UPPER(f.tiparit)<>'M' AND nl1.nrlc_id='0100843' and t.cui='13479097'group by filiala,agent_id,agent,cui,plt,client having sum(f.valoare_vn+f.val_chelt_expl_1+f.val_chelt_expl_2+f.val_disc_expl)<>0order by filiala,agent,client";
    /*
Pe axa DEPOZITELOR cu AGENTI va fi o singura diviziune:   Timisoara - FRIMU NADIA
Pe axa CLENTILOR va fi o singura diviziune:   ALMIRA TRADE
Pe axa TIMPULUI ~ va fi tot o singura diviziune : 2012
* 
select cln.denumire as filiala, nl1.nrlc_id as agent_id ,nl1.denumire as agent,t.cui,t.plt,
t.denumire as client,sum(f.valoare_vn+f.val_chelt_expl_1+f.val_chelt_expl_2+f.val_disc_expl)::numeric(12,2) as valoare,
sum(f.val_tva_c_nor+f.val_tva_c_red)::numeric(12,2) as val_tva,
sum(f.valoare_vn+f.val_chelt_expl_1+f.val_chelt_expl_2+f.val_disc_expl+f.val_tva_c_nor+f.val_tva_c_red)::numeric(12,2) as valoare_tot,
max(clt.denumire)::char(35) as clasat,MAX(grt.denumire)::char(35) as grupat,
max(s.denloc)::char(45) as loc,MAX(j.denj) as jud
from facturi_v_c f inner join tip_doc td on f.tipd_id=td.tipd_id and SUBSTR(td.contd,1,3)<>'418' inner join gestiuni g on f.gestiune_id=g.gest_id
inner join numere_lucru nl1 on f.nrlc_id=nl1.nrlc_id
inner join clasa cln on nl1.clasa_id=cln.clasa_id 
inner join vterti t on f.tert_id=t.tert_id
inner join clasa clt on t.clasa_id=clt.clasa_id
inner join grupa grt on t.grupa_id=grt.grupa_id
inner join siruta s on t.siruta=s.siruta
inner join jud j on s.jud=j.jud
where f.data_f between '2012-01-01' and '2012-12-31' and
UPPER(SUBSTR(f.cassa,1,1))='F' and UPPER(f.tiparit)<>'M' AND nl1.nrlc_id='0100843' and t.cui='13479097'
group by filiala,agent_id,agent,cui,plt,client
having sum(f.valoare_vn+f.val_chelt_expl_1+f.val_chelt_expl_2+f.val_disc_expl)<>0
order by filiala,agent,client;
     */
    
    ResultSet rs_sursa1 = null;
    ResultSet rs_sursa2 = null;
    rs_sursa1 = stmt_sursa.executeQuery(SQL_sursa1);
    String illegal_char = " ";
    String legal_char = "-";
    String mS_denumire;
    // int indexOf;
    // rs_sursa1.first();
    while (rs_sursa1.next()) {
        mS_denumire = rs_sursa1.getString("denumire_agent").trim().replaceAll(illegal_char, legal_char);
        mS_id       = rs_sursa1.getString("id_agent");
        // indexOf = mS_denumire.indexOf("-");
        // if (indexOf>0)
           // mS_denumire=mS_denumire.substring(0, indexOf);
        //mS_denumire.replaceAll(illegal_char, legal_char);
        //aS_denumire[index1]= mS_denumire;
        //aS_denumire[index1]= mS_denumire.replaceAll(illegal_char, legal_char);
        aS_denumire[index1]= mS_denumire;
        productMap.put(mS_id, mS_denumire);
        System.out.println(mS_id + " " + aS_denumire[index1]);
        index1=index1+1;
    }
    rs_sursa2 = stmt_sursa.executeQuery(SQL_sursa2);
    // rs_sursa2.first();
    while (rs_sursa2.next()) {
        mS_denumire = rs_sursa2.getString("agent").trim().replaceAll(illegal_char, legal_char);
        mS_id       = rs_sursa2.getString("agent_id");
        md_valoare  = rs_sursa2.getDouble("valoare");
        md_val_tva  = rs_sursa2.getDouble("val_tva");
        // indexOf = mS_denumire.indexOf("-");
        // if (indexOf>0)
           // mS_denumire=mS_denumire.substring(0, indexOf);
        //mS_denumire.replaceAll(illegal_char, legal_char);
        //aS_denumire[index1]= mS_denumire;
        //aS_denumire[index1]= mS_denumire.replaceAll(illegal_char, legal_char);
        //aS_denumire[index1]= mS_denumire;
        //mp.put(mS_id, mS_denumire);
        System.out.println("Valoare agenti : " + mS_id + " " + mS_denumire + " = " + Double.toString(md_valoare));
    }
    
    System.out.println("Transfer OK");
    System.out.println(Integer.toString(index1)+" records ");
    rs_sursa1.close();
    rs_sursa2.close();
    stmt_sursa.close();
    conn_sursa.close();
} catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}  
    ConnectionConfiguration ccfg = new ConnectionConfiguration(olap_server_ip, olap_server_port);
    ccfg.setUser(olap_server_user);
    ccfg.setPassword(olap_server_pass);
    Connection conn = ConnectionFactory.getInstance().newConnection(ccfg);
    Database demo2 =  null;
    try {
        demo2 =  conn.addDatabase("Pangram4");
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
    hie2.addElement("2011", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2012", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2013", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Metro", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Kaufland", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Real", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Almira-Trade", Element.ELEMENTTYPE_NUMERIC);
    Element parent, child;
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
        //String COORDINATES1[] = {"BERENDEI","2013","Metro"};
        String COORDINATES1[] = {productMap.get("0100658"),"2013","Metro"};
        cube.setData(COORDINATES1, new Double(247.26));
        
        //String COORDINATES2[] = {"BICHEL","2013","Metro"};
        String COORDINATES2[] = {productMap.get("0100849"),"2013","Metro"};
        cube.setData(COORDINATES2, new Double(2.74));
        
        String COORDINATES3[] = {productMap.get(mS_id),"2012","Almira-Trade"};
        cube.setData(COORDINATES3, md_valoare);
        
        
    } catch (Exception e) {
        System.err.println("Cube creation failed");
        System.err.println(e);
        System.exit(1);
    }
}

static void smallCube5() {
    // a se vedea : SetDimDepoAgByList
    // FACTURI_FULL
    // smallcube3 = smallcube2 + Map<Object,String>
    
    
    String[] aS_denumire = new String[100];
    
    Map<Object,String> agentMap=new HashMap<Object, String>();
    
    String mS_id="0";
    double md_valoare=0;
    double md_val_tva;

    
    int index1=0;
    String url_sursa = url_sursa_postgres;
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
    String SQL_sursa1 = " select case when SUBSTR(td.contd,1,3)='418' then 'Aviz'::char(35) else 'Factura':: char(35) end as doc,f.nrdoc,f.data_f as data,g.denumire as depozit, "
                      + " cln.denumire as filiala,nl1.denumire as agent, f.nrlc_id as id_agent, f.tert_id as id_client, t.cui,t.plt, " 
                      + " t.denumire as client,f.zscadenta,sum(f.valoare_vn+f.val_chelt_expl_1+f.val_chelt_expl_2+f.val_disc_expl)::numeric(12,2) as valoare,sum(f.val_tva_c_nor+f.val_tva_c_red)::numeric(12,2) as val_tva, " 
                      + " sum(f.valoare_vn+f.val_chelt_expl_1+f.val_chelt_expl_2+f.val_disc_expl+f.val_tva_c_nor+f.val_tva_c_red)::numeric(12,2) as valoare_tot, "
                      + " max(clt.denumire)::char(35) as clasat,MAX(grt.denumire)::char(35) as grupat, " 
                      + " max(s.denloc)::char(45) as loc,MAX(j.denj) as jud " 
                      + " from facturi_v_c f inner join tip_doc td on f.tipd_id=td.tipd_id and SUBSTR(td.contd,1,3)<>'418' inner join gestiuni g on f.gestiune_id=g.gest_id "
                      + " inner join numere_lucru nl1 on f.nrlc_id=nl1.nrlc_id " 
                      + " inner join clasa cln on nl1.clasa_id=cln.clasa_id " 
                      + " inner join vterti t on f.tert_id=t.tert_id inner join clasa clt on t.clasa_id=clt.clasa_id inner join grupa grt on t.grupa_id=grt.grupa_id "
                      + " inner join siruta s on t.siruta=s.siruta inner join jud j on s.jud=j.jud "
                      + " where f.data_f between '2012-01-01' and '2012-11-30' and UPPER(SUBSTR(f.cassa,1,1))='F' and UPPER(f.tiparit)<>'M' "
                      + " group by td.contd,nrdoc,f.data_f,depozit,filiala,agent,id_agent,id_client,cui,plt,client,zscadenta having sum(f.valoare_vn+f.val_chelt_expl_1+f.val_chelt_expl_2+f.val_disc_expl)<>0 "
                      + " order by filiala,agent,client,data " ;
    /*
select case when SUBSTR(td.contd,1,3)='418' then 'Aviz'::char(35) else 'Factura':: char(35) end as doc,f.nrdoc,f.data_f as data,g.denumire as depozit,
cln.denumire as filiala,nl1.denumire as agent, f.nrlc_id as id_agent, f.tert_id as id_client, t.cui,t.plt,
t.denumire as client,f.zscadenta,sum(f.valoare_vn+f.val_chelt_expl_1+f.val_chelt_expl_2+f.val_disc_expl)::numeric(12,2) as valoare,sum(f.val_tva_c_nor+f.val_tva_c_red)::numeric(12,2) as val_tva,
sum(f.valoare_vn+f.val_chelt_expl_1+f.val_chelt_expl_2+f.val_disc_expl+f.val_tva_c_nor+f.val_tva_c_red)::numeric(12,2) as valoare_tot,
max(clt.denumire)::char(35) as clasat,MAX(grt.denumire)::char(35) as grupat,
max(s.denloc)::char(45) as loc,MAX(j.denj) as jud 
from facturi_v_c f inner join tip_doc td on f.tipd_id=td.tipd_id and SUBSTR(td.contd,1,3)<>'418' inner join gestiuni g on f.gestiune_id=g.gest_id 
inner join numere_lucru nl1 on f.nrlc_id=nl1.nrlc_id 
inner join clasa cln on nl1.clasa_id=cln.clasa_id   
inner join vterti t on f.tert_id=t.tert_id inner join clasa clt on t.clasa_id=clt.clasa_id inner join grupa grt on t.grupa_id=grt.grupa_id 
inner join siruta s on t.siruta=s.siruta inner join jud j on s.jud=j.jud 
where f.data_f between '2012-01-01' and '2012-11-30' and UPPER(SUBSTR(f.cassa,1,1))='F' and UPPER(f.tiparit)<>'M' 
group by td.contd,nrdoc,f.data_f,depozit,filiala,agent,id_agent,id_client,cui,plt,client,zscadenta having sum(f.valoare_vn+f.val_chelt_expl_1+f.val_chelt_expl_2+f.val_disc_expl)<>0 
order by filiala,agent,client,data ;
*/
    
    ConnectionConfiguration ccfg = new ConnectionConfiguration(olap_server_ip, olap_server_port);
    ccfg.setUser(olap_server_user);
    ccfg.setPassword(olap_server_pass);
    Connection conn_dest = ConnectionFactory.getInstance().newConnection(ccfg);
    Database odb = null;
    Element parent, child;
    Consolidation[] consolidations = new Consolidation[1];
    
    try {
        odb =  conn_dest.addDatabase(olap_db_name); // 5.SmallCube
    } catch (Exception e) {
        System.err.println("Cube already created");
        odb = conn_dest.getDatabaseByName(olap_db_name);
    }

    Dimension[] dimensions = new Dimension[3];
    Dimension dim1w = odb.addDimension("dimWarehouse");
    Dimension dim3y = odb.addDimension("dimYears");
    Dimension dim2c = odb.addDimension("dimCustomers");
    Hierarchy hie1w = dim1w.getDefaultHierarchy();
    Hierarchy hie3y = dim3y.getDefaultHierarchy();
    Hierarchy hie2c = dim2c.getDefaultHierarchy();
    dimensions[0]=dim1w;
    dimensions[1]=dim3y;
    dimensions[2]=dim2c;
    
    hie3y.addElement("2011", Element.ELEMENTTYPE_NUMERIC);
    hie3y.addElement("2012", Element.ELEMENTTYPE_NUMERIC);
    hie3y.addElement("2013", Element.ELEMENTTYPE_NUMERIC);
    
    hie2c.addElement("Metro", Element.ELEMENTTYPE_NUMERIC);
    hie2c.addElement("Kaufland", Element.ELEMENTTYPE_NUMERIC);
    hie2c.addElement("Real", Element.ELEMENTTYPE_NUMERIC);
    hie2c.addElement("Almira-Trade", Element.ELEMENTTYPE_NUMERIC);
    
    
try {  
    ResultSet rs_sursa1 = null;
    ResultSet rs_sursa2 = null;
    rs_sursa1 = stmt_sursa.executeQuery(SQL_sursa1);
    String illegal_char = " ";
    String legal_char = "-";
    String mS_denumireAgent_new, mS_denumireAgent_old;
    String mS_denumireFiliala_new,mS_denumireFiliala_old;
    mS_denumireFiliala_new = " ";
    mS_denumireAgent_new = " ";
    // int indexOf;
    // rs_sursa1.first();
    
    // se baga filiala in dimensiune ,  se face bucla pe filiala ; 
    // se baga agent in dimensiune, se face bucla pe agenti
    // se baga in dimensiune clientii si valorile corespondente 
    
    // A SE VEDEA ALGORITMUL SIMILAR : getDepoAgListBySQL
    
    while (rs_sursa1.next()) {
        mS_denumireFiliala_old = mS_denumireFiliala_new;
        mS_denumireAgent_old   = mS_denumireAgent_new;
        mS_denumireFiliala_new = rs_sursa1.getString("filiala").trim().replaceAll(illegal_char, legal_char);
        mS_denumireAgent_new   = rs_sursa1.getString("agent").trim().replaceAll(illegal_char, legal_char);
        mS_id                  = rs_sursa1.getString("id_agent");
        if (!mS_denumireFiliala_new.equals(mS_denumireFiliala_old)){
            // daca se schimba filiala , 
            // se adauga in ierarhia dimensiunii DEPOZITE-AGENTI 
            hie1w.addElement(mS_denumireFiliala_new, Element.ELEMENTTYPE_NUMERIC);
        }
        
        if (!mS_denumireAgent_new.equals(mS_denumireAgent_old)){
                // daca se schimba agentul 
                parent = hie1w.getElementByName(mS_denumireFiliala_new);
                child = hie1w.addElement(mS_denumireAgent_new, Element.ELEMENTTYPE_NUMERIC);
                
                // consolidations[0] = hie1w.newConsolidation(child, parent, 1);
                // parent.updateConsolidations(consolidations);
                parent.updateConsolidations(new Consolidation[]{hie1w.newConsolidation(child, parent, 1)});
                // parent.updateConsolidations(new Consolidation[]{dim1w.newConsolidation(child, parent, 1)});
                // consolidations[0] = null;
                
                agentMap.put(mS_id, mS_denumireAgent_new);
                System.out.println(mS_id + " " + mS_denumireAgent_new);
                // index1=index1+1;
        }
        
        // hie2c.addElement("Metro", Element.ELEMENTTYPE_NUMERIC);
        
    }
   
    System.out.println("Transfer OK");
    System.out.println(Integer.toString(index1)+" records ");
    rs_sursa1.close();
    rs_sursa2.close();
    stmt_sursa.close();
    conn_sursa.close();
} catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}  
    Cube cube = null;
    try {
        cube = odb.addCube("cubeSales", dimensions);
        //String COORDINATES1[] = {"BERENDEI","2013","Metro"};
        String COORDINATES1[] = {agentMap.get("0100658"),"2013","Metro"};
        cube.setData(COORDINATES1, new Double(247.26));
        
        //String COORDINATES2[] = {"BICHEL","2013","Metro"};
        String COORDINATES2[] = {agentMap.get("0100849"),"2013","Metro"};
        cube.setData(COORDINATES2, new Double(2.74));
        
        String COORDINATES3[] = {agentMap.get(mS_id),"2012","Almira-Trade"};
        cube.setData(COORDINATES3, md_valoare);
        
        
    } catch (Exception e) {
        System.err.println("Cube creation failed");
        System.err.println(e);
        System.exit(1);
    }
}


static void smallCube6() {
    // VANZARI FULL vanzfull.sql 
    //  TM Agents : clasa_id = '0082'
    // smallcube3 = smallcube2 + Map<Object,String>
    String[] aS_denumire = new String[100];
    
    Map<Object,String> productMap=new HashMap<Object, String>();
    
    String mS_id="0";
    double md_valoare=0;
    double md_val_tva;
    
    int index1=0;
    String url_sursa = url_sursa_postgres;
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
try {
    String SQL_sursa1 = " ";
    // Doar TM - agentii MzTim
    
    String SQL_sursa2 = " ";

    /*
drop table if exists vanzfull;
create table vanzfull as
 select case when SUBSTR(td.contd,1,3)='418' then 'Aviz'::char(35) else 'Factura':: char(35) end as doc,f.nrdoc,f.data_f as data,g.denumire as depozit,
  cln.denumire as filiala,nl1.denumire as agent,t.cui,t.plt,
  t.denumire as client,n.simbol,n.denumire as produs,sum(c.cantitate)::numeric(12,2) as cant,sum(ROUND(c.cantitate*n.masa,2))::numeric(12,4) as masa,MAX(c.pret_vanzare)::numeric(12,2) as pret,c.pr_disc_incl,
  sum(ROUND(c.pret_vanzare*c.cantitate,2))::numeric(12,2) as valnet,
  MAX(case when c.pr_disc_incl>=99 then np.pret_gross else round(c.pret_vanzare*(1+c.pr_disc_incl/100),2) end )::numeric(12,2)  as pret_lista,
  sum(ROUND((case when c.pr_disc_incl>=99 then np.pret_gross else round(c.pret_vanzare*(1+c.pr_disc_incl/100),2) end)*c.cantitate,2))::numeric(12,2) as valbrut,
  max(clt.denumire)::char(35) as clasat,MAX(grt.denumire)::char(35) as grupat,MAX(clp.denumire)::char(35) as clasap,MAX(grp.denumire)::char(35) as grupap,
  max(s.denloc)::char(45) as loc,MAX(j.denj) as jud,EXTRACT(DAY FROM f.data_f) as zi,EXTRACT(MONTH FROM f.data_f) as luna,EXTRACT(YEAR FROM f.data_f) as an,EXTRACT(QUARTER FROM f.data_f) as trimestru
  from facturi_v_c f inner join tip_doc td on f.tipd_id=td.tipd_id and SUBSTR(td.docprim,1,2)<>'06'
  inner join conturi_descarcare cd on td.contc=cd.cont_venit inner join gestiuni g on f.gestiune_id=g.gest_id
  inner join numere_lucru nl1 on f.nrlc_id=nl1.nrlc_id
  inner join clasa cln on nl1.clasa_id=cln.clasa_id
 inner join vterti t on f.tert_id=t.tert_id inner join clasa clt on t.clasa_id=clt.clasa_id inner join grupa grt on t.grupa_id=grt.grupa_id
 inner join siruta s on t.siruta=s.siruta inner join jud j on s.jud=j.jud
 inner join facturi_cv_c c on f.fact_id=c.fact_id inner join nomstoc n on c.stoc_id=n.stoc_id
 inner join nomstoc_pretcrt np on n.simbol=np.simbol and n.cms<>2 and np.gestiune_cod=g.gestiune_cod and np.cont_gest=cd.cont_gest and c.codlot=np.codlot 
 inner join clasa clp on n.clasa_id=clp.clasa_id inner join grupa grp on n.grupa_id=grp.grupa_id
 where f.data_f between '2012-12-01' and '2012-12-31' and UPPER(SUBSTR(f.cassa,1,1))='F' and UPPER(f.tiparit)<>'M'
 group by 1,2,3,4,5,6,7,8,9,10,11,15 having sum(c.cantitate)<>0 union all
select 'Bon fiscal':: char(35) as doc,f.nrdoc,f.data_f as data,g.denumire as depozit,
cln.denumire as filiala,nl1.denumire as agent,t.cui,t.plt,
t.denumire as client,n.simbol,n.denumire as produs,sum(c.cantitate)::numeric(12,2) as cant,sum(ROUND(c.cantitate*n.masa,2))::numeric(12,4) as masa,MAX(c.pret_vanzare)::numeric(12,2) as pret,c.pr_disc_incl,
sum(ROUND(c.pret_vanzare*c.cantitate,2))::numeric(12,2) as valnet,
MAX(case when c.pr_disc_incl>=99 then np.pret_gross else round(c.pret_vanzare*(1+c.pr_disc_incl/100),2) end )::numeric(12,2)  as pret_lista,
sum(ROUND((case when c.pr_disc_incl>=99 then np.pret_gross else round(c.pret_vanzare*(1+c.pr_disc_incl/100),2) end)*c.cantitate,2))::numeric(12,2) as valbrut,
max(clt.denumire)::char(35) as clasat,MAX(grt.denumire)::char(35) as grupat,MAX(clp.denumire)::char(35) as clasap,MAX(grp.denumire)::char(35) as grupap,
max(s.denloc)::char(45) as loc,MAX(j.denj) as jud,EXTRACT(DAY FROM f.data_f) as zi,EXTRACT(MONTH FROM f.data_f) as luna,EXTRACT(YEAR FROM f.data_f) as an,EXTRACT(QUARTER FROM f.data_f) as trimestru 
from facturi_v_c f inner join tip_doc td on f.tipd_id=td.tipd_id and SUBSTR(td.docprim,1,2)='06' inner join conturi_descarcare cd on td.contc=cd.cont_venit inner join gestiuni g on f.gestiune_id=g.gest_id
inner join numere_lucru nl1 on f.nrlc_id=nl1.nrlc_id
inner join clasa cln on nl1.clasa_id=cln.clasa_id 
inner join vterti t on case when f.tet_id2='' or f.tet_id2 is null then f.tert_id=t.tert_id else f.tet_id2=t.tert_id end
inner join clasa clt on t.clasa_id=clt.clasa_id inner join grupa grt on t.grupa_id=grt.grupa_id
inner join siruta s on t.siruta=s.siruta inner join jud j on s.jud=j.jud
inner join facturi_cv_c c on f.fact_id=c.fact_id inner join nomstoc n on c.stoc_id=n.stoc_id
inner join nomstoc_pretcrt np on n.simbol=np.simbol and n.cms<>2 and np.gestiune_cod=g.gestiune_cod and np.cont_gest=cd.cont_gest and c.codlot=np.codlot 
inner join clasa clp on n.clasa_id=clp.clasa_id inner join grupa grp on n.grupa_id=grp.grupa_id
where f.data_f between '2012-12-01' and '2012-12-31' and UPPER(SUBSTR(f.cassa,1,1))<>'F' and UPPER(f.cassa)<>'99' and UPPER(f.tiparit)<>'M'
group by 1,2,3,4,5,6,7,8,9,10,11,15 having sum(c.cantitate)<>0 union all
select 'Factura val.':: char(35) as doc,f.nrdoc,f.data_f as data,g.denumire as depozit,
cln.denumire as filiala,nl1.denumire as agent,t.cui,t.plt,
t.denumire as client,coalesce(n.simbol,'Fara'::char(13)) as simbol,coalesce(n.denumire,f.explicatii_cs::char(45)) as produs,0::numeric(12,2) as cant,0::numeric(12,4) as masa,
MAX(coalesce(o.valoare,0))::numeric(12,2) as pret,0::numeric(6,2) as pr_disc_incl,
sum(coalesce(o.valoare,f.valoare_vn))::numeric(12,2) as valnet,0::numeric(12,2) as pret_lista,sum(coalesce(o.valoare,f.valoare_vn))::numeric(12,2) as valbrut,
max(clt.denumire)::char(35) as clasat,MAX(grt.denumire)::char(35) as grupat,MAX(coalesce(clp.denumire,'Valoric'))::char(35) as clasap,MAX(coalesce(grp.denumire,'Valoric'))::char(35) as grupap,
max(s.denloc)::char(45) as loc,MAX(j.denj) as jud,EXTRACT(DAY FROM f.data_f) as zi,EXTRACT(MONTH FROM f.data_f) as luna,EXTRACT(YEAR FROM f.data_f) as an,EXTRACT(QUARTER FROM f.data_f) as trimestru 
from facturi_v_c f inner join tip_doc td on f.tipd_id=td.tipd_id and td.docprim='021' inner join gestiuni g on f.gestiune_id=g.gest_id
inner join numere_lucru nl1 on f.nrlc_id=nl1.nrlc_id
inner join clasa cln on nl1.clasa_id=cln.clasa_id
inner join vterti t on f.tert_id=t.tert_id inner join clasa clt on t.clasa_id=clt.clasa_id inner join grupa grt on t.grupa_id=grt.grupa_id
inner join siruta s on t.siruta=s.siruta inner join jud j on s.jud=j.jud
left join fact_off_inv o on f.fact_id=o.fact_id left join nomstoc n on o.simbol=n.simbol and n.cms<>2
left join clasa clp on n.clasa_id=clp.clasa_id left join grupa grp on n.grupa_id=grp.grupa_id
where f.data_f between '2012-12-01' and '2012-12-31' and UPPER(SUBSTR(f.cassa,1,1))='F' and UPPER(f.tiparit)<>'M'
group by 1,2,3,4,5,6,7,8,9,10,11,15 having sum(f.valoare_vn+f.val_disc_expl+f.val_chelt_expl_1+f.val_chelt_expl_2)<>0 order by 5,6,9,3 ;

*/
    
    ResultSet rs_sursa1 = null;
    ResultSet rs_sursa2 = null;
    rs_sursa1 = stmt_sursa.executeQuery(SQL_sursa1);
    String illegal_char = " ";
    String legal_char = "-";
    String mS_denumire;
    // int indexOf;
    // rs_sursa1.first();
    while (rs_sursa1.next()) {
        mS_denumire = rs_sursa1.getString("denumire_agent").trim().replaceAll(illegal_char, legal_char);
        mS_id       = rs_sursa1.getString("id_agent");
        // indexOf = mS_denumire.indexOf("-");
        // if (indexOf>0)
           // mS_denumire=mS_denumire.substring(0, indexOf);
        //mS_denumire.replaceAll(illegal_char, legal_char);
        //aS_denumire[index1]= mS_denumire;
        //aS_denumire[index1]= mS_denumire.replaceAll(illegal_char, legal_char);
        aS_denumire[index1]= mS_denumire;
        productMap.put(mS_id, mS_denumire);
        System.out.println(mS_id + " " + aS_denumire[index1]);
        index1=index1+1;
    }
    rs_sursa2 = stmt_sursa.executeQuery(SQL_sursa2);
    // rs_sursa2.first();
    while (rs_sursa2.next()) {
        mS_denumire = rs_sursa2.getString("agent").trim().replaceAll(illegal_char, legal_char);
        mS_id       = rs_sursa2.getString("agent_id");
        md_valoare  = rs_sursa2.getDouble("valoare");
        md_val_tva  = rs_sursa2.getDouble("val_tva");
        // indexOf = mS_denumire.indexOf("-");
        // if (indexOf>0)
           // mS_denumire=mS_denumire.substring(0, indexOf);
        //mS_denumire.replaceAll(illegal_char, legal_char);
        //aS_denumire[index1]= mS_denumire;
        //aS_denumire[index1]= mS_denumire.replaceAll(illegal_char, legal_char);
        //aS_denumire[index1]= mS_denumire;
        //mp.put(mS_id, mS_denumire);
        System.out.println("Valoare agenti : " + mS_id + " " + mS_denumire + " = " + Double.toString(md_valoare));
    }
    
    System.out.println("Transfer OK");
    System.out.println(Integer.toString(index1)+" records ");
    rs_sursa1.close();
    rs_sursa2.close();
    stmt_sursa.close();
    conn_sursa.close();
} catch (Exception e) {
    System.err.println("An error has occurred during transfer");
    System.err.println(e);
}  
    ConnectionConfiguration ccfg = new ConnectionConfiguration(olap_server_ip, olap_server_port);
    ccfg.setUser(olap_server_user);
    ccfg.setPassword(olap_server_pass);
    Connection conn = ConnectionFactory.getInstance().newConnection(ccfg);
    Database demo2 =  null;
    try {
        demo2 =  conn.addDatabase("Pangram4");
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
    hie2.addElement("2011", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2012", Element.ELEMENTTYPE_NUMERIC);
    hie2.addElement("2013", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Metro", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Kaufland", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Real", Element.ELEMENTTYPE_NUMERIC);
    hie3.addElement("Almira-Trade", Element.ELEMENTTYPE_NUMERIC);
    Element parent, child;
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
        //String COORDINATES1[] = {"BERENDEI","2013","Metro"};
        String COORDINATES1[] = {productMap.get("0100658"),"2013","Metro"};
        cube.setData(COORDINATES1, new Double(247.26));
        
        //String COORDINATES2[] = {"BICHEL","2013","Metro"};
        String COORDINATES2[] = {productMap.get("0100849"),"2013","Metro"};
        cube.setData(COORDINATES2, new Double(2.74));
        
        String COORDINATES3[] = {productMap.get(mS_id),"2012","Almira-Trade"};
        cube.setData(COORDINATES3, md_valoare);
        
        
    } catch (Exception e) {
        System.err.println("Cube creation failed");
        System.err.println(e);
        System.exit(1);
    }
}



public static void main(String[] args) {

    CreatePaloDB();
    
     // smallCube1();
     // smallCube2();
     // smallCube3();
     // smallCube4();
    
    
    smallCube5();
    // smallCube6();
    // NoSQL !!!
    
        
     // CreateDimDepoAg();
     // Agentii au ID in denumire ; Sunt doar cei care au facturat dupa 2012-01-01 ... 
     
     // CreateDimCustomer();
     // AXA Clientilor are TERT_ID in denumire ; Sunt doar pe cei facturati dupa 2012-01-01 ... 
     // cui_id = ID sediu , tert_id = ID PLT
     
     // CreateDimProduct();
     // AXA PRODUSELOR contine doar se s-a vandut dupa 2012 iar in denumire apare si ID_PRODUS !    
    
     // 2. smallCube5() La Frimu-Nadia-TM : pe Almira-Trade sa parcurg toate produsele !  
     // 3. inserarea valorii nu e bine sa o fac in functie de productMap(ID) + clientMap(ID) + agentMap(ID) ... 
     
     // Q : De ce trebuie facute axele cubului intr-o procedura separata ? 
     // A : Pentru ca procedura de umplere a cubului trebuie sa faca update INCREMENTAL zilnic  ... 
     //     ... adica ce s-a calculat in ziua anterioara o ramas valabil ; 
     
     // 1) facturiCube1() : Pentru versiunea BETA-INITIALA ( facturi_full.sql - fara produse + cu TVA : 
     //   ~ consideram ca se creeaza cubul doar odata pe luna 
     //      iar luna analizata sa fie cea anterioara ;
     //      astfel nu este necesara o incrementare zilnica , iar pentru fiecare luna - alt cub ;
     //      In acest fel se optimizeaza procedura de creare a cubului prin faptul ca nu trebuie 
     //      doua rutine separate ( una de creeare dimensiuni si alta pentru inserare-date+valori ) 
     //   ~ inserarea valorii s-ar face in functie de clientMap(ID) + agentMap(ID) ... 
     //   ~ inserarea valorilor se fac intr-o singura rutina cu bucle while incuibate 
     //   ~ SQLu` merge repede , nu trebui procedura de transfer in pangram_warehouse_2013 ! 
     //   ~ SQLu` trebuie modificat pentru a fi in denumire ID---agent si ID---client . 
     //   ~ smallCube5 - in lucru 
     // smallCube6 = DoNothingYET!!;
    
     // smallCube5() = FACTURI_FULL
    
     // pentru intelegerea notiunii de CONSOLIDARE - trebuie parcursas procedura : smallCube2
    
    
    System.exit(0);
}
}
