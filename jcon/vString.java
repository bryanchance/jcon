//  vString.java -- java Strings
//
//  This class implements an alternative to java.lang.String that is
//  specifically geared to Icon's needs:
//	-- constituent characters are just eight bits wide (Java bytes)
//	-- operations are not synchronized
//	-- concatenation is done lazily



package rts;

public class vString extends vValue {

    private int tlength;	// total string length
    private vString prefix;	// first part of string (optional)
    private byte[] data;	// character array



//  constructors

vString() {					// new vString()
    data = new byte[0];
}

vString(char c) {				// new vString(char)
    tlength = 1;
    data = new byte[1];
    data[0] = (byte)c;
}

vString(byte[] b) {				// new vString(b[])
						// embeds b (does not copy it)
    data = b;
    tlength = data.length;
}

vString(vString s, byte b[]) {			// new vString := s || b[]
						// embeds b (does not copy it)
    tlength = s.tlength + b.length;
    prefix = s;
    data = b;
}

vString(vString s, int i, int j) {		// new vString := .s[i:j]
    s.flatten();
    tlength = j - i;
    data = new byte[tlength];
    System.arraycopy(s.data, i - 1, data, 0, tlength);
}

vString(vString s, int i, int j, vString t) {	// new vString := (s[i:j] := t)

    s.flatten();
    t.flatten();

    tlength = s.tlength - (j - i) + t.tlength;
    data = new byte[tlength];
    int n = s.data.length - j + 1;

    System.arraycopy(s.data, 0, data, 0, i - 1);
    System.arraycopy(t.data, 0, data, i - 1, t.data.length);
    System.arraycopy(s.data, s.data.length - n, data, tlength - n, n);
}



//  special vString primitives

final int length() {			// s.length()
    return tlength;
}

final char charAt(int i) {		// s.charAt(i)   [zero-based]
    this.flatten();
    return (char)((short)data[i] & 0xFF);
}

final boolean matches(vString s, int i){ // match(s, this, i)  [zero-based]
    if (this.tlength > s.tlength - i) {
	return false;
    }
    this.flatten();
    s.flatten();
    byte[] d = s.data;
    for (int j = 0; j < this.tlength; j++) {
	if (this.data[j] != d[i+j]) {
	    return false;
	}
    }
    return true;
}

final boolean identical(vString s) {	// like equals, but assumes vString
    return this.tlength == s.tlength && this.matches(s, 0);
}

final vString concat(vString s) {	// s.concat(s)
    if (tlength == 0) {
	return s;
    }
    if (s.tlength == 0) {
	return this;
    }
    s.flatten();
    return new vString(this, s.data);
}

public final vString surround(String js1, String js2) {	// s.surround(js1, js2)
    this.flatten();
    int len1 = js1.length();
    int len2 = js2.length();
    byte[] b = new byte[len1 + tlength + len2];
    for (int i = 0; i < len1; i++) {
	b[i] = (byte) js1.charAt(i);
    }
    System.arraycopy(data, 0, b, len1, tlength);
    int o = len1 + tlength;
    for (int i = 0; i < len2; i++) {
	b[o + i] = (byte) js2.charAt(i);
    }
    return iNew.String(b);
}

public final byte[] getBytes() {	// s.getBytes() rtns data (NOT a copy)
    this.flatten();
    return data;
}

public final String toString() {	// s.toString()
    this.flatten();
    return new String(data);
}

final vInteger toInteger() {		// s.toInteger -- no radix or exponent

    this.flatten();

    int i = 0;
    long v;
    boolean neg = false;
    byte c;

    while (i < tlength && Character.isWhitespace((char)data[i])) {
	i++;
    }

    if (i < tlength) {
	c = data[i];
	if (c == '-') {
	    neg = true;
	    i++;
	} else if (c == '+') {
	    i++;
	}
    }

    if (i < tlength && Character.isDigit((char)(c = data[i]))) {
	v = c - '0';
	i++;
    } else {
	return null;	// failed (here; should try general converter)
    }

    while (i < tlength && Character.isDigit((char)(c = data[i]))) {
	v = 10 * v + c - '0';		//#%#% ignores overflow
	i++;
	if (v > Long.MAX_VALUE / 10) {
	    break;
	}
    }

    while (i < tlength && Character.isWhitespace((char)data[i])) {
	i++;
    }
    if (i < tlength) {
	return null;
    } else if (neg) {
	return iNew.Integer(-v);
    } else {
	return iNew.Integer(v);
    }
}



//  internal method to collapse a tree of lazy concatenations
//  (broken into two parts so that the first part gets in-lined where called)

private void flatten() {
    if (this.prefix != null) {
	this.flatten1();
    }
}

private void flatten1() {
    byte[] d = new byte[tlength];
    vString s = this;
    int i = tlength;
    while (i > 0) {
	int j = s.data.length;
	while (j > 0) {
	   d[--i] = s.data[--j];	//#%#% is arraycopy faster? or slower?
	}
	s = s.prefix;
    }
    data = d;
    prefix = null;
}



//  general vDescriptor primitives

vString mkString()	{ return this; }	// no-op coversion to vString

public int hashCode() {	 // hashcode, consistent whether flattened or not
    vString s = this;
    int n = 0;
    while (s != null) {
	for (int i = s.data.length; i > 0; ) {
	   n = 37 * n + s.data[--i];
	}
	s = s.prefix;
    }
    return n;
}

public boolean equals(Object o)	{
    if (o == null || !(o instanceof vString))
	return false;
    vString s = (vString) o;
    return this.tlength == s.tlength && this.matches(s, 0);
}

vString write()		{ return this; }

static vString typestring = iNew.String("string");
vString type()		{ return typestring; }
int rank()		{ return 30; }		// strings rank after reals

vString image()		{ return image(tlength); }
vString report()	{ return image(16); }	 // limit to max of 16 chars


vString image(int maxlen) {		// make image, up to maxlen chars
    this.flatten();
    vByteBuffer b = new vByteBuffer(maxlen + 5);	// optimistic guess
    b.append('"');
    int i;
    for (i = 0; i < maxlen && i < tlength; i++) {
	char c = (char) data[i];
	if (c == '"') {
	    b.append('\\');
	    b.append('\"');
	} else {
	    appendEscaped(b, c);
        }
    }
    if (i < tlength) {
	b.append('.');
	b.append('.');
	b.append('.');
    }
    b.append('"');
    return b.mkString();
}


int compareTo(vValue v) {		// compare strings lexicographically
    vString s = (vString) v;
    this.flatten();
    s.flatten();
    int i;
    for (i = 0; i < this.tlength && i < s.tlength; i++) {
	int d = this.data[i] - s.data[i];
	if (d != 0) {
	    return d;
	}
    }
    return this.tlength - s.tlength;
}



vNumeric mkNumeric()	{

    // first try straightforward conversion to integer
    vInteger v = this.toInteger();
    if (v != null) {
	return v;
    }

    // nope, go the long way.  #%#% this could be improved.

    String s = this.toString().trim(); //#%#% too liberal: trims not just spaces

    if (s.length() > 0 && s.charAt(0) == '+') {	// allow leading +, by trimming 
	s = s.substring(1);
    }

    try {
	return iNew.Integer(Long.parseLong(s));
    } catch (NumberFormatException e) {
    }

    try {
	Double d = Double.valueOf(s);
	if (!d.isInfinite()) {
	    return iNew.Real(d.doubleValue());
	}
    } catch (NumberFormatException e) {
    }

    v = vInteger.radixParse(s);			// try to parse as radix value
    if (v != null) {
	return v;
    }

    iRuntime.error(102, this);
    return null;
}

vInteger mkInteger()	{
    try {
	return this.mkNumeric().mkInteger();	// allows integer("3e6")
    } catch (iError e) {
    	iRuntime.error(101, this);
	return null;
    }
}

vReal mkReal()		{
    return this.mkNumeric().mkReal();
}



vCset mkCset() {
    return iNew.Cset(this);
}


//  static methods for argument processing and defaulting

static vString argDescr(vDescriptor[] args, int index)		// required arg
{
    if (index >= args.length) {
	iRuntime.error(103);
	return null;
    } else {
	return args[index].mkString();
    }
}

static vString argDescr(vDescriptor[] args, int index, vString dflt) // opt arg
{
    if (index >= args.length || args[index] instanceof vNull) {
	return dflt;
    } else {
	return args[index].mkString();
    }
}



//  append escaped char to StringBuffer; also used for csets

private static char[] ecodes = { 'b', 't', 'n', 'v', 'f', 'r' };
private static char[] xcodes =
    { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };

static void appendEscaped(vByteBuffer b, char c)
{
    if (c >= ' ' && c <= '~') {		// printable range
	if (c == '\\') {
	    b.append('\\');
	}
	b.append(c);
    } else if (c >= 0x08 && c <= 0x0D) {
	b.append('\\');
	b.append(ecodes[c - 0x08]);	//  \b \t \n \v \f \r
    } else if (c == 0x1B) {
	b.append('\\');
	b.append('e');		//  \e
    } else if (c == 0x7F) {
	b.append('\\');
	b.append('d');		//  \d
    } else {
	b.append('\\');
	b.append('x');		//  \xnn
	b.append(xcodes[(c >> 4) & 0xF]);
	b.append(xcodes[c & 0xF]);
    }
}



//  s.posEq(n) -- return positive equivalent of position n in string s,
//		  or zero if out of bounds

int posEq(long n)
{
    if (n <= 0) {
    	n += tlength + 1;
    }
    if (n > 0 && n <= tlength + 1) {
    	return (int)n;
    } else {
    	return 0;
    }
}



//  operations



vInteger Size()	{
    return iNew.Integer(tlength);
}



vValue Concat(vDescriptor v) {
    return this.concat(v.mkString());
}



vDescriptor Index(vValue i) {
    int m = this.posEq(i.mkInteger().value);
    if (m == 0 || m > tlength) {
    	return null; /*FAIL*/
    }
    return iNew.String(this, m, m + 1);
}

vDescriptor IndexVar(vVariable v, vValue i) {
    int m = this.posEq(i.mkInteger().value);
    if (m == 0 || m > tlength) {
    	return null; /*FAIL*/
    }
    return iNew.Substring(v, m, m + 1);
}

vDescriptor Section(vValue i, vValue j) {
    int m = this.posEq(i.mkInteger().value);
    int n = this.posEq(j.mkInteger().value);
    if (m == 0 || n == 0) {
    	return null; /*FAIL*/
    }
    if (m > n) {
	return iNew.String(this, n, m);
    } else {
	return iNew.String(this, m, n);
    }
}

vDescriptor SectionVar(vVariable v, vValue i, vValue j) {
    int m = this.posEq(i.mkInteger().value);
    int n = this.posEq(j.mkInteger().value);
    if (m == 0 || n == 0) {
    	return null; /*FAIL*/
    }
    if (m > n) {
	return iNew.Substring(v, n, m);
    } else {
	return iNew.Substring(v, m, n);
    }
}



vDescriptor Select() {
    if (tlength == 0) {
	return null; /*FAIL*/
    }
    int i = (int) k$random.choose(tlength);
    return iNew.String(charAt(i));
}

vDescriptor SelectVar(vVariable v) {
    if (tlength == 0) {
	return null; /*FAIL*/
    }
    int i = (int) k$random.choose(tlength);
    return iNew.Substring(v, i+1, i+2);
}

vDescriptor Bang(iClosure c) {
    if (c.PC == 1) {
	c.o = c;
	c.oint = 0;
	c.PC = 2;
    } else {
	c.oint++;
    }
    if (c.oint >= tlength) {
	return null; /*FAIL*/
    } else {
	return iNew.String(charAt(c.oint));
    }
}

vDescriptor BangVar(iClosure c, vVariable v) {
    int i;
    if (c.PC == 1) {
	c.o = new Integer(i = 1);	//#%#%#% use c.oint instead
	c.PC = 2;
    } else {
	c.o = new Integer(i = ((Integer)c.o).intValue() + 1);
    }
    if (i > ((vString)v.deref()).tlength) {
	return null; /*FAIL*/
    } else {
	return iNew.Substring(v, i, i+1);
    }
}

vValue LLess(vDescriptor v) {
    return this.compareTo((vString)v) < 0 ? (vValue)v : null;
}
vValue LLessEq(vDescriptor v) {
    return this.compareTo((vString)v) <= 0 ? (vValue)v : null;
}
vValue LEqual(vDescriptor v) {
    return this.compareTo((vString)v) == 0 ? (vValue)v : null;
}
vValue LUnequal(vDescriptor v) {
    return this.compareTo((vString)v) != 0 ? (vValue)v : null;
}
vValue LGreaterEq(vDescriptor v) {
    return this.compareTo((vString)v) >= 0 ? (vValue)v : null;
}
vValue LGreater(vDescriptor v) {
    return this.compareTo((vString)v) > 0 ? (vValue)v : null;
}

vValue Complement()		{ return this.mkCset().Complement(); }
vValue Intersect(vDescriptor x)	{ return this.mkCset().Intersect(x); }
vValue Union(vDescriptor x)	{ return this.mkCset().Union(x); }
vValue Diff(vDescriptor x)	{ return this.mkCset().Diff(x); }


vValue Proc(long i) {
//#%#%#%# look at this again.  too many conversions.
    if (i == 0) {
	vValue b = (vValue) iEnv.builtintab.get(this.toString());
	if (b == null) {
	    return null;
	}
	return b;
    }
    vDescriptor v = (vDescriptor) iEnv.symtab.get(this.toString());
    if (v != null) {
	return v.deref().getproc();
    }
    v = (vDescriptor) iEnv.builtintab.get(this.toString());
    if (v != null) {
	return v.deref();
    }
    try {
	return this.mkInteger().getproc();
    } catch (iError e) {
	// ignore
    }
    if (i < 1 || i > 3) {
	return null;
    }
    v = (vDescriptor) iEnv.proctab[(int)i-1].get(this.toString());
    if (v != null) {
	return (vValue) v;
    }
    return null;
}

} // class vString
