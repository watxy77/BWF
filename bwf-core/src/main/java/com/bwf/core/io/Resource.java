package com.bwf.core.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
/**
 * @Author bjweijiannan
 * @description
 */
public interface Resource extends InputStreamSource {
    URL getURL(ClassLoader classLoader, String path) throws IOException;
    URI getURI() throws IOException;
    File getFile(URL url) throws IOException;
}
