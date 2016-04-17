package net.dreameater.chatroomproject.classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.dreameater.chatroomproject.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Activity context_1;
    private ArrayList<Room> pairs;

    public CustomAdapter(Activity context, ArrayList<Room> pairs) {
        context_1 = context;
        this.pairs = pairs;
    }

    public void updateRooms(ArrayList<Room> pairs) {
        this.pairs = pairs;
    }

    @Override
    public int getCount() {
        return pairs.size();
    }

    @Override
    public Room getItem(int position) {
        return pairs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context_1).inflate(R.layout.customlist, null);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) convertView.findViewById(R.id.accessicon);
            viewHolder.txt = (TextView) convertView.findViewById(R.id.RoomItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txt.setText(pairs.get(position).toString());
        int id = context_1.getResources().getIdentifier(
                pairs.get(position).getImg(), "drawable",
                context_1.getPackageName());
        viewHolder.img.setImageResource(id);
        return convertView;
    }

    public class ViewHolder {
        public ImageView img = null;
        public TextView txt = null;
    }
}
