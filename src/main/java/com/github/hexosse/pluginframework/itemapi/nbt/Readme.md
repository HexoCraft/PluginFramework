# NBT Item API

Add custom NBT tags to Items without NMS!

The API allows you to add custom NBT tags to Bukkit Itemstacks.
It completely uses reflections to interact with NMS code, so it shouldn't be version/update independent. Supported are Strings, Integer, Boolean and Double.

### How to use the API
````Java
// Create a NBTItem
NBTItem nbti = new NBTItem(item);

// Set
nbti.setString("Stringtest", "Teststring");
nbti.setInteger("Inttest", 42);
nbti.setDouble("Doubletest", 1.5d);
nbti.setBoolean("Booleantest", true);

// Get
nbti.getString("Stringtest");
nbti.getInteger("Inttest");
nbti.getDouble("Doubletest");
nbti.getBoolean("Booleantest");

// And finally get back the Bukkit Itemstack
nbti.getItem();
````


(Original code from tr7zw : https://github.com/tr7zw/Item-NBT-API)
