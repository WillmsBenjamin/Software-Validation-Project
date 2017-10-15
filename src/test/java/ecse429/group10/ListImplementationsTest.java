package ecse429.group10;

import com.rits.cloning.Cloner;
import org.apache.commons.collections4.list.CursorableLinkedList;
import org.apache.commons.collections4.list.NodeCachingLinkedList;
import org.apache.commons.collections4.list.TreeList;
import org.jukito.All;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Comparator.*;


import java.util.*;

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
     *
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
     * Test adding multiple items to list
     */
    @Test
    public void shouldAddMultipleToList(@All("filled") List list) {
        int previousSize = list.size();
        List copyList = cloner.deepClone(list);

        copyList.addAll(Arrays.asList("test", "test2"));
        assertEquals(previousSize + 2, copyList.size());
    }

    /**
     * Test adding multiple items at given index
     */
    @Test
    public void shouldAddToListAtGivenIndex(@All("filled") List list) {
        List copyList = cloner.deepClone(list);

        copyList.addAll(1, Arrays.asList("test", "test2"));
        assertEquals("test", copyList.get(1));
        assertEquals("test2", copyList.get(2));

    }

    /**
     * Test adding multiple items at given index that is out of bonds
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void failAddToListAtGivenIndex(@All("filled") List list) {
        list.addAll(90, Arrays.asList("test", "test2"));
    }

    /**
     * Removing from the first index
     *
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
     *
     * @param list
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void failRemoveFromNonExistingIndex(@All List list) {
        list.remove(15);
    }

    /**
     * Removing a specified object (in this case, a String) from list
     *
     * @param list
     */
    @Test
    public void shouldRemoveSpecifiedObject(@All("filled") List list) {
        List copyList = cloner.deepClone(list);
        assertEquals(true, copyList.remove("cbd"));
    }

    /**
     * Attempt to remove a non-existing object from inside of an array
     *
     * @param list
     */
    @Test
    public void failRemoveNonExistentObject(@All List list) {
        assertEquals(false, list.remove("testWrong"));
    }

    /**
     * Test if removes all elements from within the list
     * Includes test even with empty instances of the given list objects
     *
     * @param list
     */
    @Test
    public void shouldRemoveAll(@All List list) {
        List copyList = cloner.deepClone(list);
        copyList.clear();
        assertEquals(0, copyList.size());
    }

    /**
     * Test removal of a set of values from the array
     *
     * @param list
     */
    @Test
    public void shouldRemoveMultipleElements(@All("filled") List list) {
        List copyList = cloner.deepClone(list);
        copyList.removeAll(Arrays.asList("abc", "cbd"));

        assertEquals(false, copyList.containsAll(Arrays.asList("abc", "cbd")));
    }

    /**
     * Test removal of a set of values from the array
     *
     * @param list
     */
    @Test
    public void shouldRemoveMultipleDuplicateElements(@All("duplicate") List list) {
        List copyList = cloner.deepClone(list);
        copyList.removeAll(Arrays.asList("abc", "cbd"));

        assertEquals(false, copyList.containsAll(Arrays.asList("abc", "cbd")));
    }

    /**
     * Test removal from a list with null as a parameter
     *
     * @param list
     */
    @Test(expected = NullPointerException.class)
    public void failNullRemoval(@All("filled") List list) {
        list.removeAll(null);
    }

    /**
     * Test update of first item in a list
     */
    @Test
    public void shouldUpdateFirstItem(@All("filled") List list) {
        List copyList = cloner.deepClone(list);

        copyList.set(0, "test");

        assertEquals("test", copyList.get(0));
    }

    /**
     * Test update of an item that doesn't exist in the list
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void failUpdateNonExistingIndex(@All("filled") List list) {
        list.set(8, "test");
    }

    /**
     * Test retrieval of item at first element of list
     */
    @Test
    public void shouldRetrieveValue(@All("filled") List list) {
        assertEquals("xyz", list.get(0));
    }

    /**
     * Test retrieval of item at an index which is out of bounds
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void failRetrieveValue(@All("filled") List list) {
        list.get(10);
    }

    /**
     * Test emptiness for empty lists
     */
    @Test
    public void shouldBeEmpty(@All("empty") List list) {
        assertEquals(true, list.isEmpty());
    }

    /**
     * Test emptiness for non-empty lists
     */
    @Test
    public void shouldNotBeEmpty(@All("filled") List list) {
        assertEquals(false, list.isEmpty());
    }

    /**
     * Test if list contains "xyz"
     */
    @Test
    public void shouldContainItem(@All("filled") List list) {
        assertEquals(true, list.contains("xyz"));
    }

    /**
     * Test if list contains "xyz" & "abc"
     */
    @Test
    public void shouldContainAllItems(@All("filled") List list) {
        assertEquals(true, list.containsAll(Arrays.asList("xyz", "abc")));
    }

    /**
     * Test if list doesn't contain "xyz"
     */
    @Test
    public void shouldNotContainItem(@All("empty") List list) {
        assertEquals(false, list.contains("xyz"));
    }

    /**
     * Test if list doesn't contain "xyz" and "abc"
     */
    @Test
    public void shouldNotContainAllItems(@All("filled") List list) {
        assertEquals(false, list.containsAll(Arrays.asList("xyz", "test2")));
    }

    /**
     * Test if method returns index of element at expected index
     */
    @Test
    public void shouldReturnIndexOfElement(@All("filled") List list) {
        assertEquals(0, list.indexOf("xyz"));
    }

    /**
     * Test if method returns -1 when no element at expected index
     */
    @Test
    public void shouldNotReturnIndexOfElement(@All("empty") List list) {
        assertEquals(-1, list.indexOf("xyz"));
    }


    /**
     * Test if method only keeps the expected elements
     */
    @Test
    public void shouldRetainGivenElements(@All("duplicate") List list) {
        List expectedList = cloner.deepClone(list);
        expectedList.removeAll(Arrays.asList("xyz", "cbd"));

        List copyList = cloner.deepClone(list);
        copyList.retainAll(Arrays.asList("abc"));

        assertEquals(expectedList, copyList);
    }

    /**
     * Test if doesn't retain any of the elements in a given array
     */
    @Test
    public void shouldNotRetainGivenElements(@All("filled") List list) {
        List copyList = cloner.deepClone(list);
        copyList.retainAll(Arrays.asList("willNotRetain"));

        assertEquals(Collections.emptyList(), copyList);
    }


    /**
     * Test if elements are in alphabetical order
     */
    @Test
    public void shouldBeOrderedAlphabetically(@All("duplicate") List list) {

        String[] expectedArray = new String[]{"abc", "abc", "cbd", "xyz"};
        List<String> copyList = cloner.deepClone(list);
        copyList.sort(String::compareToIgnoreCase);

        Assert.assertArrayEquals(expectedArray, copyList.toArray());
    }

    /**
     * Test CursorableLinkedList's allows changes to the list concurrent with changes to the iterator
     */
    @Test
    public void shouldSupportModificationOfCursorAndIterator() {
        CursorableLinkedList list = new CursorableLinkedList<>(Arrays.asList("xyz", "abc", "cbd"));

        CursorableLinkedList.Cursor c1 = list.cursor();
        Iterator li = list.iterator();

        assertEquals("xyz", li.next());
        li.remove();
        assertEquals("abc", li.next());
        // test cursor skips element removed by the iterator
        assertEquals("abc", c1.next());

        // test iterator takes into consideration newly added element by cursor
        c1.add("test");
        assertEquals("test", c1.previous());
        assertEquals("test", c1.next());
    }

    /**
     * Test that TreeList indeed brings an improvement over existing Java collections
     */
    @Test
    public void shouldInsertAndRemoveFasterThanArrayListAndLinkedList() {
        TreeList treeList = new TreeList();
        ArrayList arrayList = new ArrayList();
        LinkedList linkedList = new LinkedList();

        int CONST_SIZE = 10000;

        long startTime = System.nanoTime();
        for (int i = 0; i < CONST_SIZE; i++) {
            treeList.add(i);
        }
        long stopTime = System.nanoTime();
        long durationAddTreeList = stopTime - startTime;

        startTime = System.nanoTime();
        for (int i = 0; i < CONST_SIZE; i++) {
            arrayList.add(i);
        }
        stopTime = System.nanoTime();
        long durationAddArrayList = stopTime - startTime;

        startTime = System.nanoTime();
        for (int i = 0; i < CONST_SIZE; i++) {
            linkedList.add(i);
        }
        stopTime = System.nanoTime();
        long durationAddLinkedList = stopTime - startTime;

        System.out.println("ADDING\n\n");

        System.out.println("TREE LIST " + durationAddTreeList);
        System.out.println("ARRAY LIST " + durationAddArrayList);
        System.out.println("LINKED LIST " + durationAddLinkedList);
        System.out.println("\n");
        // check that duration of adding is higher than standard java collections
        // implementing equivalent functionalities
        assertEquals(false, durationAddTreeList < durationAddArrayList && durationAddTreeList < durationAddLinkedList);


        startTime = System.nanoTime();
        for (int i = 0; i < CONST_SIZE; i += 2) {
            treeList.add(i, "test");
        }
        stopTime = System.nanoTime();
        long durationInsertTreeList = stopTime - startTime;

        startTime = System.nanoTime();
        for (int i = 0; i < CONST_SIZE; i++) {
            arrayList.add(i, "test");
        }
        stopTime = System.nanoTime();
        long durationInsertArrayList = stopTime - startTime;

        startTime = System.nanoTime();
        for (int i = 0; i < CONST_SIZE; i++) {
            linkedList.add(i, "test");
        }
        stopTime = System.nanoTime();
        long durationInsertLinkedList = stopTime - startTime;


        System.out.println("INSERTION\n\n");

        System.out.println("TREE LIST " + durationInsertTreeList);
        System.out.println("ARRAY LIST " + durationInsertArrayList);
        System.out.println("LINKED LIST " + durationInsertLinkedList);
        System.out.println("\n");

        // test that duration for insertion is much faster
        assertEquals(true, durationInsertTreeList < durationInsertArrayList && durationInsertTreeList < durationInsertLinkedList);

        startTime = System.nanoTime();
        for (int i = 0; i < treeList.size(); i++) {
            treeList.remove(i);
        }
        stopTime = System.nanoTime();
        long durationDeleteTreeList = stopTime - startTime;

        startTime = System.nanoTime();
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.remove(i);
        }
        stopTime = System.nanoTime();
        long durationDeleteArrayList = stopTime - startTime;

        startTime = System.nanoTime();
        for (int i = 0; i < linkedList.size(); i++) {
            linkedList.remove(i);
        }
        stopTime = System.nanoTime();
        long durationDeleteLinkedList = stopTime - startTime;


        System.out.println("DELETION\n\n");

        System.out.println("TREE LIST " + durationDeleteTreeList);
        System.out.println("ARRAY LIST " + durationDeleteArrayList);
        System.out.println("LINKED LIST " + durationDeleteLinkedList);
        System.out.println("\n");

        // check that duration of deletion is lower than standard java collections
        // implementing equivalent functionalities
        assertEquals(true, durationDeleteTreeList < durationDeleteArrayList && durationDeleteTreeList < durationDeleteLinkedList);


    }


}
