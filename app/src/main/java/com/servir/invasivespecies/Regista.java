package com.servir.invasivespecies;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Regista extends ActionBarActivity {

	EditText usanem;
	EditText usafon;
	EditText usamail,usapaso,usapasoc;
	View View;
	String nani;
	String simu;
	String pepe;
	String lato, lono;
	String wapi;
	String reggo = "";
	private SQLiteDatabase spatiadb;
	String huyu = "user";
	private String cntry;
	final Context context = this;
	private String cntryy;
	private String ssusanem;
	private String ssusafon;
	private String ssusapepe;
	private String ssusapaso;
	ProgressDialog mpd3;
	public static final String URL2 = "http://mobiledata.rcmrd.org/invspec/spatiareg.php";
	TextView uucnt;
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.regista);
	     overridePendingTransition(0,0);

         getSupportActionBar().setDisplayShowHomeEnabled(true);
		 getSupportActionBar().setIcon(R.mipmap.ic_launcher);
		 getSupportActionBar().setHomeButtonEnabled(true);
	 
	 
		 usanem = (EditText) findViewById(R.id.username);
		 usafon = (EditText) findViewById(R.id.uphoneno);
		 usamail = (EditText) findViewById(R.id.uemailo);
		 usapaso = (EditText) findViewById(R.id.upasso);
		 usapasoc = (EditText) findViewById(R.id.upasso2);
		 uucnt = (TextView) findViewById(R.id.textView333);
		 Button butreg = (Button) findViewById (R.id.register);

		     
		 cntryy = guc(this);
		 try{
		 if (cntryy.equals("ad")){uucnt.setText("Andorra");}
		 else if (cntryy.equals("ae")){uucnt.setText("United Arab Emirates");}
		 else if (cntryy.equals("af")){uucnt.setText("Afghanistan");}
		 else if (cntryy.equals("ag")){uucnt.setText("Antigua and Barbuda");}
		 else if (cntryy.equals("ai")){uucnt.setText("Anguilla");}
		 else if (cntryy.equals("al")){uucnt.setText("Albania");}
		 else if (cntryy.equals("am")){uucnt.setText("Armenia");}
		 else if (cntryy.equals("an")){uucnt.setText("Netherlands Antilles");}
		 else if (cntryy.equals("ao")){uucnt.setText("Angola");}
		 else if (cntryy.equals("aq")){uucnt.setText("Antarctica");}
		 else if (cntryy.equals("ar")){uucnt.setText("Argentina");}
		 else if (cntryy.equals("as")){uucnt.setText("American Samoa");}
		 else if (cntryy.equals("at")){uucnt.setText("Austria");}
		 else if (cntryy.equals("au")){uucnt.setText("Australia");}
		 else if (cntryy.equals("aw")){uucnt.setText("Aruba");}
		 else if (cntryy.equals("az")){uucnt.setText("Azerbaidjan");}
		 else if (cntryy.equals("ba")){uucnt.setText("Bosnia-Herzegovina");}
		 else if (cntryy.equals("bb")){uucnt.setText("Barbados");}
		 else if (cntryy.equals("bd")){uucnt.setText("Bangladesh");}
		 else if (cntryy.equals("be")){uucnt.setText("Belgium");}
		 else if (cntryy.equals("bf")){uucnt.setText("Burkina Faso");}
		 else if (cntryy.equals("bg")){uucnt.setText("Bulgaria");}
		 else if (cntryy.equals("bh")){uucnt.setText("Bahrain");}
		 else if (cntryy.equals("bi")){uucnt.setText("Burundi");}
		 else if (cntryy.equals("bj")){uucnt.setText("Benin");}
		 else if (cntryy.equals("bm")){uucnt.setText("Bermuda");}
		 else if (cntryy.equals("bn")){uucnt.setText("Brunei Darussalam");}
		 else if (cntryy.equals("bo")){uucnt.setText("Bolivia");}
		 else if (cntryy.equals("br")){uucnt.setText("Brazil");}
		 else if (cntryy.equals("bs")){uucnt.setText("Bahamas");}
		 else if (cntryy.equals("bt")){uucnt.setText("Bhutan");}
		 else if (cntryy.equals("bv")){uucnt.setText("Bouvet Island");}
		 else if (cntryy.equals("bw")){uucnt.setText("Botswana");}
		 else if (cntryy.equals("by")){uucnt.setText("Belarus");}
		 else if (cntryy.equals("bz")){uucnt.setText("Belize");}
		 else if (cntryy.equals("ca")){uucnt.setText("Canada");}
		 else if (cntryy.equals("cc")){uucnt.setText("Cocos (Keeling) Islands");}
		 else if (cntryy.equals("cf")){uucnt.setText("Central African Republic");}
		 else if (cntryy.equals("cg")){uucnt.setText("Congo");}
		 else if (cntryy.equals("ch")){uucnt.setText("Switzerland");}
		 else if (cntryy.equals("ci")){uucnt.setText("Ivory Coast (Cote D'Ivoire)");}
		 else if (cntryy.equals("ck")){uucnt.setText("Cook Islands");}
		 else if (cntryy.equals("cl")){uucnt.setText("Chile");}
		 else if (cntryy.equals("cm")){uucnt.setText("Cameroon");}
		 else if (cntryy.equals("cn")){uucnt.setText("China");}
		 else if (cntryy.equals("co")){uucnt.setText("Colombia");}
		 else if (cntryy.equals("cr")){uucnt.setText("Costa Rica");}
		 else if (cntryy.equals("cs")){uucnt.setText("Former Czechoslovakia");}
		 else if (cntryy.equals("cu")){uucnt.setText("Cuba");}
		 else if (cntryy.equals("cv")){uucnt.setText("Cape Verde");}
		 else if (cntryy.equals("cx")){uucnt.setText("Christmas Island");}
		 else if (cntryy.equals("cy")){uucnt.setText("Cyprus");}
		 else if (cntryy.equals("cz")){uucnt.setText("Czech Republic");}
		 else if (cntryy.equals("de")){uucnt.setText("Germany");}
		 else if (cntryy.equals("dj")){uucnt.setText("Djibouti");}
		 else if (cntryy.equals("dk")){uucnt.setText("Denmark");}
		 else if (cntryy.equals("dm")){uucnt.setText("Dominica");}
		 else if (cntryy.equals("do")){uucnt.setText("Dominican Republic");}
		 else if (cntryy.equals("dz")){uucnt.setText("Algeria");}
		 else if (cntryy.equals("ec")){uucnt.setText("Ecuador");}
		 else if (cntryy.equals("edu")){uucnt.setText("USA Educational");}
		 else if (cntryy.equals("ee")){uucnt.setText("Estonia");}
		 else if (cntryy.equals("eg")){uucnt.setText("Egypt");}
		 else if (cntryy.equals("eh")){uucnt.setText("Western Sahara");}
		 else if (cntryy.equals("er")){uucnt.setText("Eritrea");}
		 else if (cntryy.equals("es")){uucnt.setText("Spain");}
		 else if (cntryy.equals("et")){uucnt.setText("Ethiopia");}
		 else if (cntryy.equals("fi")){uucnt.setText("Finland");}
		 else if (cntryy.equals("fj")){uucnt.setText("Fiji");}
		 else if (cntryy.equals("fk")){uucnt.setText("Falkland Islands");}
		 else if (cntryy.equals("fm")){uucnt.setText("Micronesia");}
		 else if (cntryy.equals("fo")){uucnt.setText("Faroe Islands");}
		 else if (cntryy.equals("fr")){uucnt.setText("France");}
		 else if (cntryy.equals("fx")){uucnt.setText("France (European Territory)");}
		 else if (cntryy.equals("ga")){uucnt.setText("Gabon");}
		 else if (cntryy.equals("gb")){uucnt.setText("Great Britain");}
		 else if (cntryy.equals("gd")){uucnt.setText("Grenada");}
		 else if (cntryy.equals("ge")){uucnt.setText("Georgia");}
		 else if (cntryy.equals("gf")){uucnt.setText("French Guyana");}
		 else if (cntryy.equals("gh")){uucnt.setText("Ghana");}
		 else if (cntryy.equals("gi")){uucnt.setText("Gibraltar");}
		 else if (cntryy.equals("gl")){uucnt.setText("Greenland");}
		 else if (cntryy.equals("gm")){uucnt.setText("Gambia");}
		 else if (cntryy.equals("gn")){uucnt.setText("Guinea");}
		 else if (cntryy.equals("gp")){uucnt.setText("Guadeloupe (French)");}
		 else if (cntryy.equals("gq")){uucnt.setText("Equatorial Guinea");}
		 else if (cntryy.equals("gr")){uucnt.setText("Greece");}
		 else if (cntryy.equals("gs")){uucnt.setText("S. Georgia & S. Sandwich Isls.");}
		 else if (cntryy.equals("gt")){uucnt.setText("Guatemala");}
		 else if (cntryy.equals("gu")){uucnt.setText("Guam (USA)");}
		 else if (cntryy.equals("gw")){uucnt.setText("Guinea Bissau");}
		 else if (cntryy.equals("gy")){uucnt.setText("Guyana");}
		 else if (cntryy.equals("hk")){uucnt.setText("Hong Kong");}
		 else if (cntryy.equals("hm")){uucnt.setText("Heard and McDonald Islands");}
		 else if (cntryy.equals("hn")){uucnt.setText("Honduras");}
		 else if (cntryy.equals("hr")){uucnt.setText("Croatia");}
		 else if (cntryy.equals("ht")){uucnt.setText("Haiti");}
		 else if (cntryy.equals("hu")){uucnt.setText("Hungary");}
		 else if (cntryy.equals("id")){uucnt.setText("Indonesia");}
		 else if (cntryy.equals("ie")){uucnt.setText("Ireland");}
		 else if (cntryy.equals("il")){uucnt.setText("Israel");}
		 else if (cntryy.equals("in")){uucnt.setText("India");}
		 else if (cntryy.equals("int")){uucnt.setText("International");}
		 else if (cntryy.equals("io")){uucnt.setText("British Indian Ocean Territory");}
		 else if (cntryy.equals("iq")){uucnt.setText("Iraq");}
		 else if (cntryy.equals("ir")){uucnt.setText("Iran");}
		 else if (cntryy.equals("is")){uucnt.setText("Iceland");}
		 else if (cntryy.equals("it")){uucnt.setText("Italy");}
		 else if (cntryy.equals("jm")){uucnt.setText("Jamaica");}
		 else if (cntryy.equals("jo")){uucnt.setText("Jordan");}
		 else if (cntryy.equals("jp")){uucnt.setText("Japan");}
		 else if (cntryy.equals("ke")){uucnt.setText("Kenya");}
		 else if (cntryy.equals("kg")){uucnt.setText("Kyrgyzstan");}
		 else if (cntryy.equals("kh")){uucnt.setText("Cambodia");}
		 else if (cntryy.equals("ki")){uucnt.setText("Kiribati");}
		 else if (cntryy.equals("km")){uucnt.setText("Comoros");}
		 else if (cntryy.equals("kn")){uucnt.setText("Saint Kitts & Nevis Anguilla");}
		 else if (cntryy.equals("kp")){uucnt.setText("North Korea");}
		 else if (cntryy.equals("kr")){uucnt.setText("South Korea");}
		 else if (cntryy.equals("kw")){uucnt.setText("Kuwait");}
		 else if (cntryy.equals("ky")){uucnt.setText("Cayman Islands");}
		 else if (cntryy.equals("kz")){uucnt.setText("Kazakhstan");}
		 else if (cntryy.equals("la")){uucnt.setText("Laos");}
		 else if (cntryy.equals("lb")){uucnt.setText("Lebanon");}
		 else if (cntryy.equals("lc")){uucnt.setText("Saint Lucia");}
		 else if (cntryy.equals("li")){uucnt.setText("Liechtenstein");}
		 else if (cntryy.equals("lk")){uucnt.setText("Sri Lanka");}
		 else if (cntryy.equals("lr")){uucnt.setText("Liberia");}
		 else if (cntryy.equals("ls")){uucnt.setText("Lesotho");}
		 else if (cntryy.equals("lt")){uucnt.setText("Lithuania");}
		 else if (cntryy.equals("lu")){uucnt.setText("Luxembourg");}
		 else if (cntryy.equals("lv")){uucnt.setText("Latvia");}
		 else if (cntryy.equals("ly")){uucnt.setText("Libya");}
		 else if (cntryy.equals("ma")){uucnt.setText("Morocco");}
		 else if (cntryy.equals("mc")){uucnt.setText("Monaco");}
		 else if (cntryy.equals("md")){uucnt.setText("Moldavia");}
		 else if (cntryy.equals("mg")){uucnt.setText("Madagascar");}
		 else if (cntryy.equals("mh")){uucnt.setText("Marshall Islands");}
		 else if (cntryy.equals("mil")){uucnt.setText("USA Military");}
		 else if (cntryy.equals("mk")){uucnt.setText("Macedonia");}
		 else if (cntryy.equals("ml")){uucnt.setText("Mali");}
		 else if (cntryy.equals("mm")){uucnt.setText("Myanmar");}
		 else if (cntryy.equals("mn")){uucnt.setText("Mongolia");}
		 else if (cntryy.equals("mo")){uucnt.setText("Macau");}
		 else if (cntryy.equals("mp")){uucnt.setText("Northern Mariana Islands");}
		 else if (cntryy.equals("mq")){uucnt.setText("Martinique (French)");}
		 else if (cntryy.equals("mr")){uucnt.setText("Mauritania");}
		 else if (cntryy.equals("ms")){uucnt.setText("Montserrat");}
		 else if (cntryy.equals("mt")){uucnt.setText("Malta");}
		 else if (cntryy.equals("mu")){uucnt.setText("Mauritius");}
		 else if (cntryy.equals("mv")){uucnt.setText("Maldives");}
		 else if (cntryy.equals("mw")){uucnt.setText("Malawi");}
		 else if (cntryy.equals("mx")){uucnt.setText("Mexico");}
		 else if (cntryy.equals("my")){uucnt.setText("Malaysia");}
		 else if (cntryy.equals("mz")){uucnt.setText("Mozambique");}
		 else if (cntryy.equals("na")){uucnt.setText("Namibia");}
		 else if (cntryy.equals("nc")){uucnt.setText("New Caledonia (French)");}
		 else if (cntryy.equals("ne")){uucnt.setText("Niger");}
		 else if (cntryy.equals("net")){uucnt.setText("Network");}
		 else if (cntryy.equals("nf")){uucnt.setText("Norfolk Island");}
		 else if (cntryy.equals("ng")){uucnt.setText("Nigeria");}
		 else if (cntryy.equals("ni")){uucnt.setText("Nicaragua");}
		 else if (cntryy.equals("nl")){uucnt.setText("Netherlands");}
		 else if (cntryy.equals("no")){uucnt.setText("Norway");}
		 else if (cntryy.equals("np")){uucnt.setText("Nepal");}
		 else if (cntryy.equals("nr")){uucnt.setText("Nauru");}
		 else if (cntryy.equals("nt")){uucnt.setText("Neutral Zone");}
		 else if (cntryy.equals("nu")){uucnt.setText("Niue");}
		 else if (cntryy.equals("nz")){uucnt.setText("New Zealand");}
		 else if (cntryy.equals("om")){uucnt.setText("Oman");}
		 else if (cntryy.equals("pa")){uucnt.setText("Panama");}
		 else if (cntryy.equals("pe")){uucnt.setText("Peru");}
		 else if (cntryy.equals("pf")){uucnt.setText("Polynesia (French)");}
		 else if (cntryy.equals("pg")){uucnt.setText("Papua New Guinea");}
		 else if (cntryy.equals("ph")){uucnt.setText("Philippines");}
		 else if (cntryy.equals("pk")){uucnt.setText("Pakistan");}
		 else if (cntryy.equals("pl")){uucnt.setText("Poland");}
		 else if (cntryy.equals("pm")){uucnt.setText("Saint Pierre and Miquelon");}
		 else if (cntryy.equals("pn")){uucnt.setText("Pitcairn Island");}
		 else if (cntryy.equals("pr")){uucnt.setText("Puerto Rico");}
		 else if (cntryy.equals("pt")){uucnt.setText("Portugal");}
		 else if (cntryy.equals("pw")){uucnt.setText("Palau");}
		 else if (cntryy.equals("py")){uucnt.setText("Paraguay");}
		 else if (cntryy.equals("qa")){uucnt.setText("Qatar");}
		 else if (cntryy.equals("re")){uucnt.setText("Reunion (French)");}
		 else if (cntryy.equals("ro")){uucnt.setText("Romania");}
		 else if (cntryy.equals("ru")){uucnt.setText("Russian Federation");}
		 else if (cntryy.equals("rw")){uucnt.setText("Rwanda");}
		 else if (cntryy.equals("sa")){uucnt.setText("Saudi Arabia");}
		 else if (cntryy.equals("sb")){uucnt.setText("Solomon Islands");}
		 else if (cntryy.equals("sc")){uucnt.setText("Seychelles");}
		 else if (cntryy.equals("sd")){uucnt.setText("Sudan");}
		 else if (cntryy.equals("se")){uucnt.setText("Sweden");}
		 else if (cntryy.equals("sg")){uucnt.setText("Singapore");}
		 else if (cntryy.equals("sh")){uucnt.setText("Saint Helena");}
		 else if (cntryy.equals("si")){uucnt.setText("Slovenia");}
		 else if (cntryy.equals("sj")){uucnt.setText("Svalbard and Jan Mayen Islands");}
		 else if (cntryy.equals("sk")){uucnt.setText("Slovak Republic");}
		 else if (cntryy.equals("sl")){uucnt.setText("Sierra Leone");}
		 else if (cntryy.equals("sm")){uucnt.setText("San Marino");}
		 else if (cntryy.equals("sn")){uucnt.setText("Senegal");}
		 else if (cntryy.equals("so")){uucnt.setText("Somalia");}
		 else if (cntryy.equals("sr")){uucnt.setText("Suriname");}
		 else if (cntryy.equals("st")){uucnt.setText("Saint Tome (Sao Tome) and Principe");}
		 else if (cntryy.equals("su")){uucnt.setText("Former USSR");}
		 else if (cntryy.equals("sv")){uucnt.setText("El Salvador");}
		 else if (cntryy.equals("sy")){uucnt.setText("Syria");}
		 else if (cntryy.equals("sz")){uucnt.setText("Swaziland");}
		 else if (cntryy.equals("tc")){uucnt.setText("Turks and Caicos Islands");}
		 else if (cntryy.equals("td")){uucnt.setText("Chad");}
		 else if (cntryy.equals("tf")){uucnt.setText("French Southern Territories");}
		 else if (cntryy.equals("tg")){uucnt.setText("Togo");}
		 else if (cntryy.equals("th")){uucnt.setText("Thailand");}
		 else if (cntryy.equals("tj")){uucnt.setText("Tadjikistan");}
		 else if (cntryy.equals("tk")){uucnt.setText("Tokelau");}
		 else if (cntryy.equals("tm")){uucnt.setText("Turkmenistan");}
		 else if (cntryy.equals("tn")){uucnt.setText("Tunisia");}
		 else if (cntryy.equals("to")){uucnt.setText("Tonga");}
		 else if (cntryy.equals("tp")){uucnt.setText("East Timor");}
		 else if (cntryy.equals("tr")){uucnt.setText("Turkey");}
		 else if (cntryy.equals("tt")){uucnt.setText("Trinidad and Tobago");}
		 else if (cntryy.equals("tv")){uucnt.setText("Tuvalu");}
		 else if (cntryy.equals("tw")){uucnt.setText("Taiwan");}
		 else if (cntryy.equals("tz")){uucnt.setText("Tanzania");}
		 else if (cntryy.equals("ua")){uucnt.setText("Ukraine");}
		 else if (cntryy.equals("ug")){uucnt.setText("Uganda");}
		 else if (cntryy.equals("uk")){uucnt.setText("United Kingdom");}
		 else if (cntryy.equals("um")){uucnt.setText("USA Minor Outlying Islands");}
		 else if (cntryy.equals("us")){uucnt.setText("United States");}
		 else if (cntryy.equals("uy")){uucnt.setText("Uruguay");}
		 else if (cntryy.equals("uz")){uucnt.setText("Uzbekistan");}
		 else if (cntryy.equals("va")){uucnt.setText("Vatican City State");}
		 else if (cntryy.equals("vc")){uucnt.setText("Saint Vincent & Grenadines");}
		 else if (cntryy.equals("ve")){uucnt.setText("Venezuela");}
		 else if (cntryy.equals("vg")){uucnt.setText("Virgin Islands (British)");}
		 else if (cntryy.equals("vi")){uucnt.setText("Virgin Islands (USA)");}
		 else if (cntryy.equals("vn")){uucnt.setText("Vietnam");}
		 else if (cntryy.equals("vu")){uucnt.setText("Vanuatu");}
		 else if (cntryy.equals("wf")){uucnt.setText("Wallis and Futuna Islands");}
		 else if (cntryy.equals("ws")){uucnt.setText("Samoa");}
		 else if (cntryy.equals("ye")){uucnt.setText("Yemen");}
		 else if (cntryy.equals("yt")){uucnt.setText("Mayotte");}
		 else if (cntryy.equals("yu")){uucnt.setText("Yugoslavia");}
		 else if (cntryy.equals("za")){uucnt.setText("South Africa");}
		 else if (cntryy.equals("zm")){uucnt.setText("Zambia");}
		 else if (cntryy.equals("zr")){uucnt.setText("Zaire");}
		 else if (cntryy.equals("zw")){uucnt.setText("Zimbabwe");}
		 }catch(Exception e){
			 uucnt.setText("No SIM inserted");
		 }



		 
		 spatiadb=openOrCreateDatabase("InvSpecDB", Context.MODE_PRIVATE, null);
		 spatiadb.execSQL("CREATE TABLE IF NOT EXISTS userTBL(userno VARCHAR,usernem VARCHAR,usertel VARCHAR,usercntry VARCHAR,useremail VARCHAR,userpass VARCHAR);");
	
		 
		 
		 Cursor c=spatiadb.rawQuery("SELECT * FROM userTBL WHERE userno='"+huyu+"'", null);
			if(c.moveToFirst())
			{
				usanem.setText(c.getString(1));
				usafon.setText(c.getString(2));
				uucnt.setText(c.getString(3));
				usamail.setText(c.getString(4));
				usapaso.setText(c.getString(5));
				
			}
			
			Intent intentqa = getIntent();
			 lato = intentqa.getStringExtra("lattt");
			 Intent intentqaa = getIntent();
			 lono = intentqaa.getStringExtra("lonnn");
		 Intent intentqo = getIntent();
		 reggo = intentqo.getStringExtra("reggo");
			
		/*	butback.setOnClickListener(new OnClickListener(){
		    	
		    	public void onClick(View view){
		    		finish();
		    		
		    	Intent intent = new Intent (Regista.this, MainActivity.class);
		    	startActivity(intent);
		    	
		    	}
		      });*/
			
			butreg.setOnClickListener(new OnClickListener(){
		    	
		    	public void onClick(View view){
	
		    		cntry  = uucnt.getText().toString().trim();
		            ssusanem= usanem.getText().toString().replaceAll("[^a-zA-Z' ]+", "");
		            ssusanem.replace("'", "''");
		    		ssusafon = usafon.getText().toString().trim().replace("'", "''");
		    		ssusafon.replaceAll("[^0-9+]", "");
		    		//regex to validate email ad
		    		ssusapepe = usamail.getText().toString().trim();
		    		ssusapepe.replace("'", "''");
					ssusapaso = usapaso.getText().toString().trim();
		    		
		    		
		    		
		            if(    usanem.getText().toString().trim().length()==0 ||
		            		usafon.getText().toString().trim().length()==0||
							usapaso.getText().toString().trim().length()==0
		         		   )
		               {
		            	Toast.makeText(Regista.this,"Please type in your name and phone number",Toast.LENGTH_LONG).show();
		               } else if (usafon.getText().toString().trim().length()!=0 &&
         		         		   usafon.getText().toString().trim().length() < 6||
        		         		   usafon.getText().toString().trim().length() > 13){
		            	   Toast.makeText(Regista.this,"Please enter a valid phone number",Toast.LENGTH_LONG).show();
                       } else if ( usamail.getText().toString().trim().length()==0 && usafon.getText().toString().trim().length()==0){
                    	   Toast.makeText(Regista.this,"Please enter at least your phone number or email.",Toast.LENGTH_LONG).show();
					} else if (!usapaso.getText().toString().trim().equals(usapasoc.getText().toString().trim())){
						Toast.makeText(Regista.this,"Please make sure the passwords match.",Toast.LENGTH_LONG).show();
                       }else{
		         	    	       
		         	    	      // new HttpAsyncTask0().execute(new String[]{URLc});
		         			 
                    	   Cursor chk=spatiadb.rawQuery("SELECT * FROM userTBL WHERE userno='"+huyu+"'", null);
                	 		if(chk.moveToFirst())
                	 		{                                                                                                                                                                                                                                                                                                                                                                                                                   
                	 	    spatiadb.execSQL("UPDATE userTBL SET usernem='"+ssusanem+"',usertel='"+ssusafon+"',usercntry='"+cntry+"',useremail='"+ssusapepe+"',userpass='"+ssusapaso+"' WHERE userno='"+huyu+"'");
                	 		}
                	 		else
                	 		{
                	 		spatiadb.execSQL("INSERT INTO userTBL VALUES('"+huyu+"','"+ssusanem+"','"+ssusafon+"','"+cntry+"','"+ssusapepe+"','"+ssusapaso+"');");
                	 		}
						chk.close();

				         	    	
				         	 		simu = ssusafon;
				         	 		nani = ssusanem;
				         	 		wapi = cntry;
				         	 		pepe = ssusapepe;
				         	 		
				         	 		
				         	 		new HttpAsyncTaskreg().execute(new String[]{URL2});
				         	 		
		         	       }
		    	}
			    });
			
}
	
	

   	public void diambaid2(View v) {
		final Dialog mbott = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
		mbott.setContentView(R.layout.mbaind_dialog2);
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

				Intent intent = new Intent(Regista.this, MainActivity.class);
				startActivity(intent);
					mbott.dismiss();
			}
		});
		mbott.show();
	}
   	


    public static String guc(Context context){
    	try{
    		final TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
    		final String ucon = tm.getSimCountryIso();
    		if (ucon!=null && ucon.length()==2){
    			return ucon.toLowerCase(Locale.US);
    		}else if (tm.getPhoneType()!=TelephonyManager.PHONE_TYPE_CDMA){
    			String unet = tm.getNetworkCountryIso();
    			if (unet != null && unet.length() == 2){
    				unet.toLowerCase(Locale.US);
    			}
    	}
    		
    	}catch(Exception e){
    		
    	}
    	return null;
    }
    

	
      

    private class HttpAsyncTaskreg extends AsyncTask<String, Void, String> {
        
    	@Override
        protected void onPreExecute() {
    		super.onPreExecute();
    	    mpd3 = new ProgressDialog(Regista.this);
    	    mpd3.setMessage("Registering.....");
    	    mpd3.setCanceledOnTouchOutside(false);
    	    mpd3.setIndeterminate(true);
    	    mpd3.setMax(100);
    	    mpd3.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	    mpd3.show();
    	    
    	}
    	
    	@Override
        protected String doInBackground(String... urls) {

    		String outputi=null;
    		for (String url:urls){
    			outputi = getOutputFromUrl(url);
    		}
    		
    		return outputi;
    	}
    	
    	private String getOutputFromUrl(String url){
        	String outputi=null;    
        	StringBuilder sb3 = new StringBuilder();
        	
    		
        	try {
        		HttpClient httpclient = new DefaultHttpClient();
            	HttpPost httppost = new HttpPost(url);
            	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
      	        nameValuePairs.add(new BasicNameValuePair("nani", nani));
      	        nameValuePairs.add(new BasicNameValuePair("simu", simu));
    	        nameValuePairs.add(new BasicNameValuePair("wapi", wapi));
    	        nameValuePairs.add(new BasicNameValuePair("pepe", pepe));
				nameValuePairs.add(new BasicNameValuePair("pasd", ssusapaso));
    	        nameValuePairs.add(new BasicNameValuePair("lato", lato));
    	        nameValuePairs.add(new BasicNameValuePair("lono", lono));
    	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
      	       
        		HttpResponse httpr = httpclient.execute(httppost);
     	       
     	      if (httpr.getStatusLine().getStatusCode() != 200) {
                  Log.d("this ndio hii", "Server encountered an error");
                }
              
    	     BufferedReader reader = new BufferedReader(new InputStreamReader(httpr.getEntity().getContent(), "UTF8"));
             sb3 = new StringBuilder();
             sb3.append(reader.readLine() + "\n");
             String line3 = null;

             while ((line3 = reader.readLine()) != null) {
               sb3.append(line3 + "\n");
             }

                 outputi = sb3.toString();
               
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        
        	return outputi;
        
    	}
    	

    	@SuppressWarnings("unused")
    	protected void onProgressUpdate(int...progress) {
    		mpd3.setProgress(progress[0]);
    	    
    	}
    	
    	
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String outputi) {
        	try{
        		String mekamu = outputi.toString().trim();
        		
        		Log.e("shida", mekamu);
        		
        		if (mekamu.equals("steve")){

					Cursor chk=spatiadb.rawQuery("SELECT * FROM userTBL WHERE userno='"+huyu+"'", null);
					if(chk.moveToFirst())
					{
						spatiadb.execSQL("UPDATE userTBL SET usernem='"+ssusanem+"',usertel='"+ssusafon+"',usercntry='"+cntry+"',useremail='"+ssusapepe+"',userpass='"+ssusapaso+"' WHERE userno='"+huyu+"'");
					}
					else
					{
						spatiadb.execSQL("INSERT INTO userTBL VALUES('"+huyu+"','"+ssusanem+"','"+ssusafon+"','"+cntry+"','"+ssusapepe+"','"+ssusapaso+"');");
					}
					chk.close();

         	 		diambaid2(View);
 	    		
        		}else{
        			diambaidno(View);
        		}
        			
        	}catch(Exception xe){
        		diambaidno(View);
        	}
            mpd3.dismiss();
       }
    }
        
    public void diambaidno(View v) {
		final Dialog mbott = new Dialog(Regista.this, android.R.style.Theme_Translucent_NoTitleBar);
		mbott.setContentView(R.layout.mbaind_nonet3);
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
					mbott.dismiss();
			}
		});
		mbott.show();
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			// Respond to the action bar's Up/Home button
			case android.R.id.home:
				//NavUtils.navigateUpFromSameTask(this);

				if (reggo.equals("main")) {

					Intent intent = new Intent(Regista.this, MainActivity.class);
					startActivity(intent);
				}else{
					Intent intent = new Intent(Regista.this, Loginno.class);
					startActivity(intent);
				}

				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed(){

		Intent intentqo = getIntent();
		reggo = intentqo.getStringExtra("reggo");

		if (reggo.equals("main")) {

			Intent intent = new Intent(Regista.this, MainActivity.class);
			startActivity(intent);
		}else{
			Intent intent = new Intent(Regista.this, Loginno.class);
			startActivity(intent);
		}
	}





}