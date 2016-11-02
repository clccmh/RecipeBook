package edu.uno.carter_mariah.recipebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carter on 11/2/16.
 */
public class RecipeList extends ArrayList<Recipe>{

    public RecipeList() {
        super();
    }

    /**
     *
     * @param category
     */
    public RecipeList getRecipesOfType(Recipe.Category category) {
        //TODO: Condense with java lambdas once java8 compatibility is better in android
        RecipeList list_items = new RecipeList();
        if (category == Recipe.Category.ALL) {
            for (int i = 0; i < super.size(); i++) {
                list_items.add(super.get(i));
            }
        } else {
            for (int i = 0; i < super.size(); i++) {
                if (super.get(i).category == category) {
                    list_items.add(super.get(i));
                }
            }
        }
        return list_items;
    }

    public String[] getRecipesNames() {
        String[] names = new String[super.size()];
        for (int i = 0; i < super.size(); i++) {
            names[i] = super.get(i).name;
        }
        return names;
    }
}
