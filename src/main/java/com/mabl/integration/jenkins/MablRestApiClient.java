package com.mabl.integration.jenkins;

import com.mabl.integration.jenkins.domain.ApiKeyLookupResult;
import com.mabl.integration.jenkins.domain.CreateDeploymentResult;
import com.mabl.integration.jenkins.domain.ExecutionResult;

import java.io.IOException;

public interface MablRestApiClient {

    /**
     * Validate if API key is valid for mabl REST api
     * @param restApiKey api key
     * @return API Key details
     * @throws IOException on network error
     * @throws IllegalArgumentException on invalid key submission
     */
    ApiKeyLookupResult lookupApiKey(
            String restApiKey
    ) throws IOException, MablSystemError;

    /**
     * Create a new deployment to start all associated plans.
     * @param environmentId environment identifier
     * @param applicationId application identifier
     * @return deployment result
     * @throws IOException on network error
     * @throws MablSystemError on mabl error
     */
    CreateDeploymentResult createDeploymentEvent(
            String environmentId,
            String applicationId
    ) throws IOException, MablSystemError;

    /**
     * Attempt to fetch results for given deployment event
     *
     * @param eventId deployment event identifier
     * @return partially parsed download, or null on 404
     * @throws IOException on parsing error
     * @throws MablSystemError on non 200 or 404 response
     */
    ExecutionResult getExecutionResults(
            String eventId
    ) throws IOException, MablSystemError;

    /**
     * Close client connections
     */
    void close();
}
