package fr.enissay.dqluck;

import fr.enissay.dqluck.items.DQItem;
import fr.enissay.dqluck.items.DQItemsManager;
import fr.enissay.dqluck.items.ItemRarity;
import fr.enissay.dqluck.items.ItemType;
import fr.enissay.dqluck.run.DQRun;
import fr.enissay.dqluck.utils.Logger;
import org.fusesource.jansi.Ansi;

import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Main {

    private static DQItemsManager dqItemsManager = new DQItemsManager();

    public static void main(String[] args) {

        new Thread(() -> {

            // Adding the items
            Logger.logInfo("Adding the items...");

            // Weapons
            add(new DQItem("Eldenbark Greatsword", ItemRarity.LEGENDARY, ItemType.WEAPON, 0.1996, false));
            add(new DQItem("Eldenbark Greatstaff", ItemRarity.LEGENDARY, ItemType.WEAPON, 0.1996, false));

            add(new DQItem("Crystalline Shard Staff", ItemRarity.COMMON, ItemType.WEAPON, 4.0, true));
            add(new DQItem("Overgrown War Scythe", ItemRarity.COMMON, ItemType.WEAPON, 4.0, true));
            add(new DQItem("Forest Vine Spellblade", ItemRarity.COMMON, ItemType.WEAPON, 6.0, true));
            add(new DQItem("Forest Vine Hatchet", ItemRarity.COMMON, ItemType.WEAPON, 6.0, true));
            add(new DQItem("Enchanted Shard Staff", ItemRarity.COMMON, ItemType.WEAPON, 8.0, true));
            add(new DQItem("Enchanted Shard War Axe", ItemRarity.COMMON, ItemType.WEAPON, 8.0, true));

            // Armors
            add(new DQItem("Eldenbark Warrior Helmet", ItemRarity.COMMON, ItemType.ARMOR, 0.2096, true));
            add(new DQItem("Eldenbark Warrior Armor", ItemRarity.COMMON, ItemType.ARMOR, 0.2096, true));

            add(new DQItem("Eldenbark Mage Helmet", ItemRarity.COMMON, ItemType.ARMOR, 0.2096, true));
            add(new DQItem("Eldenbark Mage Armor", ItemRarity.COMMON, ItemType.ARMOR, 0.2096, true));

            add(new DQItem("Eldenbark Guardian Helmet", ItemRarity.COMMON, ItemType.ARMOR, 0.2096, true));
            add(new DQItem("Eldenbark Guardian Armor", ItemRarity.COMMON, ItemType.ARMOR, 0.2096, true));

            add(new DQItem("Crystalline Warrior Helmet", ItemRarity.COMMON, ItemType.ARMOR, 8.0, true));
            add(new DQItem("Crystalline Warrior Armor", ItemRarity.COMMON, ItemType.ARMOR, 8.0, true));

            add(new DQItem("Crystalline Mage Helmet", ItemRarity.COMMON, ItemType.ARMOR, 8.0, true));
            add(new DQItem("Crystalline Mage Armor", ItemRarity.COMMON, ItemType.ARMOR, 8.0, true));

            add(new DQItem("Crystalline Guardian Helmet", ItemRarity.COMMON, ItemType.ARMOR, 8.0, true));
            add(new DQItem("Crystalline Guardian Armor", ItemRarity.COMMON, ItemType.ARMOR, 8.0, true));

            // Spells
            add(new DQItem("Enhanced Inner Rage", ItemRarity.LEGENDARY, ItemType.SPELL, 0.025, false));
            add(new DQItem("Enhanced Inner Focus", ItemRarity.LEGENDARY, ItemType.SPELL, 0.025, false));

            add(new DQItem("Crystalline Cannon", ItemRarity.EPIC, ItemType.SPELL, 8.0, false));
            add(new DQItem("Wind Blast", ItemRarity.EPIC, ItemType.SPELL, 8.0, false));
            add(new DQItem("Agony Orbs", ItemRarity.EPIC, ItemType.SPELL, 8.0, false));
            add(new DQItem("Lightning Burst", ItemRarity.EPIC, ItemType.SPELL, 8.0, false));

            /*LinkedList<Double> list = new LinkedList<>();
            for (double count : getDqItemsManager().getItems().stream().map(DQItem::getChance).collect(Collectors.toList()))
                list.add(count);

            final double totalChance = list.stream().mapToDouble(Double::doubleValue).sum() * 100;

            MockNeat m = MockNeat.threadLocal();
            Probabilities<DQItem> s = m.probabilites(DQItem.class);
            getDqItemsManager().getItems().forEach(items -> {
                //Logger.logInfo("Added: " + items.getName() + " - " + items.getChance() + " TOTAL: " + totalChance + "%");
                s.add(items.getChance(), items);
            });*/

            final Map<DQItem, Double> map = new HashMap<>();

            getDqItemsManager().getItems().forEach(items -> {
                map.put(items, items.getChance());
            });

            int runs = 1;
            long delay = 0L;
            boolean withExtraitem = false;

            final Scanner scanner = new Scanner(System.in);
            Logger.logInput("Please enter the number of runs you want to test:");
            runs = scanner.nextInt();

            Logger.logInput("Do you want to test with the ExtraItem Game Pass ? (Type Y/N):");
            if (scanner.hasNext()) {
                String input = scanner.next();
                if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) withExtraitem = true;
                else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) withExtraitem = false;
            }
            Logger.logInput("Please enter the number of delay in milliseconds (if you don't want any delay just set to 0):");
            if (scanner.hasNext()) {
                long input = scanner.nextLong();
                delay = input;

                scanner.close();
            }
            System.out.println("");

            int multiplier = withExtraitem ? 3 : 2;

            final LinkedList<DQItem> result = new LinkedList<>();
            final List<DQRun> dqRuns = new ArrayList<>();
            final String s = Ansi.ansi().fgRgb(128, 128, 128) + "|" + Ansi.ansi().reset();

            for (int i = 1; i < runs + 1; i++) {
                final LinkedList<DQItem> dqItems = new LinkedList<>();
                for (int f = 1; f < multiplier + 1; f++) {
                    final DQItem newInstance = getRandomWeighted(map);
                    if (newInstance.shouldChangeRarity()) {
                        final ItemRarity randomRarity = randomEnum(ItemRarity.class);
                        if (randomRarity != ItemRarity.LEGENDARY) newInstance.setRarity(randomRarity);
                    }
                    // To prevent duplicated Items
                    if (!dqItems.contains(newInstance))
                        dqItems.add(newInstance);
                    else dqItems.add(new DQItem(newInstance.getName(), newInstance.getRarity(), newInstance.getType(), newInstance.getChance(), newInstance.shouldChangeRarity()));
                    result.add(newInstance);
                }
                dqRuns.add(new DQRun(withExtraitem, dqItems));
            }
            long finalDelay = delay;
            dqRuns.forEach(theRuns -> {
                LinkedList<DQItem> theItems = theRuns.getItems();

                System.out.println("--------------------| RUN #" + (dqRuns.indexOf(theRuns) + 1) + " |--------------------");
                final AtomicReference<String> runResult = new AtomicReference<>("");

                /*if (theRuns.isWithExtraItem()) theItems = Arrays.asList(item1, item2, item3);
                else theItems = Arrays.asList(item1, item2);*/

                theItems.forEach(newInstance -> {
                    System.out.println("You got... " + newInstance.toDrop() + " " + s + " Rarity: " + newInstance.getRarity().getColor() + newInstance.getRarity().getName() + Logger.cleanCodes("/rs/") + " " + s + " Type: " + newInstance.getType() + " " + s + " Chance to drop: " + Logger.cleanCodes("/bb/") + newInstance.getChance() + "%" + Logger.cleanCodes("/rs/") + " " + s + " Index: " + theRuns.getItems().indexOf(newInstance));
                });

                theItems.forEach(dqItem -> {
                    //Logger.logDebug("ITEM: " + dqItem.toDrop() + " - INDEX: " + theItems.indexOf(dqItem));

                    runResult.set(Logger.cleanCodes("/r/None :("));
                    if(theItems.stream().filter(runItem -> runItem.getRarity() == ItemRarity.LEGENDARY).collect(Collectors.toList()).size() == 1) runResult.set(Logger.cleanCodes("/g/Good"));
                    else if(theItems.stream().filter(runItem -> runItem.getRarity() == ItemRarity.LEGENDARY).collect(Collectors.toList()).size() > 1) runResult.set(Logger.cleanCodes("/m/OMGGGGG"));
                });
                Logger.logCustom("/bb/Result for legendaries: " + runResult + "/rs/");
                try {
                    Thread.sleep(finalDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("--------------------------------------------------");
            System.out.println("");
            Logger.logInfo("Result for " + runs + " runs in Enchanted Forest Nightmare: ");
            Logger.logInfo(ItemRarity.LEGENDARY.getColor() + "Legendaries: " + result.stream().filter(dqItem -> dqItem.getRarity() == ItemRarity.LEGENDARY).collect(Collectors.toList()).size());
            Logger.logInfo(ItemRarity.EPIC.getColor() + "Epics: " + result.stream().filter(dqItem -> dqItem.getRarity() == ItemRarity.EPIC).collect(Collectors.toList()).size());
            Logger.logInfo(ItemRarity.RARE.getColor() + "Rares: " + result.stream().filter(dqItem -> dqItem.getRarity() == ItemRarity.RARE).collect(Collectors.toList()).size());
            Logger.logInfo(ItemRarity.UNCOMMON.getColor() + "Uncommons: " + result.stream().filter(dqItem -> dqItem.getRarity() == ItemRarity.UNCOMMON).collect(Collectors.toList()).size());
            Logger.logInfo(ItemRarity.COMMON.getColor() + "Commons: " + result.stream().filter(dqItem -> dqItem.getRarity() == ItemRarity.COMMON).collect(Collectors.toList()).size());
            System.out.println("");
            final double chance = (runs * 100) / 101; // 101 cuz it's 100% that you'll have a legendary
            Logger.logInfo("Chance to have a legendary every " + runs + " runs: " + (chance > 100 ? 100 : chance) + "%");
            System.out.println("");
            Logger.logCustom("/y/Gold earned: " + format(170000000.0 * runs));
            Logger.logCustom("/y/Gold earned with 2x Gold Game Pass: " + format(340000000.0 * runs));
            System.out.println("");
            Logger.logCustom("/m/XP earned: " + format(11280000000.0 * runs));
            Logger.logCustom("/m/XP earned with VIP: " + format(13536000000.0 * runs));
            Logger.logCustom("/m/XP earned with Boost: " + format(22560000000.0 * runs));
            Logger.logCustom("/m/XP earned with VIP+Boost: " + format(24816000000.0 * runs));
            System.out.println("");

            int luck = 0;

            String resultLuck = Logger.cleanCodes("/r/") + "Not lucky";
            luck+=result.stream().filter(dqItem -> dqItem.getRarity() == ItemRarity.LEGENDARY).collect(Collectors.toList()).size();
            luck+=result.stream().filter(dqItem -> dqItem.getRarity() == ItemRarity.EPIC && (dqItem.getName().contains("Eldenbark Warrior") || dqItem.getName().contains("Eldenbark Mage") || dqItem.getName().contains("Eldenbark Guardian"))).collect(Collectors.toList()).size();

            if (luck == 1) resultLuck = Logger.cleanCodes("/g/") + "Lucky";
            else if (luck >= 2) resultLuck = Logger.cleanCodes("/m/") + "Very Lucky";

            Logger.logInfo("This run was : " + resultLuck);

            /*int multiplier = withExtraitem ? 3 : 2;

            LinkedList<DQItem> result = new LinkedList<>();
            for (int i = 1; i < (runs * multiplier) + 1; i++){
                DQItem newInstance = getRandomWeighted(map);
                if (newInstance.shouldChangeRarity()) {
                    final ItemRarity randomRarity = randomEnum(ItemRarity.class);
                    if (randomRarity != ItemRarity.LEGENDARY) newInstance.setRarity(randomRarity);
                }
                result.add(newInstance);

                final String s = Ansi.ansi().fgRgb(128, 128, 128) + "|" + Ansi.ansi().reset();
                final List<DQItem> runsItems = new ArrayList<>();

                if (i == 1) System.out.println("--------------------------------------------------");
                if (i % 1 == 0 || i % 2 == 1) runsItems.add(result.get(i - 1));
                System.out.println("You got... " + newInstance.toDrop() + " " + s + " Rarity: " + newInstance.getRarity().getColor() + newInstance.getRarity().getName() + Logger.cleanCodes("/rs/") + " " + s + " Type: " + newInstance.getType() + " " + s + " Chance to drop: " + Logger.cleanCodes("/bb/") + newInstance.getChance() + "%" + Logger.cleanCodes("/rs/"));
                if (i % multiplier == 0) {
                    runsItems.forEach(dqItem -> {
                        Logger.logInfo("ITEM: " + dqItem.toDrop());

                        String runResult = Logger.cleanCodes("/r/None :(");
                        if(runsItems.stream().filter(runItem -> runItem.getRarity() == ItemRarity.LEGENDARY).collect(Collectors.toList()).size() == 1) runResult = Logger.cleanCodes("/g/Good");
                        else if(runsItems.stream().filter(runItem -> runItem.getRarity() == ItemRarity.LEGENDARY).collect(Collectors.toList()).size() > 1) runResult = Logger.cleanCodes("/m/OMGGGGG");

                        Logger.logCustom("/bb/Result for legendaries: " + runResult + "/rs/");
                    });
                    System.out.println("--------------------| RUN #" + (i / multiplier) + " |--------------------");
                    runsItems.clear();
                }
            }

            System.out.println("");
            Logger.logInfo("Result for " + runs + " runs in Enchanted Forest Nightmare: ");
            Logger.logInfo(ItemRarity.LEGENDARY.getColor() + "Legendaries: " + result.stream().filter(dqItem -> dqItem.getRarity() == ItemRarity.LEGENDARY).collect(Collectors.toList()).size());
            Logger.logInfo(ItemRarity.EPIC.getColor() + "Epics: " + result.stream().filter(dqItem -> dqItem.getRarity() == ItemRarity.EPIC).collect(Collectors.toList()).size());
            Logger.logInfo(ItemRarity.RARE.getColor() + "Rares: " + result.stream().filter(dqItem -> dqItem.getRarity() == ItemRarity.RARE).collect(Collectors.toList()).size());
            Logger.logInfo(ItemRarity.UNCOMMON.getColor() + "Uncommons: " + result.stream().filter(dqItem -> dqItem.getRarity() == ItemRarity.UNCOMMON).collect(Collectors.toList()).size());
            Logger.logInfo(ItemRarity.COMMON.getColor() + "Commons: " + result.stream().filter(dqItem -> dqItem.getRarity() == ItemRarity.COMMON).collect(Collectors.toList()).size());
            System.out.println("");
            final double chance = (runs * 100) / 101; // 101 cuz it's 100% that you'll have a legendary
            Logger.logInfo("Chance to have a legendary every " + runs + " runs: " + (chance > 100 ? 100 : chance) + "%");
            System.out.println("");
            Logger.logCustom("/y/Gold earned: " + format(170000000.0 * runs));
            Logger.logCustom("/y/Gold earned with 2x Gold Game Pass: " + format(340000000.0 * runs));
            System.out.println("");
            Logger.logCustom("/m/XP earned: " + format(11280000000.0 * runs));
            Logger.logCustom("/m/XP earned with VIP: " + format(13536000000.0 * runs));
            Logger.logCustom("/m/XP earned with Boost: " + format(22560000000.0 * runs));
            Logger.logCustom("/m/XP earned with VIP+Boost: " + format(24816000000.0 * runs));
            System.out.println("");

            int luck = 0;

            String resultLuck = Logger.cleanCodes("/r/") + "Not lucky";
            luck+=result.stream().filter(dqItem -> dqItem.getRarity() == ItemRarity.LEGENDARY).collect(Collectors.toList()).size();
            luck+=result.stream().filter(dqItem -> dqItem.getRarity() == ItemRarity.EPIC && (dqItem.getName().contains("Eldenbark Warrior") || dqItem.getName().contains("Eldenbark Mage") || dqItem.getName().contains("Eldenbark Guardian"))).collect(Collectors.toList()).size();

            if (luck == 1) resultLuck = Logger.cleanCodes("/g/") + "Lucky";
            else if (luck >= 2) resultLuck = Logger.cleanCodes("/m/") + "Very Lucky";

            Logger.logInfo("This run was : " + resultLuck);*/

        }).start();

        while (true){

        }
    }

    private static String format(double number){
        String[] suffix = new String[]{"K","M","B","T"};
        int size = (number != 0) ? (int) Math.log10(number) : 0;
        if (size >= 3){
            while (size % 3 != 0) {
                size = size - 1;
            }
        }
        double notation = Math.pow(10, size);
        String result = (size >= 3) ? + (Math.round((number / notation) * 100) / 100.0d)+suffix[(size/3) - 1] : + number + "";
        return result;
    }

    private static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        Random random = new Random();
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    /**
     * Gets a random-weighted object.
     * @param balancedObjects the map with objects and their weights.
     * @return a random key-object from the map with a chance equal to its weight/totalWeight.
     * @throws IllegalArgumentException if total weight is not positive.
     */
    private static <E> E getRandomWeighted(Map<E, ? extends Number> balancedObjects) throws IllegalArgumentException {
        double totalWeight = balancedObjects.values().stream().mapToInt(Number::intValue).sum(); // Java 8

        if (totalWeight <= 0)
            throw new IllegalArgumentException("Total weight must be positive.");

        double value = Math.random()*totalWeight, weight = 0;

        for (Map.Entry<E, ? extends Number> e : balancedObjects.entrySet()) {
            weight += e.getValue().doubleValue();
            if (value < weight)
                return e.getKey();
        }

        return null; // Never will reach this point
    }

    public static DQItemsManager getDqItemsManager() {
        return dqItemsManager;
    }

    private static void add(final DQItem dqItem){
        getDqItemsManager().addItem(dqItem);
    }
}
