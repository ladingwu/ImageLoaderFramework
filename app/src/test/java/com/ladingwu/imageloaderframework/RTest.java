package com.ladingwu.imageloaderframework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by wuzhao on 2018/2/8.
 */

@RunWith(RobolectricTestRunner.class)
public class RTest {

    @Test
    public void test() {
        MainActivity mainActivity= Robolectric.setupActivity(MainActivity.class);
        mainActivity.findViewById(R.id.btn1).performClick();
    }
}
