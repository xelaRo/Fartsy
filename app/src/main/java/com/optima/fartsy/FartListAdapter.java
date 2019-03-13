package com.optima.fartsy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.optima.fartsy.model.FartListModel;

import java.util.ArrayList;

public class FartListAdapter extends ArrayAdapter<FartListModel> {

    public FartListAdapter(Context context, ArrayList<FartListModel> fartListSounds) {
        super(context, 0, fartListSounds);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        FartListModel fartListModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_fart_list, parent, false);
        }

        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_fartList_btn);
        tvName.setText(fartListModel.buttonText);
        // Populate the data into the template view using the data object

        // Return the completed view to render on screen
        return convertView;
    }


}
