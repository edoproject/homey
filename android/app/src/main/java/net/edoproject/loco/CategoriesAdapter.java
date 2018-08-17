package net.edoproject.loco;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import net.edoproject.loco.databinding.CategoryBinding;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private final static String TAG = CategoriesAdapter.class.getSimpleName();
    private List<Category> categories;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CategoryBinding binding;

        public ViewHolder(CategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category category) {
            binding.setCategory(category);
            binding.executePendingBindings();
        }
    }

    public CategoriesAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CategoryBinding binding = CategoryBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new CategoriesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CategoriesAdapter.ViewHolder holder, int position) {
        if (position<categories.size()) {
            Category category = categories.get(position);
            holder.bind(category);

            RecyclerView.LayoutManager itemsLayoutManager
                    = new LinearLayoutManager(holder.binding.items.getContext());
            holder.binding.items.setLayoutManager(itemsLayoutManager);

            ItemsAdapter itemsAdapter = new ItemsAdapter(category.getItems());
            holder.binding.items.setAdapter(itemsAdapter);

            holder.binding.categoryRemoveFromTheList.setOnClickListener((view) -> {
                Toast toast = Toast.makeText(view.getContext(),
                        "Removing item" + category.getName(), Toast.LENGTH_SHORT);
                toast.show();
                removeAt(position);
            });
        } else {
            Category category = new Category();
            holder.bind(category);
            holder.binding.categoryName.setText("Add category");

            Context context = holder.binding.categoryRow.getContext();
            // Only one layout per data binding, so the RecyclerView will be the layout
            ((RecyclerView.LayoutParams)holder.binding.categoryRow.getLayoutParams()).bottomMargin
                    = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            128, context.getResources().getDisplayMetrics());

            holder.binding.categoryAlreadyHave.setVisibility(View.GONE);
            holder.binding.categoryInNewApartment.setVisibility(View.GONE);
            holder.binding.categoryRemoveFromTheList.setVisibility(View.GONE);
            holder.binding.items.setVisibility(View.GONE);

            holder.binding.categoryName.setOnClickListener((view) -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Add category");

                final EditText input = new EditText(context);
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
                        holder.bind(category);
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

    @Override
    public int getItemCount() {
        return categories.size() + 1;
    }
}