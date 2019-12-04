
package Ex1;

import java.util.Comparator;

import javax.management.RuntimeErrorException;





/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************
	public Monom(String s) {

		try {
			s=s.toLowerCase();
			int i=0;
			String a_coefficient ="";
			String b_power="";
			while(i<s.length()&&s.charAt(i)!='x') {

				a_coefficient+=s.charAt(i);
				i++;
			}
			if((s.length()>i+1 && s.charAt(i+1)!='^')|| s.charAt(s.length()-1)=='^')
				throw new RuntimeException("your monom is not good---> "+s);

			for(int j=0;j<s.length();j++) {
				if(s.charAt(j)=='^')
					b_power=s.substring(j+1);
			}
			if(s.length()>=1&&s.charAt(0)=='x')
				set_coefficient(1);
			else if(s.length()==2&&s.charAt(0)=='-'&&s.charAt(1)=='x')
				set_coefficient(-1);
			else 
				set_coefficient(Double.parseDouble(a_coefficient));
			String x="x";

			if(!s.contains(x)) 
				set_power(0);
			else if(b_power.length()==0)
				set_power(1);
			else set_power(Integer.parseInt(b_power));

		} catch(Exception j) {

			throw new RuntimeException("Err:your monom is not good---> "+s);
		}



	}

	public void add(Monom m) {
		if(m._power==get_power()) 
		{
			m._coefficient+=get_coefficient();
			set_coefficient(m._coefficient);
		}
		else 
			throw new RuntimeException("can not add this monom");

	}


	public void multipy(Monom d) {

		this._coefficient=this._coefficient*d._coefficient;
		this._power=this._power+d._power;
	}

	public String toString() {
		String ans = "";
		if(this._coefficient==0)
			ans="0";
		else if(this._power==0)
			ans=ans+this._coefficient;
		else if(this._power==1)
			ans=ans+this._coefficient+"x";
		else if(this._power>1)
			ans=ans+this._coefficient+"x"+"^"+this._power;


		return ans;
	}
	
	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}

	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
	@Override
	public Ex1.function initFromString(String s) {
		function f=new Monom(s);
		return f;
	}
	@Override
	public function copy() {
		String s=this.toString();
		function m1=new Monom(s);
		return m1;
	}
	public boolean equals(Object obj){
		if(obj instanceof Polynom) {
			Polynom p =new Polynom(obj.toString());
			if(p.givesize()>2)
				return false;
			else if(p.iteretor().next().equals(this) && p.iteretor().next().isZero())
				return true;
			else if(p.givesize()==1 && p.iteretor().next().equals(this) )
				return true;

		}
		if(obj instanceof Monom) {
			Monom m1 =new Monom(obj.toString());
			if(this.get_power()==m1.get_power()) {
				if(Math.abs(this.get_coefficient()-m1.get_coefficient())<=EPSILON)
					return true;
				else if(this.get_coefficient()==m1.get_coefficient())
					return true;
			}
		}
		return false;



	}


}
