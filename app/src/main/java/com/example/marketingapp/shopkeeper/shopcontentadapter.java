package com.example.marketingapp.shopkeeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class shopcontentadapter extends RecyclerView.Adapter<shopcontentadapter.viewholder> {
   ArrayList<model_shopcontent>totallist;
    Context context;


    public shopcontentadapter(ArrayList<model_shopcontent> totallist, Context context) {
        this.totallist = totallist;
        this.context = context;
    }


    @NonNull
    @Override
    public shopcontentadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_shopcontent,null,false);
        return  new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull shopcontentadapter.viewholder holder, int position) {
        boolean x=true;
           holder.prodname.setText(totallist.get(position).getName());
           holder.price.setText(totallist.get(position).getPrice());
           holder.quantity.setText(totallist.get(position).getQtavailable());
          //  Log.v("jjjk", totallist.get(position).getName());
        holder.iv.setImageResource(R.drawable.prof);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mydialog=new AlertDialog.Builder(context);
                LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view=li.inflate(R.layout.dialog_layout_shop_content,null);
                EditText pname=view.findViewById(R.id.editTextTextprodname);
                EditText pprice=view.findViewById(R.id.editTextTextprice);
                EditText pqt=view.findViewById(R.id.editTextTextquantity);
                pname.setText(totallist.get(position).getName());
                pprice.setText(totallist.get(position).getPrice());
                pqt.setText(totallist.get(position).getQtavailable());
                mydialog.setView(view)
                        .setTitle("change the details")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               // dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(pname.getText().toString().trim().isEmpty()){
                                    pname.setError("enter the name");
                                    pname.requestFocus();
                                    Toast.makeText(context, "Enter the name first", Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    //   Toast.makeText(context, pqt.getText().toString(), Toast.LENGTH_SHORT).show();
                                    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("shopid");
                                    ref1.child(totallist.get(position).getName()).removeValue();
                                    modelcontent_fbase modelcontentFbase = new modelcontent_fbase();
                                    modelcontentFbase.setName(pname.getText().toString());
                                    modelcontentFbase.setPrice(pprice.getText().toString());
                                    modelcontentFbase.setQt_available(pqt.getText().toString());
                                    ref1.child(pname.getText().toString()).setValue(modelcontentFbase);
                                }



                            }
                        })
                .setNeutralButton("More", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Intent intent=new Intent(context,edit_shop_items.class);
                        intent.putExtra("name",totallist.get(position).getName());
                        intent.putExtra("price",totallist.get(position).getPrice());
                        intent.putExtra("qt",totallist.get(position).getQtavailable());
                        context.startActivity(intent);

                        dialog.dismiss();
                    }
                });

                    AlertDialog dialog = mydialog.create();
                    dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return totallist.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView iv;
        CardView cardView;
        TextView prodname,price,quantity;
        EditText pname;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.shopitemimage);
            prodname=itemView.findViewById(R.id.shopcontenttitle);
            quantity=itemView.findViewById(R.id.shopcontenquantityavailable);
            price=itemView.findViewById(R.id.shopcontentprice);
            cardView=itemView.findViewById(R.id.cvshopcontent);
        }
    }
}
