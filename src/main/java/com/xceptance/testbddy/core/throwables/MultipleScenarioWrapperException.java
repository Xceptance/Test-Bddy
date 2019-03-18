package com.xceptance.testbddy.core.throwables;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.internal.runners.model.MultipleFailureException;

import com.xceptance.testbddy.core.throwables.errors.ScenarioError;
import com.xceptance.testbddy.core.throwables.exceptions.ScenarioException;

/**
 * Wraps multiple {@link ScenarioException}s.
 * 
 * @author ckeiner
 */
public class MultipleScenarioWrapperException extends MultipleFailureException
{
    private static final long serialVersionUID = 58234950048201778L;

    /**
     * Combines a list of errors and exceptions, then calls the super constructor
     * {@link MultipleFailureException#MultipleFailureException(List)}.
     * 
     * @param exceptions
     *            A list of {@link ScenarioException}s.
     * @param errors
     *            A list of {@link ScenarioError}s.
     */
    public MultipleScenarioWrapperException(List<ScenarioException> exceptions, List<ScenarioError> errors)
    {
        super(init(exceptions, errors));
    }

    /**
     * First, it adds all exceptions to a list of Throwables, then it adds all
     * errors to the list.
     * 
     * @param exceptions
     *            A list of {@link ScenarioException}s.
     * @param errors
     *            A list of {@link ScenarioError}s.
     * @return A list of Throwables.
     */
    private static List<Throwable> init(List<ScenarioException> exceptions, List<ScenarioError> errors)
    {
        // Create a list of Throwables with a total capacity of the combined size of the
        // list of exceptions and errors
        List<Throwable> throwables = new ArrayList<>(exceptions.size() + errors.size());
        // Add all exceptions
        throwables.addAll(exceptions);
        // Add all errors
        throwables.addAll(errors);
        return throwables;
    }

    /**
     * Prints the stack trace of every Throwable to the specified
     * {@link PrintStream}.
     * 
     * @param out
     *            The PrintStream to print the stack trace to
     */
    @Override
    public void printStackTrace(PrintStream out)
    {
        // Initialize variable that counts every throwable
        int counterOfThrowables = 0;
        // For each throwable
        for (Throwable throwable : getFailures())
        {
            // Print the number of the error and the message
            out.println("Error " + counterOfThrowables + ": " + throwable.getMessage());
            // Print the stack trace of the throwable
            throwable.printStackTrace(out);
            // Insert an empty line
            out.println();
            // Increment the counter
            counterOfThrowables++;
        }
    }

    /**
     * Returns a String with the stack trace of every Throwable.<br>
     * Needed by JUnit and ExtentReports to display the errors and exceptions
     * properly.
     */
    @Override
    public String getMessage()
    {
        // Start a StrintBuilder with a string containing the amount of errors
        StringBuilder messageBuilder = new StringBuilder(String.format("There were %d errors:", getFailures().size()));
        // Iterate over all throwables
        for (Throwable e : getFailures())
        {
            // Print the minimal information
            messageBuilder.append(String.format("\n  %s(%s)", e.getClass().getName(), e.getMessage()));
            // Append the stack trace of the throwable to the string builder
            messageBuilder.append("\n  " + stackTrace(e));
        }
        // Return the String in String Builder
        return messageBuilder.toString();
    }

    /**
     * Returns the stack trace of the Throwable as String.
     * 
     * @param throwable
     *            The Throwable you want the stack trace to
     * @return A String containing the stack trace
     */
    private String stackTrace(Throwable throwable)
    {
        // Define a charset
        final Charset charset = StandardCharsets.UTF_8;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = null;
        // Try to create the Print Stream
        try
        {
            printStream = new PrintStream(byteArrayOutputStream, true, charset.name());
        } catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        // Write stack trace to the print stream
        printStackForOneError(throwable, printStream);
        // Extract the stack trace from the print stream/ByteArrayOutputStream
        String content = new String(byteArrayOutputStream.toByteArray(), charset);
        // Close the print stream
        printStream.close();
        // Close the ByteArrayOutputStream
        try
        {
            byteArrayOutputStream.close();
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }
        // Return the content
        return content;
    }

    /**
     * Prints the stack trace of a single Throwable to the specified
     * {@link PrintStream}.
     * 
     * @param error
     *            The Throwable with the stack trace to print
     * @param out
     *            The writer to print the stack trace to
     */
    private void printStackForOneError(Throwable error, PrintStream out)
    {
        // Get the index of the error
        int i = getFailures().indexOf(error) + 1;
        // Print the error number and the message
        out.println("Error " + i + ": " + error.getMessage());
        // Print the stack trace of the error to the PrintStream
        error.printStackTrace(out);
        // Print an empty line
        out.println();
    }

}
