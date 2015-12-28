package cz.trigon.bicepsrendererapi.gl.shader;

import android.opengl.GLES20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.trigon.bicepsrendererapi.game.Surface;
import cz.trigon.bicepsrendererapi.gl.interfaces.shaders.IShader;
import cz.trigon.bicepsrendererapi.gl.interfaces.shaders.IShaderManager;

// TODO Proper exception handling
public class ShaderManager implements IShaderManager {

    private Surface surface;

    private Map<String, Shader> shaders;

    public ShaderManager(Surface surface) {
        this.surface = surface;
        this.shaders = new HashMap<>();
    }

    @Override
    public void loadShader(String name, String vertex, String fragment) {
        int progId = 0, vertId = 0, fragId = 0; // Init with safe values

        ShaderFile vert = readShader(vertex);
        ShaderFile frag = readShader(fragment);

        vertId = compileShader(vertex, GLES20.GL_VERTEX_SHADER);
        fragId = compileShader(fragment, GLES20.GL_FRAGMENT_SHADER);

        progId = attachLinkValidateShaders(vertId, fragId);

        // Delete the vert/frag shaders
        GLES20.glDeleteProgram(vertId);
        GLES20.glDeleteProgram(fragId);

        // Get maps of all the uniforms/attributes
        Map<String, Integer> uniforms = new HashMap<>();
        Map<String, Integer> attributes = new HashMap<>();

        for (String s : vert.getUniforms()) {
            int loc = GLES20.glGetUniformLocation(progId, s);
            uniforms.put(s, loc);
        }

        for (String s : frag.getUniforms()) {
            int loc = GLES20.glGetUniformLocation(progId, s);
            uniforms.put(s, loc);
        }

        for (String s : vert.getAttributes()) {
            int loc = GLES20.glGetAttribLocation(progId, s);
            attributes.put(s, loc);
        }

        ShaderVariables variables = new ShaderVariables(uniforms, attributes);
        Shader shader = new Shader(this, name, progId, variables);
        this.shaders.put(name, shader);
    }

    @Override
    public IShader getShader(String name) {
        return this.shaders.get(name);
    }

    @Override
    public void deleteShader(String name) {
        Shader shader = this.shaders.get(name);
        if (shader != null) {
            GLES20.glDeleteProgram(this.shaders.get(name).getId());
        }

        this.shaders.remove(name);
    }

    private ShaderFile readShader(String path) {

        InputStream in = null;
        try {
            in = this.surface.getContent().openStream(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder source = new StringBuilder();
        BufferedReader reader;

        List<String> uniforms = new ArrayList<>();
        List<String> attributes = new ArrayList<>();

        try {
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.startsWith("uniform ")) {
                    String name = line.split(" ")[2];
                    name = name.replace(";", "");

                    if (name.endsWith("]")) {
                        int size = Integer.parseInt(name.substring(0, name.lastIndexOf("]")).substring(name.lastIndexOf("[")+1));
                        name = name.substring(0, name.lastIndexOf("["));
                        for (int i = 0; i < size; i++) {
                            uniforms.add(name + "[" + i + "]");
                        }

                    } else {
                        uniforms.add(name);
                    }

                }

                if (line.startsWith("attribute ")) {
                    String name = line.split(" ")[2];
                    name = name.replace(";", "");
                    attributes.add(name);
                }

                source.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ShaderFile sf = new ShaderFile(source.toString(), uniforms, attributes);

        return sf;
    }

    private int compileShader(String shader, int type) {
        int id = 0;
        try {
            id = GLES20.glCreateShader(type);
            if (id == 0) return 0; // If type/ogl gets fucked up

            GLES20.glShaderSource(id, shader);
            GLES20.glCompileShader(id);

            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(id, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            if (compileStatus[0] == GLES20.GL_FALSE) {
                throw new RuntimeException("Error compiling shader:\n"
                        + GLES20.glGetShaderInfoLog(id));
            }

            return id;
        } catch (Exception e) {
            GLES20.glDeleteShader(id);
            e.printStackTrace();
            return 0;
        }
    }

    private int attachLinkValidateShaders(int vert, int frag) {
        int prog = GLES20.glCreateProgram();

        try {
            GLES20.glAttachShader(prog, vert);
            GLES20.glAttachShader(prog, frag);

            final int[] linkStatus = new int[1];
            GLES20.glLinkProgram(prog);
            GLES20.glGetProgramiv(prog, GLES20.GL_LINK_STATUS, linkStatus, 0);

            if (linkStatus[0] == GLES20.GL_FALSE) {
                throw new RuntimeException("Error linking shader program:\n"
                        + GLES20.glGetProgramInfoLog(prog));
            }

            final int[] validateStatus = new int[1];
            GLES20.glValidateProgram(prog);
            GLES20.glGetProgramiv(prog, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);

            if (validateStatus[0] == GLES20.GL_FALSE) {
                throw new RuntimeException("Error validating shader program:\n"
                        + GLES20.glGetProgramInfoLog(prog));
            }

        } catch (Exception e) {
            GLES20.glDeleteProgram(prog);
            e.printStackTrace();
            return 0;
        }

        return prog;
    }
}