//  wAttrib.java -- code dealing with window attributes

package rts;

import java.awt.*;
import java.util.*;



abstract class wAttrib implements Cloneable {
    
    String name;			// attribute name
    String val;				// value; null if none specified

abstract vValue set(vWindow win);	// set window according to s
abstract vValue get(vWindow win);	// get current value, set s, return val



//  initialize known attributes

private static Hashtable attlist = new Hashtable();

static {
    newatt("bg", new aBg());
    newatt("fg", new aFg());
    newatt("font", new aFont());
    newatt("width", new aWidth());
    newatt("height", new aHeight());
    newatt("size", new aSize());
}

private static void newatt(String name, wAttrib a) {
    a.name = name;
    attlist.put(name, a);
}



//  parseAtts(args, n) -- parse attribute arglist beginning at position n
//
//  Returns a list of wAttrib objects, each of proper type and with
//  the "name" field set.  The "val" field is set, always to a vString,
//  if "=value" is found in an argument.  (Note that val==null is
//  different from val=="").
//
//  Errors are possible:  103 (string expected), 145 (bad name)

static wAttrib[] parseAtts(vDescriptor[] args, int n) {

    wAttrib list[] = new wAttrib[args.length - n];

    for (int i = n; i < args.length; i++) {

	String s = vString.argVal(args, i);
	String name, val;

	int j = s.indexOf('=');
	if (j < 0) {
	    name = s;
	    val = null;
	    // no value specified
	} else {
	    // value was specified
	    name = s.substring(0, j);
	    val = s.substring(j + 1);
	}

	wAttrib a = (wAttrib) attlist.get(name);
	if (a == null) {
	    iRuntime.error(145, args[i]);
	}
	try {
	    a = (wAttrib) a.clone();	// make new copy and alter that
	} catch (CloneNotSupportedException e)  {
	    iRuntime.bomb(e);
	}
	a.val = val;
	list[i - n] = a;
    }

    return list;
}



} // class wAttrib



class aFg extends wAttrib {

vValue get(vWindow win)	{ return win.Fg(null); }
vValue set(vWindow win)	{ return win.Fg(iNew.String(val)); }

}



class aBg extends wAttrib {

vValue get(vWindow win)	{ return win.Bg(null); }
vValue set(vWindow win)	{ return win.Bg(iNew.String(val)); }

}



class aFont extends wAttrib {

vValue get(vWindow win)	 { return win.Font(null); }
vValue set(vWindow win)	 { return win.Font(iNew.String(val)); }

}



class aWidth extends wAttrib {

vValue get(vWindow win) {
    return iNew.Integer(win.getCanvas().getSize().width);
}

vValue set(vWindow win) {
    if (win.getCanvas().config(win, null, null, val, null)) {
	return get(win);
    } else {
	return null; /*FAIL*/
    }
}

}



class aHeight extends wAttrib {

vValue get(vWindow win) {
    return iNew.Integer(win.getCanvas().getSize().height);
}

vValue set(vWindow win) {
    if (win.getCanvas().config(win, null, null, null, val)) {
	return get(win);
    } else {
	return null; /*FAIL*/
    }
}

}



class aSize extends wAttrib {

vValue get(vWindow win) { 
    Dimension d = win.getCanvas().getSize();
    return iNew.String(d.width + "," + d.height);
}

vValue set(vWindow win) {
    int j = val.indexOf(',');
    if (j < 0) {
	return null;
    }
    String w = val.substring(0, j);
    String h = val.substring(j + 1);
    if (win.getCanvas().config(win, null, null, w, h)) {
	return get(win);
    } else {
	return null; /*FAIL*/
    }
}

}
