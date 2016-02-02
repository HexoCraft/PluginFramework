### HiddenStringUtils

##### Sources
https://gist.github.com/filoghost/f53ecb7b014c40b66bdc

##### Example
https://bukkit.org/threads/storing-hidden-data-in-itemstacks-using-colors-persistent-no-nms.319970/

````
// We want to store a JSON stat into an iron sword: "{MobsKilled: 0}"
// When we kill 50 mobs with that sword, its name becomes "Mob Slayer".

// We first create the item.
ItemStack ironSword = new ItemStack(Material.IRON_SWORD);

// Create the hidden message.
List<String> lore = new ArrayList<String>();
lore.add(HiddenStringUtils.encodeString("{MobsKilled: 0}"));

// Apply the ItemMeta.
ItemMeta meta = ironSword.getItemMeta();
meta.setLore(lore);
ironSword.setItemMeta(meta);
````

````
@EventHandler
public void onEntityDeath(EntityDeathEvent event) {
     Player killer = event.getEntity().getKiller();

     if (killer == null || !killer.isOnline()) {
          return;
     }

     ItemStack inHand = killer.getItemInHand();

     // Check if the mob was killed with an iron sword.
     if (inHand == null || inHand.getType() != Material.IRON_SWORD) {
          return;
     }

     ItemMeta meta = inHand.getItemMeta();
     List<String> lore = meta.getLore();

     // Check if the mob was killed with OUR sword, scanning the lore for a hidden message.
     if (lore != null && lore.size() > 0 && HiddenStringUtils.hasHiddenString(lore.get(0))) {
          // At this point we extract the json data from the item.
          String json = HiddenStringUtils.extractHiddenString(lore.get(0));

          // Code to edit the json message, I will not explain this.
          ...

          // The string is now "{MobsKilled: 1}" if the previous was "{MobsKilled: 0}", we replace the old json.
          lore.set(0, HiddenStringUtils.replaceHiddenString(lore.get(0), json));

          // Now we save the changes.
          meta.setLore(lore);

          if (mobKills >= 50) {
               // Change the display name with 50+ kills.
               meta.setDisplayName(ChatColor.GOLD + "Mob Slayer");
          }

     inHand.setItemMeta(meta);
     }
}
````
