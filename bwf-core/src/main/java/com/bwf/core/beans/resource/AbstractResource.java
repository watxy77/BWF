package com.bwf.core.beans.resource;

import com.bwf.common.utils.StringUtils;
import com.bwf.core.io.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

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
