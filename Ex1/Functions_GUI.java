package Ex1;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.google.gson.Gson;

import Bdikot.Bookstore;



public class Functions_GUI implements functions {
	private ArrayList<function> _functions =new ArrayList<function>();

	@Override
	public boolean add(function arg0) {
		if(arg0 instanceof function) {
			_functions.add(arg0);
			return true;
		}
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends function> arg0) {
		return this._functions.addAll(arg0);
	}

	@Override
	public void clear() {
		_functions.clear();

	}

	@Override
	public boolean contains(Object arg0) {
		if(_functions.contains(arg0))
			return true;
		return false;

	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return _functions.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		if(_functions.isEmpty())
			return true;

		return false;
	}

	@Override
	public Iterator<function> iterator() {
		return _functions.iterator();
	}

	@Override
	public boolean remove(Object arg0) {
		return _functions.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		return _functions.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return _functions.retainAll(arg0);
	}

	@Override
	public int size() {

		return _functions.size();
	}

	@Override
	public Object[] toArray() {
		return _functions.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return _functions.toArray(arg0);
	}

	@Override
	public void initFromFile(String file) throws IOException {
		// TODO Auto-generated method stub
		Polynom p1 =new Polynom("2x^2");
		ComplexFunction cf =new ComplexFunction(p1);
		String line="";
		this._functions.clear();
		BufferedReader br = new BufferedReader(new FileReader(file));
		while ((line = br.readLine()) != null) 
		{
			this._functions.add(cf.initFromString(line));
		}
		br.close();


	}

	@Override
	public void saveToFile(String file) throws IOException {
		// TODO Auto-generated method stub
		try {
			PrintWriter pw = new PrintWriter(new File(file));
			for(int i=0;i<this.size();i++) {
				pw.write(this._functions.get(i).toString());
				pw.write("\n");
			}
			pw.close();
		}
		catch  (FileNotFoundException e){
			e.printStackTrace();
		}

	}

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		// TODO Auto-generated method stub
		StdDraw.setCanvasSize(width,height);
		StdDraw.setXscale(rx.get_min(),rx.get_max());
		StdDraw.setYscale(ry.get_min(),ry.get_max());
		StdDraw.setPenColor(Color.LIGHT_GRAY);
		// x 
		for (int i = (int) rx.get_min(); i <= rx.get_max(); i=i+1) {
			StdDraw.line(i,ry.get_min(), i, ry.get_max());
		}
		//y
		for (double i = ry.get_min(); i <= ry.get_max(); i=i+1) {
			StdDraw.line(rx.get_min(), i,rx.get_max(), i);
		}

		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.005);

		StdDraw.line(rx.get_min(),(-1*ry.get_min()+ry.get_min()) ,rx.get_max(),(-1*ry.get_min()+ry.get_min()));
		StdDraw.line((-1*rx.get_min()+rx.get_min()),ry.get_min(),(-1*rx.get_min()+rx.get_min()),ry.get_max());
		//draw number on x
		for (int i = (int) rx.get_min(); i <= rx.get_max(); i=i+1) {
			StdDraw.text(i, (-1*ry.get_min()+ry.get_min())-0.4,Integer.toString(i));
		}
		//draw number on y
		for (int i =(int)ry.get_min(); i <= ry.get_max(); i=i+1) {
			StdDraw.text((-1*rx.get_min()+rx.get_min())+0.4,i,Integer.toString(i));
		}
		StdDraw.setPenRadius(0.007);
		for(int i=0;i<_functions.size();i++) {
			drawFunc(_functions.get(i),resolution,rx.get_min(),rx.get_max());
		}

	}
	private void drawFunc(function f1,int n,double min ,double max) {
		StdDraw.setPenColor((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
		double[] x = new double[n+1];
		double[] y = new double[n+1];
		x[0]=min;
		y[0]=f1.f(x[0]);
		double nextstep=(max-min)/(double)n;
		for (int i =1; i <= n; i++) {
			x[i] = x[i-1]+nextstep;
			y[i] = f1.f(x[i]);
		}

		for (int i = 0; i<n; i++) {

			StdDraw.line(x[i], y[i], x[i+1], y[i+1]);
		}


	}

	@Override
	public void drawFunctions(String json_file) {
		Gson gson = new Gson();
		Parameters parameters =null;
		try 
		{

			FileReader reader = new FileReader(json_file);
			parameters = gson.fromJson(reader,Parameters.class);
			

		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Range x=new Range(parameters.Range_X[0],parameters.Range_X[1]);
		Range y=new Range(parameters.Range_Y[0],parameters.Range_Y[1]);
		this.drawFunctions(parameters.Width,parameters.Height,x,y,parameters.Resolution);


	}

	public function get(int i) {

		return _functions.get(i);
	}

}
