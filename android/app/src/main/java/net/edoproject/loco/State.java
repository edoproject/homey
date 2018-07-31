package net.edoproject.loco;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class State {
    private List<Category> categories;

    void load(String filename){
        ObjectMapper mapper = new ObjectMapper();
        try {
            setCategories(mapper.readValue(new File(filename), new TypeReference<ArrayList<Category>>(){}));
            System.out.println(getCategories());
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void load(InputStream inputStream){
        ObjectMapper mapper = new ObjectMapper();

        try {
            setCategories(mapper.readValue(inputStream, new TypeReference<ArrayList<Category>>(){}));
            System.out.println(getCategories());
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
