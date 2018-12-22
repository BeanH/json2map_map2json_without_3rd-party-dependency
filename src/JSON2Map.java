import java.util.*;

public class JSON2Map {

    public static void main(String[] args) {
        /**
         * {
         *     "name":"Lee",
         *     "family":{
         *         "father":"Old Lee",
         *         "mother":"Mrs Lee",
         *         "siblings":[
         *             "Lee One",
         *             "Lee two"
         *         ]
         *     },
         *     "age":10,
         *     "scores":[
         *         110,
         *         22,
         *         88.8
         *     ]
         * }
         * */

        String jsonString = "{\"name\":\"Lee\",\"family\":{\"father\":\"Old Lee\",\"mother\":\"Mrs Lee\",\"siblings\":[\"Lee One\",\"Lee two\"]},\"age\":10,\"scores\":[110,22,88.8]}";
        json2Map(jsonString);
    }


    /**
     * For the details of this algorithm, please read README.md
     */
    public static Map json2Map(String jsonstring) {

        Stack<Map> mapStack = new Stack<>();
        Stack<List> listStack = new Stack<>();
        Stack<Boolean> isListStack = new Stack<>();
        Stack<String> keyStack = new Stack<>();
        String key;
        Object value = null;
        StringBuilder builder = new StringBuilder();
        char[] cs = jsonstring.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            switch (cs[i]) {
                case '{':
                    mapStack.push(new HashMap());
                    isListStack.push(false);
                    break;
                case ':':
                    keyStack.push(builder.toString());
                    builder = new StringBuilder();
                    break;
                case '[':
                    isListStack.push(true);
                    listStack.push(new ArrayList());
                    break;
                case ',':
                    boolean isList = isListStack.peek();
                    if (builder.length() > 0) {  //before this ',', the value is a String
                        value = parseValue(builder.toString());
                    }
                    builder = new StringBuilder();
                    if (!isList) {
                        key = keyStack.pop();
                        mapStack.peek().put(key, value);
                    } else
                        listStack.peek().add(value);
                    break;
                case ']':
                    isListStack.pop();
                    if (builder.length() > 0)
                        value = parseValue(builder.toString());
                    builder = new StringBuilder();
                    listStack.peek().add(value);
                    value = listStack.pop();
                    break;
                case '}':
                    isListStack.pop();
                    key = keyStack.pop();
                    if (builder.length() > 0) {
                        value = parseValue(builder.toString());
                    }
                    builder = new StringBuilder();
                    mapStack.peek().put(key, value);
                    value = mapStack.pop();
                    break;
                default:
                    builder.append(cs[i]);
                    break;
            }
        }
        return (Map) value;
    }


    /**
     * deal with value, String, Integer or Decimal/Float
     */
    static private Object parseValue(String value) {
        Object ret;
        if (value.contains("\"")) {
            ret = value;
        } else {    // is number
            if (value.contains(".")) {   //decimal
                ret = Float.parseFloat(value);
            } else {                     // int
                ret = Integer.parseInt(value);
            }
        }
        return ret;
    }
}
