package ecse429.group10;

import com.rits.cloning.Cloner;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.MapIterator;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Comparator.*;


import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test for the MapIterator interface inside of the Apache commons collection
 */

@RunWith(JukitoRunner.class)
public class MapIteratorTest {

    Cloner cloner = new Cloner();

    public static class Module extends JukitoModule {

         @Override
        protected void configureTest() {

             // generic binds
             bindMany(IterableMap.class , ListOrderedMap.class , LinkedMap.class, BidiMap.class);

             // initialize empty instances of maps
             bindManyNamedInstances(IterableMap.class, "empty" , new ListOrderedMap() , new LinkedMap(), new TreeBidiMap());

             // initialize non-empty instances of maps
             bindManyNamedInstances(IterableMap.class, "filled" , new ListOrderedMap<String , Integer>()
                     {{
                        put("ONE" , 1);
                        put("TWO" , 2);
                     }},
                     new LinkedMap<String , Integer>()
                     {{
                         put("ONE" , 1);
                         put("TWO" , 2);
                     }},
                     new TreeBidiMap<String , Integer>()
                     {{
                         put("ONE" , 1);
                         put("TWO" , 2);
                     }});

             // initialize non-empty one element instances of maps
             bindManyNamedInstances(IterableMap.class, "one element" , new ListOrderedMap<String , Integer>()
                     {{
                         put("ONE" , 1);
                     }},
                     new LinkedMap<String , Integer>()
                     {{
                         put("ONE" , 1);
                     }},
                     new TreeBidiMap<String , Integer>()
                     {{
                         put("ONE" , 1);
                     }});
         }


    }

    /**
     *
     * Test that there is no next element in an empty map
     * @param map
     *
     * */
    @Test(expected = NoSuchElementException.class)
    public void failAfterLastElementEmptyMap(@All("empty") IterableMap map) {
        Iterator iterator = map.mapIterator();
        iterator.next();
    }

    /**
     *
     * Test that there is no fourth element in map of size 3 by calling next() 4 times
     * @param map
     *
     * */
    @Test(expected = NoSuchElementException.class)
    public void failAfterLastElementFilledMap(@All("filled") IterableMap map) {
        Iterator iterator = map.mapIterator();
        iterator.next();
        iterator.next();
        iterator.next();
    }

    /**
     *
     * Test that there is no second element in map of size 1 by calling next() twice
     * @param map
     *
     * */
    @Test(expected = NoSuchElementException.class)
    public void failAfterLastElementOnedMap(@All("one element") IterableMap map) {
        Iterator iterator = map.mapIterator();
        iterator.next();
        iterator.next();
    }

    /**
     *
     * Test that hasNext() returns false when called on an empty map
     * @param map
     *
     * */
    @Test
    public void shouldReturnFalseHasNextEmpty(@All("empty") IterableMap map) {
        Iterator iterator = map.mapIterator();
        assertFalse(iterator.hasNext());
    }

    /**
     *
     * Test that hasNext() returns true when called on a map of size 2
     * @param map
     *
     * */
    @Test
    public void shouldReturnTrueHasNextFilled(@All("filled") IterableMap map) {
        Iterator iterator = map.mapIterator();
        assertTrue(iterator.hasNext());
    }

    /**
     *
     * Test that hasNext() returns true when called on the before last element on a map of size 2
     * @param map
     *
     * */
    @Test
    public void shouldReturnTrueHasNextFilledLast(@All("filled") IterableMap map) {
        Iterator iterator = map.mapIterator();
        for(int i = 0 ; i < map.size() - 1 ; i++)
        {
            iterator.next();
        }
        assertTrue(iterator.hasNext());
    }

    /**
     *
     * Test that hasNext() returns false when called on the last element on a map of size 2
     * @param map
     *
     * */
    @Test
    public void shouldReturnFalseHasNextFilledLast(@All("filled") IterableMap map) {
        Iterator iterator = map.mapIterator();
        for(int i = 0 ; i < map.size() ; i++)
        {
            iterator.next();
        }
        assertFalse(iterator.hasNext());
    }

    /**
     *
     * Test that hasNext() returns true when called on map of size 1
     * @param map
     *
     * */
    @Test
    public void shouldReturnTrueHasNextOneElement(@All("one element") IterableMap map) {
        Iterator iterator = map.mapIterator();
        assertTrue(iterator.hasNext());
    }

    /**
     *
     * Test that hasNext() returns false when called on the last element of a map of size 1
     * @param map
     *
     * */
    @Test
    public void shouldReturnFalseHasNextOneElement(@All("one element") IterableMap map) {
        Iterator iterator = map.mapIterator();
        iterator.next();
        assertFalse(iterator.hasNext());
    }

}
