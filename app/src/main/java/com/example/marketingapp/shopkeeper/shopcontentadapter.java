package com.example.marketingapp.shopkeeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marketingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
           holder.prodname.setText(totallist.get(position).getProductName());
           holder.price.setText(totallist.get(position).getProductPrice());
           holder.quantity.setText(totallist.get(position).getProductQty());
           holder.offer.setText(totallist.get(position).getOfferPrice());
           holder.desc.setText(totallist.get(position).getProductDesc());
        Glide.with(context).load(totallist.get(position).getProductUrl()).into(holder.shopitemimage);

        switch(position%4)
        {
            case 0: holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card1)); break;
            case 1: holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card2)); break;
            case 2: holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card3)); break;
            default: holder.cv.setCardBackgroundColor(context.getResources().getColor(R.color.card4)); break;
        }

        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,edit_shop_items.class);
                intent.putExtra("PRODUCT_ID",totallist.get(position).getProductID());
                intent.putExtra("SHOPID",totallist.get(position).getShopID());
                intent.putExtra("PRODUCT_NAME",totallist.get(position).getProductName());
                intent.putExtra("PRICE",totallist.get(position).getProductPrice());
                intent.putExtra("QUANTITY",totallist.get(position).getProductQty());
                intent.putExtra("OFFER",totallist.get(position).getOfferPrice());
                intent.putExtra("DESCRIPTION",totallist.get(position).getProductDesc());
                intent.putExtra("PRODUCT_URL",totallist.get(position).getProductUrl());

                context.startActivity(intent);
            }
        });

//        holder.prodname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AlertDialog.Builder mydialog=new AlertDialog.Builder(context);
//                LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View view=li.inflate(R.layout.dialog_layout_shop_content,null);
//                EditText pname=view.findViewById(R.id.ediTextprodname);
//                EditText pprice=view.findViewById(R.id.editTextprice);
//                EditText pqt=view.findViewById(R.id.editTextqt);
//                EditText pdesc=view.findViewById(R.id.editTextdesc);
//                EditText poffer=view.findViewById(R.id.editTextdesc);
//                pname.setText(totallist.get(position).getProductName());
//                pprice.setText(totallist.get(position).getProductPrice());
//                pqt.setText(totallist.get(position).getProductQty());
//                pdesc.setText(totallist.get(position).getProductDesc());
//                poffer.setText(totallist.get(position).getOfferPrice());
//                mydialog.setView(view)
//                        .setTitle("Edit Details")
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                               dialog.dismiss();
//                            }
//                        })
//                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if(pname.getText().toString().trim().isEmpty()){
//                                    pname.setError("enter the name");
//                                    pname.requestFocus();
//                                    Toast.makeText(context, "Enter the name first", Toast.LENGTH_SHORT).show();
//                                }
//                                else {
//
//                                    //   Toast.makeText(context, pqt.getText().toString(), Toast.LENGTH_SHORT).show();
//                                    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("shopid");
//                                    ref1.child(totallist.get(position).getName()).removeValue();
//                                    modelcontent_fbase modelcontentFbase = new modelcontent_fbase();
//                                    modelcontentFbase.setName(pname.getText().toString());
//                                    modelcontentFbase.setPrice(pprice.getText().toString());
//                                    modelcontentFbase.setQt_available(pqt.getText().toString());
//                                    ref1.child(pname.getText().toString()).setValue(modelcontentFbase);
//                                }
//
//
//
//                            }
//                        })
//                .setNeutralButton("More", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//
//                        Intent intent=new Intent(context,edit_shop_items.class);
//                        intent.putExtra("name",totallist.get(position).getName());
//                        intent.putExtra("price",totallist.get(position).getPrice());
//                        intent.putExtra("qt",totallist.get(position).getQtavailable());
//                        context.startActivity(intent);
//
//                        dialog.dismiss();
//                    }
//                });
//
//                    AlertDialog dialog = mydialog.create();
//                    dialog.show();
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return totallist.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView shopitemimage, editProduct;
        TextView prodname,price,quantity,desc,offer;
        CardView cv;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            cv=itemView.findViewById(R.id.cvshopcontent);
            shopitemimage=itemView.findViewById(R.id.shopitemimage);
            editProduct=itemView.findViewById(R.id.editProduct);
            prodname=itemView.findViewById(R.id.shopcontentname);
            quantity=itemView.findViewById(R.id.shopcontenquantityavailable);
            price=itemView.findViewById(R.id.shopcontentprice);
            desc=itemView.findViewById(R.id.shopcontentdesc);
            offer=itemView.findViewById(R.id.shopitemoffer);
        }
    }
}
