package org.osgi.bundle;

public class BundleService {

	public BundleService() {
		DependenciesDefinition.execute();
	}

	public void start() {
		try {
			for (Class<?> clas : BundleManager.getBundle(Express.MAIN_CLASS))
				clas.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}