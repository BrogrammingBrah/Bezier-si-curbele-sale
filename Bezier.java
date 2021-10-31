import java.awt.*;
import java.applet.Applet; 
import java.util.Scanner;
public class Bezier extends Applet {
	
	
	Point[] cp; //punctele de control 
	int numpoints; //numarul de puncte de control 
	Image im; 
	Graphics img;
	double k = .025; 
	int n=6;
	int moveflag = n; //indica punctul de control deplasate
	
	Button restart; 
	
	public void init(){
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
		for(int i=0;i<n;i++)
		{
		Point p = Bernstein(cp[i],t); }
			 img.drawLine(x, y, p.x, p.y); 
			 x = p.x;
			 y = p.y; 
		
	 }
	}
		g.drawImage(im,0,0,this); 
	}
	
	//public Point Bernstein(Point p1, Point p2, Point p3, Point p4, Point p5, Point p6, double t){
	// double x = (1-t)*(1-t)*(1-t)*(1-t)*pl.x + (4*t)*(1-t)*(1-t)*(1-t)*p2.x + (6*t*t)*(1-t)*(1-t)*p3.x + (4*t*t*t)*(1-t)*p4.x + t*t*t*t*p5.x; 
	// double y = (1-t)*(1-t)*(1-t)*(1-t)*pl.y + (4*t)*(1-t)*(1-t)*(1-t)*p2.y + (6*t*t)*(1-t)*(1-t)*p3.y + (4*t*t*t)*(1-t)*p4.y + t*t*t*t*p5.y;
	//double x=(1-t)*(1-t)*(1-t)*(1-t)*(1-t)*p1.x+(5*t)*(1-t)*(1-t)*(1-t)*(1-t)*p2.x+(10*t*t)*(1-t)*(1-t)*(1-t)*p3.x+(10*t*t*t)*(1-t)*(1-t)*p4.x+(5*t*t*t*t)*(1-t)*p5.x+t*t*t*t*t*p6.x;
	//double y=(1-t)*(1-t)*(1-t)*(1-t)*(1-t)*p1.y+(5*t)*(1-t)*(1-t)*(1-t)*(1-t)*p2.y+(10*t*t)*(1-t)*(1-t)*(1-t)*p3.y+(10*t*t*t)*(1-t)*(1-t)*p4.y+(5*t*t*t*t)*(1-t)*p5.y+t*t*t*t*t*p6.y;
	/*double x;
	double y;
	int tn=1,tk=1,tnk=1;
	for(int i=1;i<n;i++)
	{
		for(int j=1;j<=k;j++)
		{
			for(int l=1;l<=(n-k);l++)
				{tnk=tnk*l;} //tnk=(n-k)!
			tk=tk*j; // tk=k!
		}
		tn=tn*i; //tn=n!
	}
	double comb;
	if(k==0)
		comb=1;
	else
		if(n==k)
			comb=1;
		else
			comb=tn/(tk*tnk);
	for(int i=0;i<n;i++)
	{
		x=(1-t)*pi.x;
	}*/
	public Point Bernstein(Point p[], double t){
	double x=0,y=0;
	double tn=1,tk=1,tnk=1;
	double a=1,b=(1-t);
	for(int i=1;i<=n;i++)
		tn=tn*i;
	for(int k=0;k<n;k++)
	{
		for(int i=0;i<k;i++)
		{
			if(i>0)
				a=a*t;
			tk=tk*i;
			if(tk==0)
				tk=1;
			for(int j=0;j<(n-k);j++)
			{
				b=(1-t)*b;
				tnk=tnk*j;
				if(tnk==0)
					tnk=1;
				//x+=(tn/(tk*tnk))*a*b*p[k+1].x;
				//y+=(tn/(tk*tnk))*a*b*p[k+1].y;
				x+=(tn/(tk*tnk))*Math.pow(t,k)*Math.pow((1-t),(n-k))*p[k+1].x;
				y+=(tn/(tk*tnk))*Math.pow(t,k)*Math.pow((1-t),(n-k))*p[k+1].y;
			}
		}
		
		
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
		moveflag = n; 
		return true; 
		} 
}
