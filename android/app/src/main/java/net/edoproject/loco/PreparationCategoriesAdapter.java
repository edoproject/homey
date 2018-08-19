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
import net.edoproject.loco.databinding.PreparationCategoryBinding;
import java.util.List;

public class PreparationCategoriesAdapter extends RecyclerView.Adapter<PreparationCategoriesAdapter.ViewHolder> {
    private final static String TAG = PreparationCategoriesAdapter.class.getSimpleName();
    private List<Category> categories;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private PreparationCategoryBinding binding;

        public ViewHolder(PreparationCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category category) {
            binding.setCategory(category);
            binding.executePendingBindings();
        }
    }

    public PreparationCategoriesAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public PreparationCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PreparationCategoryBinding binding = PreparationCategoryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new PreparationCategoriesAdapter.ViewHolder(binding);
    }

    private void bindItems(PreparationCategoriesAdapter.ViewHolder holder, Category category) {
        RecyclerView.LayoutManager itemsLayoutManager
                = new LinearLayoutManager(holder.binding.items.getContext());
        holder.binding.items.setLayoutManager(itemsLayoutManager);

        PreparationItemsAdapter preparationItemsAdapter = new PreparationItemsAdapter(category.getItems());
        holder.binding.items.setAdapter(preparationItemsAdapter);
    }

    @Override
    public void onBindViewHolder(PreparationCategoriesAdapter.ViewHolder holder, int position) {
        if (position<categories.size()) {
            Category category = categories.get(position);
            holder.bind(category);

            bindItems(holder, category);

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
                        Category new_category = new Category();
                        new_category.setName(newCategoryName);
                        categories.add(new_category);
                        bindItems(holder, new_category);
                        notifyItemInserted(categories.size() - 1);
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