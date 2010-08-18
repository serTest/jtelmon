/*
 * http://forums.sun.com/thread.jspa?threadID=5383117
 * Swing - Display arraylist using DefaultListModel
 *
 * java.sun.com/products/jfc/tsc/tech_topics/jlist_1/jlist.html
 * Article : Advanced JList Programming   
 * 
 * http://www.apl.jhu.edu/~hall/java/Swing-Tutorial/Swing-Tutorial-JList.html
 * JLists, Data Models, and Cell Renderers
 * 
 * 
 *
 */


package JD031;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;



public class PersonList extends JFrame {

    DefaultListModel listModel;
    JList personList;


   public PersonList(ArrayList<Person> person)
   {
       listModel = new DefaultListModel();

      for (int i = 0; i < person.size(); i++)
    {
         listModel.addElement(person.get(i));
     }
    Container cn = this.getContentPane();
    personList = new JList(listModel);
   cn.setLayout(new BorderLayout());
   cn.add(new JScrollPane(personList),BorderLayout.CENTER);
   this.setSize(600,200);
   this.setVisible(true);
   this.setTitle("Enjoy Media - Collection");

}


public static void main(String args[])
{
     ArrayList<Person> person = new ArrayList<Person>();

     person.add(new Person("Marcalous","Nocolas"));
     person.add(new Person("Albert","Thomas"));
     person.add(new Person("xxxxxxxxxxxxxx","tom"));


      PersonList app = new PersonList(person);
      app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }
}

class Person{

        private String fName;
        private String lName;
 
        public Person(String fName, String lName)
      {
            this.fName = fName;
            this.lName = lName;
      }

   public String toString()
   {
      return String.format("\n%-30s%-15s", fName, lName);
   }
}
