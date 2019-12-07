package Ex1;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		if(_functions.isEmpty())
			return true;

		return false;
	}

	@Override
	public Iterator<function> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {

		return _functions.size();
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initFromFile(String file) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveToFile(String file) throws IOException {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	public function get(int i) {

		return _functions.get(i);
	}

}
