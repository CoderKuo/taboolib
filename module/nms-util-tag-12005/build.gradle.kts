import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    compileOnly(project(":common"))
    compileOnly(project(":common-util"))
    compileOnly(project(":common-legacy-api"))
    compileOnly(project(":common-platform-api"))
    compileOnly(project(":module:nms"))
    compileOnly(project(":module:bukkit-util"))
    compileOnly(project(":module:bukkit-xseries"))
    compileOnly(project(":module:nms-util-tag"))
    compileOnly(project(":platform:platform-bukkit"))
    // 服务端
    compileOnly("ink.ptms:nms-all:1.0.0")
    compileOnly("ink.ptms.core:v12005:12005:mapped")
}

tasks {
    withType<ShadowJar> {
        archiveClassifier.set("")
        relocate("org.tabooproject", "taboolib.library")
    }
    build {
        dependsOn(shadowJar)
    }
}