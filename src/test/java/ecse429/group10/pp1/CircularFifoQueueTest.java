package ecse429.group10.pp1;

import com.rits.cloning.Cloner;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.Unmodifiable;
import org.apache.commons.collections4.iterators.AbstractMapIteratorDecorator;
import org.apache.commons.collections4.iterators.EmptyMapIterator;
import org.apache.commons.collections4.iterators.UnmodifiableMapIterator;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.commons.collections4.trie.UnmodifiableTrie;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Test for the Queue interface inside of the Apache commons collection
 */

@Ignore
public class CircularFifoQueueTest {

    private CircularFifoQueue emptyQueue;
    private CircularFifoQueue filledQueue;
    Cloner cloner = new Cloner();


    /**
     *
     * Setting up the tests before running them
     *
     * */
    @Before
    public void setUp() {
        this.emptyQueue = new CircularFifoQueue<Integer>(10);
        this.filledQueue = new CircularFifoQueue<Integer>(10);
        for(int i = 0 ; i < 10 ; i++)
        {
            this.filledQueue.add(i);
        }
    }

    /**
     *
     * test that the correct size is returned
     *
     * */
    @Test
    public void shouldReturnSize() {
        int emptySize = this.emptyQueue.size();
        int filledSize = this.filledQueue.size();
        assertEquals(0 , emptySize);
        assertEquals(10 , filledSize);

        MapIterator test2 = new AbstractMapIteratorDecorator(EmptyMapIterator.INSTANCE);
        MapIterator test = new AbstractMapIteratorDecorator(test2);

        Trie test1 = new UnmodifiableTrie(new PatriciaTrie());

        UnmodifiableTrie trie = new UnmodifiableTrie(test1);
    }

    /**
     *
     * Test reading an element from an empty queue
     *
     * */
    @Test(expected = NoSuchElementException.class)
    public void failReadElementEmptyQueue() {
        this.emptyQueue.get(0);
    }

    /**
     *
     * Test reading an element from a filled queue
     *
     * */
    @Test
    public void shouldReadElementFilledQueue() {
        assertEquals(0 , this.filledQueue.get(0));
    }

    /**
     *
     * Test adding an element to an empty queue
     *
     * */
    @Test
    public void shouldAddEmptyQueue() {
        int element = 0;
        this.emptyQueue.add(element);
        assertEquals(1 , this.emptyQueue.size());
        assertEquals(0 , this.emptyQueue.get(0));
    }

    /**
     *
     * Test adding an element to a filled queue
     *
     * */
    @Test
    public void shouldAddFilledQueue() {
        int element = 0;
        this.filledQueue.add(element);
        assertEquals(0 , this.filledQueue.get(9));
    }

    /**
     *
     * Test clearing a filled queue
     *
     * */
    @Test
    public void shoudlClearQueue() {
        this.filledQueue.clear();
        assertEquals(0 , this.filledQueue.size());
    }

    /**
     *
     * Test if isFullCapacity returns false for an empty queue
     *
     * */
    @Test
    public void shouldReturnFalseCapacity() {
        assertFalse(this.emptyQueue.isAtFullCapacity());
    }

    /**
     *
     * Test if isFullCapacity returns true for a filled queue
     *
     * */
    @Test
    public void shouldReturnTrueCapacity() {
        assertTrue(this.filledQueue.isAtFullCapacity());
    }

    /**
     *
     * Test if isEmpty returns true for an empty queue
     *
     * */
    @Test
    public void shouldReturnTrueEmpty() {
        assertTrue(this.emptyQueue.isEmpty());
    }

    /**
     *
     * Test if isEmpty returns false for a filled queue
     *
     * */
    @Test
    public void shouldReturnFalseEmpty() {
        assertFalse(this.filledQueue.isEmpty());
    }

    /**
     *
     * Test if isFull returns false for an empty queue
     *
     * */
    @Test
    public void shouldReturnFalseFull() {
        assertFalse(this.emptyQueue.isFull());
    }

    /**
     *
     * Test if isFull returns flase for a filled queue
     *
     * */
    @Test
    public void shouldReturnFalseFilledFull() {
        assertFalse(this.filledQueue.isFull());
    }

    /**
     *
     * Test adding an element to an empty queue using offer()
     *
     * */
    @Test
    public void shouldOfferEmptyQueue() {
        CircularFifoQueue copiedQueue = cloner.deepClone(this.emptyQueue);
        copiedQueue.offer(0);
        assertEquals(1 , copiedQueue.size());
        assertEquals(0 , copiedQueue.get(0));
    }

    /**
     *
     * Test adding an element to a filled queue using offer()
     *
     * */
    @Test
    public void shouldOfferFilledQueue() {
        CircularFifoQueue copiedQueue = cloner.deepClone(this.filledQueue);
        copiedQueue.offer(0);
        assertEquals(10 , copiedQueue.size());
        assertEquals(0 , copiedQueue.get(9));
    }

    /**
     *
     * test the peek method on empty queue
     *
     * */
    @Test
    public void shouldPeekEmpty() {
        assertEquals(null , this.emptyQueue.peek());
    }

    /**
     *
     * test the peek method on nonempty queue
     *
     * */
    @Test
    public void shouldPeekFilled() {
        assertEquals(0 , this.filledQueue.peek());
    }

    /**
     *
     * test the element method on empty queue
     *
     * */
    @Test(expected = NoSuchElementException.class)
    public void failElementEmpty() {
        assertEquals(null , this.emptyQueue.element());
    }

    /**
     *
     * test the element method on nonempty queue
     *
     * */
    @Test
    public void shouldElementFilled() {
        assertEquals(0 , this.filledQueue.element());
    }

    /**
     *
     * test the poll method on empty queue
     *
     * */
    @Test
    public void shouldPollEmpty() {
        assertEquals(null , this.emptyQueue.poll());
    }

    /**
     *
     * test the poll method on nonempty queue
     *
     * */
    @Test
    public void shouldPollFilled() {
        assertEquals(0 , this.filledQueue.poll());
    }

    /**
     *
     * test the remove method on empty queue
     *
     * */
    @Test(expected = NoSuchElementException.class)
    public void failRemoveEmpty() {
        assertEquals(null , this.emptyQueue.remove());
    }

    /**
     *
     * test the remove method on nonempty queue
     *
     * */
    @Test
    public void shouldRemoveFilled() {
        assertEquals(0 , this.filledQueue.remove());
        assertEquals(9 , this.filledQueue.size());
    }

}
