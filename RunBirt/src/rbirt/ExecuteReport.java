/*
 *  http://stackoverflow.com/questions/82123/initialization-troubles-with-birt-runtime-engine-api
 *  http://longlake.minnovent.com/repos/birt_example/
 *  http://www.reportingengines.com/forum/deploying-integrating-birt-report-engine-applications/13287-cant-get-working-simple-example.html
 *  http://www.birt-exchange.org/devshare/deploying-birt-reports/568-execute-birt-reports-from-java-class/#description
 *  http://www.samaxes.com/2008/06/23/birt-stripes-example/
 *  http://dev.eclipse.org/newslists/news.eclipse.birt/msg29068.html
 *  
 */

	
package rbirt;
// import java.util.logging.Level;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
public class ExecuteReport {
	// private static String BIRT_HOME = "E:\\BIRT\\birt-runtime-2_3_2_2\\ReportEngine\\plugins";
	// private static String BIRT_HOME = "E:\\BIRT\\birt-runtime-2_3_2_2\\ReportEngine";
	/**
	 * @param args
	 */
	public static void main(String[] args) {


        IReportEngine engine=null;
        EngineConfig config = null;

        try{
                config = new EngineConfig( );                   
                // config.setBIRTHome("E:\\BIRT\\birt-runtime-2_3_2_2\\ReportEngine");
                config.setBIRTHome("E:/BIRT/birt-runtime-2_3_2_2/ReportEngine");
                // config.setLogConfig("d:\\Temp\\temp", Level.FINEST);
                Platform.startup( config );
                IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
                engine = factory.createReportEngine( config );          

                IReportRunnable design = null;
                //Open the report design
                design = engine.openReportDesign("E:\\BIRT\\birt-runtime-2_3_2_2\\ReportEngine\\samples\\report005.rptdesign");
                IRunAndRenderTask task = engine.createRunAndRenderTask(design);                 

                HTMLRenderOption options = new HTMLRenderOption();              
                options.setOutputFileName("output/resample/Parmdisp.html");
                options.setOutputFormat("html");

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
