package com.infernoteam.helpmysql;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mohammed Issa on 3/4/2018.
 */

public class LayoutManager extends BaseAdapter {
    private ArrayList<Item>items;
    private Context context;

    public LayoutManager(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
         View view= LayoutInflater.from(context).inflate(R.layout.item,viewGroup,false);
         Item item=items.get(i);
         String name=item.getName();
         String company_name=item.getCompany_name();
         double salary=item.getSalary();
         TextView txt_name=view.findViewById(R.id._name);
         TextView txt_company_name=view.findViewById(R.id._company_name);
         TextView txt_salary=view.findViewById(R.id._salary);
         txt_name.setText(name);
         txt_company_name.setText(company_name);
         txt_salary.setText(String.valueOf(salary));
         return view;
    }
}
