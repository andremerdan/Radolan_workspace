package data;

import java.util.Date;

/**
 * Represents database entries denoting times and areas that have been checked by the user.
 * 
 * @author Patrick Allan Blair
 *
 */
public class NegativeEntry {
	private long t1;
	private long t2;
	private int[] a;

	/**
	 * 
	 * @param start The starting date of this entry.
	 * @param end The ending date of this entry.
	 * @param area The area of this entry.
	 */
	public NegativeEntry(Date start, Date end, int[] area){
		this.t1 = start.getTime();
		this.t2 = end.getTime();
		this.a = area;
	}

	public Date getStartTime() {
		return new Date(t1);
	}

	public void setStartTime(Date time) {
		this.t1 = time.getTime();
	}
	
	public Date getEndTime() {
		return new Date(t2);
	}

	public void setEndTime(Date time) {
		this.t2 = time.getTime();
	}

	public int[] getArea() {
		return a;
	}

	public void setArea(int[] area) {
		this.a = area;
	}

	public void setX1(int x1){
		this.a[0] = x1;
	}

	public void setY1(int y1){
		this.a[1] = y1;
	}

	public void setX2(int x2){
		this.a[2] = x2;
	}

	public void setY2(int y2){
		this.a[3] = y2;
	}
}
