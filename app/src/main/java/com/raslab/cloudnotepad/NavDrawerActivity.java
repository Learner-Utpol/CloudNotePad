package com.raslab.cloudnotepad;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , NoteFragment.NewNote , TodoListFragment.NewTodoList, NewNoteFragment.NewmNoteFragment,
        TranslationFragment.Translator , NewToDoListFragment.backTodolist{
    private FragmentManager fragmentManager;
    private TranslationFragment.Translator onNewTranslatorFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        mainFragment();

       // finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id==R.id.action_Logout){
            signOut();
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, NavDrawerActivity.class);
            startActivity(intent);
        } else if (id == R.id.navTranslate) {
          onNewTranslatorFragment();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent( NavDrawerActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        // [END auth_fui_signout]
    }
    private void mainFragment() {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        MainFragment mainFragment= new MainFragment();
        ft.add(R.id.constraintLayout, mainFragment);
       //ft.addToBackStack(null);
        ft.commit();
       // finish();
    }
    @Override
    public void onNewNoteFgStart() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft =fragmentManager.beginTransaction();
        NewNoteFragment newNoteFragment= new NewNoteFragment();
        ft.replace(R.id.constraintLayout,newNoteFragment);
        ft.addToBackStack(null);
        ft.commit();

    }


    @Override
    public void onNewTodoList() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft =fragmentManager.beginTransaction();
        NewToDoListFragment newToDoListFragment= new NewToDoListFragment();
        ft.replace(R.id.constraintLayout,newToDoListFragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    @Override
    public void onNewNoteFragment() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        MainFragment mainFragment= new MainFragment();
        ft.replace(R.id.constraintLayout, mainFragment);
        //ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public void onNewTranslatorFragment() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        TranslationFragment translationFragment = new TranslationFragment();
        ft.replace(R.id.constraintLayout,translationFragment);
        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public void onBackTodoList() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        MainFragment mainFragment= new MainFragment();
        ft.replace(R.id.constraintLayout, mainFragment);
        //ft.addToBackStack(null);
        ft.commit();
    }
}
