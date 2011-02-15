package passport.bimbim.in;


import org.ksoap2.serialization.SoapObject;


public class DescribeButtonSection extends ResultObject {

   public DescribeButtonSection(String soapname, SoapObject soap) {
      super(soapname, soap);
   }
     
   @Override
   public ResultObject addSoapObject(String soapname, SoapObject soap) {
      System.out.println("in describeButtonSection addSoapObject");
      if (soapname == null) {
         super.addSoapObject(soapname, soap);
      }
      return null;
   }

}
