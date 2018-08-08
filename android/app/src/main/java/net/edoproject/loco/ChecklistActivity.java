package net.edoproject.loco;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

public class ChecklistActivity extends AppCompatActivity {
    private RecyclerView categoriesView;
    private RecyclerView.Adapter categoriesAdapter;
    private RecyclerView.LayoutManager categoriesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_item_checklist);

        categoriesView = findViewById(R.id.item_checklist_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        categoriesView.setHasFixedSize(true);

        // use a linear layout manager
        categoriesLayoutManager = new LinearLayoutManager(this);
        categoriesView.setLayoutManager(categoriesLayoutManager);

        State state = new State(this);

        List categories = state.getCategories();

        categoriesAdapter = new CategoriesAdapter(categories);
        categoriesView.setAdapter(categoriesAdapter);
    }
}
