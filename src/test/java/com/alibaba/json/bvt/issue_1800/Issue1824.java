package com.alibaba.json.bvt.issue_1800;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;
import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Issue1824 extends TestCase {
    private Calendar calendar;
    private TimeZone originTimeZone;
    private Locale originLocale;

    @Override
    public void setUp() throws Exception {
        originTimeZone = JSON.defaultTimeZone;
        originLocale = JSON.defaultLocale;

        TimeZone tempTimeZone = TimeZone.getTimeZone("Asia/Shanghai");
        Locale tempLocale = Locale.CHINA;

        calendar = Calendar.getInstance(tempTimeZone, tempLocale);
        calendar.set(Calendar.MILLISECOND, 0);

        JSON.defaultTimeZone = tempTimeZone;
        JSON.defaultLocale = tempLocale;
    }

    @Override
    public void tearDown() {
        JSON.defaultTimeZone = originTimeZone;
        JSON.defaultLocale = originLocale;
    }

    public void test_for_case1() {
        calendar.set(2018, Calendar.MAY, 2, 0, 0, 0);
        Date date = calendar.getTime();

        String dateStr = JSON.toJSONString(date, SerializerFeature.UseISO8601DateFormat);
        assertEquals("\"2018-05-02T00:00:00+08:00\"", dateStr);
    }

    public void test_for_case2() {
        calendar.set(2018, Calendar.MAY, 2, 10, 39, 27);
        Date date = calendar.getTime();

        String dateStr = JSON.toJSONString(date, SerializerFeature.UseISO8601DateFormat);
        assertEquals("\"2018-05-02T10:39:27+08:00\"", dateStr);
    }
}
