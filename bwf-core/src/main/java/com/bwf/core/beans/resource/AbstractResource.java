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
    public boolean exists() {
        // Try file existence: can we find the file in the file system?
        if (isFile()) {
            try {
                return getFile().exists();
            }
            catch (IOException ex) {
                Log logger = LogFactory.getLog(getClass());
                if (logger.isDebugEnabled()) {
                    logger.debug("Could not retrieve File for existence check of " + getDescription(), ex);
                }
            }
        }
        // Fall back to stream existence: can we open the stream?
        try {
            getInputStream().close();
            return true;
        }
        catch (Throwable ex) {
            Log logger = LogFactory.getLog(getClass());
            if (logger.isDebugEnabled()) {
                logger.debug("Could not retrieve InputStream for existence check of " + getDescription(), ex);
            }
            return false;
        }
    }

    @Override
    public boolean isReadable() {
        return exists();
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public URL getURL() throws IOException {
        throw new FileNotFoundException(getDescription() + " cannot be resolved to URL");
    }

    @Override
    public URI getURI() throws IOException {
        URL url = getURL();
        try {
            return new URI(StringUtils.replace(url.toString(), " ", "%20"));
        }
        catch (URISyntaxException ex) {
            throw new IOException("Invalid URI [" + url + "]", ex);
        }
    }

    @Override
    public File getFile() throws IOException {
        throw new FileNotFoundException(getDescription() + " cannot be resolved to absolute file path");
    }

    @Override
    public ReadableByteChannel readableChannel() throws IOException {
        return Channels.newChannel(getInputStream());
    }

    @Override
    public long contentLength() throws IOException {
        InputStream is = getInputStream();
        try {
            long size = 0;
            byte[] buf = new byte[256];
            int read;
            while ((read = is.read(buf)) != -1) {
                size += read;
            }
            return size;
        }
        finally {
            try {
                is.close();
            }
            catch (IOException ex) {
                Log logger = LogFactory.getLog(getClass());
                if (logger.isDebugEnabled()) {
                    logger.debug("Could not close content-length InputStream for " + getDescription(), ex);
                }
            }
        }
    }

    @Override
    public long lastModified() throws IOException {
        File fileToCheck = getFileForLastModifiedCheck();
        long lastModified = fileToCheck.lastModified();
        if (lastModified == 0L && !fileToCheck.exists()) {
            throw new FileNotFoundException(getDescription() +
                    " cannot be resolved in the file system for checking its last-modified timestamp");
        }
        return lastModified;
    }

    @Override
    public Resource createRelative(String var1) throws IOException {
        throw new FileNotFoundException("Cannot create a relative resource for " + getDescription());
    }

    @Override
    public String getFilename() {
        return null;
    }

    protected File getFileForLastModifiedCheck() throws IOException {
        return getFile();
    }
}
