package com.xceptance.testbddy.core.reporting;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.xceptance.testbddy.core.bdd.status.Status;

/**
 * Depicts an element, that is created by the {@link ReportInterface}. This way,
 * each BDD component can be logged depending on whether it failed or was
 * skipped among others.
 *
 * @author ckeiner
 *
 */
public interface ReportElement
{
    /**
     * Assigns the status as categories to the element.
     * 
     * @param stati
     *            The list of {@link Status} to add as categories
     */
    public default void assignCategory(Set<Status> stati)
    {
        // Build a list of strings
        List<String> stringStati = new ArrayList<>(stati.size());
        // Write each status in the list stati as string
        stati.stream().map(s -> s.toString()).forEach(s -> stringStati.add(s));
        // Make an array of the size of the categories
        String[] categoryArray = new String[stringStati.size()];
        // Save the elements of the list in the array categoryArray
        stringStati.toArray(categoryArray);
        // Call the assignCategory-method taking arrays as parameter
        assignCategory(categoryArray);
    }

    /**
     * Assigns categories to the element.
     * 
     * @param categories
     *            A String describing the category. Each element describes one such
     *            category.
     */
    public void assignCategory(String... categories);

    /**
     * Reports the element as failed with the additional message.
     *
     * @param description
     *            The additional message.
     */
    public void fail(String description);

    /**
     * Reports the element as failed due to the specified cause.
     *
     * @param throwable
     *            The cause of the failure.
     */
    public void fail(Throwable throwable);

    /**
     * Reports the element as fatal with the additional message.
     *
     * @param description
     *            The additional message.
     */
    public void fatal(String description);

    /**
     * Reports the element as fatal due to the specified cause.
     *
     * @param throwable
     *            The cause of the fatal error.
     */
    public void fatal(Throwable throwable);

    /**
     * Reports the element as passed with the additional message.
     *
     * @param description
     *            The additional message.
     */
    public void pass(String description);

    /**
     * Reports the element as pending with the additional message.
     *
     * @param description
     *            The additional message.
     */
    public void pending(String description);

    /**
     * Reports the element as skipped with the additional message.
     *
     * @param description
     *            The additional message.
     */
    public void skip(String description);

    /**
     * Reports the element as skipped due to the specified cause.
     *
     * @param throwable
     *            The cause of being skipped.
     */
    public void skip(Throwable throwable);

}
