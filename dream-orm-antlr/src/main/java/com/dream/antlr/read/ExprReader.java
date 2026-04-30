package com.dream.antlr.read;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.util.ExprUtil;

public class ExprReader extends StringReader {
    private ExprInfo lastInfo;

    public ExprReader(String sql) {
        super(sql);
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
            case 34:
                reset();
                lastInfo = pushJavaStr();
                break;
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
            case 13:
            case 32:
                lastInfo = push();
                break;
            case 33:
                mark();
                c = read();
                if (c == 61) {
                    lastInfo = new ExprInfo(ExprType.NEQ, "<>", getStart(), getEnd());
                } else {
                    reset();
                    lastInfo = new ExprInfo(ExprType.ERR, "!", getStart(), getEnd());
                }
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
                reset();
                lastInfo = pushComment();
                if (lastInfo.getExprType() == ExprType.COMMENT) {
                    lastInfo = push();
                }
                break;
            case 46:
                lastInfo = new ExprInfo(ExprType.DOT, ".", getStart(), getEnd());
                break;
            case 47:
                lastInfo = new ExprInfo(ExprType.DIVIDE, "/", getStart(), getEnd());
                break;
            case 44:
                lastInfo = new ExprInfo(ExprType.COMMA, ",", getStart(), getEnd());
                break;
            case 59:
                lastInfo = new ExprInfo(ExprType.SEMICOLON, ";", getStart(), getEnd());
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
                lastInfo = new ExprInfo(ExprType.INVOKER, "@", getStart(), getEnd());
                break;
            case 94:
                lastInfo = new ExprInfo(ExprType.BITXOR, "^", getStart(), getEnd());
                break;
            case 96:
                reset();
                lastInfo = pushSingleMark();
                break;
            case 124:
                lastInfo = new ExprInfo(ExprType.BITOR, "|", getStart(), getEnd());
                break;
            case -1:
                lastInfo = new ExprInfo(ExprType.ACC, "ACC", getStart(), getEnd());
                break;
            default:
                if (c >= 19968) {
                    reset();
                    lastInfo = pushLetter();
                    break;
                } else {
                    lastInfo = new ExprInfo(ExprType.ERR, String.valueOf((char) c), getStart(), getEnd());
                }
                break;
        }
        return lastInfo;
    }

    public ExprInfo next() {
        int nextMark = next;
        ExprInfo lastInfoMark = lastInfo;
        ExprInfo exprInfo = push();
        next = nextMark;
        lastInfo = lastInfoMark;
        return exprInfo;
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
        ExprType exprType = ExprUtil.getExprTypeInLetter(info);
        if (!ExprUtil.isKeyWord(exprType) && ExprUtil.isFunction(exprType) && !ExprUtil.isLBrace(c)) {
            exprType = ExprType.LETTER;
        } else if (ExprType.LETTER == exprType && ExprUtil.isLBrace(c)) {
            exprType = ExprType.MY_FUNCTION;
        }
        return new ExprInfo(exprType, info, getStart(), getEnd());
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
        String info = new String(chars, 1, len - 2);
        return new ExprInfo(ExprType.STR, info, getStart(), getEnd());
    }

    private ExprInfo pushJavaStr() {
        mark();
        skip(1);
        int count = 1;
        int c;
        while ((c = read()) != -1 && !ExprUtil.isJavaStr(c)) {
            count++;
        }
        count++;
        reset();
        char[] chars = new char[count];
        int len = read(chars, 0, count);
        String info = new String(chars, 1, len - 2);
        return new ExprInfo(ExprType.JAVA_STR, info, getStart(), getEnd());
    }

    private ExprInfo pushSingleMark() {
        mark();
        skip(1);
        int count = 1;
        int c;
        while ((c = read()) != -1 && !ExprUtil.isSingleMark(c)) {
            count++;
        }
        count++;
        reset();
        char[] chars = new char[count];
        int len = read(chars, 0, count);
        String info = new String(chars, 1, len - 2);
        return new ExprInfo(ExprType.SINGLE_MARK, info, getStart(), getEnd());
    }

    private ExprInfo pushNumber() {
        mark();
        int c;
        int count = 0;
        while ((c = read()) != -1 && (ExprUtil.isNumber(c) || ExprUtil.isDot(c))) {
            count++;
        }
        if (ExprUtil.isLetter(c)) {
            reset();
            return pushLetter();
        }
        reset();
        char[] chars = new char[count];
        int len = read(chars, 0, count);
        String info = new String(chars, 0, len);
        return new ExprInfo(ExprType.NUMBER, info, getStart(), getEnd());

    }

    private ExprInfo pushComment() {
        mark();
        if (read() == 45) {
            if (read() == 45) {
                if (read() == 32) {
                    int count = 3;
                    int c = read();
                    while (c != -1 && c != 10) {
                        c = read();
                        count++;
                    }
                    reset();
                    char[] chars = new char[count];
                    int len = read(chars, 0, count);
                    String comment = new String(chars, 0, len);
                    return new ExprInfo(ExprType.COMMENT, comment, getStart(), getEnd());
                }
            }
            reset();
            read();
            return new ExprInfo(ExprType.SUB, "-", getStart(), getEnd());
        } else {
            reset();
            return push();
        }
    }

    public ExprInfo getLastInfo() {
        return lastInfo;
    }
}
