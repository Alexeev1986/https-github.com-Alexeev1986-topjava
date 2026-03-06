package ru.javawebinar.topjava;

import org.slf4j.Logger;
import org.springframework.lang.NonNull;
import org.springframework.test.context.support.DefaultActiveProfilesResolver;

import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;

//http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver
public class ActiveDbProfileResolver extends DefaultActiveProfilesResolver {
    private static final Logger log = getLogger(ActiveDbProfileResolver.class);

    @Override
    public @NonNull String[] resolve(@NonNull Class<?> aClass) {
        // https://stackoverflow.com/a/52438829/548473
        String[] activeProfiles = super.resolve(aClass);
        String dbProfile = Profiles.getActiveDbProfile();
        String[] activeProfilesWithDb = Arrays.copyOf(activeProfiles, activeProfiles.length + 1);
        activeProfilesWithDb[activeProfiles.length] = Profiles.getActiveDbProfile();

        log.info("\n========================================\n" +
                        "ActiveDbProfileResolver for: {}\n" +
                        "  • Explicit profiles:  {}\n" +
                        "  • Detected DB profile: {}\n" +
                        "  • Final active profiles: {}\n" +
                        "========================================",
                aClass.getSimpleName(),
                Arrays.toString(activeProfiles),
                dbProfile,
                Arrays.toString(activeProfilesWithDb));

        return activeProfilesWithDb;
    }
}
