package com.example.dildo.booklist;


class Book {
    private final double mRating;
    private final String mTitle;
    private final String mAuthor;
    private final String mDescription;
    private final double mPrice;


    public Book(double rating, String title, String author, String description, double price) {
        mRating = rating;
        mTitle = title;
        mAuthor = author;
        mDescription = description;
        mPrice = price;

    }

    public double getRating() {
        return mRating;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDescription() {
        return mDescription;
    }

    public double getPrice() {
        return mPrice;
    }

}
