package com.loop_anime.app.api;

import com.loop_anime.app.api.model.DirectLink;

import java.util.List;

/**
 * Created by allan on 14/8/24.
 */
public class DirectLinkResponse {

    private DirectLinkPayload payload;

    public DirectLinkPayload getPayload() {
        return payload;
    }

    public class DirectLinkPayload {

        private List<DirectLink> links;

        public List<DirectLink> getDirectLinks() {
            return links;
        }
    }
}
