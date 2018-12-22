import java.util.*;

public class Map2JSON {

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("name", "Lee");
        map.put("age", Arrays.asList(new Integer[]{33, 34, 35}));
        Map family = new HashMap();
        family.put("father", "Dad");
        family.put("mother", "Mum");
        family.put("siblings", Arrays.asList(new String[]{"Brother", "Sister"}));
        map.put("family", family);
        map.put("birthday", new Date(1992, 12, 12));
        System.out.println(toJson(map));

        //System.out.println(toJson(Arrays.asList(new String[]{"Brother", "Sister"})));

        /**
         * {
         *     "birthday":"Thu Jan 12 00:00:00 PST 3893",
         *     "name":"Lee",
         *     "family":{
         *         "mother":"Mum",
         *         "siblings":[
         *             "Brother",
         *             "Sister"
         *         ],
         *         "father":"Dad"
         *     },
         *     "age":[
         *         33,
         *         34,
         *         35
         *     ]
         * }
         * */
    }

    private static String toJson(Object obj) {
        if (obj instanceof String) {
            return obj.toString();
        } else {
            StringBuffer xmlBuffer = new StringBuffer();
            if (obj instanceof List<?>) {
                xmlBuffer.append("[");
                List<?> list = (List<?>) obj;
                for (int i = 0; i < list.size(); i++) {
                    Object fieldValue = list.get(i);
                    addValueToBuffer(xmlBuffer, fieldValue);
                    if (i < list.size() - 1) {
                        xmlBuffer.append(",");
                    }
                }
                xmlBuffer.append("]");
            } else {
                addObjectToBuffer(xmlBuffer, (Map) obj);
            }
            return xmlBuffer.toString();
        }
    }

    private static void addObjectToBuffer(StringBuffer xmlBuffer, Map map) {
        xmlBuffer.append("{");
        for (Object key : map.keySet()) {
            String fieldName = (String) key;
            Object fieldValue = map.get(key);
            if (null != fieldValue) {
                xmlBuffer.append("\"");
                xmlBuffer.append(fieldName);
                xmlBuffer.append("\":");
                addValueToBuffer(xmlBuffer, fieldValue);
                xmlBuffer.append(",");
            }
        }
        xmlBuffer.deleteCharAt(xmlBuffer.length() - 1);
        xmlBuffer.append("}");
    }


    private static void addListToBuffer(StringBuffer xmlBuffer, Object fieldvalue) {
        xmlBuffer.append("[");
        List<?> v_list = (List<?>) fieldvalue;
        for (int i = 0; i < v_list.size(); i++) {
            if (null != v_list.get(i)) {
                Object fieldValue = v_list.get(i);
                addValueToBuffer(xmlBuffer, fieldValue);
            }
            if (i < v_list.size() - 1) {
                xmlBuffer.append(",");
            }
        }
        xmlBuffer.append("]");
    }

    private static void addValueToBuffer(StringBuffer buffer, Object fieldValue) {
        if (fieldValue instanceof Integer
                || fieldValue instanceof Double
                || fieldValue instanceof Long
                || fieldValue instanceof Boolean) {
            buffer.append(fieldValue.toString());
        } else if (fieldValue instanceof String) {
            buffer.append("\"");
            String str = fieldValue.toString();
            buffer.append(str);
            buffer.append("\"");
        } else if (fieldValue instanceof List<?>) {
            addListToBuffer(buffer, fieldValue);
        } else if (fieldValue instanceof Map) {
            addObjectToBuffer(buffer, (Map) fieldValue);
        } else {
            buffer.append("\"");
            buffer.append(fieldValue.toString());
            buffer.append("\"");
        }
    }
}
