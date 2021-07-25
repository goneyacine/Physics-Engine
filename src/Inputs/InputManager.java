package Inputs;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;

import com.physicsEngine.vectors.Vector2;

public class InputManager {

    private Vector2 mousePosition = new Vector2(0, 0);

    private static InputManager inputManager;
    /** Printable keys */
    // #region
    public static final int SPACE = 32,
APOSTROPHE = 39,
COMMA = 44,
MINUS = 45,
PERIOD = 46,
SLASH = 47,
KEY_0 = 48,
KEY_1 = 49,
KEY_2 = 50,
KEY_3 = 51,
KEY_4 = 52,
KEY_5 = 53,
KEY_6 = 54,
KEY_7 = 55,
KEY_8 = 56,
KEY_9 = 57,
SEMICOLON = 59,
EQUAL = 61,
A = 65,
B = 66,
C = 67,
D = 68,
E = 69,
F = 70,
G = 71,
H = 72,
I = 73,
J = 74,
K = 75,
L = 76,
M = 77,
N = 78,
O = 79,
P = 80,
Q = 81,
R = 82,
S = 83,
T = 84,
U = 85,
V = 86,
W = 87,
X = 88,
Y = 89,
Z = 90,
LEFT_BRACKET = 91,
BACKSLASH = 92,
RIGHT_BRACKET = 93,
GRAVE_ACCENT = 96,
WORLD_1 = 161,
WORLD_2 = 162;
    // #endregion
    /** Function keys. */
    // #region
public static final int ESCAPE = 256,
ENTER = 257,
TAB = 258,
BACKSPACE = 259,
INSERT = 260,
DELETE = 261,
RIGHT = 262,
LEFT = 263,
DOWN = 264,
UP = 265,
PAGE_UP = 266,
PAGE_DOWN = 267,
HOME = 268,
END = 269,
CAPS_LOCK = 280,
SCROLL_LOCK = 281,
NUM_LOCK = 282,
PRINT_SCREEN = 283,
PAUSE = 284,
F1 = 290,
F2 = 291,
F3 = 292,
F4 = 293,
F5 = 294,
F6 = 295,
F7 = 296,
F8 = 297,
F9 = 298,
F10 = 299,
F11 = 300,
F12 = 301,
F13 = 302,
F14 = 303,
F15 = 304,
F16 = 305,
F17 = 306,
F18 = 307,
F19 = 308,
F20 = 309,
F21 = 310,
F22 = 311,
F23 = 312,
F24 = 313,
F25 = 314,
KP_0 = 320,
KP_1 = 321,
KP_2 = 322,
KP_3 = 323,
KP_4 = 324,
KP_5 = 325,
KP_6 = 326,
KP_7 = 327,
KP_8 = 328,
KP_9 = 329,
KP_DECIMAL = 330,
KP_DIVIDE = 331,
KP_MULTIPLY = 332,
KP_SUBTRACT = 333,
KP_ADD = 334,
KP_ENTER = 335,
KP_EQUAL = 336,
LEFT_SHIFT = 340,
LEFT_CONTROL = 341,
LEFT_ALT = 342,
LEFT_SUPER = 343,
RIGHT_SHIFT = 344,
RIGHT_CONTROL = 345,
RIGHT_ALT = 346,
RIGHT_SUPER = 347,
MENU = 348,
LAST = MENU;
    // #endregion
    /** key objects */
    //#region
    private final Key[] keys = { 
new Key(SPACE, "SPACE"),
new Key(APOSTROPHE, "APOSTROPHE"), 
new Key(COMMA, "COMMA"),
new Key(MINUS, "MINUS"),
new Key(PERIOD, "PERIOD"),
new Key(SLASH, "SLASH"),
new Key(KEY_0, "KEY_0"),
new Key(KEY_1, "KEY_1"),
new Key(KEY_2, "KEY_2"),
new Key(KEY_3, "KEY_3"),
new Key(KEY_4, "KEY_4"),
new Key(KEY_5, "KEY_5"),
new Key(KEY_6, "KEY_6"),
new Key(KEY_7, "KEY_7"),
new Key(KEY_8, "KEY_8"),
new Key(KEY_9, "KEY_9"),
new Key(SEMICOLON, "SEMICOLON"),
new Key(EQUAL, "EQUAL"),
new Key(A, "A"),
new Key(B, "B"),
new Key(C, "C"),
new Key(D, "D"),
new Key(E, "E"),
new Key(G, "F"),
new Key(G, "G"),
new Key(H, "H"),
new Key(I, "I"),
new Key(J, "J"),
new Key(K, "K"),
new Key(L, "L"),
new Key(M, "M"),
new Key(N, "N"),
new Key(O, "O"),
new Key(P, "P"),
new Key(Q, "Q"),
new Key(R, "R"),
new Key(S, "S"),
new Key(T, "T"),
new Key(U, "U"),
new Key(V, "V"),
new Key(W, "W"),
new Key(X, "X"),
new Key(Y, "Y"),
new Key(Z, "Z"),
new Key(LEFT_BRACKET, "LEFT_BRACKET"),
new Key(BACKSLASH, "BACKSLASH"),
new Key(RIGHT_BRACKET, "RIGHT_BRACKET"),
new Key(GRAVE_ACCENT, "GRAVE_ACCENT"),
new Key(WORLD_1, "WORLD_1"),
new Key(WORLD_2, "WORLD_2"),

new Key(ESCAPE, "ESCAPE"),
new Key(ENTER, "ENTER"),
new Key(TAB, "TAB"),
new Key(BACKSPACE, "BACKSPACE"),
new Key(INSERT, "INSERT"),
new Key(DELETE, "DELETE"),
new Key(RIGHT, "RIGHT"),
new Key(LEFT, "LEFT"),
new Key(DOWN, "DOWN"),
new Key(UP, "UP"),
new Key(PAGE_UP, "PAGE_UP"),
new Key(PAGE_DOWN, "PAGE_DOWN"),
new Key(HOME, "HOME"),
new Key(END, "END"),
new Key(CAPS_LOCK, "CAPS_LOCK"),
new Key(SCROLL_LOCK, "SCROLL_LOCK"),
new Key(NUM_LOCK, "NUM_LOCK"),
new Key(PRINT_SCREEN, "PRINT_SCREEN"),
new Key(PAUSE, "PAUSE"),
new Key(F1, "F1"),
new Key(F2, "F2"),
new Key(F3, "F3"),
new Key(F4, "F4"),
new Key(F5, "F5"),
new Key(F6, "F6"),
new Key(F7, "F7"),
new Key(F8, "F8"),
new Key(F9, "F9"),
new Key(F10, "F10"),
new Key(F11, "F11"),
new Key(F12, "F12"),
new Key(F13, "F13"),
new Key(F14, "F14"),
new Key(F15, "F15"),
new Key(F16, "F16"),
new Key(F17, "F17"),
new Key(F18, "F18"),
new Key(F19, "F19"),
new Key(F20, "F20"),
new Key(F21, "F21"),
new Key(F22, "F22"),
new Key(F23, "F23"),
new Key(F24, "F24"),
new Key(F25, "F25"),
new Key(KP_0, "KP_0"),
new Key(KP_1, "KP_1"),
new Key(KP_2, "KP_2"),
new Key(KP_3, "KP_3"),
new Key(KP_4, "KP_4"),
new Key(KP_5, "KP_5"),
new Key(KP_6, "KP_6"),
new Key(KP_7, "KP_7"),
new Key(KP_8, "KP_8"),
new Key(KP_9, "KP_9"),
new Key(KP_DECIMAL, "KP_DECIMAL"),
new Key(KP_DIVIDE, "KP_DIVIDE"),
new Key(KP_MULTIPLY, "KP_MULTIPLY"),
new Key(KP_SUBTRACT, "KP_SUBTRACT"),
new Key(KP_ADD, "KP_ADD"),
new Key(KP_ENTER, "KP_ENTER"),
new Key(KP_EQUAL, "KP_EQUAL"),
new Key(LEFT_SHIFT, "LEFT_SHIFT"),
new Key(LEFT_CONTROL, "LEFT_CONTROL"),
new Key(LEFT_ALT, "LEFT_ALT"),
new Key(LEFT_SUPER, "LEFT_SUPER"),
new Key(RIGHT_SHIFT, "RIGHT_SHIFT"),
new Key(RIGHT_CONTROL, "RIGHT_CONTROL"),
new Key(RIGHT_ALT, "RIGHT_ALT"),
new Key(RIGHT_SUPER, "RIGHT_SUPER"),
new Key(MENU, "MENU"),
new Key(MENU, "LAST") };
    // #endregion   
     /** Mouse buttons. See <a target="_blank" href="http://www.glfw.org/docs/latest/input.html#input_mouse_button">mouse button input</a> for how these are used. */
     //#region
     public static final int
     MOUSE_BUTTON_1      = 0,
     MOUSE_BUTTON_2      = 1,
     MOUSE_BUTTON_3      = 2,
     MOUSE_BUTTON_4      = 3,
     MOUSE_BUTTON_5      = 4,
     MOUSE_BUTTON_6      = 5,
     MOUSE_BUTTON_7      = 6,
     MOUSE_BUTTON_8      = 7,
     MOUSE_BUTTON_LAST   = MOUSE_BUTTON_8,
     MOUSE_BUTTON_LEFT   = MOUSE_BUTTON_1,
     MOUSE_BUTTON_RIGHT  = MOUSE_BUTTON_2,
     MOUSE_BUTTON_MIDDLE = MOUSE_BUTTON_3;
     //#endregion
     /** mouse buttons key objects */
     //#region
    private final Key[] mouseButtons = {
        new Key(MOUSE_BUTTON_1,"MOUSE_BUTTON_1"),
        new Key(MOUSE_BUTTON_2,"MOUSE_BUTTON_2"),
        new Key(MOUSE_BUTTON_3,"MOUSE_BUTTON_3"),
        new Key(MOUSE_BUTTON_4,"MOUSE_BUTTON_4"),
        new Key(MOUSE_BUTTON_5,"MOUSE_BUTTON_5"),
        new Key(MOUSE_BUTTON_6,"MOUSE_BUTTON_6"),
        new Key(MOUSE_BUTTON_7,"MOUSE_BUTTON_7"),
        new Key(MOUSE_BUTTON_LAST,"MOUSE_BUTTON_LAST"),
        new Key(MOUSE_BUTTON_LEFT,"MOUSE_BUTTON_LEFT"),
        new Key(MOUSE_BUTTON_RIGHT,"MOUSE_BUTTON_RIGHT"),
        new Key(MOUSE_BUTTON_MIDDLE,"MOUSE_BUTTON_MIDDLE")};
    //#endregion

    public InputManager(long window) {
        //setting up keyboard key event listener
        GLFWKeyCallback keyCallback = null;
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
            setKeyState(key, action);
            }
        });
        //setting up mouse buttons event listener
        GLFWMouseButtonCallback mouseCallback = null;
        glfwSetMouseButtonCallback(window, mouseCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
              setMouseButtonState(button, action);
            }
        });
        //setting up mouse cursor position tracker
        GLFWCursorPosCallback posCallback = null;
        glfwSetCursorPosCallback(window, posCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mousePosition.x = (float) xpos;
                mousePosition.y = (float) ypos;
            }
        });
        inputManager = this;
    }

    /**
     * the InputManager class is responsible for handling inputs, it has info about
     * key stats, mouse buttons stats, cursor position ...
     * 
     * @return the input manager instance used by this game
     */
    public static InputManager inputManager() {
        return inputManager;
    }

    /**
     * 
     * @param key the targer key
     * @return This will either return ture or false. true if the key is pressed
     *         down, false if it’s not.
     */
    public boolean getKeyDown(int keyCode) {
        return getKeyState(keyCode) == 1;
    }

    /**
     * 
     * @param key the target key
     * @return true if a key is continuously pressed & false if it's not
     */
    public boolean getKey(int keyCode) {
        int state = getKeyState(keyCode);
        return  state == 2 || state == 1;
    }
    /**
     * 
     * @param key the targer button
     * @return This will either return ture or false. true if the button is pressed
     *         down, false if it’s not.
     */
    public boolean getMouseDown(int buttonCode){
        return getMouseButtonState(buttonCode) == 1;
    }
        /**
     * 
     * @param key the target button
     * @return true if a mouse button is continuously pressed & false if it's not
     */
    public boolean getMouseButton(int buttonCode){
        int state = getMouseButtonState(buttonCode);
        return  state == 2 || state == 1;
    }

    /**
     * 
     * @return the cursor position in the screen
     */
    public Vector2 mousePosition() {
        return mousePosition;
    }
    /**
     * 
     * @param keyCode the code of the target key
     * @return 0 if the key is not pressed, 1 if it's pressed once , 2 if it's continuously & -1 if the key code is worng
     */
    public int getKeyState(int keyCode){
        //this function uses binary search to optimize the search of the key
            int l = 0, r = keys.length - 1;
            while (l <= r) {
                int m = l + (r - l) / 2;
      
                if (keys[m].keyCode == keyCode)
                    return keys[m].state;
      
                if (keys[m].keyCode < keyCode)
                    l = m + 1;
      
                else
                    r = m - 1;
            }
      
            // if we reach here, then element was
            // not present
            return -1;
    }
     /**
     * 
     * @param keyCode the code of the target key
     * @return 1 if the key state is set correctly & -1 if it's not 
     */
    private int setKeyState(int keyCode,int state){
       //this function uses binary search to optimize the search of the key
       int l = 0, r = keys.length - 1;
       while (l <= r) {
           int m = l + (r - l) / 2;
 
           if (keys[m].keyCode == keyCode){
               keys[m].state = state;
               return 1;
           }
 
           if (keys[m].keyCode < keyCode)
               l = m + 1;
 
           else
               r = m - 1;
       }
 
       // if we reach here, then element was
       // not present
       return -1;
        }
    /**
     * 
     * @param keyCode the code of the target mouse button
     * @return 0 if the button is not pressed, 1 if it's pressed once , 2 if it's continuously & -1 if the button code is worng
     */
    public int getMouseButtonState(int buttonCode){

    for(Key key : mouseButtons){

        if(key.keyCode == buttonCode)
        return key.state;

    }
    return -1;
    }
      /**
     * 
     * @param keyCode the code of the target mouse button
     * @return 1 if the button state is set correctly & -1 if it's not 
     */
    public int setMouseButtonState(int buttonCode,int state){

        for(Key key : mouseButtons){
    
            if(key.keyCode == buttonCode){
            key.state = state;
            return 1;
            }
    
        }
        return -1;
        }



    /** this class contains info about a specific key like state, name & glfw code` */
    public class Key {
        private int keyCode;
        private String name;
        // 0 when the button is not pressed, 1 when it's pressed & 2 when it's repeated
        private int state = 0;

        public Key(int keyCode, String name) {
            this.keyCode = keyCode;
            this.name = name;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int keyCode() {
            return this.keyCode;
        }

        public String name() {
            return this.name;
        }
    }
}
