package passport.bimbim.in;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

abstract public class ResultObject implements Serializable {

    private static final long serialVersionUID = 1L; // set to something appropriate
   
    /*//**
     * HashMap2 is used because later versions of Android HashMap are doing
     * something funky with loadFactor (missing) which causes an Exception in
     * Jni.c when a Serialized HashMap is inflated.
     */
   public HashMap<String, Object> mFields = new HashMap<String, Object>();

    public ResultObject() {
    }

    public ResultObject(String soapname, SoapObject soap) {
       System.out.println("in Constructor of resultObject with soapname and soapobject");
       addSoapObject(soapname, soap);
    }

    public ResultObject addSoapObject(String soapname, SoapObject soap) {
       System.out.println("in ReusltObject addSoapObject");
       storePrimitives(soap);
       return null;
    }

    public void storePrimitives(SoapObject soap) {
          System.out.println("in ReusltObject StorePrimitive");
       PropertyInfo propertyInfo = new PropertyInfo();
       int count = soap.getPropertyCount();
       System.out.println("count "+count);
       mFields.clear();
       for (int i = 0; i < count; i++) {
          System.out.println("In for loop Store Primitive"+i);
          Object obj1 = soap.getProperty(i);
          System.out.println("OBJECTTTTTTTTTTTTTTT"+obj1.toString());
          soap.getPropertyInfo(i, propertyInfo);
          System.out.println("propertyInfo  "+propertyInfo);
          if (obj1 instanceof SoapPrimitive) {
             // store the primitive
             System.out.println("if (obj1 instanceof SoapPrimitive)");
             String key = propertyInfo.name;
             System.out.println("if (obj1 instanceof SoapPrimitive)");
             System.out.println("Keyy iss"+key);
             String value = obj1.toString();
             System.out.println("Valueee"+value);
             // make a Vector if a value already exists for this key
             if (mFields.containsKey(key)) {
                Object o = mFields.get(key);
                if (o instanceof Vector<?>) { // add to existing Vector
                   ((Vector<Object>)o).add(value);               
                } else {
                   Vector<Object> v = new Vector<Object>();
                   v.add(value);
                   mFields.put(key, v);
                }
             } else {
                mFields.put(key, value);
             }
          }
       }
    }

    public String getString(String name) {
       System.out.println("in ReusltObject getString");
       Object soap = mFields.get(name);
       if (soap == null) {
          return "";
       }
       return soap.toString();
    }

   

}
