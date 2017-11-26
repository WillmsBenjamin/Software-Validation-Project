package ecse429.group10.pp1;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.apache.commons.collections4.iterators.ArrayListIterator;
import org.apache.commons.collections4.iterators.ObjectArrayIterator;
import org.apache.commons.collections4.iterators.ObjectArrayListIterator;
import org.apache.commons.collections4.list.CursorableLinkedList;
import org.apache.commons.collections4.list.NodeCachingLinkedList;
import org.apache.commons.collections4.list.TreeList;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.hamcrest.internal.ArrayIterator;
import org.jukito.All;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.*;
import org.junit.runner.RunWith;

import static java.util.Comparator.*;


import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test for the OrderedMap interface of the Apache commons collection
 */

@Ignore
@RunWith(JukitoRunner.class)
public class OrderedMapsTest {

    public static class Module extends JukitoModule {

        @Override
        protected void configureTest() {

            // generic binds
            bindMany(OrderedMap.class , ListOrderedMap.class , LinkedMap.class);

            // initialize empty instances of ordered maps
            bindManyNamedInstances(OrderedMap.class ,  "empty" , new ListOrderedMap() , new LinkedMap());

            //initialize non-empty one element instances of ordered maps
            bindManyNamedInstances(OrderedMap.class, "one element",
                    new ListOrderedMap<String, Integer>() {{
                        put("ONE", 1);
                    }},
                    new LinkedMap<String, Integer>() {{
                        put("ONE", 1);
                    }});

            // initialize non-empty instances of ordered maps
            bindManyNamedInstances(OrderedMap.class, "filled",
                    new ListOrderedMap<String, Integer>() {{
                        put("ONE", 1);
                        put("TWO", 2);
                    }},
                    new LinkedMap<String, Integer>() {{
                        put("ONE", 1);
                        put("TWO", 2);
                    }});
        }
    }

    /**
     *
     * Test firstKey() throws an exception on empty map
     * @param map
     *
     * */
    @Test(expected = NoSuchElementException.class)
    public void failFirstKeyEmptyMap(@All("empty") OrderedMap map) {
        map.firstKey();
    }

    /**
     *
     * Test firstKey() returns the first key in a one element map
     * @param map
     *
     * */
    @Test
    public void shouldFirstKeyOneElementMap(@All("one element") OrderedMap map) {
        Object key = map.firstKey();
        assertEquals("ONE" , key);
    }

    /**
     *
     * Test firstKey() returns the first key in a filled map
     * @param map
     *
     * */
    @Test
    public void shouldFirstKeyFilledMap(@All("filled") OrderedMap map) {
        Object key = map.firstKey();
        assertEquals("ONE" , key);
    }

    /**
     *
     * Test lastKey() throws an exception on empty map
     * @param map
     *
     * */
    @Test(expected = NoSuchElementException.class)
    public void failLastKeyEmptyMap(@All("empty") OrderedMap map) {
        map.lastKey();
    }

    /**
     *
     * Test lastkey() returns the last key in a one element map
     * @param map
     *
     * */
    @Test
    public void shouldLastKeyOneElementMap(@All("one element") OrderedMap map) {
        Object key = map.lastKey();
        assertEquals("ONE" , key);
    }

    /**
     *
     * Test lastKey() returns the last key in a filled map
     * @param map
     *
     * */
    @Test
    public void shouldLastKeyFilledMap(@All("filled") OrderedMap map) {
        Object key = map.lastKey();
        assertEquals("TWO" , key);
    }

    /**
     *
     * Test nextKey() returns the key after the one specified in a filled map
     * @param map
     *
     * */
    @Test
    public void shouldNextKeyFilledMap(@All("filled") OrderedMap map) {
        Object key = map.nextKey("ONE");
        assertEquals("TWO" , key);
    }

    /**
     *
     * Test previousKey() returns the key before the one specified in a filled map
     * @param map
     *
     * */
    @Test
    public void shouldPreviousKeyFilledMap(@All("filled") OrderedMap map) {
        Object key = map.previousKey("TWO");
        assertEquals("ONE" , key);
    }

    /**
     *
     * Test adding element to map
     * @param map
     *
     * */
    @Test
    public void putElementEmptyMap(ListOrderedMap map) {
        map = new ListOrderedMap();
        map.put("ONE" , 1);
        assertEquals(1 , map.size());
        assertEquals(1 , map.getValue(0));
        assertEquals("ONE" , map.get(0));
    }

    /**
     *
     * Test adding element to map at a specified index
     * @param map
     *
     * */
    @Test
    public void putIndexedElementEmptyMap(ListOrderedMap map) {
        map = new ListOrderedMap();
        map.put("TWO" , 2);
        map.put("THREE" , 3);
        map.put(0 , "ONE" , 1);
        assertEquals(3 , map.size());
        assertEquals(1 , map.getValue(0));
        assertEquals("ONE" , map.get(0));
    }

    /**
     *
     * Test adding multiple element at once to map
     * @param map
     *
     * */
    @Test
    public void putAllElementEmptyMap(ListOrderedMap map , ListOrderedMap add) {
        map = new ListOrderedMap();
        add = new ListOrderedMap();
        add.put("ONE" , 1);
        add.put("TWO" , 2);
        add.put("THREE" , 3);
        map.putAll(add);
        assertEquals(3 , map.size());
        assertEquals(1 , map.getValue(0));
        assertEquals("ONE" , map.get(0));
        assertEquals(2 , map.getValue(1));
        assertEquals("TWO" , map.get(1));
        assertEquals(3 , map.getValue(2));
        assertEquals("THREE" , map.get(2));
    }

    /**
     *
     * Test removing element from map
     * @param map
     *
     * */
    @Test
    public void shoudRemoveElementMap(ListOrderedMap map) {
        map = new ListOrderedMap();
        map.put("ONE" , 1);
        map.put("TWO" , 2);
        map.remove(1);
        assertEquals(1 , map.size());
    }

    /**
     *
     * Test that removing an element from an empty map throws an exception
     * @param map
     *
     * */
    @Test(expected = IndexOutOfBoundsException.class)
    public void failRemoveEmptyMap(ListOrderedMap map) {
        map = new ListOrderedMap();
        map.remove(0);
    }

    /**
     *
     * Test get() throws an exception on an empty map
     * @param map
     *
     * */
    @Test(expected = IndexOutOfBoundsException.class)
    public void failGetKeyEmptyListOrderedMap(ListOrderedMap map)
    {
        map = new ListOrderedMap();
        map.get(0);
    }

    /**
     *
     * Test get() returns the desired key at a specified index in a map of size 1
     * @param map
     *
     * */
    @Test
    public void shouldGetKeyOneElementListOrderedMap(ListOrderedMap map)
    {
        map = new ListOrderedMap();
        map.put("ONE" , 1);
        assertEquals("ONE" , map.get(0));
    }

    /**
     *
     * Test get() throws an exception when index is out of bound
     * @param map
     *
     * */
    @Test(expected = IndexOutOfBoundsException.class)
    public void failGetKeyOneElementListOrderedMap(ListOrderedMap map)
    {
        map = new ListOrderedMap();
        map.put("ONE" , 1);
        assertEquals("ONE" , map.get(1));
    }

    /**
     *
     * Test get() returns the desired key at a specified index in a map of size 2
     * @param map
     *
     * */
    @Test
    public void shouldGetKeyFilledtListOrderedMap(ListOrderedMap map)
    {
        map = new ListOrderedMap();
        map.put("ONE" , 1);
        map.put("TWO" , 2);
        assertEquals("ONE" , map.get(0));
    }

    /**
     *
     * Test get() throws an exception when index is out of bound
     * @param map
     *
     * */
    @Test(expected = IndexOutOfBoundsException.class)
    public void failGetKeyFilledtListOrderedMap(ListOrderedMap map)
    {
        map = new ListOrderedMap();
        map.put("ONE" , 1);
        map.put("TWO" , 2);
        assertEquals("ONE" , map.get(2));
    }

    /**
     *
     * Test getValue() throws an exception on an empty map
     * @param map
     *
     * */
    @Test(expected = IndexOutOfBoundsException.class)
    public void failGetValueEmptyListOrderedMap(ListOrderedMap map)
    {
        map = new ListOrderedMap();
        map.getValue(0);
    }

    /**
     *
     * Test getValue() returns the desired key at a specified index in a map of size 1
     * @param map
     *
     * */
    @Test
    public void shouldGetValueOneElementListOrderedMap(ListOrderedMap map)
    {
        map = new ListOrderedMap();
        map.put("ONE" , 1);
        assertEquals(1 , map.getValue(0));
    }

    /**
     *
     * Test getValue() throws an exception when index is out of bound
     * @param map
     *
     * */
    @Test(expected = IndexOutOfBoundsException.class)
    public void failGetValueOneElementListOrderedMap(ListOrderedMap map)
    {
        map = new ListOrderedMap();
        map.put("ONE" , 1);
        assertEquals(2 , map.getValue(1));
    }

    /**
     *
     * Test getValue() returns the desired key at a specified index in a map of size 2
     * @param map
     *
     * */
    @Test
    public void shouldGetValueFilledtListOrderedMap(ListOrderedMap map)
    {
        map = new ListOrderedMap();
        map.put("ONE" , 1);
        map.put("TWO" , 2);
        assertEquals(1 , map.getValue(0));
    }

    /**
     *
     * Test getValue() throws an exception when index is out of bound
     * @param map
     *
     * */
    @Test(expected = IndexOutOfBoundsException.class)
    public void failGetValueFilledtListOrderedMap(ListOrderedMap map)
    {
        map = new ListOrderedMap();
        map.put("ONE" , 1);
        map.put("TWO" , 2);
        assertEquals(3 , map.getValue(2));
    }

    /**
     *
     * Test modifying a value given a specified index in a non-empty map
     * @param map
     *
     * */
    @Test
    public void shouldSetValueMap(ListOrderedMap map) {
        map = new ListOrderedMap();
        map.put("ONE" , 1);
        map.setValue(0 , 0);
        assertEquals(0 , map.getValue(0));
    }

    /**
     *
     * Test modifying a value given a specified index in an empty map
     * @param map
     *
     * */
    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldSetValueEmptyMap(ListOrderedMap map) {
        map = new ListOrderedMap();
        map.setValue(0 , 0);
    }
}