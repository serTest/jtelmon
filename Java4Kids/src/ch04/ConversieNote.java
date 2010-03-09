package ch04;

public class ConversieNote {

      public char conversieNote( int testResult){
	char grade;
	if (testResult >= 9){
		grade = 'A';
	}else if (testResult >= 8 && testResult < 9){
                grade = 'B';
	  }else if (testResult >= 7 && testResult < 8){
		grade = 'C';
	}else {
		grade = 'D';
	}
	return grade;
   }

   public static void main(String[] args){
	ConversieNote rc = new ConversieNote();

        char yourGrade = rc.conversieNote(8);
	System.out.println("Your first grade is " +   yourGrade);
	yourGrade = rc.conversieNote(6);
	System.out.println("Your second grade is " +   yourGrade);


   }
}
