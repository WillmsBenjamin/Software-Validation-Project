package ecse429.group10.pp3;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.CatchAndRethrowClosure;
import org.apache.commons.collections4.functors.ChainedClosure;
import org.apache.commons.collections4.functors.NOPClosure;
import org.apache.commons.collections4.functors.SwitchClosure;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by alex on 11/26/17.
 */
public class ChainedClosureTest {



    SwitchClosure switchClosure;
    ChainedClosure chainedClosure;
    Closure catchAndRethrowClosure;
    Closure[] closuresArray;
    ArrayList<Closure<String>> closuresList  = new ArrayList<>();

    //region Rules
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    //endregion

    //region Test Configuration
    @Before
    public void setUp() throws Exception {
        Closure<String> closure1 = (String x) -> System.out.println(x + " - CLOSURE 1");
        Closure<String> closure2 = (String x) -> System.out.println(x + " - CLOSURE 2");
        Predicate<String> predicate1 = (String x) -> x.length() < 25;
        Predicate<String> predicate2 = (String x) -> x.length() > 10;
        Predicate[] predicates = {predicate1, predicate2};
        Closure[] bufferClosures = {closure1, closure2};

        switchClosure = new SwitchClosure(predicates, bufferClosures, null);
        chainedClosure = new ChainedClosure(bufferClosures);
        catchAndRethrowClosure = generateEmptyExceptionClosure(); // stub for abstract class of catch and rethrow closure

        closuresArray = new Closure[]{switchClosure, chainedClosure, catchAndRethrowClosure};

        closuresList.add(switchClosure);
        closuresList.add(chainedClosure);
        closuresList.add(catchAndRethrowClosure);
    }

    @After
    public void tearDown() throws Exception {
        closuresArray = null;
        closuresList.clear();
    }
    //endregion

    //region Closure Tests

    /**
     * Test 1.01 Test if closures are properly returned by the getClosures method by comparing with
     * predefined array
     */
    @Test
    public void shouldReturnClosures(){
        Closure<String> testChainedClosure = ChainedClosure.chainedClosure(switchClosure, chainedClosure, catchAndRethrowClosure);
        Closure[] actualClosures = ((ChainedClosure<String>)testChainedClosure).getClosures();

        assertArrayEquals(closuresArray, actualClosures);
    }

    /**
     * Test 1.02 Test that the factory method which allows for the creation of a ChainedClosure by specifying multiple Closures works.
     */
    @Test
    public void shouldReturnNewChainedClosureWithMultipleClosuresFactoryOverload(){
        Closure<String> testChainedClosure = ChainedClosure.chainedClosure(switchClosure, chainedClosure, catchAndRethrowClosure);

        assertNotEquals(NOPClosure.nopClosure(), testChainedClosure);
        assertArrayEquals(closuresArray, ((ChainedClosure<String>)testChainedClosure).getClosures());
    }

    /**
     * Test 1.03 Test that we can create a ChainedClosure with no arguments passed to the factory method
     */
    @Test
    public void shouldReturnEmptyClosureWithMultipleClosuresFactoryOverload(){
        Closure<String> testChainedClosure = ChainedClosure.chainedClosure();

        assertEquals(NOPClosure.nopClosure(), testChainedClosure);
    }

    /**
     * Test 1.04 Test that we can pass a ChainedClosure as a collection type to the factory method
     */
    @Test
    public void shouldReturnNewChainedClosureWithCollectionFactoryOverload() {
        Closure<String> testChainedClosure = ChainedClosure.chainedClosure(closuresList);

        assertEquals(closuresList, Arrays.asList(((ChainedClosure<String>)testChainedClosure).getClosures()));
    }

    /**
     * Test 1.05 Test that we cannot pass a non-instantiated collection to the factory method
     */
    @Test(expected = NullPointerException.class)
    public void shouldFailWithNullPointerExceptionWithNullClosureWithCollectionFactoryOverload() {
        ArrayList nullArrayList = null;
        Closure<String> testChainedClosure = ChainedClosure.chainedClosure(nullArrayList);
    }

    /**
     * Test 1.06 Test that we can pass an empty collection to the factory method
     */
    @Test
    public void shouldReturnEmptyClosureWithCollectionFactoryOverload(){
        ArrayList nullArrayList = new ArrayList();
        Closure<String> testChainedClosure = ChainedClosure.chainedClosure(nullArrayList);

        assertEquals(NOPClosure.nopClosure(), testChainedClosure);
    }

    /**
     * Test 1.07 Test that the closures execute properly
     */
    @Test
    public void shouldExecuteClosure(){
        Closure<String> testChainedClosure = ChainedClosure.chainedClosure(closuresList);
        testChainedClosure.execute("TEST-CLOSURES");

        assertEquals("TEST-CLOSURES - CLOSURE 1\r\nTEST-CLOSURES - CLOSURE 1\r\nTEST-CLOSURES - CLOSURE 2\r\n", systemOutRule.getLog());
    }

    //endregion

    //region Stubs
    private static <T> Closure<T> generateEmptyExceptionClosure() {
        return new CatchAndRethrowClosure<T>() {

            @Override
            protected void executeAndThrow(final T input) {
            }
        };
    }
    //endregion
}
