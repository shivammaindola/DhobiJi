package viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.laundry.R;

import Interface.ItemClickListener;

/**
 * Created by Lenovo on 04-05-2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

   public TextView txtMenuName;
   public TextView food_name;
   public ImageView food_image;
   public ImageView imageView;
   public TextView txtMenuPrice;
    public TextView food_price;
    public TextView food_description;

   private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);

    txtMenuName=(TextView)itemView.findViewById(R.id.menu_name);
    imageView= (ImageView)itemView.findViewById(R.id.menu_image);
    txtMenuPrice= (TextView)itemView.findViewById(R.id.menu_price);
    food_name=(TextView)itemView.findViewById(R.id.food_name);
    food_image=(ImageView)itemView.findViewById(R.id.food_image);
    food_price= (TextView)itemView.findViewById(R.id.food_price);
    food_description=(TextView)itemView.findViewById(R.id.food_description);



    itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
  this.itemClickListener=itemClickListener;

    }
    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
