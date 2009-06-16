package rbirt;
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

public class ReportExecutor2 {
	// private static String BIRT_HOME = "E:\\BIRT\\birt-runtime-2_3_2_2\\ReportEngine";
	private static String BIRT_HOME = "/opt/BIRT/birt-runtime-2_3_2/ReportEngine";
	public static void main(String[] args) {
		PlatformConfig platformConfig = new PlatformConfig();
		platformConfig.setBIRTHome(BIRT_HOME);
		try {
			Platform.startup(platformConfig);
		} catch (BirtException e1) {
			e1.printStackTrace();
		}
		IReportEngineFactory factory =
		( IReportEngineFactory ) Platform.createFactoryObject
		( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
		 EngineConfig engineConfig = new EngineConfig();
		 engineConfig.setBIRTHome(BIRT_HOME); //will replace with configuration file
		 IReportEngine engine = factory.createReportEngine(engineConfig);		
		
		HTMLRenderOption ho = new HTMLRenderOption( );
		ho.setImageHandler( new HTMLCompleteImageHandler( ));
		
		String designName = "./SimpleReport.rptdesign";
		IReportRunnable runnable = null;
		try {
		runnable = engine.openReportDesign( designName );
		}
		catch ( EngineException e ) {
			System.err.println
			( "Design " + designName + " not found!" );
			engine.destroy( );
			System.exit( -1 );
		}
		// Get the value of a simple property.
		// @SuppressWarnings("unused")
		String author = ( String ) runnable.getProperty
		( IReportRunnable.AUTHOR );
		
		
		
		
		
	}

}
