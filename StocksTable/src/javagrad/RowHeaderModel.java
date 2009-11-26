import javax.swing.AbstractListModel;
public class RowHeaderModel extends AbstractListModel{
    int size;

    public RowHeaderModel(int numRows){
	size=numRows;
    }
       

    public void removeElementAt(int row){
	fireIntervalRemoved(this,row,row);
	size--;
    }
    public int getSize(){
	return size;
    }
    public void addElement(){
	size++;
	fireIntervalAdded(this,size-1,size-1);
    }
    public Object getElementAt(int index){
	return new Integer(index);
    }
};
