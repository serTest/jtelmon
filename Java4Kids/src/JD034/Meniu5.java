/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JD034;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class Meniu5 extends JFrame implements ActionListener
{
private JPanel topPanel;
private JPopupMenu popupMenu;
public Meniu5()
{

setTitle( "Popup Menu Application" );
setSize( 310, 130 );
setBackground( Color.gray );
topPanel = new JPanel();
topPanel.setLayout( null );
getContentPane().add( topPanel );
// Creeaza optiunile pentru meniul pop-up
JMenuItem menuFileNew = new JMenuItem( "New" );
JMenuItem menuFileOpen = new JMenuItem( "Open..." );
JMenuItem menuFileSave = new JMenuItem( "Save" );
JMenuItem menuFileSaveAs = new JMenuItem( "Save As..." );
JMenuItem menuFileExit = new JMenuItem( "Exit" );
// Creeaza un meniu pop-up
popupMenu = new JPopupMenu( "Menu" );
popupMenu.add( menuFileNew );
popupMenu.add( menuFileOpen );
popupMenu.add( menuFileSave );
popupMenu.add( menuFileSaveAs );
popupMenu.add( menuFileExit );
topPanel.add( popupMenu );
//Suport pentru ascultarea evenimentelor generate de actiunile cu
// mouse-ul
enableEvents( AWTEvent.MOUSE_EVENT_MASK );
menuFileNew.addActionListener( this );
menuFileOpen.addActionListener( this );
menuFileSave.addActionListener( this );
menuFileSaveAs.addActionListener( this );
menuFileExit.addActionListener( this );
}
public void processMouseEvent( MouseEvent event )
{
if( event.isPopupTrigger() )
{
popupMenu.show( event.getComponent(),
event.getX(), event.getY() );
}
super.processMouseEvent( event );
}
public void actionPerformed( ActionEvent event )
{
System.out.println( event );
}
public static void main( String args[] )
{
Meniu5 mainFrame = new Meniu5();
mainFrame.setVisible( true );
}
}