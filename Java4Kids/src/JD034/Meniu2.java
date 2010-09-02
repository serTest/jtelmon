/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JD034;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class Meniu2 extends JFrame implements ActionListener
{
private final int ITEM_PLAIN = 0; // Item types
private final int ITEM_CHECK = 1;
private final int ITEM_RADIO = 2;
private JPanel topPanel;
private JMenuBar menuBar;
private JMenu menuFile;
private JMenu menuEdit;
private JMenu menuProperty;
private JMenuItem menuPropertySystem;
private JMenuItem menuPropertyEditor;
private JMenuItem menuPropertyDisplay;
private JMenuItem menuFileNew;
private JMenuItem menuFileOpen;
private JMenuItem menuFileSave;
private JMenuItem menuFileSaveAs;
private JMenuItem menuFileExit;
private JMenuItem menuEditCopy;
private JMenuItem menuEditCut;
private JMenuItem menuEditPaste;
public Meniu2()
{
setTitle( "Complete Menu Application" );
setSize( 310, 130 );topPanel = new JPanel();
topPanel.setLayout( new BorderLayout() );
getContentPane().add( topPanel );
// Creeaza bara de meniu
menuBar = new JMenuBar();
// Seteaza aceasta instanta ca fiind bara de meniu a aplicatiei
setJMenuBar( menuBar );
// creeaza un submeniu "Properties"
menuProperty = new JMenu( "Properties" );
menuProperty.setMnemonic( 'P' );
// Creeaza optiunile din submeniul "Properties"
menuPropertySystem = CreateMenuItem( menuProperty,ITEM_PLAIN,"System...", null, 'S', null );
menuPropertyEditor = CreateMenuItem(menuProperty, ITEM_PLAIN,"Editor...", null, 'E', null );
menuPropertyDisplay = CreateMenuItem(menuProperty,ITEM_PLAIN,"Display...", null, 'D', null );
// Creeaza un meniu "File"
menuFile = new JMenu( "File" );
menuFile.setMnemonic( 'F' );
menuBar.add( menuFile );
// Construieste optiunile meniului "File"
menuFileNew  = CreateMenuItem( menuFile,ITEM_PLAIN,"New",null,'N', null );
menuFileOpen = CreateMenuItem(menuFile,ITEM_PLAIN,"Open...",new ImageIcon("open.gif"), 'O',"Open a new file" );

menuFileSave = CreateMenuItem( menuFile, ITEM_PLAIN, "Save", new ImageIcon( "save.gif" ), 'S'," Save this file" );
menuFileSaveAs = CreateMenuItem( menuFile, ITEM_PLAIN,"Save As...", null, 'A',"Save this data to a new file" );
// Adauga submeniul "Properties" la meniul "File"
menuFile.addSeparator();
menuFile.add( menuProperty );
menuFile.addSeparator();
menuFileExit = CreateMenuItem( menuFile, ITEM_PLAIN,"Exit", null, 'x',"Exit the program" );
// Creeaza meniul "Edit"
menuEdit = new JMenu( "Edit" );
menuEdit.setMnemonic( 'E' );
menuBar.add( menuEdit );
// Creeaza optiunile meniului "Edit"
menuEditCut = CreateMenuItem( menuEdit, ITEM_PLAIN,"Cut", null, 't', "Cut data to the clipboard" );
menuEditCopy = CreateMenuItem( menuEdit, ITEM_PLAIN,"Copy", null, 'C', "Copy data to the clipboard" );
menuEditPaste = CreateMenuItem( menuEdit, ITEM_PLAIN,"Paste", null, 'P',"Paste data from the clipboard" );
}
public JMenuItem CreateMenuItem( JMenu menu, int iType,String sText, ImageIcon image,int acceleratorKey, String sToolTip )
{
// Creeaza optiunea meniului
JMenuItem menuItem;
switch( iType ){
    case ITEM_RADIO:
        menuItem = new JRadioButtonMenuItem();
        break;
    case ITEM_CHECK:
        menuItem = new JCheckBoxMenuItem();
        break;
    default:
        menuItem = new JMenuItem();
        break;
}
// Adauga textul optiunii
menuItem.setText( sText );
// Adauga imaginea (optional)
if( image != null )
menuItem.setIcon( image );
// Adauga acceleratorul (combinatia de taste)
if( acceleratorKey > 0 )
menuItem.setMnemonic( acceleratorKey );
// Adauga textul pentru "tool tip" (optional)
if( sToolTip != null )
menuItem.setToolTipText( sToolTip );

//Adauga un obiect ascultator de evenimente pentru aceasta optiune a
// meniului
menuItem.addActionListener( this );
menu.add( menuItem );
return menuItem;
}
public void actionPerformed( ActionEvent event )
{
    System.out.println( event );
}
public static void main( String args[] )
{
    Meniu2 mainFrame = new Meniu2();
    mainFrame.setVisible( true );
}
}