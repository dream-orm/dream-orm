package com.moxa.dream.driver.listener;


import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.antlr.sql.ToDREAM;
import com.moxa.dream.module.core.listener.DeleteListener;
import com.moxa.dream.module.core.listener.InsertListener;
import com.moxa.dream.module.core.listener.QueryListener;
import com.moxa.dream.module.core.listener.UpdateListener;
import com.moxa.dream.module.mapped.MappedParam;
import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DebugListener implements QueryListener, UpdateListener, InsertListener, DeleteListener {
	static ToDREAM toDREAM=new ToDREAM();
	static final String START_DATE="startDate";
	static final String LOGS="logs";
	static final String lineSeparator = System.getProperty("line.separator", "\n");
	@Override
	public void before(MappedStatement mappedStatement) {
		mappedStatement.put(START_DATE,System.currentTimeMillis());
		PackageStatement statement = mappedStatement.getStatement();
		List<MappedParam> mappedParamList = mappedStatement.getMappedParamList();
		List<Object> paramList;
		if(!ObjectUtil.isNull(mappedParamList)){
			paramList = mappedParamList.stream().map(mappedParam -> mappedParam.getParamValue()).collect(Collectors.toList());
		}else{
			paramList=new ArrayList<>();
		}
		String sql = mappedStatement.getSql();
		StringBuilder builder = new StringBuilder();
		builder.append("SQL:"+sql).append(lineSeparator);
		builder.append("PARAM:"+paramList).append(lineSeparator);
		mappedStatement.put(LOGS,builder);
	}

	@Override
	public void afterReturn(Object result, MappedStatement mappedStatement) {
		after(mappedStatement);
	}

	@Override
	public void exception(Exception e, MappedStatement mappedStatement) {
		StringBuilder builder=(StringBuilder) mappedStatement.get(LOGS);
		builder.append("ERROR:"+e).append(lineSeparator);
		after(mappedStatement);
	}
	public void after(MappedStatement mappedStatement){
		StringBuilder builder=(StringBuilder) mappedStatement.get(LOGS);
		builder.append("TIME:"+(System.currentTimeMillis()-(long)mappedStatement.get(START_DATE))+"ms").append(lineSeparator);
		System.out.println(builder);
	}
}
