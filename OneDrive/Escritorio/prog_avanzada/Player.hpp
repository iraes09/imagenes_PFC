#ifndef PLAYER_HPP
#define PLAYER_HPP

#include <cstdio>
#include <string>

#define clamp(a,b,c)  std::min((c),std::max((b),(a)))

class Player {

    public:
    std::string name;
    void increaseLife(int change);
    bool spendMoney(int change);
    void increaseSpeed(float change);
    void printPlayer();
    
    private:
    float x = 0.0f;
    float y = 0.0f;
    float speed = 5.0f;
    float minSpeed = 1.0f;
    float maxSpeed = 10.0f;
    int hp = 100;
    int maxHp = 100;
    int gold = 50;

};

void buyPotion(Player& p);
void fallInLava(Player& p);
void pickUpBoots();

#endif