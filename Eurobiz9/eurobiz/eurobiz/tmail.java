package eurobiz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.IOException;
import com.lowagie.text.DocumentException;

/**
 * <p>Title: eurobiz</p>
 *
 * <p>Description: JBuilder 11 Ubuntu 5.04</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Spaghettiland</p>
 *
 * @author netlander
 * @version 0.2.1
 */
public class tmail
    extends JFrame {
  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileExit = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();
  JToolBar jToolBar = new JToolBar();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  JButton jButton3 = new JButton();
  ImageIcon image1 = new ImageIcon(eurobiz.tmail.class.getResource(
      "openFile.png"));
  ImageIcon image2 = new ImageIcon(eurobiz.tmail.class.getResource(
      "closeFile.png"));
  ImageIcon image3 = new ImageIcon(eurobiz.tmail.class.getResource("help.png"));
  JLabel statusBar = new JLabel();
  JButton jButton4 = new JButton();
  public tmail() {
    try {
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Component initialization.
   *
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {
    contentPane = (JPanel) getContentPane();
    contentPane.setLayout(borderLayout1);
    setSize(new Dimension(400, 300));
    setTitle("T-mail");
    statusBar.setText(" ");
    jMenuFile.setText("File");
    jMenuFileExit.setText("Exit");
    jMenuFileExit.addActionListener(new tmail_jMenuFileExit_ActionAdapter(this));
    jMenuHelp.setText("Help");
    jMenuHelpAbout.setText("About");
    jMenuHelpAbout.addActionListener(new tmail_jMenuHelpAbout_ActionAdapter(this));
    jButton3.addActionListener(new tmail_jButton3_actionAdapter(this));
    jButton2.addActionListener(new tmail_jButton2_actionAdapter(this));
    jButton1.addActionListener(new tmail_jButton1_actionAdapter(this));
    jButton1.setText("tele-mail");
    jButton2.setText("close");
    jButton3.setText("about");
    jButton4.setText("Problem");
    jButton4.addActionListener(new tmail_jButton4_actionAdapter(this));
    jMenuBar1.add(jMenuFile);
    jMenuFile.add(jMenuFileExit);
    jMenuBar1.add(jMenuHelp);
    jMenuHelp.add(jMenuHelpAbout);
    setJMenuBar(jMenuBar1);
    jButton1.setIcon(image1);
    jButton1.setToolTipText("Send tele-mails 2 deps");
    jButton2.setIcon(image2);
    jButton2.setToolTipText("Close File");
    jButton3.setIcon(image3);
    jButton3.setToolTipText("About");
    jToolBar.add(jButton1);
    jToolBar.add(jButton2);
    jToolBar.add(jButton3);
    jToolBar.add(jButton4);
    contentPane.add(jToolBar, BorderLayout.NORTH);
    contentPane.add(statusBar, BorderLayout.SOUTH);
  }

  /**
   * File | Exit action performed.
   *
   * @param actionEvent ActionEvent
   */
  void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
    System.exit(0);
  }

  /**
   * Help | About action performed.
   *
   * @param actionEvent ActionEvent
   */
  void jMenuHelpAbout_actionPerformed(ActionEvent actionEvent) {
    tmail_AboutBox dlg = new tmail_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                    (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.pack();
    dlg.show();
  }

  public void jButton3_actionPerformed(ActionEvent e) {
    jMenuHelpAbout_actionPerformed(e);
  }

  public void jButton2_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  public void jButton1_actionPerformed(ActionEvent e) {
    tele4deps.Search4Rar();
    String osName = System.getProperty("os.name");
    System.out.println(osName);
    // Linux ?
  }

  public void jButton4_actionPerformed(ActionEvent e) {
        System.out.println("Kuku");

        try {
          sqlsmtp.DoStuff();
        }
        catch (Exception exception) {
          exception.printStackTrace();
        }
  }

  class tmail_jButton1_actionAdapter
    implements ActionListener {
  private tmail adaptee;
  tmail_jButton1_actionAdapter(tmail adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}

class tmail_jMenuFileExit_ActionAdapter
    implements ActionListener {
  tmail adaptee;

  tmail_jMenuFileExit_ActionAdapter(tmail adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent) {
    adaptee.jMenuFileExit_actionPerformed(actionEvent);
  }
}

class tmail_jButton2_actionAdapter
    implements ActionListener {
  private tmail adaptee;
  tmail_jButton2_actionAdapter(tmail adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton2_actionPerformed(e);
  }
}

class tmail_jMenuHelpAbout_ActionAdapter
    implements ActionListener {
  tmail adaptee;

  tmail_jMenuHelpAbout_ActionAdapter(tmail adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent) {
    adaptee.jMenuHelpAbout_actionPerformed(actionEvent);
  }
}

class tmail_jButton3_actionAdapter
    implements ActionListener {
  private tmail adaptee;
  tmail_jButton3_actionAdapter(tmail adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {

    adaptee.jButton3_actionPerformed(e);
  }
}
    }

class tmail_jButton4_actionAdapter
    implements ActionListener {
  private tmail adaptee;
  tmail_jButton4_actionAdapter(tmail adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton4_actionPerformed(e);
  }
}
