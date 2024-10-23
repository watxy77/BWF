package com.bwf.core.beans.resource;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

public class EncodedResource extends AbstractResource{
    @Nullable
    private final String path;

    public EncodedResource(@Nullable String path) {
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
