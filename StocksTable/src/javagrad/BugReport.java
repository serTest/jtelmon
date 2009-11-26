public class BugReport{
    final static String mailhost="somehost.somedomain.com";
    final static String to="santaclause@north.pole.ca";

    public static void report(String user, String message) {
	try {
	    Qsmtp connect=new Qsmtp(mailhost);
	    connect.sendmsg("<>",to, "Bug report from user "+user,
			message);
	} catch(Exception e){
	    System.out.println("Exception reporting crash. Continuing");
	}
	    
    }
	

}
