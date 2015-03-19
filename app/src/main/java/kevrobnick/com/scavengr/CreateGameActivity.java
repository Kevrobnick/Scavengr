package kevrobnick.com.scavengr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraCaptureSession;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.Image;
import android.widget.Toast;
import android.media.*;
import android.database.*;

import java.io.IOException;


public class CreateGameActivity extends ActionBarActivity {
  //Some additional items needed for the massive DB store when taking a photo

    final static int CREATE_GAME_ACTIVITY_REQUEST_CODE =1;
    Uri imageUri = null;
    static TextView imageDetails = null;
    public static ImageView showImg = null;
    CreateGameActivity CameraActivity = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        imageDetails = (TextView) findViewById(R.id.imageDetails);
        showImg = (ImageView) findViewById(R.id.showImg);
        CameraActivity =this;

        // TODO: Picture taken from camera to database
        ImageButton createGameButton = (ImageButton)findViewById(R.id.cameraButton);
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCamera = new Intent("android.media.action.IMAGE_CAPTURE");

               // Some additional intent attributes

                openCamera.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                openCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
                startActivityForResult(openCamera, CREATE_GAME_ACTIVITY_REQUEST_CODE);
                // define file name to save the photo taken
                String fileName = "Camera_Example.jpg";
                //Making peramiters
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");
                // this is the current activity attribute, define and save for later here
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            }
        });
        //
    }
    protected void onActivityResult( int requestCode, int resultCode, Intent data)
    {
        if( requestCode == CREATE_GAME_ACTIVITY_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                String imageId = convertImageUriToFile (imageUri,CameraActivity);
                // Create and execute an AsyncTask to load captured image
                new LoadImagesFromSDCard().execute(""+imageId);
            }
            else if (resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this,"Picture Not Taken",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Picture not taken", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static String convertImageUriToFile (Uri imageUri, Activity activity)
    {
        Cursor cursor = null;
        int imageID = 0;
        try{
            String[] proj ={
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.ImageColumns.ORIENTATION
            };
            cursor = activity.managedQuery(imageUri, proj,null,null,null);

            // Get data for query

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            int size = cursor.getCount();
            if (size == 0){
                imageDetails.setText("No Image");
            } else{
                int thumbID = 0;
                if (cursor.moveToFirst()){
                    imageID = cursor.getInt(columnIndex);
                    thumbID = cursor.getInt(columnIndexThumb);
                    String Path = cursor.getString(file_ColumnIndex);

                    String CapturedImageDetails =  "CapturedImageDetails \n\n"
                            +" ImageID :"+imageID+"\n"
                            +" ThumbID :"+thumbID+"\n"
                            +" Path :"+Path+"\n";
                    // display captured image details on the activity
                    imageDetails.setText(CapturedImageDetails);
                }
            }

         }finally {
            if (cursor != null){
                cursor.close();
            }
        }
        return ""+imageID;
    }

    public class LoadImagesFromSDCard extends AsyncTask<String,Void,Void>{
        private ProgressDialog Dialog = new ProgressDialog(CreateGameActivity.this);
        Bitmap mBitmap;

        protected void onPreExecute(){
            Dialog.setMessage("Loading image from SDcard...");
            Dialog.show();
        }
        protected Void doInBackground (String...urls){
            Bitmap bitmap = null;
            Bitmap newBitmap = null;
            Uri uri = null;
            try{
               uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ""+urls[0]);
               bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                if (bitmap != null){
                    newBitmap = Bitmap.createScaledBitmap(bitmap, 170, 170, true);
                    bitmap.recycle();
                    if (newBitmap != null){
                        mBitmap = newBitmap;
                    }
                }

            }catch (IOException e)
            {
                cancel(true);
            }
        return null;
        }
        protected void onPostCreate(Void unused){
            Dialog.dismiss();
            if (mBitmap != null){
                showImg.setImageBitmap(mBitmap);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
