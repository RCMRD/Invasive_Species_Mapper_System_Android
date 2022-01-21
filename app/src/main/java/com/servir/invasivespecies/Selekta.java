package com.servir.invasivespecies;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.servir.invasivespecies.utils.Constantori;


public class Selekta extends AppCompatActivity {
	

	Button btnSelect;
	String datno, locno, s_Lat, s_Lon, s001;
	
	ImageView im1, im2, im3, im4, im5, im6, im7, im8, im9, im10, im11, im12, im13;
    ImageView imOther;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapicha);
		overridePendingTransition(0, 0);

		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setIcon(R.mipmap.ic_launcher);
		getSupportActionBar().setHomeButtonEnabled(true);

		btnSelect = (Button) findViewById(R.id.selecto);

		im1 = (ImageView) findViewById(R.id.im1);
		im2 = (ImageView) findViewById(R.id.im2);
		im3 = (ImageView) findViewById(R.id.im3);
		im4 = (ImageView) findViewById(R.id.im4);
		im5 = (ImageView) findViewById(R.id.im5);
		im6 = (ImageView) findViewById(R.id.im6);
		im7 = (ImageView) findViewById(R.id.im7);
		im8 = (ImageView) findViewById(R.id.im8);
		im9 = (ImageView) findViewById(R.id.im9);
		im10 = (ImageView) findViewById(R.id.im10);
		im11 = (ImageView) findViewById(R.id.im11);
		im12 = (ImageView) findViewById(R.id.im12);
		im13 = (ImageView) findViewById(R.id.im13);
		imOther = (ImageView) findViewById(R.id.imOther);

		doDBStuff("Entered");

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
				im7.setBackground(null);
				im8.setBackground(null);
				im9.setBackground(null);
				im10.setBackground(null);
				im11.setBackground(null);
				im12.setBackground(null);
				im13.setBackground(null);
				imOther.setBackground(null);
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
				im7.setBackground(null);
				im8.setBackground(null);
				im9.setBackground(null);
				im10.setBackground(null);
				im11.setBackground(null);
				im12.setBackground(null);
				im13.setBackground(null);
				imOther.setBackground(null);
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
				im7.setBackground(null);
				im8.setBackground(null);
				im9.setBackground(null);
				im10.setBackground(null);
				im11.setBackground(null);
				im12.setBackground(null);
				im13.setBackground(null);
				imOther.setBackground(null);
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
				im7.setBackground(null);
				im8.setBackground(null);
				im9.setBackground(null);
				im10.setBackground(null);
				im11.setBackground(null);
				im12.setBackground(null);
				im13.setBackground(null);
				imOther.setBackground(null);
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
				im7.setBackground(null);
				im8.setBackground(null);
				im9.setBackground(null);
				im10.setBackground(null);
				im11.setBackground(null);
				im12.setBackground(null);
				im13.setBackground(null);
				imOther.setBackground(null);
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
				im7.setBackground(null);
				im8.setBackground(null);
				im9.setBackground(null);
				im10.setBackground(null);
				im11.setBackground(null);
				im12.setBackground(null);
				im13.setBackground(null);
				imOther.setBackground(null);
				s001 = "Chromolaena Odorata";
			}
		});

		im7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable highlight = getResources().getDrawable(R.drawable.highlighta);
				im7.setBackground(highlight);
				im2.setBackground(null);
				im3.setBackground(null);
				im4.setBackground(null);
				im5.setBackground(null);
				im1.setBackground(null);
				im6.setBackground(null);
				im8.setBackground(null);
				im9.setBackground(null);
				im10.setBackground(null);
				im11.setBackground(null);
				im12.setBackground(null);
				im13.setBackground(null);
				imOther.setBackground(null);
				s001 = "Parthenium Hysterophorus";
			}
		});

		im8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable highlight = getResources().getDrawable(R.drawable.highlighta);
				im8.setBackground(highlight);
				im2.setBackground(null);
				im3.setBackground(null);
				im4.setBackground(null);
				im5.setBackground(null);
				im1.setBackground(null);
				im6.setBackground(null);
				im7.setBackground(null);
				im9.setBackground(null);
				im10.setBackground(null);
				im11.setBackground(null);
				im12.setBackground(null);
				im13.setBackground(null);
				imOther.setBackground(null);
				s001 = "Prosopis Juliflora Species";
			}
		});

		im9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable highlight = getResources().getDrawable(R.drawable.highlighta);
				im9.setBackground(highlight);
				im2.setBackground(null);
				im3.setBackground(null);
				im4.setBackground(null);
				im5.setBackground(null);
				im1.setBackground(null);
				im6.setBackground(null);
				im7.setBackground(null);
				im8.setBackground(null);
				im10.setBackground(null);
				im11.setBackground(null);
				im12.setBackground(null);
				im13.setBackground(null);
				imOther.setBackground(null);
				s001 = "Opuntia Stricta";
			}
		});

		im10.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable highlight = getResources().getDrawable(R.drawable.highlighta);
				im10.setBackground(highlight);
				im2.setBackground(null);
				im3.setBackground(null);
				im4.setBackground(null);
				im5.setBackground(null);
				im1.setBackground(null);
				im6.setBackground(null);
				im7.setBackground(null);
				im8.setBackground(null);
				im9.setBackground(null);
				im11.setBackground(null);
				im12.setBackground(null);
				im13.setBackground(null);
				imOther.setBackground(null);
				s001 = "Tithonia Diversifolia Species";
			}
		});

		im11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable highlight = getResources().getDrawable(R.drawable.highlighta);
				im10.setBackground(highlight);
				im2.setBackground(null);
				im3.setBackground(null);
				im4.setBackground(null);
				im5.setBackground(null);
				im1.setBackground(null);
				im6.setBackground(null);
				im7.setBackground(null);
				im8.setBackground(null);
				im9.setBackground(null);
				im10.setBackground(null);
				im12.setBackground(null);
				im13.setBackground(null);
				imOther.setBackground(null);
				s001 = "Camphor Tree";
			}
		});

		im12.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable highlight = getResources().getDrawable(R.drawable.highlighta);
				im10.setBackground(highlight);
				im2.setBackground(null);
				im3.setBackground(null);
				im4.setBackground(null);
				im5.setBackground(null);
				im1.setBackground(null);
				im6.setBackground(null);
				im7.setBackground(null);
				im8.setBackground(null);
				im9.setBackground(null);
				im10.setBackground(null);
				im11.setBackground(null);
				im13.setBackground(null);
				imOther.setBackground(null);
				s001 = "Morning Glory";
			}
		});

		im13.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable highlight = getResources().getDrawable(R.drawable.highlighta);
				im10.setBackground(highlight);
				im2.setBackground(null);
				im3.setBackground(null);
				im4.setBackground(null);
				im5.setBackground(null);
				im1.setBackground(null);
				im6.setBackground(null);
				im7.setBackground(null);
				im8.setBackground(null);
				im9.setBackground(null);
				im10.setBackground(null);
				im11.setBackground(null);
				im12.setBackground(null);
				imOther.setBackground(null);
				s001 = "Sodom Apple";
			}
		});

		imOther.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Drawable highlight = getResources().getDrawable(R.drawable.highlighta);
				imOther.setBackground(highlight);
				im2.setBackground(null);
				im3.setBackground(null);
				im4.setBackground(null);
				im5.setBackground(null);
				im1.setBackground(null);
				im6.setBackground(null);
				im7.setBackground(null);
				im8.setBackground(null);
				im9.setBackground(null);
				im10.setBackground(null);
				s001 = "Other";
			}
		});




		btnSelect.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
			if (!s001.equals("")) {
				Intent intent = new Intent(Selekta.this, Collecta.class);
				intent.putExtra(Constantori.INTENT_LAT, s_Lat);
				intent.putExtra(Constantori.INTENT_LON, s_Lon);
				intent.putExtra(Constantori.INTENT_DATNO, datno);
				intent.putExtra(Constantori.INTENT_LOCNO, locno);
				intent.putExtra(Constantori.INTENT_SPECIES, s001);
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

				break;

			default:
				break;

		}

	}
	


}
