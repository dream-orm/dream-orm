package com.moxa.dream.system.plugin;

public class PluginException extends RuntimeException {
    public PluginException(String msg) {
        super(msg);
    }

    public PluginException(Exception e) {
        super(e);
    }
}
