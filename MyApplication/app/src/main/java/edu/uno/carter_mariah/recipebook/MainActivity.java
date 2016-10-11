package edu.uno.carter_mariah.recipebook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private ListView list;
    private enum Category {ALL, BREAKFAST, LUNCH, DINNER, DESERT, DRINKS};
    private Category category = Category.ALL;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recipes.add(new Recipe("Pancakes", "Breakfast", new String[]{"This is a test step"}, new int[]{1}, new String[]{"This is a test quantity"}, new String[]{"oz"}));

        list = (ListView) findViewById(R.id.list_view);

        String[] list_items = new String[1];
        list_items[0] = recipes.get(0).name;

        list.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, list_items));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_all) {
            category = Category.ALL;
            toolbar.setTitle("Recipe Book");
        } else if (id == R.id.nav_breakfast) {
            category = Category.BREAKFAST;
            toolbar.setTitle("Recipe Book: Breakfast");
        } else if (id == R.id.nav_lunch) {
            category = Category.LUNCH;
            toolbar.setTitle("Recipe Book: Lunch");
        } else if (id == R.id.nav_dinner) {
            category = Category.DINNER;
            toolbar.setTitle("Recipe Book: Dinner");
        } else if (id == R.id.nav_desert) {
            category = Category.DESERT;
            toolbar.setTitle("Recipe Book: Desert");
        } else if (id == R.id.nav_drinks) {
            category = Category.DRINKS;
            toolbar.setTitle("Recipe Book: Drinks");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
