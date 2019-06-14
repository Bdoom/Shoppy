package com.example.shoppy;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;


// Add multiple stores.
// Bed Stores:
// Matress Firm
// Matress warehouse
// Sears
// Macy's
// Sleep number?
// Electronics Stores:
// Best Buy
// Microcenter
// Clothing?:
// Abercrombie & fitch
// Hollister
// gap
// Hotels?
// Compare hotel prices?
// Flights?
// Compare flight prices?

public class MainActivity extends AppCompatActivity implements  mainFragment.OnFragmentInteractionListener, listFragment.OnFragmentInteractionListener, graphFragment.OnFragmentInteractionListener {

    private Activity myActivity;
    private mainFragment mainFragment;
    private SearchContainer searchContainer;

    public mainFragment GetMainFragment()
    {
        if (mainFragment != null)
        {
            return mainFragment;
        }
        else
        {
            System.out.println("Main fragment is null. Returning null.");
            return null;
        }
    }


    public void StartSort()
    {
        Intent intent = new Intent(getBaseContext(), MultipleSearchResultsActivity.class);

        //intent.putExtra("EXTRA_SESSION_ID", sessionId);
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_main:
                    selectedFragment = mainFragment.newInstance();
                    mainFragment = (mainFragment)selectedFragment;
                    break;

                case R.id.navigation_list:
                    selectedFragment = listFragment.newInstance();
                    break;

                case R.id.navigation_graphs:
                    selectedFragment = new graphFragment();
                    break;

            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();


            return true;
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mainFragment theMainFragment = mainFragment.newInstance();
        transaction.replace(R.id.frame_layout, theMainFragment);
        transaction.commit();

        searchContainer = new SearchContainer(this);

        mainFragment = theMainFragment;

        MobileAds.initialize(this, "ca-app-pub-6636580205361410~8791143179");

        myActivity = this;

    }

}
