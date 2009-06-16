/*
 * http://wiki.eclipse.org/Simple_Execute_(BIRT)_2.1
 * http://wiki.eclipse.org/Java_-_Execute_Modified_Report_(BIRT)
 * 
 */
package rbirt;
import java.util.logging.Level;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.framework.PlatformConfig;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLCompleteImageHandler;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
public class ExecuteReportBirt22 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IReportEngine engine=null;
		EngineConfig config = null;

		try{
			config = new EngineConfig( );			
			// config.setBIRTHome("C:\\birt\\birt-runtime-2_2_1\\birt-runtime-2_2_1\\ReportEngine");
			config.setBIRTHome("/opt/BIRT/birt-runtime-2_3_2/ReportEngine");
			config.setLogConfig("/opt/tmp/test", Level.FINEST);
			Platform.startup( config );
			IReportEngineFactory factory = (IReportEngineFactory) Platform
			.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
			engine = factory.createReportEngine( config );		

			IReportRunnable design = null;
			//Open the report design
			design = engine.openReportDesign("Reports/customers.rptdesign"); 
			IRunAndRenderTask task = engine.createRunAndRenderTask(design); 		
			//task.setParameterValue("Top Count", (new Integer(5)));
			//task.validateParameters();
					
			HTMLRenderOption options = new HTMLRenderOption();		
			options.setOutputFileName("output/resample/Parmdisp.html");
			options.setOutputFormat("html");
			//options.setHtmlRtLFlag(false);
			//options.setEmbeddable(false);
			//options.setImageDirectory("C:\\test\\images");

			//PDFRenderOption options = new PDFRenderOption();
			//options.setOutputFileName("c:/temp/test.pdf");
			//options.setOutputFormat("pdf");

			task.setRenderOption(options);
			task.run();
			task.close();
			engine.destroy();
		}catch( Exception ex){
			ex.printStackTrace();
		}		
		finally
		{
		       Platform.shutdown( );
		}      
		
	}

}
