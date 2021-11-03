package com.example.android.quakereportapp;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.android.quakereportapp.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.word_list, parent, false);
        }


        Earthquake currEarthquake = getItem(position);

        TextView magTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        magTextView.setText(formatMagnitude(currEarthquake.getMag()));

        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();
        int magnitudeColor = getMagnitudeColor(currEarthquake.getMag());
        magnitudeCircle.setColor(magnitudeColor);


        TextView placeTextView1 = (TextView) listItemView.findViewById(R.id.place1);
        TextView placeTextView2 = (TextView) listItemView.findViewById(R.id.place2);
        if(currEarthquake.getPlace().contains("km")) {
            String[] parts = currEarthquake.getPlace().split(" of ");
            placeTextView1.setText(parts[0] + " of");
            placeTextView2.setText(parts[1]);
        } else{
            placeTextView1.setText("Near the");
            placeTextView2.setText(currEarthquake.getPlace());
        }


        Date dateObject = new Date(currEarthquake.getTimeInMilliseconds());
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeView.setText(formattedTime);

        return listItemView;
        }


    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);

    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }



    }

