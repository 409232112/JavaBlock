package wyc.block.entity;

import java.util.Map;

public class Wallets {

    private static Map<String,Wallet> wallets;

    public static Map<String, Wallet> getWallets() {
        return wallets;
    }

    public static void setWallets(Map<String, Wallet> wallets) {
        Wallets.wallets = wallets;
    }
}
