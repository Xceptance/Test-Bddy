package com.xceptance.testbddy.core.throwables.exceptions;

import com.xceptance.testbddy.core.bdd.scenario.Scenario;

/**
 * Describes an exception that happens in a {@link Scenario}.
 * 
 * @author ckeiner
 */
public class ScenarioException extends RuntimeException
{
    private static final long serialVersionUID = 453433579352686803L;

    /**
     * @see RuntimeException#RuntimeException()
     */
    public ScenarioException()
    {
        super();
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable, boolean, boolean)
     */
    public ScenarioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public ScenarioException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public ScenarioException(String message)
    {
        super(message);
    }

    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public ScenarioException(Throwable cause)
    {
        super(cause);
    }

}
