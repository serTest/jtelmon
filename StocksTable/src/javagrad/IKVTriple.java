public class IKVTriple{
    private Object key;
    private Object value;
    private int index;

    public Object getValue(){
	return value;
    }

    public int getIndex(){
	return index;
    }

    public Object getKey(){
	return key;
    }
    public String toString(){
	return "IKVTriple[" +index+","+key+","+value+"]";
    }
    public IKVTriple(int theIndex, Object theKey, Object theValue){
	key=theKey;
	value=theValue;
	index=theIndex;
    }

}
    
