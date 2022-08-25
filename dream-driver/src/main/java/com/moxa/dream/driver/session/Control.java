package com.moxa.dream.driver.session;

public class Control {
    private boolean cache;
    private boolean batch;
    private boolean autoCommit;

    private Control() {
    }

    public boolean isCache() {
        return cache;
    }

    public boolean isBatch() {
        return batch;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public static class Builder {
        Control control = new Control();

        public Builder cache(boolean cache) {
            control.cache = cache;
            return this;
        }

        public Builder batch(boolean batch) {
            control.batch = batch;
            return this;
        }

        public Builder autoCommit(boolean autoCommit) {
            control.autoCommit = autoCommit;
            return this;
        }

        public Control build() {
            return control;
        }
    }
}
