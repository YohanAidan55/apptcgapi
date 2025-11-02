@org.springframework.modulith.ApplicationModule(
        displayName = "user",
        allowedDependencies = {"security", "notification :: NotificationApi", "exception"}
)
package com.aidan.apptcg.user;
