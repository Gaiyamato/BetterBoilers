package com.elytradev.betterboilers.tile.boiler;

import javax.annotation.Nullable;

public class TileEntityFireboxBlock extends TileEntityBoilerPart implements IBoilerPart {
    private TileEntityBoilerController controller;

    @Override
    @Nullable
    public TileEntityBoilerController getController() {
        return this.controller;
    }

    @Override
    public void setController(TileEntityBoilerController controller) {
        this.controller = controller;
    }
}