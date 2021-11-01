import java.awt.*;
import java.applet.Applet; 
import javax.swing.*;
import netscape.javascript.*;
public class Bezier extends Applet {
	
	
	Point[] cp; //punctele de control 
	int numpoints; //numarul de puncte de control 
	Image im; 
	Graphics img;
	double k = .025; 
	public int n=8;
	//String s;
	int moveflag ; //indica punctul de control deplasate
	
	Button restart; 
	
	public void init(){
		//s=getParameter("nrpuncte");
	//	n=Integer.valueOf(s).intValue();
		moveflag=n+1;
		cp = new Point[n]; 
		im = createImage(size().width,size().height);
		img = im.getGraphics(); 
		restart = new Button("Restart"); 
		add(restart);
	 } 
	public void update(Graphics g) {
		paint(g);
	} 

	public void paint(Graphics g){
	 setBackground(Color.white);
	 img.setColor(Color.black); 
	 img.clearRect(0,0,size().width,size().height); //deseneaza punctele de control si poligonul de control 
	 for(int i=0;i<numpoints;i++) {
		 img.setColor(Color.red); 
		 img.fillOval(cp[i].x-2, cp[i].y-2,n,n); 
		 img.setColor(Color.blue); 
		 if(numpoints>1 && i<(numpoints-1)) 
			img.drawLine(cp[i].x,cp[i].y, cp[i+1].x,cp[i+1].y); 
	} //calculeaza si deseneaza curba Bezier 
	if(numpoints == n) { 
		int x = cp[0].x, y = cp[0].y; 
		img.setColor(Color.black); 
	for(double t=k;t<=1+k;t+=k){
		
		    Point p = Bernstein(cp,t); 
			img.drawLine(x, y, p.x, p.y); 
			x = p.x;
			y = p.y; 
		
	}
	}
		g.drawImage(im,0,0,this); 
	}
	
	public double fact(int nr){
		double f=1;
		for(int i=1;i<=nr;i++)
			f*=i;
		return f;
	}
	
	
	public Point Bernstein(Point[] p, double t){
		double x,y;
		int nr=p.length;
		x=Math.pow(1-t,nr-1)*p[0].x;
		y=Math.pow(1-t,nr-1)*p[0].y;
		for(int i=1;i<nr;i++){
			x+=(fact(nr-1)/(fact(nr-1-i)*fact(i)))*Math.pow(t,i)*Math.pow(1-t,nr-1-i)*p[i].x;
			y+=(fact(nr-1)/(fact(nr-1-i)*fact(i)))*Math.pow(t,i)*Math.pow(1-t,nr-1-i)*p[i].y;
		}
	
		return new Point ((int)Math.round(x), (int)Math.round(y)); 
	} 
	
	public boolean action(Event e, Object o) {
	if (e.target == restart) { 
	 numpoints = 0;
	 repaint();
	 return true; 
	}
	 return false; 
	}
	 
	public boolean mouseDown(Event evt, int x, int y){
	 Point point = new Point(x,y);
	 if(numpoints < n) {
		 cp[numpoints] = point;
		 numpoints++; 
		 repaint(); 
	 }
	 else 
	 for(int i=0;i<numpoints;i++) 
		for(int j=-2;j<3;j++) 
			for(int l=-2;l<3;l++)
				if(point.equals(new Point(cp[i].x+j, cp[i].y+l)))
					moveflag = i;
		return true; 
	} 
	public boolean mouseDrag(Event evt, int x, int y){
	 if(moveflag < numpoints) {
		cp[moveflag].move(x,y);
		repaint();
	 }
	 return true; 
	 }
	public boolean mouseUp(Event evt, int x, int y) {
		moveflag = n+1; 
		return true; 
		} 
}
