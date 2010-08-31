/*
 * www.jopendocument.org/start_spreadsheet_1.html
 * 
 */

package ODS;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class TestODS2 {

   public static void main(String args[]) {
        try {
            final Object[][] data = new Object[6][2];
            data[0] = new Object[] { "January", 1 };
            data[1] = new Object[] { "February", 3 };
            data[2] = new Object[] { "March", 8 };
            data[3] = new Object[] { "April", 10 };
            data[4] = new Object[] { "May", 15 };
            data[5] = new Object[] { "June", 18 };
            String[] columns = new String[] { "Month", "Temp" };
            TableModel model = new DefaultTableModel(data, columns);
            final File file = new File("temperature.ods");
            SpreadSheet.createEmpty(model).saveAs(file);
            OOUtils.open(file);
        } catch (IOException ex) {
            Logger.getLogger(TestODS1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
