package com.xceptance.testbddy.core.throwables.errors;

import com.xceptance.testbddy.core.bdd.scenario.Scenario;

/**
 * Describes an error that happens in a {@link Scenario}.
 * 
 * @author ckeiner
 */
public class ScenarioError extends Error
{
    private static final long serialVersionUID = 453433579352686803L;

    /**
     * @see Error#Error()
     */
    public ScenarioError()
    {
        super();
    }

    /**
     * @see Error#Error(String, Throwable, boolean, boolean)
     */
    public ScenarioError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @see Error#Error(String, Throwable)
     */
    public ScenarioError(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * @see Error#Error(String)
     */
    public ScenarioError(String message)
    {
        super(message);
    }

    /**
     * @see Error#Error(Throwable)
     */
    public ScenarioError(Throwable cause)
    {
        super(cause);
    }

}
