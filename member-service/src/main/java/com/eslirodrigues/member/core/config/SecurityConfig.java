package com.eslirodrigues.member.core.config;

import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class SecurityConfig implements SecurityIdentityAugmentor {

    @Override
    public Uni<SecurityIdentity> augment(
            SecurityIdentity identity,
            AuthenticationRequestContext context
    ) {
        if (identity.isAnonymous()) {
            return Uni.createFrom().item(identity);
        }

        if (identity.getPrincipal() instanceof JsonWebToken jwt) {
            JsonObject realmAccess = jwt.getClaim("realm_access");
            if (realmAccess != null && realmAccess.containsKey("roles")) {
                JsonArray rolesArray = realmAccess.getJsonArray("roles");
                Set<String> roles = new HashSet<>();
                
                for (JsonValue role : rolesArray) {
                    if (role.getValueType() == JsonValue.ValueType.STRING) {
                        roles.add("ROLE_" + ((JsonString) role).getString());
                    }
                }
                var builder = QuarkusSecurityIdentity.builder(identity);
                builder.addRoles(roles);
                return Uni.createFrom().item(builder.build());
            }
        }
        return Uni.createFrom().item(identity);
    }
}