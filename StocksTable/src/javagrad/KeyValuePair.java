public class KeyValuePair{
    private Object key;
    private Object value;

    public Object getValue(){
	return value;
    }

    public Object getKey(){
	return key;
    }
    public String toString(){
	return "KeyValuePair[" +key+","+value+"]";
    }
    public KeyValuePair(Object theKey, Object theValue){
	key=theKey;
	value=theValue;
    }

}
    
