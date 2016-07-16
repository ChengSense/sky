import org.osgi.bundle.BundleService;

public class Main {

	public static void main(String[] agrs) {
		BundleService service = new BundleService();
		service.start();
	}
}
