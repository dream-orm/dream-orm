package com.moxa.dream.drive.session;

import com.moxa.dream.system.core.executor.Executor;

public interface SessionFactory {
    Session openSession();

    Session openSession(Control control);

    Session openSession(Executor executor);
}
