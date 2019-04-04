package com.cqjtu.bysj.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Route {
    private String path;
    private String component;
    private String redirect;
    private Map<String, String> meta;
    private List<Route> children;

    public Route() {
    }

    public Route(String path, String redirect) {
        this.path = path;
        this.redirect = redirect;
    }


    public Route(String path, String component, Map<String, String> meta, List<Route> children) {
        this.path = path;
        this.component = component;
        this.meta = meta;
        this.children = children;
    }

    public Route(String path, String component, String redirect, Map<String, String> meta, List<Route> children) {
        this.path = path;
        this.component = component;
        this.redirect = redirect;
        this.meta = meta;
        this.children = children;
    }

    public Route(String path, String component, Map<String, String> meta) {
        this.path = path;
        this.component = component;
        this.meta = meta;
    }
}
