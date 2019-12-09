package Ex1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;




/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author 
 *
 */
public class Polynom implements Polynom_able{
	private ArrayList<Monom>_monoms=new ArrayList<Monom>();
	private Monom_Comperator c = new Monom_Comperator();

	/**
	 * create a new polynom with monom zero 
	 */
	public Polynom() {
		_monoms=new ArrayList<Monom>(1);
		_monoms.add(0,new Monom(0,0));
	}
	/**
	 * get a string and build new polynom
	 * @param s
	 */
	public Polynom(String s) {
		s=s.replaceAll(" ","");
		s=s.toLowerCase();
		String [] s1=s.split("(?=\\-)|(?=\\+)");
		for(int i=0;i<s1.length;i++)
			this._monoms.add(new Monom(s1[i]));
		this._monoms.sort(c);

	}
	@Override
	/**
	 * give the value of the polynom in f(x)
	 */
	public double f(double x) {
		double f_ans=0;
		for(int i=0;i<_monoms.size();i++) {
			f_ans=f_ans+_monoms.get(i).f(x);
		}
		return f_ans;
	}
	public int givesize() {
		return this._monoms.size();
	}

	@Override
	/**
	 * add a polynom to current polynom
	 */
	public void add(Polynom_able p1) {
		// TODO Auto-generated method stub

		Polynom p2=new Polynom(p1.toString());
		for(int i=0;i<p2._monoms.size();i++) {
			this.add(p2._monoms.get(i));
		}
		this._monoms.sort(c);


	}

	@Override
	/**
	 * add a monom to current polynom
	 */
	public void add(Monom m1) {
		// TODO Auto-generated method stub

		for(int i=0;i<this._monoms.size();i++) {
			if(this._monoms.get(i).get_power()==m1.get_power()) {
				m1.add(this._monoms.get(i));
				this._monoms.set(i,m1);
				this._monoms.sort(c);
				return;
			}
		}
		this._monoms.add(m1);
		this._monoms.sort(c);
	}

	@Override
	/**
	 * substract a polynom from the current polynom
	 */
	public void substract(Polynom_able p1) {
		// TODO Auto-generated method stub
		Polynom p2=new Polynom(p1.toString());
		String s1=p2.toString();
		String s3=s1.replace('+','a');
		s3=s3.replace('-','b');
		s3=s3.replace('b','+');
		s3=s3.replace('a','-');
		p2=new Polynom(s3);
		this.add(p2);
		this._monoms.sort(c);


	}

	@Override
	/**
	 * multiply a polynom from the current polynom
	 */
	public void multiply(Polynom_able p1) {
		// TODO Auto-generated method stub
		Polynom newp= new Polynom();
		Monom m1;
		Polynom p2=new Polynom(p1.toString());
		for(int i=0;i<p2._monoms.size();i++) {
			for(int j=0;j<this._monoms.size();j++) {
				m1=new Monom(p2._monoms.get(i));
				m1.multipy(this._monoms.get(j));
				newp.add(m1);
			}
		}
		this._monoms.clear();
		this.add(newp);
		this._monoms.sort(c);

	}
	@Override
	/**
	 * check if object is equal to the current polynom
	 */
	public boolean equals(Object p1) {
		if(p1 instanceof Polynom) {
			Iterator<Monom> itr=this.iteretor();
			Iterator<Monom> itr2=((Polynom) p1).iteretor();
			Monom m1=new Monom(itr.next());
			Monom m2=new Monom(itr2.next());
			if(!m1.equals(m2))
				return false;
			while(itr.hasNext()) {
				m1=new Monom(itr.next());
				if(itr2.hasNext()) 
					m2=new Monom(itr2.next());
				else if(!itr2.hasNext() && m1.isZero())
					return true;
				if(!m1.equals(m2))
					return false;
			}
			if(!itr2.hasNext())
				return true;
			if(itr2.hasNext())
				if(itr2.next().isZero())
					return true;
			return false;
		}
		if(p1 instanceof Monom) {
			if(this.givesize()==2) {
				if(this._monoms.get(0).equals(p1) && this._monoms.get(1).isZero())
					return true;
			}
			if(this.givesize()==1)
				if(this._monoms.get(0).equals(p1))
					return true;
		}
		return false;


	}



	@Override
	public boolean isZero() {
		// TODO Auto-generated method stub
		boolean flag=true;
		for(int i=0;i<this._monoms.size();i++)
			if(!this._monoms.get(i).isZero())
				flag=false;

		return flag;
	}

	@Override
	/**
	 *  Intermediate value theorem
	 */
	public double root(double x0, double x1, double eps) {
		// TODO Auto-generated method stub

		if((f(x0)*f(x1)>0)) throw new RuntimeException("ERR: your f(x0) and f(x1) are both positive ot negative ");
		if((f(x0)*f(x1)==0)) 
		{
			if(f(x0)==0) return x0;
			else return x1;
		}
		double smallFromEps=((x0+x1)/2);
		while(Math.abs(f(smallFromEps))>eps)
		{
			if(f(smallFromEps)*f(x0)<0) 
				x1=smallFromEps;
			else
				x0=smallFromEps;
			smallFromEps=((x0+x1)/2);
		}
		return smallFromEps;
	}

	@Override
	/**
	 * copy this polynom and retrun a new polynom 
	 */
	public function copy() {
		// TODO Auto-generated method stub
		function p=new Polynom(this.toString());
		return p;
	}

	@Override
	/**
	 * return the derivative of this polynom in a form of polynom
	 */
	public Polynom_able derivative() {
		// TODO Auto-generated method stub
		Polynom p=new Polynom();
		for(int i=0;i<this._monoms.size();i++)
			p.add(this._monoms.get(i).derivative());
		p._monoms.sort(c);
		return p;
	}

	@Override
	/**
	 * calcuate the area bettween the function and the positive area in the x0 x1 point
	 */
	public double area(double x0, double x1, double eps) {
		// TODO Auto-generated method stub
		double area_sum=0;
		for (; x0 < x1; x0+=eps) {
			if(f(x0)>0)
				area_sum+=(f(x0)*eps);
		}
		return area_sum;

	}

	@Override
	public Iterator<Monom> iteretor() {
		// TODO Auto-generated method stub
		return this._monoms.iterator();

	}
	@Override
	public void multiply(Monom m1) {
		// TODO Auto-generated method stub
		Polynom newp=new Polynom();
		Monom m2;
		for(int i=0;i<this._monoms.size();i++) {
			m2=new Monom(this._monoms.get(i));
			m2.multipy(m1);
			newp.add(m2);
		}
		this._monoms.clear();
		this.add(newp);
		this._monoms.sort(c);
	}

	/**
	 * return the string of this polynom
	 */
	public String toString() {
		String ans="";
		for(int i=0;i<this._monoms.size();i++) {
			if(this._monoms.get(i).get_coefficient()==0)
				ans=ans+"";
			else if(this._monoms.get(i).get_coefficient()>0)
				ans=ans+"+"+this._monoms.get(i).toString();
			else
				ans=ans+this._monoms.get(i).toString();

		}


		return ans;
	}
	@Override
	public function initFromString(String s) {
		// TODO Auto-generated method stub
		function f =new Polynom(s);
		return f;
	}

}
