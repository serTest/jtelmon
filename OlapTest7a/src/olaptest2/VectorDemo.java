/*
 * http://www.javabeginner.com/java-vector.htm
 * http://www.roseindia.net/java/beginners/vectordemo.shtml
 * http://java.sun.com/j2se/1.4.2/docs/api/java/util/Vector.html
 *
 */

package olaptest2;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class VectorDemo extends JFrame {

	private JLabel jlbString = new JLabel("Enter a string");
	public VectorDemo() {
		super("Vector class demo");
		// Made final as it can be accessed by inner classes
		final JLabel jlbStatus = new JLabel();
		Container contentPane = getContentPane();
		final Vector vector = new Vector(1);
		contentPane.setLayout(new FlowLayout());
		contentPane.add(jlbString);
		final JTextField jtfInput = new JTextField(10);
		contentPane.add(jtfInput);
		JButton jbnAdd = new JButton("Add");
		jbnAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				vector.addElement(jtfInput.getText().trim());
				jlbStatus.setText("Appended to end: "
						+ jtfInput.getText().trim());
				jtfInput.setText("");
			}
		});
		contentPane.add(jbnAdd);
		JButton jbnRemove = new JButton("Remove");
		jbnRemove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Returns true if element in vector
				if (vector.removeElement(jtfInput.getText().trim()))
					jlbStatus.setText("Removed: " + jtfInput.getText());
				else
					jlbStatus.setText(jtfInput.getText().trim()
							+ " not in vector");
			}
		});
		contentPane.add(jbnRemove);
		JButton jbnFirst = new JButton("First");
		jbnFirst.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					jlbStatus.setText("First element: "
							+ vector.firstElement());
				} catch (NoSuchElementException exception) {
					jlbStatus.setText(exception.toString());
				}
			}
		});
		contentPane.add(jbnFirst);
		JButton jbnLast = new JButton("Last");
		jbnLast.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					jlbStatus.setText("Last element: "
							+ vector.lastElement());
				} catch (NoSuchElementException exception) {
					jlbStatus.setText(exception.toString());
				}
			}
		});
		contentPane.add(jbnLast);
		JButton jbnEmpty = new JButton("Is Empty?");
		jbnEmpty.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jlbStatus.setText(vector.isEmpty() ? "Vector is empty"
						: "Vector is not empty");
			}
		});
		contentPane.add(jbnEmpty);
		JButton jbnContains = new JButton("Contains");
		jbnContains.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String searchKey = jtfInput.getText().trim();
				if (vector.contains(searchKey)) {
					jlbStatus.setText("Vector contains " + searchKey);
				} else {
					jlbStatus.setText("Vector does not contain "
							+ searchKey);
				}
			}
		});
		contentPane.add(jbnContains);
		JButton jbnFindElement = new JButton("Find");
		jbnFindElement.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jlbStatus.setText("Element found at location "
						+ vector.indexOf(jtfInput.getText().trim()));
			}
		});
		contentPane.add(jbnFindElement);
		JButton jbnTrim = new JButton("Trim");
		jbnTrim.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				vector.trimToSize();
				jlbStatus.setText("Vector trimmed to size");
			}
		});
		contentPane.add(jbnTrim);
		JButton jbnSize = new JButton("Size/Capacity");
		jbnSize.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jlbStatus.setText("Size = " + vector.size()
						+ "; Capacity = " + vector.capacity());
			}
		});
		contentPane.add(jbnSize);
		JButton jbnDisplay = new JButton("Display");
		jbnDisplay.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Enumeration enum1 = vector.elements();
				StringBuffer buf = new StringBuffer();
				while (enum1.hasMoreElements())
					buf.append(enum1.nextElement()).append(" ");
				JOptionPane.showMessageDialog(null, buf.toString(),
						"Contents of Vector", JOptionPane.PLAIN_MESSAGE);
			}
		});
		contentPane.add(jbnDisplay);
		contentPane.add(jlbStatus);
		setSize(300, 200);
		setVisible(true);
	}
	public static void main(String args[]) {
		VectorDemo vectorDemo = new VectorDemo();
		vectorDemo.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
