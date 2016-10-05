package com.mjm.solid.solidmanager.engine;

import java.util.List;

public abstract class MediaProviderFactory<T> {
    public abstract List<T> getList();
}