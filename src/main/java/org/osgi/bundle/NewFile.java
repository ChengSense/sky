package org.osgi.bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.common.io.ByteStreams;

public class NewFile extends File {
	public NewFile(InputStream input) {
		super("./" + System.currentTimeMillis() + ".jar");
		try {
			FileOutputStream output = new FileOutputStream(this);
			output.write(ByteStreams.toByteArray(input));
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
