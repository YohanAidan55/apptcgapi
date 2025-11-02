@org.springframework.modulith.ApplicationModule(
    displayName = "security",
    allowedDependencies = {"user :: UserApi", "user :: DTO"},
    type = org.springframework.modulith.ApplicationModule.Type.OPEN
)
package com.aidan.apptcg.security;
