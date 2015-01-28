import com.impinj.octanesdk.Tag;

public class TagWrapper {
	private Tag tag;
	private TagLocation location;
	
	public TagWrapper() {
		this.tag = null;
		this.location = null;
	}
	
	public TagWrapper(Tag tag) {
		this.tag = tag;
		location = TagLocation.WAREHOUSE;
	}
	
	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public TagLocation getLocation() {
		return location;
	}

	public void setLocation(TagLocation location) {
		this.location = location;
	}
	
	public boolean isInStock() {
		return location != TagLocation.OUT_OF_STORE;
	}
}
