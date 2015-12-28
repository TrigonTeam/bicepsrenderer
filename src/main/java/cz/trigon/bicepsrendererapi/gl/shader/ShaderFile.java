package cz.trigon.bicepsrendererapi.gl.shader;

import java.util.List;

public class ShaderFile {

    private String code;
    private List<String> uniforms;
    private List<String> attributes;

    public ShaderFile(String code, List<String> uniforms,List<String> attributes) {
        this.code = code;
        this.uniforms = uniforms;
        this.attributes = attributes;
    }

    public String getCode() {
        return code;
    }

    public List<String> getUniforms() {
        return uniforms;
    }

    public List<String> getAttributes() {
        return attributes;
    }

}
