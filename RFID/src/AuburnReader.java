import com.impinj.octanesdk.ImpinjReader;


public class AuburnReader extends ImpinjReader {
	private ReaderLocation location;
	
	public ReaderLocation getLocation() {
		return location;
	}

	public void setLocation(ReaderLocation location) {
		this.location = location;
	}
	
}
