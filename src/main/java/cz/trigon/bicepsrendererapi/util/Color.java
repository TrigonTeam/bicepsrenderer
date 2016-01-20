package cz.trigon.bicepsrendererapi.util;

public class Color extends Vector4 {

    public static final float RED = Color.packColor(android.graphics.Color.RED);
    public static final float GREEN = Color.packColor(android.graphics.Color.GREEN);
    public static final float BLUE = Color.packColor(android.graphics.Color.BLUE);
    public static final float BLACK = Color.packColor(android.graphics.Color.BLACK);
    public static final float WHITE = Color.packColor(android.graphics.Color.WHITE);
    public static final float CYAN = Color.packColor(android.graphics.Color.CYAN);
    public static final float GRAY = Color.packColor(android.graphics.Color.GRAY);
    public static final float LIGHT_GRAY = Color.packColor(android.graphics.Color.LTGRAY);
    public static final float DARK_GRAY = Color.packColor(android.graphics.Color.DKGRAY);
    public static final float MAGENTA = Color.packColor(android.graphics.Color.MAGENTA);
    public static final float YELLOW = Color.packColor(android.graphics.Color.YELLOW);
    public static final float TRANSPARENT = Color.packColor(android.graphics.Color.TRANSPARENT);

    public static float packColor(int r, int g, int b, int a) {
        return Float.intBitsToFloat((a << 24) | (b << 16) | (g << 8) | r);
    }

    public static float packColor(int color) {
        return Color.packColor(android.graphics.Color.red(color), android.graphics.Color.green(color),
                android.graphics.Color.blue(color), android.graphics.Color.alpha(color));
    }

    public static int r(float color) {
        return Float.floatToIntBits(color) & 0xFF;
    }

    public static int g(float color) {
        return (Float.floatToIntBits(color) >> 8) & 0xFF;
    }

    public static int b(float color) {
        return (Float.floatToIntBits(color) >> 16) & 0xFF;
    }

    public static int a(float color) {
        return (Float.floatToIntBits(color) >> 24) & 0xFF;
    }

    private int r, g, b, a;
    private float value;
    private int valueInt;
    private String str;

    public Color() {
        this(0, 0, 0, 0);
    }

    public Color(Vector4 toCopy) {
        this(toCopy.x, toCopy.y, toCopy.z, toCopy.w);
    }

    public Color(int color) {
        this(android.graphics.Color.red(color), android.graphics.Color.green(color),
                android.graphics.Color.blue(color), android.graphics.Color.alpha(color));
    }

    public Color(float color) {
        this(Float.floatToIntBits(color));
    }

    public Color(float r, float g, float b, float a) {
        super(r, g, b, a);

        this.r = (int) (r * 255);
        this.g = (int) (g * 255);
        this.b = (int) (b * 255);
        this.a = (int) (a * 255);

        this.valueInt = (this.a << 24) | (this.b << 16) | (this.g << 8) | this.r;
        this.value = Float.intBitsToFloat(this.valueInt);
        this.str = "R: " + this.r + ", G: " + this.g + ", B: " + this.b + ", A: " + this.a;
    }

    public Color(int r, int g, int b, int a) {
        super(r / 255f, g / 255f, b / 255f, a / 255f);

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;

        this.valueInt = (this.a << 24) | (this.b << 16) | (this.g << 8) | this.r;
        this.value = Float.intBitsToFloat(this.valueInt);
        this.str = "R: " + this.r + ", G: " + this.g + ", B: " + this.b + ", A: " + this.a;
    }

    public Color(float r, float g, float b) {
        this(r, g, b, 1f);
    }

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public float val() {
        return this.value;
    }

    public int r() {
        return this.r;
    }

    public int g() {
        return this.g;
    }

    public int b() {
        return this.b;
    }

    public int a() {
        return this.a;
    }

    public int vali() {
        return this.valueInt;
    }

    @Override
    public String toString() {
        return this.str;
    }

}
