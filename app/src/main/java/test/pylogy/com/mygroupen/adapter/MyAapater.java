package test.pylogy.com.mygroupen.adapter;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import test.pylogy.com.mygroupen.R;
import test.pylogy.com.mygroupen.entity.YouLike;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class MyAapater extends BaseAdapter {
    private List<YouLike> list;
    public MyAapater(List<YouLike> list){
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_youlike,null);
            viewHolder.img_iv= (ImageView) convertView.findViewById(R.id.main_item_youlike_iv);
            viewHolder.tv_title= (TextView) convertView.findViewById(R.id.main_item_youlike_title);
            viewHolder.tv_distance= (TextView) convertView.findViewById(R.id.main_item_youlike_distance);
            viewHolder.tv_info= (TextView) convertView.findViewById(R.id.main_item_youlike_info);
            viewHolder.tv_price= (TextView) convertView.findViewById(R.id.main_item_youlike_price);
            viewHolder.tv_sales= (TextView) convertView.findViewById(R.id.main_item_youlike_sales);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.img_iv.setImageResource(R.mipmap.ic_launcher);
        viewHolder.tv_title.setText(list.get(position).getTitle());
        viewHolder.tv_distance.setText(list.get(position).getDistance());
        viewHolder.tv_info.setText(list.get(position).getInfo());
        viewHolder.tv_price.setText(list.get(position).getPrice());
        viewHolder.tv_sales.setText(list.get(position).getSales());
        return convertView;
    }
    private class ViewHolder{
        ImageView img_iv;
        TextView tv_title;
        TextView tv_distance;
        TextView tv_info;
        TextView tv_price;
        TextView tv_sales;
    }
}
