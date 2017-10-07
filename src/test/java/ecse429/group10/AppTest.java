package ecse429.group10;

import org.apache.commons.collections4.list.*;
import org.apache.commons.collections4.queue.PredicatedQueue;
import org.apache.commons.collections4.queue.TransformedQueue;
import org.junit.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jukito.All;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static junit.framework.Assert.assertEquals;

/**
 * Unit test for simple App.
 */

@RunWith(JukitoRunner.class)
public class AppTest {

    public static class Module extends JukitoModule {

        @Override
        protected void configureTest() {
            bindMany(List.class, TreeList.class, CursorableLinkedList.class);
        }
    }



    @Test
    public void testAdd(@All List list) {

        int previousSize = list.size();
        System.out.println(previousSize);
        list.add(1);
        System.out.println(list.size());
        assertEquals(previousSize + 1, list.size());
    }

    @Test
    public void testRemove(@All List list) {

        list.add(1);
        list.add(2);
        list.add(3);
        int previousSize = list.size();
        list.remove(0);


        assertEquals(2, list.get(0));
    }
}
