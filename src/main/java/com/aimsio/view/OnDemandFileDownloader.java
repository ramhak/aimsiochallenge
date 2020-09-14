package com.aimsio.view;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;

import java.io.IOException;
import java.util.Objects;

public class OnDemandFileDownloader extends FileDownloader {


    public interface OnDemandStreamResource extends StreamResource.StreamSource {
        String getFilename ();
    }

    private static final long serialVersionUID = 1L;
    private final OnDemandStreamResource onDemandStreamResource;

    public OnDemandFileDownloader (OnDemandStreamResource onDemandStreamResource) {
        super(new StreamResource(onDemandStreamResource, ""));
        this.onDemandStreamResource = Objects.requireNonNull(onDemandStreamResource,
                "The given on-demand stream resource may never be null!");
    }

    @Override
    public boolean handleConnectorRequest (VaadinRequest request, VaadinResponse response, String path)
            throws IOException {
        getResource().setFilename(onDemandStreamResource.getFilename());
        return super.handleConnectorRequest(request, response, path);
    }

    private StreamResource getResource() {
        StreamResource result;
        this.getSession().lock();
        try {
            result = (StreamResource) this.getResource("dl");
        } finally {
            this.getSession().unlock();
        }
        return result;
    }
}
