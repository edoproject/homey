
package net.edoproject.loco;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
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

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private List<Item> items;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView itemNameView;
        private ImageView alreadyHaveView;
        private ImageView inNewApartmentView;
        private ImageView removeItemView;

        public ViewHolder(View view) {
            super(view);
            itemNameView = view.findViewById(R.id.itemName);
            alreadyHaveView = view.findViewById(R.id.itemAlreadyHave);
            inNewApartmentView = view.findViewById(R.id.itemInNewApartment);
            removeItemView = view.findViewById(R.id.itemRemoveFromTheList);
        }

        public TextView getItemNameView() {
            return itemNameView;
        }
        public ImageView getAlreadyHaveView() { return alreadyHaveView; }
        public ImageView getInNewApartmentView() {
            return inNewApartmentView;
        }
        public ImageView getRemoveItemView() {
            return removeItemView;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ItemsAdapter(List<Item> items) {
        this.items = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if ( position<items.size() ) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            Item item = items.get(position);
            holder.getItemNameView().setText(item.getName());

            holder.getAlreadyHaveView().setColorFilter(ContextCompat.getColor(holder.getAlreadyHaveView().getContext(),
                    item.isAlreadyHave() ?
                            R.color.colorBlueItemText :
                            R.color.colorMediumGrayItemsIcon));

            holder.getAlreadyHaveView().setOnClickListener((view) -> {
                if (item.isAlreadyHave()) {
                    Toast toast = Toast.makeText(view.getContext(), "I don't have it yet", Toast.LENGTH_SHORT);
                    toast.show();
                    item.setAlreadyHave(false);
                    holder.getAlreadyHaveView().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.colorMediumGrayItemsIcon));
                } else {
                    Toast toast = Toast.makeText(view.getContext(), "I already have it", Toast.LENGTH_SHORT);
                    toast.show();
                    item.setAlreadyHave(true);
                    holder.getAlreadyHaveView().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.colorBlueItemText));
                }
            });

            holder.getInNewApartmentView().setColorFilter(ContextCompat.getColor(holder.getInNewApartmentView().getContext(),
                    item.isInNewApartment() ?
                            R.color.colorBlueItemText :
                            R.color.colorMediumGrayItemsIcon));

            holder.getInNewApartmentView().setOnClickListener((view) -> {
                if (item.isInNewApartment()) {
                    Toast toast = Toast.makeText(view.getContext(), "I will have it", Toast.LENGTH_SHORT);
                    toast.show();
                    item.setInNewApartment(false);
                    holder.getInNewApartmentView().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.colorMediumGrayItemsIcon));
                } else {
                    Toast toast = Toast.makeText(view.getContext(), "I will not have it", Toast.LENGTH_SHORT);
                    toast.show();
                    item.setInNewApartment(true);
                    holder.getInNewApartmentView().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.colorBlueItemText));
                }
            });

            holder.getRemoveItemView().setOnClickListener((view) -> {
                Toast toast = Toast.makeText(view.getContext(), "Removing item" + item.getName(), Toast.LENGTH_SHORT);
                toast.show();
                removeAt(position);
            });
        } else {
            TextView tv = holder.getItemNameView();
            tv.setText("Add item");
            holder.getAlreadyHaveView().setVisibility(View.GONE);
            holder.getInNewApartmentView().setVisibility(View.GONE);
            holder.getRemoveItemView().setVisibility(View.GONE);
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
                        String newItemName = input.getText().toString();
                        Item item = new Item();
                        item.setName(newItemName);
                        items.add(item);
                        notifyItemInserted(items.size()-1);
                        //notifyDataSetChanged();
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

    private void add() {
        items.add(new Item());
        notifyItemInserted(items.size()-1);
    }

    private void removeAt(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size() + 1;
    }
}

