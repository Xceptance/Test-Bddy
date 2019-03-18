package com.xceptance.testbddy.core.bdd.status;

public interface Statusable
{
    /**
     * Ignore this component.<br>
     * This means, it doesn't appear in the report.
     * 
     * @return The current class
     */
    public Statusable ignore();

    /**
     * This component is a work in progess.
     * 
     * @return The current class
     */
    public Statusable wip();

    /**
     * Skip this component.<br>
     * This means, it appears in the report, but is not executed.
     * 
     * @return The current class
     */
    public Statusable skip();

}
