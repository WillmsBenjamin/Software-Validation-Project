package ecse429.group10.pp3;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.OrderedBidiMap;
import org.apache.commons.collections4.OrderedMapIterator;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.apache.commons.collections4.iterators.EmptyOrderedMapIterator;
import org.apache.commons.collections4.map.HashedMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

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
     * Test 2.03.a: Test for null input on the constructor with a map as argument.
     */
    @Test(expected = NullPointerException.class)
    public void shouldFailTreeBidiMapWithNullPointerExceptionAsValue() {
        TreeBidiMap adaptedMap;
        hMap.put(8, null);
        adaptedMap = new TreeBidiMap(hMap);
    }

    /*
     * Test 2.03.b: Test for null input on the constructor with a map as argument.
     */
    @Test(expected = NullPointerException.class)
    public void shouldFailTreeBidiMapWithNullPointerExceptionAsKey() {
        TreeBidiMap adaptedMap;
        hMap.put(null, "hhh");
        adaptedMap = new TreeBidiMap(hMap);
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
     * Test 2.07: Test for containsKey(key) showing the key is contained.
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
    @Test(expected = NullPointerException.class)
    public void shouldFailContainsKeyWithNullPointerException() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        map.containsKey(null);
    }

    /*
     * Test 2.10: Test for containsKey(key) with non-Comparable input.
     * Path through checkKey() and checkNonNullComparable() throwing ClassCastException.
     */
    @Test(expected = ClassCastException.class)
    public void shouldFailContainsKeyWithClassCastException() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int[] array = new int[5];
        map.containsKey(array);
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
    @Test(expected = NullPointerException.class)
    public void shouldFailContainsValueWithNullPointerException() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        map.containsValue(null);
    }

    /*
     * Test 2.15: Test for containsValue(value) with non-Comparable input.
     * Path through checkValue() and checkNonNullComparable() throwing ClassCastException.
     */
    @Test(expected = ClassCastException.class)
    public void shouldFailContainsValueWithClassCastException() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int[] array = new int[5];
        map.containsValue(array);
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

    /*
     * Test 2.20: Test for new EntrySet with empty map
     */
    @Test
    public void shouldReturnNewEntrySet() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        Set<TreeBidiMap.Entry> returnedSet = emptyMap.entrySet();
        assertNotNull(returnedSet);
        assertEquals(0, returnedSet.size());

    }

    /*
     * Test 2.21: Test for retrieval ofEntrySet with nonempty map
     */
    @Test
    public void shouldReturnEntryView() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        Set<TreeBidiMap.Entry> returnedSet = map.entrySet();
        assertNotNull(returnedSet);
        assertEquals(7, returnedSet.size());
    }

    /*
     * Test 2.22: Test get(key) with empty map
     */
    @Test
    public void shouldReturnNullKeyNotPresentTreeEmpty() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        assertNull(emptyMap.get(1));
    }

    /*
     * Test 2.23: Test get(key) with value present in nonempty map
     */
    @Test
    public void shouldReturnValueFromKey() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertEquals("aaa", map.get(1));
    }

    /*
     * Test 2.24: Test get(key) with value not present in nonempty map
     */
    @Test
    public void shouldReturnNullKeyNotPresent() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertNull(map.get(23));
    }

    /*
     * Test 2.25: Test get(key) with non comparable key
     */
    @Test(expected = ClassCastException.class)
    public void shouldThrowClassCastExceptionKeyWrongType_get() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int[] array = new int[5];
        map.get(array);
    }

    /*
     * Test 2.26: Test get(key) with null key
     */
    @Test(expected = NullPointerException.class)
    public void shouldThrowClassCastExceptionKeyNull_get() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        map.get(null);
    }

    /*
     * Test 2.27: Test getKey(value) with empty map
     */
    @Test
    public void shouldReturnNullValueNotPresentTreeEmpty() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        assertNull(emptyMap.getKey("value"));
    }

    /*
     * Test 2.28: Test getKey(value) with value present in nonempty map
     */
    @Test
    public void shouldReturnKeyFromValue() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertEquals(1, map.getKey("aaa"));
    }

    /*
     * Test 2.29: Test getKey(value) with value not present in nonempty map
     */
    @Test
    public void shouldReturnNullValueNotPresent() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertNull(map.getKey("wrong"));
    }

    /*
     * Test 2.30: Test get(key) with non comparable key
     */
    @Test(expected = ClassCastException.class)
    public void shouldThrowClassCastExceptionValueWrongType_getKey() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int[] array = new int[5];
        map.getKey(array);
    }

    /*
     * Test 2.31: Test get(key) with null key
     */
    @Test(expected = NullPointerException.class)
    public void shouldThrowClassCastExceptionValueNull_getKey() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        map.getKey(null);
    }

    /*
     * Test 2.32: Test put of one element in empty TreeBidiMap
     */
    @Test
    public void shouldIncreaseSizeToOne() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        emptyMap.put(1, "new");
        assertFalse(emptyMap.isEmpty());
        assertTrue(emptyMap.containsKey(1));
        assertTrue(emptyMap.containsValue("new"));
        assertEquals(1, emptyMap.size());
    }

    /*
     * Test 2.33: Test put of one element in nonempty TreeBidiMap
     */
    @Test
    public void shouldIncreaseSizeByOne() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int prev_size = map.size();
        map.put(9, "new");
        assertFalse(map.isEmpty());
        assertTrue(map.containsKey(9));
        assertTrue(map.containsValue("new"));
        assertEquals(prev_size + 1, map.size());
    }

    /*
     * Test 2.34: Test put of one element with existing key in nonempty TreeBidiMap
     */
    @Test
    public void shouldReplaceExistingValue() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int prev_size = map.size();
        map.put(1, "new");
        assertFalse(map.isEmpty());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsValue("new"));
        assertEquals(prev_size, map.size());
        assertEquals("new", map.get(1));
        assertEquals(1, map.getKey("new"));
    }

    /*
     * Test 2.35: Test put of one element with existing value in nonempty TreeBidiMap
     */
    @Test
    public void shouldReplaceRemoveExistingKey() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int prev_size = map.size();
        map.put(9, "aaa");
        assertFalse(map.isEmpty());
        assertTrue(map.containsKey(9));
        assertTrue(map.containsValue("aaa"));
        assertFalse(map.containsKey(1));
        assertEquals(prev_size, map.size());
        assertEquals("aaa", map.get(9));
        assertEquals(9, map.getKey("aaa"));
    }

    /*
     * Test 2.36: Test put of one element with key of invalid type
     */
    @Test(expected = ClassCastException.class)
    public void shouldFailThrowClassCastException_put() {
        TreeBidiMap map = new TreeBidiMap();
        int[] array = new int[5];
        map.put(array, "new");
    }


    /*
     * Test 2.37: Test put of one element with null key
     */
    @Test(expected = NullPointerException.class)
    public void shouldFailThrowNullPointerException_put() {
        TreeBidiMap map = new TreeBidiMap();
        map.put(null, "new");
    }

    /*
     * Test 2.38 Test remove of one element with empty map
     */
    @Test
    public void shouldReturnNullNoMappingToRemove() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        assertNull(emptyMap.remove(1));
    }

    /*
    * Test 2.39 Test remove of one element present in nonempty map
    */
    @Test
    public void shouldReturnValueOfMappingRemoved() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int prev_size = map.size();
        assertEquals("aaa", map.remove(1));
        assertEquals(prev_size - 1, map.size());
    }

    /*
    * Test 2.40 Test remove of one element not present in nonempty map
    */
    @Test
    public void shouldReturnNullValueToRemoveNotMapped() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int prev_size = map.size();
        assertNull(map.remove(34));
        assertEquals(prev_size, map.size());
    }

    /*
     * Test 2.41: Test remove of one element with key of invalid type
     */
    @Test(expected = ClassCastException.class)
    public void shouldFailThrowClassCastException_remove() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int[] array = new int[5];
        map.remove(array);
    }

    /*
     * Test 2.42: Test remove of one element with null key
     */
    @Test(expected = NullPointerException.class)
    public void shouldFailThrowNullPointerException_remove() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        map.remove(null);
    }

    /*
     * Test 2.43: Test putAll of empty map into empty map
     */
    @Test
    public void shouldNotAlterNewMap() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        TreeBidiMap map = new TreeBidiMap();
        assertTrue(emptyMap.isEmpty());
        map.putAll(emptyMap);
        assertTrue(map.isEmpty());
    }

    /*
     * Test 2.44: Test putAll of nonempty map into empty map
     */
    @Test
    public void shouldChangeMapSizeToOtherMapSize() {
        TreeBidiMap otherMap = new TreeBidiMap(hMap);
        TreeBidiMap map = new TreeBidiMap();
        int size = otherMap.size();
        assertFalse(otherMap.isEmpty());
        map.putAll(otherMap);
        assertFalse(map.isEmpty());
        assertEquals(size, map.size());
    }

    /*
     * Test 2.45: Test putAll of empty map into nonempty map
     */
    @Test
    public void shouldNotAlterNewNonEmptyMap() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        TreeBidiMap map = new TreeBidiMap(hMap);
        int size = map.size();
        assertTrue(emptyMap.isEmpty());
        assertFalse(map.isEmpty());
        map.putAll(emptyMap);
        assertFalse(map.isEmpty());
        assertEquals(size, map.size());
    }

    /*
     * Test 2.46: Test putAll of nonempty map into nonempty map with different pairing
     */
    @Test
    public void shouldChangeSizeToSumOfMaps() {
        HashedMap hMap2;
        hMap2 = new HashedMap(3);
        hMap2.put(8, "zzz");
        hMap2.put(9, "yyy");
        hMap2.put(10, "xxx");
        hMap2.put(11, "www");
        hMap2.put(12, "vvv");
        hMap2.put(13, "uuu");
        hMap2.put(14, "ttt");

        TreeBidiMap otherMap = new TreeBidiMap(hMap2);
        TreeBidiMap map = new TreeBidiMap(hMap);
        int othersize = otherMap.size();
        int size = map.size();
        assertFalse(otherMap.isEmpty());
        assertFalse(map.isEmpty());
        map.putAll(otherMap);
        assertFalse(map.isEmpty());
        assertEquals(size + othersize, map.size());

    }

    /*
     * Test 2.47: Test putAll of nonempty map into nonempty map with matching key
     */
    @Test
    public void shouldResultInValueMappingChange() {
        TreeBidiMap otherMap = new TreeBidiMap(hMap);
        TreeBidiMap map = new TreeBidiMap(hMap);

        otherMap.put(3, "hello");
        otherMap.put(4, "wazaap");

        int size = map.size();
        assertFalse(otherMap.isEmpty());
        assertFalse(map.isEmpty());

        assertEquals("ccc", map.get(3));
        assertEquals("ddd", map.get(4));

        map.putAll(otherMap);

        assertFalse(map.isEmpty());
        assertEquals(size, map.size());
        assertEquals("hello", map.get(3));
        assertEquals("wazaap", map.get(4));
    }

    /*
     * Test 2.48: Test putAll of nonempty map into nonempty map with matching value
     */
    @Test
    public void shouldResultInKeyMappingChange() {
        TreeBidiMap otherMap = new TreeBidiMap(hMap);
        TreeBidiMap map = new TreeBidiMap(hMap);

        otherMap.put(34, "bbb");
        otherMap.put(23, "aaa");

        int size = map.size();
        assertFalse(otherMap.isEmpty());
        assertFalse(map.isEmpty());

        assertEquals(1, map.getKey("aaa"));
        assertEquals(2, map.getKey("bbb"));

        map.putAll(otherMap);

        assertFalse(map.isEmpty());
        assertEquals(size, map.size());
        assertNull(map.get(1));
        assertNull(map.get(2));
        assertEquals(34, map.getKey("bbb"));
        assertEquals(23, map.getKey("aaa"));
    }

    /*
     * Test 2.49: Test putAll of nonempty map into nonempty map with exact same entries
     */
    @Test
    public void shouldNotAlterMap_putAll() {
        TreeBidiMap otherMap = new TreeBidiMap(hMap);
        TreeBidiMap map = new TreeBidiMap(hMap);

        int othersize = otherMap.size();
        int size = map.size();

        assertEquals(size, othersize);

        map.putAll(otherMap);

        assertEquals(size, map.size());

    }

    /*
     * Test 2.50 Test remove of one element with empty map
     */
    @Test
    public void shouldReturnNullNoValueMappingToRemove() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        assertNull(emptyMap.removeValue("aaa"));
    }

    /*
     * Test 2.51 Test remove of one element present in nonempty map
     */
    @Test
    public void shouldReturnKeyOfMappingRemoved() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int prev_size = map.size();
        assertEquals(1, map.removeValue("aaa"));
        assertEquals(prev_size - 1, map.size());
    }

    /*
     * Test 2.52 Test remove of one element not present in nonempty map
     */
    @Test
    public void shouldReturnNullKeyToRemoveNotMapped() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int prev_size = map.size();
        assertNull(map.removeValue("wrong"));
        assertEquals(prev_size, map.size());
    }

    /*
     * Test 2.53: Test remove of one element with key of invalid type
     */
    @Test(expected = ClassCastException.class)
    public void shouldFailThrowClassCastException_removeValue() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int[] array = new int[5];
        map.removeValue(array);
    }

    /*
     * Test 2.54: Test remove of one element with null key
     */
    @Test(expected = NullPointerException.class)
    public void shouldFailThrowNullPointerException_removeValue() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        map.removeValue(null);
    }

    /*
     * Test 2.55: Test firstKey on empty input
     */
    @Test(expected = NoSuchElementException.class)
    public void shouldFailEmptyFirstKey() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        emptyMap.firstKey();
    }

    /*
     * Test 2.56: Test firstKey on single element input
     */
    @Test
    public void shouldReturnOneElementFirstKey() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        assertEquals(1, map.firstKey());
    }

    /*
     * Test 2.57: Test firstKey on arbitrary size input
     */
    @Test
    public void shouldReturnFirstKey() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertEquals(1, map.firstKey());
    }

    /*
     * Test 2.58: Test lastKey on empty input
     */
    @Test(expected = NoSuchElementException.class)
    public void shouldFailEmptyLastKey() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        emptyMap.lastKey();
    }

    /*
     * Test 2.59: Test lastKey on single element input
     */
    @Test
    public void shouldReturnOneElementLastKey() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        assertEquals(1, map.lastKey());
    }

    /*
     * Test 2.60: Test lastKey on arbitrary size input
     */
    @Test
    public void shouldReturnLastKey() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertEquals(7, map.lastKey());
    }

    /*
     * Test 2.61: Test nextKey on empty input
     */
    @Test
    public void shouldReturnEmptyNextKey() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        assertEquals(null, emptyMap.nextKey(1));
    }

    /*
     * Test 2.62: Test nextKey(key) on empty input with null key
     */
    @Test(expected = NullPointerException.class)
    public void shouldFailReturnEmptyNextKey() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        emptyMap.nextKey(null);
    }

    /*
     * Test 2.63: Test nextKey on single element input
     */
    @Test
    public void shouldReturnOneElementNextKey() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        assertEquals(null, map.nextKey(1));
    }

    /*
     * Test 2.64: Test nextKey on arbitrary size input
     */
    @Test
    public void shouldReturnNextKey() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertEquals(2, map.nextKey(1));
    }

    /*
     * Test 2.65: Test previousKey on empty input
     */
    @Test
    public void shouldReturnEmptyPreviousKey() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        assertNull(emptyMap.previousKey(1));

    }

    /*
     * Test 2.66: Test previousKey on empty input with null key
     */
    @Test(expected = NullPointerException.class)
    public void shouldFailEmptyPreviousKey() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        emptyMap.previousKey(null);

    }

    /*
     * Test 2.67: Test previousKey on single element input
     */
    @Test
    public void shouldReturnOneElementPreviousKey() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        assertEquals(null, map.previousKey(1));
    }

    /*
     * Test 2.68: Test previousKey on arbitrary size input
     */
    @Test
    public void shouldReturnPreviousKey() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertEquals(1, map.previousKey(2));
    }

    /*
     * Test 2.69: Test keySet on empty input
     */
    @Test
    public void shouldReturnEmptyKeySet() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        int[] expected = {};
        int[] result = new int[emptyMap.keySet().size()];
        int index = 0;
        for (Object i : emptyMap.keySet()) {
            result[index++] = (Integer) i;
        }
        assertArrayEquals(expected, result);
    }

    /*
     * Test 2.70: Test keySet on single element input
     */
    @Test
    public void shouldReturnOneElementKeySet() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        int[] expected = {1};
        int[] result = new int[map.keySet().size()];
        int index = 0;
        for (Object i : map.keySet()) {
            result[index++] = (Integer) i;
        }
        assertArrayEquals(expected, result);
    }

    /*
     * Test 2.71: Test keySet on arbitrary size input
     */
    @Test
    public void shouldReturnKeySet() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int[] expected = {1, 2, 3, 4, 5, 6, 7};
        int[] result = new int[map.keySet().size()];
        int index = 0;
        for (Object i : map.keySet()) {
            result[index++] = (Integer) i;
        }
        assertArrayEquals(expected, result);
    }

    /*
     * Test 2.72: Test values on empty input
     */
    @Test
    public void shouldReturnEmptyValues() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        String[] expected = {};
        String[] result = new String[emptyMap.values().size()];
        int index = 0;
        for (Object i : emptyMap.values()) {
            result[index++] = (String) i;
        }
        assertArrayEquals(expected, result);
    }

    /*
     * Test 2.73: Test values on single element input
     */
    @Test
    public void shouldReturnOneElementValues() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        String[] expected = {"aaa"};
        String[] result = new String[map.values().size()];
        int index = 0;
        for (Object i : map.values()) {
            result[index++] = (String) i;
        }
        assertArrayEquals(expected, result);
    }

    /*
     * Test 2.74: Test values on arbitrary size input
     */
    @Test
    public void shouldReturnValues() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        String[] expected = {"aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg"};
        String[] result = new String[map.values().size()];
        int index = 0;
        for (Object i : map.values()) {
            result[index++] = (String) i;
        }
        assertArrayEquals(expected, result);
    }


    /*
     * Test 2.75: Test inverseBidiMap on empty input
     */
    @Test
    public void shouldReturnEmptyInverseBidiMap() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        TreeBidiMap expected = new TreeBidiMap(new HashedMap());
        assertEquals(expected, emptyMap.inverseBidiMap());
    }

    /*
     * Test 2.76: Test inverseBidiMap on single element input
     */
    @Test
    public void shouldReturnOneElementInverseBidiMap() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        TreeBidiMap expected = new TreeBidiMap(new HashedMap());
        expected.put("aaa", 1);

        assertEquals(expected, map.inverseBidiMap());
    }

    /*
     * Test 2.77: Test inverseBidiMap on arbitrary size input
     */
    @Test
    public void shouldReturnInverseBidiMap() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        TreeBidiMap expected = new TreeBidiMap(new HashedMap());
        expected.put("aaa", 1);
        expected.put("bbb", 2);
        expected.put("ccc", 3);
        expected.put("ddd", 4);
        expected.put("eee", 5);
        expected.put("fff", 6);
        expected.put("ggg", 7);
        assertEquals(expected, map.inverseBidiMap());
    }

    /*
     * Test 2.78: Test equals for empty input
     */
    @Test
    public void shouldReturnEmptyEquals() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        TreeBidiMap expected = new TreeBidiMap(new HashedMap());
        assertEquals(true, emptyMap.equals(expected));
    }

    /*
     * Test 2.79: Test equals for single element input
     */
    @Test
    public void shouldReturnOneElementEquals() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        TreeBidiMap expected = new TreeBidiMap(new HashedMap());
        expected.put(1, "aaa");
        assertEquals(true, map.equals(expected));
    }

    /*
     * Test 2.80: Test equals for arbitrary size input
     */
    @Test
    public void shouldReturnEquals() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        TreeBidiMap expected = new TreeBidiMap(hMap);
        assertEquals(true, map.equals(expected));
    }

    /*
     * Test 2.81: Test hashCode for empty input
     */
    @Test
    public void shouldReturnEmptyHashCode() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        assertEquals(0, emptyMap.hashCode());
    }

    /*
     * Test 2.82: Test hashCode for single element input
     */
    @Test
    public void shouldReturnOneElementHashCode() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        TreeBidiMap expected = new TreeBidiMap(new HashedMap());
        expected.put(1, "aaa");
        assertEquals(expected.hashCode(), map.hashCode());
    }

    /*
     * Test 2.83: Test hashCode for arbitrary size input
     */
    @Test
    public void shouldReturnHashCode() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        TreeBidiMap expected = new TreeBidiMap(hMap);
        assertEquals(expected.hashCode(), map.hashCode());
    }

    /*
     * Test 2.84: Test toString for empty input
     */
    @Test
    public void shouldReturnEmptyToString() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        String expected = "{}";
        assertEquals(expected, emptyMap.toString());
    }

    /*
     * Test 2.85: Test toString for single element input
     */
    @Test
    public void shouldReturnOneElementToString() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        String expected = "{1=aaa}";
        assertEquals(expected, map.toString());
    }

    /*
     * Test 2.86: Test toString for arbitrary size input
     */
    @Test
    public void shouldReturnToString() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        String expected = "{1=aaa, 2=bbb, 3=ccc, 4=ddd, 5=eee, 6=fff, 7=ggg}";
        assertEquals(expected, map.toString());
    }

    /*
     * Test 2.87: Test entrySet for empty input
     */
    @Test
    public void shouldReturnEmptyEntrySet() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        TreeBidiMap expected = new TreeBidiMap(new HashedMap());
        assertEquals(expected.entrySet(), emptyMap.entrySet());
    }

    /*
     * Test 2.88: Test entrySet for single element input
     */
    @Test
    public void shouldReturnOneElementEntrySet() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        TreeBidiMap expected = new TreeBidiMap(new HashedMap());
        expected.put(1, "aaa");
        assertEquals(expected.entrySet(), map.entrySet());
    }

    /*
     * Test 2.89: Test entrySet for arbitrary size input
     */
    @Test
    public void shouldReturnEntrySet() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        TreeBidiMap expected = new TreeBidiMap(hMap);
        assertEquals(expected.entrySet(), map.entrySet());
    }

    /*
     * Test 2.90: MapIterator test on empty set
     */
    @Test
    public void shouldReturnFalseOnEmptySet() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        MapIterator it = map.mapIterator();
        assertFalse(it.hasNext());

    }

    /*
     * Test 2.91: MapIterator test on single element
     */
    @Test
    public void shouldReturnTrueOneElementInSet() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "test");
        MapIterator it = map.mapIterator();
        assertTrue(it.hasNext());

    }

    /*
     * Test 2.92:  MapIterator test data return type with populated set 
     */
    @Test
    public void shouldReturnTrueOnIteratorTypeFullSet() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        MapIterator it = map.mapIterator();
        assertTrue(it instanceof OrderedMapIterator);
    }

    /*
     * Test 2.93:  MapIterator test data return type with empty set 
     */
    @Test
    public void shouldReturnTrueOnIteratorTypeEmptySet() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        MapIterator it = map.mapIterator();
        assertTrue(it instanceof EmptyOrderedMapIterator);
    }

    /*
     * Test 2.94:  InverseBidiMap size test
     */
    @Test
    public void shouldReturnProperInverseSize() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertEquals(7, inverse.size());

    }

    /*
     * Test 2.95: InverseBidiMap element order check 
     */
    @Test
    public void shouldReturnProperInverseElementOrder() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put("A", "B");
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertEquals("A", inverse.get("B"));

    }

    /*
     * Test 2.96: InverseBidiMap with empty set 
     */
    @Test
    public void shouldReturnProperInverseEmptySize() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertEquals(0, inverse.size());

    }

    /*
     * Test 2.97: InverseBidiMap with one element 
     */
    @Test
    public void shouldReturnProperInverseOneElementSize() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put("A", "B");
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertEquals(1, inverse.size());

    }

    /*
     * Test 2.98: InverseBidi Map with clear 
     */
    @Test
    public void shouldClearInverseBidiMap() {
        TreeBidiMap clearableMap = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = clearableMap.inverseBidiMap();
        assertEquals(7, inverse.size());
        inverse.clear();
        assertEquals(0, inverse.size());
    }

    /*
     * Test 2.99: Test for containsKey(key) in inverse BidiMap.
      */
    @Test
    public void shouldReturnKeyIsContainedFirstInverse() {
        TreeBidiMap clearableMap = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = clearableMap.inverseBidiMap();
        assertTrue(inverse.containsKey("aaa"));
    }

    /*
     * Test 2.100: Test for containsKey(key) in inverse BidiMap.
      */
    @Test
    public void shouldReturnKeyIsNotContainedInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertFalse(inverse.containsKey("zzz"));
    }

    /*
     * Test 2.101: Test for containsValue(value) showing the value is contained in inverse bidimap.
     
     */
    @Test
    public void shouldReturnValueIsContainedFirstInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertTrue(inverse.containsValue(1));
    }

    /*
     * Test 2.102: Test for containsValue(value) showing the value is contained in inverse bidimap.
     */
    @Test
    public void shouldReturnValueIsContainedLastInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertTrue(inverse.containsValue(7));
    }

    /*
     * Test 2.103: Test for containsValue(value) showing the value is not contained in inverse bidimap.
     */
    @Test
    public void shouldReturnValueIsNotContainedInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertFalse(inverse.containsValue(60));
    }

    /*
     * Test 2.104: Test for containsValue(value) with null input in inverse bidimap.
     */
    @Test(expected = NullPointerException.class)
    public void shouldFailContainsValueWithNullPointerExceptionInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        inverse.containsValue(null);
    }

    /*
     * Test 2.105: Test for isEmpty() with map size = 0 for inverseBidimap.
     */
    @Test
    public void shouldReturnMapIsEmptyInverse() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        OrderedBidiMap inverse = emptyMap.inverseBidiMap();
        assertTrue(inverse.isEmpty());
    }

    /*
     * Test 2.106: Test for isEmpty() with map size != 0 for inverseBidimap.
     */
    @Test
    public void shouldReturnMapIsNotEmptyInverse() {
        TreeBidiMap nonEmptyMap = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = nonEmptyMap.inverseBidiMap();
        assertFalse(inverse.isEmpty());
    }

    /*
     * Test 2.107: Test for new EntrySet with empty map for inverseBidimap
     */
    @Test
    public void shouldReturnNewEntrySetInverse() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        OrderedBidiMap inverse = emptyMap.inverseBidiMap();
        Set<TreeBidiMap.Entry> returnedSet = inverse.entrySet();
        assertNotNull(returnedSet);
        assertEquals(0, returnedSet.size());

    }

    /*
     * Test 2.108: Test for retrieval of EntrySet with nonempty map for inverseBididmap
     */
    @Test
    public void shouldReturnEntryViewInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        Set<TreeBidiMap.Entry> returnedSet = inverse.entrySet();
        assertNotNull(returnedSet);
        assertEquals(7, returnedSet.size());
    }

    /*
     * Test 2.109: Test firstKey on empty input for inverseBididmap
     */
    @Test(expected = NoSuchElementException.class)
    public void shouldFailEmptyFirstKeyInverse() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        OrderedBidiMap inverse = emptyMap.inverseBidiMap();
        inverse.firstKey();
    }

    /*
     * Test 2.110: Test firstKey on single element input for inverseBididmap
     */
    @Test
    public void shouldReturnOneElementFirstKeyInverse() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertEquals("aaa", inverse.firstKey());
    }

    /*
     * Test 2.111: Test firstKey on arbitrary size input for inverseBididmap
     */
    @Test
    public void shouldReturnFirstKeyInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertEquals("aaa", inverse.firstKey());
    }

    /*
     * Test 2.112: Test get(key) with empty map for inverseBididmap
     */
    @Test
    public void shouldReturnNullKeyNotPresentTreeEmptyInverse() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        OrderedBidiMap inverse = emptyMap.inverseBidiMap();
        assertNull(inverse.get(1));
    }

    /*
     * Test 2.113: Test get(key) with value present in nonempty map for inverseBididmap
     */
    @Test
    public void shouldReturnValueFromKeyInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertEquals(1, inverse.get("aaa"));
    }

    /*
     * Test 2.114: Test get(key) with value not present in nonempty map for inverseBididmap
     */
    @Test
    public void shouldReturnNullKeyNotPresentInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertNull(inverse.get("xxx"));
    }

    /*
     * Test 2.115: Test get(key) with non comparable key for inverseBididmap
     */
    @Test(expected = ClassCastException.class)
    public void shouldThrowClassCastExceptionKeyWrongTypeInverse_get() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        int[] array = new int[5];
        inverse.get(array);
    }

    /*
     * Test 2.116: Test get(key) with null key for inverseBididmap
     */
    @Test(expected = NullPointerException.class)
    public void shouldThrowClassCastExceptionKeyNullInverse_get() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        inverse.get(null);
    }


    /*
     * Test 2.117: Test hashCode for empty input for inverseBididmap
     */
    @Test
    public void shouldReturnEmptyHashCodeInverse() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        OrderedBidiMap inverse = emptyMap.inverseBidiMap();
        assertEquals(0, inverse.hashCode());
    }

    /*
     * Test 2.118: Test hashCode for single element input for inverseBididmap
     */
    @Test
    public void shouldReturnOneElementHashCodeInverse() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        OrderedBidiMap inverse = map.inverseBidiMap();
        TreeBidiMap expected = new TreeBidiMap(new HashedMap());
        expected.put(1, "aaa");
        OrderedBidiMap inverseExpected = expected.inverseBidiMap();
        assertEquals(inverseExpected.hashCode(), inverse.hashCode());
    }

    /*
     * Test 2.119: Test hashCode for arbitrary size input for inverseBididmap
     */
    @Test
    public void shouldReturnHashCodeInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        TreeBidiMap expected = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        OrderedBidiMap inverseExpected = expected.inverseBidiMap();
        assertEquals(inverseExpected.hashCode(), inverse.hashCode());
    }

    /*
     * Test 2.120: Test lastKey on empty input for inverseBididmap
     */
    @Test(expected = NoSuchElementException.class)
    public void shouldFailEmptyLastKeyInverse() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        OrderedBidiMap inverse = emptyMap.inverseBidiMap();
        inverse.lastKey();
    }

    /*
     * Test 2.121: Test lastKey on single element input for inverseBididmap
     */
    @Test
    public void shouldReturnOneElementLastKeyInverse() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertEquals("aaa", inverse.lastKey());
    }

    /*
     * Test 2.122: Test lastKey on arbitrary size input for inverseBididmap 
     */
    @Test
    public void shouldReturnLastKeyInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertEquals("ggg", inverse.lastKey());
    }

    /*
     * Test 2.123: Test nextKey on empty input for inverseBididmap 
     */
    @Test
    public void shouldReturnEmptyNextKeyInverse() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        OrderedBidiMap inverse = emptyMap.inverseBidiMap();
        assertEquals(null, inverse.nextKey(1));
    }

    /*
     * Test 2.124: Test nextKey(key) on empty input with null key for inverseBididmap
     */
    @Test(expected = NullPointerException.class)
    public void shouldFailReturnEmptyNextKeyInverse() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        OrderedBidiMap inverse = emptyMap.inverseBidiMap();
        emptyMap.nextKey(null);
    }

    /*
     * Test 2.125: Test nextKey on single element input for inverseBididmap
     */
    @Test
    public void shouldReturnOneElementNextKeyInverse() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertEquals(null, inverse.nextKey("aaa"));
    }

    /*
     * Test 2.126: Test nextKey on arbitrary size input for inverseBididmap
     */
    @Test
    public void shouldReturnNextKeyInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertEquals("bbb", inverse.nextKey("aaa"));
    }

    /*
     * Test 2.127: Test previousKey on empty input for inverseBididmap
     */
    @Test
    public void shouldReturnEmptyPreviousKeyInverse() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        OrderedBidiMap inverse = emptyMap.inverseBidiMap();
        assertEquals(null, inverse.previousKey("aaa"));

    }

    /*
     * Test 2.128: Test previousKey on empty input with null key for inverseBididmap
     */
    @Test(expected = NullPointerException.class)
    public void shouldFailEmptyPreviousKeyInverse() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        OrderedBidiMap inverse = emptyMap.inverseBidiMap();
        inverse.previousKey(null);

    }

    /*
     * Test 2.129: Test previousKey on single element input for inverseBididmap
     */
    @Test
    public void shouldReturnOneElementPreviousKeyInverse() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put(1, "aaa");
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertEquals(null, inverse.previousKey("aaa"));
    }

    /*
     * Test 2.130: Test previousKey on arbitrary size input for inverseBididmap
     */
    @Test
    public void shouldReturnPreviousKeyInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertEquals("aaa", inverse.previousKey("bbb"));
    }

    /*
     * Test 2.131: Test put of one element in empty TreeBidiMap for inverseBididmap
     */
    @Test
    public void shouldIncreaseSizeToOneInverse() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        emptyMap.put(1, "new");
        OrderedBidiMap inverse = emptyMap.inverseBidiMap();
        assertFalse(inverse.isEmpty());
        assertTrue(inverse.containsKey("new"));
        assertTrue(inverse.containsValue(1));
        assertEquals(1, inverse.size());
    }

    /*
     * Test 2.132: Test put of one element in nonempty TreeBidiMap for inverseBididmap
     */
    @Test
    public void shouldIncreaseSizeByOneInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int prev_size = map.size();
        map.put(9, "new");
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertFalse(inverse.isEmpty());
        assertTrue(inverse.containsKey("new"));
        assertTrue(inverse.containsValue(9));
        assertEquals(prev_size + 1, inverse.size());
    }

    /*
     * Test 2.133: Test put of one element with existing key in nonempty TreeBidiMap for inverseBididmap
     */
    @Test
    public void shouldReplaceExistingValueInverse() {
        TreeBidiMap map = new TreeBidiMap(hMap);
        int prev_size = map.size();
        map.put(1, "new");
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertFalse(inverse.isEmpty());
        assertTrue(inverse.containsKey("new"));
        assertTrue(inverse.containsValue(1));
        assertEquals(prev_size, inverse.size());
        assertEquals(1, inverse.get("new"));
        assertEquals("new", inverse.getKey(1));
    }


    /*
     * Test 2.134: Test put of one element with key of invalid type for inverseBididmap
     */
    @Test(expected = ClassCastException.class)
    public void shouldFailThrowClassCastExceptionInverse_put() {
        TreeBidiMap map = new TreeBidiMap();
        OrderedBidiMap inverse = map.inverseBidiMap();
        int[] array = new int[5];
        inverse.put(array, "new");
    }

    /*
     * Test 2.135: Test put of one element with null key for inverseBididmap
     */
    @Test
    public void shouldFailThrowNullPointerExceptionInverse_put() {
        TreeBidiMap map = new TreeBidiMap();
        OrderedBidiMap inverse = map.inverseBidiMap();
        try {
            inverse.put(null, "new");
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
            return;
        }
        fail();
    }

    /*
     * Test 2.136: Test for toString() with map size = 0 for inverseBididmap
     */
    @Test
    public void shouldReturnEmptyBracesAsStringInverse() {
        TreeBidiMap emptyMap = new TreeBidiMap();
        OrderedBidiMap inverse = emptyMap.inverseBidiMap();
        assertEquals("{}", inverse.toString());
    }

    /*
     * Test 2.137: Test for toString() with map size != 0 for inverseBididmap
     */
    @Test
    public void shouldReturnMapInBracesAsStringInverse() {
        TreeBidiMap nonEmptyMap = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = nonEmptyMap.inverseBidiMap();
        assertEquals("{aaa=1, bbb=2, ccc=3, ddd=4, eee=5, fff=6, ggg=7}", inverse.toString());
    }

    /*
     * Test 2.138: Test values on empty input for inverseBididmap
     */
    @Test
    public void shouldReturnEmptyValuesInverse() {
        TreeBidiMap emptyMap = new TreeBidiMap(new HashedMap());
        OrderedBidiMap inverse = emptyMap.inverseBidiMap();
        String[] expected = {};
        String[] result = new String[inverse.values().size()];
        int index = 0;
        for (Object i : inverse.values()) {
            result[index++] = (String) i;
        }
        assertArrayEquals(expected, result);
    }

    /*
     * Test 2.139: Test values on single element input for inverseBididmap
     */
    @Test
    public void shouldReturnOneElementValuesInverse() {
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        map.put("bbb", "aaa");
        OrderedBidiMap inverse = map.inverseBidiMap();
        String[] expected = {"bbb"};
        String[] result = new String[inverse.values().size()];
        int index = 0;
        for (Object i : inverse.values()) {
            result[index++] = (String) i;
        }
        assertArrayEquals(expected, result);
    }

    /**
     * Test 2.140: Test the while loop specified on lines 668 to 671 of the getSmaller condition (i.e. covering the case that
     * a right child of the tree is also our ancestor)T
     */
    @Test
    public void shouldReturnElementWhoseRightChildIsAncestorOfNode(){
        TreeBidiMap map = new TreeBidiMap(hMap);
        assertEquals(4, map.previousKey(5));
    }

    /**
     * Test 2.141: Test to check that we can remove a node from a tree with only one node. The replacement for the node
     * becomes null.
     */
    @Test
    public void shouldRemoveKeyWithNullReplacement(){
        HashedMap tempMap = new HashedMap();
        tempMap.put(1, 1);
        TreeBidiMap map = new TreeBidiMap(tempMap);

        map.remove(1);
        assertNull(map.get(1));
    }

    /**
     * Test 2.142: Test to check that we can remove a node from a tree with two nodes, one of which is a parent, and the other
     * one a child. We check that the new root becomes the child, as specified by condition on line 981
     */
    @Test
    public void shouldRemoveKeyWithoutParentButWithOtherChild(){
        HashedMap tempMap = new HashedMap();
        tempMap.put(1, 3);
        tempMap.put(2, 2);
        TreeBidiMap map = new TreeBidiMap(tempMap);

        map.remove(2);
        assertNull(map.get(2));
    }

    /**
     * Test 2.143: Test to check that we can return an empty reverse map iterator if the
     */
    @Test
    public void shouldReturnEmptyInverseMapIterator(){
        //tests this for inverse
        TreeBidiMap map = new TreeBidiMap(new HashedMap());
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertFalse(inverse.mapIterator().hasNext());
    }

    /**
     * Test 2.144: Test to check that we can return a reverse map iterator
     */
    @Test
    public void shouldReturnInverseMapIterator(){
        //tests this for inverse
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        assertTrue(inverse.mapIterator().hasNext());
    }

    /**
     * Test 2.145: Test to check that we cannot call getKey() on the iterator without moving its pointer first
     */
    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWithLastReturnGetKeyNodeInverseMapIterator(){
        //tests this for inverse
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        inverse.mapIterator().getKey();
    }

    /**
     * Test 2.146: Test to check that we cannot call getValue() on the iterator without moving its pointer first
     */
    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWithLastReturnGetValueNodeInverseMapIterator(){
        //tests this for inverse
        TreeBidiMap map = new TreeBidiMap(hMap);
        OrderedBidiMap inverse = map.inverseBidiMap();
        inverse.mapIterator().getValue();
    }


    /**
     * Test 2.147: Test to check that we can put a whole map inside of an InverseBidiMap
     */
    @Test
    public void shouldPutAllInsideOfInverseTreeBidiMap(){
        TreeBidiMap map = new TreeBidiMap(new HashMap());
        OrderedBidiMap inverse = map.inverseBidiMap();
        inverse.putAll(hMap);

        assertEquals(hMap.size(), inverse.size());
    }

    /**
     * Test 2.148: Test to check that we can return an empty keyset of an InverseTreeBidiMap
     */
    @Test
    public void shouldReturnValueViewKeysetInverseTreeBidiMap(){
        TreeBidiMap map = new TreeBidiMap(new HashMap());
        OrderedBidiMap inverse = map.inverseBidiMap();
        Set tempSet = inverse.keySet();

        assertTrue(tempSet.isEmpty());
    }
}