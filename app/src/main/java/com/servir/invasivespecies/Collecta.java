package com.servir.invasivespecies;

import android.Manifest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.ExifInterface;
import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.servir.invasivespecies.utils.Constantori;
import com.servir.invasivespecies.utils.CustomAdapter;
import com.servir.invasivespecies.utils.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.os.Environment.getExternalStorageDirectory;

public class Collecta extends AppCompatActivity {

    Button btnBack, btnSend, btnFoto;
    String datno, locno;
    String s002, s004, s006, s007, s008, s031, s0313, s005, s02x, s04x;
    RadioGroup rsettle;
    Spinner	s6,s3, s31, s312, s313, s33s;
    EditText s2, s4, s7, s8, s87, s8s;
    RadioButton neww, prevv, newws, prevvs;

    String s003 = "SELECT";
    String s0312 = "SELECT";

    String	s001  = "";
    String	s033  = "";

    String syes = "Accessible";
    String sno = "Inaccessible";
    TextView s1;
    ImageView sceneimage;
    String picnm, s_Lat, s_Lon;
    Context context = this;
    String naniask = "";
    File f, pathGeneral;
    String photosuffix = ".jpg";
    String taken = "nope";
    int erer = 0;
    String lepicpath;
    private final static int PICTURE_STUFF = 1;
    private final static int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 13;

    android.view.View View;
    DatabaseHandler db = DatabaseHandler.getInstance(this);

    private Uri fileUri;

    //spinners with images
    String[] canopy_features = {"SELECT","Trace ( less than 1% )","Low ( 1.0% to 5.0% )","Moderate ( 5.1% to 25% )","High ( 25.1% to 100% )"};
    int canopy_images[] = {R.drawable.select_button_theme,R.drawable.trace_canopy_theme,R.drawable.low_canopy_theme, R.drawable.moderate_canopy_theme, R.drawable.high_canopy_theme};

    String[] species_features = {"SELECT","Single Plant","Scattered Plants","Dense Monoculture","Scattered Dense Patches"};
    int species_images[] = {R.drawable.select_button_theme,R.drawable.single_species,R.drawable.scattered_species, R.drawable.dense_species, R.drawable.scatter_dense_species};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kolek);
        overridePendingTransition(0,0);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        if (Build.VERSION.SDK_INT < 23) {

        }else {
            reqPermission("top");
        }

        btnBack = (Button) findViewById (R.id.backo);
        btnSend = (Button) findViewById (R.id.doneo);
        sceneimage = (ImageView) findViewById(R.id.s12pic);
        rsettle = (RadioGroup) findViewById(R.id.radiops2s);

        s1= (TextView) findViewById (R.id.s1);
        s6= (Spinner) findViewById (R.id.s6);
        s3= (Spinner) findViewById (R.id.s3);
        s31= (Spinner) findViewById (R.id.s31);
        s312= (Spinner) findViewById (R.id.s312);
        s313= (Spinner) findViewById (R.id.s313);
        s33s= (Spinner) findViewById (R.id.s33s);

        s2= (EditText) findViewById (R.id.s2);
        s4= (EditText) findViewById (R.id.s4);
        s7= (EditText) findViewById (R.id.s7);
        s8= (EditText) findViewById (R.id.s8);
        s8s= (EditText) findViewById (R.id.s8s);
        s87= (EditText) findViewById (R.id.s87);

        neww = (RadioButton) findViewById(R.id.neww);
        prevv = (RadioButton) findViewById(R.id.prevv);
        newws = (RadioButton) findViewById(R.id.newws);
        prevvs = (RadioButton) findViewById(R.id.prevvs);
        btnFoto = (Button) findViewById(R.id.bfopic);

        doDBStuff("Entered");

        s1.setText(s001);
        if (!s001.equals("Other")){
            s87.setText("");
            s87.setEnabled(false);
            s87.setVisibility(View.GONE);
        }else{
            s87.setEnabled(true);
            s87.setVisibility(View.VISIBLE);
        }


        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s003 = canopy_features[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomAdapter customAdapterCanopy=new CustomAdapter(getApplicationContext(),canopy_images,canopy_features);
        s3.setAdapter(customAdapterCanopy);

        s312.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s0312 = species_features[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomAdapter customAdapterSpecies=new CustomAdapter(getApplicationContext(),species_images,species_features);
        s312.setAdapter(customAdapterSpecies);


        s31.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0,
                                       android.view.View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String mm = s31.getSelectedItem().toString();

                if (mm.equals("Other")){
                    s8.setEnabled(true);
                    s8.setVisibility(View.VISIBLE);
                }else{
                    s8.setText("");
                    s8.setEnabled(false);
                    s8.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        rsettle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {

                if (newws.isChecked()){

                    s33s.setSelection(0);
                    s33s.setEnabled(false);
                    s33s.setVisibility(View.GONE);

                    s8s.setText("");
                    s8s.setEnabled(false);
                    s8s.setVisibility(View.GONE);

                }else if (prevvs.isChecked()){
                    s33s.setEnabled(true);
                    s33s.setVisibility(View.VISIBLE);
                }


            }
        });


        s33s.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0,
                                       android.view.View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                String mm = s33s.getSelectedItem().toString();

                if (mm.equals("Other")){
                    s8s.setEnabled(true);
                    s8s.setVisibility(View.VISIBLE);
                }else{
                    s8s.setText("");
                    s8s.setEnabled(false);
                    s8s.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });



        btnFoto.setOnClickListener(new OnClickListener(){

            public void onClick(View view){

                pathGeneral = Constantori.folderImages;

                picnm = datno + photosuffix;

                if (Build.VERSION.SDK_INT < 23) {
                    PigaPicha();
                } else {

                    if (
                            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED &&
                                    ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED

                    ) {

                        PigaPicha();

                    } else {

                        reqPermission("Camera");

                    }
                }
            }
        });


        btnBack.setOnClickListener(new OnClickListener(){

            public void onClick(View view){

                Intent intent = new Intent (Collecta.this, Selekta.class);
                intent.putExtra(Constantori.INTENT_LAT, s_Lat);
                intent.putExtra(Constantori.INTENT_LON, s_Lon);
                intent.putExtra(Constantori.INTENT_DATNO, datno);
                intent.putExtra(Constantori.INTENT_LOCNO, locno);
                intent.putExtra(Constantori.INTENT_SPECIES, s001);
                startActivity(intent);

            }
        });


        btnSend.setOnClickListener(new OnClickListener(){

            public void onClick(View view){

                if (s001.equals("Other")){
                    s001 = s87.getText().toString().trim();
                }

                s006  = "";
                s031 = s31.getSelectedItem().toString().trim();
                s0313 = s313.getSelectedItem().toString().trim();

                s02x = "Square Metres";
                s04x = "";

                if(s7.getText().toString().trim().length() == 0){
                    s007 = "None";
                }else{
                    s007 = s7.getText().toString().trim();
                }

                s002 = s2.getText().toString().trim();
                s004 = "";
                s008 = s8.getText().toString().trim();

                if (s031.equals("Other")){
                    s031 = s008;
                }

                if (neww.isChecked()){
                    s005 = sno;
                }else if (prevv.isChecked()){
                    s005 = syes;
                }

                if (newws.isChecked()){
                    s033 = "None";
                }else if (prevvs.isChecked()){

                    if(!s33s.getSelectedItem().toString().trim().equals("Other")){
                        s033 = s33s.getSelectedItem().toString().trim();
                    }else{
                        s033 = s8s.getText().toString().trim();
                    }

                }


                if (
                    s003.equals("SELECT")||
                    s31.getSelectedItem().toString().trim().equals("SELECT")||
                    s0312.equals("SELECT")||
                    s2.getText().toString().equals("")||
                    s033.equals("")
                ){
                    Toast.makeText(Collecta.this, "Please complete the questionnaire first", Toast.LENGTH_LONG ).show();
                }else if(s31.getSelectedItem().toString().trim().equals("Other") &&
                        s8.getText().toString().equals("")){
                    Toast.makeText(Collecta.this, "Please specify the habitat", Toast.LENGTH_LONG ).show();
                }else if(s1.getText().toString().trim().equals("Other") &&
                        s87.getText().toString().equals("")) {
                    Toast.makeText(Collecta.this, "Please specify species", Toast.LENGTH_LONG).show();
                }else{
       			
       			    s002 = s002 + " " + s02x;
                    s004 = "";

                    try {

                        JSONObject submap = new JSONObject();

                        submap.put(Constantori.KEY_DATNO, datno);
                        submap.put(Constantori.KEY_LOCNO,locno);
                        submap.put(Constantori.KEY_DATINDEX,locno);
                        submap.put(Constantori.KEY_DATFTRNAME,s001);
                        submap.put(Constantori.KEY_DATCNT,s006);
                        submap.put(Constantori.KEY_DATIAR,s002);
                        submap.put(Constantori.KEY_DATGAR,s004);
                        submap.put(Constantori.KEY_DATCC,s003);
                        submap.put(Constantori.KEY_DATHAB,s031);
                        submap.put(Constantori.KEY_DATABD,s0312);
                        submap.put(Constantori.KEY_DATOWN,s0313);
                        submap.put(Constantori.KEY_DATARA,s005);
                        submap.put(Constantori.KEY_DATSET,s033);
                        submap.put(Constantori.KEY_DATLAT,s_Lat);
                        submap.put(Constantori.KEY_DATLON,s_Lon);
                        submap.put(Constantori.KEY_DATPICNM,picnm);
                        submap.put(Constantori.KEY_DATCOM,s007);
                        submap.put(Constantori.KEY_DATSTATUS,Constantori.SAVE_DATSTATUS);

                        JSONArray subArray = new JSONArray();

                        subArray.put(submap);

                        if (db.CheckIsDataAlreadyInDBorNot(Constantori.TABLE_DAT, Constantori.KEY_DATNO, datno)){
                            db.updateDataToTable(Constantori.TABLE_DAT, Constantori.KEY_DATNO, datno, subArray);
                        }else{
                            db.insertDataToTable(Constantori.TABLE_DAT, subArray);
                        }

                        JSONObject submap2 = new JSONObject();
                        submap2.put(Constantori.KEY_USERPIC,picnm);
                        submap2.put(Constantori.KEY_USERPICPATH,lepicpath);
                        submap2.put(Constantori.KEY_SENDSTAT,Constantori.SAVE_DATSTATUS);


                        JSONArray subArray2 = new JSONArray();

                        subArray2.put(submap2);

                        db.insertDataToTable(Constantori.TABLE_PIC, subArray2);

                        dlg_saved(View);

                    } catch (JSONException e) {
                        Log.e(Constantori.APP_ERROR_PREFIX + "_KolekError", "exception", e);
                    }


                }

            }
        });


    }

    @Override
    public void onBackPressed(){

    }


    public void dlg_saved(View v) {
        final Dialog mbott = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        mbott.setContentView(R.layout.mbaind_sav);
        mbott.setCanceledOnTouchOutside(false);
        mbott.setCancelable(false);
        WindowManager.LayoutParams lp = mbott.getWindow().getAttributes();
        lp.dimAmount=0.85f;
        mbott.getWindow().setAttributes(lp);
        mbott.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mbott.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Button mbaok = (Button) mbott.findViewById(R.id.mbabtn1);
        mbaok.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){

                finish();

                Intent intent = new Intent (context, MainActivity.class);
                startActivity(intent);

                mbott.dismiss();
            }
        });
        mbott.show();
    }


    private void saveImage(Bitmap finalBitmap) {

        Log.e(Constantori.APP_ERROR_PREFIX+"_CollectPics",pathGeneral + " : " + picnm);

        File file = new File(pathGeneral, picnm);

        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            taken = "yap";
            erer = 1;

            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                sceneimage.setImageBitmap(bitmap);
                lepicpath = file.getAbsolutePath();

                ExifInterface exif = new ExifInterface(lepicpath);
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE,s_Lat);
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, s_Lon);
                exif.saveAttributes();

            }



        }
        catch (Exception e) {
            Toast.makeText(context, "Camera error, take photograph again.", Toast.LENGTH_LONG ).show();
            e.printStackTrace();
        }

        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(this, new String[] { file.toString() }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_STUFF && resultCode == RESULT_OK) {


            Bitmap bitmap;

            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inJustDecodeBounds= false;
            bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    bitmap,
                    String.valueOf(System.currentTimeMillis()),
                    "Description");

            saveImage(bitmap);

            String root2 = getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/";
            File myDir2 = new File(root2);
            File file2 = new File(myDir2, "temp.jpg");
            if (file2.exists()){
                file2.delete();
            }

        }

    }


    private File createImageFile() throws IOException {
        // Create an image file name

        File image = File.createTempFile(
                "temp",
                ".jpg",
                pathGeneral
        );

        f = image;

        return f;
    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        try {
            outState.putParcelable("file_uri", fileUri);
        }catch (Exception ss){

        }
    }

    /*
     * Here we restore the fileUri again
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        try{
            fileUri = savedInstanceState.getParcelable("file_uri");
        }catch (Exception ss){

        }
    }

    @SuppressLint("NewApi")
    private void reqPermission(final String nini) {

        naniask = nini;

        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();

        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("Location");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Storage");
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add("Cell Network");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "This application needs to access your " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }

        return;

    }

    @SuppressLint("NewApi")
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                ) {
                    // All Permissions Granted
                    if (naniask.equals("Camera")) {
                        PigaPicha();
                    }
                    Toast.makeText(context, "Permissions enabled", Toast.LENGTH_LONG).show();

                } else {
                    // Permission Denied
                    Toast.makeText(context, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void PigaPicha() {

        if (Build.VERSION.SDK_INT < 23) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            f = new File(getExternalStorageDirectory(), picnm);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            startActivityForResult(intent, PICTURE_STUFF);

        } else {

            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(Collecta.this, Collecta.this.getApplicationContext().getPackageName() + ".provider", createImageFile()));
                startActivityForResult(intent, PICTURE_STUFF);

            } catch (Exception zz) {
                Log.e(Constantori.APP_ERROR_PREFIX+"_Camera", "exception", zz);
                Toast.makeText(context,  Constantori.ERROR_NO_MEDIA_PERMISSION, Toast.LENGTH_LONG).show();
            }

        }
    }

    private void doDBStuff(String where){

        switch (where){

            case "Entered":

                Intent intent0 = getIntent();
                s_Lat = intent0.getStringExtra(Constantori.INTENT_LAT);
                Intent intent1 = getIntent();
                s_Lon = intent1.getStringExtra(Constantori.INTENT_LON);
                Intent intent2 = getIntent();
                datno = intent2.getStringExtra(Constantori.INTENT_DATNO);
                Intent intent3 = getIntent();
                locno = intent3.getStringExtra(Constantori.INTENT_LOCNO);
                Intent intent4 = getIntent();
                s001 = intent4.getStringExtra(Constantori.INTENT_SPECIES);

                break;

            default:
                break;

        }

    }

}
