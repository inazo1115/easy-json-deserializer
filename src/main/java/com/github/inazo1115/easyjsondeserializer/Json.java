package com.github.inazo1115.easyjsondeserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonIOException;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class Json {

    private Gson gson;
    private String json;

    public Json() {
        this.gson = new Gson();
    }

    public Json(String json) {
        this.gson = new Gson();
        this.json = json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Json get(String key) throws NullPointerException, IllegalTypeException, IOException {
        Map<String, Json> map = this.mapVal();
        Json ret = map.get(key);
        if (ret == null) {
            throw new NullPointerException("key not found: " + key);
        }
        return ret;
    }

    public Json index(int idx) throws NullPointerException, IllegalTypeException, IOException {
        ArrayList<Json> arr = this.arrayVal();
        if (idx > arr.size() - 1) {
            String s = String.format("out of index: idx=%s, size=%s", idx, arr.size());
            throw new NullPointerException(s);
        }
        return arr.get(idx);
    }

    public <T> T val(Class<T> classOfT) throws NullPointerException, IllegalTypeException, IOException {
        this.checkNull();
        try {
            return gson.fromJson(this.json, classOfT);
        } catch (JsonSyntaxException e) {
            String s = String.format("'%s' cannot be parsed as %s", this.json.toString(), classOfT);
            throw new IllegalTypeException(s);
        } catch (JsonIOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public <T> T val(Type typeOfT) throws NullPointerException, IllegalTypeException, IOException {
        this.checkNull();
        try {
            return gson.fromJson(this.json, typeOfT);
        } catch (JsonSyntaxException e) {
            String s = String.format("'%s' cannot be parsed as %s", this.json.toString(), typeOfT);
            throw new IllegalTypeException(s);
        } catch (JsonIOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public String stringVal() throws NullPointerException, IllegalTypeException, IOException {
        return this.val(String.class);
    }

    public Integer intVal() throws NullPointerException, IllegalTypeException, IOException {
        return this.val(Integer.class);
    }

    public Float floatVal() throws NullPointerException, IllegalTypeException, IOException {
        return this.val(Float.class);
    }

    public Double doubleVal() throws NullPointerException, IllegalTypeException, IOException {
        return this.val(Double.class);
    }

    public Map<String, Json> mapVal() throws NullPointerException, IllegalTypeException, IOException {
        Map<String, Json> ret = new LinkedTreeMap<>();
        Type type = new TypeToken<LinkedTreeMap<String, Object>>(){}.getType();
        for (Map.Entry<String, Object> e : ((Map<String, Object>)this.val(type)).entrySet()) {
            ret.put(e.getKey(), new Json(gson.toJson(e.getValue())));
        }
        return ret;
    }

    public ArrayList<Json> arrayVal() throws NullPointerException, IllegalTypeException, IOException {
        ArrayList<Json> ret = new ArrayList<>();
        for (Object o : this.val(ArrayList.class)) {
            ret.add(new Json(gson.toJson(o)));
        }
        return ret;
    }

    public String toString() {
        return "Json: " + this.json.toString();
    }

    private void checkNull() throws NullPointerException {
        if (this.json == null) {
            throw new NullPointerException("target json is null");
        }
    }
}
