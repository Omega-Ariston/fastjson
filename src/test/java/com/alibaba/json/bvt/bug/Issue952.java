package com.alibaba.json.bvt.bug;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by wenshao on 19/12/2016.
 */
public class Issue952 extends TestCase {
    private final static String formatter_iso8601_pattern     = "yyyy-MM-dd'T'HH:mm:ss";
    private final static String formatter_iso8601_pattern_23     = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private final static String formatter_iso8601_pattern_29     = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";

    public void test_for_issue() throws Exception {
        String pattern = "yyyy-MM-dd'T'HH:mm:ss";

        LocalDateTime dateTime = LocalDateTime.now();

        int nano = dateTime.getNano();
        if (nano == 0) {
            pattern = formatter_iso8601_pattern;
        } else if (nano % 1000000 == 0) {
            pattern = formatter_iso8601_pattern_23;
        } else {
            pattern = formatter_iso8601_pattern_29;
        }

        DateTimeFormatter formatter   = DateTimeFormatter.ofPattern(pattern);

        String text = JSON.toJSONString(dateTime, SerializerFeature.UseISO8601DateFormat);
        assertEquals(JSON.toJSONString(formatter.format(dateTime)), text);
    }
}
