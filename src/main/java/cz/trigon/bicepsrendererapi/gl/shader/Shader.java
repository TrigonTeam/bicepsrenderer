package cz.trigon.bicepsrendererapi.gl.shader;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.FloatBuffer;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.gl.interfaces.matrices.IMatrix;
import cz.trigon.bicepsrendererapi.gl.interfaces.shaders.IShader;

public class Shader implements IShader{

    private ShaderManager mgr;

    private String name;
    private int id;
    private ShaderVariables variables;

    public Shader(ShaderManager mgr, String name, int id, ShaderVariables variables) {
        this.mgr = mgr;
        this.name = name;
        this.id = id;
        this.variables = variables;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void bind() {
        this.mgr.bind(this);
    }

    @Override
    public void setUniform1f(String name, float val1) {
        if (hasUniform(name)) {
            GLES20.glUniform1f(this.variables.getUniform(name), val1);
        } else {
            Log.e(Surface.LDTAG, "Set nonexistent uniform float \"" + name + "\" in program " + this.name);
        }
    }

    @Override
    public void setUniform2f(String name, float val1, float val2) {
        if (hasUniform(name)) {
            GLES20.glUniform2f(this.variables.getUniform(name), val1, val2);
        } else {
            Log.e(Surface.LDTAG, "Set nonexistent uniform vec2f \"" + name + "\" in program " + this.name);
        }
    }

    @Override
    public void setUniform3f(String name, float val1, float val2, float val3) {
        if (hasUniform(name)) {
            GLES20.glUniform3f(this.variables.getUniform(name), val1, val2, val3);
        } else {
            Log.e(Surface.LDTAG, "Set nonexistent uniform vec3f \"" + name + "\" in program " + this.name);
        }
    }

    @Override
    public void setUniform4f(String name, float val1, float val2, float val3, float val4) {
        if (hasUniform(name)) {
            GLES20.glUniform4f(this.variables.getUniform(name), val1, val2, val3, val4);
        } else {
            Log.e(Surface.LDTAG, "Set nonexistent uniform vec4f \"" + name + "\" in program " + this.name);
        }
    }

    @Override
    public void setUniform1i(String name, int val1) {
        if (hasUniform(name)) {
            GLES20.glUniform1i(this.variables.getUniform(name), val1);
        } else {
            Log.e(Surface.LDTAG, "Set nonexistent uniform int \"" + name + "\" in program " + this.name);
        }
    }

    @Override
    public void setUniform2i(String name, int val1, int val2) {
        if (hasUniform(name)) {
            GLES20.glUniform2i(this.variables.getUniform(name), val1, val2);
        } else {
            Log.e(Surface.LDTAG, "Set nonexistent uniform vec2i \"" + name + "\" in program " + this.name);
        }
    }

    @Override
    public void setUniform3i(String name, int val1, int val2, int val3) {
        if (hasUniform(name)) {
            GLES20.glUniform3i(this.variables.getUniform(name), val1, val2, val3);
        } else {
            Log.e(Surface.LDTAG, "Set nonexistent uniform vec3i \"" + name + "\" in program " + this.name);
        }
    }

    @Override
    public void setUniform4i(String name, int val1, int val2, int val3, int val4) {
        if (hasUniform(name)) {
            GLES20.glUniform4i(this.variables.getUniform(name), val1, val2, val3, val4);
        } else {
            Log.e(Surface.LDTAG, "Set nonexistent uniform vec4i \"" + name + "\" in program " + this.name);
        }
    }

    @Override
    public void setUniformMatrix2f(String name, IMatrix matrix) {
        if (hasUniform(name)) {
            GLES20.glUniformMatrix2fv(this.variables.getUniform(name), 1, false, matrix.getData().asFloatBuffer());
        } else {
            Log.e(Surface.LDTAG, "Set nonexistent uniform mat2f \"" + name + "\" in program " + this.name);
        }
    }

    @Override
    public void setUniformMatrix3f(String name, IMatrix matrix) {
        if (hasUniform(name)) {
            GLES20.glUniformMatrix3fv(this.variables.getUniform(name), 1, false, matrix.getData().asFloatBuffer());
        } else {
            Log.e(Surface.LDTAG, "Set nonexistent uniform mat3f \"" + name + "\" in program " + this.name);
        }
    }

    @Override
    public void setUniformMatrix4f(String name, IMatrix matrix) {
        if (hasUniform(name)) {
            GLES20.glUniformMatrix4fv(this.variables.getUniform(name), 1, false, matrix.getData().asFloatBuffer());
        } else {
            Log.e(Surface.LDTAG, "Set nonexistent uniform mat4f \"" + name + "\" in program " + this.name);
        }
    }

    @Override
    public boolean hasUniform(String name) {
        return this.variables.hasUniform(name);
    }

    @Override
    public boolean hasAttribute(String name) {
        return this.variables.hasAttribute(name);
    }

    @Override
    public int getUniformLocation(String name) {
        return this.variables.getUniform(name);
    }

    @Override
    public int getAttribLocation(String name) {
        return this.variables.getAttribute(name);
    }

    @Override
    public void deleteShader() {
        this.mgr.deleteShader(this.name);
    }

}
