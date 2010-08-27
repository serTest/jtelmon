/*
  Copyright (c) 2005, Ulrich Hilger, Light Development, http://www.lightdev.com
  All rights reserved.

  Redistribution and use in source and binary forms, with or without modification, 
  are permitted provided that the following conditions are met:

    - Redistributions of source code must retain the above copyright notice, this 
       list of conditions and the following disclaimer.
       
    - Redistributions in binary form must reproduce the above copyright notice, 
       this list of conditions and the following disclaimer in the documentation 
       and/or other materials provided with the distribution.
       
    - Neither the name of Light Development nor the names of its contributors may be 
       used to endorse or promote products derived from this software without specific 
       prior written permission.

  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
  SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT 
  OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
  HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
  TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
*/

package boundtree;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * PersistentRegionEditor - an example application to demonstrate usage of a data 
 * persistence framework with JTree 
 *
 * <p>Use this sample code as a copanion to article 'Binding JTree to a database' available 
 * at <a href="http://articles.lightdev.com/persistent_tree/persistent_tree_article.pdf" target="_blank">
 * http://articles.lightdev.com/persistent_tree/persistent_tree_article.pdf</a>.</p>
 * 
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the BSD License,
 *      for details see file license.txt in the distribution package of this software
 *
 * @version 1, 5.8.2005
 */
public class PersistentRegionEditor extends JFrame implements ActionListener {
	
  /**
   * constructor
   */
  public PersistentRegionEditor(String server, String db, String user, String pw) {
  	this.server = server;
  	this.db = db;
  	this.user = user;
  	this.pw = pw;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK); // watch window events to notice exit attempt
		buildUi();
	}
  
  /**
   * application entry point
   * @param args  command line arguments
   */
    @SuppressWarnings({"ResultOfObjectAllocationIgnored", "CallToThreadDumpStack"})
	public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
/*
    String server = args[0];
    String db = args[1];
    String user = args[2];
    String pw = args[3];
*/
    String server = "192.168.61.205";
    String db = "contact_database";
    String user = "postgres";
    String pw = "telinit";

		new PersistentRegionEditor(server, db, user, pw);
	}

  /**
   * construct a tree that has all the enhancements described in the article this 
   * sample accompanies
   * @return an enhanced tree
   */
	private JPersistentTree getTree() {	
		/*
		 * this line was removed from the old RegionEditor example:
		 *    JTree tree = new JTree(getSampleTreeRoot());
		 *    
		 * instead only the following two lines are required to use a tree that 
		 * is bound to a database
		 */
		store = new RegionStoreAdapter(server, db, user, pw);
		JPersistentTree tree = new JPersistentTree(store);
		
    tree.setShowsRootHandles(true);
    tree.setEditable(true);
    tree.setCellEditor(
    		new UserTreeCellEditor(tree, (DefaultTreeCellRenderer) tree.getCellRenderer()));
    NodeMoveTransferHandler handler = new NodeMoveTransferHandler();
    tree.setTransferHandler(handler);
    tree.setDropTarget(new TreeDropTarget(handler));
    tree.setDragEnabled(true);
    return tree;
	}
	
  /**
   * build the user interface of this application
   */
	private void buildUi() {
		// create tree panel
    JPanel treePanel = new JPanel(new BorderLayout());    
    treePanel.setBorder(new EmptyBorder(3, 3, 3, 3));
    regTree = getTree();
    treePanel.add(new JScrollPane(regTree), BorderLayout.CENTER);
    
    // create button panel
    JPanel editBtnPanel = new JPanel(new GridLayout(2, 1));
    editBtnPanel.add(getButton(BTN_TXT_ADD));
    editBtnPanel.add(getButton(BTN_TXT_REMOVE));
    
    // helper panel to keep button panel in the upper right area
    JPanel rightPanel = new JPanel(new BorderLayout());
    rightPanel.setBorder(new EmptyBorder(3, 0, 3, 3));
    rightPanel.add(editBtnPanel, BorderLayout.NORTH);
    rightPanel.setPreferredSize(new Dimension(80,80));
    
    // button to show license
    rightPanel.add(getButton(BTN_TXT_LICENSE), BorderLayout.SOUTH);

    // set the layout of this frame
    setLayout(new BorderLayout());
    
    // add components to this frame
    Container contentPane = getContentPane();
    contentPane.add(treePanel, BorderLayout.CENTER);
    contentPane.add(rightPanel, BorderLayout.EAST);
    
    // set this frame's properties as approriate
    setTitle(APP_TITLE);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(300, 300);
    setLocationRelativeTo(null);
    setVisible(true);
	}
	
  /**
   * catch window closing events to handle application exit properly
   * @param e  the WindowEvent that led to this method call
   */
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      exitApp();
    }
    else {
      super.processWindowEvent(e);
    }
  }
  
  /**
   * handle application exit, i.e. close database connection 
   * before actually exiting in our case
   */
  public void exitApp() {
  	store.closeConnection();
  	System.exit(0);
  }

  /**
   * create a button
   * @param name  the name the button shall display
   * @return  the created button
   */
	private JButton getButton(String name) {
    JButton b = new JButton(name);
    b.addActionListener(this);
    return b;
	}
	
  /**
   * add a region to the currently selected tree node (if any). 
   */
  private void addRegion() {
  	regTree.createInSelectedNode(new Region(id, "new region " + id++));
  }

  /**
   * remove the currently selected tree node (if any)
   */
  private void removeRegion() {
  	regTree.deleteSelectedNode();
  }
  
  /**
   * show the license of this application
   * 
   * <p>This assumes a file named 'license.txt' to exist in 
   * the directory this class resides in.</p>
   */
    @SuppressWarnings("CallToThreadDumpStack")
  private void showLicense() {
		try {
	    InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("license.txt"));
	    BufferedReader br = new BufferedReader(isr);
            @SuppressWarnings("StringBufferMayBeStringBuilder")
	    StringBuffer buf = new StringBuffer();
	    String line;
			line = br.readLine();
	    while(line != null) {
	    	buf.append(line);
	    	buf.append(System.getProperty("line.separator"));
	    	line = br.readLine();
	    }
	    isr.close();
	    br.close();
	  	JDialog ld = new JDialog();
	  	ld.setLayout(new BorderLayout());
	    Container contentPane = ld.getContentPane();
	    JTextArea ta = new JTextArea(buf.toString());
	    ta.setLineWrap(false);
	    contentPane.add(new JScrollPane(ta));
	  	ld.setModal(true);
	  	ld.setTitle(BTN_TXT_LICENSE);
	  	ld.setSize(600,400);
	  	ld.setLocationRelativeTo(this);
	  	ld.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
  }
  
	/* ------------------------- ActionListener implementation start ------------------- */

  /**
   * route button clicks appropriately
   */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src instanceof JButton) {
			JButton b = (JButton) src;
			if(b.getText().equalsIgnoreCase(BTN_TXT_ADD)) {
				addRegion();
			}
			else if(b.getText().equalsIgnoreCase(BTN_TXT_REMOVE)) {
				removeRegion();
			}
			else if(b.getText().equalsIgnoreCase(BTN_TXT_LICENSE)) {
				showLicense();
			}
		}
	}
	
	/* ------------------------- ActionListener implementation end ------------------- */
  
  /* ------------------------- class fields start ------------------- */
  
  /** our tree */
  private JPersistentTree regTree;
  /** an field to help simulate region ids */
  private int id = 0;
  /** the data store to use */
	private RegionStoreAdapter store;
	/** name of the database server to connect to */
  private String server;
  /** name of the database name to use */
  private String db;
  /** name of user to connect as */
  private String user;
  /** password of user */
  private String pw;
	
  /* ------------------------- class fields end ------------------- */
	
	/* ------------------------- class constants start ------------------- */

  /** the title of the application */
  private static final String APP_TITLE = "PersistentRegionEditor";
  /** add button text */
	private static final String BTN_TXT_ADD = "add";
  /** remove button text */
	private static final String BTN_TXT_REMOVE = "remove";
	/** license button text */
	private static final String BTN_TXT_LICENSE = "License";
	
	/* ------------------------- class constants end ------------------- */

}
