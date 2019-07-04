package Test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.AnsiColor;


import static org.junit.jupiter.api.Assertions.*;

public class WeaponCardTest {

    private WeaponCard card;

    @BeforeEach
    public void init() {
        card = new WeaponCard();
    }

    @Test
    public void constructorTest() {
        assertTrue(card instanceof WeaponCard, "ERROR of instance");

        WeaponCard card2 = new WeaponCard(card);
        assertTrue(card2 instanceof WeaponCard, "ERROR of instance");
        //System.out.println(AnsiColor.RED+"\uD83D\uDDA4");
        System.out.print(AnsiColor.GREEN + "Sprog" + AnsiColor.RESET + " ha un numero diverso di ammo rispetto a prima:\n");
        System.out.print("Ammo " + AnsiColor.BLUE + "blu" + AnsiColor.RESET + ": 3 ");
        for (int i = 0; i < 3; i++) {
            System.out.print(AnsiColor.BLUE + "⬜" + AnsiColor.RESET);
        }
        System.out.print("\n");
        System.out.print("Ammo " + AnsiColor.RED + "rosse" + AnsiColor.RESET + ": 2 ");
        for (int i = 0; i < 2; i++) {
            System.out.print(AnsiColor.RED + "⬜" + AnsiColor.RESET);
        }
        System.out.print("\n");
        System.out.print("Ammo " + AnsiColor.YELLOW + "gialle" + AnsiColor.RESET + ": 1 ");
        for (int i = 0; i < 1; i++) {
            System.out.print(AnsiColor.YELLOW + "⬜" + AnsiColor.RESET);
        }
        System.out.println("\n");

        System.out.print("A" + AnsiColor.GREEN + " Sprog" + AnsiColor.RESET + " si e' cambiato il numero dei marchi in: 4 ");
        for (int i = 0; i < 4; i++) {
            System.out.print(AnsiColor.GREEN + "\uD83D\uDCA7" + AnsiColor.RESET);
        }
        System.out.println("\n");

        System.out.print(AnsiColor.GREEN + "Sprog" + AnsiColor.RESET + " ha subito danni e quindi e' rimasto con 6 vite ");
        for (int i = 0; i < 6; i++) {
            System.out.print(AnsiColor.GREEN+"\uD83D\uDDA4" + AnsiColor.RESET);
        }
    }
    @Test
    public void equalsTest() {
        assertFalse(card.equals(null), "ERROR: must be false card not equals with null");

        WeaponCard card2 = card;
        assertTrue(card.equals(card2), "ERROR: must be true cards are the same object");

        AmmoCard card3 = new AmmoCard();
        assertFalse(card.equals(card3), "ERROR: must be false, they are different classes");

        //TODO
        //oggetti distinti ma uguali->serve costruttore
    }

   /* @Test
    void jsonLoadTest() {
        JsonFileReader jsonFileReader = new JsonFileReader();// C:\Users\new xps\Documents\ing-sw-2019-44\src\main\resources\cards\weaponCards.json
       // loadWeaponCards is never used
        String cards = jsonFileReader.loadWeaponCards("src/main/resources/cards/weaponCards.json");

        assertNotNull(cards);

      //  System.out.println("cards "+cards);

        Match match = new Match();
        List<WeaponCard> weaponCards = null;

        System.out.println(weaponCards);
        System.out.println(weaponCards.get(0).getBaseEffect().get(0).getMarks());
        System.out.println(weaponCards.get(0).getBaseEffect().get(0).getStrategy());
        System.out.println(weaponCards.get(6).getAdvancedEffect().get(0).toString());

        System.out.println(weaponCards.get(1).getName());
        for(int i=0;i<21;i++){
        System.out.println("size qua:"+weaponCards.get(i).getBaseEffect().get(0).getOptionalEffects().size());}
        System.out.println(weaponCards.size());

        for(WeaponCard weaponCard : weaponCards){
            System.out.println(weaponCard.getBaseEffect().size());
            for(Effect baseEffect : weaponCard.getBaseEffect()){
                System.out.println("effects size"+baseEffect.getOptionalEffects().size());
                for(OptionalEffect optionalEffect : baseEffect.getOptionalEffects()){
                    String costs = "1";
                    for(String cost : optionalEffect.getCost()){
                        System.out.println(costs+" "+cost);
                    }
                }

            }
        }

    }*/
}
