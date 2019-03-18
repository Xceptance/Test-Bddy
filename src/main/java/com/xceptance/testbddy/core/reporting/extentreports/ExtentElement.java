package com.xceptance.testbddy.core.reporting.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.xceptance.testbddy.core.reporting.ReportElement;

/**
 * Depicts a test element for {@link ExtentReports}, which is an
 * {@link ExtentTest}.
 *
 * @author ckeiner
 *
 */
public class ExtentElement implements ReportElement
{
    /**
     * The element created by the {@link ExtentReportInterface}.
     */
    public ExtentTest extentElement;

    /**
     * Creates a new instance of this class with the given element.
     * 
     * @param extentElement
     *            The {@link ExtentTest}.
     */
    public ExtentElement(ExtentTest extentElement)
    {
        this.extentElement = extentElement;
    }

    @Override
    public void assignCategory(String... name)
    {
        getElement().assignCategory(name);
    }

    @Override
    public void fail(String description)
    {
        extentElement.fail(description);
    }

    @Override
    public void fail(Throwable throwable)
    {
        extentElement.fail(throwable);
    }

    @Override
    public void fatal(String description)
    {
        extentElement.fatal(description);
    }

    @Override
    public void fatal(Throwable throwable)
    {
        extentElement.fatal(throwable);
    }

    public ExtentTest getElement()
    {
        return this.extentElement;
    }

    @Override
    public void pass(String description)
    {
        extentElement.pass(description);
    }

    @Override
    public void pending(String description)
    {
        this.skip(description);
    }

    @Override
    public void skip(String description)
    {
        extentElement.skip(description);
    }

    @Override
    public void skip(Throwable throwable)
    {
        extentElement.skip(throwable);
    }

}
