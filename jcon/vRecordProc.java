package rts;



public class vRecordProc extends vValue {
    vString name;		// name of record Type
    vString img;		// image: record constructor <name>
    String[] fieldnames;	// names of fields
    String[] varnames;		// variable names
    int nextsn = 1;		// next serial number
    iRecordClosure cachedclosure;



// constructors

public static vRecordProc New(String name, String[] fields) {
    return new vRecordProc(name, fields);
}

private vRecordProc(String name, String[] fieldnames) {
    this.name = vString.New(name);
    this.img = this.name.surround("record constructor ", "");
    this.fieldnames = fieldnames;
    this.varnames = new String[fieldnames.length];
    for (int i = 0; i < varnames.length; i++) {
	varnames[i] = name + "." + fieldnames[i];
    }
}




public iClosure instantiate(vDescriptor[] args, iClosure parent) {
    if (cachedclosure == null) {
	cachedclosure = new iRecordClosure();
    }
    cachedclosure.init(this, args, parent);
    return cachedclosure;
}

vValue getproc()	{ return this; }

vString image()		{ return img; }

static vString typestring = vString.New("procedure");
public vString Type()	{ return typestring; }

int rank()		{ return 80; }	// record constructors sort with procs
int compareTo(vValue v) {
    return vProc.compareLastWord(name, v.image());
}

public vInteger Args()	{ return vInteger.New(fieldnames.length); }

int find(String s) {
    for (int i = 0; i < fieldnames.length; i++) {
	if (s == fieldnames[i]) {
	    return i;
	}
    }
    // The following exists for indexing records with constructed
    // names.  It is the rare case.
    for (int i = 0; i < fieldnames.length; i++) {
	if (s.equals(fieldnames[i])) {
	    return i;
	}
    }
    return -1;
}



} // class vRecordProc



class iRecordClosure extends iValueClosure {

    vRecordProc constr;

void init(vRecordProc constr, vDescriptor[] args, iClosure parent) {
    init();
    this.constr = constr;
    this.parent = parent;
    arguments = args;
}

vDescriptor function(vDescriptor[] args) {
    return new vRecord(constr, args);
}

} // class iRecordClosure
