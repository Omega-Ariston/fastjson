package com.alibaba.json.bvt.issue_1600;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;

import java.util.Calendar;

public class Issue1685 extends TestCase {
    public void test_for_issue() throws Exception {
        test_for_calendar(-1, "+0800");

        test_for_calendar(-1, "");

        test_for_calendar(-62135596800000L, "+0800");

        test_for_calendar(1500000000000L, "+0800");

        test_for_calendar(1500000000000L, "");
    }

    public void test_for_calendar(long mills, String timeZone) {
        String s1 = "\"\\/Date(" + mills + timeZone + ")\\/\"";
        Calendar c = JSON.parseObject(s1, Calendar.class);

        Calendar calendar = Calendar.getInstance(JSON.defaultTimeZone, JSON.defaultLocale);
        calendar.setTimeInMillis(mills);

        assertEquals(JSON.toJSONString(calendar), JSON.toJSONString(c));
    }
}