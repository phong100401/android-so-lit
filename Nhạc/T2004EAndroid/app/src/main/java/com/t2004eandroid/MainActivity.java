package com.t2004eandroid;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.t2004eandroid.activity.FragmentLogin;
import com.t2004eandroid.activity.FragmentRegister;
import com.t2004eandroid.activity.MainActivity2;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FrameLayout frameLayout; // chứa nội dung chính
    BottomNavigationView bottomNavigationView;

    FragmentRegister fragmentRegister;
    FragmentLogin fragmentLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        frameLayout = findViewById(R.id.frame_layout);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        navigationView.bringToFront();
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(
                        this,
                        drawerLayout,
                        toolbar,
                        R.string.open_menu,
                        R.string.close_menu);
        drawerLayout.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnItemSelectedListener(this);

        // add fragment.
        fragmentRegister = new FragmentRegister();
        fragmentLogin = new FragmentLogin();
//        helloFragment = new HelloFragment();
//
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragmentRegister, FragmentRegister.class.getName())
                .commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_button:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, fragmentRegister, FragmentRegister.class.getName())
                        .commit();
                break;
            case R.id.policy:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, fragmentLogin, FragmentLogin.class.getName())
                        .commit();
                break;
            case R.id.list_contact:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, fragmentLogin, FragmentLogin.class.getName())
                        .commit();
                break;
            case R.id.add_contact:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, fragmentRegister, FragmentRegister.class.getName())
                        .commit();
                break;
        }
        return false;
    }
}