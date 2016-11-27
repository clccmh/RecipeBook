package edu.uno.carter_mariah.recipebook;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

/**
 * Created by carter on 11/1/16.
 */
public class RecipeListFragment extends Fragment {
    RecipeList recipes;
    RecipeList recipeList;
    View view;
    ListView list;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.recipe_list_fragment, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle((String) getArguments().getSerializable("Title"));

        recipeList = (RecipeList) getArguments().getSerializable("List");
        recipes = recipeList.getRecipesOfType((Recipe.Category) getArguments().getSerializable("Category"));

        list = (ListView) view.findViewById(R.id.recipe_list);
        setAdapter();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment recipeFragment = new RecipeFragment();
                Bundle args = new Bundle();
                args.putSerializable("recipe", recipes.get(position));
                recipeFragment.setArguments(args);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.recipe_list_fragment, recipeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        list.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                menu.add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        recipeList.remove(recipes.get(info.position));
                        recipes.remove(info.position);
                        setAdapter();
                        return false;
                    }
                });
            }
        });


        return view;
    }

    private void setAdapter() {
        list.setAdapter(new ArrayAdapter( view.getContext(), android.R.layout.simple_list_item_1, recipes.getRecipesNames()));
    }
}
