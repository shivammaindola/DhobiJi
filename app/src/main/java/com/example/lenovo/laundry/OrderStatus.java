package com.example.lenovo.laundry;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.laundry.Common.Common;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import Interface.ItemClickListener;
import model.Request;
import viewHolder.MenuViewHolder;
import viewHolder.OrderViewHolder;

public class OrderStatus extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
    private static final String TAG = "Order status";

    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Firebase
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        recyclerView=(RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
       recyclerView.setLayoutManager(layoutManager);
        Log.e(TAG, "before loadorders");

        loadOrders(Common.currentUser.getPhone());
    }

   private void loadOrders(final String phone) {

       FirebaseRecyclerOptions<Request> options;
       options = new FirebaseRecyclerOptions.Builder<Request>()
               .setQuery(requests.orderByChild("phone").equalTo(phone), Request.class)
               .build();
       Log.e(TAG, "after options");


       adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(options) {
           @Override
           protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Request model) {

               holder.txtOrderId.setText(adapter.getRef(position).getKey());
               holder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
               holder.txtOrderAddress.setText(model.getAddress());
               holder.txtOrderPhone.setText(model.getPhone());
               final Request clickItem = model;
           }

           @NonNull
           @Override
           public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
               return new OrderViewHolder(view);
           }
           };
       recyclerView.setAdapter(adapter);
       adapter.startListening();
       adapter.notifyDataSetChanged();
   }

private String convertCodeToStatus(String status){

        if(status.equals("0"))
            return "placed";
        else if(status.equals("1"))
            return "on the way";
        else
            return "shipped";
       }

}
