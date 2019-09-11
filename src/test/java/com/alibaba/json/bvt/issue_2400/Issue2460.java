package com.alibaba.json.bvt.issue_2400;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;

public class Issue2460 extends TestCase {
    public void test_for_issue() throws Exception {
        String str1 = "[1,2,3,\"{\"]";
        assertTrue(JSON.isValidArray(str1));

        String str2 = "[ {\"123\":\"321\"} ]";
        assertTrue(JSON.isValidArray(str2));

        String str3 = "[1, 2, 3,   {\"123\":\"321\"}    ]";
        assertTrue(JSON.isValidArray(str3));
    }
}
