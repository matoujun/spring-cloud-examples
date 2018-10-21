package org.matoujun.cloud.api.util;

import org.junit.Test;

/**
 * @author matoujun

 */
public class CommonTest {

    @Test
    public void replaceTest(){
        StringBuilder stringBuilder = new StringBuilder("?,?,");
        stringBuilder.deleteCharAt(3).append(")");
        System.out.println(stringBuilder.toString());
        System.out.println("end");
    }
}
