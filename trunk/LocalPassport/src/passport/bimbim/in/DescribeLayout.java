package passport.bimbim.in;


import java.util.Vector;

import org.ksoap2.serialization.SoapObject;

public class DescribeLayout extends ResultObject {

    public Vector<DescribeButtonSection> buttonSection = new Vector<DescribeButtonSection>();
    public Vector<DescribeLayout> detailLayoutSections = new Vector<DescribeLayout>();
    public Vector<DescribeLayout> editLayoutSections = new Vector<DescribeLayout>();
   // public Vector<RelatedList> relatedLists = new Vector<RelatedList>();

    public DescribeLayout(String soapname, SoapObject soap) {
       
       super(soapname, soap);
       System.out.println("in describeLayout Constructor");
    }

    @Override
    public ResultObject addSoapObject(String soapname, SoapObject soap) {
       System.out.println("in describeLayout addSoapObject");
       ResultObject obj = null;
       if (soapname == null) {
          System.out.println("soapname == null");
          super.addSoapObject(soapname, soap);
       } else if (soapname.equals("buttonSection")) {
          buttonSection.add((DescribeButtonSection)(obj = new DescribeButtonSection(null, soap)));
       } else if (soapname.equals("detailLayoutSections")) {
          detailLayoutSections.add((DescribeLayout)(obj = new DescribeLayout(null, soap)));
       } else if (soapname.equals("editLayoutSections")) {
          editLayoutSections.add((DescribeLayout)(obj = new DescribeLayout(null, soap)));
       }
       return obj;
    }

}
