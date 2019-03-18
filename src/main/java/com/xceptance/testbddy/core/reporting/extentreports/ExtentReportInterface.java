package com.xceptance.testbddy.core.reporting.extentreports;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.xceptance.testbddy.core.reporting.ReportElement;
import com.xceptance.testbddy.core.reporting.ReportInterface;

/**
 * The class for reporting with {@link ExtentReports}.
 * 
 * @author ckeiner
 *
 */
public class ExtentReportInterface implements ReportInterface
{
    /**
     * The report interface to ExtentReports.
     */
    public ExtentReports extentReports;

    /**
     * The {@link ExtentElement} for a feature.
     */
    public ExtentElement feature;

    /**
     * The {@link ExtentElement} for a scenario.
     */
    public ExtentElement scenario;

    /**
     * The {@link ExtentElement} for a step.
     */
    public ExtentElement step;

    /**
     * Creates a new {@link ExtentReportInterface} and only sets new values if they
     * aren't set yet.
     */
    private ExtentReportInterface()
    {
        File file = new File("./" + ReportInterface.PATH);
        // Create directory if it doesn't exist yet
        if (!file.isDirectory())
        {
            file.mkdirs();
        }
        // Initialize the HtmlReporter
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("./" + ReportInterface.PATH + "extent.html");
        // Initialize ExtentReports
        extentReports = new ExtentReports();
        // Attach the HtmlReporter
        extentReports.attachReporter(htmlReporter);
    }

    /**
     * Optimizes concurrent access to the instance (Initialization-on-demand holder
     * idiom)
     * 
     * @author ckeiner
     */
    private static class LazyHolder
    {
        static final ExtentReportInterface INSTANCE = new ExtentReportInterface();
    }

    /**
     * Creates and gets the instance from the LazyHolder
     * 
     * @return The Singleton Instance of the ExtentReportInterface.
     */
    public static ExtentReportInterface getInstance()
    {
        return LazyHolder.INSTANCE;
    }

    @Override
    public ReportElement feature(String description)
    {
        try
        {
            feature = new ExtentElement(extentReports.createTest(new GherkinKeyword("Feature"), description));
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return feature;
    }

    @Override
    public ReportElement scenario(String description)
    {
        try
        {
            ExtentTest scenarioNode = feature.getElement().createNode(new GherkinKeyword("Scenario"), description);
            scenario = new ExtentElement(scenarioNode);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return scenario;
    }

    @Override
    public <T> ReportElement scenarioOutline(String description, T testdata)
    {
        try
        {
            ExtentTest scenarioNode;
            if (testdata != null)
            {
                scenarioNode = feature.getElement().createNode(new GherkinKeyword("Scenario"),
                        description + " with Data: " + testdata.toString());
            }
            else
            {
                scenarioNode = feature.getElement().createNode(new GherkinKeyword("Scenario"),
                        description + " with no Data supplied");
            }
            scenario = new ExtentElement(scenarioNode);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return scenario;
    }

    @Override
    public ReportElement step(GherkinKeyword keyword, String description)
    {
        ExtentTest stepNode = scenario.getElement().createNode(keyword, description);
        step = new ExtentElement(stepNode);
        return step;
    }

    @Override
    public void finishReport()
    {
        extentReports.flush();
    }

    @Override
    public <T> ReportElement scenarioOutline(String description)
    {
        try
        {
            ExtentTest scenarioNode = feature.getElement().createNode(new GherkinKeyword("Scenario"), description);
            scenario = new ExtentElement(scenarioNode);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return scenario;
    }

}
