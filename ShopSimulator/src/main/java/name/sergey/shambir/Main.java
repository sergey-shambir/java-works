package name.sergey.shambir;

import name.sergey.shambir.controllers.ShopWorld;

// TODO: fix static analyzer issues.
class Main {
    public static void main(String[] args) {
        try {
            ShopWorld world = new ShopWorld();
            world.runLoop();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
