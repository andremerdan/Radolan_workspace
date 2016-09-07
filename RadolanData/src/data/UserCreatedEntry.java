package data;

import java.util.Date;

/**
 * Represents user created entries into the database.
 * 
 * @author Patrick Allan Blair
 *
 */
public class UserCreatedEntry {
	private long t1;
	private long t2;
	private String g;
	private int[] c;
	private int mv;
	private int w;
	private String b;

	/**
	 * 
	 * @param start The starting date of this entry.
	 * @param end The ending date of this entry.
	 * @param center The rain centers of this entry.
	 * @param windDirection The wind direction of this entry.
	 * @param maxValue The maximum precipitation of this entry.
	 * @param description A description for this entry.
	 */
	public UserCreatedEntry(Date start, Date end, int[] center, int windDirection, int maxValue, String description){
		this.t1 = start.getTime();
		this.t2 = end.getTime();
		this.c = center;
		this.w = windDirection;
		this.mv = maxValue;
		this.g = generateGlyphName();
		this.setDescription(description);
	}

	/**
	 * Generates the name used for glyph image files for this entry. The name is generated through all values
	 * of the entry and may be used as an identifier.
	 * 
	 * @return A name for the glyph image file of this entry.
	 */
	public String generateGlyphName(){
		StringBuilder glyph = new StringBuilder();
		glyph.append(t1 + "-" + t2 + "-" + mv + "-" + w);
		for(int i : c) glyph.append("-" + i);
		glyph.append(".png");
		return glyph.toString();
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

	public int getMaxValue() {
		return mv;
	}

	public void setMaxValue(int maxValue) {
		this.mv = maxValue;
	}

	public int[] getCenter() {
		return c;
	}

	public void setCenter(int[] center) {
		this.c = center;
	}

	public int getWindDirection() {
		return w;
	}

	public void setWindDirection(int windDirection) {
		this.w = windDirection;
	}

	public String getGlyphName() {
		return g;
	}

	public String getDescription() {
		return b;
	}

	public void setDescription(String description) {
		this.b = description;
	}
}
