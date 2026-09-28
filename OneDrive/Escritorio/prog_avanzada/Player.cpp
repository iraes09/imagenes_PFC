#include "Player.hpp"

void Player::increaseLife(int change) {
    hp = clamp(0, hp + change, maxHp);
}

void Player::increaseSpeed(float change) {
    speed = clamp(minSpeed, speed * change, maxSpeed);
}

void Player::printPlayer() {
    printf("%s | pos(%g, %g) | hp %d/%d | gold %d | speed %g\n", name.c_str(), x, y, hp, maxHp, gold, speed);
}

bool Player::spendMoney(int change) {
    if(hp == 0) return false;
    if(gold-change < 0) return false;
    gold -= change;
    return true;
}

void buyPotion(Player& p) {
    if(p.spendMoney(30)) {
        p.increaseLife(40);
    } else {
        printf( "estas pelao");
    }
}

void fallInLava(Player& p) {
    p.increaseLife(-150);
}

void pickUpBoots(Player& p) {
    p.increaseSpeed(10.0f);
}