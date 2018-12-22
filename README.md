## json2map_map2json_without_3rd-party-dependency
This project shows how to convert a JSON string into a Map and vice versa without third party dependency like GSON, fastjson.

## Algorithm
### json2map - JSON2Map.java
The algorithm of converting a JSON string to a map, is similar to converting a string into a binary tree, like "4(2(3)(1))(6(5))".
We could also use Stack to deal with this problem. The difference is the former is much more complex than the latter, because we need to consider lot of cases. However, it would not be that much difficult if we go through the cases one by one.

As we all know, a JSON string would contain Map(Object) with "{}", List with "[]", plain String with "" and Integer/decimal numbers.
We could have 4 Stacks to hold the values:
* mapStack : to hold the Maps
* listStack : to hold the Lists
* isListStack: to hold whether we are currently building a List
* keyStack: to hold the Keys

Here are the cases we need to consider one by one:
'{' : this means a start of a Map, push a new Map into the mapStack

':' : means we have just finished a key, and starting to meet a value, push the key into keyStack

'[' : this means a start of List, push a new List into the listStack, also push a true into the isListStack

',' : this could be two cases, 
      it could be within a List to separate the values(need to put the value into the list);
      or within a Map(need to put the key-value pair into the map).
      We will use the pop the value from isListStack to justify is a List case or Map case.

']' : means the end of a List, pop the list from listStack and assign to value

'}' : means the end of a Map, pop the Map from mapStack and assign to value

otherwise: plain chars, just append to the StringBuilder

Example:
```
Convert the below JSON string into a Map:
    Plain string look:   {"name":"Lee","family":{"father":"Old Lee","mother":"Mrs Lee","siblings":["Lee One","Lee two"]},"age":10,"scores":[110,22,88.8]}
    JSON tree look:
            {
              "name":"Lee",
              "family":{
                  "father":"Old Lee",
                  "mother":"Mrs Lee",
                  "siblings":[
                      "Lee One",
                      "Lee two"
                  ]
              },
              "age":10,
              "scores":[
                  110,
                  22,
                  88.8
              ]
          }        
  
```

### json2map - Map2JSON.java

Traverse the Map with a StringBuffer appending the serialisation of each map entry. 
For each entry, verify its class type and deal and serialize it according to its data type.
For Map and List type, we need to recurse to serialize.

Example:

```
A Map:

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
        
To:
        {
            "birthday":"Thu Jan 12 00:00:00 PST 3893",
            "name":"Lee",
            "family":{
                "mother":"Mum",
                "siblings":[
                    "Brother",
                    "Sister"
                ],
                "father":"Dad"
            },
            "age":[
                33,
                34,
                35
            ]
        }
       
```

* Currently, the code could just convert to plain String.(It is easy to convert into the better looking String just add some "\n");
