package name.sshambir.product;

public class MockDiscountInfo implements DiscountInfo {
    public int discountPercentage;

    @Override
    public int getDiscountPercentage(CustomerCategory customer) {
        return this.discountPercentage;
    }
}
