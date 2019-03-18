package com.xceptance.testbddy.core.throwables.exceptions;

import com.xceptance.testbddy.core.bdd.Feature;

/**
 * Describes an exception that happens in a {@link Feature}.
 * 
 * @author ckeiner
 */
public class FeatureException extends RuntimeException
{
    private static final long serialVersionUID = 453433579352686803L;

    /**
     * @see RuntimeException#RuntimeException()
     */
    public FeatureException()
    {
        super();
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable, boolean, boolean)
     */
    public FeatureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public FeatureException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public FeatureException(String message)
    {
        super(message);
    }

    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public FeatureException(Throwable cause)
    {
        super(cause);
    }

}
