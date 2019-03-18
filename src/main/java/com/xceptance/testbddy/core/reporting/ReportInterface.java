package com.xceptance.testbddy.core.reporting;

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
     * Creates a {@link ReportElement} for a feature with the specified description.
     * 
     * @param description
     *            The description of the feature.
     * @return A ReportElement depicting the feature.
     */
    public ReportElement feature(String description);

    /**
     * Creates a {@link ReportElement} for a scenario with the specified
     * description.
     * 
     * @param description
     *            The description of the scenario.
     * @return A ReportElement depicting the scenario.
     */
    public ReportElement scenario(String description);

    /**
     * Creates a {@link ReportElement} for a scenario outline with the specified
     * description and test datum.
     * 
     * @param description
     *            The description of the scenario outline.
     * @param testdata
     *            The test datum for the scenario outline.
     * @param <T>
     *            The type of the test datum.
     * @return A ReportElement depicting the scenario outline.
     */
    public <T> ReportElement scenarioOutline(String description, T testdata);

    /**
     * Creates a {@link ReportElement} for a scenario outline with the specified
     * description.
     * 
     * @param description
     *            The description of the scenario outline.
     * @param <T>
     *            The type of the test datum.
     * @return A ReportElement depicting the scenario outline.
     */
    public <T> ReportElement scenarioOutline(String description);

    /**
     * Creates a {@link ReportElement} for a step with the specified description.
     * 
     * @param description
     *            The description of the step.
     * @param keyword
     *            The {@link GherkinKeyword} for either Given, When, Then or And.
     * @return A ReportElement depicting the step.
     */
    public ReportElement step(GherkinKeyword keyword, String description);

    /**
     * Completes the report.
     */
    public void finishReport();

}
