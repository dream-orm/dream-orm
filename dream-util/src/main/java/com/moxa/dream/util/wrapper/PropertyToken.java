package com.moxa.dream.util.wrapper;

public class PropertyToken {
    private String name;
    private String children;
    private String ref;
    private String nextName;

    public PropertyToken(String token) {
        this(token, "");
    }

    public PropertyToken(String token, String ref) {
        this.ref = ref;
        parser(token);
    }

    public void parser(String fullname) {
        if (fullname == null)
            return;
        int delim = fullname.indexOf('.');
        if (delim > -1) {
            name = fullname.substring(0, delim);
            children = fullname.substring(delim + 1);
            nextName = name + ".";
        } else {
            name = fullname;
            children = null;
            nextName = fullname;
        }
        int index;
        if ((index = name.indexOf("[")) > 0 && name.endsWith("]")) {
            children = fullname.substring(index);
            name = fullname.substring(0, index);
            nextName = name;
        }
    }

    public boolean hasNext() {
        return children != null;
    }

    public PropertyToken next() {
        return new PropertyToken(children, ref + nextName);
    }

    public String getName() {
        return name;
    }

    public String getRef() {
        if (ref.endsWith("."))
            return ref.substring(0, ref.length() - 1);
        else
            return ref;
    }
}