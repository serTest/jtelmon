/*
 * www.programare.org/viewtopic.php?t=3549
 * Java de la 0 la Expert : pag. 744
 */

package JD034.Menu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
public class FereastraAplicatie extends JFrame {
static JMenuBar meniuBara;
final JTextArea text=new JTextArea();
Image img;
int pozitie=230;
public FereastraAplicatie() throws Exception {
super("Copy/Cut/Paste");
Toolkit t=this.getToolkit();
Dimension marimeEcran=Toolkit.getDefaultToolkit().getScreenSize();
setBounds(pozitie,pozitie,marimeEcran.width-2*pozitie,marimeEcran.height/2-30);
img=t.getImage("icon.jpg");
this.setIconImage(img);
this.getContentPane().setLayout(new BorderLayout());
faMeniu();
faBaraUnelte();
faContinut();
}
protected void faContinut() throws Exception {
JPanel continut=new JPanel(new BorderLayout());
continut.add(text);
text.setWrapStyleWord(true);
text.setLineWrap(true);
text.addMouseListener(new MouseAdapter(){
public void mousePressed(MouseEvent e){
int i=e.getClickCount();
if(e.getButton()==3 && i==1){
JPopupMenu pop=faMeniuPopup();
pop.show(text, e.getX(), e.getY());
pop.setVisible(true);
}
}
});
this.getContentPane().add(continut);
}
protected void faMeniu(){
meniuBara=new JMenuBar();
meniuBara.setOpaque(true);
JMenu editare=faMeniuEditare();
editare.setMnemonic('E');
meniuBara.add(editare);
setJMenuBar(meniuBara);
}
protected JMenu faMeniuEditare(){
JMenu editeaza=new JMenu("Editare");
JMenuItem Icopie=editeaza.add(new Copie("Copie",new ImageIcon("copie.gif")));
editeaza.addSeparator();
JMenuItem Itaie=editeaza.add(new Taie("Taie",new ImageIcon("taie.gif")));
editeaza.addSeparator();
JMenuItem Ilipeste=editeaza.add(new Lipeste("Lipeste",new ImageIcon("lipeste.gif")));
Icopie.setMnemonic('C');
Itaie.setMnemonic('T');
Ilipeste.setMnemonic('L');
return editeaza;
}
JPopupMenu faMeniuPopup(){
final JPopupMenu editeaza=new JPopupMenu("Editare");
JMenuItem Icopie=editeaza.add(new Copie("Copie",new ImageIcon("copie.gif")));
JMenuItem Itaie=editeaza.add(new Taie("Taie",new ImageIcon("taie.gif")));
JMenuItem Ilipeste=editeaza.add(new Lipeste("Lipeste",new ImageIcon("lipeste.gif")));
Icopie.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
Itaie.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,ActionEvent.CTRL_MASK));
Ilipeste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.CTRL_MASK));
return editeaza;
}
void faBaraUnelte(){
JToolBar tb=new JToolBar();
tb.add(new Copie("",new ImageIcon("copie.gif")));
tb.add(new Taie("",new ImageIcon("taie.gif")));
tb.add(new Lipeste("",new ImageIcon("lipeste.gif")));
this.getContentPane().add(tb,BorderLayout.NORTH);
}
class Copie extends AbstractAction {
Copie(String nume,ImageIcon icon){
super(nume,icon);
}
public void actionPerformed(ActionEvent e){
text.copy();
}

}
class Taie extends AbstractAction {
Taie(String nume,ImageIcon icon){
super(nume,icon);
}
public void actionPerformed(ActionEvent e){
text.cut();
}
}
class Lipeste extends AbstractAction {
Lipeste(String nume,ImageIcon icon){
super(nume,icon);
}
public void actionPerformed(ActionEvent e){
text.paste();
}
}
public void iesire(){
System.exit(0);
}
}
