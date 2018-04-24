%{
	import java.util.List;
	import java.util.StringTokenizer;
	import java.util.ArrayList;
%}
%token  ATTR 
%token  NUM

%left OR
%left AND
%token OF

%%

result: policy { res = (Policy)$1.obj; }

policy:   ATTR                       { $$.obj = leaf_policy($1.sval);        }
        | policy OR  policy          { $$.obj = kof2_policy(1, (Policy)$1.obj, (Policy)$3.obj); }
        | policy AND policy          { $$.obj = kof2_policy(2, (Policy)$1.obj, (Policy)$3.obj); }
        | NUM OF '(' arg_list ')'    { $$.obj = kof_policy($1.ival, (List<Policy>)$4.obj);     }
        | '(' policy ')'             { $$ = $2;                     }

arg_list: policy                     { $$.obj = new ArrayList<Policy>();
                                       ((List<Policy>)$$.obj).add((Policy)$1.obj); }
        | arg_list ',' policy        { $$ = $1;
                                       ((List<Policy>)$$.obj).add((Policy)$3.obj); }
;

%%
private Policy res;
StringTokenizer st;

public Policy parse(String input){
	input = input.replaceAll("\n", "");
	this.st = new StringTokenizer(input, " \t\r\f");
	yyparse();
	return this.res;
}

private int yylex(){
	String s;
	int tok;
	if(!st.hasMoreTokens()){
		return 0;
	}
	s = st.nextToken();
	if(s.equals("(") || s.equals(")")){
		tok = s.charAt(0);
		yylval = new ParserVal(s);
	}
	else if(s.equals("&") || s.toLowerCase().equals("and")){
		tok = AND;
		yylval = new ParserVal(s);
	}
	else if(s.equals("|") || s.toLowerCase().equals("or")){
		tok = OR;
		yylval = new ParserVal(s);
	}
	else if(s.toLowerCase().equals("of")){
		tok = OF;
		yylval = new ParserVal(s);
	}
	else {
		boolean isNum = true;
		for( char c : s.toCharArray()){
			if(!Character.isDigit(c)){
				isNum = false;
				break;
			}
		}
		if(isNum){
			tok = NUM;
			yylval = new ParserVal(Integer.parseInt(s));
		}
		else{
			tok = ATTR;
			yylval = new ParserVal(s);
		}
	}

	return tok;
}

public void yyerror(String error){
	System.err.println("Error:" + error);
}


Policy leaf_policy(String attr){
	Policy p = new Policy();
	p.attr = attr;
	p.k = 1;
	return p;
}

Policy kof2_policy(int k, Policy l, Policy r){
	Policy p = new Policy();
	p.k = k;
	p.children = new Policy[2];
	p.children[0] = l;
	p.children[1] = r;
	return p;
}

Policy kof_policy(int k, List<Policy> list){
	Policy p = new Policy();
	p.k = k;
	p.children = new Policy[list.size()];
	list.toArray(p.children);
	return p;
}
