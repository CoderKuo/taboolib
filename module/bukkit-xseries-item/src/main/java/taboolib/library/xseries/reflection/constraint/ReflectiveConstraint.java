package taboolib.library.xseries.reflection.constraint;

import taboolib.library.xseries.reflection.ReflectiveHandle;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface ReflectiveConstraint {
    String category();

    String name();

    boolean appliesTo(ReflectiveHandle<?> handle);
}
