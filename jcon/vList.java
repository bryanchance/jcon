//  vList -- an Icon list
//
//  java.util.vectors are used to implement lists.
//  The *end* of the vector is the *front* of a list,
//  so that push() and pop() are relatively quick.
//  (put and pull will be slow for long lists, at least with JDK 1.0.2).

import java.util.*;

class vList extends vValue {

    Vector v;



vList(int n, vValue x)  {			// new Vlist(n, x)
    v = new Vector(n);
    for (int i = 0; i < n; i++) {
    	v.addElement(iNew.SimpleVar(x));
    }
}

vList(vDescriptor[] elements) {			// new Vlist(elements[])
    v = new Vector(elements.length);
    for (int i = elements.length - 1; i >= 0; i--) {	// add back-to-front
    	v.addElement(iNew.SimpleVar(elements[i]));
    }
}



// runtime primitives

String image()		{ return "list(" + v.size() + ")"; }	//#%#% add s/n

String report()		{ return this.image(); } //#%#% redo with elem details

String type()		{ return "list"; }




//  L.posEq(n) -- return positive equivalent of position n in list L,
//		  or zero if out of bounds

int posEq(long n)
{
    long len = v.size();
    if (n <= 0) {
    	n += len + 1;
    }
    if (n > 0 && n <= len + 1) {
    	return (int)n;
    } else {
    	return 0;
    }
}



//  operations



vInteger Size()	{					//  *L
    return iNew.Integer(v.size());
}



vValue Push(vDescriptor x) {				// push(L, x)
    v.addElement(iNew.SimpleVar(x));
    return this;
}

vValue Pull() {						// pull(L)
    if (v.size() == 0) {
    	return null; /*FAIL*/
    }
    vDescriptor x = (vDescriptor) v.firstElement();
    v.removeElementAt(0);
    return x.deref();
}

vValue Pop() {						// pop(L)
    if (v.size() == 0) {
    	return null; /*FAIL*/
    }
    vDescriptor x = (vDescriptor) v.lastElement();
    v.removeElementAt(v.size()-1);
    return x.deref();
}

vValue Get() {						// get(L)
    if (v.size() == 0) {
    	return null; /*FAIL*/
    }
    vDescriptor x = (vDescriptor) v.lastElement();
    v.removeElementAt(v.size()-1);
    return x.deref();
}

vValue Put(vDescriptor x) {				// put(L, x)
    v.insertElementAt(iNew.SimpleVar(x), 0);
    return this;
}



vDescriptor Index(vValue i) {				//  L[i]
    int m = this.posEq(i.mkInteger().value);
    if (m == 0 || m > v.size()) {
    	return null; /*FAIL*/
    }
    // index from BACK end of vector
    return (vDescriptor) v.elementAt(v.size() - m);	// return as variable
}



vDescriptor Section(vValue i, vValue j) {		//  L[i:j]
    int m = this.posEq(i.mkInteger().value);
    int n = this.posEq(j.mkInteger().value);
    if (m == 0 || n == 0) {
    	return null; /*FAIL*/
    }
    if (m > n) {
    	int t = m;
	m = n;
	n = t;
    }
    vDescriptor a[] = new vDescriptor[n-m];
    for (int k = 0; k < a.length; k++) {
    	a[k] = (vDescriptor) v.elementAt(v.size() - (k + m));
    }
    return iNew.List(a);
}



vDescriptor Select() {					//  ?L
    if (v.size() == 0) {
	return null; /*FAIL*/
    }
    return (vDescriptor) v.elementAt((int)iRuntime.random(v.size()));
    							// return as variable
}



vDescriptor Bang(iClosure c) {				//  !L
    int i;
    if (c.o == null) {
	c.o = new Integer(i = 1);
    } else {
	c.o = new Integer(i = ((Integer)c.o).intValue() + 1);
    }
    if (i > v.size()) {
	return null; /*FAIL*/
    } else {
	// indexing runs from BACK end of vector
	return (vDescriptor) v.elementAt(v.size() - i);	// generate as variable
    }
}



} // class vList