package org.osgi.bundle;

public class Express {

	public static final String JAR = "jar";
	public static final String CLASS = "class";
	public static final String SUFFIXES_DOT = ".";
	public static final String EXPRESS_DOT = "\\.";
	public static final String SUFFIXES_JAR = ".jar";
	public static final String SLASH = "/";
	public static final String SUFFIXES_CLASS = ".class";
	public static final String EXPRESS_SUFFIXES_CLASS = "\\.class$";
	public static final String EXPRESS_OBJECT = "\\[*L?";
	public static final String EXPRESS_BASE_TYPE = "\\[*(Z|C|B|S|I|F|J|D)?";
	public static final String EXPRESS_BASE_SYSTEM = "(java|javax|org\\.ietf\\.jgss|org\\.omg|org\\.w3c\\.dom|org\\.xml\\.sax).*";
	public static final String PAKAGE_FILTER = "(org\\.osgi\\.bundle\\.BundleManager|org\\.osgi\\.bundle\\.ClassCache)";
	public static final String MAIN_CLASS = "org.osgi.bundle.Launcher";

	public static String disClass(String str) {
		if (str == null)
			return "";
		return str.replaceAll(SLASH, SUFFIXES_DOT).replaceAll(EXPRESS_SUFFIXES_CLASS, "").replaceAll(";", "");
	}

	public static String enClass(String str) {
		if (str == null)
			return "";
		return str.replaceAll(EXPRESS_DOT, SLASH) + SUFFIXES_CLASS;
	}

	public static boolean filter(String str) {
		if (str.matches(PAKAGE_FILTER))
			return true;
		return false;
	}

	public static String get(String str) {
		if (str.endsWith(SUFFIXES_JAR)) {
			return JAR;
		}
		if (str.endsWith(SUFFIXES_CLASS)) {
			return CLASS;
		}
		return "";
	}
}
