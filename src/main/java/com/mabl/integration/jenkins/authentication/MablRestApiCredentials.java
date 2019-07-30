package com.mabl.integration.jenkins.authentication;

import com.cloudbees.plugins.credentials.CredentialsNameProvider;
import com.cloudbees.plugins.credentials.NameWith;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
import com.cloudbees.plugins.credentials.common.StandardUsernameCredentials;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Util;
import hudson.model.AbstractItem;
import hudson.util.Secret;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * mabl core REST API credential holder
 */

@NameWith(
        value = MablRestApiCredentials.NameProvider.class,
        priority = 32 // must be higher than parent interface
)
public interface MablRestApiCredentials
        extends StandardUsernameCredentials {

    class NameProvider extends CredentialsNameProvider<MablRestApiCredentials> {
        @NonNull
        @Override
        public String getName(@NonNull MablRestApiCredentials credentials) {
            final String description = Util.fixEmptyAndTrim(credentials.getDescription());
            return String.format("%s/mabl-api-key %s", credentials.getUsername(),
                    description != null ? " (" + description + ")" : ""
            );
        }
    }

    Secret getApiKey() throws IOException, InterruptedException;

    String getWorkspaceId();

    String getDescription();

    String getId();
}