//  vVarExpr -- variable expression
//
//  A VarExpr represents an assignable expression such as a substring,
//  table element, or indexed list entry.  Every VarExpr points to 
//  an underlying SimpleVar.

abstract class vVarExpr extends vVariable {

    vVariable var;

    vVarExpr(vVariable v) {		// constructor
    	var = v;
    }

}
