package com.servir.invasivespecies;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class Selekta extends ActionBarActivity {
	

	Button tuma;
	String datno="";
	String sax="";
	String say="";
	String xcon="";
	String xorg="";
	String s001="";
	String rdatno;


	ImageView im1;
    ImageView im2;
    ImageView im3;
    ImageView im4;
    ImageView im5;
    ImageView im6;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapicha);
		overridePendingTransition(0, 0);

		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setIcon(R.mipmap.ic_launcher);
		getSupportActionBar().setHomeButtonEnabled(true);

		tuma = (Button) findViewById(R.id.selecto);

		im1 = (ImageView) findViewById(R.id.im1);
		im2 = (ImageView) findViewById(R.id.im2);
		im3 = (ImageView) findViewById(R.id.im3);
		im4 = (ImageView) findViewById(R.id.im4);
		im5 = (ImageView) findViewById(R.id.im5);
		im6 = (ImageView) findViewById(R.id.im6);

		Intent intentqa = getIntent();
		sax = intentqa.getStringExtra("lonnn");
		Intent intentqsa = getIntent();
		say = intentqsa.getStringExtra("lattt");
		Intent intentqdsa = getIntent();
		datno = intentqdsa.getStringExtra("datno");

		Intent intentorg = getIntent();
		xorg = intentorg.getStringExtra("orgaa");

		Intent intentcon = getIntent();
		xcon = intentcon.getStringExtra("consa");

		im1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable highlight = getResources().getDrawable(R.drawable.highlighta);
				im1.setBackground(highlight);
				im2.setBackground(null);
				im3.setBackground(null);
				im4.setBackground(null);
				im5.setBackground(null);
				im6.setBackground(null);
				s001 = "Acacia Reficiens";
			}
		});

		im2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable highlight = getResources().getDrawable(R.drawable.highlighta);
				im2.setBackground(highlight);
				im1.setBackground(null);
				im3.setBackground(null);
				im4.setBackground(null);
				im5.setBackground(null);
				im6.setBackground(null);
				s001 = "Opuntia Species";
			}
		});

		im3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable highlight = getResources().getDrawable(R.drawable.highlighta);
				im3.setBackground(highlight);
				im2.setBackground(null);
				im1.setBackground(null);
				im4.setBackground(null);
				im5.setBackground(null);
				im6.setBackground(null);
				s001 = "Lantana Camara";
			}
		});

		im4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable highlight = getResources().getDrawable(R.drawable.highlighta);
				im4.setBackground(highlight);
				im2.setBackground(null);
				im3.setBackground(null);
				im1.setBackground(null);
				im5.setBackground(null);
				im6.setBackground(null);
				s001 = "Prosopis Species";
			}
		});

		im5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable highlight = getResources().getDrawable(R.drawable.highlighta);
				im5.setBackground(highlight);
				im2.setBackground(null);
				im3.setBackground(null);
				im4.setBackground(null);
				im1.setBackground(null);
				im6.setBackground(null);
				s001 = "Water Hyacinth";
			}
		});

		im6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable highlight = getResources().getDrawable(R.drawable.highlighta);
				im6.setBackground(highlight);
				im2.setBackground(null);
				im3.setBackground(null);
				im4.setBackground(null);
				im5.setBackground(null);
				im1.setBackground(null);
				s001 = "Other";
			}
		});


		tuma.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
			if (!s001.equals("")) {
				Intent intent = new Intent(Selekta.this, Colecta.class);
				intent.putExtra("lattt", say);
				intent.putExtra("lonnn", sax);
				intent.putExtra("datno", datno);
				intent.putExtra("spcs", s001);
				intent.putExtra("orgaa", xorg);
				intent.putExtra("consa", xcon);
					startActivity(intent);
			}else{
				Toast.makeText(Selekta.this, "Please select a species first.", Toast.LENGTH_LONG ).show();
			}
			}
		});
	}
	
	@Override
	public void onBackPressed(){
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			// Respond to the action bar's Up/Home button
			case android.R.id.home:
				//NavUtils.navigateUpFromSameTask(this);
				Intent intent = new Intent(Selekta.this, MainActivity.class);
				startActivity(intent);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	


}
