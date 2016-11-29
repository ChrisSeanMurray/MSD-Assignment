package ie.dit.msd_assignment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Size;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    EditText v_arrows;
    EditText v_venue;
    EditText v_details;
    JournalDBManager dbm;
    Calendar c;
    Button image;
    Button gallery;
    ImageView mImageView;
    byte[] imageStore;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECT_PICTURE = 2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar) ;
        setSupportActionBar(myToolbar);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);

        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }


        //assigning the edittext views to their corresponding elements
        v_arrows = (EditText)findViewById(R.id.arrowCount);
        v_venue = (EditText)findViewById(R.id.venue);
        v_details = (EditText)findViewById(R.id.journalDetails);
        image = (Button)findViewById(R.id.imageButton);
        image.setOnClickListener(this);
        gallery = (Button)findViewById(R.id.gallery);
        gallery.setOnClickListener(this);
        mImageView = (ImageView)findViewById(R.id.mImageView);


        //seting up db connection
        dbm = new JournalDBManager(this);

        //setting up calendar
        c = Calendar.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbaritems, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_save was selected
            case R.id.action_save:

                if(v_venue.getText().toString().length() <= 0 || v_arrows.getText().toString().length()<= 0 || v_details.getText().toString().length() <= 0){
                    Toast.makeText(this, "You have left one of the fields empty", Toast.LENGTH_SHORT).show();
                    break;
                }
                else {
                    int arrowCount = Integer.parseInt(v_arrows.getText().toString());
                    String venue = v_venue.getText().toString();
                    String entry = v_details.getText().toString();



                    //calling system date
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String date = df.format(c.getTime());

                    dbm.open();
                    dbm.insertEntry(arrowCount, date, venue, entry, imageStore);
                    dbm.close();
                    Toast.makeText(this, "Successful insertion", Toast.LENGTH_SHORT).show();
                    v_arrows.setText("");
                    v_details.setText("");
                    v_venue.setText("");
                    mImageView.setImageDrawable(getDrawable(R.drawable.target));
                    break;
                }
            // action with ID action_settings was selected


            case R.id.action_cancel:
                finish();
                break;
            default:
                break;
        }

        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton:
                doCameraStuff();
                break;
            case R.id.gallery:
                doGalleryStuff();
            default:
                break;
        }
    }

    public void doCameraStuff(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }

    }

    public void doGalleryStuff(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
        }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                mImageView.setImageBitmap((Bitmap) extras.get("data"));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = ((Bitmap) extras.get("data"));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
                imageStore=stream.toByteArray();
            }
            else if(requestCode == SELECT_PICTURE){
//                Uri selectedImageUri = data.getData();
//                bitToByte(getPath(selectedImageUri));
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    mImageView.setImageBitmap(selectedImage);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 0, stream);
                    imageStore=stream.toByteArray();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
    }



}
