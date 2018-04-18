package com.mabl.integration.jenkins.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * mabl result from deployment event creation
 */

public class ApiKeyLookupResult {
    public final String id;
    public final String name;
    public final String workspaceId;

    @JsonCreator
    public ApiKeyLookupResult(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("organization_id") String workspaceId
    ) {
        this.id = id;
        this.name = name;
        this.workspaceId = workspaceId;
    }
}