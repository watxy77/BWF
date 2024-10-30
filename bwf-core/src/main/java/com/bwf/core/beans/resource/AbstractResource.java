package com.bwf.core.beans.resource;

import com.bwf.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public abstract class AbstractResource implements Resource {
    @Override
    public URL getURL(ClassLoader classLoader, String path) {
        URL url = classLoader.getResource(path);
        return url;
    }

    @Override
    public URI getURI() throws IOException {
        return null;
    }

    @Override
    public File getFile(URL url) {
        File file = new File(url.getFile());
        return file;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }
}
