package com.example.recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdaptar extends RecyclerView.Adapter<RecyclerAdaptar.MyViewHolder>{

    // TODO 'logt+tab'
    private static final String TAG = "RecyclerAdaptar";

    private List<String> names;

    RecyclerAdaptar(List<String> names) {
        this.names = names;
    }

    private int count = 0;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // TODO 'logi+tab'
        Log.i(TAG, "onCreateViewHolder: "+count++);


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sample_layot,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(names.get(position));
        holder.textView2.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView ;
        TextView textView, textView2;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    // TODO Delete from List
                    names.remove(getAdapterPosition());

                    // TODO Delete from RecyclerView
                    notifyItemRemoved(getAdapterPosition());

                    return true;
                }
            });

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(),names.get(getAdapterPosition()),Toast.LENGTH_SHORT).show();
        }
    }
}
