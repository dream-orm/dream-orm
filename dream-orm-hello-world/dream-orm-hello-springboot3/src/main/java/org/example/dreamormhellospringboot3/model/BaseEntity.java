package org.example.dreamormhellospringboot3.model;


import com.dream.system.annotation.Ignore;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 创建者
     */
    @Ignore
    private Object createBy;

    /**
     * 创建时间
     */
    @Ignore
    private java.util.Date createTime;

    /**
     * 更新者
     */
    @Ignore
    private Object updateBy;

    /**
     * 更新时间
     */
    @Ignore
    private java.util.Date updateTime;
}
