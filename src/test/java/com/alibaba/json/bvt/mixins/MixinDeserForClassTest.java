package com.alibaba.json.bvt.mixins;

import org.junit.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import junit.framework.TestCase;

public class MixinDeserForClassTest extends TestCase {
    static class BaseClass {
        @JSONField( deserialize = true )
        public String a;

        @JSONField( deserialize = false, name = "a" )
        public void setA( String v ) {
            a = "XXX" + v;
        }
    }

    static class BaseClass1 {
        @JSONField( deserialize = false )
        public String a;

        @JSONField( deserialize = true, name = "a" )
        public void setA( String v ) {
            a = "XXX" + v;
        }
    }

    class Mixin1 {
        @JSONField( deserialize = false )
        public String a;

        @JSONField( deserialize = true, name = "a" )
        public void setA( String v ) {
        }
    }

    class Mixin2 {
        @JSONField( deserialize = false )
        public String a;

        @JSONField( deserialize = true, name = "a2" )
        public void setA( String v ) {
            a = "a2 " + v;
        }
    }

    public void test_1() throws Exception {
        BaseClass1 base = JSON.parseObject( "{\"a\":\"132\"}", BaseClass1.class );
        Assert.assertEquals( "XXX132", base.a );
    }

    public void test_2() throws Exception {
        JSON.addMixInAnnotations(BaseClass.class, Mixin1.class);
        BaseClass base = JSON.parseObject( "{\"a\":\"132\"}", BaseClass.class );
        Assert.assertEquals( "XXX132", base.a );
        JSON.removeMixInAnnotations(BaseClass.class);
    }

    public void test_3() throws Exception {
        JSON.addMixInAnnotations(BaseClass.class, Mixin1.class);
        BaseClass base = JSON.parseObject( "{\"a\":\"132\"}", BaseClass.class );
        Assert.assertEquals( "XXX132", base.a );
        JSON.removeMixInAnnotations(BaseClass.class);

        JSON.addMixInAnnotations(BaseClass.class, Mixin2.class);
        base = JSON.parseObject( "{\"a2\":\"143\"}", BaseClass.class );
        Assert.assertEquals( "XXX143", base.a );
        JSON.removeMixInAnnotations(BaseClass.class);

        base = JSON.parseObject( "{\"a\":\"132\", \"a2\":\"143\"}", BaseClass.class );
        Assert.assertEquals( "132", base.a );
    }

    public void test_4() throws Exception {
        JSON.addMixInAnnotations(BaseClass.class, Mixin1.class);
        BaseClass base = JSON.parseObject( "{\"a\":\"132\"}", BaseClass.class );
        Assert.assertEquals( "XXX132", base.a );

        JSON.addMixInAnnotations(BaseClass.class, Mixin2.class);
        base = JSON.parseObject( "{\"a2\":\"143\"}", BaseClass.class );
        Assert.assertEquals( "XXX143", base.a );
        JSON.removeMixInAnnotations(BaseClass.class);
    }

}
