package com.dream.drive.transaction;

class TransactionContext {

    private static final ThreadLocal<String> XID_HOLDER = new ThreadLocal<>();

    private TransactionContext() {
    }

    public static String getXID() {
        return XID_HOLDER.get();
    }

    public static void release() {
        XID_HOLDER.remove();
    }

    public static void holdXID(String xid) {
        XID_HOLDER.set(xid);
    }


}
