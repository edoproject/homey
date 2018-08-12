package net.edoproject.loco;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class State {
    private Context context;
    private List<Category> categories;
    private static final String TAG = State.class.getSimpleName();
    private static final String savedStateFilename = "state.json";

    public State(Context context) {
        this.context = context;
        load(savedStateFilename);
    }

    private void load(String filename) {
        Log.d(TAG, "Trying to load the previous state");
        try {
            load(context.openFileInput(filename));
        } catch (FileNotFoundException e) {
            Log.d(TAG, "Previous state not found");
            load();
        }


    }

    private void load(){
        Log.d(TAG, "Loading initial state");
        load(context.getResources().openRawResource(R.raw.default_liet));
    }

    private void load(InputStream inputStream) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            setCategories(mapper.readValue(inputStream,
                    new TypeReference<ArrayList<Category>>(){}));
            System.out.println(getCategories());
        } catch (JsonGenerationException e) {
            Log.e(TAG, "JsonGenerationException", e);
        } catch (JsonMappingException e) {
            Log.e(TAG, "JsonMappingException", e);
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }
    }

    public void reset(){
        load();
    }

    public void reload() {
        load(savedStateFilename);
    }

    private void save(String filename) {
        Log.d(TAG, "Saving state");

        ObjectMapper mapper = new ObjectMapper();
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getCategories()).getBytes());
            outputStream.close();
        } catch (Exception e) {
            Log.e(TAG, "Exception", e);
        }
    }

    public void save() {
        save(savedStateFilename);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
