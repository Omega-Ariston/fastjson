package com.alibaba.json.bvt.jdk8;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Locale;
import java.util.TimeZone;

/**
 * TestCase for Issue #1599
 */
public class LocalDateTimeTest6 extends TestCase {
    private TimeZone originTimeZone;
    private Locale originLocale;
    private VO vo1;
    private VO vo2;
    private VOAnnotation voa;

    @Override
    public void setUp() {
        originTimeZone = JSON.defaultTimeZone;
        originLocale = JSON.defaultLocale;

        JSON.defaultTimeZone = TimeZone.getTimeZone("Asia/Shanghai");
        JSON.defaultLocale = Locale.CHINA;

        LocalDateTime dateTime1 = LocalDateTime.of(2019, Month.SEPTEMBER, 10, 11, 24, 35, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(2019, Month.SEPTEMBER, 10, 11, 24, 35, 56789);

        vo1 = new VO();
        vo1.setDateTime(dateTime1);

        vo2 = new VO();
        vo2.setDateTime(dateTime2);

        voa = new VOAnnotation();
        voa.setDateTime(dateTime1);
    }

    @Override
    public void tearDown() {
        JSON.defaultTimeZone = originTimeZone;
        JSON.defaultLocale = originLocale;
    }

    public void testForLong() {
        assertEquals("{\"dateTime\":1568085875000}", JSON.toJSONString(vo1));
        assertEquals("{\"dateTime\":1568085875000}", JSON.toJSONString(vo2));
    }

    public void testForISO8601() {
        assertEquals("{\"dateTime\":\"2019-09-10T11:24:35\"}", JSON.toJSONString(vo1, SerializerFeature.UseISO8601DateFormat));
        assertEquals("{\"dateTime\":\"2019-09-10T11:24:35.000056789\"}", JSON.toJSONString(vo2, SerializerFeature.UseISO8601DateFormat));
    }

    public void testForDateFormat() {
        assertEquals("{\"dateTime\":\"2019-09-10 11:24:35\"}", JSON.toJSONString(vo1, SerializerFeature.WriteDateUseDateFormat));
        assertEquals("{\"dateTime\":\"2019-09-10 11:24:35\"}", JSON.toJSONString(vo2, SerializerFeature.WriteDateUseDateFormat));
    }

    public void testForPriority() {
        assertEquals("{\"dateTime\":\"2019-09-10T11:24:35\"}", JSON.toJSONString(vo1, SerializerFeature.UseISO8601DateFormat, SerializerFeature.WriteDateUseDateFormat));
        assertEquals("{\"dateTime\":\"2019-09-10T11:24:35.000056789\"}", JSON.toJSONString(vo2, SerializerFeature.UseISO8601DateFormat, SerializerFeature.WriteDateUseDateFormat));
    }

    public void testForAnnotation() {
        assertEquals("{\"dateTime\":\"2019-09-10\"}", JSON.toJSONString(voa));
        assertEquals("{\"dateTime\":\"2019-09-10\"}", JSON.toJSONString(voa, SerializerFeature.UseISO8601DateFormat));
        assertEquals("{\"dateTime\":\"2019-09-10\"}", JSON.toJSONString(voa, SerializerFeature.WriteDateUseDateFormat));
        assertEquals("{\"dateTime\":\"2019-09-10\"}", JSON.toJSONString(voa, SerializerFeature.UseISO8601DateFormat, SerializerFeature.WriteDateUseDateFormat));
    }

    public static class VO {
        private LocalDateTime dateTime;

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }
    }

    public static class VOAnnotation {
        @JSONField(format = "yyyy-MM-dd")
        private LocalDateTime dateTime;

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }
    }
}
