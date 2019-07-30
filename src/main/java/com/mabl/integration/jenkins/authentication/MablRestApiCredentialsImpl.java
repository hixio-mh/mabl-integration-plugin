package com.mabl.integration.jenkins.authentication;

import com.cloudbees.plugins.credentials.CredentialsMatchers;
import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.CredentialsScope;
import com.cloudbees.plugins.credentials.domains.DomainRequirement;
import com.cloudbees.plugins.credentials.impl.BaseStandardCredentials;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.AbstractItem;
import hudson.util.Secret;
import org.jenkins.ui.icon.Icon;
import org.jenkins.ui.icon.IconSet;
import org.jenkins.ui.icon.IconType;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * mabl core REST API credential holder
 */

public class MablRestApiCredentialsImpl
        extends BaseStandardCredentials
        implements MablRestApiCredentials {

    private static final String PRETTY_NAME = "mabl";
    private static final String SUCCESS_MESSAGE = "mabl";

    private final String workspaceId; // unused
    private final Secret apiKey;

    @DataBoundConstructor
    public MablRestApiCredentialsImpl(
            final @CheckForNull CredentialsScope scope,
            final @CheckForNull String id,
            final @NonNull String username,
            final @NonNull String apiKey,
            final @CheckForNull String description
    ) {
        super(scope, id, description);
        this.workspaceId = username;
        this.apiKey = Secret.fromString(apiKey);
    }


    @NonNull
    @Override
    public String getUsername() {
        return getWorkspaceId(); // should only be one token per workspace
    }

    @Override
    public String getWorkspaceId() {
        return workspaceId;
    }

    @Override
    public Secret getApiKey() {
        return apiKey;
    }

    /**
     * Fetch matching credential, or default credential
     *
     * @param abstractItem reference abstract item
     * @return Matching credential, or default credential, or null if no credentials
     */
    public static MablRestApiCredentialsImpl getCredentials(
            final AbstractItem abstractItem,
            @Nullable final String credentialId
    ) {
        final List<MablRestApiCredentialsImpl> credentials =
                CredentialsProvider.lookupCredentials(MablRestApiCredentialsImpl.class,
                        abstractItem,
                        null,
                        new ArrayList<DomainRequirement>());

        if (credentialId == null || credentials.isEmpty()) {
            return null;
        }

        // Find first credential with the given id, or just first credential
        return CredentialsMatchers.firstOrDefault(
                credentials,
                CredentialsMatchers.allOf(CredentialsMatchers.withId(credentialId)),
                credentials.get(0)
        );
    }

    @Extension
    public static class DescriptorImpl extends BaseStandardCredentialsDescriptor {

        private static final String ICON_CLASS_NAME = "icon-mabl-credentials";

        @Override
        public String getDisplayName() {
            return "mabl API key";
        }

        @Override
        public String getIconClassName() {
            return ICON_CLASS_NAME;
        }

        static {
            IconSet.icons.addIcon(new Icon(
                    ICON_CLASS_NAME + " icon-sm",
                    "mabl-step/images/16x16/credentials.png",
                    Icon.ICON_SMALL_STYLE,
                    IconType.PLUGIN
            ));
            IconSet.icons.addIcon(new Icon(
                    ICON_CLASS_NAME + " icon-md",
                    "mabl-step/images/24x24/credentials.png",
                    Icon.ICON_MEDIUM_STYLE,
                    IconType.PLUGIN
            ));
            IconSet.icons.addIcon(new Icon(
                    ICON_CLASS_NAME + " icon-lg",
                    "mabl-step/images/32x32/credentials.png",
                    Icon.ICON_LARGE_STYLE,
                    IconType.PLUGIN
            ));
            IconSet.icons.addIcon(new Icon(
                    ICON_CLASS_NAME + " icon-xlg",
                    "mabl-step/images/48x48/credentials.png",
                    Icon.ICON_XLARGE_STYLE,
                    IconType.PLUGIN
            ));
        }
    }
}