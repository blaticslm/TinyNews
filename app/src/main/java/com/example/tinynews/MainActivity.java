package com.example.tinynews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    //Second arrive
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* (res/navigation/nav_graph) and (res/layout/activity_main) LCA is MainActivity */
        //the navigation bottom
        BottomNavigationView navigationView = findViewById(R.id.nav_view);

        /*first item in main activity, and its name field is helper to get nav_host_frame --> historical reason*/
        //nav_host_frame -> nav_graph: 3 fragments
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_frame);

        //Combining: 3 fragments + bottom navigation bar
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navigationView, navController);

        //Combining with top action bar: this(MainActivity) + navController(3 fragments + bottom navigation bar)
        //NavigationUI.setupActionBarWithNavController(this, navController);

    }

    @Override
    public boolean onSupportNavigateUp() { //action bar back to last level
        return navController.navigateUp();
    }
}