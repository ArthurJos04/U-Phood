package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class classezinha extends RecyclerView.Adapter<classezinha.MyViewHolder>{

    String dt1[], dt2 [];
    int images[];
    Context context;

    public classezinha(Context ct,String a1[], String a2[],int img[]){

        context = ct;
        dt1 = a1;
        dt2 = a2;
        images = img;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tela, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mytext1.setText(dt1[position]);
        holder.mytext2.setText(dt2 [position]);
        holder.myimage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mytext1, mytext2;
        ImageView myimage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mytext1 = itemView.findViewById(R.id.mytext1);
            mytext2 = itemView.findViewById(R.id.mytext2);
            myimage = itemView.findViewById(R.id.myimage);
        }
    }
}
