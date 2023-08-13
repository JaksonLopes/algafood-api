package com.algaworks.algafood.core.jackson;

import com.algaworks.algafood.api.model.mixin.RestauranteMixin;
import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JacksonMixinModulo extends SimpleModule {

    private static final long serialVersionUID = -1811784812797211208L;

    public JacksonMixinModulo() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
    }
}
