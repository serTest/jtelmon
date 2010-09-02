/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JD034;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class MyToolBar extends JFrame implements ActionListener
{
private JPanel topPanel;
private JButton buttonNew;
private JButton buttonOpen;
private JButton buttonSave;
private JButton buttonCopy;
private JButton buttonCut;
private JButton buttonPaste;
public MyToolBar()
{
setTitle( "Basic Toolbar Application" );
setSize( 310, 130 );
setBackground( Color.gray );
topPanel = new JPanel();
topPanel.setLayout( new BorderLayout() );
getContentPane().add( topPanel );
// Creeza un toolbar
JToolBar myToolbar = new JToolBar();
topPanel.add( myToolbar, BorderLayout.NORTH );
// Adauga butoane la toolbar
buttonNew = addToolbarButton( myToolbar, false, "New",
"new", "Create a new document" );
buttonOpen = addToolbarButton( myToolbar, true, "Open","open",
"Open an existing document" );
buttonSave = addToolbarButton( myToolbar, true, "Save","save",
"Open an existing document" );
myToolbar.addSeparator();
buttonCopy = addToolbarButton( myToolbar, true, null,"copy",
"Copy selection to the clipboard" );
buttonCut = addToolbarButton( myToolbar, true, null,"cut",
"Cut selection to the clipboard" );
buttonPaste = addToolbarButton( myToolbar, true, null,"paste",
"Paste selection from the clipboard" );
// Adauga o zona de editare pentru a umple spatiul
JTextArea textArea = new JTextArea();
topPanel.add( textArea, BorderLayout.CENTER );
}
// Metoda care creeaza butoanele din toolbar
public JButton addToolbarButton( JToolBar toolBar,boolean bUseImage,
String sButtonText,String sButton, String sToolHelp )
{
JButton b;
// Creeaza un nou buton
if( bUseImage )
b = new JButton( new ImageIcon( sButton + ".gif" ) );
else
b = (JButton)toolBar.add( new JButton() );
// Adauga butonul la toolbar
toolBar.add( b );
// Adauga text la buton (optional)
if( sButtonText != null )
b.setText( sButtonText );
else
{
b.setMargin( new Insets( 0, 0, 0, 0 ) );
}
// Adauga "tool tip" (optional)
if( sToolHelp != null )
b.setToolTipText( sToolHelp );
// Ne asiguram ca butonul trimite un mesaj cand utilizatorul face click
// pe el
b.setActionCommand( "Toolbar:" + sButton );
b.addActionListener( this );
return b;
}
public void actionPerformed( ActionEvent event )
{
System.out.println( event );
}
public static void main( String args[] )
{
MyToolBar mainFrame = new MyToolBar();
mainFrame.setVisible( true );
}
}