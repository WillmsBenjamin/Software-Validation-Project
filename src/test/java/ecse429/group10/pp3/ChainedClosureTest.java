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

import java.lang.reflect.Array;
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
        Predicate<String> predicate2 = (String x) -> x.length() < 10;
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

    @Test
    public void shouldReturnClosures(){
        Closure<String> testChainedClosure = ChainedClosure.chainedClosure(switchClosure, chainedClosure, catchAndRethrowClosure);
        Closure[] actualClosures = ((ChainedClosure<String>)testChainedClosure).getClosures();

        assertArrayEquals(closuresArray, actualClosures);
    }


    @Test
    public void shouldReturnNewChainedClosureWithCollectionConstructorOverload() {
        Closure<String> testChainedClosure = ChainedClosure.chainedClosure(closuresList);
    }

    @Test
    public void shouldReturnNewChainedClosureWithMultipleClosuresConstructorOverload(){
        Closure<String> testChainedClosure = ChainedClosure.chainedClosure(switchClosure, chainedClosure, catchAndRethrowClosure);

        assertNotEquals(NOPClosure.nopClosure(), testChainedClosure);
        assertArrayEquals(closuresArray, ((ChainedClosure<String>)testChainedClosure).getClosures());
    }

    @Test
    public void shouldReturnEmptyClosure(){
        Closure<String> testChainedClosure = ChainedClosure.chainedClosure();

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
