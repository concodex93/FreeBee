package com.example.conorbyrne.freebee;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class HaveFragment extends Fragment {


    // UI vars
    private RecyclerView mHaveList;
    private View mMainView;


    // Firebase
    private DatabaseReference mItemDatabase;

    public HaveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_have, container, false);

        mHaveList = (RecyclerView) mMainView.findViewById(R.id.have_list);

        mItemDatabase = FirebaseDatabase.getInstance().getReference();

        mHaveList.setHasFixedSize(true);
        mHaveList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return mMainView;
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<Item, HaveViewHolder> itemFirebaseRecyclerViewAdapter =
                new FirebaseRecyclerAdapter<Item, HaveViewHolder>(
                        Item.class, R.layout.have_single_layout, HaveViewHolder.class, mItemDatabase) {
                    @Override
                    protected void populateViewHolder(HaveViewHolder wantViewHolder, Item item, int position) {

                        wantViewHolder.setName(item.getName());
                    }
                };

        mHaveList.setAdapter(itemFirebaseRecyclerViewAdapter);
    }



    public static class HaveViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public HaveViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setName(String name){

            TextView mItemNameView = (TextView) mView.findViewById(R.id.singletexthave);
            mItemNameView.setText(name);
        }


    }

}
