package com.github.inazo1115.easyjsondeserializer;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import com.github.inazo1115.easyjsondeserializer.Json;

public class JsonTest {

    // Used for test.
    class Dog {
        String name;
        int age;
        Dog(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    @Test
    public void canGetStringVal() throws Exception {
        Json json = new Json("\"foo\"");
        assertEquals("foo", json.stringVal());
    }

    @Test
    public void canGetIntVal() throws Exception {
        Json json = new Json("1");
        assertEquals(Integer.valueOf(1), json.intVal());
    }

    @Test
    public void canGetFloatVal() throws Exception {
        Json json = new Json("0.1");
        assertEquals(0.1f, json.floatVal(), 0.0000001);
    }

    @Test
    public void canGetDoubleVal() throws Exception {
        Json json = new Json("0.1");
        assertEquals(0.1d, json.doubleVal(), 0.0000001);
    }

    @Test
    public void canGetMapVal() throws Exception {
        Json json0 = new Json("{\"foo\":1}");
        assertEquals(Integer.valueOf(1), json0.mapVal().get("foo").intVal());

        Json json1 = new Json("{\"foo\":\"bar\"}");
        assertEquals("bar", json1.mapVal().get("foo").stringVal());

        Json json2 = new Json("{\"foo\":1,\"bar\":2}");
        assertEquals(Integer.valueOf(1), json2.mapVal().get("foo").intVal());
        assertEquals(Integer.valueOf(2), json2.mapVal().get("bar").intVal());
    }

    @Test
    public void canGetArrayVal() throws Exception {
        Json json = new Json("[11,22,33,44,55]");
        ArrayList<Json> arr = json.arrayVal();
        assertEquals(5, arr.size());
        assertEquals(Integer.valueOf(11), arr.get(0).intVal());
        assertEquals(Integer.valueOf(22), arr.get(1).intVal());
        assertEquals(Integer.valueOf(33), arr.get(2).intVal());
        assertEquals(Integer.valueOf(44), arr.get(3).intVal());
        assertEquals(Integer.valueOf(55), arr.get(4).intVal());
    }

    @Test
    public void canGetUserDefinedClassVal() throws Exception {
        Json json = new Json("{\"name\":\"foo\",\"age\":1}");
        Dog d = json.val(Dog.class);
        assertEquals("foo", d.name);
        assertEquals(1, d.age);
    }

    @Test
    public void canAccessByGetMethod() throws Exception {
        Json json = new Json("{\"a\":{\"b\":{\"c\":100},\"d\":{\"e\":200,\"f\":300}}}");
        assertEquals(Integer.valueOf(100), json.get("a").get("b").get("c").intVal());
        assertEquals(Integer.valueOf(200), json.get("a").get("d").get("e").intVal());
        assertEquals(Integer.valueOf(300), json.get("a").get("d").get("f").intVal());
    }

    @Test
    public void canAccessByIndexMethod() throws Exception {
        Json json = new Json("[11,22,33,44,55]");
        assertEquals(Integer.valueOf(11), json.index(0).intVal());
        assertEquals(Integer.valueOf(22), json.index(1).intVal());
        assertEquals(Integer.valueOf(33), json.index(2).intVal());
        assertEquals(Integer.valueOf(44), json.index(3).intVal());
        assertEquals(Integer.valueOf(55), json.index(4).intVal());
    }

    @Test
    public void useSetJson() throws Exception {
        Json json = new Json();
        json.setJson("1");
        assertEquals(Integer.valueOf(1), json.intVal());
    }
}
