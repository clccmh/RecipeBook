package edu.uno.carter_mariah.recipebook;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * TODO: Persistent storage with sqllite
 * TODO: Add recipe activity
 * TODO: Conversions
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecipeList recipes = new RecipeList();
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
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recipes.add(new Recipe("Pancakes", Recipe.Category.BREAKFAST, new String[]{"This is a test step"}, new int[]{1}, new String[]{"This is a test quantity"}, new String[]{"oz"}));
        replaceListFragment(Recipe.Category.ALL, "Recipe Book: All");

       }

    private void replaceListFragment(Recipe.Category category, String title) {
        Fragment recipeListFragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putSerializable("Category", category);
        args.putSerializable("List", recipes);
        args.putString("Title", title);
        recipeListFragment.setArguments(args);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.recipe_list_fragment, recipeListFragment)
                .addToBackStack(null)
                .commit();
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
            replaceListFragment(Recipe.Category.ALL, "Recipe Book: All");
        } else if (id == R.id.nav_breakfast) {
            replaceListFragment(Recipe.Category.BREAKFAST, "Recipe Book: Breakfast");
        } else if (id == R.id.nav_lunch) {
            replaceListFragment(Recipe.Category.LUNCH, "Recipe Book: Lunch");
        } else if (id == R.id.nav_dinner) {
            replaceListFragment(Recipe.Category.DINNER, "Recipe Book: Dinner");
        } else if (id == R.id.nav_desert) {
            replaceListFragment(Recipe.Category.DESERT, "Recipe Book: Desert");
        } else if (id == R.id.nav_drinks) {
            replaceListFragment(Recipe.Category.DRINKS, "Recipe Book: Drinks");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
