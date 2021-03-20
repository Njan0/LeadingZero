/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njan.leadingzero;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
 *
 * @author Jan
 */
public class LeadingZeroGeneratorTest {
    
    public LeadingZeroGeneratorTest() {
    }
    
    public static void setUpClass() {
    }
    
    public static void tearDownClass() {
    }

    /**
     * Test of Generate method, of class LeadingZeroGenerator.
     */
    @Test
    public void testGenerate() {
        System.out.println("Generate");
        String[] list = {"8", "10", "13",
                         "A8C", "A10B", "A13D",
                         "A7C8", "A7B10", "A13D10"};
        String[] expResult = {"08", "10", "13",
                              "A08C", "A10B", "A13D",
                              "A07C8", "A07B10", "A13D10"};
        String[] result = LeadingZeroGenerator.Generate(list);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of Split method, of class LeadingZeroGenerator.
     */
    @Test
    public void testSplit() {
        System.out.println("Split");
        String[] list = {"10", "Test", "1Test2", "Test3Four"};
        String[][] expStrings = {{"", ""}, {"Test"}, {"", "Test", ""}, {"Test", "Four"}};
        Integer[][] expSplitters = {{10}, {}, {1, 2}, {3}};
        
        LeadingZeroGenerator.SplittedString[] result = LeadingZeroGenerator.Split(list);
        assertEquals(list.length, result.length);
        
        for (int i = 0; i < list.length; ++i) {
            assertArrayEquals(expStrings[i], result[i].strings.toArray());
            assertArrayEquals(expSplitters[i], result[i].splitters.toArray()); 
        }
    }
    
}
