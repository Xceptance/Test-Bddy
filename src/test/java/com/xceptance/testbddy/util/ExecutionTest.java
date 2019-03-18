package com.xceptance.testbddy.util;

import org.junit.Before;

public abstract class ExecutionTest
{
    protected int execution;

    @Before
    public void init()
    {
        execution = 0;
    }

}
