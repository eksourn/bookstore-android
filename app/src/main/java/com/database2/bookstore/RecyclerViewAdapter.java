package com.database2.bookstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<JSONObject> books;

    public RecyclerViewAdapter(ArrayList<JSONObject> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list,
                parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        //  Log.i(TAG, "Obj ArrayList: " + objList.get(0).getString("isbn"));
        try {
            holder.title.setText(books.get(position).getString("title"));
            holder.isbn.setText(books.get(position).getString("isbn"));
            holder.genre.setText(books.get(position).getString("genre"));

            holder.rating.setText(books.get(position).getString("rating"));
            try{
                String count = books.get(position).getString("book_count");
                holder.qtyLbl.setText("Qauntity");
                holder.price.setText(books.get(position).getString("book_count"));

            }catch (Exception e){
                holder.price.setText("$"+books.get(position).getString("price"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView isbn;
        private TextView genre;
        private  TextView price;
        private TextView rating;
        private TextView qtyLbl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            isbn = itemView.findViewById(R.id.textViewISBN);
            genre = itemView.findViewById(R.id.textViewGenre);
            price = itemView.findViewById(R.id.textViewPrice);
            rating = itemView.findViewById(R.id.textViewRating);
            qtyLbl = itemView.findViewById(R.id.textViewDual);
        }
    }
}
