package Ex1;

import javax.management.RuntimeErrorException;

public class ComplexFunction implements complex_function {
	private NodeF root = new NodeF(Operation.None);

	private class NodeF{
		Operation op;
		function fun;
		NodeF left;
		NodeF right;
		public NodeF(Operation op) {
			this.op=op;
			this.fun=null;
			this.left=null;
			this.right=null;
		}
		public NodeF(function fun) {
			this.op=Operation.None;
			this.fun=fun;
			this.left=null;
			this.right=null;
		}
		public NodeF(NodeF node) {
			this.op=node.op;
			this.fun=node.fun;
			this.left=node.left;
			this.right=node.right;
		}
	}


	public ComplexFunction(String string, function p1, function p2) {
		if(WhatOp(string)==Operation.Error) {
			throw new RuntimeErrorException(null,"inpot not good");
		}
		this.root.left=new NodeF(p1.copy());
		this.root.right=new NodeF(p2.copy());
		this.root.op=WhatOp(string);
		this.root.fun=null;


	}
	public Operation WhatOp(String s){
		String ls=s.toLowerCase();
		if(ls.equals("plus"))
			return Operation.Plus;
		if(ls.equals("mul"))
			return Operation.Times;
		if(ls.equals("div"))
			return Operation.Divid;
		if(ls.equals("max"))
			return Operation.Max;
		if(ls.equals("min"))
			return Operation.Min;
		if(ls.equals("comp"))
			return Operation.Comp;
		else
			return Operation.Error;
	}

	public ComplexFunction(function p3) {
		this.root= new NodeF(p3.copy());
	}

	private ComplexFunction() {

	}
	@Override
	public double f(double x) {
		return inorder(this.root,x);

	}
	public double inorder(NodeF node, double x) 
	{ 
		if (node == null) 
			return 0;
		if(node.op!=Operation.None) {
			switch(node.op) {
			case Plus:
				return inorder(node.left,x)+inorder(node.right,x); 
			case Times:
				return inorder(node.left,x)*inorder(node.right,x);
			case Divid:
				return inorder(node.left,x)/inorder(node.right,x);
			case Max:
				return Math.max(inorder(node.left,x),inorder(node.right,x));
			case Min:
				return Math.min(inorder(node.left,x),inorder(node.right,x));
			case Comp:
				return node.left.fun.f(node.right.fun.f(x));

			}
		}
		return node.fun.f(x);
	} 


	@Override
	public function initFromString(String s) {
		s=s.replaceAll(" ","");
		if(s.charAt(0)=='+' || s.charAt(0)=='-' || Character.isDigit(s.charAt(0)))
			return new Polynom(s);
		String op="";
		String nc1="";
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)=='(') {
				op=op+s.substring(0,i);
				nc1=nc1+s.substring(i+1,s.length()-1);
				break;
			}
		}
		int numofopen=0;
		int numofclose=0;
		int numofpsic=0;
		String left="";
		String right="";
		for(int i=0;i<=nc1.length();i++) {
			if(numofopen==numofclose && numofclose>=1 && numofopen>=1) {
				left=nc1.substring(0,i);
				right=nc1.substring(i+1,nc1.length());
				break;
			}
			if(numofpsic>=1 && numofopen==0) {
				left=nc1.substring(0,i-1);
				right=nc1.substring(i,nc1.length());
				break;
			}
			if(nc1.charAt(i)=='(')
				numofopen++;
			if(nc1.charAt(i)==')')
				numofclose++;
			if(nc1.charAt(i)==',')
				numofpsic++;
		}

		return new ComplexFunction(op,initFromString(left),initFromString(right));



	}
	private boolean isOp(String s) {
		s=s.toLowerCase();
		if(s.equals("mul"))
			return true;
		if(s.equals("plus"))
			return true;
		if(s.equals("div"))
			return true;
		if(s.equals("comp"))
			return true;
		if(s.equals("max"))
			return true;
		if(s.equals("min"))
			return true;
		else
			return false;
	}

	@Override
	public function copy() {
		ComplexFunction f =new ComplexFunction();
		f.root=new NodeF(this.root);
		return f;
	}

	@Override
	public void plus(function f1) {
		NodeF newnode = new NodeF(Operation.Plus);
		newnode.right=new NodeF(f1);
		newnode.left=this.root;
		this.root=newnode;
	}

	@Override
	public void mul(function f1) {
		NodeF newnode = new NodeF(Operation.Times);
		newnode.right=new NodeF(f1);
		newnode.left=this.root;
		this.root=newnode;
	}

	@Override
	public void div(function f1) {
		NodeF newnode = new NodeF(Operation.Divid);
		newnode.right=new NodeF(f1);
		newnode.left=this.root;
		this.root=newnode;

	}

	@Override
	public void max(function f1) {
		NodeF newnode = new NodeF(Operation.Max);
		newnode.right=new NodeF(f1);
		newnode.left=this.root;
		this.root=newnode;

	}

	@Override
	public void min(function f1) {
		NodeF newnode = new NodeF(Operation.Min);
		newnode.right=new NodeF(f1);
		newnode.left=this.root;
		this.root=newnode;

	}

	@Override
	public void comp(function f1) {
		NodeF newnode = new NodeF(Operation.Comp);
		newnode.right=new NodeF(f1);
		newnode.left=this.root;
		this.root=newnode;

	}

	@Override
	public function left() {
		ComplexFunction compL =new ComplexFunction();
		compL.root=this.root.left;
		return compL;
	}

	@Override
	public function right() {
		ComplexFunction compR =new ComplexFunction();
		compR.root=this.root.right;
		return compR;
	}

	@Override
	public Operation getOp() {
		return root.op;
	}
	private String WhatOp(Operation op) {
		if(op==Operation.Plus)
			return "plus";
		if(op==Operation.Times)
			return "mul";
		if(op==Operation.Divid)
			return "div";
		if(op==Operation.Min)
			return "min";
		if(op==Operation.Max)
			return "max";
		else
			return "comp";
	}
	public String toString() {
		return printPreorder(this.root);
	}
	private String printPreorder(NodeF node) { 
		String ans="";
		if (node == null) 
			return ans; 

		if(node.op==Operation.None) {
			if(node.fun==null)
				return ans;
			ans=ans+node.fun.toString();
			return ans;
		}
		else
			ans=ans+WhatOp(node.op)+"(";
		ans=ans+printPreorder(node.left)+","+printPreorder(node.right)+")";
		return ans;


	} 
	public boolean equals(Object obj) {
		
		if(obj instanceof ComplexFunction) {
			for(int i=-100;i<=100;i+=2) {
				if(((ComplexFunction) obj).f(i)!=this.f(i) &&Math.abs(((ComplexFunction) obj).f(i)-this.f(i))>Monom.EPSILON)
					return false;
			}
			return true;
		}
		if(obj instanceof Polynom) {
			for(int i=-100;i<=100;i+=2) {
				if(((Polynom) obj).f(i)!=this.f(i) && Math.abs(((Polynom) obj).f(i)-this.f(i))>Monom.EPSILON)
					return false;
			}
			return true;
		}
		if(obj instanceof Monom) {
			for(int i=-100;i<=100;i+=2) {
				if(((Monom) obj).f(i)!=this.f(i) && Math.abs(((Monom) obj).f(i)-this.f(i))>Monom.EPSILON)
					return false;
			}
			return true;
		}
		return false;
	}


}
