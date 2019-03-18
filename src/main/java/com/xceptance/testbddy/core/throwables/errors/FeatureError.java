package com.xceptance.testbddy.core.throwables.errors;

import com.xceptance.testbddy.core.bdd.Feature;

/**
 * Describes an error that happens in a {@link Feature}.
 * 
 * @author ckeiner
 */
public class FeatureError extends AssertionError
{
    private static final long serialVersionUID = 453433579352686803L;

    /**
     * @see AssertionError#AssertionError()
     */
    public FeatureError()
    {
        super();
    }

    /**
     * @see AssertionError#AssertionError(boolean)
     */
    public FeatureError(boolean detailMessage)
    {
        super(detailMessage);
    }

    /**
     * @see AssertionError#AssertionError(char)
     */
    public FeatureError(char detailMessage)
    {
        super(detailMessage);
    }

    /**
     * @see AssertionError#AssertionError(double)
     */
    public FeatureError(double detailMessage)
    {
        super(detailMessage);
    }

    /**
     * @see AssertionError#AssertionError(float)
     */
    public FeatureError(float detailMessage)
    {
        super(detailMessage);
    }

    /**
     * @see AssertionError#AssertionError(int)
     */
    public FeatureError(int detailMessage)
    {
        super(detailMessage);
    }

    /**
     * @see AssertionError#AssertionError(long)
     */
    public FeatureError(long detailMessage)
    {
        super(detailMessage);
    }

    /**
     * @see AssertionError#AssertionError(Object)
     */
    public FeatureError(Object detailMessage)
    {
        super(detailMessage);
    }

    /**
     * @see AssertionError#AssertionError(String, Throwable)
     */
    public FeatureError(String message, Throwable cause)
    {
        super(message, cause);
    }

}
