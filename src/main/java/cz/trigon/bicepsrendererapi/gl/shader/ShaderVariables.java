package cz.trigon.bicepsrendererapi.gl.shader;

import java.util.Map;

public class ShaderVariables {

    private Map<String, Integer> uniforms;
    private Map<String, Integer> attributes;

    public ShaderVariables(Map<String, Integer> uniforms, Map<String, Integer> attributes) {
        this.uniforms = uniforms;
        this.attributes = attributes;
    }

    public boolean hasUniform(String name) {
        return this.uniforms.containsKey(name);
    }

    public boolean hasAttribute(String name) {
        return this.attributes.containsKey(name);
    }

    public int getUniform(String name) {
        return this.uniforms.get(name);
    }

    public int getAttribute(String name) {
        return this.attributes.get(name);
    }

}
