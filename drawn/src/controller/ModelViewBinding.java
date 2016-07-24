package controller;

import java.util.HashSet;
import java.util.Set;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;

/**
 * Bind a property to an expression which may in turn contain properties that depend on it. For example if the model
 * shape x changes, the ellipse.centerX depends on the shape x. If centerX changes, the model shape x depends on
 * centerX. To avoid circular binding, we make sure that properties are propagated in a single direction (from model to
 * view or from view to model)
 * 
 *
 */
public class ModelViewBinding {

    Set<DoubleBinding> bindings = new HashSet<DoubleBinding>();

    DoubleBinding propagateFrom = null;

    boolean propagateDirection = false;

    void bind(final DoubleProperty prop, final DoubleBinding expr) {

        // data propagates from model to view if the property to be set is from a JavaFX Shape
        final boolean modelToView = prop.getBean() != null && prop.getBean() instanceof javafx.scene.shape.Shape;

        // expressions must be hanged in a set, otherwise they will get garbage collected and their listeners will take
        // no effect!
        bindings.add(expr);

        // initially the model is populated while the UI nodes are not, so we copy the value
        if (modelToView)
            prop.set(expr.doubleValue());

        // then we listen to expression changes and update the property
        expr.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                // if propagation takes place in the opposite direction, we ignore
                if (propagateFrom != null && propagateDirection != modelToView)
                    return;

                if (propagateFrom == null) {
                    // propagation starts
                    propagateFrom = expr;
                    propagateDirection = modelToView;
                }

                // propagate the change: set the property. This will generate more calls to this listener, all of which
                // should be ignored as we are only propagating in a single direction
                prop.set(expr.doubleValue());

                if (propagateFrom == expr)
                    // we are back to the initial expression, therefore propagation finished
                    propagateFrom = null;
            }
        });
    }

}
