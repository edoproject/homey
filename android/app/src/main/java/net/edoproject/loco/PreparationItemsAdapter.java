package net.edoproject.loco;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import net.edoproject.loco.databinding.PreparationItemBinding;

import java.util.List;

public class PreparationItemsAdapter extends RecyclerView.Adapter<PreparationItemsAdapter.ViewHolder> {
    private List<Item> items;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private PreparationItemBinding binding;

        public ViewHolder(PreparationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Item item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }

    public PreparationItemsAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public PreparationItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PreparationItemBinding itemBinding = PreparationItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if ( position<items.size() ) {
            Item item = items.get(position);
            holder.bind(item);

            holder.binding.itemRemoveFromTheList.setOnClickListener((view) -> {
                Toast toast = Toast.makeText(view.getContext(),
                        "Removing item" + item.getName(), Toast.LENGTH_SHORT);
                toast.show();
                removeAt(position);
            });
        } else {
            Item item = new Item();
            holder.bind(item);

            holder.binding.itemName.setText("Add item");
            holder.binding.itemAlreadyHave.setVisibility(View.GONE);
            holder.binding.itemInNewApartment.setVisibility(View.GONE);
            holder.binding.itemRemoveFromTheList.setVisibility(View.GONE);

            View tv = holder.itemView;

            tv.setOnClickListener((view) -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(tv.getContext());
                builder.setTitle("Add item");

                final EditText input = new EditText(tv.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newItemName = input.getText().toString();
                        Item new_item = new Item();
                        new_item.setName(newItemName);
                        items.add(new_item);
                        notifyItemInserted(items.size() - 1);
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
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }
}
