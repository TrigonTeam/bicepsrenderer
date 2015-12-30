attribute vec2 vPos;
attribute vec4 vColor;

varying vec4 fColor;

void main() {
    gl_Position = vec4(vPos, 0.0, 1.0);
    fColor = vColor;
}