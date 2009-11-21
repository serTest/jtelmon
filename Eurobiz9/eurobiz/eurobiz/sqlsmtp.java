
/*
 * Created on May 31, 2005
 * Author root@montebanato.ro
 */
package eurobiz;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Color;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

//import org.jfree.chart.*;
import org.jfree.data.general.DefaultPieDataset;
// import org.jfree.data.category.*;
//import org.jfree.chart.plot.*;
//import org.jfree.chart.title.DateTitle;

public class sqlsmtp {

  public sqlsmtp() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

	static Integer niLuna;
	static Integer niInterval;
	static String url_pg      = "jdbc:postgresql://192.168.61.200:5432/pangram_main_2008";
	static String username_pg = "postgres";
	static String password_pg = "telinit";
	static Connection conn_pg = null;
	static Statement stmt_pg  = null;
	static ResultSet rs_pg    = null;

	public static void alexTemplate(){}

		public static void openConnection() {
			try {
				Class.forName("org.postgresql.Driver");
				System.out.println("Driver postgres OK");
			} catch (Exception e) {
				System.err.println("Failed to load Driver postgres");
			}
			try {
				conn_pg = DriverManager.getConnection(url_pg,username_pg,password_pg);
				stmt_pg = conn_pg.createStatement();
				System.out.println("Connection to Postgres OK");
			} catch (Exception e) {
				System.err.println("Connection to Postgres failed");
			}
		}

		public static void closeConnection(){
			try {
				  stmt_pg.close();
				  conn_pg.close();
			  }
	        catch (Exception e) {
				  System.err.println("An error has occurred during closing connection");
				  System.err.println(e);
			}
		}

		public static ResultSet getMyResultSet(String SQL_pg, PreparedStatement myps) {
			  try {
			  	  rs_pg = myps.executeQuery();
			  } catch (Exception e) {
				  System.err.println("An error has occurred during prepared query execution");
				  System.err.println(e);
			  }
	      return rs_pg;
		}

	public static Table topTeleDep() throws DocumentException {
		Table tabel = new Table(3);
		tabel.setAutoFillEmptyCells(true);
	    //String gest=" "  ;
	    Timestamp tsDateTime;
	    String sDateTime = " ";
        Double dProcent = new Double(34.58);
        // Double dValoare = new Double(-1.23);
        String sProcent = " ";
        String sDepozit = " ";
        //String sValoare = " ";
		  try {
		    DefaultPieDataset dataset1 = new DefaultPieDataset();
		    // DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
		    String SQL_pg = " select o.pli,max(o.nume_tr) as nume_tr,max(p.denumire),max(o.operare) as time ,max(o.nr_tr) as numar from offline_ristoric o inner join pli p on o.pli=p.pli group by o.pli order by 1";
		    PreparedStatement ps = conn_pg.prepareStatement(SQL_pg);
		  	rs_pg = getMyResultSet(SQL_pg, ps);
		  	// String tmp=" "  ;
			// String tmp2=" "  ;
	        tabel.setBorderWidth(1);
	        tabel.setBorderColor(new java.awt.Color (233,133,144));
	        Cell celula  = new Cell(" Depozit   " );
	        Cell celula2 = new Cell(" TimeStamp ");
	        Cell celula3 = new Cell(" TeleNumar ");
	        tabel.addCell(celula);
	        tabel.addCell(celula2);
	        tabel.addCell(celula3);
			while (rs_pg.next()) {
		        dProcent = Double.valueOf(rs_pg.getString("numar"));
		        sDepozit = rs_pg.getString("max");
		        tsDateTime = rs_pg.getTimestamp("time");
		        sDateTime = tsDateTime.toString();
		        // NumberFormat gFormat = NumberFormat.getIntegerInstance(Locale.GERMANY);
		        // sValoare = gFormat.format(dValoare);
		        DecimalFormat df1 = new DecimalFormat("#,##0.00");
		        sProcent = df1.format(dProcent);
		        celula =  new Cell(sDepozit);
		        celula2 = new Cell(sDateTime);
		        celula3 = new Cell( sProcent );
		        dataset1.setValue(sDepozit,dProcent);
		        celula.setHorizontalAlignment(Element.ALIGN_CENTER);
		        celula.setBackgroundColor(new java.awt.Color (234,255,230));
		        celula.setHorizontalAlignment(Element.ALIGN_LEFT);
		        celula.setVerticalAlignment(Element.ALIGN_MIDDLE);
		        celula2.setHorizontalAlignment(Element.ALIGN_CENTER);
		        celula2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		        celula3.setHorizontalAlignment(Element.ALIGN_CENTER);
		        celula3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		        tabel.addCell(celula);
		        tabel.addCell(celula2);
		        tabel.addCell(celula3);
			}
			System.out.println("Transfer OK");
			rs_pg.close();
 		  }
          catch (Exception e) {
			  System.err.println("An error has occurred during transfer");
			  System.err.println(e);
		  }
		return tabel;
	}

	public static void makeMyPdfDok(String pdfName) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		openConnection();
		System.out.println("Connection opened ... ");
		Table tabel  = topTeleDep();
		closeConnection();
		System.out.println("Connection closed ... ");
		   try {
			   PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(pdfName));
			   Date now = new Date();
			    // Long longTimeNow = Long.valueOf(now.getTime());
			   // Long longTimeNow = new Long(now.getTime());
			   DateFormat df1 = DateFormat.getDateInstance(DateFormat.SHORT);
			   String sDateNow = df1.format(now);
			   document.open();
			   HeaderFooter footer = new HeaderFooter(new Phrase("Pagina: "), true);
			   footer.setBorder(Rectangle.NO_BORDER);
			   document.setFooter(footer);
			   HeaderFooter header = new HeaderFooter(new Phrase("RAPORT Teletransmisii "+sDateNow), false);
			   document.setHeader(header);
			   Paragraph paCoperta = new Paragraph("Pangram S.A. - "+sDateNow, FontFactory.getFont(FontFactory.HELVETICA, 25, Font.BOLDITALIC, new Color(0, 0, 255)));
			   Chapter chCoperta = new Chapter(paCoperta, 1);
			   chCoperta.setNumberDepth(0);
			   Paragraph paraCoperta = new Paragraph("DepIT@montebanato.ro ", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
			   paraCoperta.alignment();
			   Paragraph paraCoperta2 = new Paragraph("Teletransmisii depozite ", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.ITALIC, new java.awt.Color (125,125,125)));
			   chCoperta.add(paraCoperta);
			   chCoperta.add(paraCoperta2);
			   chCoperta.add(tabel);
			   document.add(chCoperta);
		   } catch (DocumentException de) {
			    System.err.println("makeMyPdfDok : An error has occurred in the PDF ");
			    System.err.println(de);
		   		de.printStackTrace();
		   } catch(Exception de) {
			   de.printStackTrace();
		   }
		   document.close();
	}

    public static File[] listFiles() {
        //String[] dir = new java.io.File(".").list( );
        File[] listDir = new File(".").listFiles( );
        // java.util.Arrays.sort(dir);
        // for (int i=0; i<dir.length; i++)
        //    System.out.println(dir[i]);
        return listDir;
    }


    public static void DoStuff() throws DocumentException,IOException {
            String theFile="z2005_0002.pdf";
            // String path2dir = "/home/temp/send_pli09/";
            // String[] listDir = new File(path2dir).list( );
            makeMyPdfDok(theFile);
            SendMailUsage myMail = new SendMailUsage();
            // int lengthList = listDir.length ;
            myMail.sendMyMail(theFile, "Alex");
            //for(int i=0; i<lengthList;i++) {
                    // myMail.sendMyMail(theFile);
                    // myMail.sendMyMail(path2dir + listDir[i]);
            //}
            System.out.println(" Gata ! ");
    }


	public static void main(String[] args) throws DocumentException,IOException {
		String theFile="z2005_0002.pdf";
		// String path2dir = "/home/temp/send_pli09/";
		// String[] listDir = new File(path2dir).list( );
		makeMyPdfDok(theFile);
		SendMailUsage myMail = new SendMailUsage();
		// int lengthList = listDir.length ;
		myMail.sendMyMail(theFile, " ");
		//for(int i=0; i<lengthList;i++) {
			// myMail.sendMyMail(theFile);
			// myMail.sendMyMail(path2dir + listDir[i]);
		//}
		System.out.println(" Gata ! ");
	}
      private void jbInit() throws Exception {
      }
  }
