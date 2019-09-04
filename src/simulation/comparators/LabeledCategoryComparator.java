package simulation.comparators;

import java.util.List;

public abstract class LabeledCategoryComparator extends LabeledComparator{
    private int categoriesCounter;
    private String[] categories;

    LabeledCategoryComparator(String label, String descr, String[] categories){
        super(label,descr);
        this.categories=categories;
        categoriesCounter=categories.length;
    }

    public int getCategoriesCounter() {
        return categoriesCounter;
    }

    public String getCategory(int i){
        return categories[i];
    }
}
