package passport.bimbim.in;
import java.util.Hashtable;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/*
 * Represents the Category object. KvmSerializable interface needs to be 
 * implemented for performing serialization.
 */

public class Category implements KvmSerializable
{
    public int CategoryId;
    public String Name;
    public String Description;
    
    public Category(){}
    

    public Category(int categoryId, String name, String description) {
        
        CategoryId = categoryId;
        Name = name;
        Description = description;
    }


    public Object getProperty(int arg0) {
        
        switch(arg0)
        {
        case 0:
            return CategoryId;
        case 1:
            return Name;
        case 2:
            return Description;
        }
        
        return null;
    }

    public int getPropertyCount() {
        return 3;
    }

    public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
        switch(index)
        {
        case 0:
            info.type = PropertyInfo.INTEGER_CLASS;
            info.name = "CategoryId";
            break;
        case 1:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "Name";
            break;
        case 2:
            info.type = PropertyInfo.STRING_CLASS;
            info.name = "Description";
            break;
        default:break;
        }
    }

    public void setProperty(int index, Object value) {
        switch(index)
        {
        case 0:
            CategoryId = Integer.parseInt(value.toString());
            break;
        case 1:
            Name = value.toString();
            break;
        case 2:
            Description = value.toString();
            break;
        default:
            break;
        }
    }
}
