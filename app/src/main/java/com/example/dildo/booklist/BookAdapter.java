package com.example.dildo.booklist;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;


class BookAdapter extends ArrayAdapter<Book> {

     BookAdapter(@NonNull Context context, @NonNull List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,@NonNull ViewGroup  parent) {

        ViewHolderItem viewHolder;
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);

            viewHolder = new ViewHolderItem();
            viewHolder.rating = (TextView) convertView.findViewById(R.id.book_rating);
            viewHolder.title = (TextView) convertView.findViewById(R.id.book_title);
            viewHolder.author = (TextView) convertView.findViewById(R.id.book_author);
            viewHolder.snippet = (TextView) convertView.findViewById(R.id.book_snippet);
            viewHolder.price = (TextView) convertView.findViewById(R.id.book_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        // Locate the Book at the given position in the bookList
        Book currentbook = getItem(position);

        if (currentbook != null) {
            String formatRating = formatRating(currentbook.getRating());
            viewHolder.rating.setText(formatRating);
            viewHolder.title.setText(currentbook.getTitle());
            viewHolder.author.setText(currentbook.getAuthor());
            viewHolder.snippet.setText(currentbook.getDescription());
            String formatPrice = formatPrice(currentbook.getPrice());
            viewHolder.price.setText(formatPrice);
        }


        return convertView;
    }

    private String formatRating(double rating) {
        DecimalFormat ratingFormat = new DecimalFormat("0.0");
        return ratingFormat.format(rating);
    }

    private String formatPrice(double price) {
        DecimalFormat priceFormat = new DecimalFormat("0.00");
        return priceFormat.format(price);
    }

    private static class ViewHolderItem {
        TextView rating;
        TextView title;
        TextView author;
        TextView snippet;
        TextView price;
    }


}
