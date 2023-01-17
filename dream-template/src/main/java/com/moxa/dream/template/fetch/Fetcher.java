package com.moxa.dream.template.fetch;

import java.util.Map;

public interface Fetcher {
    void fetch(Object result, String property, Map<String, Object> paramMap);
}
