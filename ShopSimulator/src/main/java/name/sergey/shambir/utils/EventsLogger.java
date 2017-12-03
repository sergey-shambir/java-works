package name.sergey.shambir.utils;

import java.math.BigDecimal;
import java.time.LocalTime;

public class EventsLogger {
    private LocalTime currentTime;

    public EventsLogger(LocalTime currentTime) {
        this.currentTime = currentTime;
    }

    public void setCurrentTime(LocalTime currentTime) {
        this.currentTime = currentTime;
    }

    public void logCustomerEntered(String name, int customerCount) {
        System.out.printf("[%s] New customer '%s' arrived, there are %d customers\n", getTime(), name, customerCount);
    }

    public void logCustomerPickUpProduct(String name, String productName, int count) {
        String units = (count == 1) ? "unit" : "units";
        System.out.printf("[%s] Customer '%s' picked up %d %s of %s\n", getTime(), name, count, units, productName);
    }

    public void logCustomerStoppedShopping(String name) {
        System.out.printf("[%s] Customer '%s' goes to cash desk\n", getTime(), name);
    }

    public void logCustomerPaid(String name, String[] productsNames, BigDecimal price, BigDecimal bonuses) {
        System.out.printf("[%s] Customer '%s' paid %s for %s, got %s bonuses\n", getTime(), name, price.toString(),
                          formatProductNames(productsNames), bonuses.toString());
    }

    public void logCustomerPaymentFailed(String name, BigDecimal price) {
        System.out.printf("[%s] Customer '%s' cannot pay %s and leaves shop without any product\n", getTime(), name,
                          price.toString());
    }

    public void logCustomerLeaveDry(String name) {
        System.out.printf("[%s] Customer '%s' leaves shop without any product\n", getTime(), name);
    }

    private static String formatProductNames(String[] productsNames) {
        String text = "";
        switch (productsNames.length) {
            case 1:
                text += productsNames[0];
                break;
            case 2:
                text += productsNames[0] + " and " + productsNames[1];
                break;
            default: {
                final String remaining = Integer.toString(productsNames.length - 2);
                text += productsNames[0] + ", " + productsNames[1] + " and " + remaining + " other products";
                break;
            }
        }
        return text;
    }

    private final String getTime() {
        return this.currentTime.toString();
    }
}
