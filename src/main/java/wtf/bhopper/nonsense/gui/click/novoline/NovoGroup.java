package wtf.bhopper.nonsense.gui.click.novoline;

import wtf.bhopper.nonsense.module.property.Property;
import wtf.bhopper.nonsense.module.property.impl.*;
import wtf.bhopper.nonsense.util.render.NVGHelper;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.nanovg.NanoVG.*;

public class NovoGroup extends NovoComponent {

    private final GroupProperty property;

    private boolean expanded = false;
    private final List<NovoComponent> components = new ArrayList<>();

    public NovoGroup(NovoPanel panel, GroupProperty property, int indention) {
        super(panel, indention);
        this.property = property;

        for (Property<?> p : this.property.getProperties()) {
            if (p instanceof BooleanProperty booleanProperty) components.add(new NovoSwitch(this.panel, booleanProperty, this.indention + 1));
            else if (p instanceof NumberProperty numberProperty) components.add(new NovoSlider(this.panel, numberProperty, this.indention + 1));
            else if (p instanceof EnumProperty<?> enumProperty) components.add(new NovoSelector(this.panel, enumProperty, this.indention + 1));
            else if (p instanceof ColorProperty colorProperty) components.add(new NovoColorPicker(this.panel, colorProperty, this.indention + 1));
            else if (p instanceof GroupProperty groupProperty) components.add(new NovoGroup(this.panel, groupProperty, this.indention + 1));
            else if (p instanceof ButtonProperty buttonProperty) components.add(new NovoButton(this.panel, buttonProperty, this.indention + 1));
        }

    }


    @Override
    public void draw(float mouseX, float mouseY, float delta) {

        if (!this.property.isAvailable()) {
            this.expanded = false;
            return;
        }

        if (this.mouseIntersecting(mouseX, mouseY, MOD_HEIGHT)) {
            this.setToolTip(this.property.description);
        }

        this.yOff = this.panel.yOff;

        this.drawBackground(MOD_HEIGHT, this.expanded ? this.indentColor1(BG_COLOR) : this.indentColor(BG_COLOR));
        NVGHelper.fontSize(16.0F);
        NVGHelper.textAlign(NVG_ALIGN_CENTER | NVG_ALIGN_MIDDLE);
        NVGHelper.drawText(this.property.displayName + "...", WIDTH / 2.0F, yOff + MOD_HEIGHT / 2.0F + 1.0F, 0xFFFFFFFF);

        this.panel.yOff += MOD_HEIGHT;

        if (this.expanded) {
            for (NovoComponent component : this.components) {
                component.draw(mouseX, mouseY, delta);
            }
        }

    }

    @Override
    public void mouseClick(float mouseX, float mouseY, int button) {

        if (!this.property.isAvailable()) {
            return;
        }

        if (this.mouseIntersecting(mouseX, mouseY, MOD_HEIGHT)) {
            if (button == 0) {
                this.expanded = !this.expanded;
            }
        } else if (this.expanded) {
            for (NovoComponent component : this.components) {
                component.mouseClick(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void mouseRelease(float mouseX, float mouseY, int button) {
        for (NovoComponent component : this.components) {
            component.mouseRelease(mouseX, mouseY, button);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        for (NovoComponent component : this.components) {
            component.keyTyped(typedChar, keyCode);
        }
    }
}