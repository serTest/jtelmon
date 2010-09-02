/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JD034;

import java.awt.*;
import javax.swing.*;
class Meniu4 extends JFrame
{
private JPanel topPanel;
public Meniu4()
{
setTitle( "Menu Application #3" );
setSize( 310, 130 );
topPanel = new JPanel();
getContentPane().add( topPanel );
JMenuBar menuBar = new JMenuBar();
setJMenuBar( menuBar );
JMenu optionMenu = new JMenu( "Menu" );
menuBar.add( optionMenu );
JRadioButtonMenuItem menuCursorSmall =
new JRadioButtonMenuItem( "Small Cursor" );
optionMenu.add( menuCursorSmall );
JRadioButtonMenuItem menuCursorMedium =
new JRadioButtonMenuItem( "Medium Cursor" );
optionMenu.add( menuCursorMedium );
JRadioButtonMenuItem menuCursorLarge =
new JRadioButtonMenuItem( "Large Cursor" );
optionMenu.add( menuCursorLarge );
ButtonGroup cursorGroup = new ButtonGroup();
cursorGroup.add( menuCursorSmall );
cursorGroup.add( menuCursorMedium );
cursorGroup.add( menuCursorLarge );
}
public static void main( String args[] )
{
Meniu4 mainFrame = new Meniu4();
mainFrame.setVisible( true );
}
}