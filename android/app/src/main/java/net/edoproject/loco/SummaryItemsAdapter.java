
package net.edoproject.loco;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SummaryItemsAdapter extends RecyclerView.Adapter<SummaryItemsAdapter.ViewHolder> {
    private List<Item> items;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView itemNameView;
        private CheckBox completed;

        public ViewHolder(View view) {
            super(view);
            itemNameView = view.findViewById(R.id.summaryItemName);
            completed = view.findViewById(R.id.summaryItemCompleted);
        }

        public TextView getItemNameView() {
            return itemNameView;
        }
        public CheckBox getCompletedView() { return completed; }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SummaryItemsAdapter(List<Item> items) {
        this.items = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SummaryItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_item, parent, false);
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
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }
}

