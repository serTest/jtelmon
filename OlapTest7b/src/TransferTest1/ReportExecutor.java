/*
http://www.packtpub.com/article/deployment-of-reports-with-birt
http://digiassn.blogspot.com/2008/08/birt-launch-birt-rcp-application.html
http://opensource.sys-con.com/node/336872?page=0,1
*/

package TransferTest1;
import java.util.HashMap;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.framework.PlatformConfig;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
public class ReportExecutor {
private static String BIRT_HOME = "E:/BIRT/birt-runtime-2_3_2_2/ReportEngine";
private static String IMAGE_PATH = "E:/BIRT/images";
private String reportLocation;
private String reportOutputLocation;
/**
* setupCLIParameters
*
* This will setup the arguments
* @return
*/
public Options setupCLIParameters()
{
Options options = new Options();
options.addOption("i", "input", true, "The report file to execute");
options.addOption("o", "output", true, "The name of the output file");
return options;
}
/**
* parseCommandLineOptions
*
* Given the arguments passed into main, this method will use the
* Apache Commons CLI
* to parse those options and return
* a CommandLine object with the options
*
* @param args
* @return CommandLine
*/
public CommandLine parseCommandLineOptions(String []args)
{
//First, parse the command line options using Apache Commons CLI
CommandLineParser parser = new PosixParser();
Options options = setupCLIParameters();
CommandLine line = null;
HelpFormatter formatter = new HelpFormatter();
//Try to parse the command line options, exit the app if there is an error
try {
//Get the options
   line = parser.parse(options, args);
}
catch (Exception e) {
System.err.println("Parsing failed. Reason: " + e.getMessage());
formatter.printHelp("ReportExecutor", options);
System.exit(-1);
}
return line;
}
/**
* startupPlatform
*
* This will startup the Eclipse platform and load any plugins
*/
private void startupPlatform()
{
//Initialize the Eclipse platform, plug-ins, and Report Engine
PlatformConfig platformConfig = new PlatformConfig();
platformConfig.setBIRTHome(BIRT_HOME);
try {
Platform.startup(platformConfig);
}
catch (BirtException e) {
e.printStackTrace();
//We cannot start the platform, exit
System.exit(-1);
}
}
/**
* createReportEngine
*
* This will create a report engine to use
* @return
*/
private IReportEngine createReportEngine()
{
//Create a new report engine factory
IReportEngineFactory factory = (IReportEngineFactory) Platform.
createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
//create a new report engine
EngineConfig engineConfig = new EngineConfig();
// engineConfig.setBIRTHome("E:/BIRT/birt-runtime-2_3_2_2/ReportEngine");
 engineConfig.setBIRTHome("E:/BIRT/birt-runtime-2_3_2_2");
//will replace with configuration file
return factory.createReportEngine(engineConfig);
}
/**
* Executes a report with no parameters, only requires report name to execute
* @param reportName
* @return
*/
public void executeReportNoParams(String reportName, String outputFile, IReportEngine engine)
{
try {
//Create the report runnable and runandrender task
IReportRunnable runnable = engine.openReportDesign(reportName);
IRunAndRenderTask task = engine.createRunAndRenderTask(runnable);
//Set Render context to handle url and image locations
HTMLRenderContext renderContext = new HTMLRenderContext();
renderContext.setImageDirectory(IMAGE_PATH);
HashMap contextMap = new HashMap();
contextMap.put( EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext );
task.setAppContext( contextMap );
//Set rendering options - such as file or stream output,
//Output format, whether it is embeddable, etc
HTMLRenderOption options = new HTMLRenderOption();
options.setOutputFileName(outputFile);
options.setOutputFormat("html");
task.setRenderOption(options);
//Run the report and close
task.run();
task.close();
}
catch (EngineException e) {
e.printStackTrace();
System.exit(-1);
}
}
/**
* executeReport
*
* This method will execute the report and save the the output file
* @param reportInput
* @param reportOutput
*/
public void executeReport(String reportInput, String reportOutput)
{
//Start up the platform
startupPlatform();
//Create a Report Engine
IReportEngine engine = createReportEngine();
//Create a run and render task, and execute report
executeReportNoParams(reportInput, reportOutput, engine);
//Shutdown platform
Platform.shutdown();
}
/**
* @param args
*/
public static void main(String[] args) {
ReportExecutor re = new ReportExecutor();
//Get command line options
// CommandLine cl = re.parseCommandLineOptions(args);
//Get the input file and output file
// String reportInputFile = cl.getOptionValue("i");
String reportInputFile = new String("E:/BIRT/birt-runtime-2_3_2_2/ReportEngine/samples/report005.rptdesign");
// String reportOutputFile = cl.getOptionValue("o");
String reportOutputFile = new String("E:/BIRT/report005.html");
//Execute the report
re.executeReport(reportInputFile, reportOutputFile);
}
}