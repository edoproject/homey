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
    private final static String TAG = State.class.getSimpleName();
    private Context context;
    private List<Category> categories;
    private static final String savedStateFilename = "state.json";

    public State(Context context) {
        Log.e(TAG, "Ctate CTR");
        this.context = context;
        load(savedStateFilename);
        Log.d(TAG, "CTOR: \n" + nameHierarchy(this.categories));
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
        Log.d(TAG, "save: \n" + nameHierarchy(categories));
        save(savedStateFilename);
    }

    public List<Category> getCategories() {
        Log.d(TAG, "getCategories");
        return categories;
    }

    public List<Category> getSummary() {
        Log.d(TAG, "getSummary");
        List<Category> summary = new ArrayList<>();
        Category toBuy = new Category("Items to buy", true, new ArrayList<>());
        List<Item> itemsToBuy = toBuy.getItems();
        summary.add(toBuy);

        Category dupplicates = new Category("Dupplicates", true, new ArrayList<>());
        List<Item> itemsDupplicates = dupplicates.getItems();
        summary.add(dupplicates);

        Category donation = new Category("To donate", true, new ArrayList<>());
        List<Item> itemsToDonate = donation.getItems();
        summary.add(donation);

        Category junk = new Category("To dump", true, new ArrayList<>());
        List<Item> itemsJunk = junk.getItems();
        summary.add(junk);

        for (Category category : categories) {
            List<Item> items = category.getItems();
            for (Item item : items) {
                if (!item.isAlreadyHave() && !item.isInNewApartment()) {
                    itemsToBuy.add(item);
                }
                if (item.isAlreadyHave() && item.isInNewApartment()) {
                    itemsDupplicates.add(item);
                }
                if (item.getAction() == Item.Action.DONATE) {
                    itemsToDonate.add(item);
                }
                if (item.getAction() == Item.Action.DUMP) {
                    itemsJunk.add(item);
                }
            }
        }

        Log.d(TAG, "getSummary: \n" + nameHierarchy(summary));
        return summary;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        Log.d(TAG, "setCategories: \n" + nameHierarchy(this.categories));
    }

    static String nameHierarchy(List<Category> categories) {
        StringBuilder sb = new StringBuilder();

        for (Category category : categories) {
            sb.append(category.getName());
            sb.append(": {");
            List<Item> items = category.getItems();
            for (Item item : items) {
                sb.append(item.getName());
                sb.append(", ");
            }
            sb.append("}\n");
        }
        return sb.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Log.d(TAG, "DTOR: \n" + nameHierarchy(this.categories));
    }
}
