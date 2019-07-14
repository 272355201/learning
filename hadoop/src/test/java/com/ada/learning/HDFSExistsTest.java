package com.ada.learning;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.StringTokenizer;

/**
 * Unit test for simple App.
 */
public class HDFSExistsTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testWordCount(){
        StringTokenizer itr = new StringTokenizer("ab\nc\td efgg");
//        while (itr.hasMoreTokens()){
//            System.out.println(itr.nextToken());
//        }

        while (itr.hasMoreElements()){
            System.out.println(itr.nextToken());
        }

    }
}
