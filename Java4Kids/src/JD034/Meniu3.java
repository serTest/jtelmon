/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JD034;

import java.awt.*;
import javax.swing.*;
class Meniu3 extends JFrame {
    private JPanel topPanel;
    public Meniu3(){
        setTitle( "Menu Application #2" );
        setSize( 310, 130 );
        setBackground( Color.gray );
        topPanel = new JPanel();
        topPanel.setLayout( new BorderLayout() );
        getContentPane().add( topPanel );
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar( menuBar );
        JMenu optionMenu = new JMenu( "Menu" );
        menuBar.add( optionMenu );
        // Creeaza optiunile meniului "check box"
        JCheckBoxMenuItem menuEditInsert= new JCheckBoxMenuItem( "Insert" );
        optionMenu.add( menuEditInsert );
        JCheckBoxMenuItem menuEditWrap= new JCheckBoxMenuItem( "Wrap lines" );
        optionMenu.add( menuEditWrap );
        JCheckBoxMenuItem menuEditCaps= new JCheckBoxMenuItem( "Caps Lock" );
        optionMenu.add( menuEditCaps );
    }
    public static void main( String args[] )
    {
        Meniu3 mainFrame = new Meniu3();
        mainFrame.setVisible( true );
    }
}
