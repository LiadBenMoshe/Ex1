package Ex1;
public class Parameters {
	int Width;
	int Height;
	int Resolution;
	double [] Range_X;
	double [] Range_Y;
	
	public String toString() {
		return "["+this.Width+","+this.Height+","+this.Resolution+",["+Range_X[0]+","+Range_X[1]
		+"],["+Range_Y[0]+","+Range_Y[1]+"]]";
		
	}
	
}
