package com.example.techapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class DashBoard<MyOwnAdapter> extends AppCompatActivity {

    private ImageButton img1,img2,img;

    private SearchView svSearch;

    private TextView textView4,textView3,tvProdSensor,tvProdAlexa,tvProdBand,tvCCTV,tvProjector,tvWatches,tvCamera,tvTempSensor,tvCanon,tvPodcast,tvTCamera,tvCharger,tvUSB,tvTBand,tvCam,tvMotherboard,tvPod,tvPhone,tvSensor2,tvProjector2;

    private Button btnSensor,btnAlexa,btnBand,btnCCTV,btnProjector,btnWatches,btnCamera,btnTempSensor,btnCanon,btnPodcast,btnTCamera,btnCharger,btnUSB,btnTBand,btnCam,btnMotherboard,btnPod,btnPhone,btnSensor2,btnProjector2;

    private ImageButton imgCart;

    RecyclerView rvRecycler;

    RecyclerView rvRecycler2;

    int[] images1 ={R.drawable.watch,R.drawable.charger,R.drawable.band,R.drawable.camera,R.drawable.canon};

    int[] images2 ={R.drawable.cctv,R.drawable.earpods,R.drawable.motherboard,R.drawable.usb,R.drawable.phone};

    int[] images3 ={R.drawable.micamera,R.drawable.projector,R.drawable.threewatch,R.drawable.temperaturesensor,R.drawable.band_sensor};

    int[] images4 ={R.drawable.micamera,R.drawable.projector,R.drawable.threewatch,R.drawable.temperaturesensor,R.drawable.band_sensor};

    String[] subject ={"charger","camera","sensor","alexa","band","cctv","projector"};

    ListView listOfItems;

    MyOwnAdapter1 adapter;

    MyOwnAdapter2 adapter2;

    ArrayList<Model> aList,bList;

    AdminDatabase adminDatabase=new AdminDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        rvRecycler=findViewById(R.id.rvRecycler);
        rvRecycler2=findViewById(R.id.rvRecycler2);

        aList=new ArrayList<Model>();
        bList=new ArrayList<Model>();

         //Add Product Name and Image in arraylist
        /*
        Cursor cursor=adminDatabase.getData();
        while (cursor.moveToNext()){
            aList.add(new Model(cursor.getString(1),cursor.getBlob(2)));
            bList.add(new Model(cursor.getString(1),cursor.getBlob(2)));
        }

         */

        aList.add(new Model("Band $100",R.drawable.charger));
        aList.add(new Model("Charger $200",R.drawable.charger));
        aList.add(new Model("Canon $250",R.drawable.canon));
        aList.add(new Model("Earpods $150",R.drawable.cctv));
        aList.add(new Model("MiCamera $100",R.drawable.micamera));
        aList.add(new Model("CPU $180",R.drawable.motherboard));

        bList.add(new Model("Band $100",R.drawable.band));
        bList.add(new Model("Charger $200",R.drawable.charger));
        bList.add(new Model("Canon $250",R.drawable.canon));
        bList.add(new Model("Earpods $150",R.drawable.cctv));
        bList.add(new Model("MiCamera $100",R.drawable.micamera));
        bList.add(new Model("CPU $180",R.drawable.motherboard));


        adapter = new MyOwnAdapter1(this,aList, new ClickListener() {
            @Override public void onPositionClicked(int position) {

            }
            @Override
            public void onLongClicked(int position) {

            }
        });
        adapter2 = new MyOwnAdapter2(this,bList, new ClickListener() {
            @Override public void onPositionClicked(int position) {

            }
            @Override
            public void onLongClicked(int position) {

            }
        });

        rvRecycler.setAdapter(adapter);
        rvRecycler2.setAdapter(adapter2);

        //rvRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false);

        rvRecycler.setLayoutManager(gridLayoutManager);

        //rvRecycler2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        rvRecycler2.setLayoutManager(new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false));

        imgCart=findViewById(R.id.imgCart);
        svSearch=findViewById(R.id.svSearch);
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img=findViewById(R.id.img);
        textView4=findViewById(R.id.textView4);
        textView3=findViewById(R.id.textView3);
        listOfItems=findViewById(R.id.listOfItems);


        Intent intent=new Intent(getApplicationContext(),MyReceiver.class);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),2343,intent,0);
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(10),pendingIntent);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subject);

        listOfItems.setAdapter(arrayAdapter);
        listOfItems.setBackgroundColor(1);
        listOfItems.setVisibility(View.INVISIBLE);

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                adapter2.getFilter().filter(newText);
                return false;
            }
        });

        //listener to navigate to cart

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnCart();
            }
        });
        //Navigate to explore,dashboard and profile section
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashboardIntent=new Intent(getApplicationContext(),Cart.class);
                startActivity(dashboardIntent);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent=new Intent(getApplicationContext(),Profile.class);
                startActivity(profileIntent);
            }
        });
    }

    //function to navigate to cart
    public void btnCart(){
        Intent cartIntent=new Intent(getApplicationContext(),ProductCart.class);
        startActivity(cartIntent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item=menu.findItem(R.id.menuItem);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                adapter2.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}