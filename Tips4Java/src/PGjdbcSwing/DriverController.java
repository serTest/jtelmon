/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PGjdbcSwing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
import java.text.*;

public class DriverController implements ActionListener, ExceptionListener
{
    private MvbView mvb = null;
    private DriverModel driver = null;
    public static final int OPERATIONSUCCESS = 0;
    public static final int OPERATIONFAILED = 1;
    public static final int VALIDATIONERROR = 2;

    public DriverController(MvbView mvb)
    {
        this.mvb = mvb;
        driver = new DriverModel();
        driver.addExceptionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        String actionCommand = e.getActionCommand();

        if (actionCommand.equals("Insert Driver"))
        {
            DriverInsertDialog iDialog = new DriverInsertDialog(mvb);
            iDialog.pack();
            mvb.centerWindow(iDialog);
            iDialog.setVisible(true);
            return;
        }

        if (actionCommand.equals("Show Driver"))
        {
            showAllDrivers();
            return;
        }
    }

    public void exceptionGenerated(ExceptionEvent ex)
    {
        String message = ex.getMessage();

        Toolkit.getDefaultToolkit().beep();

        if (message != null)
        {
            mvb.updateStatusBar(ex.getMessage());
        }
        else
        {
            mvb.updateStatusBar("An exception occurred!");
        }
    }

    private void showAllDrivers()
    {
        ResultSet rs = driver.showDriver();
        CustomTableModel model = new CustomTableModel(driver.getConnection(),
                                                      rs);
        CustomTable data = new CustomTable(model);

        model.addExceptionListener(this);
        data.addExceptionListener(this);
        mvb.addTable(data);
    }

    class DriverInsertDialog extends JDialog implements ActionListener
    {
        private JTextField driverID = new JTextField(10);
        private JTextField driverName = new JTextField(15);
        private JTextField driverAddr = new JTextField(15);
        private JTextField driverCity = new JTextField(10);
        private JTextField driverBirth = new JTextField(10);
        private JTextField driverPhone = new JTextField(10);

        public DriverInsertDialog(JFrame parent)
        {
            super(parent, "Insert Branch", true);
            setResizable(false);

            JPanel contentPane = new JPanel(new BorderLayout());
            setContentPane(contentPane);
            contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
                                                                  10));
            JPanel inputPane = new JPanel();
            inputPane.setBorder(BorderFactory.createCompoundBorder(
                         new TitledBorder(new EtchedBorder(), "Branch Fields"),
                         new EmptyBorder(5, 5, 5, 5)));

            GridBagLayout gb = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            inputPane.setLayout(gb);

            JLabel label= new JLabel("Driver SIN: ", SwingConstants.RIGHT);
            c.gridwidth = GridBagConstraints.RELATIVE;
            c.insets = new Insets(0, 0, 0, 5);
            c.anchor = GridBagConstraints.EAST;
            gb.setConstraints(label, c);
            inputPane.add(label);

            c.gridwidth = GridBagConstraints.REMAINDER;
            c.insets = new Insets(0, 0, 0, 0);
            c.anchor = GridBagConstraints.WEST;
            gb.setConstraints(driverID, c);
            inputPane.add(driverID);

            label = new JLabel("Driver Name: ", SwingConstants.RIGHT);
            c.gridwidth = GridBagConstraints.RELATIVE;
            c.insets = new Insets(5, 0, 0, 5);
            c.anchor = GridBagConstraints.EAST;
            gb.setConstraints(label, c);
            inputPane.add(label);

            c.gridwidth = GridBagConstraints.REMAINDER;
            c.insets = new Insets(5, 0, 0, 0);
            c.anchor = GridBagConstraints.WEST;
            gb.setConstraints(driverName, c);
            inputPane.add(driverName);

            label = new JLabel("Driver Address: ", SwingConstants.RIGHT);
            c.gridwidth = GridBagConstraints.RELATIVE;
            c.insets = new Insets(5, 0, 0, 5);
            c.anchor = GridBagConstraints.EAST;
            gb.setConstraints(label, c);
            inputPane.add(label);

            c.gridwidth = GridBagConstraints.REMAINDER;
            c.insets = new Insets(5, 0, 0, 0);
            c.anchor = GridBagConstraints.WEST;
            gb.setConstraints(driverAddr, c);
            inputPane.add(driverAddr);

            label = new JLabel("Driver City: ", SwingConstants.RIGHT);
            c.gridwidth = GridBagConstraints.RELATIVE;
            c.insets = new Insets(5, 0, 0, 5);
            c.anchor = GridBagConstraints.EAST;
            gb.setConstraints(label, c);
            inputPane.add(label);

            c.gridwidth = GridBagConstraints.REMAINDER;
            c.insets = new Insets(5, 0, 0, 0);
            c.anchor = GridBagConstraints.WEST;
            gb.setConstraints(driverCity, c);
            inputPane.add(driverCity);

            label = new JLabel("Driver Birthdate: ", SwingConstants.RIGHT);
            c.gridwidth = GridBagConstraints.RELATIVE;
            c.insets = new Insets(5, 0, 0, 5);
            c.anchor = GridBagConstraints.EAST;
            gb.setConstraints(label, c);
            inputPane.add(label);

            c.gridwidth = GridBagConstraints.REMAINDER;
            c.insets = new Insets(5, 0, 0, 0);
            c.anchor = GridBagConstraints.WEST;
            gb.setConstraints(driverBirth, c);
            inputPane.add(driverBirth);

            label = new JLabel("Driver Phone: ", SwingConstants.RIGHT);
            c.gridwidth = GridBagConstraints.RELATIVE;
            c.insets = new Insets(5, 0, 0, 5);
            c.anchor = GridBagConstraints.EAST;
            gb.setConstraints(label, c);
            inputPane.add(label);

            c.gridwidth = GridBagConstraints.REMAINDER;
            c.insets = new Insets(5, 0, 0, 0);
            c.anchor = GridBagConstraints.WEST;
            gb.setConstraints(driverPhone, c);
            inputPane.add(driverPhone);

            driverPhone.addActionListener(this);
            driverPhone.setActionCommand("OK");

            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
            buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 2));

            JButton OKButton = new JButton("OK");
            JButton cancelButton = new JButton("Cancel");

            OKButton.addActionListener(this);
            OKButton.setActionCommand("OK");

            cancelButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        dispose();
                    }
                });

            buttonPane.add(Box.createHorizontalGlue());
            buttonPane.add(OKButton);
            buttonPane.add(Box.createRigidArea(new Dimension(10,0)));
            buttonPane.add(cancelButton);

            contentPane.add(inputPane, BorderLayout.CENTER);
            contentPane.add(buttonPane, BorderLayout.SOUTH);

            addWindowListener(new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                    {
                        dispose();
                    }
                });
        }

        public void actionPerformed(ActionEvent e)
        {
            String actionCommand = e.getActionCommand();

            if (actionCommand.equals("OK"))
            {
                if (validateInsert() != VALIDATIONERROR)
                {
                    dispose();
                }
                else
                {
                    Toolkit.getDefaultToolkit().beep();

                    JOptionPane errorPopup = new JOptionPane();
                    errorPopup.showMessageDialog(this, "Invalid Input",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private int validateInsert()
        {
            try
            {
                Integer          did;
                String         dname;
                String         daddr;
                String         dcity;
                java.sql.Date dbirth;
                Integer       dphone;

                if (driverID.getText().trim().length() != 0)
                {
                    did = Integer.valueOf(driverID.getText().trim());

                    if (driver.findDriver(did.intValue()))
                    {
                        Toolkit.getDefaultToolkit().beep();
                        mvb.updateStatusBar("Driver " + did.toString() +
                                            " already exists!");

                        return OPERATIONFAILED;
                    }
                }
                else
                {
                    return VALIDATIONERROR;
                }

                if (driverName.getText().trim().length() != 0)
                {
                    dname = driverName.getText().trim();
                }
                else
                {
                    return VALIDATIONERROR;
                }

                if (driverAddr.getText().trim().length() != 0)
                {
                    daddr = driverAddr.getText().trim();
                }
                else
                {
                    return VALIDATIONERROR;
                }

                if (driverCity.getText().trim().length() != 0)
                {
                    dcity = driverCity.getText().trim();
                }
                else
                {
                    return VALIDATIONERROR;
                }

                if (driverBirth.getText().trim().length() != 0)
                {
                    String stringDate = driverBirth.getText().trim();
                    SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yy");

                    java.util.Date utilDate = fm.parse(stringDate);
                    dbirth = new java.sql.Date(utilDate.getTime());
                }
                else
                {
                    return VALIDATIONERROR;
                }

                if (driverPhone.getText().trim().length() != 0)
                {
                    dphone = Integer.valueOf(driverPhone.getText().trim());
                }
                else
                {
                    dphone = null;

                }

                mvb.updateStatusBar("Inserting driver...");

                if (driver.insertDriver(did, dname, daddr, dcity, dbirth,
                    dphone))
                {
                    mvb.updateStatusBar("Operation successful.");
                    showAllDrivers();

                    return OPERATIONSUCCESS;
                }
                else
                {
                    Toolkit.getDefaultToolkit().beep();
                    mvb.updateStatusBar("Operation failed.");

                    return OPERATIONFAILED;
                }
            }
            catch (NumberFormatException ex)
            {
                return VALIDATIONERROR;
            }
            catch (ParseException ex)
            {
                return VALIDATIONERROR;
            }
        }
    }
}
