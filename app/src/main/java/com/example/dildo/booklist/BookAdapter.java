package com.example.dildo.booklist;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;


class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(@NonNull Context context, @NonNull List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }
        // Locate the Book at the given position in the bookList
        Book currentbook = getItem(position);

        //Find Rating TextView,format the rating to 1 decimal,display rating
        TextView rating = (TextView) listItemView.findViewById(R.id.book_rating);
        assert currentbook != null;
        String formatRating = formatRating(currentbook.getRating());
        rating.setText(formatRating);


        TextView title = (TextView) listItemView.findViewById(R.id.book_title);
        title.setText(currentbook.getTitle());

        TextView author = (TextView) listItemView.findViewById(R.id.book_author);
        author.setText(currentbook.getAuthor());

        TextView snippet = (TextView) listItemView.findViewById(R.id.book_snippet);
        snippet.setText(currentbook.getDescription());

        TextView price = (TextView) listItemView.findViewById(R.id.book_price);
        String formatPrice = formatPrice(currentbook.getPrice());
        price.setText(formatPrice);


        return listItemView;
    }

    private String formatRating(double rating) {
        DecimalFormat ratingFormat = new DecimalFormat("0.0");
        return ratingFormat.format(rating);
    }

    private String formatPrice(double price) {
        DecimalFormat priceFormat = new DecimalFormat("0.00");
        return priceFormat.format(price);
    }

}
