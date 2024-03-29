package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;

import java.util.HashMap;
import java.util.Map;

/**
 * 辅助语法器，复杂SQL翻译抽象树的核心
 */
public abstract class HelperExpr extends SqlExpr {
    //accept缓存
    public static Map<String, Boolean> acceptMap = new HashMap<>();
    //辅助语法器生成类
    protected Helper helper;
    //辅助语法器
    protected SqlExpr helpExpr;
    //解析器名称
    private String name;
    //辅助语法器是否可以解析单词
    private Boolean accept;

    public HelperExpr(ExprReader exprReader, Helper helper) {
        super(exprReader);
        this.helper = helper;
        this.helpExpr = helper.helper();
        setExprTypes(ExprType.HELP, ExprType.NIL);
    }

    /**
     * 获取本次语法器与请的辅助语法器是否可解析单词（辅助语法器可以继续请辅助）
     *
     * @param exprType
     * @return
     */
    @Override
    protected boolean exprBefore(ExprType exprType) {
        boolean exprBefore = super.exprBefore(exprType);
        if (!exprBefore) {
            SqlExpr helpExpr0 = helpExpr;
            String key = this.name() + ":" + exprType.name();
            accept = acceptMap.get(key);
            if (accept == null) {
                accept = false;
                while (true) {
                    accept |= helpExpr0.exprBefore(exprType);
                    if (accept) {
                        break;
                    }
                    if (helpExpr0 instanceof HelperExpr) {
                        helpExpr0 = ((HelperExpr) helpExpr0).helper.helper();
                    } else {
                        break;
                    }
                }
                acceptMap.put(key, accept);
            }
        }
        return exprBefore;
    }

    /**
     * 规约，如果本语法器可以解析单词，调用解析单词函数，
     * 否则如果辅助语法器可解析，让帮助语法器解析并获取抽象树
     * 否则调用父类规约
     *
     * @param exprInfo 单词信息
     * @return
     * @throws AntlrException
     */
    @Override
    public Statement exprNil(ExprInfo exprInfo) throws AntlrException {
        if (self) {
            return exprSelf(exprInfo);
        }
        if (accept && acceptList.contains(ExprType.HELP)) {
            Statement statement = helpExpr.expr();
            helpExpr = helper.helper();
            return exprHelp(statement);
        } else {
            return super.exprNil(exprInfo);
        }
    }

    /**
     * 处理辅助语法器解析生成的抽象树
     *
     * @param statement
     * @return
     * @throws AntlrException
     */
    protected abstract Statement exprHelp(Statement statement) throws AntlrException;

    protected String name() {
        if (name == null) {
            name = super.name() + ":" + helpExpr.name();
        }
        return name;
    }

    public interface Helper {
        SqlExpr helper();
    }
}
