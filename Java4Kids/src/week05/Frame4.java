/*
 * http://home.cogeco.ca/~ve3ll/jatutorb.htm
 * 
 */

package week05;

import java.awt.*; import java.awt.event.*; import javax.swing.*;
public class Frame4 extends JFrame implements ActionListener,ItemListener
{
  JPanel panel=new JPanel();  // create pane content object
  JLabel prompt=new JLabel(" Sports Watched ");
  JCheckBox ow=new JCheckBox("Open Wheel (eg F1, CART)",false);
  JCheckBox fg=new JCheckBox("Fender (eg NASCAR, Busch)",false);
  JButton pressme=new JButton("Press Me");
  Frame4()   // the constructor
  {
    super("CheckBox Demo"); setBounds(50,50,240,200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container con=this.getContentPane(); // inherit main frame
    con.add(panel);
    panel.setLayout(new GridLayout(4,1,10,4)); // reset manager
    panel.add(prompt); panel.add(ow); panel.add(fg); panel.add(pressme);
    ow.addItemListener(this); // register checkbox and button events
    fg.addItemListener(this); pressme.addActionListener(this);
    pressme.setMnemonic('P'); setVisible(true);
  }
  // now the basic event listeners
  public void itemStateChanged(ItemEvent ie)
  {
    Object source=ie.getItem();
    if (source == fg)
    {
       int select=ie.getStateChange();
       if (select == ItemEvent.SELECTED)
       {
          // do something
   	   }
    }
  }
  public void actionPerformed(ActionEvent ae)
  {
    Object source=ae.getSource();
    if (source == pressme) // button from above example
    JOptionPane.showMessageDialog(null,"I hear you!","Message Dialog",
    JOptionPane.PLAIN_MESSAGE);   ;// show something
  }
  // and finally the main method
  public static void main(String args[]) {new Frame4();}
}