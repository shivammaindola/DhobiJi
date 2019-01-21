package com.example.lenovo.laundry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.laundry.Common.Common;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.signin.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Interface.ItemClickListener;
import model.Category;
import viewHolder.MenuViewHolder;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog mDialog;

    FirebaseDatabase database;
    DatabaseReference category;

    public ProgressBar progressBar;
    TextView txtFullName;


    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter adapter;
    //ArrayList<Category> cat = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Food");
        setSupportActionBar(toolbar);


        //init Firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");


        Log.d("cat", String.valueOf(category));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(home.this, Cart.class);
                startActivity(cartIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //set name
         View headerView= navigationView.getHeaderView(0);
         txtFullName=(TextView)headerView.findViewById(R.id.txtFullName);
         txtFullName.setText(Common.currentUser.getName());

      //Load menu
        category.keepSynced(true);
        recycler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycler_menu.setLayoutManager(layoutManager);
        //loadMenu();
        //recycler_menu.setAdapter(getadapter);
        // getadapter.notifyDataSetChanged();
        Log.e("home","Outside loadmenu");
        loadMenu();
    }

    private void loadMenu() {
        Log.e("home","Inside loadmenu");
        mDialog = new ProgressDialog(home.this);
        mDialog.setMessage("please wait...");
        mDialog.show();


        FirebaseRecyclerOptions<Category> options;
        options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(category, Category.class)
                .build();
        Log.e("home","After Options");
        adapter= new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {

            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Log.e("home","Inside MenuView");
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
                return  new MenuViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull Category model) {
                Log.e("home","Inside onBind");
                holder.txtMenuName.setText(model.getName());
                holder.txtMenuPrice.setText(model.getPrice());
                Picasso.with(getBaseContext()).load(model.getImage()).into(holder.imageView);
                final Category clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // Toast.makeText(home.this, "" + clickItem.getName(), Toast.LENGTH_SHORT).show();I
                        Intent clothDetail = new Intent(home.this,ClothesDetail.class);
                        clothDetail.putExtra("ClothId",adapter.getRef(position).getKey());
                       startActivity(clothDetail);

        }
               });
                mDialog.hide();
            }
        };


        recycler_menu.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();
        Log.e("home","last loadmenu");



    }



  @Override
    protected void onStart() {
      Log.e("home","Inside onStart");
      super.onStart();
      loadMenu();
      adapter.startListening();

    }

    @Override
    protected void onStop() {
        Log.e("home","Inside onStop");

        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_cart) {
            Intent cart= new Intent(home.this,Cart.class);
            startActivity(cart);

        } else if (id == R.id.nav_orders) {
            Intent order= new Intent(home.this,OrderStatus.class);
            startActivity(order);

        } else if (id == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            Intent logout= new Intent(home.this,signIn.class);
            logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logout);
                }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
