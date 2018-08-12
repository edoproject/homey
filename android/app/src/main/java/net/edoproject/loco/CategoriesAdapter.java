package net.edoproject.loco;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private final static String TAG = CategoriesAdapter.class.getSimpleName();
    private List<Category> categories;
    private RecyclerView.LayoutManager itemsLayoutManager;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        private ImageView alreadyHaveView;
        private ImageView inNewApartmentView;
        private ImageView removeItemView;
        private RelativeLayout categoryLayout;
        public RecyclerView itemsView;
        public View view;

        public ViewHolder(View v) {
            super(v);
            categoryLayout = v.findViewById(R.id.categoryRow);
            textView = v.findViewById(R.id.categoryName);
            alreadyHaveView = v.findViewById(R.id.categoryAlreadyHave);
            inNewApartmentView = v.findViewById(R.id.categoryInNewApartment);
            removeItemView = v.findViewById(R.id.categoryRemoveFromTheList);
            itemsView = v.findViewById(R.id.items);
            view = v;
        }

        public RelativeLayout getCategoryLayout() { return categoryLayout; }
        public TextView getTextView() { return textView; }
        public ImageView getAlreadyHaveView() { return alreadyHaveView; }
        public ImageView getInNewApartmentView() { return inNewApartmentView; }
        public ImageView getRemoveItemView() { return removeItemView; }
        public RecyclerView getItemsView() { return itemsView; }
        public View getCategoryView() { return view; }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CategoriesAdapter(List<Category> categories) {
        this.categories = categories;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category, parent, false);
        return new ViewHolder(v);
    }

    private void bindViewHolderToCategory(ViewHolder holder, Category category){
        itemsLayoutManager = new LinearLayoutManager(holder.itemsView.getContext());
        holder.itemsView.setLayoutManager(itemsLayoutManager);

        ItemsAdapter itemsAdapter = new ItemsAdapter(category.getItems());
        holder.itemsView.setAdapter(itemsAdapter);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if ( position<categories.size() ) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            Category category = categories.get(position);
            holder.getTextView().setText(category.getName());

            holder.getCategoryView().setOnClickListener((view) -> {
                if (category.isExpanded()) {
                    Toast toast = Toast.makeText(view.getContext(), "collapsing", Toast.LENGTH_SHORT);
                    toast.show();
                    category.setExpanded(false);
                    holder.itemsView.setVisibility(View.GONE);
                } else {
                    Toast toast = Toast.makeText(view.getContext(), "expanding", Toast.LENGTH_SHORT);
                    toast.show();
                    category.setExpanded(true);
                    holder.itemsView.setVisibility(View.VISIBLE);
                }
            });

            holder.getAlreadyHaveView().setOnClickListener((view) -> {
                Toast toast = Toast.makeText(view.getContext(), "I already have them all", Toast.LENGTH_SHORT);
                toast.show();
                List<Item> items = category.getItems();
                for (Item item: items) {
                    item.setAlreadyHave(true);
                }
                holder.itemsView.getAdapter().notifyItemRangeChanged(0, items.size());
            });

            holder.getInNewApartmentView().setOnClickListener((view) -> {
                Toast toast = Toast.makeText(view.getContext(), "I will have them all", Toast.LENGTH_SHORT);
                toast.show();
                List<Item> items = category.getItems();
                for (Item item: items) {
                    item.setInNewApartment(true);
                }
                holder.itemsView.getAdapter().notifyItemRangeChanged(0, items.size());
            });

            holder.getRemoveItemView().setOnClickListener((view) -> {
                Toast toast = Toast.makeText(view.getContext(), "Removing item" + category.getName(), Toast.LENGTH_SHORT);
                toast.show();
                removeAt(position);
            });

            bindViewHolderToCategory(holder, category);
        } else {
            TextView tv = holder.getTextView();
            tv.setText("Add category");

            RelativeLayout categoryLayout = holder.getCategoryLayout();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) categoryLayout.getLayoutParams();
            params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, categoryLayout.getContext().getResources().getDisplayMetrics());
            categoryLayout.setLayoutParams(params);

            holder.getAlreadyHaveView().setVisibility(View.GONE);
            holder.getInNewApartmentView().setVisibility(View.GONE);
            holder.getRemoveItemView().setVisibility(View.GONE);
            holder.getItemsView().setVisibility(View.GONE);
            tv.setOnClickListener((view) -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(tv.getContext());
                builder.setTitle("Add category");

                // Set up the input
                final EditText input = new EditText(tv.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newCategoryName = input.getText().toString();
                        Category category = new Category();
                        category.setName(newCategoryName);
                        categories.add(category);
                        State.nameHierarchy(categories);
                        Log.d(TAG, "Added category");
                        Log.d(TAG, State.nameHierarchy(categories));
                        notifyItemInserted(categories.size()-1);
                        bindViewHolderToCategory(holder, category);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            });
        }
    }

    private void removeAt(int position) {
        categories.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, categories.size());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return categories.size() + 1;
    }
}