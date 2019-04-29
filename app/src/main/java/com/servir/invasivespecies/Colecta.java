package com.servir.invasivespecies;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.*;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBarActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.servir.invasivespecies.utils.CustomAdapter;

public class Colecta extends AppCompatActivity {
	
	Button rudi;
	Button tuma;
	Button btnfoto;
	String s002, s004, s006, s007, s008, s031,s0313, s005,s02x,s04x ;
	String s003 = "SELECT";
    String s0312 = "SELECT";
	String datno = "";
	String	sax = "";
	String	say = "";
	String huyu = "user";
	String	s001  = "";
	String	xcon  = "";
	String	xorg  = "";
	String syes = "Accessible";
	String sno = "Inaccessible";
	View View;
	SQLiteDatabase spatiadb;
	ArrayAdapter<String> spinnerArrayAdapter;
	Spinner	s6,s3, s31, s312, s313/*, s2x, s4x*/;
	EditText s2, s4, s7, s8, s87;
	TextView s1;
	RadioButton neww, prevv; 
	String picnm = "";
	String lepic;
    Context context = this;
    String naniask = "";
    String lepicnm;
    File f;
    String taken = "nope";
    int erer = 0;
	
	String datloc;
	String rdatno = "";
    String lepicpath;
	ImageView sceneimage;
	private final static int PICTURE_STUFF = 1;
    private final static int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 13;
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

		rudi = (Button) findViewById (R.id.backo);
        tuma = (Button) findViewById (R.id.doneo);
		sceneimage = (ImageView) findViewById(R.id.s12pic); 
        
        s1= (TextView) findViewById (R.id.s1);
        s6= (Spinner) findViewById (R.id.s6);
        s3= (Spinner) findViewById (R.id.s3);
        s31= (Spinner) findViewById (R.id.s31);
        s312= (Spinner) findViewById (R.id.s312);
        s313= (Spinner) findViewById (R.id.s313);
        /*s2x= (Spinner) findViewById (R.id.s2x);
        s4x= (Spinner) findViewById (R.id.s4x);*/
      
        s2= (EditText) findViewById (R.id.s2);
        s4= (EditText) findViewById (R.id.s4);
        s7= (EditText) findViewById (R.id.s7);
        s8= (EditText) findViewById (R.id.s8);
		s87= (EditText) findViewById (R.id.s87);
        
        neww = (RadioButton) findViewById(R.id.neww);
		prevv = (RadioButton) findViewById(R.id.prevv);
		btnfoto = (Button) findViewById(R.id.bfopic);
		/*neww2 = (RadioButton) findViewById(R.id.neww2);
		prevv2 = (RadioButton) findViewById(R.id.prevv2);
		neww3 = (RadioButton) findViewById(R.id.neww3);
		prevv3 = (RadioButton) findViewById(R.id.prevv3);
		neww4 = (RadioButton) findViewById(R.id.neww4);
		prevv4 = (RadioButton) findViewById(R.id.prevv4);*/
		
    
         Intent intentqa = getIntent();
		sax = intentqa.getStringExtra("lonnn");
		Intent intentqasp = getIntent();
		s001 = intentqasp.getStringExtra("spcs");
		 Intent intentqsa = getIntent();
		 say = intentqsa.getStringExtra("lattt");
		 Intent intentqdsa = getIntent();
		 datno = intentqdsa.getStringExtra("datno");

		Intent intentorg = getIntent();
		xorg = intentorg.getStringExtra("orgaa");

		Intent intentcon = getIntent();
		xcon = intentcon.getStringExtra("consa");

		    rdatno = datno.replace(":","_");
		    rdatno = rdatno.replace("-","_");
       
       
       spatiadb=openOrCreateDatabase("InvSpecDB", Context.MODE_PRIVATE, null);
		spatiadb.execSQL("CREATE TABLE IF NOT EXISTS datTBL(datno VARCHAR,datftrname VARCHAR,datcnt VARCHAR,datiar VARCHAR,datgar VARCHAR,datcc VARCHAR,dathab VARCHAR,databd VARCHAR,datown VARCHAR,datara VARCHAR,datcom VARCHAR,datx VARCHAR,daty VARCHAR,datpicnm VARCHAR,datorg VARCHAR,datcon VARCHAR);");
		spatiadb.execSQL("CREATE TABLE IF NOT EXISTS picTBL(userpic VARCHAR, userpicpath VARCHAR, sendstat VARCHAR);");



		/*s1.setOnItemSelectedListener(new OnItemSelectedListener() {


			public void onItemSelected(AdapterView<?> arg0,
									   android.view.View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				String mm = s1.getSelectedItem().toString();

				if (mm.equals("Other")){
					s87.setEnabled(true);
				}else{
					s87.setText("");
					s87.setEnabled(false);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});*/

		s1.setText(s001);
		if (!s001.equals("Other")){
			s87.setText("");
			s87.setEnabled(false);
		}else{
			s87.setEnabled(true);
		}


        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "You Select Position: "+position+" "+fruits[position], Toast.LENGTH_SHORT).show();
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
	 	       }else{
	 	    	  s8.setText("");
	 	    	  s8.setEnabled(false);
	 	       }
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}

     	});
       
       
       btnfoto.setOnClickListener(new OnClickListener(){
	    	
	    	public void onClick(View view){

				
                if (Build.VERSION.SDK_INT < 23) {
                    PigaPicha();
                } else {

                    if (
                            ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                    ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED &&
                                    ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                                    ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED

                            ) {

                        PigaPicha();

                    } else {

                        reqPermission("anaingia");

                    }


                }



	    	       }
	      });
       
       
    rudi.setOnClickListener(new OnClickListener(){
        	
        	public void onClick(View view){
        		Intent intent = new Intent (Colecta.this, Selekta.class);

				intent.putExtra("lattt", say);
				intent.putExtra("lonnn", sax);
				intent.putExtra("datno", datno);
				intent.putExtra("orgaa", xorg);
				intent.putExtra("consa", xcon);

				startActivity(intent);
        	}
    });
    
        	
    tuma.setOnClickListener(new OnClickListener(){
             	
             	public void onClick(View view){
             	
                  //s001 = s1.getSelectedItem().toString().trim();

					if (s001.equals("Other")){
						s001 = s87.getText().toString().trim();
					}


		    	  //s006 = s6.getSelectedItem().toString().trim();
		    	  s006  = "";
		    	  //s003 = s3.getSelectedItem().toString().trim();
		    	  s031 = s31.getSelectedItem().toString().trim();
		    	  //s0312 = s312.getSelectedItem().toString().trim();
		    	  s0313 = s313.getSelectedItem().toString().trim();
		    	  
		    	  s02x = "Square Metres";
		    	  s04x = "Square Metres";
       		      
       		      
             		if(s7.getText().toString().trim().length() == 0){
       		    	s007 = "None";
       		      }else{
       		    	s007 = s7.getText().toString().trim();  
       		      }


             	  s002 = s2.getText().toString().trim();
       		      s004 = s4.getText().toString().trim();
       		      s007 = s7.getText().toString().trim();
       		      s008 = s8.getText().toString().trim();

					s001 = s001.replace("'", "''");
       		      s002 = s002.replace("'", "''");
       		      s004 = s004.replace("'", "''");
       		      s007 = s007.replace("'", "''");
       		      s008 = s008.replace("'", "''");


       		      s002 = s002.replace(",", "~");
    		      s004 = s004.replace(",", "~");
    		      s001 = s001.replace(",", "~");
    		      s007 = s007.replace(",", "~");
    		      s008 = s008.replace(",", "~");
       		      
       		      if (s031.equals("Other")){
       		    	  s031 = s008;
       		      }
       		      
       		      if (neww.isChecked()){
       		    	  s005 = sno;
       		      }else if (prevv.isChecked()){
       		    	  s005 = syes;
       		      }
       		      
       		      /*
       		   if (neww2.isChecked()){
    		    	  s009 = sno;
    		      }else if (prevv2.isChecked()){
    		    	  s009 = syes;
    		      }
       		   
       		 if (neww3.isChecked()){
  		    	  s010 = sno;
  		      }else if (prevv3.isChecked()){
  		    	  s010 = syes;
  		      }
       		 
       		 if (neww4.isChecked()){
  		    	  s011 = sno;
  		      }else if (prevv4.isChecked()){
  		    	  s011 = syes;
  		      }*/
       		
       		if (
       			//s1.getSelectedItem().toString().trim().equals("SELECT")||
       			s003.equals("SELECT")||
       			//s6.getSelectedItem().toString().trim().equals("SELECT")||
       			s31.getSelectedItem().toString().trim().equals("SELECT")||
                        s0312.equals("SELECT")||
       			//s312.getSelectedItem().toString().trim().equals("SELECT")||
                        // s313.getSelectedItem().toString().trim().equals("SELECT")||
       			/*s2x.getSelectedItem().toString().trim().equals("UNITS")||
       			s4x.getSelectedItem().toString().trim().equals("UNITS")||*/
       			s2.getText().toString().equals("")||
       			s4.getText().toString().equals("")||
       			s7.getText().toString().equals("")){
       			
       			Toast.makeText(Colecta.this, "Please complete the questionnaire first", Toast.LENGTH_LONG ).show();
       		
       		}else if(s31.getSelectedItem().toString().trim().equals("Other") && 
       				 s8.getText().toString().equals("")){
       		
       			Toast.makeText(Colecta.this, "Please specify the habitat", Toast.LENGTH_LONG ).show();
       		
       		}else if(s1.getText().toString().trim().equals("Other") &&
				s87.getText().toString().equals("")) {

				Toast.makeText(Colecta.this, "Please specify species", Toast.LENGTH_LONG).show();

			}else{
       			
       			   /*if (!s1.getSelectedItem().toString().equals("Dam")){
       				   s017 = "N/A";
       			   }*/
       				
       				s002 = s002 + " " + s02x;
       				s004 = s004 + " " + s04x;

       				
       			
       			    Cursor chk=spatiadb.rawQuery("SELECT * FROM datTBL WHERE datno='"+datno+"'", null);
		  	 		if(chk.moveToFirst())

		  	 		{	
		      		  spatiadb.execSQL("UPDATE datTBL SET datftrname='"+s001+"',datcnt='"+s006+"',datiar='"+s002+"',datgar='"+s004+"',datcc='"+s003+"',dathab='"+s031+"',databd='"+s0312+"',datown='"+s0313+"',datara='"+s005+"',datcom='"+s007+"',datx='"+sax+"',daty='"+say+"',datpicnm='"+picnm+"',datorg='"+xorg+"',datcon='"+xcon+"' WHERE datno='"+datno+"'");
		  	 	    }
		  	 		else
		  	 		{
		  	 	     spatiadb.execSQL("INSERT INTO datTBL VALUES('"+datno+"','"+s001+"','"+s006+"','"+s002+"','"+s004+"','"+s003+"','"+s031+"','"+s0312+"','"+s0313+"','"+s005+"','"+s007+"','"+sax+"','"+say+"','"+picnm+"','"+xorg+"','"+xcon+"');");
		  	 		}
					chk.close();
					
					String bado = "pending";

				if (!picnm.equals("")) {

					Cursor chk2 = spatiadb.rawQuery("SELECT * FROM picTBL WHERE userpic='" + picnm + "'", null);
					if (chk2.moveToFirst()) {
						spatiadb.execSQL("UPDATE picTBL SET userpic='" + picnm + "',userpicpath='" + lepicpath + "',sendstat='" + bado + "' WHERE userpic='" + picnm + "'");
					} else {
						spatiadb.execSQL("INSERT INTO picTBL VALUES('" + picnm + "','" + lepicpath + "','" + bado + "');");
					}
					chk2.close();

				}


             		
		  	 		
             	    diambaids(View);
       		}
             		
             	}
    });   

       
    }
	
	@Override
	public void onBackPressed(){
		
	}
	
	
	public void diambaids(View v) {
		final Dialog mbott = new Dialog(Colecta.this, android.R.style.Theme_Translucent_NoTitleBar);
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
				
				s2.setText("");
				s4.setText("");
				s7.setText("");
				s8.setText("");
				//s1.setSelection(0);
				s3.setSelection(0);
				s6.setSelection(0);
				s31.setSelection(0);
				s312.setSelection(0);
				s313.setSelection(0);
				neww.setChecked(true);
				
				Intent intent = new Intent (Colecta.this, MainActivity.class);
	        	startActivity(intent);
				
	            
					mbott.dismiss();
			}
		});
		mbott.show();
	}


    private void PigaPicha() {

        String tempopic = Environment.getExternalStorageDirectory().getAbsolutePath() + "/IMGISMS";

        if (Build.VERSION.SDK_INT < 23) {



            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            f = new File(tempopic, "temp.jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            startActivityForResult(intent, PICTURE_STUFF);

        } else {

            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", createImageFile()));
                startActivityForResult(intent, PICTURE_STUFF);

            } catch (Exception zz) {
                Log.e("camera", zz.getMessage());
                Toast.makeText(context, "Please enable permissions for camera", Toast.LENGTH_LONG).show();
            }
        }
    }



    private void saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/IMGISMS";
        File myDir = new File(root);

        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        picnm = datno +   ".jpg";
        Log.e("aaaaaaaaa", datno);
        File file = new File(myDir, picnm);

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

        if (requestCode==PICTURE_STUFF && resultCode == RESULT_OK){



            Bitmap bitmap;

            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inJustDecodeBounds= false;
            bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    bitmap,
                    String.valueOf(System.currentTimeMillis()),
                    "Description");
            saveImage(bitmap);

            String root2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/IMGISMS";
            File myDir2 = new File(root2);
            File file2 = new File(myDir2, "temp.jpg");
            if (file2.exists()){
                file2.delete();
            }

        }


    }


    private File createImageFile() throws IOException {
        // Create an image file name

        String root = Environment.getExternalStorageDirectory().getAbsolutePath() +"/IMGISMS";
        File myDir = new File(root);

        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        picnm = "temp";



        File image = File.createTempFile(
                picnm,
                ".jpg",
                myDir
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

        if (!addPermission(permissionsList, android.Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("Location");
        if (!addPermission(permissionsList, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Storage");
        if (!addPermission(permissionsList, android.Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, android.Manifest.permission.READ_PHONE_STATE))
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
                perms.put(android.Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
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
     
     

	

}
