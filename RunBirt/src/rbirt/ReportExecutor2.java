package rbirt;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.IPlatformContext;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.framework.PlatformFileContext;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLCompleteImageHandler;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.RenderOptionBase;

@SuppressWarnings("deprecation")
public class ReportExecutor2 {

	public static void main(String[] args) {
		// Create an EngineConfig object.
		EngineConfig config = new EngineConfig( );
		// Set up the path to your BIRT home directory.
		config.setBIRTHome
		( "E:/BIRT/birt-runtime-2_3_2_2/ReportEngine" );
		IPlatformContext context = new PlatformFileContext( );
		config.setEngineContext( context );
		// Start the platform for a non-RCP application.
		try {
			Platform.startup( config );
		} catch (BirtException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IReportEngineFactory factory =
		( IReportEngineFactory ) Platform.createFactoryObject
		( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
		// Set up writing images or charts embedded in HTML output.
		HTMLRenderOption ho = new HTMLRenderOption( );
		ho.setImageHandler( new HTMLCompleteImageHandler( ));
		config.setEmitterConfiguration
		( RenderOptionBase.OUTPUT_FORMAT_HTML, ho );
		// Create the engine.
		IReportEngine engine = factory.createReportEngine( config );
		
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
		@SuppressWarnings("unused")
		String author = ( String ) runnable.getProperty
		( IReportRunnable.AUTHOR );
		
		
		
		
		
	}

}
