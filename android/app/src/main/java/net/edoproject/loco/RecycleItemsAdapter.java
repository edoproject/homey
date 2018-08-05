
package net.edoproject.loco;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecycleItemsAdapter extends RecyclerView.Adapter<RecycleItemsAdapter.ViewHolder> {
    private List<Item> items;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView itemNameView;
        private ImageView keepItView;
        private ImageView donateItView;
        private ImageView dumpItView;

        public ViewHolder(View view) {
            super(view);
            itemNameView = view.findViewById(R.id.recycleItemName);
            keepItView = view.findViewById(R.id.recycleItemKeepIt);
            donateItView = view.findViewById(R.id.recycleItemDonateIt);
            dumpItView = view.findViewById(R.id.recycleItemDumpIt);
        }

        public TextView getItemNameView() {
            return itemNameView;
        }
        public ImageView getKeepItView() { return keepItView; }
        public ImageView getDonateItView() {
            return donateItView;
        }
        public ImageView getDumpItView() {
            return dumpItView;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecycleItemsAdapter(List<Item> items) {
        this.items = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position > items.size()-1){
            return;
        }
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Item item = items.get(position);
        TextView tv =  holder.getItemNameView();
        tv.setText(item.getName());
        setActionColors(item.getAction(), holder, holder.itemView.getContext());

        holder.getKeepItView().setOnClickListener((view) -> {
            Toast toast = Toast.makeText(view.getContext(), "Let's keep " + item.getName(), Toast.LENGTH_SHORT);
            toast.show();
            item.setAction(Item.Action.KEEP);
            setActionColors(item.getAction(), holder, view.getContext());
        });

        holder.getDonateItView().setOnClickListener((view) -> {
            Toast toast = Toast.makeText(view.getContext(), "Let's donate " + item.getName(), Toast.LENGTH_SHORT);
            toast.show();
            item.setAction(Item.Action.DONATE);
            setActionColors(item.getAction(), holder, view.getContext());
        });

        holder.getDumpItView().setOnClickListener((view) -> {
            Toast toast = Toast.makeText(view.getContext(), "Let's dump " + item.getName(), Toast.LENGTH_SHORT);
            toast.show();
            item.setAction(Item.Action.DUMP);
            setActionColors(item.getAction(), holder, view.getContext());
        });
    }

    private void setActionColors(Item.Action action, ViewHolder holder, Context context){
        switch (action) {
            case KEEP:
                holder.getKeepItView().setColorFilter(ContextCompat.getColor(context, R.color.colorBlueItemText));
                holder.getDonateItView().setColorFilter(ContextCompat.getColor(context, R.color.colorMediumGrayItemsIcon));
                holder.getDumpItView().setColorFilter(ContextCompat.getColor(context, R.color.colorMediumGrayItemsIcon));
                break;
            case DONATE:
                holder.getKeepItView().setColorFilter(ContextCompat.getColor(context, R.color.colorMediumGrayItemsIcon));
                holder.getDonateItView().setColorFilter(ContextCompat.getColor(context, R.color.colorBlueItemText));
                holder.getDumpItView().setColorFilter(ContextCompat.getColor(context, R.color.colorMediumGrayItemsIcon));
                break;
            case DUMP:
                holder.getKeepItView().setColorFilter(ContextCompat.getColor(context, R.color.colorMediumGrayItemsIcon));
                holder.getDonateItView().setColorFilter(ContextCompat.getColor(context, R.color.colorMediumGrayItemsIcon));
                holder.getDumpItView().setColorFilter(ContextCompat.getColor(context, R.color.colorBlueItemText));
                break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }
}

