package edu.uno.carter_mariah.recipebook;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

/**
 * Created by carter on 11/1/16.
 */
public class RecipeListFragment extends Fragment {
    RecipeList recipes;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.recipe_list_fragment, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle((String) getArguments().getSerializable("Title"));

        RecipeList recipeList = (RecipeList) getArguments().getSerializable("List");
        recipes = recipeList.getRecipesOfType((Recipe.Category) getArguments().getSerializable("Category"));

        ListView list = (ListView) view.findViewById(R.id.recipe_list);
        list.setAdapter(new ArrayAdapter(
                            view.getContext(),
                            android.R.layout.simple_list_item_1,
                            recipes.getRecipesNames()
                        )
        );
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment recipeFragment = new RecipeFragment();
                Bundle args = new Bundle();
                args.putStringArray("ingredients", recipes.get(position).items);
                recipeFragment.setArguments(args);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.recipe_list_fragment, recipeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
