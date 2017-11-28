package ecse429.group10.pp3;

import org.apache.commons.collections4.BagUtils;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.SortedBag;
import org.apache.commons.collections4.bag.*;

import org.apache.commons.collections4.functors.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by ben on 11/27/17.
 */

public class ŒBagUtilsTest {

    /*
     * As explained in project part 2, BagUtils depends on Bag and SortedBag.
     * As SortedBag extends Bag, we can use a SortedBag instance to test all
     * the methods in BagUtils. TreeBag "is the standard implementation of a sorted bag"
     * according to the apache commons collections documentation, and so it
     * was chosen as the single class used to replace the necessary stub/mock
     * to test BagUtils (we can assume TreeBag is already tested).
     */
    private TreeBag bagInstance;
    private ArrayList<Integer> items;

    /*
     * To setup the tests, the bag is filled with 4 unique items, to allow checking of contents, and order.
     */
    @Before
    public void setUp() {
        this.bagInstance = new TreeBag<Integer>();
        this.items = new ArrayList<>();
        this.items.add(5);
        this.items.add(3);
        this.items.add(16);
        this.items.add(1);
        this.bagInstance.addAll(items);
    }

    @After
    public void tearDown() {
        this.bagInstance = null;
        items = null;
    }

    /*
     * Test 3.01: for method "synchronizedBag(final Bag<E> bag)".
     * It checks whether the method returns a SynchronizedBag type.
     */
    @Test
    public void shouldReturnSynchronizedBag() {
        Bag returnedBag = BagUtils.synchronizedBag(bagInstance);
        assertTrue(returnedBag instanceof SynchronizedBag);
        assertTrue(returnedBag.containsAll(items));
        assertEquals(4, returnedBag.size());
    }

    /*
     * Test 3.02: for method "synchronizedBag(final Bag<E> bag)".
     * It checks whether the method throws a null pointer exception on null input.
     */
    @Test
    public void shouldFailSynchronizedBagWithNullPointerException() {
        try {
            BagUtils.synchronizedBag(null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            return;
        }
        fail();
    }

    /*
     * Test 3.03: for method "unmodifiableBag(final Bag<E> bag)".
     * It checks whether the method returns an UnmodifiableBag type.
     */
    @Test
    public void shouldReturnUnmodifiableBag() {
        Bag returnedBag = BagUtils.unmodifiableBag(bagInstance);
        assertTrue(returnedBag instanceof UnmodifiableBag);
        assertTrue(returnedBag.containsAll(items));
        assertEquals(4, returnedBag.size());
    }

    /*
     * Test 3.04: for method "unmodifiableBag(final Bag<E> bag)".
     * It checks whether the method throws a null pointer exception on null input.
     */
    @Test
    public void shouldFailUnmodifiableBagWithNullPointerException() {
        try {
            BagUtils.unmodifiableBag(null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            return;
        }
        fail();
    }

    /*
     * Test 3.05: for method "predicatedBag(final Bag<E> bag, final Predicate<? super E> predicate)".
     * It checks whether the method returns a PredicatedBag type with the specified predicate (not null).
     */
    @Test
    public void shouldReturnPredicatedBag() {
        Bag returnedBag = BagUtils.predicatedBag(bagInstance, NotNullPredicate.INSTANCE);
        assertTrue(returnedBag instanceof PredicatedBag);
        assertTrue(returnedBag.containsAll(items));
        assertEquals(4, returnedBag.size());
        try {
            returnedBag.add(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(4, returnedBag.size());
            return;
        }
        fail();
    }

    /*
     * Test 3.06: for method "predicatedBag(final Bag<E> bag, final Predicate<? super E> predicate)".
     * It checks whether the method throws a null pointer exception on null input (bag, or predicate).
     */
    @Test
    public void shouldFailPredicatedBagWithNullPointerException() {
        int i = 0;
        try {
            BagUtils.predicatedBag(null, FalsePredicate.INSTANCE);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            i++;
        }
        if(i == 0) {
            fail();
        }
        try {
            BagUtils.predicatedBag(bagInstance, null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            return;
        }
        fail();
    }

    /*
     * Test 3.07: for method "transformingBag(final Bag<E> bag, final Transformer<? super E, ? extends E> transformer)".
     * It checks whether the method returns a TransformedBag type with the specified transformer.
     */
    @Test
    public void shouldReturnTransformedBag() {
        ConstantTransformer trans = new ConstantTransformer(77);
        Bag returnedBag = BagUtils.transformingBag(bagInstance, trans);
        assertTrue(returnedBag instanceof TransformedBag);
        assertTrue(returnedBag.containsAll(items));
        assertEquals(4, returnedBag.size());
        returnedBag.add(30);
        assertTrue(returnedBag.contains(77));
        assertEquals(5, returnedBag.size());
    }

    /*
     * Test 3.08: for method "transformingBag(final Bag<E> bag, final Transformer<? super E, ? extends E> transformer)".
     * It checks whether the method throws a null pointer exception on null input (bag, or transformer).
     */
    @Test
    public void shouldFailTransformingBagWithNullPointerException() {
        int i = 0;
        try {
            BagUtils.transformingBag(null, new PredicateTransformer(NotNullPredicate.INSTANCE));
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            i++;
        }
        if(i == 0) {
            fail();
        }
        try {
            BagUtils.transformingBag(bagInstance, null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            return;
        }
        fail();
    }

    /*
     * Test 3.09: for method "collectionBag(final Bag<E> bag)".
     * It checks whether the method returns a CollectionBag type.
     */
    @Test
    public void shouldReturnCollectionBag() {
        Bag returnedBag = BagUtils.collectionBag(bagInstance);
        assertTrue(returnedBag instanceof CollectionBag);
        assertTrue(returnedBag.containsAll(items));
        assertEquals(4, returnedBag.size());
    }

    /*
     * Test 3.10: for method "collectionBag(final Bag<E> bag)".
     * It checks whether the method throws a null pointer exception on null input.
     */
    @Test
    public void shouldFailCollectionBagWithNullPointerException() {
        try {
            BagUtils.collectionBag(null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            return;
        }
        fail();
    }

    /*
     * Test 3.11: for method "synchronizedSortedBag(final SortedBag<E> bag)".
     * It checks whether the method returns a SynchronizedSortedBag type.
     */
    @Test
    public void shouldReturnSynchronizedSortedBag() {
        SortedBag returnedBag = BagUtils.synchronizedSortedBag(bagInstance);
        assertTrue(returnedBag instanceof SynchronizedSortedBag);
        assertTrue(returnedBag.containsAll(items));
        assertEquals(4, returnedBag.size());
        assertEquals(1, returnedBag.first());
        assertEquals(16, returnedBag.last());
    }

    /*
     * Test 3.12: for method "synchronizedSortedBag(final SortedBag<E> bag)".
     * It checks whether the method throws a null pointer exception on null input.
     */
    @Test
    public void shouldFailSynchronizedSortedBagWithNullPointerException() {
        try {
            BagUtils.synchronizedSortedBag(null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            return;
        }
        fail();
    }

    /*
     * Test 3.13: for method "unmodifiableSortedBag(final SortedBag<E> bag)".
     * It checks whether the method returns an UnmodifiableBag type.
     */
    @Test
    public void shouldReturnUnmodifiableSortedBag() {
        SortedBag returnedBag = BagUtils.unmodifiableSortedBag(bagInstance);
        assertTrue(returnedBag instanceof UnmodifiableSortedBag);
        assertTrue(returnedBag.containsAll(items));
        assertEquals(4, returnedBag.size());
        assertEquals(1, returnedBag.first());
        assertEquals(16, returnedBag.last());
    }

    /*
     * Test 3.14: for method "unmodifiableSortedBag(final SortedBag<E> bag)".
     * It checks whether the method throws a null pointer exception on null input.
     */
    @Test
    public void shouldFailUnmodifiableSortedBagWithNullPointerException() {
        try {
            BagUtils.unmodifiableSortedBag(null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            return;
        }
        fail();
    }

    /*
     * Test 3.15: for method "predicatedSortedBag(final SortedBag<E> bag, final Predicate<? super E> predicate)".
     * It checks whether the method returns a PredicatedSortedBag type with the specified predicate (not null).
     */
    @Test
    public void shouldReturnPredicatedSortedBag() {
        SortedBag returnedBag = BagUtils.predicatedSortedBag(bagInstance, NotNullPredicate.INSTANCE);
        assertTrue(returnedBag instanceof PredicatedSortedBag);
        assertTrue(returnedBag.containsAll(items));
        assertEquals(4, returnedBag.size());
        assertEquals(1, returnedBag.first());
        assertEquals(16, returnedBag.last());
        try {
            returnedBag.add(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(4, returnedBag.size());
            return;
        }
        fail();
    }

    /*
     * Test 3.16: for method "predicatedSortedBag(final SortedBag<E> bag, final Predicate<? super E> predicate)".
     * It checks whether the method throws a null pointer exception on null input (bag, or predicate).
     */
    @Test
    public void shouldFailPredicatedSortedBagWithNullPointerException() {
        int i = 0;
        try {
            BagUtils.predicatedSortedBag(null, FalsePredicate.INSTANCE);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            i++;
        }
        if(i == 0) {
            fail();
        }
        try {
            BagUtils.predicatedSortedBag(bagInstance, null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            return;
        }
        fail();
    }

    /*
     * Test 3.17: for method "transformingSortedBag(final SortedBag<E> bag, final Transformer<? super E, ? extends E> transformer)".
     * It checks whether the method returns a TransformedSortedBag type with the specified transformer.
     */
    @Test
    public void shouldReturnTransformedSortedBag() {
        ConstantTransformer trans = new ConstantTransformer(77);
        SortedBag returnedBag = BagUtils.transformingSortedBag(bagInstance, trans);
        assertTrue(returnedBag instanceof TransformedSortedBag);
        assertTrue(returnedBag.containsAll(items));
        assertEquals(4, returnedBag.size());
        returnedBag.add(30);
        assertTrue(returnedBag.contains(77));
        assertEquals(1, returnedBag.first());
        assertEquals(77, returnedBag.last());
        assertEquals(5, returnedBag.size());
    }

    /*
     * Test 3.18: for method "transformingSortedBag(final SortedBag<E> bag, final Transformer<? super E, ? extends E> transformer)".
     * It checks whether the method throws a null pointer exception on null input (bag, or transformer).
     */
    @Test
    public void shouldFailTransformingSortedBagWithNullPointerException() {
        int i = 0;
        try {
            BagUtils.transformingSortedBag(null, new PredicateTransformer(NotNullPredicate.INSTANCE));
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            i++;
        }
        if(i == 0) {
            fail();
        }
        try {
            BagUtils.transformingSortedBag(bagInstance, null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            return;
        }
        fail();
    }

    /*
     * Test 3.19: for method "emptyBag()".
     * It checks whether the method returns an empty UnmodifiableBag type.
     */
    @Test
    public void shouldReturnEmptyBag() {
        Bag returnedBag = BagUtils.emptyBag();
        assertTrue(returnedBag instanceof UnmodifiableBag);
        assertTrue(returnedBag.isEmpty());
    }

    /*
     * Test 3.20: for method "emptySortedBag()".
     * It checks whether the method returns an empty UnmodifiableSortedBag type.
     */
    @Test
    public void shouldReturnEmptySortedBag() {
        SortedBag returnedBag = BagUtils.emptySortedBag();
        assertTrue(returnedBag instanceof UnmodifiableSortedBag);
        assertTrue(returnedBag.isEmpty());
    }
}
