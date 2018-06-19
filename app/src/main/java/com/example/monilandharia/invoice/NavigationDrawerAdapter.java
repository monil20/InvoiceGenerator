package com.example.monilandharia.invoice;

/**
 * Created by Monil Andharia on 30-Mar-16.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    int flag=0;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        if(flag==0){
            holder.image.setImageResource(R.drawable.ic_invoice);
        }
        else if(flag==1){
            holder.image.setImageResource(R.drawable.ic_product);
        }
        else if(flag==2){
            holder.image.setImageResource(R.drawable.ic_client1);
        }
        else if(flag==3){
            holder.image.setImageResource(R.drawable.settings);
        }
        flag++;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.imageView2);

            Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/ProductSans-Regular.ttf");
            title.setTypeface(typeface);
        }
    }
}
