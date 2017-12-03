package name.sergey.shambir.utils;

import name.sergey.shambir.controllers.SupermarketSystem;
import name.sergey.shambir.models.Product;

import java.math.BigDecimal;
import java.util.Random;

public class ProductGenerator {
    private static final String[] FOOD_NAMES = {
        "milk",   "chocolate", "cocoa", "coffee", "meal",   "tomato",
        "potato", "onion",     "beef",  "ham",    "chicken"};
    private static final String[] DISH_NAMES = {
        "plate", "cup", "glass", "spoon", "fork", "tea-set", "teapot"};
    private static final String[] HYGIENE_PRODUCT_NAMES = {
        "wipes",     "soap",       "shampoo",   "comb",
        "hairdryer", "toothbrush", "toothpaste"};
    private static final String[] ALCOHOL_NAMES = {
        "wine", "whiskey", "beer", "liquor", "tequila", "martini"};
    private static final String[] SMOCKABLE_NAMES = {"cigarettes", "cigar"};

    private static final double MAX_PRICE = 20.0;
    private static final int MAX_BONUS_VALUE = 10;

    private final Random random;
    private final EnumRandomGenerator<Product.Category> categoryGenerator;

    public ProductGenerator(Random random) {
        this.random = random;
        this.categoryGenerator =
            new EnumRandomGenerator<>(random, Product.Category.class);
    }

    public Product nextProduct() {
        final Product.Category category = categoryGenerator.nextValue();
        final String name = nextProductName(category);
        final double price = MAX_PRICE * random.nextDouble();
        final int bonusPercentage = random.nextInt(MAX_BONUS_VALUE + 1);

        return new Product(name, category, new BigDecimal(price),
                           bonusPercentage);
    }

    private String nextProductName(Product.Category category) {
        String[] names;
        switch (category) {
            default:
            case Food:
                names = FOOD_NAMES;
                break;
            case Dish:
                names = DISH_NAMES;
                break;
            case HygieneProduct:
                names = HYGIENE_PRODUCT_NAMES;
                break;
            case Alcohol:
                names = ALCOHOL_NAMES;
                break;
            case Smokables:
                names = SMOCKABLE_NAMES;
                break;
        }
        final int index = random.nextInt(names.length);
        return names[index];
    }
}
