package ecse429.group10;


import com.rits.cloning.Cloner;
import org.apache.commons.collections4.iterators.*;
import org.jukito.All;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import java.util.ListIterator;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class IteratorTest {
    Cloner cloner = new Cloner();

    public static class Module extends JukitoModule {

        @Override
        protected void configureTest() {

            CollatingIterator collatingIterator = new CollatingIterator(new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    return s1.compareToIgnoreCase(s2);
                }
            });

            collatingIterator.addIterator(Arrays.asList("abc", "cbd", "xyz").iterator());

            CollatingIterator collatingIteratorEmpty = new CollatingIterator(new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    return s1.compareToIgnoreCase(s2);
                }
            });

            bindManyNamedInstances(Iterator.class, "non-empty", new ArrayIterator<>(new String[]{"abc", "cbd", "xyz"}),
                    collatingIterator,
                    new LoopingIterator<>(Arrays.asList("abc", "cbd", "xyz")),
                    new LoopingListIterator<>(Arrays.asList("abc", "cbd", "xyz")),
                    new ArrayListIterator(new String[]{"abc", "cbd", "xyz"}),
                    new ObjectArrayIterator(new String[]{"abc", "cbd", "xyz"}),
                    new UniqueFilterIterator<>(Arrays.asList("abc", "cbd", "xyz").iterator()));

            bindManyNamedInstances(Iterator.class, "non-looping", new ArrayIterator<>(new String[]{"abc", "cbd", "xyz"}),
                    collatingIterator,
                    new ArrayListIterator(new String[]{"abc", "cbd", "xyz"}),
                    new ObjectArrayIterator(new String[]{"abc", "cbd", "xyz"}),
                    new UniqueFilterIterator<>(Arrays.asList("abc", "cbd", "xyz").iterator()));

            bindManyNamedInstances(Iterator.class, "looping",
                    new LoopingIterator<>(Arrays.asList("abc", "cbd", "xyz")),
                    new LoopingListIterator<>(Arrays.asList("abc", "cbd", "xyz")));


            bindManyNamedInstances(Iterator.class, "empty", new ArrayIterator<>(new String[]{}),
                    collatingIteratorEmpty,
                    new LoopingIterator<>(Arrays.asList()),
                    new LoopingListIterator<>(Arrays.asList()),
                    new ArrayListIterator(new String[]{}),
                    new ObjectArrayIterator(new String[]{}),
                    new UniqueFilterIterator<>(Arrays.asList().iterator()));
        }
    }

    @Test
    public void shouldIterateThroughAllItems(@All("non-empty") Iterator iterator) {
        Iterator iteratorCopy = cloner.deepClone(iterator);

        assertEquals("abc", iteratorCopy.next());
        assertEquals("cbd", iteratorCopy.next());
        assertEquals("xyz", iteratorCopy.next());
    }

    @Test(expected = NoSuchElementException.class)
    public void failIterateThroughAllItems(@All("non-looping") Iterator iterator) {
        Iterator iteratorCopy = cloner.deepClone(iterator);

        assertEquals("abc", iteratorCopy.next());
        assertEquals("cbd", iteratorCopy.next());
        assertEquals("xyz", iteratorCopy.next());
        iteratorCopy.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void failIterateThroughEmptyItems(@All("empty") Iterator iterator) {
        Iterator iteratorCopy = cloner.deepClone(iterator);

        iteratorCopy.next();
    }

    @Test
    public void shouldIterateThroughAllItemsAndGoBackInLoop(@All("looping") Iterator iterator) {
        Iterator iteratorCopy = cloner.deepClone(iterator);

        assertEquals("abc", iteratorCopy.next());
        assertEquals("cbd", iteratorCopy.next());
        assertEquals("xyz", iteratorCopy.next());
        assertEquals("abc", iteratorCopy.next());
    }


    @Test
    public void shouldCheckNextItemThroughAllItems(@All("non-looping") Iterator iterator) {
        Iterator iteratorCopy = cloner.deepClone(iterator);

        assertEquals(true, iteratorCopy.hasNext());
        iteratorCopy.next();
        assertEquals(true, iteratorCopy.hasNext());
        iteratorCopy.next();
        assertEquals(true, iteratorCopy.hasNext());
        iteratorCopy.next();
        assertEquals(false, iteratorCopy.hasNext());
    }


    @Test(expected = UnsupportedOperationException.class)
    public void failRemoveCurrentItem(@All("non-empty") Iterator iterator) {
        Iterator iteratorCopy = cloner.deepClone(iterator);

        iteratorCopy.next();
        iteratorCopy.remove();
    }

    @Test
    public void shouldSupportRemovalOfCurrentItemZippingIteratorAndLoopingIterator() {

        LinkedList bufferInstance = new LinkedList<>(Arrays.asList("abc", "cbd", "xyz"));
        ArrayList bufferInstance2 = new ArrayList<>(Arrays.asList("abc", "cbd", "xyz"));
        LinkedList bufferInstance3 = new LinkedList<>(Arrays.asList("abc", "cbd", "xyz"));

        ZippingIterator zippingIterator = new ZippingIterator(bufferInstance.listIterator(), bufferInstance2.listIterator());
        LoopingIterator loopingIterator = new LoopingIterator<>(bufferInstance3);

        zippingIterator.next();
        zippingIterator.remove();
        loopingIterator.next();
        assertEquals(2, bufferInstance.size());
        loopingIterator.remove();
        assertEquals(2, bufferInstance3.size());
    }

    @Test(expected = IllegalStateException.class)
    public void failMultipleSupportRemovalOfCurrentItemZippingIteratorAndLoopingIterator() {

        LinkedList bufferInstance = new LinkedList<>(Arrays.asList("abc", "cbd", "xyz"));
        ArrayList bufferInstance2 = new ArrayList<>(Arrays.asList("abc", "cbd", "xyz"));
        LinkedList bufferInstance3 = new LinkedList<>(Arrays.asList("abc", "cbd", "xyz"));

        ZippingIterator zippingIterator = new ZippingIterator(bufferInstance.listIterator(), bufferInstance2.listIterator());
        LoopingIterator loopingIterator = new LoopingIterator<>(bufferInstance3);

        zippingIterator.next();
        zippingIterator.remove();
        zippingIterator.remove();
        loopingIterator.next();
        assertEquals(2, bufferInstance.size());
        loopingIterator.remove();
        loopingIterator.remove();
        assertEquals(2, bufferInstance3.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotIterateThroughDuplicateItems() {
        UniqueFilterIterator uniqueFilterIterator = new UniqueFilterIterator<>(Arrays.asList("abc", "cbd", "xyz", "abc").iterator());

        uniqueFilterIterator.next();
        uniqueFilterIterator.next();
        uniqueFilterIterator.next();
        uniqueFilterIterator.next();
    }

    @Test
    public void shouldSupportAlternatingIteration() {

        ZippingIterator zippingIterator = new ZippingIterator(new LinkedList<>(Arrays.asList("abc", "cbd", "xyz")).listIterator(), new ArrayList<>(Arrays.asList("abc", "cbd", "xyz")).listIterator());

        assertEquals("abc", zippingIterator.next());
        assertEquals("abc", zippingIterator.next());
    }


}
