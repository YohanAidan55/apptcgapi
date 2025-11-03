@org.springframework.modulith.ApplicationModule(
    displayName = "security",
    allowedDependencies = {"user :: UserApi", "user :: DTO", "user :: UserEntity", "user :: Enums"},
    type = org.springframework.modulith.ApplicationModule.Type.OPEN
)
package com.aidan.apptcg.security;
