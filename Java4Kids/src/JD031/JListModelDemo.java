/*
 * http://www.java2s.com/Code/Java/Swing-JFC/DemonstrateSwingJListListModel.htm
 * Demonstrate Swing JList ListModel
 */

package JD031;

/*
 * Copyright (c) Ian F. Darwin, http://www.darwinsys.com/, 1996-2002.
 * All rights reserved. Software written by Ian F. Darwin and others.
 * $Id: LICENSE,v 1.8 2004/02/09 03:33:38 ian Exp $
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * Java, the Duke mascot, and all variants of Sun's Java "steaming coffee
 * cup" logo are trademarks of Sun Microsystems. Sun's, and James Gosling's,
 * pioneering role in inventing and promulgating (and standardizing) the Java
 * language and environment is gratefully acknowledged.
 *
 * The pioneering role of Dennis Ritchie and Bjarne Stroustrup, of AT&T, for
 * inventing predecessor languages C and C++ is also gratefully acknowledged.
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Demonstrate Swing "JList" ListModel
 *
 * @author Ian Darwin
 * @author Tweaked by Jonathan Fuerth of SQLPower.ca
 */
public class JListModelDemo extends JListDemo {

  JListModelDemo(String s) {
    super(s);
    ListModel lm = new StaticListModel();
    list.setModel(lm);
    list.setCellRenderer(new MyCellRenderer());
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public static void main(String[] s) {
    JListModelDemo l = new JListModelDemo("ListModel");
    l.pack();
    l.setVisible(true);
  }

  class MyCellRenderer extends JLabel implements ListCellRenderer {

    /*
     * Get the Renderer for a given List Cell. This is the only method
     * defined by ListCellRenderer. If the object is already a component,
     * keep it, else toString it and wrap it in a JLabel. Reconfigure the
     * Component each time we're called to accord for whether it's selected
     * or not.
     */
    public Component getListCellRendererComponent(JList list, Object value, // value
                                        // to
                                        // display
        int index, // cell index
        boolean isSelected, // is the cell selected
        boolean cellHasFocus) // the list and the cell have the focus
    {
      Component c = null;
      if (value == null) {
        c = new JLabel("(null)");
      } else if (value instanceof Component) {
        c = (Component) value;
      } else {
        c = new JLabel(value.toString());
      }

      if (isSelected) {
        c.setBackground(list.getSelectionBackground());
        c.setForeground(list.getSelectionForeground());
      } else {
        c.setBackground(list.getBackground());
        c.setForeground(list.getForeground());
      }

      if (c instanceof JComponent) {
        ((JComponent) c).setOpaque(true);
      }

      return c;
    }
  }

  class StaticListModel implements ListModel {
    private final Object[] data = { "Hello", new Object(),
        new java.util.Date(), new JLabel("Hello world!"), this, };

    public Object getElementAt(int index) {
      return data[index];
    }

    public int getSize() {
      return data.length;
    }

    public void addListDataListener(ListDataListener ldl) {
      // since the list never changes, we don't need this :-)
    }

    public void removeListDataListener(ListDataListener ldl) {
      // since the list never changes, we don't need this :-)
    }
  }
}

class JListDemo extends JFrame {
  JList list = null;

  JListDemo(String s) {
    super(s);
    Container cp = getContentPane();
    cp.setLayout(new FlowLayout());
    ArrayList data = new ArrayList();
    data.add("Hi");
    data.add("Hello");
    data.add("Goodbye");
    data.add("Adieu");
    data.add("Adios");
    list = new JList(data.toArray());
    list.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent evt) {
        if (evt.getValueIsAdjusting())
          return;
        System.out.println("Selected from " + evt.getFirstIndex()
            + " to " + evt.getLastIndex());
      }
    });
    cp.add(list, BorderLayout.CENTER);
  }

  public static void main(String[] s) {
    JListDemo l = new JListDemo("Greetings");
    l.pack();
    l.setVisible(true);
  }
}
