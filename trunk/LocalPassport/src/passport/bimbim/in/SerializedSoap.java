package passport.bimbim.in;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class SerializedSoap implements Serializable {
static final public String UTF8_Encoding="UTF-8";

public String namespace;
public String name;
public byte[] bytes;

public SerializedSoap(String namespace, String name, byte[] bytes){
this.namespace=namespace;
this.name=name;
this.bytes=bytes;
}

public SerializedSoap(SoapObject soap){
SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
envelope.setOutputSoapObject(soap);
XmlSerializer aSerializer=Xml.newSerializer();
ByteArrayOutputStream os=new ByteArrayOutputStream();
try {
aSerializer.setOutput(os, UTF8_Encoding);
envelope.write(aSerializer);
aSerializer.flush();
} catch (Exception e) {
e.printStackTrace();
}
namespace=soap.getNamespace();
name=soap.getName();
bytes=os.toByteArray();
}

public SoapObject toSoapObject(){
SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
SoapObject soap=null;
try {
ByteArrayInputStream inputStream=new ByteArrayInputStream(bytes);
XmlPullParser p=Xml.newPullParser();
p.setInput(inputStream, UTF8_Encoding);
envelope.parse(p);
soap=(SoapObject)envelope.bodyIn;
} catch (Exception e) {
e.printStackTrace();
}

return soap;
}
}