package taboolib.library.xseries.reflection.minecraft;

import taboolib.library.xseries.reflection.ReflectiveNamespace;
import taboolib.library.xseries.reflection.jvm.classes.DynamicClassHandle;
import taboolib.library.xseries.reflection.jvm.classes.PackageHandle;
import org.intellij.lang.annotations.Language;
import org.intellij.lang.annotations.Pattern;

/**
 * A specialized type of {@link DynamicClassHandle} which currently doesn't
 * have any extra functionalities.
 */
public class MinecraftClassHandle extends DynamicClassHandle {
    public MinecraftClassHandle(ReflectiveNamespace namespace) {
        super(namespace);
    }

    public MinecraftClassHandle inPackage(MinecraftPackage minecraftPackage) {
        super.inPackage(minecraftPackage);
        return this;
    }

    public MinecraftClassHandle inPackage(MinecraftPackage minecraftPackage, @Pattern(PackageHandle.JAVA_PACKAGE_PATTERN) String packageName) {
        super.inPackage(minecraftPackage, packageName);
        return this;
    }

    @Override
    public MinecraftClassHandle inner(@Language("Java") String declaration) {
        return inner(namespace.ofMinecraft(declaration));
    }

    public MinecraftClassHandle named(@Pattern(PackageHandle.JAVA_IDENTIFIER_PATTERN) String... clazzNames) {
        super.named(clazzNames);
        return this;
    }

    public MinecraftClassHandle map(MinecraftMapping mapping, @Pattern(PackageHandle.JAVA_IDENTIFIER_PATTERN) String className) {
        this.classNames.add(className);
        return this;
    }

    @Override
    public MinecraftClassHandle clone() {
        MinecraftClassHandle handle = new MinecraftClassHandle(namespace);
        handle.array = this.array;
        handle.parent = this.parent;
        handle.packageName = this.packageName;
        handle.classNames.addAll(this.classNames);
        return handle;
    }
}
