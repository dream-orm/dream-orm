package org.example.dreamormhellospringboot.sequence;

import com.dream.system.table.ColumnInfo;
import com.dream.template.sequence.AbstractSequence;

public class SnowFlakeSequence extends AbstractSequence {
    SnowFlake snowFlake = new SnowFlake();

    @Override
    protected Object sequence(ColumnInfo columnInfo) {
        Class<?> type = columnInfo.getField().getType();
        Long nextId = snowFlake.next();
        if (type == Long.class) {
            return nextId;
        } else if (type == String.class) {
            return String.valueOf(nextId);
        } else if (type == Integer.class) {
            return nextId.intValue();
        }
        throw new RuntimeException("不支持的主键类型：" + type.getName());
    }
}
