/*
 * http://www-acad.sheridanc.on.ca/~jollymor/prog24178/swing3d.html
 * KO? ToDo!
 */

package JD031;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ListEx1 extends JFrame implements ActionListener
{
	private JButton cmdAdd;
	private JButton cmdRem;
	private JTextField txtAnimal;
	private DefaultListModel model;
	private JList lstAnimals;

	public ListEx1() {
		super("List Box Example");

		cmdAdd = new JButton("Add to List");
		cmdAdd.addActionListener(this);
		cmdRem = new JButton("Remove from List");
		cmdRem.addActionListener(this);
		txtAnimal = new JTextField(15);

		model = new DefaultListModel();
		lstAnimals = new JList(model);
		lstAnimals.setVisibleRowCount(4);

		model.addElement("Cat");
		model.addElement("Cow");
		model.addElement("Dog");
		model.addElement("Fish");
		model.addElement("Monkey");

		JPanel pnlAnimal = new JPanel(new FlowLayout(FlowLayout.LEFT,3,0));
		pnlAnimal.add(new JLabel("New Animal:"));
		pnlAnimal.add(txtAnimal);

		JPanel pnlNorth = new JPanel(new GridLayout(0,1));
		pnlNorth.add(pnlAnimal);
		pnlNorth.add(cmdAdd);

		JScrollPane scrAnimals = new JScrollPane(lstAnimals,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		Container pane = this.getContentPane();

		pane.add(pnlNorth, BorderLayout.NORTH);
		pane.add(scrAnimals, BorderLayout.CENTER);
		pane.add(cmdRem, BorderLayout.SOUTH);

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

	}

	public static void main(String[] args) {
		ListEx1 list = new ListEx1();
	}
}
