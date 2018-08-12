package net.edoproject.loco;
import static org.mockito.Mockito.*;
import android.content.Context;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StateBuilderTest {
    @Test
    public void load_json() {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/java/net/edoproject/loco/resources/state.json");
            Context context = mock(Context.class);
            when(context.openFileInput((String)notNull())).thenReturn(fileInputStream);

            State state = new State(context);
            System.out.print(State.nameHierarchy(state.getCategories()));
            Category category = new Category();
            category.setName("new one");
            List<Category> categories1 = state.getCategories();
            List<Category> categories2 = state.getCategories();
            categories1.add(category);

            System.out.print(State.nameHierarchy(categories1));
            System.out.print(State.nameHierarchy(categories2));

        } catch (FileNotFoundException e) {
            System.out.print("aaa");
        }
    }

}