package net.edoproject.loco;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StateBuilderTest {
    @Test
    public void load_json() {
        State sb = new State();
        sb.load("src/main/res/raw/default_liet.json");
        assertEquals(4, 2 + 2);
    }

}