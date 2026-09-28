#include "Player.cpp"

int main() {
    Player hero;
    hero.name = "Aria";
    hero.printPlayer();

    buyPotion(hero);
    hero.printPlayer();

    buyPotion(hero);
    hero.printPlayer();

    pickUpBoots(hero);
    pickUpBoots(hero);
    hero.printPlayer();

    fallInLava(hero);
    fallInLava(hero);
    hero.printPlayer();

    return 0;
}