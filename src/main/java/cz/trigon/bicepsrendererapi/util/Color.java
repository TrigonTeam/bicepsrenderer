package cz.trigon.bicepsrendererapi.util;

public class Color extends Vector4 {

    public static final Color RED = new Color(android.graphics.Color.RED);
    public static final Color GREEN = new Color(android.graphics.Color.GREEN);
    public static final Color BLUE = new Color(android.graphics.Color.BLUE);
    public static final Color BLACK = new Color(android.graphics.Color.BLACK);
    public static final Color WHITE = new Color(android.graphics.Color.WHITE);
    public static final Color CYAN = new Color(android.graphics.Color.CYAN);
    public static final Color GRAY = new Color(android.graphics.Color.GRAY);
    public static final Color LIGHT_GRAY = new Color(android.graphics.Color.LTGRAY);
    public static final Color DARK_GRAY = new Color(android.graphics.Color.DKGRAY);
    public static final Color MAGENTA = new Color(android.graphics.Color.MAGENTA);
    public static final Color YELLOW = new Color(android.graphics.Color.YELLOW);
    public static final Color TRANSPARENT = new Color(android.graphics.Color.TRANSPARENT);

    public static float packColor(int r, int g, int b, int a) {
        return Float.intBitsToFloat(((r & 0xFF) << 24) | ((g & 0xFF) << 16) | ((b & 0xFF) << 8)  | (a & 0xFF));
    }

    private int r, g, b, a;
    private float value;
    private int valueInt;

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

    public Color(float r, float g, float b, float a) {
        super(r, g, b, a);

        this.r = (int) (r * 255);
        this.g = (int) (g * 255);
        this.b = (int) (b * 255);
        this.a = (int) (a * 255);

        this.valueInt = ((this.r & 0xFF) << 24) | ((this.g & 0xFF) << 16) |
                ((this.b & 0xFF) << 8) | (this.a & 0xFF);
        this.value = Float.intBitsToFloat(this.valueInt);
    }

    public Color(int r, int g, int b, int a) {
        super(r / 255f, g / 255f, b / 255f, a / 255f);

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;

        this.valueInt = ((r & 0xFF) << 24) | ((g & 0xFF) << 16) |
                ((b & 0xFF) << 8) | (a & 0xFF);
        this.value = Float.intBitsToFloat(this.valueInt);
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

}
