package com.xceptance.testbddy.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * Resolves any placeholder by using beanshell.
 * 
 * @author ckeiner
 *
 * @param <T>
 *            The type of the object of which the placeholder is a field or
 *            method.
 */
public class PlaceholderResolver<T>
{
    /**
     * The beanshell interpreter
     */
    public final static Interpreter interpreter = new Interpreter();

    /**
     * The String with which placeholders start
     */
    public final static String DEFINITION_START = "data";

    /**
     * The String with which placeholders start
     */
    public final static String ENCLOSE_START = "<";

    /**
     * The String with which placeholders end
     */
    public final static String ENCLOSE_END = ">";

    /**
     * Resolves any placeholder thats enclosed by {@link #ENCLOSE_START} and
     * {@link #ENCLOSE_END}.
     * 
     * @param description
     *            The String which should be checked for placeholders.
     * @param testdata
     *            The instance of which the placeholders are a field or method of.
     * @return A String where all placeholders are resolved if a field or method
     *         like that is found. <br>
     *         If none is found, the placeholder is inserted again.
     */
    public String resolvePlaceholders(final String description, final T testdata)
    {
        setInterpreterToData(testdata);
        // Set the current description as output
        String output = description;
        // Search for any matches in the description
        Matcher matcher = Pattern.compile(ENCLOSE_START + ".*?" + ENCLOSE_END).matcher(description);
        // For each match
        while (matcher.find())
        {
            // Remove the enclosing characters
            String match = matcher.group().replaceAll(ENCLOSE_START, "").replaceAll(ENCLOSE_END, "");
            // Resolve the found placeholder
            String resolvedValue = resolvePlaceholder(match);
            // Replace the old value with the resolved one
            output = output.replace(ENCLOSE_START + match + ENCLOSE_END, resolvedValue);
        }
        // Return the resolved value
        return output;
    }

    /**
     * Resolves the placeholder by asking the testdata object for it.
     * 
     * @param placeholder
     *            The placeholder without the enclosing characters.
     * @return A String where the placeholders are resolved if a field or method
     *         like that is found. <br>
     *         If none is found, the placeholder is returned with its enclosing
     *         characters again.
     */
    private String resolvePlaceholder(final String placeholder)
    {
        // Set the resolvedValue to the current placeholder
        String resolvedValue = placeholder;
        try
        {
            // Resolve the placeholder
            Object value = interpreter.eval(resolvedValue);
            // If we found something
            if (value != null)
            {
                // Set the output to the string value of it
                resolvedValue = value.toString();
            }
        } catch (EvalError e)
        {
            // Since this is an optional component, simply print the Stack Trace in case of
            // an error
            e.printStackTrace();
        }
        // If we didn't find anything or it was resolved to nothing
        if (resolvedValue == null || resolvedValue.isEmpty())
        {
            // Place the enclosing characters around the placeholder
            resolvedValue = ENCLOSE_START + placeholder + ENCLOSE_END;
        }
        // Return the resolved value
        return resolvedValue;
    }

    /**
     * Registers the test datum at the interpreter
     * 
     * @param testdatum
     *            The test datum that should be registered at the interpreter
     */
    private void setInterpreterToData(T testdatum)
    {
        try
        {
            interpreter.set(DEFINITION_START, testdatum);
        } catch (EvalError e)
        {
            e.printStackTrace();
        }
    }

}
