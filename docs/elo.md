# ELO System

> [!IMPORTANT]
> As the drivers does not compete with equal machinery we are going to evaluate the drivers against their teammates.

Method for calculating the relative skill levels of players in two-player games such as chess. It is named after its creator Arpad Elo, a Hungarian-American physics professor. This method is also used in different sports and e-sports.

## How to calculate it

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


## Corner cases
> [!NOTE]
> Before 1981 the Formula 1 was not as structured as it is now (number of drivers per team racing, drivers for different teams in the same race...), so we need to take into account some corner cases

### _More than 2 drivers per team (Argentina GP 1955...)_

The drivers will get wins and loses depends on the final position, accumulating the loses or the winnings in terms of ELO but knowing to compensate the winnings and loses in the team, the $K$ multiplier will be decreased depending on the number of drivers for the team following the next formula: $K = (32 - (N - 1)) * 1.5$

| Number of drivers in the team | $K$ |
|-------------------------------|-----|
| 2                             | 32  |
| 3                             | 24  |
| 4                             | 16  |
| 5                             | 9.6 |
| ...                           | ... |



### _N drivers share the same car in the race (Argentina GP 1955...)_

We will take the average ELO of that car, and it will be used to calculate the new rating for each driver.

### _Driver race for multiple teams in the same weekend (1978 Italian GP)_

He will get ELO updated for each team.