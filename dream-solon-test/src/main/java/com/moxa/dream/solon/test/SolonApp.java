package com.moxa.dream.solon.test;

import com.moxa.dream.solon.share.EnableShare;
import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.Solon;
//@EnableShare(HikariDataSource.class)
public class SolonApp {
    public static void main(String[] args) {
        Solon.start(SolonApp.class, args);
    }
}
