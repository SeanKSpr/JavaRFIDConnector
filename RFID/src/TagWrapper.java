import com.impinj.octanesdk.Tag;
/**
 * TagWrapper is an implementation of the Wrapper design pattern. This class adds functionality to 
 * the Impinj written Tag class. In particular, TagWrapper adds a TagLocation, and a time seen 
 * (in Epoch time) to the tag. 
 * 
 * @since 2-3-2015
 * @version 1
 * @author Sean Spurlin
 * 
 */
public class TagWrapper {
	private Tag tag;
	private TagLocation location;
	private long timeSeen;
	
	/**
	 * Default constructor for TagWrapper. Creates a TagWrapper with all fields set to null.
	 */
	public TagWrapper() {
		this.tag = null;
		this.location = null;
	}
	
	/**
	 * Constructor which takes the input tag and wraps it up. When it does this, it sets the timestamp of the tag to be the
	 * current time in milliseconds from the Epoch, and sets the TagLocation to be in the warehouse.
	 * @param tag The tag which is to be wrapped up
	 */
	public TagWrapper(Tag tag) {
		this.tag = tag;
		location = TagLocation.WAREHOUSE;
		this.setTimeSeen(System.currentTimeMillis());
	}
	
	/**
	 * Returns the tag field of the TagWrapper
	 * @return Tag
	 */
	public Tag getTag() {
		return tag;
	}
	
	/**
	 * Sets the tag of the TagWrapper to the input Tag
	 * @param tag the new Tag which is being wrapped
	 */
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
	/**
	 * Returns the location of the wrapped tag
	 * @return the location of the wrapped tag
	 */
	public TagLocation getLocation() {
		return location;
	}

	/**
	 * Sets the location of the wrapped tag to be that of the input location
	 * @param location the new location of the wrapped tag
	 */
	public void setLocation(TagLocation location) {
		this.location = location;
	}
	
	/**
	 * Boolean function which is used to determine whether or not the product associated with the wrapped tag is still in
	 * stock
	 * @return False of the tag's location isn't out of the store; True otherwise.
	 */
	public boolean isInStock() {
		return location != TagLocation.OUT_OF_STORE;
	}
	
	/**
	 * Returns the time the tag was wrapped up which is nearly equivalent to the time the tag was read by the RFID reader. 
	 * The time seen is in Epoch time.
	 * @return the time the wrapped tag was seen
	 */
	public long getTimeSeen() {
		return timeSeen;
	}
	
	/**
	 * Sets the time the tag was seen to the input long
	 * @param timeSeen the new time in which the tag was seen
	 */
	public void setTimeSeen(long timeSeen) {
		this.timeSeen = timeSeen;
	}
}
