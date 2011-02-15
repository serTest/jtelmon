package passport.bimbim.in;

import org.ksoap2.serialization.SoapObject;
import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableSoapObject implements Parcelable {
private SoapObject soapObject;


public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
public ParcelableSoapObject createFromParcel(Parcel in) {
ParcelableSoapObject pSObject=new ParcelableSoapObject();
SerializedSoap sSoap=(SerializedSoap)in.readSerializable();
pSObject.setSoapObject(sSoap.toSoapObject());
return pSObject;
}

public ParcelableSoapObject[] newArray(int size) {
return new ParcelableSoapObject[size];
}
};



@Override
public int describeContents() {
return 0;
}

@Override
public void writeToParcel(Parcel parcel, int arg1) {
SerializedSoap aSerializedSoap=new SerializedSoap(getSoapObject());
parcel.writeSerializable(aSerializedSoap);
}

public SoapObject getSoapObject() {
return soapObject;
}

public void setSoapObject(SoapObject soapObject) {
this.soapObject = soapObject;
}

public String getType(){
Object o=(getSoapObject()!=null)?getSoapObject().getProperty("type"):null;
return (o!=null)?o.toString():null;
}

public String getName(){
Object o=(getSoapObject()!=null)?getSoapObject().getProperty("name"):null;
return (o!=null)?o.toString():null;
}

public String getId(){
Object o=(getSoapObject()!=null)?getSoapObject().getProperty("Id"):null;
return (o!=null)?o.toString():null;
}

}