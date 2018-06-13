package com.example.android.knowgreen;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GreenNewsAdapter extends ArrayAdapter<GreenNews> {

    private Context mContext;

    public GreenNewsAdapter(Activity context, ArrayList<GreenNews> greenNews) {
        super(context, 0, greenNews);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_news_item_display, parent, false);
        }

        final GreenNews currentGreenNews = getItem(position);

        ImageView newsImg = (ImageView) listItemView.findViewById(R.id.news_image);
        assert currentGreenNews != null;
        GlideApp.with(mContext)
                .load(currentGreenNews.getThumbnail())
                .centerCrop()
                .into(newsImg);

        newsImg.setVisibility(View.VISIBLE);

        TextView newsTitle = (TextView) listItemView.findViewById(R.id.news_title);
        newsTitle.setText(currentGreenNews.getNewsTitle());

        TextView newsAuthor = (TextView) listItemView.findViewById(R.id.news_author);
        if (currentGreenNews.getAuthor() != null){
        newsAuthor.setText(currentGreenNews.getAuthor());}


        TextView dateNtime = listItemView.findViewById(R.id.date_time);

        String date = dateFormatter(currentGreenNews.getDateNtime());
        dateNtime.setText(date);

        TextView section = listItemView.findViewById(R.id.section);
        section.setText(currentGreenNews.getSection());



  return listItemView;
    }

        private static String dateFormatter(String dateString) {
            Date date = null;
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss\'Z\'");

            try {
                date = dateFormater.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dateFormater = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");

            return dateFormater.format(date);
        }


}










