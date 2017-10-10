package ecse429.group10;

import com.rits.cloning.Cloner;
import org.apache.commons.collections4.list.*;
import org.apache.commons.collections4.queue.PredicatedQueue;
import org.apache.commons.collections4.queue.TransformedQueue;
import org.jukito.*;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;

/**
 * Test for concrete implementations of the List interface inside of the Apache commons collection
 */

@RunWith(JukitoRunner.class)
public class ListImplementationsTest {


    Cloner cloner = new Cloner();


    public static class Module extends JukitoModule {

        @Override
        @Before
        protected void configureTest() {

            // generic binds
            bindMany(List.class, TreeList.class, CursorableLinkedList.class, NodeCachingLinkedList.class);

            // initialize empty instances of the specific list implementations that we want
            bindManyNamedInstances(List.class, "empty", new TreeList<>(),
                    new CursorableLinkedList<>(),
                    new NodeCachingLinkedList<>());

            // initialize non-empty instances of the specific list implementations that we want
            bindManyNamedInstances(List.class, "filled", new TreeList<>(Arrays.asList("xyz", "abc", "cbd")),
                    new CursorableLinkedList<>(Arrays.asList("xyz", "abc", "cbd")),
                    new NodeCachingLinkedList<>(Arrays.asList("xyz", "abc", "cbd")));

            bindManyNamedInstances(List.class, "duplicate", new TreeList<>(Arrays.asList("xyz", "abc", "cbd", "abc")),
                    new CursorableLinkedList<>(Arrays.asList("xyz", "abc", "cbd", "abc")),
                    new NodeCachingLinkedList<>(Arrays.asList("xyz", "abc", "cbd", "abc")));


        }
    }


    /**
     * Test 1) Test adding to a list
     * This also includes doing the test for both, filled and empty instances, since all bound instances to the List.class including
     * the generic empty one, "filled", etc. pass through this method due to @All
     * @param list
     */
    @Test
    public void shouldAddToList(@All List list) {
        int previousSize = list.size();
        List copyList = cloner.deepClone(list);
        copyList.add("test");
        assertEquals(previousSize + 1, copyList.size());
    }

    /**
     * Removing from the first index
     * @param list
     */
    @Test
    public void shouldRemoveFromFirstIndex(@All("filled") List list) {
        List copyList = cloner.deepClone(list);
        String removedElement = (String) copyList.remove(0);
        assertEquals("xyz", removedElement);
    }

    /**
     * Removing from an index which is not populated in the list
     * @param list
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void failRemoveFromNonExistingIndex(@All List list) {
        list.remove(15);
    }

    /**
     * Removing a specified object (in this case, a String) from list
     * @param list
     */
    @Test
    public void shouldRemoveSpecifiedObject(@All("filled") List list){
        List copyList = cloner.deepClone(list);
        assertEquals(true, copyList.remove("cbd"));
    }

    /**
     * Attempt to remove a non-existing object from inside of an array
     * @param list
     */
    @Test
    public void failRemoveNonExistentObject(@All List list) {
        assertEquals(false, list.remove("testWrong"));
    }

    /**
     * Test if removes all elements from within the list
     * Includes test even with empty instances of the given list objects
     * @param list
     */
    @Test
    public void shouldRemoveAll(@All List list){
        List copyList = cloner.deepClone(list);
        copyList.clear();
        assertEquals(0, copyList.size());
    }

    /**
     * Test removal of a set of values from the array
     * @param list
     */
    @Test
    public void shouldRemoveMultipleElements(@All("filled") List list){
        List expectedList = cloner.deepClone(list);

        // since we already tested that remove works for individual items, we can use it here
        expectedList.remove(2);
        expectedList.remove(1);

        List copyList = cloner.deepClone(list);
        copyList.removeAll(Arrays.asList("abc", "cbd"));

        assertEquals(expectedList, copyList);
    }

    /**
     * Test removal of a set of values from the array
     * @param list
     */
    @TestEagerSingleton
    public void shouldRemoveMultipleDuplicateElements(@All("duplicate") List list){

        List expectedList = cloner.deepClone(list);
        // since we already tested that remove works for individual items, we can use it here
        expectedList.remove(1);
        expectedList.remove(1);
        expectedList.remove(1);

        List copyList = cloner.deepClone(list);
        copyList.removeAll(Arrays.asList("abc", "cbd"));


        assertEquals(expectedList, copyList);
    }


}
