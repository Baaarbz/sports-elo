# F1 Elo

[![CD/CI](https://github.com/Baaarbz/f1-elo/actions/workflows/main.yml/badge.svg)](https://github.com/Baaarbz/f1-elo/actions/workflows/main.yml)

> [!NOTE]
> ### Disclaimer
> This is a personal project, I am not affiliated with Formula 1 or any of its teams. The data used in this project is
> from the [Jolpica F1 API](https://github.com/jolpica/jolpica-f1). I run personally with the expenses of the server,
> domain and so on. If you like this project and want to support it, you can buy me a coffee.

**Formula 1 ELO system. Who are the GOATs? Let's see!**
<!-- TOC -->
* [F1 Elo](#f1-elo)
  * [About the project](#about-the-project)
  * [Assumptions](#assumptions)
  * [Ways of calculating the rating](#ways-of-calculating-the-rating)
    * [ELO System](#elo-system)
      * [How to calculate it](#how-to-calculate-it)
      * [Corner cases](#corner-cases)
        * [More than 2 drivers per team (Argentina GP 1955...)](#more-than-2-drivers-per-team-argentina-gp-1955)
        * [$N$ drivers share the same car in the race (Argentina GP 1955...)](#n-drivers-share-the-same-car-in-the-race-argentina-gp-1955)
        * [Driver race for multiple teams in the same weekend (1978 Italian GP)](#driver-race-for-multiple-teams-in-the-same-weekend-1978-italian-gp)
    * [iRating System](#irating-system)
    * [TrueSkill System](#trueskill-system)
  * [Milestones](#milestones)
  * [References](#references)
<!-- TOC -->

## About the project

This project is a personal project to calculate the ELO of the Formula 1 drivers, inspired in the next video of [Mr V's Garage](https://www.youtube.com/live/U16a8tdrbII?t=1046s) and motivated to know who are the greatest of all time. 
<br/>The system will count with 3 different ways to calculate the rating of the drivers: ELO System, iRating system and TrueSkill system.

## Assumptions

- Rookie drivers will start with 1000 ELO/rating
- Indy 500 drivers will be ignored


## Ways of calculating the rating

### ELO System

> [!IMPORTANT]
> As the drivers does not compete with equal machinery we are going to evaluate the drivers against their teammates.

Method for calculating the relative skill levels of players in two-player games such as chess. It is named after its creator Arpad Elo, a Hungarian-American physics professor. This method is also used in different sports and e-sports.

#### How to calculate it

$$
R'=R+K(S-E)\\
$$
$$
E=\cfrac{Q_A}{(Q_A + Q_B)}\\
$$
$$
Q = 10^{\cfrac{R}{(400)}}
$$

| Parameters  | Description                                                                            |
|-------------|----------------------------------------------------------------------------------------|
| $Q_A$       | $Q_A$ for driver                                                                       |
| $Q_B$       | $Q_B$ for teammate                                                                     |
| $R'$        | New driver rating                                                                      |
| $R$         | Old driver rating                                                                      |
| $K$         | Multiplier used. Value $K=32$                                                          |
| $S$         | Value depending on the result against the teammate ${Win: 1 // Draw: 0.5 // Lose: 0}$  |
| $K(S-E)$    | Rating winnings or losings for driver                                                  |


#### Corner cases
> [!NOTE] 
> Before 1981 the Formula 1 was not as structured as it is now (number of drivers per team racing, drivers for different teams in the same race...), so we need to take into account some corner cases

* _**More than 2 drivers per team (Argentina GP 1955...)**_<br/>

The drivers will get wins and loses depends on the final position, accumulating the loses or the winnings in terms of ELO but knowing to compensate the winnings and loses in the team, the $K$ multiplier will be decreased depending on the number of drivers for the team following the next formula: $K = (32 - (N - 1)) * 1.5$

| Number of drivers in the team | $K$ |
|-------------------------------|-----|
| 2                             | 32  |
| 3                             | 24  |
| 4                             | 16  |
| 5                             | 9.6 |
| ...                           | ... |



* **_$N$ drivers share the same car in the race (Argentina GP 1955...)_** <br/>
We will take the average ELO of that car, and it will be used to calculate the new rating for each driver.


* **_Driver race for multiple teams in the same weekend (1978 Italian GP)_** <br/>

He will get ELO updated for each team.

### iRating System

TBD

### TrueSkill System

TBD

## Milestones

TBD

## References

[Elo Rating System](https://stanislav-stankovic.medium.com/elo-rating-system-6196cc59941e) <br/>
[Jolpica F1 API](https://github.com/jolpica/jolpica-f1)<br/>
[Mr V's Garage inspiring video](https://www.youtube.com/live/U16a8tdrbII?t=1046s)<br/>
[TrueSkill System](https://www.microsoft.com/en-us/research/project/trueskill-ranking-system/)<br/>
[TrueSkill System Paper](https://www.microsoft.com/en-us/research/wp-content/uploads/2007/01/NIPS2006_0688.pdf)<br/>
[ELO System](https://en.wikipedia.org/wiki/Elo_rating_system)<br/>
[iRating System](https://www.iracing.com/license-progression/) <br/>