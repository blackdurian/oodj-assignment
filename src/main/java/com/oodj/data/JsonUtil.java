package com.oodj.data;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil<T>{

    private ObjectMapper mapper = new ObjectMapper();

    public JsonUtil() {

    }

    public void serialize(List<T> values,String path) {
        try {
            File file = new File(path);
            if (file.createNewFile()) {
                System.out.println("File has been created.");
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(file,values);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<T> deserialize(String path, Class<?> tClass)   {
        List<T> result = new ArrayList<>();
        JavaType type = mapper.getTypeFactory().
                constructCollectionType(ArrayList.class, tClass);

        try {
            File file = new File(path);
            if (!file.exists()){
                file.createNewFile();
            }
            BufferedReader br = new BufferedReader(
                    new FileReader(file));
            result = mapper.readValue(br, type);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return result;
    }
}
