package com.xceptance.testbddy.core.throwables.errors;

import com.xceptance.testbddy.core.bdd.steps.AbstractStep;

/**
 * Describes an Error that happens in an {@link AbstractStep}.
 * 
 * @author ckeiner
 */
public class StepError extends Error
{
    private static final long serialVersionUID = 453433579352686803L;

    /**
     * @see Error#Error()
     */
    public StepError()
    {
        super();
    }

    /**
     * @see Error#Error(String, Throwable, boolean, boolean)
     */
    public StepError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @see Error#Error(String, Throwable)
     */
    public StepError(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * @see Error#Error(String)
     */
    public StepError(String message)
    {
        super(message);
    }

    /**
     * @see Error#Error(Throwable)
     */
    public StepError(Throwable cause)
    {
        super(cause);
    }

}
