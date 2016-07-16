/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Color;

/**
 *
 * @author Marc
 */
public class ImageTest {
    
    public ImageTest() {
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
     * Test of loadImage method, of class Image.
     */
    @Test
    public void testLoadImage() {
        System.out.println("loadImage");
        String name = "input.jpg";
        Image result = Image.loadImage(name);
        assertTrue("An image exists", result != null);
        assertTrue("Image name fits", result.getName() == name);
    }

    /**
     * Test of getName method, of class Image.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Image instance = Image.loadImage("input.jpg");
        String expResult = "input.jpg";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of addFilter method, of class Image.
     */
    @Test
    public void testAddFilter() {
        System.out.println("addFilter");
        String filter = "mono";
        Image instance = Image.loadImage("input.jpg");
        instance.addFilter(filter);
        assertEquals(filter, instance.getFilter(instance.sizeFilter() - 1));
    }

    /**
     * Test of getFilter method, of class Image.
     */
    @Test
    public void testGetFilter() {
        System.out.println("getFilter");
        Image instance = Image.loadImage("input.jpg");
        String expResult = "rot90";
        instance.addFilter(expResult);
        String result = instance.getFilter(instance.sizeFilter() - 1);
        assertEquals(expResult, result);
    }

    /**
     * Test of sizeFilter method, of class Image.
     */
    @Test
    public void testSizeFilter() {
        System.out.println("sizeFilter");
        Image instance = Image.loadImage("input.jpg");
        int expResult = 3;
        instance.addFilter("mono");
        instance.addFilter("rot90");
        instance.addFilter("mono");
        int result = instance.sizeFilter();
        assertEquals(expResult, result);
    }
    
    /**
     * Success test of setPixel method, of class Image.
     */
    @Test
    public void testSetPixelSuccess() {
        System.out.println("setPixel");
        String name = "input.jpg";
        Image instance = Image.loadImage(name);
        instance.setPixel(0, 0, new Color(255, 255, 255));
    }
    
    /**
     * Fail test of setPixel method, of class Image.
     */
    @Test
    public void testSetPixelFail() {
        System.out.println("setPixel");
        String name = "input.jpg";
        Image instance = Image.loadImage(name);
        instance.setPixel(-1, -1, new Color(255, 255, 255));
    }
    
    /**
     * Success test of getPixel method, of class Image.
     */
    @Test
    public void testGetPixelSuccess() {
        System.out.println("getPixel");
        String name = "input.jpg";
        Image instance = Image.loadImage(name);
        instance.getPixel(0, 0);
    }
    
    /**
     * Fail test of getPixel method, of class Image.
     */
    @Test
    public void testGetPixelFail() {
        System.out.println("getPixel");
        String name = "input.jpg";
        Image instance = Image.loadImage(name);
        instance.getPixel(-1, -1);
    }
}
