package ecse429.group10.pp3;

import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.apache.commons.collections4.map.HashedMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TreeBidiMapTest {

    private HashedMap hMap;

    @Before
    public void setUp() {
        hMap = new HashedMap(3);
        hMap.put(1, "aaa");
        hMap.put(2, "bbb");
        hMap.put(3, "ccc");
        hMap.put(4, "ddd");
        hMap.put(5, "eee");
        hMap.put(6, "fff");
        hMap.put(7, "ggg");
    }

    @After
    public void tearDown() {
        hMap = null;
    }

    /*
     * Test 2.01: Test for the constructor with no argument.
     * It uses another method (size()) which must be tested, and so, failures
     * must be cross-referenced with the tests for size().
     */
    @Test
    public void shouldCreateEmptyTreeBidiMap() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        assertEquals(0, emptyMap.size());
    }

    /*
     * Test 2.02: Test for the constructor with a map as argument.
     * It uses other methods (size(), and putAll()) which must be tested, and so, failures
     * must be cross-referenced with the tests for size() and putAll().
     */
    @Test
    public void shouldCreateTreeBidiMapFromMapInput() {
        TreeBidiMap adaptedMap = new TreeBidiMap(hMap);
        assertEquals(7, adaptedMap.size());
    }

    /*
     * Test 2.03: Test for null input on the constructor with a map as argument.
     */
    @Test
    public void shouldFailTreeBidiMapWithNullPointerException() {
        HashedMap clone = hMap.clone();
        TreeBidiMap adaptedMap;
        clone.put(8, null);
        int i = 0;
        try {
            adaptedMap = new TreeBidiMap(clone);
        } catch(Exception e) {
            assertTrue(e instanceof NullPointerException);
            i++;
        }
        if(i == 0) {
            fail();
        }
        clone = hMap.clone();
        clone.put(null, "hhh");
        try {
            adaptedMap = new TreeBidiMap(clone);
        } catch(Exception e) {
            assertTrue(e instanceof NullPointerException);
            return;
        }
        fail();
    }

    /*
     * Test 2.04: Test for size().
     * Inputs of size 0, and arbitrary size.
     */
    @Test
    public void shouldReturnSizeOfMap() {
        TreeBidiMap[] maps = new TreeBidiMap[2];
        maps[0] = new TreeBidiMap(new HashedMap()); //size 0
        maps[1] = new TreeBidiMap(hMap);//arbitrary size (7)

        assertEquals(0, maps[0].size());
        assertEquals(7, maps[1].size());
    }

    /*
     * Test 2.05: Test for clear().
     * Cannot access modifications, so modify() can't be checked.
     * Cannot access RootNodes, so they can't be checked.
     */
    @Test
    public void shouldClearMap() {
        TreeBidiMap clearableMap = new TreeBidiMap(hMap);
        assertEquals(7, clearableMap.size());
        clearableMap.clear();
        assertEquals(0, clearableMap.size());
    }

    /*
     * Test 2.06: Test for containsKey(key) showing the key is contained.
     * Path through checkKey() and checkNonNullComparable() without throwing an exception,
     * then through lookupKey() and lookup() with the key being the first to be checked.
     */
    @Test
    public void shouldReturnKeyIsContainedFirst() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertTrue(map.containsKey(1));
    }

    /*
     * Test 2.06: Test for containsKey(key) showing the key is contained.
     * Path through checkKey() and checkNonNullComparable() without throwing an exception,
     * then through lookupKey() and lookup() with the key being the last to be checked.
     */
    @Test
    public void shouldReturnKeyIsContainedLast() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertTrue(map.containsKey(7));
    }

    /*
     * Test 2.08: Test for containsKey(key) showing the key is not contained.
     * Path through checkKey() and checkNonNullComparable() without throwing an exception,
     * then through lookupKey() and lookup() with the key not being contained
     * (else block checked, and while runs to termination).
     */
    @Test
    public void shouldReturnKeyIsNotContained() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertFalse(map.containsKey(50));
    }

    /*
     * Test 2.09: Test for containsKey(key) with null input.
     * Path through checkKey() and checkNonNullComparable() throwing NullPointerException.
     */
    @Test
    public void shouldFailContainsKeyWithNullPointerException() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        try {
            map.containsKey(null);
        } catch(Exception e) {
            assertTrue(e instanceof NullPointerException);
            return;
        }
        fail();
    }

    /*
     * Test 2.10: Test for containsKey(key) with non-Comparable input.
     * Path through checkKey() and checkNonNullComparable() throwing ClassCastException.
     */
    @Test
    public void shouldFailContainsKeyWithClassCastException() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int[] array = new int[5];
        try {
            map.containsKey(array);
        } catch(Exception e) {
            assertTrue(e instanceof ClassCastException);
            return;
        }
        fail();
    }

    /*
     * Test 2.11: Test for containsValue(value) showing the value is contained.
     * Path through checkValue() and checkNonNullComparable() without throwing an exception,
     * then through lookupValue() and lookup() with the value being the first to be checked.
     */
    @Test
    public void shouldReturnValueIsContainedFirst() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertTrue(map.containsValue("aaa"));
    }

    /*
     * Test 2.12: Test for containsValue(value) showing the value is contained.
     * Path through checkValue() and checkNonNullComparable() without throwing an exception,
     * then through lookupValue() and lookup() with the value being the last to be checked.
     */
    @Test
    public void shouldReturnValueIsContainedLast() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertTrue(map.containsValue("ggg"));
    }

    /*
     * Test 2.13: Test for containsValue(value) showing the value is not contained.
     * Path through checkValue() and checkNonNullComparable() without throwing an exception,
     * then through lookupValue() and lookup() with the value not being contained
     * (else block checked, and while runs to termination).
     */
    @Test
    public void shouldReturnValueIsNotContained() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertFalse(map.containsValue("zzz"));
    }

    /*
     * Test 2.14: Test for containsValue(value) with null input.
     * Path through checkValue() and checkNonNullComparable() throwing NullPointerException.
     */
    @Test
    public void shouldFailContainsValueWithNullPointerException() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        try {
            map.containsValue(null);
        } catch(Exception e) {
            assertTrue(e instanceof NullPointerException);
            return;
        }
        fail();
    }

    /*
     * Test 2.15: Test for containsValue(value) with non-Comparable input.
     * Path through checkValue() and checkNonNullComparable() throwing ClassCastException.
     */
    @Test
    public void shouldFailContainsValueWithClassCastException() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int[] array = new int[5];
        try {
            map.containsValue(array);
        } catch(Exception e) {
            assertTrue(e instanceof ClassCastException);
            return;
        }
        fail();
    }

    /*
     * Test 2.16: Test for toString() with map size = 0.
     * Path setting first if in doToString() to true, promptly returning.
     * Relies on TreeBidiMap() constructor, tested earlier. If errors arise,
     * cross reference them with test 2.01.
     */
    @Test
    public void shouldReturnEmptyBracesAsString() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        assertEquals("{}", emptyMap.toString());
    }

    /*
     * Test 2.17: Test for toString() with map size != 0.
     * Path setting first if in doToString() to false, and passing through all remaining branches.
     * Relies on TreeBidiMap() constructor, tested earlier. If errors arise,
     * cross reference them with test 2.01.
     */
    @Test
    public void shouldReturnMapInBracesAsString() {
        TreeBidiMap nonEmptyMap = new TreeBidiMap(hMap);
        assertEquals("{1=aaa, 2=bbb, 3=ccc, 4=ddd, 5=eee, 6=fff, 7=ggg}", nonEmptyMap.toString());
    }

    /*
     * Test 2.18: Test for isEmpty() with map size = 0.
     */
    @Test
    public void shouldReturnMapIsEmpty() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        assertTrue(emptyMap.isEmpty());
    }

    /*
     * Test 2.19: Test for isEmpty() with map size != 0.
     */
    @Test
    public void shouldReturnMapIsNotEmpty() {
        TreeBidiMap nonEmptyMap = new TreeBidiMap(hMap);
        assertFalse(nonEmptyMap.isEmpty());
    }
}
