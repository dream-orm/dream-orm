package com.moxa.dream.antlr.read;

import com.moxa.dream.antlr.bind.ExprInfo;
import com.moxa.dream.antlr.bind.ExprType;
import com.moxa.dream.antlr.expr.SqlExpr;
import com.moxa.dream.antlr.factory.MyFunctionFactory;
import com.moxa.dream.antlr.smt.CustomFunctionStatement;
import com.moxa.dream.antlr.util.ExprUtil;

import java.util.Locale;
import java.util.Stack;

public class ExprReader extends StringReader {

    private Stack<SqlExpr> exprStack = new Stack();

    private ExprInfo lastInfo;

    private Stack<ExprInfo> exprInfoStack = new Stack<>();

    private MyFunctionFactory myFunctionFactory;

    public ExprReader(String sql) {
        this(sql, null);
    }


    public ExprReader(String sql, MyFunctionFactory myFunctionFactory) {
        super(sql);
        this.myFunctionFactory = myFunctionFactory;
    }

    public String getSql() {
        return this.value;
    }

    public ExprInfo push() {
        mark();
        int c = read();
        switch (c) {
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
                reset();
                lastInfo = pushNumber();
                break;
            case 38:
                lastInfo = new ExprInfo(ExprType.BITAND, "&", getStart(), getEnd());
                break;
            case 39:
                reset();
                lastInfo = pushStr();
                break;
            case 36:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 95:
                reset();
                lastInfo = pushLetter();
                break;
            case 9:
            case 10:
            case 32:
                lastInfo = push();
                break;
            case 37:
                lastInfo = new ExprInfo(ExprType.MOD, "%", getStart(), getEnd());
                break;
            case 40:
                lastInfo = new ExprInfo(ExprType.LBRACE, "(", getStart(), getEnd());
                break;
            case 41:
                lastInfo = new ExprInfo(ExprType.RBRACE, ")", getStart(), getEnd());
                break;
            case 42:
                lastInfo = new ExprInfo(ExprType.STAR, "*", getStart(), getEnd());
                break;
            case 43:
                lastInfo = new ExprInfo(ExprType.ADD, "+", getStart(), getEnd());
                break;
            case 45:
                lastInfo = new ExprInfo(ExprType.SUB, "-", getStart(), getEnd());
                break;
            case 47:
                lastInfo = new ExprInfo(ExprType.DIVIDE, "/", getStart(), getEnd());
                break;
            case 44:
                lastInfo = new ExprInfo(ExprType.COMMA, ",", getStart(), getEnd());
                break;
            case 58:
                lastInfo = new ExprInfo(ExprType.COLON, ":", getStart(), getEnd());
                break;
            case 60:
                mark();
                c = read();
                switch (c) {
                    case 60:
                        lastInfo = new ExprInfo(ExprType.LLM, "<<", getStart(), getEnd());
                        break;
                    case 61:
                        lastInfo = new ExprInfo(ExprType.LEQ, "<=", getStart(), getEnd());
                        break;
                    case 62:
                        lastInfo = new ExprInfo(ExprType.NEQ, "<>", getStart(), getEnd());
                        break;
                    default:
                        reset();
                        lastInfo = new ExprInfo(ExprType.LT, "<", getStart(), getEnd());
                        break;
                }
                break;
            case 61:
                lastInfo = new ExprInfo(ExprType.EQ, "=", getStart(), getEnd());
                break;
            case 62:
                mark();
                c = read();
                switch (c) {
                    case 61:
                        lastInfo = new ExprInfo(ExprType.GEQ, ">=", getStart(), getEnd());
                        break;
                    case 62:
                        lastInfo = new ExprInfo(ExprType.RRM, ">>", getStart(), getEnd());
                        break;
                    default:
                        reset();
                        lastInfo = new ExprInfo(ExprType.GT, ">", getStart(), getEnd());
                        break;
                }
                break;
            case 63:
                lastInfo = new ExprInfo(ExprType.MARK, "?", getStart(), getEnd());
                break;
            case 64:
                mark();
                c = read();
                switch (c) {
                    case 40:
                        lastInfo = pushSkip();
                        break;
                    default:
                        reset();
                        lastInfo = new ExprInfo(ExprType.INVOKER, "@", getStart(), getEnd());
                        break;
                }
                break;
            case 94:
                lastInfo = new ExprInfo(ExprType.BITXOR, "^", getStart(), getEnd());
                break;
            case 96:
                lastInfo = new ExprInfo(ExprType.SINGLE_MARK, "`", getStart(), getEnd());
                break;
            case 124:
                lastInfo = new ExprInfo(ExprType.BITOR, "|", getStart(), getEnd());
                break;
            case -1:
                lastInfo = new ExprInfo(ExprType.ACC, "ACC", getStart(), getEnd());
                break;
            default:
                lastInfo = new ExprInfo(ExprType.ERR, String.valueOf((char) c), getStart(), getEnd());
                break;
        }
        return lastInfo;
    }

    private ExprInfo pushLetter() {
        mark();
        int count = 0;
        int c;
        while ((c = read()) != -1 && (ExprUtil.isLetter(c) || ExprUtil.isNumber(c))) {
            count++;
        }
        reset();
        char[] chars = new char[count];
        int len = read(chars, 0, count);
        String info = new String(chars, 0, len);
        if (ExprUtil.isDot(c)) {
            info += pushDotLetter();
        }
        ExprType exprType = ExprUtil.getExprTypeInLetter(info);
        if (ExprUtil.isLBrace(c) && myFunctionFactory != null) {
            CustomFunctionStatement customFunctionStatement = myFunctionFactory.create(info);
            if (customFunctionStatement != null) {
                customFunctionStatement.setFunctionName(info.toUpperCase(Locale.ENGLISH));
                return new ExprInfo(ExprType.MY_FUNCTION, customFunctionStatement, getStart(), getEnd());
            }
        } else if (ExprUtil.isBoolean(info)) {
            return new ExprInfo(ExprType.BOOLEAN, info, getStart(), getEnd());
        }
        return new ExprInfo(exprType, info, getStart(), getEnd());
    }

    private String pushDotLetter() {
        mark();
        skip(1);
        int count = 1;
        int c;
        while ((c = read()) != -1 && (ExprUtil.isLetter(c) || ExprUtil.isNumber(c) || ExprUtil.isStar(c))) {
            count++;
        }
        reset();
        char[] chars = new char[count];
        int len = read(chars, 0, count);
        String info = new String(chars, 0, len);
        if (ExprUtil.isDot(c)) {
            info += pushDotLetter();
        }
        return info;
    }

    private ExprInfo pushStr() {
        mark();
        skip(1);
        int count = 1;
        int c;
        while ((c = read()) != -1 && !ExprUtil.isStr(c)) {
            count++;
        }
        count++;
        reset();
        char[] chars = new char[count];
        int len = read(chars, 0, count);
        String info = new String(chars, 0, len);
        return new ExprInfo(ExprType.STR, info, getStart(), getEnd());
    }

    public ExprInfo pushSkip() {
        mark();
        int c, count = 1;
        int balance = 0;
        while ((c = read()) != -1 && (!ExprUtil.isRBrace(c) || balance > 0)) {
            if (ExprUtil.isLBrace(c))
                balance++;
            else if (ExprUtil.isRBrace(c))
                balance--;
            count++;
        }
        reset();
//        if (!ExprUtil.isRBrace(c)) {
//
//        }
        char[] chars = new char[count];
        int len = read(chars, 0, count);
        String info = new String(chars, 0, len - 1);
        return new ExprInfo(ExprType.SKIP, info, getStart(), getEnd());
    }

    private ExprInfo pushNumber() {
        mark();
        int c;
        int count = 0;
        ExprType exprType = ExprType.INT;
        while ((c = read()) != -1 && (ExprUtil.isNumber(c) || ExprUtil.isDot(c))) {
            if (ExprUtil.isDot(c)) {
                if (exprType == ExprType.INT)
                    exprType = ExprType.DOUBLE;
                else
                    throw new RuntimeException("The number format is incorrect");
            }
            count++;
        }
        if (ExprUtil.isF(c)) {
            skip(1);
            if (exprType == ExprType.INT || exprType == ExprType.DOUBLE) {
                exprType = ExprType.FLOAT;
            } else throw new RuntimeException("The number format is incorrect");
        } else if (ExprUtil.isL(c)) {
            skip(1);
            if (exprType == ExprType.INT) {
                exprType = ExprType.LONG;
            } else throw new RuntimeException("The number format is incorrect");
        } else if (ExprUtil.isD(c)) {
            skip(1);
            if (exprType == ExprType.INT) {
                exprType = ExprType.DOUBLE;
            } else throw new RuntimeException("The number format is incorrect");
        }
        reset();
        char[] chars = new char[count];
        int len = read(chars, 0, count);
        String info = new String(chars, 0, len);
        return new ExprInfo(exprType, info, getStart(), getEnd());

    }

    public Stack<ExprInfo> getExprInfoStack() {
        return exprInfoStack;
    }

    public void pop() {
        exprStack.pop();
    }


    public boolean tryMark(SqlExpr abstractExpr) {
        if (exprStack.isEmpty() || exprStack.peek() != abstractExpr)
            return exprStack.add(abstractExpr);
        return false;
    }

    public void push(ExprInfo exprInfo) {
        exprInfoStack.push(exprInfo);
    }

    public ExprInfo getLastInfo() {
        return lastInfo;
    }
}
