package net.edoproject.loco;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecycleCategoriesAdapter extends RecyclerView.Adapter<RecycleCategoriesAdapter.ViewHolder> {
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
        public RecyclerView itemsView;
        public View view;
        public String newCategory;

        public ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.recycleCategoryName);
            alreadyHaveView = v.findViewById(R.id.recycleCategoryIkeepIt);
            inNewApartmentView = v.findViewById(R.id.recycleCategoryDonateIt);
            removeItemView = v.findViewById(R.id.recycleCategoryDumpIt);
            itemsView = v.findViewById(R.id.recycleItems);
            view = v;
        }

        public TextView getTextView() { return textView; }
        public ImageView getAlreadyHaveView() { return alreadyHaveView; }
        public ImageView getInNewApartmentView() { return inNewApartmentView; }
        public ImageView getRemoveItemView() { return removeItemView; }
        public RecyclerView getItemsView() { return itemsView; }
        public View getCategoryView() { return view; }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecycleCategoriesAdapter(List<Category> categories) {
        this.categories = categories;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_category, parent, false);
        return new ViewHolder(v);
    }

    private void bindViewHolderToCategory(ViewHolder holder, Category category){
        itemsLayoutManager = new LinearLayoutManager(holder.itemsView.getContext());
        holder.itemsView.setLayoutManager(itemsLayoutManager);

        RecycleItemsAdapter itemsAdapter = new RecycleItemsAdapter(category.getItems());
        holder.itemsView.setAdapter(itemsAdapter);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if ( position < categories.size() ) {
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
            });

            bindViewHolderToCategory(holder, category);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return categories.size();
    }
}