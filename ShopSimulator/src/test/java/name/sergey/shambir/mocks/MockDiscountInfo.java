package name.sergey.shambir.mocks;

import name.sergey.shambir.models.Customer;
import name.sergey.shambir.models.DiscountInfo;

public class MockDiscountInfo implements DiscountInfo {
    public int discountPercentage;

    @Override
    public int getDiscountPercentage(Customer customer) {
        return this.discountPercentage;
    }
}
