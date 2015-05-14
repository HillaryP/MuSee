package edu.uw.prathh.musee.exhibits;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;


import edu.uw.prathh.musee.R;

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private String[] exhibits;

    public GridAdapter(Context c) {
        mContext = c;
        exhibits = getExhibits();
    }

    private String[] getExhibits() {
        List<ParseObject> names;
        ParseQuery<ParseObject> query = new ParseQuery<>("Exhibits");
        query.orderByDescending("name");
        try {
            names = query.find();
            String[] nameArray = new String[names.size()];
            int index = 0;
            for (ParseObject name : names) {
                nameArray[index] = name.getString("name");
                index++;
            }
            Log.i("GridAdapter", "Name Array: " + Arrays.toString(nameArray));
            return nameArray;
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public int getCount() {
        return exhibits.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_layout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.exhibit_name_image);
            imageView.setImageResource(R.drawable.photos);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            TextView textView = (TextView) view.findViewById(R.id.exhibit_name_text);
            textView.setText(exhibits[position]);
        } else {
            view = convertView;
        }
        return view;
    }
}