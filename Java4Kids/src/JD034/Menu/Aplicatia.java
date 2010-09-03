/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JD034.Menu;

import javax.swing.*;
import java.beans.*;
// import com.incors.plaf.kunststoff.KunststoffLookAndFeel;

public class Aplicatia {
public static void main(String[] args) {
try {
// UIManager.setLookAndFeel("com.incors.plaf.kunststoff.KunststoffLookAndFeel");
FereastraAplicatie fereastra= new FereastraAplicatie();
fereastra.setVisible(true);
{
Icon icon=new ImageIcon("icon.jpg");
JOptionPane.showMessageDialog(fereastra,"Titlu personalizat si icon personalizat","Mesaj Personalizat",JOptionPane.INFORMATION_MESSAGE,icon);
Object[] optiuni={"Da,va rog!","In nici un caz!"};
int nr=JOptionPane.showOptionDialog(fereastra, "Alege Optiune", "Mesaj cu optiuni personalizate", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, optiuni, optiuni[0]);
System.out.println("S-a ales butonul:" +optiuni[nr]);
int numarul=JOptionPane.showConfirmDialog(fereastra, "Apasati butonul dorit", "Mesaj cu optiuni implicite", JOptionPane.YES_NO_OPTION);
System.out.println("S-a ales butonul: "+optiuni[numarul]);
String iesire=(String)JOptionPane.showInputDialog(fereastra,"Va rugam introduceti o propozitie","Dialog de confirmare",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("icon.jpg"),null,"Introduceti aici");
System.out.println("S-a introdus: "+iesire);
String[] pachete=new String[] {"AWT","SWING","Accesebility","Java 2D","Drag and Drop"};
String iesirea=(String)JOptionPane.showInputDialog(fereastra,"Alegeti pachetul preferat","Dialog de confirmare",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("icon.jpg"),pachete,"Java 2 D");
System.out.println("S-a introdus: "+iesirea);
final JOptionPane panouOptiuni=new JOptionPane("Singurul mod de a inchide fereastra este\n"+"prin apasarea unuia din butoanele.\n"+"Mesaj cu optiuni",JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_OPTION);
final JDialog dialog=new JDialog(fereastra,"Apasati un buton",true);
dialog.setContentPane(panouOptiuni);
panouOptiuni.addPropertyChangeListener(new PropertyChangeListener(){
public void propertyChange(PropertyChangeEvent e){
String prop=e.getPropertyName();
if(dialog.isVisible() && (e.getSource()==panouOptiuni)&&(prop.equals(JOptionPane.VALUE_PROPERTY)||prop.equals(JOptionPane.INPUT_VALUE_PROPERTY)));
{
dialog.setVisible(false);
}
}
});
dialog.pack();
dialog.setVisible(true);
int valoare=((Integer)panouOptiuni.getValue()).intValue();
if(valoare==JOptionPane.YES_OPTION){
System.out.println("S-a ales butonul <Da>");
}
else
System.out.println("S-a ales butonul <Nu>");
}

}
catch(Exception ex){
System.out.println("Eroare"+ex.toString());
}

}

}
