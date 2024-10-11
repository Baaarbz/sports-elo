# F1 Elo
Formula 1 ELO system. Who are the GOATs? Let's see

- Due to the car made huge differences the evaluation will we against team mates
- Equations to calculate elo (as in chess):

  [Elo Rating System](https://stanislav-stankovic.medium.com/elo-rating-system-6196cc59941e)

  $$
  R'=R+K(S-E)\\
$$
$$
  E=\cfrac{Q_A}{(Q_A + Q_B)}\\
$$
$$
  Q = 10^{\cfrac{R}{(400)}}
  $$

    - $Q_A$ for driver
    - $Q_B$ for teammate
    - $R'$ new driver rating
    - $R$  old driver rating
    - $K(S-E)$ Rating winnings or losings for driver
    - $S$ Result against teammate
        - Win: $S=1$
        - Draw: $S=0.5$
        - Lose: $S=0$
    - $K$ is a multiplier we will use $K=32$ the double than chess.com
- A rookie will always join with 1000 of ELO
- If in one race there are more than 2 drivers (Argentina GP 1955) per team the drivers will get wins and loses depends of the final position, accumulating the loses or the winnings in terms of ELO, for example:
    - There are 4 drivers for a team in the same race, driver A, B, C and D end the race in the respective positions (1º A, 2º B, 3º C, 4º D)
    - Each driver wins and loses the same ELO, for example 10

  Then the result will end as:

    - Driver A: Wins $+30$ ELO (wins against 3 drivers $+10 * 3 = 30$ )
    - Driver B: Wins $+10$ ELO (wins against 2 drivers $+10 * 2 = 20$  but lose against 1 driver $-10*1 = -10$)
    - Driver C: Lose$-10$ ELO (wins against 1 driver $+10 * 1 = 10$  but loses against 2 drivers $-10*2 = -20$)
    - Driver D: Lose $-30$ ELO (loses against 3 drivers $-10 * 3 = -30$ )
- If more than one driver shares the same car (Argentina GP 1955) we will take the average ELO of that car and they will win or lose the same amount of ELO
- If a driver race for multiple teams in the same weekend we will ignore it (1978 Italian GP)
- Clear not wanted data of Indie 500 race
- Load until last completed season the ELO system with excel:

[Formula 1 World Championship (1950 - 2024)](https://www.kaggle.com/datasets/rohanrao/formula-1-world-championship-1950-2020?resource=download)

- How to evaluate perfomance taking into account the car. Options next iteration:
    - Add a new multiplier to  $R'=R+K(S-E)$  as for example: $R'=R+K(S-E)*TR$ where $TR$ is **T**eam **R**ating, tradeoffs:
        - ELO only can be calculated at the end of the season.
        - It should be an ELO if you win against teammate and other if you lose, for example: you have the worst car but you end 1º and 2º in the race, you should not lose ELO, or at least you should not lose a lot, hard to fix this case (to not lose or win ELOif you  end in front of other cars…)
        - If you have the 10th car and you en 8th when you expected result is 19th and 20th you should win a lot more
    - Increase/decrease the value of $S$ depending if the driver is performing above or under the car performance
        - $R'=R+K((S+TP)-E)$
        - Where TP is theorical performance
        - The values of TP will be bases on:
            - If you finish last with the best car you should be penalized as a lost $TP = -1$
            - If you finish first with the best car nothing should change $TP= 0$
            - And the same but inversed for the worst car
        - This makes more sense than multiplying randomly, if the driver is performing better than the car therically performance will ve rewarded but, if is underperforming then will be penalized
        - If the driver end as DNF, we dont know if was by his mistake (should get full penalization) or mechanical failure (not deserve full penalization) then, in this case, this new value called $TP$ well be equals to 0
        - In the table bellow well be shown how this will be calculated, X axis = theorical position Y axis = driver position at race

  |  | 1º | 2º | 3º | 4º | 5º | 6º | 7º | 8º | 9º | 10º | 11º | 12º | 13º | 14º | 15º |
      | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
  | 1º | 0 | 0 |  |  |  |  |  |  |  |  |  |  |  |  |  |
  | 2º | 0 | 0 |  |  |  |  |  |  |  |  |  |  |  |  |  |
  | 3º |  |  | 0 | 0 |  |  |  |  |  |  |  |  |  |  |  |
  | 4º |  |  | 0 | 0 |  |  |  |  |  |  |  |  |  |  |  |
  | 5º |  |  |  |  | 0 | 0 |  |  |  |  |  |  |  |  |  |
  | 6º |  |  |  |  | 0 | 0 |  |  |  |  |  |  |  |  |  |
  | 7º |  |  |  |  |  |  | 0 | 0 |  |  |  |  |  |  |  |
  | 8º |  |  |  |  |  |  | 0 | 0 |  |  |  |  |  |  |  |
  | 9º |  |  |  |  |  |  |  |  | 0 | 0 |  |  |  |  |  |
  | 10º |  |  |  |  |  |  |  |  | 0 | 0 |  |  |  |  |  |
  | 11º |  |  |  |  |  |  |  |  |  |  | 0 | 0 |  |  |  |
  | 12º |  |  |  |  |  |  |  |  |  |  | 0 | 0 |  |  |  |
  | 13º |  |  |  |  |  |  |  |  |  |  |  |  | 0 | 0 |  |
  | 14º |  |  |  |  |  |  |  |  |  |  |  |  | 0 | 0 |  |
  | 15º |  |  |  |  |  |  |  |  |  |  |  |  |  |  | 0 |