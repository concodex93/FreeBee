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

public class WantFragment extends Fragment {

    // UI vars
    private RecyclerView mWantList;
    private View mMainView;


    // Firebase
    private DatabaseReference mItemDatabase;

    public WantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_want, container, false);

        mWantList = (RecyclerView) mMainView.findViewById(R.id.want_list);

        mItemDatabase = FirebaseDatabase.getInstance().getReference();

        mWantList.setHasFixedSize(true);
        mWantList.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return mMainView;
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<Item, WantViewHolder> itemFirebaseRecyclerViewAdapter =
                new FirebaseRecyclerAdapter<Item, WantViewHolder>(
                Item.class, R.layout.want_single_layout, WantViewHolder.class, mItemDatabase) {
            @Override
            protected void populateViewHolder(WantViewHolder wantViewHolder, Item item, int position) {

                wantViewHolder.setName(item.getName());
            }
        };

        mWantList.setAdapter(itemFirebaseRecyclerViewAdapter);
    }



    public static class WantViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public WantViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setName(String name){

            TextView mItemNameView = (TextView) mView.findViewById(R.id.textviewsingle);
            mItemNameView.setText(name);
        }


    }
}

