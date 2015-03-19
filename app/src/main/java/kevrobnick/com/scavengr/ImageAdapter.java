package kevrobnick.com.scavengr;

/**
 * Created by Rob on 3/18/2015.
 */

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import kevrobnick.com.database.Game;


public class ImageAdapter extends ArrayAdapter<Game>{
    Context context;
    int layoutResourceId;

   ArrayList<Game> data = new ArrayList<Game>();
    public ImageAdapter(Context context, int layoutResourceId, ArrayList<Game> data) {
        super(context, layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        ImageHolder holder = null;

        if(row == null)
        {
            LayoutInflater inFlater = ((Activity)context).getLayoutInflater();
            row = inFlater.inflate(layoutResourceId,parent,false);

            holder = new ImageHolder();
            holder.txtTitle =  (TextView)row.findViewById(R.id.txtTitle);
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            row.setTag(holder);
        }
        else
        {
            holder = (ImageHolder)row.getTag();
        }
        Game picture = data.get(position);
        holder.txtTitle.setText(picture.get_name());
//convert byte to bitmap take from Game class
        byte[] outImage=picture.get_image();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.imgIcon.setImageBitmap(theImage);
        return row;

    }
    static class ImageHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }

}
