This project shows how to convert a JSON string into a Map and vice versa without third party dependency like GSON, fastjson.
### Algorithm

The algorithm of converting a JSON string to a map, is similar to converting a string into a binary tree, like "4(2(3)(1))(6(5))".
We could also use Stack to deal with this problem. The difference is the former is much more complex than the latter, because we need to consider lot of cases.
However, it would not be that much difficult if we go through the cases one by one.

As we all know, a JSON string would contain Map(Object) with "{}", List with "[]", plain String with "" and Integer/decimal numbers.

We have 4 Stacks to hold the values:
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


