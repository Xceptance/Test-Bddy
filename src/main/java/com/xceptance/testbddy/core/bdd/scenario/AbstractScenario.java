package com.xceptance.testbddy.core.bdd.scenario;

import java.util.LinkedHashSet;
import java.util.Set;

import com.xceptance.testbddy.core.bdd.status.Status;
import com.xceptance.testbddy.core.bdd.status.Statusable;
import com.xceptance.testbddy.core.reporting.ReportInterface;

/**
 * The abstract class for all scenarios.
 * 
 * @author ckeiner
 *
 */
public abstract class AbstractScenario implements Statusable
{
    /**
     * The reporter responsible for reporting
     */
    private ReportInterface reporter;

    /**
     * The description of the scenario.
     */
    final private String description;

    /**
     * The Status of the scenario.
     */
    private final Set<Status> status;

    public AbstractScenario(String description)
    {
        this.description = description;
        this.status = new LinkedHashSet<Status>();
    }

    /**
     * Executes the scenario.
     */
    public abstract void test();

    /**
     * Skips the scenario and reports it as such.
     */
    public abstract void skipScenario();

    public String getDescription()
    {
        return description;
    }

    public ReportInterface getReporter()
    {
        return reporter;
    }

    public void setReporter(ReportInterface reporter)
    {
        this.reporter = reporter;
    }

    public Set<Status> getStatus()
    {
        return this.status;
    }

    @Override
    public AbstractScenario ignore()
    {
        getStatus().add(Status.IGNORE);
        return this;
    }

    @Override
    public AbstractScenario wip()
    {
        getStatus().add(Status.WIP);
        return this;
    }

    @Override
    public AbstractScenario skip()
    {
        getStatus().add(Status.SKIP);
        return this;
    }
}
