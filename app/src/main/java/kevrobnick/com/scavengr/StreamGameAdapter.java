package kevrobnick.com.scavengr;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Created by Kevin on 3/18/2015.
 */
public class StreamGameAdapter extends ArrayAdapter<StreamGame> {
    Context context;
    int layoutResourceId;
    StreamGame data[] = null;

    public StreamGameAdapter(Context context, int layoutResourceId, StreamGame[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        StreamGameHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new StreamGameHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (StreamGameHolder)row.getTag();
        }

        StreamGame sg = data[position];
        holder.txtTitle.setText(sg.title);
        holder.imgIcon.setImageResource(sg.icon);

        return row;
    }

    static class StreamGameHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}
