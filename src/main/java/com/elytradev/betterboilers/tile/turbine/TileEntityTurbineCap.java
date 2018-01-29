package com.elytradev.betterboilers.tile.turbine;

import javax.annotation.Nullable;

public class TileEntityTurbineCap extends TileEntityTurbinePart implements ITurbinePart {
    private TileEntityTurbineController controller;

    @Override
    @Nullable
    public TileEntityTurbineController getController() {
        return this.controller;
    }

    @Override
    public void setController(TileEntityTurbineController controller) {
        this.controller = controller;
    }
}