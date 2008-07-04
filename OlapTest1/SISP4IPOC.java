package com.atolcd.pentaho.kettle.plugin;

import java.util.ArrayList;
import java.util.List;

import org.palo.api.ConnectionFactory;
import org.palo.api.Consolidation;
import org.palo.api.Cube;
import org.palo.api.Dimension;
import org.palo.api.Element;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStep;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.normaliser.Messages;




/**
 * @author nmo -- ATOL C&D -- 
 * 
 *
 */
public class SISP4IPOC extends BaseStep implements StepInterface {
	
	private SISP4IPOCData data;
	private SISP4IPOCMeta meta;
	
	

	public SISP4IPOC(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans) {
		super(stepMeta, stepDataInterface, copyNr, transMeta, trans);
	}
	
	private void addConsolidation(Consolidation aCons, Element elem) throws Exception{
		Consolidation tmp[] = elem.getConsolidations();
		boolean alreadyIn = false;
		for (int i=0; i<tmp.length; i++){
			if (aCons.getChild() == tmp[i].getChild()){
				alreadyIn = true;
				break;
			}
		}
		if (!alreadyIn){
			Consolidation next[] = new Consolidation[tmp.length+1];
			for (int j=0; j<tmp.length; j++)
				next[j] = tmp[j];
			next[tmp.length] = aCons;
			elem.updateConsolidations(next);
		}
	}
	
	private void addDimensionParentElement(String value, String dimension, String parent, double weight) throws Exception{
		if (log.isDebug())
			logDebug("Call addDimensionParentElement with :'"+ value +"', '"+dimension+"', '"+parent+"'");
		Dimension dim = null;
		if ((parent == null)||(parent.equals(""))){// root element in a dimension
			if ((dim = data.database.getDimensionByName(dimension.trim())) == null){
				dim = data.database.addDimension(dimension.trim());
				if (log.isDebug())
					logDebug("Create Dimension : "+dimension);
			}
			if (dim.getElementByName(value.trim()) == null){
				if (log.isDebug())
					logDebug("adding Parent Element :"+value);
				dim.addElement(value.trim(), Element.ELEMENTTYPE_CONSOLIDATED);
			}
		}
		else {
			dim = data.database.getDimensionByName(dimension.trim());
			Element parentElement = dim.getElementByName(parent.trim());
			Element newElement = null;
			if ((newElement = dim.getElementByName(value.trim())) == null){
				newElement = dim.addElement(value.trim(), Element.ELEMENTTYPE_CONSOLIDATED);
				if (log.isDebug())
					logDebug("adding Parent Element :"+value);
			}
			addConsolidation(dim.newConsolidation(newElement, parentElement, weight), parentElement);
			if (log.isDebug())
				logDebug("Create Consolidation beetwen "+value+" and "+parent);
		}
	}
	
	private void addDimensionSingleElement(String value, String dimension, String parent, double weight) throws Exception{
		Dimension dim = null;
		if (log.isDebug())
			logDebug("Call addDimensionSingleElement with :'"+ value +"', '"+dimension+"', '"+parent+"'");
		if ((parent == null)||(parent.equals(""))){// il s'agit de l'element root de la dimension
			if ((dim = data.database.getDimensionByName(dimension.trim())) == null){
				dim = data.database.addDimension(dimension.trim());
				if (log.isDebug())
					logDebug("Create Dimension : "+dimension);
			}
			if (dim.getElementByName(value.trim()) == null){
				dim.addElement(value.trim(), Element.ELEMENTTYPE_NUMERIC);
				if (log.isDebug())
					logDebug("adding Single Element :"+value);
			}
		}
		else {
			dim = data.database.getDimensionByName(dimension.trim());
			Element parentElement = dim.getElementByName(parent.trim());
			Element newElement = null;
			if ((newElement = dim.getElementByName(value.trim())) == null){
				newElement = dim.addElement(value.trim(), Element.ELEMENTTYPE_NUMERIC);
				if (log.isDebug())
					logDebug("adding Single Element :"+value);
			}
			// on recupere les consolidations de l'element
			addConsolidation(dim.newConsolidation(newElement, parentElement, weight), parentElement);
			if (log.isDebug())
				logDebug("Create Consolidation beetwen "+value+" and "+parent);
		}
	}
	
	/**
	 * Check if all dimensions exists if DB, if not, create them
	 *
	 */
	public Dimension[] checkDimension() throws Exception{
		if (log.isDebug())
			logDebug("Checking Dimension existance");
		List dimName = getDimensionNameList();
		
		Dimension listDim[] = new Dimension[dimName.size()];
		
		for (int i=0; i< dimName.size(); i++){
			Dimension aDim = null;
			if ((aDim = data.database.getDimensionByName(((String) dimName.get(i)).trim())) == null){
				aDim = data.database.addDimension(((String) dimName.get(i)).trim());
				if (log.isDebug())
					logDebug("Adding dimension '"+(String) dimName.get(i)+"'");
			}
			listDim[i] = aDim;
		}
		return listDim;
	}
	
	/**
	 * Checking if all define cube exists, if else creating them
	 *
	 */
	public void checkCube() throws Exception{
		if (log.isDebug())
			logDebug("Checking Cube existence");
		data.cube = new Cube[meta.valueNode.size()];
		Dimension listDim[] = checkDimension();
		for (int i=0; i< meta.valueNode.size(); i++){
			// if cube doesn't exist = create it
			Cube myCube = null;
			String cubeName = meta.getCubeName()[meta.valueNode.get(i).intValue()];
			if ((myCube = data.database.getCubeByName(cubeName.trim())) == null){
				myCube = data.database.addCube(cubeName.trim(), listDim);
			}
			data.cube[i] = myCube;
		}
	}
	
	public List getDimensionNameList() throws Exception{
		ArrayList<String> result = new ArrayList<String>();
		for (int k=0; k<meta.elementNode.size(); k++){
		//for (int i=0; i< meta.getDimensionName().length; i++){
			int i= ((Integer)meta.elementNode.get(k)).intValue();
			if ((meta.getDimensionName()[i] != null) && (!meta.getDimensionName()[i].equals("")))
				if (! result.contains((String)meta.getDimensionName()[i]))
					result.add((String)meta.getDimensionName()[i]);
		}
		return result;
	}

	public boolean processRow(StepMetaInterface smi, StepDataInterface sdi)
			throws KettleException {
		meta = (SISP4IPOCMeta)smi;
	    data = (SISP4IPOCData)sdi;
	    boolean sendToErrorRow=false;
		String errorMessage = null;

	    
	    Object[] r=getRow();    // get row, blocks when needed!
		if (r==null)  // no more input to be expected...
		{
			setOutputDone();
			logBasic("processNoRow");
			return false;
		}
		
		
		
		if (first){
			try {
				meta.computeParentNode();
				meta.computeElementNode();
				meta.computeValueNode();
				logDebug("ParentNode = "+meta.parentNode);
				logDebug("ElementNode = "+meta.elementNode);
				logDebug("Value = "+meta.valueNode);
				
	//			 Cache lookup indexes of fields
				//
				
				data.inputRowMeta = getInputRowMeta();
				data.fieldnrs=new int[meta.getFieldName().length];
				for (int i=0;i<meta.getFieldName().length;i++)
				{
					data.fieldnrs[i] = data.inputRowMeta.indexOfValue(meta.getFieldName()[i]);
					if (data.fieldnrs[i]<0)
					{
						logError(Messages.getString("SISP4IPOC.Log.CouldNotFindFieldInRow",meta.getFieldName()[i])); //$NON-NLS-1$ //$NON-NLS-2$
						setErrors(1);
						stopAll();
						return false;
					}
				}
				
				// check if cubes exist, if not not, create them
				checkCube();
				
				first = false;
			}
			catch (Exception e){
				throw new KettleException(e.getMessage());
			}
		}
	
		try {
			// adding parents field
			if (log.isDebug())
				logDebug("Adding Parents ....");
			for (int i=0; i<meta.parentNode.size(); i++){
				int val= ((Integer)meta.parentNode.get(i)).intValue();
				
				String parentValue = null;
				int indexField = meta.getIndexField((String)meta.getParentName()[val]);
				if (indexField == -1)
					parentValue = null;
				else parentValue = (String) r[data.fieldnrs[indexField]];
				
				//float weight = new Float(0).floatValue();
				double weight = new Double(0).doubleValue();
				try {
				//	weight = Float.parseFloat((String)meta.getWeight()[val]);
					weight = new Double(meta.getWeight()[val]);
				}catch (Exception e){	
				}
				
				if (log.isDebug())
					logDebug("Calling addDimensionParentElement with arguments : "+r[data.fieldnrs[val]]+", "+meta.getDimensionName()[val]+", "+parentValue+", "+weight);
				addDimensionParentElement((String)r[data.fieldnrs[val]], (String)meta.getDimensionName()[val], parentValue, weight);
			}
	
			// adding non-parent fields in dimension and create a consolidation beetwen this field and his parent
			if (log.isDebug())
				logDebug("Adding Childrens ...");
			for (int i=0; i<meta.elementNode.size(); i++){
				int val= ((Integer)meta.elementNode.get(i)).intValue();
				
				String parentValue = null;
				int indexField = meta.getIndexField((String)meta.getParentName()[val]);
				if (indexField == -1)
					parentValue = null;
				else parentValue = (String) r[data.fieldnrs[indexField]];
				
				double weight = new Double(0).doubleValue();
				try {
				//	weight = Float.parseFloat((String)meta.getWeight()[val]);
					weight = new Double(meta.getWeight()[val]);
				}catch (Exception e){	
				}
	
				if (log.isDebug())
					logDebug("Calling addDimensionSingleElement with arguments : "+r[data.fieldnrs[val]]+", "+meta.getDimensionName()[val]+", "+parentValue+", "+weight);
				addDimensionSingleElement((String)r[data.fieldnrs[val]], (String)meta.getDimensionName()[val], parentValue, weight);
			}
			// add value
			if (log.isDebug()){
				logDebug("Adding value ...");
				for (int k=0; k<r.length; k++)
					logDebug("Row "+k+" = "+r[k]);
				logDebug("nodeValue = "+meta.valueNode);
			}
	
			for (int i=0; i< meta.valueNode.size(); i++){
			
				int val = ((Integer)meta.valueNode.get(i)).intValue();
				
				//Float value = new Float(0);
				Double value = new Double(0);

				//value= new Float((String)r[data.fieldnrs[val]]);
				Object tmp = r[data.fieldnrs[val]];
				if (tmp instanceof String)
					value = new Double((String) r[data.fieldnrs[val]]);
				else if (tmp instanceof Double)
					value = new Double((Double) r[data.fieldnrs[val]]);
				else if (tmp instanceof Integer)
					value = new Double((Integer) r[data.fieldnrs[val]]);
				else throw new Exception("Unsupported Data Type for cube Value");

				
				String list[] = new String[meta.elementNode.size()];
				int num=0;
		
				for (int k=0; k<meta.elementNode.size(); k++){
					int aVal= ((Integer)meta.elementNode.get(k)).intValue();
					list[num++]=(String)r[data.fieldnrs[aVal]];
				}

		
				if (log.isDebug()){
					logDebug("Coordonnees = ");	
					for (int y=0; y<list.length; y++)
						logDebug(y+" : "+list[y]);
					logDebug("Valeur = "+value);
				}
				
				Cube aCube = data.cube[i];

				Float aVal = new Float(value);
				aCube.setData(list, aVal.toString());

			}
		}catch (Exception e){
			if (getStepMeta().isDoingErrorHandling())
			{
		          sendToErrorRow = true;
		          errorMessage = e.toString();
			}
			else
			{
				setErrors(1);
				stopAll();
				setOutputDone();  // signal end to receiver(s)
				return false;
			}
			if (sendToErrorRow)
			{
				try {
					putError(getInputRowMeta(), r, 1, errorMessage, null, "SISP4IPOC001");
				}catch (Exception e2){}
			}

		}

		if (checkFeedback(linesRead)) logBasic(Messages.getString("SISP4IPOC.Log.LineNumber")+linesRead); //$NON-NLS-1$
		
	
		return true;
	}
	
	
	
	public boolean init(StepMetaInterface smi, StepDataInterface sdi)
	{
	    meta = (SISP4IPOCMeta)smi;
	    data = (SISP4IPOCData)sdi;

	    return super.init(smi, sdi);
	}

	public void dispose(StepMetaInterface smi, StepDataInterface sdi)
	{
	    meta = (SISP4IPOCMeta)smi;
	    data = (SISP4IPOCData)sdi;

	    super.dispose(smi, sdi);
	}
	
	//
	// Run is were the action happens!
	public void run()
	{
		logBasic("Starting to run...");
//		 get connection's informations 
	    
		if (data == null){
			logError ("Data is null");
        	setErrors(1);
			stopAll();
		}
		if ( meta.getConnectionPassword() == null){
			logError ("No Passwd");
        	setErrors(1);
			stopAll();
		}

//	  connect to palo server on localhost with default credentials.
		if (log.isDebug())
			logDebug("Try connect to Palo Server '"+meta.getConnectionServer()
				+":"+meta.getConnectionPort()+"' with "
				+meta.getConnectionLogin()+"/"+meta.getConnectionPassword());
		try{
		    data.connection = ConnectionFactory.getInstance().newConnection(
	        		meta.getConnectionServer(),
	        		meta.getConnectionPort(),
	        		meta.getConnectionLogin(), meta.getConnectionPassword());
		    
		    if (data.connection == null){
		    	logError ("Can't connect to the Palo server . Exiting ...");
	        	setErrors(1);
				stopAll();
		    }
		    logDebug("Connected to the Palo Server");
		    
		    logDebug("Trying to get the Palo's DB '"+meta.getConnectionDatabase()+"'");
		    
		    
		    data.database = data.connection.getDatabaseByName(meta.getConnectionDatabase());
		    if (data.database == null) {
		    	// creating it
		    	try {
		    		if (log.isDebug())
		    			logDebug("Creating DB : "+meta.getConnectionDatabase());
		    		data.database = data.connection.addDatabase(meta.getConnectionDatabase());
		    	}
		    	catch (Exception e){
		        	logError ("Database '" + meta.getConnectionDatabase() + "' not found. exiting...");
		        	setErrors(1);
					stopAll();
		    	}
	        }
		    logDebug("Database '"+data.database.getName()+"' found on '"+meta.getConnectionServer()+":"+meta.getConnectionPort()+"'");

		}
		catch (Exception e){
			logError("Unexpected error : "+e.toString());
            logError(Const.getStackTracker(e));
			setErrors(1);
			stopAll();
		}
	        
		try
		{
			while (processRow(meta, data) && !isStopped());
		}
		catch(Exception e)
		{
			data.connection.disconnect();
			logError("Unexpected error : "+e.toString());
            logError(Const.getStackTracker(e));
			setErrors(1);
			stopAll();
		}
		finally
		{
			data.connection.disconnect();
		    dispose(meta, data);
			logBasic("Finished, processing "+linesRead+" rows");
			markStop();
		}
	}

}
