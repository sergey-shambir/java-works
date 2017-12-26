package name.sergey.shambir.random;

import name.sergey.shambir.models.Product;
import name.sergey.shambir.quantity.QuantityCategory;

public class ProductGenerator {
    private static final String[] FOOD_NAMES = {"milk",   "chocolate", "cocoa", "coffee", "meal",   "tomato",
                                                "potato", "onion",     "beef",  "ham",    "chicken"};
    private static final String[] DISH_NAMES = {"plate", "cup", "glass", "spoon", "fork", "tea-set", "teapot"};
    private static final String[] HYGIENE_PRODUCT_NAMES = {"wipes",     "soap",       "shampoo",   "comb",
                                                           "hairdryer", "toothbrush", "toothpaste"};
    private static final String[] ALCOHOL_NAMES = {"wine", "whiskey", "beer", "liquor", "tequila", "martini"};
    private static final String[] SMOCKABLES_NAMES = {"cigarettes", "cigar"};

    private static final double MIN_PRICE = 2.0;
    private static final double MAX_PRICE = 20.0;
    private static final int MAX_BONUS_VALUE = 10;

    private final EasyRandom random;
    private final EnumRandomGenerator<Product.Category> categoryGenerator;
    private final EnumRandomGenerator<QuantityCategory> quantityCategoryGenerator;

    public ProductGenerator(EasyRandom random) {
        this.random = random;
        this.categoryGenerator = new EnumRandomGenerator<>(random, Product.Category.class);
        this.quantityCategoryGenerator = new EnumRandomGenerator<>(random, QuantityCategory.class);
    }

    public Product nextProduct() {
        final Product.Category category = this.categoryGenerator.nextValue();
        final QuantityCategory quantityCategory = this.quantityCategoryGenerator.nextValue();
        final String name = nextProductName(category);
        final double price = this.random.nextDoubleInRange(MIN_PRICE, MAX_PRICE);
        final double bonusPercentage = this.random.nextIntInRange(0, MAX_BONUS_VALUE);

        return new Product(name, category, quantityCategory, price, bonusPercentage);
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
            case Smockables:
                names = SMOCKABLES_NAMES;
                break;
        }
        return random.nextItem(names);
    }
}
