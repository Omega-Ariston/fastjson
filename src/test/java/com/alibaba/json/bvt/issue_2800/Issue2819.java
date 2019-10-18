package com.alibaba.json.bvt.issue_2800;

import java.math.BigInteger;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import jersey.repackaged.com.google.common.collect.Lists;
import junit.framework.TestCase;

public class Issue2819 extends TestCase {
    public void test_for_issue() throws Exception {
        BigInteger bint = new BigInteger("231548942313154123");
        List<Object> list = Lists.newArrayList();
        list.add(12345678912312121L);
        list.add(bint);
        System.out.println(JSON.toJSONString(list, SerializerFeature.BrowserCompatible));
    }
}
