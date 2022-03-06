package com.moxa.dream.util.wrapper;

public class PropertyToken {
    private String name;
    private String children;

    public PropertyToken(String token) {
        parser(token);
    }

    public void parser(String fullname) {
        if (fullname == null)
            return;
        int delim = fullname.indexOf('.');
        if (delim > -1) {
            name = fullname.substring(0, delim);
            children = fullname.substring(delim + 1);
        } else {
            name = fullname;
            children = null;
        }
        int index;
        if ((index = name.indexOf("[")) > 0 && name.endsWith("]")) {
            children = fullname.substring(index);
            name = fullname.substring(0, index);
        }
    }

    public boolean hasNext() {
        return children != null;
    }

    public PropertyToken next() {
        return new PropertyToken(children);
    }

    public String getName() {
        return name;
    }

}