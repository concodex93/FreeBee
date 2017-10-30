package com.example.conorbyrne.freebee;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // This is the main UI page of the App
    private Toolbar mToolbar;

    // Vars for tabs
    private ViewPager mViewPager;
    private TabSectionsPagerAdapter mTabSectionsPagerAdapter;
    private TabLayout mTabLayout;

    // Firebase related vars
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get signed in user instance
        mAuth = FirebaseAuth.getInstance();

        // Create toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("FreeBee");

        // For tabs
        mViewPager = (ViewPager) findViewById(R.id.tab_viewpager);
        mTabSectionsPagerAdapter = new TabSectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabSectionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    // Check if user is signed in (not null) and update UI accordingly
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null){

            sendToStart();
        }
    }

    // Creates menu in toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Logic for handling menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        // Log out btn
        if(item.getItemId() == R.id.menu_log_out_btn){

            // Will send back to StartActivity
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }

        return true;
    }

    // Sends user to StartActivity when logging out
    private void sendToStart(){
        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }
}
