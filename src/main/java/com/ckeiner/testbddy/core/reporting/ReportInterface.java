package com.ckeiner.testbddy.core.reporting;

import com.aventstack.extentreports.GherkinKeyword;

/**
 * The interface for reporting frameworks.
 * 
 * @author ckeiner
 *
 */
public interface ReportInterface
{
    /**
     * The path where the report should be created
     */
    public final static String PATH = "report/";

    /**
     * Reports a feature with the specified description.
     * 
     * @param description
     *            The description of the feature.
     * @return A {@link ReportElement} depicting the feature.
     */
    public ReportElement feature(String description);

    /**
     * Reports a scenario with the specified description.
     * 
     * @param description
     *            The description of the scenario.
     * @return A {@link ReportElement} depicting the scenario.
     */
    public ReportElement scenario(String description);

    /**
     * Reports a scenario outline with the specified description and testdata.
     * 
     * @param description
     *            The description of the scenario outline.
     * @param testdata
     *            The test data for the scenario outline.
     * @return A {@link ReportElement} depicting the scenario outline.
     */
    public <T> ReportElement scenarioOutline(String description, T testdata);

    /**
     * Reports a scenario outline with the specified description.
     * 
     * @param description
     *            The description of the scenario outline.
     * @return A {@link ReportElement} depicting the scenario outline.
     */
    public <T> ReportElement scenarioOutline(String description);

    /**
     * Reports a step with the specified description.
     * 
     * @param description
     *            The description of the step.
     * @param keyword
     *            The {@link GherkinKeyword} for either Given, When, Then or And.
     * @return A {@link ReportElement} depicting the step.
     */
    public ReportElement step(GherkinKeyword keyword, String description);

    /**
     * Completes the report.
     */
    public void finishReport();

}