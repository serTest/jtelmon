public class Debug{

    static final private boolean enabled=false;

    static public void println(Object o){
	if (enabled)
	    System.err.println(o);
    }
}
