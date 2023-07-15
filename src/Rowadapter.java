package com.example.workshop30;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.workshop30.databinding.CustomRowsBinding;
import com.example.workshop30.models.Weapon;
import com.example.workshop30.databinding.CustomRowsBinding;

import java.util.ArrayList;

public class Rowadapter extends RecyclerView.Adapter<Rowadapter.ItemViewHolder> {
private  Context context = null;
private ArrayList<Weapon> weaponList = null;
CustomRowsBinding binding;

public Rowadapter(Context context, ArrayList<Weapon> weapons){
    this.weaponList = weapons;
    this.context = context;

}

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(CustomRowsBinding.inflate(LayoutInflater.from(context), parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Weapon currentWeapon = weaponList.get(position);
        holder.bind(context, currentWeapon);
    }





    @Override
    public int getItemCount() {
        return weaponList.size();
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        CustomRowsBinding itemBinding;

        public ItemViewHolder(@NonNull CustomRowsBinding binding){
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        public void bind(Context context, Weapon currentWeapon){
itemBinding.name.setText(currentWeapon.getName());
itemBinding.attack.setText("Att: " + currentWeapon.getAttack());
            itemBinding.def.setText("Def: " + currentWeapon.getDefense());
            itemBinding.desc.setText(currentWeapon.getDescription());
            Glide.with(context).load(currentWeapon.getImage()).into(itemBinding.icon);
        }
    }
}
