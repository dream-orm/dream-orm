package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.bind.ExprInfo;
import com.moxa.dream.antlr.bind.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;

public class QueryExpr extends SqlExpr {
    private QueryStatement queryStatement = new QueryStatement();


    public QueryExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.SELECT);
    }

    @Override
    protected Statement exprSelect(ExprInfo exprInfo) {
        SelectExpr selectExpr = new SelectExpr(exprReader);
        Statement statement = selectExpr.expr();
        queryStatement.setSelectStatement((SelectStatement) statement);
        this.setExprTypes(ExprType.FROM, ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFrom(ExprInfo exprInfo) {
        FromExpr fromExpr = new FromExpr(exprReader);
        Statement statement = fromExpr.expr();
        queryStatement.setFromStatement((FromStatement) statement);
        setExprTypes(ExprType.WHERE, ExprType.GROUP, ExprType.ORDER, ExprType.LIMIT, ExprType.OFFSET, ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprWhere(ExprInfo exprInfo) {
        WhereExpr whereExpr = new WhereExpr(exprReader);
        queryStatement.setWhereStatement((WhereStatement) whereExpr.expr());
        setExprTypes(ExprType.GROUP, ExprType.ORDER, ExprType.LIMIT, ExprType.OFFSET, ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprGroup(ExprInfo exprInfo) {
        GroupExpr groupExpr = new GroupExpr(exprReader);
        queryStatement.setGroupStatement((GroupStatement) groupExpr.expr());
        setExprTypes(ExprType.HAVING, ExprType.ORDER, ExprType.LIMIT, ExprType.OFFSET, ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprHaving(ExprInfo exprInfo) {
        HavingExpr havingExpr = new HavingExpr(exprReader);
        queryStatement.setHavingStatement((HavingStatement) havingExpr.expr());
        setExprTypes(ExprType.ORDER, ExprType.LIMIT, ExprType.OFFSET, ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprOrder(ExprInfo exprInfo) {
        OrderExpr orderExpr = new OrderExpr(exprReader);
        queryStatement.setOrderStatement((OrderStatement) orderExpr.expr());
        setExprTypes(ExprType.LIMIT, ExprType.OFFSET, ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprOffSet(ExprInfo exprInfo) {
        return exprPage(exprInfo);
    }

    @Override
    protected Statement exprLimit(ExprInfo exprInfo) {
        return exprPage(exprInfo);
    }

    private Statement exprPage(ExprInfo exprInfo) {
        LimitExpr limitExpr = new LimitExpr(exprReader);
        queryStatement.setLimitStatement((LimitStatement) limitExpr.expr());
        setExprTypes(ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprUnion(ExprInfo exprInfo) {
        UnionExpr unionExpr = new UnionExpr(exprReader);
        UnionStatement unionStatement = (UnionStatement) unionExpr.expr();
        queryStatement.setUnionStatement(unionStatement);
        setExprTypes(ExprType.UNION, ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return queryStatement;
    }

    public static class SelectExpr extends SqlExpr {
        private SelectStatement selectStatement = new SelectStatement();

        public SelectExpr(ExprReader exprReader) {
            super(exprReader);
            setExprTypes(ExprType.SELECT);
        }

        @Override
        protected Statement exprSelect(ExprInfo exprInfo) {

            PreSelectExpr preSelectExpr = new PreSelectExpr(exprReader);
            preSelectExpr.setExprTypes(ExprType.SELECT);
            PreSelectStatement preSelect = (PreSelectStatement) preSelectExpr.expr();
            selectStatement.setPreSelect(preSelect);

            ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader,
                    () -> new AliasColumnExpr(exprReader),
                    new ExprInfo(ExprType.COMMA, ","));
            ListColumnStatement listColumnStatement = (ListColumnStatement) listColumnExpr.expr();
            selectStatement.setSelectList(listColumnStatement);
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        public Statement nil() {
            return selectStatement;
        }

        public static class PreSelectExpr extends SqlExpr {
            private PreSelectStatement preSelectStatement = new PreSelectStatement();

            public PreSelectExpr(ExprReader exprReader) {
                super(exprReader);
                setExprTypes(ExprType.SELECT);
            }

            @Override
            protected Statement exprSelect(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.DISTINCT, ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDistinct(ExprInfo exprInfo) {
                preSelectStatement.setDistinct(true);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            public Statement nil() {
                return preSelectStatement;
            }
        }
    }

    public static class FromExpr extends SqlExpr {
        private FromStatement fromStatement = new FromStatement();

        public FromExpr(ExprReader exprReader) {
            super(exprReader);
            setExprTypes(ExprType.FROM);
        }

        @Override
        protected Statement exprFrom(ExprInfo exprInfo) {
            push();
            AliasColumnExpr aliasColumnExpr = new AliasColumnExpr(exprReader,
                    () -> {
                        ColumnExpr columnExpr = new ColumnExpr(exprReader);
                        columnExpr.setExprTypes(ExprType.LETTER, ExprType.LBRACE, ExprType.INVOKER);
                        return columnExpr;
                    });
            aliasColumnExpr.setExprTypes(ExprType.HELP);
            fromStatement.setMainTable(aliasColumnExpr.expr());
            ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, () -> new JoinExpr(exprReader), new ExprInfo(ExprType.BLANK, " "));
            listColumnExpr.addExprTypes(ExprType.NIL);
            ListColumnStatement listColumnStatement = (ListColumnStatement) listColumnExpr.expr();
            if (listColumnStatement.getColumnList().length > 0)
                fromStatement.setJoinList(listColumnStatement);
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        public Statement nil() {
            return fromStatement;
        }

        public static class JoinExpr extends SqlExpr {
            private JoinStatement joinStatement;
            private ExprType[] ON = new ExprType[]{ExprType.ON};

            public JoinExpr(ExprReader exprReader) {
                super(exprReader);
                setExprTypes(ExprType.COMMA, ExprType.LEFT, ExprType.RIGHT, ExprType.CROSS, ExprType.INNER, ExprType.NIL);
            }

            @Override
            protected Statement exprComma(ExprInfo exprInfo) {
                push();
                joinStatement = new JoinStatement.CommaJoinStatement();
                AliasColumnExpr aliasColumnExpr = new AliasColumnExpr(exprReader,
                        () -> {
                            ColumnExpr columnExpr = new ColumnExpr(exprReader);
                            columnExpr.setExprTypes(ExprType.LETTER, ExprType.LBRACE);
                            return columnExpr;
                        });
                aliasColumnExpr.setExprTypes(ExprType.HELP);
                joinStatement.setJoinTable(aliasColumnExpr.expr());
                ON = new ExprType[]{ExprType.NIL};
                setExprTypes(ON);
                return expr();
            }

            @Override
            protected Statement exprCross(ExprInfo exprInfo) {
                push();
                joinStatement = new JoinStatement.CrossJoinStatement();
                ON = new ExprType[]{ExprType.ON, ExprType.NIL};
                setExprTypes(ExprType.OUTER, ExprType.JOIN);
                return expr();
            }

            @Override
            protected Statement exprLeft(ExprInfo exprInfo) {
                push();
                joinStatement = new JoinStatement.LeftJoinStatement();
                setExprTypes(ExprType.OUTER, ExprType.JOIN);
                return expr();
            }

            @Override
            protected Statement exprRight(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.OUTER, ExprType.JOIN);
                return expr();
            }

            @Override
            protected Statement exprOuter(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.JOIN);
                return expr();
            }

            @Override
            protected Statement exprInner(ExprInfo exprInfo) {
                push();
                joinStatement = new JoinStatement.InnerJoinStatement();
                setExprTypes(ExprType.OUTER, ExprType.JOIN);
                return expr();
            }

            @Override
            protected Statement exprJoin(ExprInfo exprInfo) {
                push();
                AliasColumnExpr aliasColumnExpr = new AliasColumnExpr(exprReader,
                        () -> {
                            ColumnExpr columnExpr = new ColumnExpr(exprReader);
                            columnExpr.setExprTypes(ExprType.LETTER, ExprType.LBRACE);
                            return columnExpr;
                        });
                aliasColumnExpr.setExprTypes(ExprType.HELP);
                joinStatement.setJoinTable(aliasColumnExpr.expr());
                setExprTypes(ON);
                return expr();
            }

            @Override
            protected Statement exprOn(ExprInfo exprInfo) {
                push();
                CompareExpr operTreeExpr = new CompareExpr(exprReader);
                Statement statement = operTreeExpr.expr();
                joinStatement.setOn(statement);
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            public Statement nil() {
                return joinStatement;
            }
        }

    }

    public static class WhereExpr extends SqlExpr {
        private WhereStatement whereStatement = new WhereStatement();

        public WhereExpr(ExprReader exprReader) {
            super(exprReader);
            setExprTypes(ExprType.WHERE);
        }

        @Override
        protected Statement exprWhere(ExprInfo exprInfo) {
            push();
            CompareExpr operTreeExpr = new CompareExpr(exprReader);
            Statement statement = operTreeExpr.expr();
            whereStatement.setCondition(statement);
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        public Statement nil() {
            return whereStatement;
        }
    }

    public static class GroupExpr extends SqlExpr {
        private GroupStatement groupStatement = new GroupStatement();

        public GroupExpr(ExprReader exprReader) {
            super(exprReader);
            setExprTypes(ExprType.GROUP);
        }

        @Override
        protected Statement exprGroup(ExprInfo exprInfo) {
            push();
            setExprTypes(ExprType.BY);
            return expr();
        }

        @Override
        protected Statement exprBy(ExprInfo exprInfo) {
            push();
            ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, new ExprInfo(ExprType.COMMA, ","));

            groupStatement.setGroup(listColumnExpr.expr());
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        public Statement nil() {
            return groupStatement;
        }
    }

    public static class HavingExpr extends SqlExpr {
        private HavingStatement havingStatement = new HavingStatement();

        public HavingExpr(ExprReader exprReader) {
            super(exprReader);
            setExprTypes(ExprType.HAVING);
        }

        @Override
        protected Statement exprHaving(ExprInfo exprInfo) {
            push();
            CompareExpr operTreeExpr = new CompareExpr(exprReader);
            havingStatement.setCondition(operTreeExpr.expr());
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        public Statement nil() {
            return havingStatement;
        }
    }

    public static class OrderExpr extends SqlExpr {
        private OrderStatement orderStatement = new OrderStatement();

        public OrderExpr(ExprReader exprReader) {
            super(exprReader);
            setExprTypes(ExprType.ORDER);
        }

        @Override
        protected Statement exprOrder(ExprInfo exprInfo) {
            push();
            setExprTypes(ExprType.BY);
            return expr();
        }

        @Override
        protected Statement exprBy(ExprInfo exprInfo) {
            push();
            ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, () -> new AscDescExpr(exprReader), new ExprInfo(ExprType.COMMA, ","));
            orderStatement.setOrder(listColumnExpr.expr());
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        public Statement nil() {
            return orderStatement;
        }

        public static class AscDescExpr extends HelperExpr {
            private Statement sortStatement;
            private Statement statement;

            public AscDescExpr(ExprReader exprReader) {
                this(exprReader, () -> new ColumnExpr(exprReader));
            }


            public AscDescExpr(ExprReader exprReader, Helper helper) {
                super(exprReader, helper);
            }

            @Override
            protected Statement exprAsc(ExprInfo exprInfo) {
                push();
                statement = new OrderStatement.AscStatement(sortStatement);
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDesc(ExprInfo exprInfo) {
                push();
                statement = new OrderStatement.DescStatement(sortStatement);
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement nil() {
                if (statement == null)
                    statement = sortStatement;
                return statement;
            }

            @Override
            public Statement exprHelp(Statement statement) {
                this.sortStatement = statement;
                setExprTypes(ExprType.NIL, ExprType.ASC, ExprType.DESC);
                return expr();
            }
        }
    }

    public static class LimitExpr extends HelperExpr {
        private LimitStatement limitStatement = new LimitStatement();

        public LimitExpr(ExprReader exprReader) {
            this(exprReader, () -> new CompareExpr(exprReader));
        }

        public LimitExpr(ExprReader exprReader, Helper helper) {
            super(exprReader, helper);
            setExprTypes(ExprType.LIMIT, ExprType.OFFSET);
        }


        @Override
        protected Statement exprLimit(ExprInfo exprInfo) {
            push();
            setExprTypes(ExprType.HELP);
            return expr();
        }

        @Override
        protected Statement exprComma(ExprInfo exprInfo) {
            push();
            setExprTypes(ExprType.HELP);
            return expr();
        }


        @Override
        public Statement nil() {
            return limitStatement;
        }

        @Override
        protected Statement exprOffSet(ExprInfo exprInfo) {
            push();
            limitStatement.setOffset(true);
            Statement first;
            if ((first = limitStatement.getFirst()) != null) {
                limitStatement.setSecond(first);
                limitStatement.setFirst(null);
            }
            setExprTypes(ExprType.HELP);
            return expr();
        }

        @Override
        public Statement exprHelp(Statement statement) {
            if (limitStatement.isOffset()) {
                if (limitStatement.getFirst() == null) {
                    limitStatement.setFirst(statement);
                    setExprTypes(ExprType.LIMIT, ExprType.NIL);
                } else {
                    limitStatement.setSecond(statement);
                    setExprTypes(ExprType.NIL);
                }
            } else {
                if (limitStatement.getFirst() == null) {
                    limitStatement.setFirst(statement);
                    setExprTypes(ExprType.OFFSET, ExprType.COMMA, ExprType.NIL);
                } else {
                    limitStatement.setSecond(statement);
                    setExprTypes(ExprType.NIL);
                }
            }
            return expr();
        }
    }

    public static class UnionExpr extends HelperExpr {
        private UnionStatement unionStatement = new UnionStatement();

        public UnionExpr(ExprReader exprReader) {
            this(exprReader, () -> new QueryExpr(exprReader));
        }

        public UnionExpr(ExprReader exprReader, Helper helper) {
            super(exprReader, helper);
            setExprTypes(ExprType.UNION);
        }

        @Override
        protected Statement exprUnion(ExprInfo exprInfo) {
            push();
            setExprTypes(ExprType.LBRACE, ExprType.ALL, ExprType.HELP);
            return expr();
        }

        @Override
        protected Statement exprAll(ExprInfo exprInfo) {
            push();
            unionStatement.setAll(true);
            setExprTypes(ExprType.LBRACE, ExprType.HELP);
            return expr();
        }

        @Override
        protected Statement exprLBrace(ExprInfo exprInfo) {
            push();
            setExprTypes(ExprType.HELP);
            return expr();
        }

        @Override
        public Statement exprHelp(Statement statement) {
            unionStatement.setStatement(statement);
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        public Statement nil() {
            return unionStatement;
        }
    }

}
