/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import java.util.List;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marc
 */
public class CommandTest {
    
    public CommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCommandWord method, of class Command.
     */
    @Test
    public void testGetCommandWord() {
        System.out.println("getCommandWord");
        List<String> commandWords = new ArrayList<>();
        commandWords.add("raspberry");
        commandWords.add("strawberry");
        commandWords.add("blueberry");
        Command instance = new Command(commandWords);
        String expResult = "raspberry";
        String result = instance.getCommandWord();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCommandArgs method, of class Command.
     */
    @Test
    public void testGetCommandArgs() {
        System.out.println("getCommandArgs");
        List<String> commandWords = new ArrayList<>();
        commandWords.add("raspberry");
        commandWords.add("strawberry");
        commandWords.add("blueberry");
        Command instance = new Command(commandWords);
        List<String> expResult = new ArrayList<>();
        expResult.add("strawberry");
        expResult.add("blueberry");
        List<String> result = instance.getCommandArgs();
        assertTrue("getCommandArgs Passed !", expResult.containsAll(result));
    }

    /**
     * Test of isUnknown method, of class Command.
     */
    @Test
    public void testIsUnknown() {
        System.out.println("isUnknown");
        List<String> commandWords = new ArrayList<>();
        commandWords.add("raspberry");
        commandWords.add("strawberry");
        commandWords.add("blueberry");
        Command instance = new Command(commandWords);
        boolean expResult = true;
        boolean result = instance.isUnknown();
        assertEquals(expResult, result);
    }

    /**
     * Test of hasArgs method, of class Command.
     */
    @Test
    public void testHasArgs() {
        System.out.println("hasArgs");
        List<String> commandWords = new ArrayList<>();
        commandWords.add("raspberry");
        commandWords.add("strawberry");
        commandWords.add("blueberry");
        Command instance = new Command(commandWords);
        boolean expResult = true;
        boolean result = instance.hasArgs();
        assertEquals(expResult, result);
    }
    
}
